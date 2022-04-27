package br.net.pin.jabx.data;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.util.ArrayList;
import com.google.gson.Gson;
import br.net.pin.jabx.mage.WizChars;
import br.net.pin.jabx.mage.WizData;

public class Head {
  public String catalog;
  public String schema;
  public String name;

  public Head() {}

  public Head(String name) {
    this.name = name;
  }

  public Head(String schema, String name) {
    this.schema = schema;
    this.name = name;
  }

  public Head(String catalog, String schema, String name) {
    this.catalog = catalog;
    this.schema = schema;
    this.name = name;
  }

  public String getSchemaName() {
    return WizChars.sum(".", this.schema, this.name);
  }

  public String getCatalogSchemaName() {
    return WizChars.sum(".", this.catalog, this.schema, this.name);
  }

  public String getNameForFile() {
    return WizChars.sum(".", this.catalog, this.schema, this.name);
  }

  public Table getTable(Connection connection) throws Exception {
    var result = new Table(this, new ArrayList<>(), new ArrayList<>());
    var meta = connection.getMetaData();
    var set = meta.getPrimaryKeys(this.catalog, this.schema, this.name);
    while (set.next()) {
      result.keys.add(set.getString(4));
    }
    var rst = meta.getColumns(this.catalog, this.schema, this.name, "%");
    while (rst.next()) {
      var campo = new Field();
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

  public static Head fromString(String json) {
    return new Gson().fromJson(json, Head.class);
  }
}
