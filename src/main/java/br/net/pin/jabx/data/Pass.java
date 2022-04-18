package br.net.pin.jabx.data;

import com.google.gson.Gson;

public class Pass {
  private byte[] data;

  public Pass() {
    this.data = null;
  }

  public Pass(byte[] data) {
    this.data = data;
  }

  public Pass(String data) {
    this.data = data.getBytes();
  }

  public String getPass() {
    return data != null ? new String(data) : null;
  }

  @Override
  public String toString() {
    return new Gson().toJson(this);
  }

  public static Pass fromString(String json) {
    return new Gson().fromJson(json, Pass.class);
  }
}
