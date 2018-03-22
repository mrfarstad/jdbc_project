package com.mycompany.app;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

public class OvelsePaApparat extends Ovelse {

  private int antallSett;
  private int antallKilo;
  private Apparat apparat;

  public OvelsePaApparat(int ovelseId, String navn, int antallKilo, int antallSett) {
    this.ovelseId = ovelseId;
    this.navn = navn;
    this.antallKilo = antallKilo;
    this.antallSett = antallSett;
  }

  @Override
  public void initialize(Connection conn) {
    try {
      Statement stmt = conn.createStatement();
      ResultSet rs =
          stmt.executeQuery(
              "select ovelseId, navn, antallSett, antallKilo, apparatId from Ovelse inner join OvelsePaApparat where ovelseId="
                  + ovelseId);
      while (rs.next()) {
        ovelseId = rs.getInt("ovelseId");
        navn = rs.getString("navn");
        antallSett = rs.getInt("antallSett");
        antallKilo = rs.getInt("antallKilo");
        apparat = new Apparat(rs.getInt("apparatId"));
        apparat.initialize(conn);
      }
    } catch (Exception e) {
      System.out.println("db error during select of OvelsePaApparat= " + e);
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
      stmt.executeUpdate("insert into Ovelse values(" + ovelseId + ", \"" + navn + "\");");
      stmt.executeUpdate(
          "insert into OvelsePaApparat values("
              + ovelseId
              + ", "
              + antallKilo
              + ", "
              + antallSett
              + ", "
              + (apparat != null ? apparat.getApparatId() : "NULL")
              + ");");
    } catch (Exception e) {
      System.out.println("db error during insert of OvelsePaApparat=" + e);
      return;
    }
  }
}
