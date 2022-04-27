package br.net.pin.jabx.data;

import java.util.List;

import com.google.gson.Gson;

public class Insert {
  public TableHead table;
  public List<Valued> valueds;

  public Insert() {
  }

  public Insert(TableHead table, List<Valued> valueds) {
    this.table = table;
    this.valueds = valueds;
  }

  @Override
  public String toString() {
      return new Gson().toJson(this);
  }

  public static Table fromString(String json) {
    return new Gson().fromJson(json, Table.class);
  }
}
