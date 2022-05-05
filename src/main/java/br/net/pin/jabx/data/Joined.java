package br.net.pin.jabx.data;

import java.util.List;

import com.google.gson.Gson;

public class Joined {
  public Ties ties;
  public Registry registry;
  public List<Claused> clauses;

  public Joined() {
  }

  public Joined(Ties ties) {
    this.ties = ties;
  }

  public Joined(Ties ties, Registry registry) {
    this.ties = ties;
    this.registry = registry;
  }

  public Joined(Registry registry) {
    this.registry = registry;
  }

  public Joined(Registry registry, List<Claused> clauses) {
    this.registry = registry;
    this.clauses = clauses;
  }

  public Joined(Ties ties, Registry registry, List<Claused> clauses) {
    this.ties = ties;
    this.registry = registry;
    this.clauses = clauses;
  }

  public boolean hasClauses() {
    return this.clauses != null && !this.clauses.isEmpty();
  }

  @Override
  public String toString() {
    return new Gson().toJson(this);
  }

  public static Joined fromString(String json) {
    return new Gson().fromJson(json, Joined.class);
  }

  public static enum Ties {
    INNER, LEFT, RIGHT, FULL, CROSS
  }
}
