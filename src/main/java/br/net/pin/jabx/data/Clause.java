package br.net.pin.jabx.data;

import com.google.gson.Gson;

public class Clause implements Fixable {
  public Seems seems;
  public Likes likes;
  public Valued valued;
  public Tying tying;

  public Clause() {
  }

  public Clause(Seems seem, Likes likes, Valued valued, Tying tying) {
    this.seems = seem;
    this.likes = likes;
    this.valued = valued;
    this.tying = tying;
  }

  @Override
  public String toString() {
    return new Gson().toJson(this);
  }

  public static Clause fromString(String json) {
    return new Gson().fromJson(json, Clause.class);
  }
}
