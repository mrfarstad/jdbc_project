package com.mycompany.app;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Treningsokt extends ActiveDomainObject {

  private Integer oktId;
  private LocalDateTime datoTid;
  private Integer varighet;
  private Integer form;
  private Integer prestasjon;

  private static DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

  public Treningsokt(int oktId) {
    this.oktId = oktId;
    this.datoTid = null;
    this.varighet = null;
    this.form = null;
    this.prestasjon = null;
  }

  public Treningsokt(
      int oktId, String dato, String tidspunkt, int varighet, int form, int prestasjon) {
    this.oktId = oktId;
    this.datoTid = LocalDateTime.parse(dato + " " + tidspunkt, formatter);
    this.varighet = varighet;
    this.form = form;
    this.prestasjon = prestasjon;
  }

  public Integer getOktId() {
    return oktId;
  }

  @Override
  public void initialize(Connection conn) {
    try {
      Statement stmt = conn.createStatement();
      ResultSet rs = stmt.executeQuery("select * from Treningsokt where oktId=" + oktId);
      if (!rs.isBeforeFirst()) {
        oktId = null;
      }
      while (rs.next()) {
        oktId = rs.getInt("oktId");
        datoTid =
            LocalDateTime.parse(
                rs.getString("dato") + " " + rs.getString("tidspunkt").substring(0, 5), formatter);
        varighet = rs.getInt("varighet");
        form = rs.getInt("form");
        prestasjon = rs.getInt("prestasjon");
      }
    } catch (Exception e) {
      System.out.println("db error during select of Treningsokt= " + e);
      return;
    }
  }

  @Override
  public void refresh(Connection conn) {
    initialize(conn);
  }

  @Override
  public void save(Connection conn) {

    DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm");

    String dato = datoTid.format(dateFormatter);
    String tid = datoTid.format(timeFormatter);
    try {
      Statement stmt = conn.createStatement();
      stmt.executeUpdate(
          "insert into Treningsokt values ("
              + oktId
              + ", \""
              + dato
              + "\", \""
              + tid
              + ":00\", "
              + varighet
              + ", "
              + form
              + ", "
              + prestasjon
              + ")");
    } catch (Exception e) {
      System.out.println("db error during insert of Treningsokt=" + e);
      return;
    }
  }

  public void deleteRow(Connection conn, Integer id) {
    try {
      Statement stmt = conn.createStatement();
      stmt.executeUpdate("delete from Treningsokt where oktId = " + id);
    } catch (Exception e) {
      System.out.println("db error during deletion of Treningsokt =" + e);
      return;
    }
  }
}
