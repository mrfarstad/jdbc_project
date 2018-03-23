package com.mycompany.app;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class RegistreringCtrl extends DBConnect {

  private Apparat apparat;
  private OvelsePaApparat ovelsePaApparat;
  private OvelseUtenApparat ovelseUtenApparat;
  private Treningsokt treningsokt;
  private Notat notat;

  public RegistreringCtrl() {
    connect();
    try {
      conn.setAutoCommit(false);
    } catch (SQLException e) {
      System.out.println("db error during setAuoCommit of RegistreringCtrl=" + e);
      return;
    }
  }

  public Apparat registrerApparat(int apparatId, String navn, String beskrivelse) {
    apparat = new Apparat(apparatId);
    apparat.initialize(conn);
    if (apparat.getApparatId() != null) {
      apparat.deleteRow(conn, apparatId);
    }
    apparat = new Apparat(apparatId, navn, beskrivelse);
    apparat.save(conn);
    return apparat;
  }

  public OvelsePaApparat registrerOvelsePaApparat(
      int ovelseId, String navn, int antallKilo, int antallSett, int apparatId) {
    Apparat app = new Apparat(apparatId);
    app.initialize(conn);
    ovelsePaApparat = new OvelsePaApparat(ovelseId);
    ovelsePaApparat.initialize(conn);
    if (ovelsePaApparat.getOvelseId() != null) {
      ovelsePaApparat.deleteRow(conn, ovelseId);
    }
    ovelsePaApparat = new OvelsePaApparat(ovelseId, navn, antallKilo, antallSett, app);
    ovelsePaApparat.save(conn);
    return ovelsePaApparat;
  }

  public OvelseUtenApparat registrerOvelseUtenApparat(
      int ovelseId, String navn, String beskrivelse) {
    ovelseUtenApparat = new OvelseUtenApparat(ovelseId);
    ovelseUtenApparat.initialize(conn);
    if (ovelseUtenApparat.getOvelseId() != null) {
      ovelseUtenApparat.deleteRow(conn, ovelseId);
    }
    ovelseUtenApparat = new OvelseUtenApparat(ovelseId, navn, beskrivelse);
    ovelseUtenApparat.save(conn);
    return ovelseUtenApparat;
  }

  public Notat registrerNotat(int notatId, String beskrivelse, int oktId) {
    notat = new Notat(notatId, beskrivelse, oktId);
    notat.initialize(conn);
    if (notat.getNotatId() != null) {
      notat.deleteRow(conn, notatId);
    }
    notat = new Notat(notatId, beskrivelse, oktId);
    notat.save(conn);
    return notat;
  }

  public Treningsokt registrerTreningsokt(
      int oktId,
      String dato,
      String tidspunkt,
      String varighet,
      int form,
      int prestasjon,
      int notatId,
      String beskrivelse,
      List<Integer> ovelser) {
    treningsokt = new Treningsokt(oktId);
    treningsokt.initialize(conn);
    if (treningsokt.getOktId() != null) {
      treningsokt.deleteRow(conn, oktId);
    }
    treningsokt = new Treningsokt(oktId, dato, tidspunkt, varighet, form, prestasjon, ovelser);
    treningsokt.save(conn);
    registrerNotat(notatId, beskrivelse, oktId);
    return treningsokt;
  }

  public OvelsesGruppe registrerOvelsesGruppe(int gruppeId, String muskelGruppe) {
    OvelsesGruppe gr = new OvelsesGruppe(gruppeId, muskelGruppe);
    gr.save(conn);
    return gr;
  }

  public List<Treningsokt> senesteOkter(int antallTreningsokter) {
    List<Treningsokt> okter = new ArrayList<>();
    try {
      Statement stmt = conn.createStatement();
      ResultSet rs = stmt.executeQuery("select * from Treningsokt order by dato desc limit " + antallTreningsokter);
      while (rs.next()) {
        okter.add(
            new Treningsokt(
                rs.getInt("oktId"),
                rs.getString("dato"),
                rs.getString("tidspunkt").substring(0, 5),
                rs.getString("varighet"),
                rs.getInt("form"),
                rs.getInt("prestasjon"),
                null));
      }
    } catch (Exception e) {
      System.out.println("db error during select of Treningsokt= " + e);
    }
    return okter;
  }
  
  public void resultatloggFraTidsintervall(LocalDate startDato, LocalDate sluttDato) {
	    try {
	      Statement stmt = conn.createStatement();
	      String query = 
	              "select * from Treningsokt natural join OvelserUtfortPaTreningsokt natural join Ovelse where dato between \""
		                  + startDato
		                  + "\" and \""
		                  + sluttDato + "\";";
	      ResultSet rs =
	          stmt.executeQuery(query);
	      while (rs.next()) {
	    	  System.out.println("Treningsøkt:"); 
	        System.out.println("oktId: " + rs.getInt("oktId"));
	        System.out.println("ovelseId: " + rs.getInt("ovelseId"));
	        System.out.println("dato: " + rs.getString("dato"));
	        System.out.println("tidspunkt: " + rs.getString("tidspunkt"));
	        System.out.println("varighet: " + rs.getString("varighet"));
	        System.out.println("form: " + rs.getInt("form"));
	        System.out.println("prestasjon: " + rs.getInt("prestasjon"));
	        System.out.println("navn: " + (rs.getString("navn")));
	        System.out.println("------------" ); 
	      }
	    } catch (Exception e) {
	      System.out.println("db error during select of Treningsokt= " + e);
	    }
	  }

  public void fullforRegistrering() {
    // Skriv inn alt som skal lagres
    try {
      conn.commit();
    } catch (SQLException e) {
      System.out.println("db error during commit of RegistreringCtrl=" + e);
      return;
    }
  }
}
