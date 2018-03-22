package com.mycompany.app;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

public class Notat extends ActiveDomainObject {
	
	private int notatId;
	private String beskrivelse;
	
	public Notat(int notatId, String beskrivelse) {
		this.notatId = notatId;
		this.beskrivelse = beskrivelse;
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
          stmt.executeQuery("select notatId, beskrivelse from Notat where notatId=" + notatId);
      while (rs.next()) {
    	  notatId = rs.getInt("notatId");
    	  beskrivelse = rs.getString("beskrivelse");
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
	        stmt.executeUpdate(
	            "insert into Notat values (" + notatId + ", " + beskrivelse + ")");
	      } catch (Exception e) {
	        System.out.println("db error during insert of Notat=" + e);
	        return;
	      }
	    }
}
