package br.net.pin.jabx.data;

public enum Data {

  HSQLMemory("org.hsqldb.jdbcDriver", "jdbc:hsqldb:mem:$path", 9000,
      new HelperHSQL()),

  HSQLLocal("org.hsqldb.jdbcDriver", "jdbc:hsqldb:file:$path;hsqldb.lock_file=true", 9000,
      new HelperHSQL()),

  HSQLClient("org.hsqldb.jdbcDriver", "jdbc:hsqldb:hsql://$path:$port/$data", 9000,
      new HelperHSQL()),

  DerbyInner("org.apache.derby.jdbc.EmbeddedDriver", "jdbc:derby:$path;create=true", 1527,
      new HelperDerby()),

  DerbyClient("org.apache.derby.jdbc.ClientDriver",
      "jdbc:derby://$path:$port/$data;create=true", 1527, new HelperDerby()),

  FirebirdInner("org.firebirdsql.jdbc.FBDriver", "jdbc:firebirdsql:embedded:$path", 3050,
      new HelperFirebird()),

  FirebirdLocal("org.firebirdsql.jdbc.FBDriver", "jdbc:firebirdsql:local:$path", 3050,
      new HelperFirebird()),

  FirebirdClient("org.firebirdsql.jdbc.FBDriver", "jdbc:firebirdsql:$path/$port:$data",
      3050, new HelperFirebird()),

  MySQL("com.mysql.jdbc.Driver", "jdbc:mysql://$path:$port/$data", 3306,
      new HelperMySQL()),

  Postgres("org.postgresql.Driver", "jdbc:postgresql://$path:$port/$data", 5432,
      new HelperPostgres());

  private final String clazz;
  private final String formation;
  private final Integer defaultPort;
  private final Helper helper;

  private Data(String clazz, String formation, Integer defaultPort, Helper auxiliar) {
    this.clazz = clazz;
    this.formation = formation;
    this.defaultPort = defaultPort;
    this.helper = auxiliar;
  }

  public String getClazz() {
    return this.clazz;
  }

  public String getFormation() {
    return this.formation;
  }

  public Integer getDefaultPort() {
    return defaultPort;
  }

  public Helper getHelper() {
    return this.helper;
  }

  public String getURLIdenty() {
    var dollarAt = this.formation.indexOf("$");
    if (dollarAt == -1) {
      return this.formation;
    } else {
      return this.formation.substring(0, dollarAt);
    }
  }

  public static Data fromURL(String jdbc) {
    for (Data data : Data.values()) {
      if (jdbc.startsWith(data.getURLIdenty())) {
        return data;
      }
    }
    return null;
  }
}
