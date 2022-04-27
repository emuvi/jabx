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

  public TableField() {
  }

  public TableField(String name, Nature nature, Integer size, Integer precision, Boolean notNull, Boolean key) {
    this.name = name;
    this.nature = nature;
    this.size = size;
    this.precision = precision;
    this.notNull = notNull;
    this.key = key;
  }

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
      case BIG_SERIAL:
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

  
}
