package com.mycompany.app;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

public class ØvelsePåApparat extends Øvelse {
	
	private int antallSett;
	private int antallKilo;
	private Apparat apparat;

	public ØvelsePåApparat(int øvelseId, String navn, int antallKilo, int antallSett) {
		this.øvelseId = øvelseId;
		this.navn = navn;
		this.antallKilo = antallKilo;
		this.antallSett = antallSett;
	}
	
	  @Override
	  public void initialize(Connection conn) {
	    try {
	      Statement stmt = conn.createStatement();
	      ResultSet rs =
	          stmt.executeQuery("select øvelseId, navn, antallSett, antallKilo, apparatId from Øvelse inner join ØvelsePåApparat where øvelseId=" + øvelseId);
	      while (rs.next()) {
	        øvelseId = rs.getInt("øvelseId");
	        navn = rs.getString("navn");
	        antallSett = rs.getInt("antallSett");
	        antallKilo = rs.getInt("antallKilo");
	        apparat = new Apparat(rs.getInt("apparatId"));
	        apparat.initialize(conn);
	      }
	    } catch (Exception e) {
	      System.out.println("db error during select of Øvelse= " + e);
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
		            "insert into Øvelse values (" + øvelseId + ", " + navn + ")");
		        stmt.executeUpdate(
			            "insert into ØvelsePåApparat values (" + øvelseId + ", " + antallKilo + ", " + antallSett + ", " + apparat.getApparatId()  + ")");
		      } catch (Exception e) {
		        System.out.println("db error during insert of Øvelse=" + e);
		        return;
		      }
		    }
}
