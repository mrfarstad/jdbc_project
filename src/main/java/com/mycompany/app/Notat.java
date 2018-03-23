package com.mycompany.app;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

public class Notat extends ActiveDomainObject {

  private Integer notatId;
  private String beskrivelse;
  private Integer oktId;
  
  public Notat(int notatId) {
	  this.notatId = notatId;
	  this.beskrivelse = null;
	  this.oktId = null;
  }

  public Notat(int notatId, String beskrivelse, int oktId) {
    this.notatId = notatId;
    this.beskrivelse = beskrivelse;
    this.oktId = oktId;
  }

  public Integer getNotatId() {
    return notatId;
  }

  public String getBeskrivelse() {
    return beskrivelse;
  }

  @Override
  public void initialize(Connection conn) {
    try {
      Statement stmt = conn.createStatement();
      ResultSet rs =
          stmt.executeQuery("select * from Notat where notatId=" + notatId);
      if (!rs.isBeforeFirst()) {
        notatId = null;
      }
      while (rs.next()) {
        notatId = rs.getInt("notatId");
        beskrivelse = rs.getString("beskrivelse");
        oktId = rs.getInt("oktId");
      }
    } catch (Exception e) {
      System.out.println("db error during select of Notat= " + e);
      return;
    }
  }

  @Override
  public void refresh(Connection conn) {
    initialize(conn);
  }

  @Override
  public void save(Connection conn) {
    try {
      Statement stmt = conn.createStatement();
      stmt.executeUpdate("insert into Notat values (" + notatId + ", \"" + beskrivelse + "\", " + oktId + ");");
    } catch (Exception e) {
      System.out.println("db error during insert of Notat=" + e);
      return;
    }
  }

  public void deleteRow(Connection conn, Integer apparatId) {
    try {
      Statement stmt = conn.createStatement();
      stmt.executeUpdate("delete from Notat where notatId = " + notatId);
    } catch (Exception e) {
      System.out.println("db error during deletion of Notat=" + e);
      return;
    }
  }
}
