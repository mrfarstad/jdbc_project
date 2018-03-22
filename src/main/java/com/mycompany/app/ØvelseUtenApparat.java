package com.mycompany.app;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

public class ØvelseUtenApparat extends Øvelse {

  private String beskrivelse;

  public ØvelseUtenApparat(int øvelseId, String navn, String beskrivelse) {
    this.øvelseId = øvelseId;
    this.navn = navn;
    this.beskrivelse = beskrivelse;
  }

  @Override
  public void initialize(Connection conn) {
    try {
      Statement stmt = conn.createStatement();
      ResultSet rs =
          stmt.executeQuery(
              "select øvelseId, navn, beskrivelse from Øvelse inner join ØvelseUtenApparat where øvelseId="
                  + øvelseId);
      while (rs.next()) {
        øvelseId = rs.getInt("øvelseId");
        navn = rs.getString("navn");
        beskrivelse = rs.getString("beskrivelse");
      }
    } catch (Exception e) {
      System.out.println("db error during select of ØvelseUtenApparat= " + e);
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
      stmt.executeUpdate("insert into Øvelse values (" + øvelseId + ", " + navn + ")");
      stmt.executeUpdate("insert into ØvelseUtenApparat values (" + beskrivelse + ")");
    } catch (Exception e) {
      System.out.println("db error during insert of ØvelseUtenApparat=" + e);
      return;
    }
  }
}
