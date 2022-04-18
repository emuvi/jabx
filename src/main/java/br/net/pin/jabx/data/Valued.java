package br.net.pin.jabx.data;

import com.google.gson.Gson;

public class Valued {
  public String name;
  public Object data; 
  public Nature type;

  @Override
  public String toString() {
    return new Gson().toJson(this);
  }

  public static Valued fromString(String json) {
    return new Gson().fromJson(json, Valued.class);
  }
}
