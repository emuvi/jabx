package br.net.pin.jabx.data;

import com.google.gson.Gson;

public class Typed implements Fixable {
  public String name;
  public Nature type;

  public Typed() {
  }

  public Typed(String name) {
    this.name = name;
  }

  public Typed(String name, Nature type) {
    this.name = name;
    this.type = type;
  }

  @Override
  public String toString() {
    return new Gson().toJson(this);
  }

  public static Valued fromString(String json) {
    return new Gson().fromJson(json, Valued.class);
  }
}
