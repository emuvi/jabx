package br.net.pin.jabx.data;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.util.ArrayList;
import br.net.pin.jabx.mage.WizChars;
import br.net.pin.jabx.mage.WizData;
import com.google.gson.Gson;

public class TableHead {
  public String catalog;
  public String schema;
  public String name;

  public TableHead() {
  }

  public TableHead(String catalog, String schema, String name) {
    this.catalog = catalog;
    this.schema = schema;
    this.name = name;
  }
  
  public String getSchemaName() {
    return WizChars.sum(".", schema, name);
  }

  public String getCatalogSchemaName() {
    return WizChars.sum(".", catalog, schema, name);
  }

  public String getNameForFile() {
    return WizChars.sum(".", catalog, schema, name);
  }

  public Table getTable(Connection connection) throws Exception {
    Table result = new Table(this, new ArrayList<>(), new ArrayList<>());
    DatabaseMetaData meta = connection.getMetaData();
    ResultSet set = meta.getPrimaryKeys(this.catalog, this.schema, this.name);
    while (set.next()) {
      result.keys.add(set.getString(4));
    }
    ResultSet rst = meta.getColumns(this.catalog, this.schema, this.name, "%");
    while (rst.next()) {
      TableField campo = new TableField();
      campo.name = rst.getString(4);
      campo.nature = WizData.getNatureOfSQL(rst.getInt(5));
      campo.size = rst.getInt(7);
      campo.precision = rst.getInt(9);
      campo.notNull = "NO".equals(rst.getString(18));
      campo.key = false;
      if (result.keys.contains(campo.name)) {
        campo.key = true;
      }
      result.fields.add(campo);
    }
    return result;
  }

  @Override
  public String toString() {
      return new Gson().toJson(this);
  }

  public static TableHead fromString(String json) {
    return new Gson().fromJson(json, TableHead.class);
  }
}
