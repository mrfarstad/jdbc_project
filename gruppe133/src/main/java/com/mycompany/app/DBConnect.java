package com.mycompany.app;

import java.sql.*;
import java.util.Properties;

public abstract class DBConnect {
  protected Connection conn;

  public DBConnect() {}

  public void connect() {
    try {
      Class.forName("com.mysql.jdbc.Driver").newInstance();
      // Properties for user and password. Here the user and password are both 'paulr'
      Properties p = new Properties();
      p.put("user", "gruppe133");
      p.put("password", "gruppe133");
      //            conn = DriverManager.getConnection("jdbc:mysql://mysql.ansatt.ntnu.no/sveinbra_ektdb?autoReconnect=true&useSSL=false",p);
      conn =
          DriverManager.getConnection(
              "jdbc:mysql://127.0.0.1:8081/ekt?autoReconnect=true&useSSL=false", p);
    } catch (Exception e) {
      throw new RuntimeException("Unable to connect", e);
    }
  }
}
