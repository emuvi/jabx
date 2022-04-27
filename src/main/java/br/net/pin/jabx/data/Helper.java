package br.net.pin.jabx.data;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import br.net.pin.jabx.mage.WizData;

public abstract class Helper {
  public List<Head> getHeads(Connection link) throws Exception {
    var meta = link.getMetaData();
    var set = meta.getTables(null, null, "%", new String[] { "TABLE" });
    var result = new ArrayList<Head>();
    while (set.next()) {
      result.add(new Head(set.getString(1), set.getString(2), set.getString(3)));
    }
    return result;
  }

  public void create(Connection connection, Table table) throws Exception {
    this.create(connection, table, false);
  }

  public void create(Connection connection, Table table, boolean ifNotExists)
      throws Exception {
    var builder = new StringBuilder();
    builder.append("CREATE TABLE ");
    if (ifNotExists) {
      builder.append("IF NOT EXISTS ");
    }
    builder.append(table.getCatalogSchemaName());
    builder.append(" (");
    for (var i = 0; i < table.fields.size(); i++) {
      if (i > 0) {
        builder.append(", ");
      }
      builder.append(this.formNature(table.fields.get(i)));
    }
    builder.append(")");
    connection.createStatement().execute(builder.toString());
  }

  public ResultSet select(Connection link, Select select) throws Exception {
    var builder = new StringBuilder("SELECT ");
    if (select.fields == null || !select.fields.isEmpty()) {
      builder.append("*");
    } else {
      for (var i = 0; i < select.fields.size(); i++) {
        if (i > 0) {
          builder.append(", ");
        }
        builder.append(select.fields.get(i));
      }
    }
    builder.append(" FROM ");
    builder.append(select.table.getSchemaName());
    builder.append(this.formClauses(select.clauses));
    var prepared = link.prepareStatement(builder.toString());
    var param_index = 1;
    if (select.clauses != null && !select.clauses.isEmpty()) {
      for (var clause : select.clauses) {
        if (clause.valued.data != null) {
          this.setParameter(prepared, param_index, clause.valued);
          param_index++;
        }
      }
    }
    return prepared.executeQuery();
  }

  public int insert(Connection link, Insert insert) throws Exception {
    var builder = new StringBuilder("INSERT INTO ");
    builder.append(insert.table.getCatalogSchemaName());
    builder.append(" (");
    for (var i = 0; i < insert.valueds.size(); i++) {
      if (i > 0) {
        builder.append(", ");
      }
      builder.append(insert.valueds.get(i).name);
    }
    builder.append(") VALUES (");
    for (var i = 0; i < insert.valueds.size(); i++) {
      if (i > 0) {
        builder.append(", ");
      }
      final var value = insert.valueds.get(i);
      if (value.data != null) {
        builder.append("?");
      } else {
        builder.append("NULL");
      }
    }
    builder.append(")");
    var prepared = link.prepareStatement(builder.toString());
    var param_index = 1;
    for (var valued : insert.valueds) {
      if (valued != null) {
        this.setParameter(prepared, param_index, valued);
        param_index++;
      }
    }
    return prepared.executeUpdate();
  }

  public int update(Connection link, Update update) throws Exception {
    var builder = new StringBuilder("UPDATE ");
    builder.append(update.table.getCatalogSchemaName());
    builder.append(" SET ");
    for (var i = 0; i < update.valueds.size(); i++) {
      if (i > 0) {
        builder.append(", ");
      }
      builder.append(update.valueds.get(i).name);
      builder.append(" = ");
      if (update.valueds.get(i).data == null) {
        builder.append("NULL");
      } else {
        builder.append("?");
      }
    }
    builder.append(this.formClauses(update.clauses));
    var prepared = link.prepareStatement(builder.toString());
    var param_index = 1;
    for (var valued : update.valueds) {
      if (valued != null) {
        this.setParameter(prepared, param_index, valued);
        param_index++;
      }
    }
    if (update.clauses != null && !update.clauses.isEmpty()) {
      for (var clause : update.clauses) {
        if (clause.valued != null) {
          this.setParameter(prepared, param_index, clause.valued);
          param_index++;
        }
      }
    }
    return prepared.executeUpdate();
  }

  public ResultSet delete(Connection link, Delete delete) throws Exception {
    var builder = new StringBuilder("DELETE FROM ");
    builder.append(delete.table.getSchemaName());
    builder.append(this.formClauses(delete.clauses));
    var prepared = link.prepareStatement(builder.toString());
    var param_index = 1;
    if (delete.clauses != null && !delete.clauses.isEmpty()) {
      for (var clause : delete.clauses) {
        if (clause.valued.data != null) {
          this.setParameter(prepared, param_index, clause.valued);
          param_index++;
        }
      }
    }
    return prepared.executeQuery();
  }

