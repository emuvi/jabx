package br.net.pin.jabx.data;

import com.google.gson.Gson;

public class Clause {
  public Boolean not;
  public Condition like;
  public Valued value;
  public Tie tie;
  
  @Override
  public String toString() {
    return new Gson().toJson(this);
  }

  public static Clause fromString(String json) {
    return new Gson().fromJson(json, Clause.class);
  }
}
