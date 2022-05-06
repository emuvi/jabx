package br.net.pin.jabx.data;

import com.google.gson.Gson;

public class Filter implements Fixable {
  public Filter.Seems seems;
  public Filter.Likes likes;
  public Valued valued;
  public Filter.Ties ties;

  public Filter() {
  }

  public Filter(Seems seem, Likes likes, Valued valued, Ties ties) {
    this.seems = seem;
    this.likes = likes;
    this.valued = valued;
    this.ties = ties;
  }

  @Override
  public String toString() {
    return new Gson().toJson(this);
  }

  public static Filter fromString(String json) {
    return new Gson().fromJson(json, Filter.class);
  }

  public static enum Seems {
    SAME, DIVERSE
  }

  public static enum Likes {
    EQUALS,

    BIGGER, LESSER,

    BIGGER_EQUALS, LESSER_EQUALS,

    STARTS_WITH, ENDS_WITH,

    CONTAINS,
  }

  public static enum Ties {
    AND, OR
  }
}
