package com.mycompany.app;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDateTime;
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
		  ovelseIds.stream().forEach(id -> {
			  Ovelse ovelse;
			  try {
				  ovelse = new OvelsePaApparat(id);
				  ovelse.initialize(conn);
			  } catch(Exception e) {
				  ovelse = new OvelseUtenApparat(id);
				  ovelse.initialize(conn);
			  }
			  if (ovelse.getOvelseId() != null) {
				  ovelser.add(ovelse);
			  }
		  });
		  return ovelser;
	  }
	  
//	  public void resultatloggFraTidsintervall(LocalDateTime startTid, LocalDateTime sluttid) {
//		    List<Treningsokt> logg = new ArrayList<>();
//		    try {
//		      Statement stmt = conn.createStatement();
//		      ResultSet rs =
//		          stmt.executeQuery(
//		              "select * from Ovelse inner join limit " + antallTreningsokter);
//		      while (rs.next()) {
//		        okter.add(
//		            new Treningsokt(
//		                rs.getInt("oktId"),
//		                rs.getString("dato"),
//		                rs.getString("tidspunkt").substring(0, 5),
//		                rs.getInt("varighet"),
//		                rs.getInt("form"),
//		                rs.getInt("prestasjon")));
//		      }
//		    } catch (Exception e) {
//		      System.out.println("db error during select of Treningsokt= " + e);
//		    }
//		    return okter;
//		  }

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