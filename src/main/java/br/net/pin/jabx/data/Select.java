package br.net.pin.jabx.data;

import java.util.List;

import com.google.gson.Gson;

public class Select {
  public List<String> fields;
  public TableHead table;
  public List<Clause> clauses;
  public List<String> orders;
  public Integer offset;
  public Integer limit;

  public Select() {
  }

  public Select(TableHead table) {
    this.table = table;
  }

  public Select(List<String> fields, TableHead table, List<Clause> clauses, List<String> orders, Integer offset, Integer limit) {
    this.fields = fields;
    this.table = table;
    this.clauses = clauses;
    this.orders = orders;
    this.offset = offset;
    this.limit = limit;
  }

  @Override
  public String toString() {
      return new Gson().toJson(this);
  }

  public static Table fromString(String json) {
    return new Gson().fromJson(json, Table.class);
  }
}
