package br.net.pin.jabx.data;

import java.sql.Connection;

public class HelperMySQL extends Helper {

  @Override
  public boolean isErrorPrimaryKey(Exception error) {
    throw new UnsupportedOperationException();
  }

  @Override
  public void createTable(Connection connection, Table table, boolean ifNotExists) throws Exception {
    throw new UnsupportedOperationException();
  }

}
