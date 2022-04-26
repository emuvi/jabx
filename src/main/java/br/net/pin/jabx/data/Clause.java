package br.net.pin.jabx.data;

import com.google.gson.Gson;

public class Clause {
  public Same same;
  public Condition likes;
  public Valued valued;
  public Tie tie;
  
  @Override
  public String toString() {
    return new Gson().toJson(this);
  }

  public static Clause fromString(String json) {
    return new Gson().fromJson(json, Clause.class);
  }
}
