package com.mycompany.app;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

public class OvelsesGruppe extends ActiveDomainObject {
	
	private int gruppeId;
	private MuskelGruppe muskelGruppe;

  @Override
  public void initialize(Connection conn) {
    try {
      Statement stmt = conn.createStatement();
      ResultSet rs =
          stmt.executeQuery("select gruppeId, navn from OvelsesGruppe where gruppeId=" + gruppeId);
      while (rs.next()) {
        gruppeId = rs.getInt("gruppeId");
        muskelGruppe = stringToMuskelGruppe(rs.getString("muskelGruppe"));
      }
    } catch (Exception e) {
      System.out.println("db error during select of OvelsesGruppe= " + e);
      return;
    }
  }

  public MuskelGruppe stringToMuskelGruppe(String str) {
	  switch (str) {
	  	case "bein":
	  		return MuskelGruppe.BEIN;
	  	case "biceps":
	  		return MuskelGruppe.BICEPS;
	  	case "triceps":
	  		return MuskelGruppe.TRICEPS;
	  	case "mage":
	  		return MuskelGruppe.MAGE;
	  	case "rygg":
	  		return MuskelGruppe.RYGG;
	  	case "bryst":
	  		return MuskelGruppe.BRYST;
	  	case "skuldre":
	  		return MuskelGruppe.SKULDRE;
	  	default:
	  		return null;
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
	            "insert into Ovelse values (" + gruppeId + ", " + muskelGruppe + ")");
	      } catch (Exception e) {
	        System.out.println("db error during insert of MuskelGruppe=" + e);
	        return;
	      }
	    }
}
