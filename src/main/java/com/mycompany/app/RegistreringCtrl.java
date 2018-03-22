package com.mycompany.app;

import java.sql.SQLException;

public class RegistreringCtrl extends DBConnect {

  private Apparat apparat;
  private Øvelse øvelse;
  private Treningsøkt treningsøkt;

  public RegistreringCtrl() {
    connect();
    // La laging av avtale være en transaksjon
    try {
      conn.setAutoCommit(false);
    } catch (SQLException e) {
      System.out.println("db error during setAuoCommit of RegistreringCtrl=" + e);
      return;
    }
  }

  public Apparat registrerApparat(int apparatId, String navn, String beskrivelse) {
    // Sjekk om apparat allerede eksisterer
    apparat = new Apparat(apparatId);
    apparat.initialize(conn);
    if (apparat.getApparatId() == null) {
      //Hvis ikke, lag en ny rad i apparat tabellen
      apparat = new Apparat(apparatId, navn, beskrivelse);
      apparat.save(conn);
    }
    return apparat;
  }

  public ØvelsePåApparat registrerØvelsePåApparat(
      int øvelseId, String navn, int antallKilo, int antallSett, int apparatId) {
    registrerApparat(apparatId, navn, "");
    ØvelsePåApparat øvelsePåApparat = new ØvelsePåApparat(øvelseId, navn, antallKilo, antallSett);
    øvelsePåApparat.save(conn);
    return øvelsePåApparat;
  }

  public ØvelseUtenApparat registrerØvelseUtenApparat(int øvelseId, String navn, String beskrivelse) {
	    registrerApparat(øvelseId, navn, navn);
	    ØvelseUtenApparat øvelseUtenApparat = new ØvelseUtenApparat(øvelseId, navn, beskrivelse);
	    øvelseUtenApparat.save(conn);
	    return øvelseUtenApparat;
  }
  
  private Notat registrerNotat(int notatId, String beskrivelse) {
	    // Sjekk om apparat allerede eksisterer
	    Notat notat = new Notat(notatId, beskrivelse);
	    notat.initialize(conn);
	    if (notat.getNotatId() == null) {
	      //Hvis ikke, lag en ny rad i apparat tabellen
	    	notat = new Notat(notatId, beskrivelse);
	    	notat.save(conn);
	    }
	    return notat;
	  }

  public Treningsøkt registrerTreningsøkt(
	      int øktId, String dato, String tidspunkt, int varighet, int form, int prestasjon, int notatId, String beskrivelse) {
	  registrerNotat(notatId, beskrivelse);
	  Treningsøkt treningsøkt = new Treningsøkt(øktId, dato, tidspunkt, varighet, form, prestasjon);
	  treningsøkt.save(conn);
	  return treningsøkt;
	  
  }

  public void fullførRegistrering() {
    // Skriv inn alt som skal lagres
    try {
      conn.commit();
    } catch (SQLException e) {
      System.out.println("db error during commit of RegistreringCtrl=" + e);
      return;
    }
  }
}
