package com.mycompany.app;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class ResultatloggCtrl extends DBConnect {

  public ResultatloggCtrl() {
    connect();
    // La laging av avtale vaere en transaksjon
    try {
      conn.setAutoCommit(false);
    } catch (SQLException e) {
      System.out.println("db error during setAuoCommit of RegistreringCtrl=" + e);
      return;
    }
  }

  public Collection<Ovelse> getOvelserFromDb(Collection<Integer> ovelseIds) {
    Collection<Ovelse> ovelser = new ArrayList<>();
    ovelseIds
        .stream()
        .forEach(
            id -> {
              Ovelse ovelse;
              try {
                ovelse = new OvelsePaApparat(id);
                ovelse.initialize(conn);
              } catch (Exception e) {
                ovelse = new OvelseUtenApparat(id);
                ovelse.initialize(conn);
              }
              if (ovelse.getOvelseId() != null) {
                ovelser.add(ovelse);
              }
            });
    return ovelser;
  }

  public void resultatloggFraTidsintervall(LocalDate starttid, LocalDate sluttid) {
    try {
      Statement stmt = conn.createStatement();
      ResultSet rs =
          stmt.executeQuery(
              "select * from Treningsokt natural join OvelserUtfortPaTreningsokt natural join Ovelse where dato between "
                  + starttid
                  + " and "
                  + sluttid);
      while (rs.next()) {
        System.out.println(rs.getInt("oktId"));
        System.out.println(rs.getInt("oktId"));
        System.out.println(rs.getInt("dato"));
        System.out.println(rs.getInt("tidspunkt"));
        System.out.println(rs.getInt("varighet"));
        System.out.println(rs.getInt("form"));
        System.out.println(rs.getInt("prestasjon"));
        System.out.println(rs.getInt("navn"));
      }
    } catch (Exception e) {
      System.out.println("db error during select of Treningsokt= " + e);
    }
  }

  public void fullforResultatlogg() {
    // Skriv inn alt som skal lagres
    try {
      conn.commit();
    } catch (SQLException e) {
      System.out.println("db error during commit of RegistreringCtrl=" + e);
      return;
    }
  }
}
