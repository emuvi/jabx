package br.net.pin.jabx.data;

import java.util.List;
import com.google.gson.Gson;

public class Delete implements Fixable {
  public Head table;
  public List<Clause> clauses;
  public Integer limit;

  public Delete() {}

  public Delete(Head table, List<Clause> clauses, Integer limit) {
    this.table = table;
    this.clauses = clauses;
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
