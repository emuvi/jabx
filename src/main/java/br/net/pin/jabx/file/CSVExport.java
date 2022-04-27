package br.net.pin.jabx.file;

import java.io.File;
import java.io.FileOutputStream;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.sql.Connection;
import java.util.List;
import br.net.pin.jabx.data.Linker;
import br.net.pin.jabx.data.Select;
import br.net.pin.jabx.data.Table;
import br.net.pin.jabx.data.TableHead;
import br.net.pin.jabx.flow.Pace;
import br.net.pin.jabx.flow.PaceCmd;

public class CSVExport extends Thread {

  private final Linker origin;
  private final File destiny;
  private final Pace pace;

  public CSVExport(Linker origin, File destiny) {
    this(origin, destiny, null);
  }

  public CSVExport(Linker origin, File destiny, Pace pace) {
    super("ExportToCSV");
    this.origin = origin;
    this.destiny = destiny;
    this.pace = pace != null ? pace : new PaceCmd();
  }

  @Override
  public void run() {
    try {
      pace.log("Origin: " + origin);
      pace.log("Establishing destiny: " + destiny);
      if (!destiny.exists()) {
        Files.createDirectories(destiny.toPath());
      }
      if (!destiny.exists()) {
        throw new Exception("Could not create the destination folder.");
      }
      if (!destiny.isDirectory()) {
        throw new Exception("The destination must be a directory.");
      }
      pace.waitIfPausedAndThrowIfStopped();
      pace.log("Connecting to Origin...");
      try (Connection originConn = origin.connect()) {
        pace.log("Connected.");
        pace.waitIfPausedAndThrowIfStopped();
        pace.log("Getting tables...");
        List<TableHead> heads = origin.base.getHelper().getHeads(originConn);
        for (TableHead head : heads) {
          pace.log("Processing: " + head);
          Table table = head.getTable(originConn);
          try (PrintWriter writer = new PrintWriter(new FileOutputStream(new File(destiny,
              head.getNameForFile() + ".tab"), false), true)) {
            writer.write(table.toString());
          }
          final var fileDestiny = new File(destiny, head.getNameForFile() + ".csv");
          try (CSVFile csvFile = new CSVFile(fileDestiny, CSVFile.Mode.WRITE)) {
            final var row = new String[table.fields.size()];
            for (int i = 0; i < table.fields.size(); i++) {
              row[i] = table.fields.get(i).name;
            }
            csvFile.writeLine(row);
            var rstOrigin = origin.base.getHelper().select(originConn, new Select(head));
            long recordCount = 0;
            while (rstOrigin.next()) {
              recordCount++;
              pace.log("Writing record " + recordCount + " of " + head.name);
              for (int i = 0; i < table.fields.size(); i++) {
                row[i] = table.fields.get(i).formatValue(rstOrigin.getObject(i + 1));
              }
              csvFile.writeLine(row);
            }
          }
        }
      }
      pace.log("CSV Export Finished!");
    } catch (Exception error) {
      pace.log(error);
    }
  }
}
