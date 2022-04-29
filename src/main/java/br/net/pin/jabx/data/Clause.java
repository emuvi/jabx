package br.net.pin.jabx.data;

import com.google.gson.Gson;

public class Clause implements Fixable {
  public Seems seem;
  public Likeds likes;
  public Valued valued;
  public Tying tied;

  public Clause() {
  }

  public Clause(Seems seem, Likeds likes, Valued valued, Tying tied) {
    this.seem = seem;
    this.likes = likes;
    this.valued = valued;
    this.tied = tied;
  }

  @Override
  public String toString() {
    return new Gson().toJson(this);
  }

  public static Clause fromString(String json) {
    return new Gson().fromJson(json, Clause.class);
  }
}