  public String formNature(Field field) {
    var builder = new StringBuilder(field.name);
    switch (field.nature) {
      case BOOL:
        builder.append(" BOOLEAN");
        break;
      case BIT:
        builder.append(" BIT");
        break;
      case TINY:
        builder.append(" TINYINT");
        break;
      case SMALL:
        builder.append(" SMALLINT");
        break;
      case INT:
      case SERIAL:
        builder.append(" INTEGER");
        break;
      case LONG:
      case BIG_SERIAL:
        builder.append(" BIGINT");
        break;
      case FLOAT:
        builder.append(" FLOAT");
        break;
      case REAL:
        builder.append(" REAL");
        break;
      case DOUBLE:
        builder.append(" DOUBLE");
        break;
      case NUMERIC:
      case BIG_NUMERIC:
        builder.append(" NUMERIC");
        if (field.size != null) {
          builder.append("(");
          builder.append(field.size);
          if (field.precision != null) {
            builder.append(",");
            builder.append(field.precision);
          }
          builder.append(")");
        }
        break;
      case CHAR:
        builder.append(" CHAR(1)");
        break;
      case CHARS:
      case PASS:
        builder.append(" VARCHAR");
        if (field.size != null) {
          builder.append("(");
          builder.append(field.size);
          builder.append(")");
        }
        break;
      case DATE:
        builder.append(" DATE");
        break;
      case TIME:
        builder.append(" TIME");
        break;
      case DATE_TIME:
      case TIMESTAMP:
        builder.append(" TIMESTAMP");
        break;
      case BYTES:
      case BLOB:
        builder.append(" BLOB");
        if (field.size != null) {
          builder.append("(");
          builder.append(field.size);
          builder.append(")");
        }
        break;
      case TEXT:
        builder.append(" TEXT");
        if (field.size != null) {
          builder.append("(");
          builder.append(field.size);
          builder.append(")");
        }
        break;
      default:
        throw new UnsupportedOperationException();
    }
    if (Objects.equals(field.notNull, true)) {
      builder.append(" NOT NULL");
    }
    return builder.toString();
  }

  public String formClauses(List<Clause> clauses) {
    if ((clauses == null) || clauses.isEmpty()) {
      return "";
    }
    var builder = new StringBuilder();
    builder.append(" WHERE ");
    var nextIsOr = false;
    for (var i = 0; i < clauses.size(); i++) {
      if (i > 0) {
        builder.append(nextIsOr ? " OR " : " AND ");
      }
      var clause = clauses.get(i);
      if (clause.same == Same.DIVERS) {
        builder.append(" NOT ");
      }
      builder.append(clause.valued.name);
      if (clause.valued.data == null) {
        builder.append(" IS NULL ");
      } else {
        builder.append(this.formCondition(clause.likes));
        builder.append(" ? ");
      }
      nextIsOr = clause.tie == Tie.OR;
    }
    return builder.toString();
  }

  public String formCondition(Condition condition) {
    switch (condition) {
      case EQUALS:
        return "=";
      case BIGGER:
        return ">";
      case LESSER:
        return "<";
      case BIGGER_EQUALS:
        return ">=";
      case LESSER_EQUALS:
        return "<=";
      case STARTS_WITH:
        return "STARTS WITH";
      case ENDS_WITH:
        return "ENDS WITH";
      case CONTAINS:
        return "CONTAINS";
      default:
        throw new UnsupportedOperationException();
    }
  }

  public void setParameter(PreparedStatement prepared, int index, Valued valued)
      throws Exception {
    if (valued.type == null) {
      prepared.setObject(index, valued.data);
    } else {
      switch (valued.type) {
        case BOOL:
          prepared.setBoolean(index, WizData.getBoolean(valued.data));
          break;
        case BIT:
        case BYTE:
          prepared.setByte(index, WizData.getByte(valued.data));
          break;
        case TINY:
        case SMALL:
          prepared.setShort(index, WizData.getShort(valued.data));
          break;
        case INT:
        case SERIAL:
          prepared.setInt(index, WizData.getInt(valued.data));
          break;
        case LONG:
        case BIG_SERIAL:
          prepared.setLong(index, WizData.getLong(valued.data));
          break;
        case FLOAT:
        case REAL:
          prepared.setFloat(index, WizData.getFloat(valued.data));
          break;
        case DOUBLE:
        case NUMERIC:
          prepared.setDouble(index, WizData.getDouble(valued.data));
          break;
        case BIG_NUMERIC:
          prepared.setBigDecimal(index, WizData.getBigDecimal(valued.data));
          break;
        case CHAR:
        case CHARS:
        case PASS:
          prepared.setString(index, WizData.getString(valued.data));
          break;
        case DATE:
          prepared.setDate(index, WizData.getDate(valued.data));
          break;
        case TIME:
          prepared.setTime(index, WizData.getTime(valued.data));
          break;
        case DATE_TIME:
        case TIMESTAMP:
          prepared.setTimestamp(index, WizData.getTimestamp(valued.data));
          break;
        case BYTES:
          prepared.setBytes(index, WizData.getBytes(valued.data));
          break;
        case BLOB:
        case TEXT:
          prepared.setBlob(index, WizData.getBlob(valued.data));
          break;
        default:
          throw new UnsupportedOperationException();
      }
    }
  }

  public boolean isPrimaryKey(Exception error) {
    return error.getMessage().contains("unique constraint");
  }

  public static Helper instance = new Helper() {
  };
}
