package br.net.pin.jabx.file;

import java.io.File;
import java.nio.file.Files;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.Objects;
import br.net.pin.jabx.data.Linker;
import br.net.pin.jabx.data.Table;
import br.net.pin.jabx.data.TableField;
import br.net.pin.jabx.data.TableHead;
import br.net.pin.jabx.flow.Pace;
import br.net.pin.jabx.mage.WizFile;

public class CSVImport extends Thread {
  private final Pace progress;
  private final File origin;
  private final Linker destiny;

  public CSVImport(File origin, Linker destiny, Pace progress) {
    super("ImportFromCSV");
    this.origin = origin;
    this.destiny = destiny;
    this.progress = progress;
  }

  @Override
  public void run() {
    try {
      if (!origin.exists()) {
        throw new Exception("The origin must exist.");
      }
      try (Connection connection = destiny.connect()) {
        progress.log("Connected to destiny database.");
        if (origin.isFile()) {
          importCSVFile(origin, connection);
        } else {
          for (File inside : origin.listFiles()) {
            if (isCSVFile(inside)) {
              importCSVFile(inside, connection);
            }
          }
        }
      }
      progress.log("Finished to import from CSV.");
    } catch (Exception e) {
      progress.log(e);
    }
  }

  private boolean isCSVFile(File file) {
    return file.isFile() && file.getName().toLowerCase().endsWith(".csv");
  }

  private void importCSVFile(File csvFile, Connection connection) throws Exception {
    progress.log("Importing CSV File: " + csvFile.getName());
    String tableName = WizFile.getBaseName(csvFile.getName());
    File tableFile = new File(csvFile.getParent(), tableName + ".tab");
    Table table;
    if (tableFile.exists()) {
      progress.log("Loading table metadata from file.");
      table = Table.fromString(Files.readString(tableFile.toPath()));
      destiny.base.getHelper().createTable(connection, table, true);
    } else {
      progress.log("Loading table metadata from connection.");
      String schema = null;
      String name = tableName;
      if (name.contains(".")) {
        schema = WizFile.getBaseName(name);
        name = WizFile.getExtension(name);
      }
      table = new TableHead(null, schema, name).getTable(connection);
    }

    try (CSVFile reader = new CSVFile(csvFile, CSVFile.Mode.READ)) {
      progress.log("CSV File: " + csvFile.getName() + " opened.");
      boolean firstLine = true;
      String[] line;
      long lineCount = 0l;
      while ((line = reader.readLine()) != null) {
        lineCount++;
        progress.log("Processing line  " + lineCount + " of file: " + csvFile.getName());
        Object[] values = new Object[line.length];
        for (int i = 0; i < values.length; i++) {
          if (firstLine) {
            values[i] = line[i];
          } else {
            var field = table.fields.get(i);
            values[i] = field.getValueFrom(line[i]);
            fixValuesForSQLTypes(values, i, field);
          }
        }
        var fields = new ArrayList<TableField>();
        if (firstLine) {
          firstLine = false;
          for (int i = 0; i < values.length; i++) {
            for (TableField field : table.fields) {
              if (Objects.equals(values[i], field.name)) {
                fields.add(field);
                break;
              }
            }
          }
          if (fields.size() == values.length) {
            table.fields = fields;
          }
        } else {
          progress.log("Inserting line  " + lineCount + " of file: " + csvFile.getName());
          destiny.base.getHelper().insert(table, connection, values);
        }
      }
    }
  }

  private void fixValuesForSQLTypes(Object[] values, int i, TableField field) {
    if (values[i] == null)
      return;
    switch (field.nature) {
      case DATE:
        values[i] = new java.sql.Date(((java.util.Date) values[i]).getTime());
        break;
      case TIME:
        values[i] = new java.sql.Time(((java.util.Date) values[i]).getTime());
        break;
      case TIMESTAMP:
        values[i] = new java.sql.Timestamp(((java.util.Date) values[i]).getTime());
        break;
      default:
        break;
    }
  }
}
