package br.net.pin.jabx.data;

import java.util.List;
import com.google.gson.Gson;

public class Select implements Fixable {
  public Head table;
  public List<String> fields;
  public List<Clause> clauses;
  public List<String> orders;
  public Integer offset;
  public Integer limit;

  public Select() {}

  public Select(Head table) {
    this.table = table;
  }

  public Select(Head table, List<String> fields, List<Clause> clauses,
      List<String> orders, Integer offset, Integer limit) {
    this.table = table;
    this.fields = fields;
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
