package br.net.pin.jabx.data;

public enum Deed {
  INSERT(true),
  SELECT(false),
  UPDATE(true),
  DELETE(true);

  public final boolean mutates;

  private Deed(boolean mutates) {
    this.mutates = mutates;
  }
}