package br.net.pin.jabx.data;

import com.google.gson.Gson;

public class Claused implements Fixable {
  public Seems seems;
  public Likes likes;
  public Valued valued;
  public Ties ties;

  public Claused() {
  }

  public Claused(Seems seem, Likes likes, Valued valued, Ties ties) {
    this.seems = seem;
    this.likes = likes;
    this.valued = valued;
    this.ties = ties;
  }

  @Override
  public String toString() {
    return new Gson().toJson(this);
  }

  public static Claused fromString(String json) {
    return new Gson().fromJson(json, Claused.class);
  }

  public static enum Ties {
    AND, OR
  }
}
