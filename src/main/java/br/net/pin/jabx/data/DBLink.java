package br.net.pin.jabx.data;

import java.sql.Connection;
import java.sql.DriverManager;
import com.google.gson.Gson;

public class DBLink {
  public String name;
  public DataBase base;
  public String path;
  public Integer port;
  public String data;
  public String user;
  public Pass pass;

  public DBLink() {}

  public DBLink(String name) {
    this.name = name;
  }

  public DBLink(String name, DataBase base) {
    this.name = name;
    this.base = base;
  }

  public DBLink(String name, DataBase base, String path) {
    this.name = name;
    this.base = base;
    this.path = path;
  }

  public DBLink(String name, DataBase base, String path, String data) {
    this.name = name;
    this.base = base;
    this.path = path;
    this.data = data;
  }

  public DBLink(String name, DataBase base, String path, Integer port, String data) {
    this.name = name;
    this.base = base;
    this.path = path;
    this.port = port;
    this.data = data;
  }

  public DBLink(String name, DataBase base, String path, String data, String user,
      Pass pass) {
    this.name = name;
    this.base = base;
    this.path = path;
    this.data = data;
    this.user = user;
    this.pass = pass;
  }

  public DBLink(DataBase base) {
    this.base = base;
  }

  public DBLink(DataBase base, String path) {
    this.base = base;
    this.path = path;
  }

  public DBLink(DataBase base, String path, String data) {
    this.base = base;
    this.path = path;
    this.data = data;
  }

  public DBLink(DataBase base, String path, Integer port, String data) {
    this.base = base;
    this.path = path;
    this.port = port;
    this.data = data;
  }

  public DBLink(DataBase base, String path, String data, String user, Pass pass) {
    this.base = base;
    this.path = path;
    this.data = data;
    this.user = user;
    this.pass = pass;
  }

  public DBLink(String name, DataBase base, String path, Integer port, String data,
      String user, Pass pass) {
    this.name = name;
    this.base = base;
    this.path = path;
    this.port = port;
    this.data = data;
    this.user = user;
    this.pass = pass;
  }

  public String getFormed() {
    var result = this.base.formation;
    if (result.contains("$path") && this.path != null) {
      result = result.replace("$path", this.path);
    }
    if (result.contains("$port")) {
      if (this.port != null) {
        result = result.replace("$port", this.port.toString());
      } else if (this.base != null) {
        result = result.replace("$port", this.base.defaultPort.toString());
      }
    }
    if (result.contains("$data") && this.data != null) {
      result = result.replace("$data", this.data);
    }
    return result;
  }

  public Connection connect() throws Exception {
    Class.forName(this.base.clazz);
    if ((this.user != null && !this.user.isEmpty() && this.pass != null)) {
      return DriverManager.getConnection(this.getFormed(), this.user, this.pass
          .getPass());
    }
    return DriverManager.getConnection(this.getFormed());
  }

  private transient Connection linked = null;

  public Connection link() throws Exception {
    if (this.linked == null) {
      this.linked = this.connect();
    }
    if (this.linked.isClosed()) {
      this.linked = this.connect();
    }
    return this.linked;
  }

  @Override
  public String toString() {
    return new Gson().toJson(this);
  }

  public static DBLink fromString(String json) {
    return new Gson().fromJson(json, DBLink.class);
  }
}
