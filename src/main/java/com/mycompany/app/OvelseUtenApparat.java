package com.mycompany.app;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

public class OvelseUtenApparat extends Ovelse {

  private String beskrivelse;
  
  public OvelseUtenApparat(int ovelseId) {
	  this.ovelseId = ovelseId;
	  this.beskrivelse = null;
  }

  public OvelseUtenApparat(int ovelseId, String navn, String beskrivelse) {
    this.ovelseId = ovelseId;
    this.navn = navn;
    this.beskrivelse = beskrivelse;
  }

  @Override
  public void initialize(Connection conn) {
    try {
      Statement stmt = conn.createStatement();
      ResultSet rs =
          stmt.executeQuery(
              "select * from Ovelse inner join OvelseUtenApparat where Ovelse.ovelseId="
                  + ovelseId);
      if (!rs.isBeforeFirst()) {
        ovelseId = null;
      }
      while (rs.next()) {
        ovelseId = rs.getInt("ovelseId");
        navn = rs.getString("navn");
        beskrivelse = rs.getString("beskrivelse");
      }
    } catch (Exception e) {
      System.out.println("db error during select of OvelseUtenApparat= " + e);
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
      stmt.executeUpdate("insert into Ovelse values (" + ovelseId + ", \"" + navn + "\")");
      stmt.executeUpdate("insert into OvelseUtenApparat values (" + ovelseId + ", \"" + beskrivelse + "\")");
    } catch (Exception e) {
      System.out.println("db error during insert of OvelseUtenApparat=" + e);
      return;
    }
  }

  public void deleteRow(Connection conn, Integer id) {
    try {
      Statement stmt = conn.createStatement();
      stmt.executeUpdate("delete from Ovelse where ovelseId = " + id);
    } catch (Exception e) {
      System.out.println("db error during deletion of OvelseUtenApparat=" + e);
      return;
    }
  }
}
