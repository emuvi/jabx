package br.net.pin.jabx.data;

import br.net.pin.jabx.mage.WizBytes;
import br.net.pin.jabx.mage.WizDate;

public class TableField {
  public String name;
  public Nature nature;
  public Integer size;
  public Integer precision;
  public Boolean notNull;
  public Boolean key;

  public Object getValueFrom(String formatted) throws Exception {
    if (formatted == null || formatted.isEmpty()) {
      return null;
    }
    switch (nature) {
      case BOOL:
      case BIT:
        return Boolean.parseBoolean(formatted);
      case BYTE:
        return Byte.parseByte(formatted);
      case INT:
      case SERIAL:
        return Integer.parseInt(formatted);
      case LONG:
      case SERIAL_LONG:
        return Long.parseLong(formatted);
      case FLOAT:
      case REAL:
        return Float.parseFloat(formatted);
      case DOUBLE:
      case NUMERIC:
        return Double.parseDouble(formatted);
      case CHAR:
        return formatted.charAt(0);
      case CHARS:
      case TEXT:
        return formatted;
      case DATE:
        return WizDate.parseDate(formatted);
      case TIME:
        return WizDate.parseTime(formatted);
      case TIMESTAMP:
        return WizDate.parseTimestamp(formatted);
      case BYTES:
        return WizBytes.decodeFromBase64(formatted);
      default:
        throw new Exception("DataType Not Supported.");
    }
  }

  public String formatValue(Object value) throws Exception {
    if (value == null) {
      return "";
    }
    switch (nature) {
      case BOOL:
      case BIT:
      case BYTE:
      case TINY:
      case SMALL:
      case INT:
      case LONG:
      case FLOAT:
      case REAL:
      case DOUBLE:
      case NUMERIC:
      case CHAR:
      case CHARS:
      case TEXT:
        return String.valueOf(value);
      case DATE:
        return WizDate.formatDate(WizDate.get(value));
      case TIME:
        return WizDate.formatTime(WizDate.get(value));
      case TIMESTAMP:
        return WizDate.formatTimestamp(WizDate.get(value));
      case BYTES:
        return WizBytes.encodeToBase64(WizBytes.get(value));
      default:
        throw new Exception("DataType Not Supported.");
    }
  }

  public String getSQLDescription() {
    var builder = new StringBuilder(name);
    switch (nature) {
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
      case SERIAL_LONG:
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
        builder.append(" NUMERIC");
        if (size != null) {
          builder.append("(");
          builder.append(size);
          if (precision != null) {
            builder.append(",");
            builder.append(precision);
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
        if (size != null) {
          builder.append("(");
          builder.append(size);
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
      case TEXT:
        builder.append(" TEXT");
        if (size != null) {
          builder.append("(");
          builder.append(size);
          builder.append(")");
        }
        break;
      case BYTES:
        builder.append(" BLOB");
        if (size != null) {
          builder.append("(");
          builder.append(size);
          builder.append(")");
        }
        break;
      default:
        throw new UnsupportedOperationException();
    }
    if (notNull) {
      builder.append(" NOT NULL");
    }
    return builder.toString();
  }
}
