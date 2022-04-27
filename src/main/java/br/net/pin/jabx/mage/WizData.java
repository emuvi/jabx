package br.net.pin.jabx.mage;

import java.math.BigDecimal;
import java.sql.Blob;
import java.sql.Date;
import java.sql.Time;
import java.sql.Timestamp;
import br.net.pin.jabx.data.Nature;

public class WizData {
  public static Nature getNatureOfSQL(int jdbcType) {
    switch (jdbcType) {
      case 16:
        return Nature.BOOL;
      case -7:
      case -6:
      case 5:
      case 4:
        return Nature.INT;
      case -5:
        return Nature.LONG;
      case 6:
      case 7:
        return Nature.FLOAT;
      case 8:
      case 2:
      case 3:
        return Nature.DOUBLE;
      case 1:
      case -15:
        return Nature.CHAR;
      case 12:
      case -1:
      case -9:
      case -16:
        return Nature.CHARS;
      case 91:
        return Nature.DATE;
      case 92:
      case 2013:
        return Nature.TIME;
      case 93:
      case 2014:
        return Nature.TIMESTAMP;
      case -2:
      case -3:
      case -4:
      case 2004:
      case 2005:
      case 2011:
      case 2009:
        return Nature.BYTES;
      default:
        throw new UnsupportedOperationException(
            "Could not identify the data nature of jdbc type: " + jdbcType);
    }
  }

  public static Boolean getBoolean(Object data) {
    throw new UnsupportedOperationException("Not implemented yet");
  }

  public static Byte getByte(Object data) {
    throw new UnsupportedOperationException("Not implemented yet");
  }

  public static Short getShort(Object data) {
    throw new UnsupportedOperationException("Not implemented yet");
  }

  public static Integer getInt(Object data) {
    throw new UnsupportedOperationException("Not implemented yet");
  }

  public static Long getLong(Object data) {
    throw new UnsupportedOperationException("Not implemented yet");
  }

  public static Float getFloat(Object data) {
    throw new UnsupportedOperationException("Not implemented yet");
  }

  public static Double getDouble(Object data) {
    throw new UnsupportedOperationException("Not implemented yet");
  }

  public static BigDecimal getBigDecimal(Object data) {
    throw new UnsupportedOperationException("Not implemented yet");
  }

  public static String getString(Object data) {
    throw new UnsupportedOperationException("Not implemented yet");
  }

  public static Date getDate(Object data) {
    throw new UnsupportedOperationException("Not implemented yet");
  }

  public static Time getTime(Object data) {
    throw new UnsupportedOperationException("Not implemented yet");
  }

  public static Timestamp getTimestamp(Object data) {
    throw new UnsupportedOperationException("Not implemented yet");
  }

  public static byte[] getBytes(Object data) {
    throw new UnsupportedOperationException("Not implemented yet");
  }

  public static Blob getBlob(Object data) {
    throw new UnsupportedOperationException("Not implemented yet");
  }
}
