package br.net.pin.jabx.data;

import com.google.gson.Gson;

public class Valued {
  public String name;
  public Nature type;
  public Object data;

  public Valued() {
  }

  public Valued(String name, Nature type, Object data) {
    this.name = name;
    this.type = type;
    this.data = data;
  }

  @Override
  public String toString() {
    return new Gson().toJson(this);
  }

  public static Valued fromString(String json) {
    return new Gson().fromJson(json, Valued.class);
  }
}
