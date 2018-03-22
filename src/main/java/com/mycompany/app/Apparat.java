package com.mycompany.app;

import java.sql.*;

public class Apparat extends ActiveDomainObject {

  private int apparatId;
  private String navn;
  private String beskrivelse;

  public Apparat(int apparatId) {
    this.apparatId = apparatId;
    this.navn = null;
    this.beskrivelse = null;
  }

  public Apparat(int apparatId, String navn, String beskrivelse) {
    this.apparatId = apparatId;
    this.navn = navn;
    this.beskrivelse = beskrivelse;
  }

  public Integer getApparatId() {
    return apparatId;
  }

  public String getNavn() {
    return navn;
  }

  public String getBeskrivelse() {
    return beskrivelse;
  }

  @Override
  public void initialize(Connection conn) {
    try {
      Statement stmt = conn.createStatement();
      ResultSet rs =
          stmt.executeQuery(
              "select apparatId, navn, beskrivelse from Apparat where apparatId=" + apparatId);
      while (rs.next()) {
        apparatId = rs.getInt("apparatId");
        navn = rs.getString("navn");
        beskrivelse = rs.getString("beskrivelse");
      }
    } catch (Exception e) {
      System.out.println("db error during select of Apparat= " + e);
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
      stmt.executeUpdate(
          "insert into Apparat values (" + apparatId + ", " + navn + "," + beskrivelse + ")");
    } catch (Exception e) {
      System.out.println("db error during insert of Apparat=" + e);
      return;
    }
  }
}
