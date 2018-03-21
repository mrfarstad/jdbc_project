package com.mycompany.app;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

public class Øvelse extends ActiveDomainObject {

  private int øvelseId;
  private String navn;

  @Override
  public void initialize(Connection conn) {
    try {
      Statement stmt = conn.createStatement();
      ResultSet rs =
          stmt.executeQuery("select øvelseId, navn from Øvelse where apparatId=" + øvelseId);
      while (rs.next()) {
        øvelseId = rs.getInt("øvelseId");
        navn = rs.getString("navn");
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
	            "insert into Øvelse values (" + øvelseId + ", " + navn + "," + navn + ")");
	      } catch (Exception e) {
	        System.out.println("db error during insert of Øvelse=" + e);
	        return;
	      }
	    }
}
