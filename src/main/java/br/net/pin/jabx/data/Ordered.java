package br.net.pin.jabx.data;

import com.google.gson.Gson;

public class Ordered {
  public String name;
  public Boolean desc;

  public Ordered() {
  }

  public Ordered(String name) {
    this.name = name;
  }

  public Ordered(String name, Boolean desc) {
    this.name = name;
    this.desc = desc;
  }

  @Override
  public String toString() {
    return new Gson().toJson(this);
  }

  public static Ordered fromString(String json) {
    return new Gson().fromJson(json, Ordered.class);
  }
}
