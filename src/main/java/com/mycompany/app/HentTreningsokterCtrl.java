package com.mycompany.app;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class HentTreningsokterCtrl extends DBConnect {

  public HentTreningsokterCtrl() {
    connect();
    // La laging av avtale vaere en transaksjon
    try {
      conn.setAutoCommit(false);
    } catch (SQLException e) {
      System.out.println("db error during setAuoCommit of RegistreringCtrl=" + e);
      return;
    }
  }
  
  public List<Treningsokt> senesteOkter(int antallTreningsokter) {
	    List<Treningsokt> okter = new ArrayList<>();
	    try {
	      Statement stmt = conn.createStatement();
	      ResultSet rs =
	          stmt.executeQuery(
	              "select * from Treningsokt"
	                  + " limit "
	                  + antallTreningsokter
	                  + ");");
	      while (rs.next()) {
	        okter.add(
	            new Treningsokt(
	                rs.getInt("oktId"),
	                rs.getString("dato"),
	                rs.getString("tidspunkt"),
	                rs.getInt("varighet"),
	                rs.getInt("form"),
	                rs.getInt("prestasjon")));
	      }
	    } catch (Exception e) {
	      System.out.println("db error during select of Treningsokt= " + e);
	    }
	    return okter;
	  }

  public void fullforHentTreningsokter() {
    // Skriv inn alt som skal lagres
    try {
      conn.commit();
    } catch (SQLException e) {
      System.out.println("db error during commit of RegistreringCtrl=" + e);
      return;
    }
  }
}
