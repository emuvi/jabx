package br.net.pin.jabx.data;

import java.sql.Connection;
import java.sql.DriverManager;
import com.google.gson.Gson;

public class Linker {
  public String name;
  public Data base;
  public String path;
  public Integer port;
  public String data;
  public String user;
  public Pass pass;

  public String getFormed() {
    String result = base.getFormation();
    if (result.contains("$path") && path != null) {
      result = result.replace("$path", path);
    }
    if (result.contains("$port")) {
      if (port != null) {
        result = result.replace("$port", port.toString());
      } else if (base != null) {
        result = result.replace("$port", base.getDefaultPort().toString());
      }
    }
    if (result.contains("$data") && data != null) {
      result = result.replace("$data", data);
    }
    return result;
  }

  public Connection connect() throws Exception {
    Class.forName(base.getClazz());
    if ((user != null && !user.isEmpty() && pass != null)) {
      return DriverManager.getConnection(getFormed(), user, pass.getPass());
    } else {
      return DriverManager.getConnection(getFormed());
    }
  }

  @Override
  public String toString() {
    return new Gson().toJson(this);
  }

  public static Linker fromString(String json) {
    return new Gson().fromJson(json, Linker.class);
  }
}
