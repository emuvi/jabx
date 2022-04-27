package br.net.pin.jabx.data;

import java.util.List;

import com.google.gson.Gson;

public class Update {
  public TableHead table;
  public List<Valued> valueds;
  public List<Clause> clauses;
  public Integer limit;

  public Update() {
  }

  public Update(TableHead table, List<Valued> valueds, List<Clause> clauses, Integer limit) {
    this.table = table;
    this.valueds = valueds;
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
