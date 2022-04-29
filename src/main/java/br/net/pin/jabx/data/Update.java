package br.net.pin.jabx.data;

import java.util.List;

import com.google.gson.Gson;

public class Update implements Fixable {
  public Registry registry;
  public List<Valued> valueds;
  public List<Clause> clauses;
  public Integer limit;

  public Update() {
  }

  public Update(Registry registry) {
    this.registry = registry;
  }

  public Update(Registry registry, List<Valued> valueds) {
    this.registry = registry;
    this.valueds = valueds;
  }

  public Update(Registry registry, List<Valued> valueds, List<Clause> clauses) {
    this.registry = registry;
    this.valueds = valueds;
    this.clauses = clauses;
  }

  public Update(Registry registry, List<Valued> valueds, List<Clause> clauses, Integer limit) {
    this.registry = registry;
    this.valueds = valueds;
    this.clauses = clauses;
    this.limit = limit;
  }

  @Override
  public String toString() {
    return new Gson().toJson(this);
  }

  public static Update fromString(String json) {
    return new Gson().fromJson(json, Update.class);
  }
}
