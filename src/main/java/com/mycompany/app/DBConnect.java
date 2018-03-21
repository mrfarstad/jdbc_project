package com.mycompany.app;

import java.sql.*;
import java.util.Properties;

public class DBConnect {
  protected Connection conn;
  
  static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";
  static final String DB_URL = "jdbc:mysql://localhost:3306/gruppe133";
  static final String USER = "root";
  static final String PASS = "gruppe133";

  public void connect() {
    try {
      Class.forName(JDBC_DRIVER);
      Properties props = new Properties();
      props.put("db", "db");
      props.put("user", USER);
      props.put("password", PASS);
      props.put("database", PASS);
      conn =
          DriverManager.getConnection(
        		  DB_URL , props);
    } catch (Exception e) {
      throw new RuntimeException("Unable to connect to the MySQL server", e);
    }
  }
  
  public static void main(String[] args) {
	  
	DBConnect db = new DBConnect();
	db.connect();
	Apparat apparat = new Apparat(1, "", "");
	apparat.initialize(db.conn);
}
}
