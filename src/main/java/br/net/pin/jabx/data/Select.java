package br.net.pin.jabx.data;

import java.util.List;

import com.google.gson.Gson;

public class Select implements Fixable {
  public Registry registry;
  public List<Typed> fields;
  public List<Joined> joins;
  public List<Claused> clauses;
  public List<Ordered> orders;
  public Integer offset;
  public Integer limit;

  public Select() {
  }

  public Select(Registry registry) {
    this.registry = registry;
  }

  public Select(Registry registry, List<Typed> fields) {
    this.registry = registry;
    this.fields = fields;
  }

  public Select(Registry registry, List<Typed> fields, List<Joined> joins) {
    this.registry = registry;
    this.fields = fields;
    this.joins = joins;
  }

  public Select(Registry registry, List<Typed> fields, List<Joined> joins, List<Claused> clauses) {
    this.registry = registry;
    this.fields = fields;
    this.joins = joins;
    this.clauses = clauses;
  }

  public Select(Registry registry, List<Typed> fields, List<Joined> joins, List<Claused> clauses,
      List<Ordered> orders) {
    this.registry = registry;
    this.fields = fields;
    this.joins = joins;
    this.clauses = clauses;
    this.orders = orders;
  }

  public Select(Registry registry, List<Typed> fields, List<Joined> joins, List<Claused> clauses, List<Ordered> orders,
      Integer offset) {
    this.registry = registry;
    this.fields = fields;
    this.joins = joins;
    this.clauses = clauses;
    this.orders = orders;
    this.offset = offset;
  }

  public Select(Registry registry, List<Typed> fields, List<Joined> joins, List<Claused> clauses, List<Ordered> orders,
      Integer offset, Integer limit) {
    this.registry = registry;
    this.fields = fields;
    this.joins = joins;
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
