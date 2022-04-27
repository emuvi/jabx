package br.net.pin.jabx.data;

import java.io.Closeable;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;

public class Helped implements Closeable {
  public Helper helper;
  public Connection link;

  public Helped() {
  }

  public Helped(Helper helper) {
    this.helper = helper;
  }

  public Helped(Connection link) {
    this.link = link;
  }

  public Helped(Helper helper, Connection link) {
    this.helper = helper;
    this.link = link;
  }

  @Override
  public void close() throws IOException {
    try {
      this.link.close();
    } catch (SQLException e) {
      throw new IOException(e);
    }
  }
}
