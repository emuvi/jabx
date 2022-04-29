package br.net.pin.jabx.data;

import java.util.List;

import com.google.gson.Gson;

public class Select implements Fixable {
  public Registry registry;
  public List<String> fields;
  public List<Clause> clauses;
  public List<String> orders;
  public Integer offset;
  public Integer limit;

  public Select() {
  }

  public Select(Registry registry) {
    this.registry = registry;
  }

  public Select(Registry registry, List<String> fields, List<Clause> clauses,
      List<String> orders, Integer offset, Integer limit) {
    this.registry = registry;
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

  public static Select fromString(String json) {
    return new Gson().fromJson(json, Select.class);
  }
}
