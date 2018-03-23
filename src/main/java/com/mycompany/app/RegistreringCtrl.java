package com.mycompany.app;

import java.sql.SQLException;
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

  // Use case 1
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

  // Use case 1
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

  // Use case 1
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

  // Use case 1
  public Treningsokt registrerTreningsokt(
      int oktId,
      String dato,
      String tidspunkt,
      int varighet,
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
