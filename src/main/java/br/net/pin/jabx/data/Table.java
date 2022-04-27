package br.net.pin.jabx.data;

import java.util.List;

import com.google.gson.Gson;

public class Table {
  public TableHead head;
  public List<TableField> fields;
  public List<String> keys;

  public Table() {
  }

  public Table(TableHead head, List<TableField> fields, List<String> keys) {
    this.head = head;
    this.fields = fields;
    this.keys = keys;
  }

  public String getSchemaName() {
    return head.getSchemaName();
  }

  public String getCatalogSchemaName() {
    return head.getCatalogSchemaName();
  }

  @Override
  public String toString() {
      return new Gson().toJson(this);
  }

  public static Table fromString(String json) {
    return new Gson().fromJson(json, Table.class);
  }
}
