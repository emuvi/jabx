package br.net.pin.jabx.data;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public abstract class Helper {

  public abstract boolean isErrorPrimaryKey(Exception error);

  public abstract void createTable(Connection connection, Table table,
      boolean ifNotExists) throws Exception;

  public List<TableHead> getHeads(Connection connection) throws Exception {
    DatabaseMetaData meta = connection.getMetaData();
    ResultSet set = meta.getTables(null, null, "%", new String[] {"TABLE"});
    List<TableHead> result = new ArrayList<>();
    while (set.next()) {
      result.add(new TableHead(set.getString(1), set.getString(2), set.getString(3)));
    }
    return result;
  }

  public ResultSet selectAll(Table fromTable, Connection onLink) throws Exception {
    StringBuilder builder = new StringBuilder("SELECT ");
    boolean first = true;
    for (TableField field : fromTable.fields) {
      if (first) {
        first = false;
      } else {
        builder.append(", ");
      }
      builder.append(field.name);
    }
    builder.append(" FROM ");
    builder.append(fromTable.getSchemaName());
    return onLink.createStatement().executeQuery(builder.toString());
  }

  public void insert(Table inTable, Connection onLink, Object... theValues)
      throws Exception {
    StringBuilder builder = new StringBuilder("INSERT INTO ");
    builder.append(inTable.getSchemaName());
    builder.append(" (");
    for (int i = 0; i < theValues.length; i++) {
      if (i > 0) {
        builder.append(", ");
      }
      builder.append(inTable.fields.get(i).name);
    }
    builder.append(") VALUES (");
    for (int i = 0; i < theValues.length; i++) {
      if (i > 0) {
        builder.append(", ");
      }
      if (theValues[i] != null) {
        builder.append("?");
      } else {
        builder.append("NULL");
      }
    }
    builder.append(")");
    var prepared = onLink.prepareStatement(builder.toString());
    var param_index = 1;
    for (int i = 0; i < theValues.length; i++) {
      if (theValues[i] != null) {
        prepared.setObject(param_index, theValues[i]);
        param_index++;
      }
    }
    prepared.executeUpdate();
  }
}
