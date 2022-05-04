package br.net.pin.jabx.data;

import java.util.List;

import com.google.gson.Gson;

public class Delete implements Fixable {
  public Registry registry;
  public List<Claused> clauses;
  public Integer limit;

  public Delete() {
  }

  public Delete(Registry registry, List<Claused> clauses, Integer limit) {
    this.registry = registry;
    this.clauses = clauses;
    this.limit = limit;
  }

  @Override
  public String toString() {
    return new Gson().toJson(this);
  }

  public static Delete fromString(String json) {
    return new Gson().fromJson(json, Delete.class);
  }
}
