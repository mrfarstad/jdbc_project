package com.mycompany.app;

import static org.junit.Assert.assertTrue;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;

import org.junit.Test;

public class RegistreringCtrlTest {

  @Test
  public void testRegistrering() {
    //	  Use case 1
    RegistreringCtrl reg = new RegistreringCtrl();
    reg.registrerApparat(1, "rack", "very niz");
    reg.registrerOvelsePaApparat(2, "low bar", 75, 3, 1);
    reg.registrerOvelseUtenApparat(3, "high bar", "digg");
    reg.registrerTreningsokt(
        4, "2018-03-23", "11:19", "01:11:19", 10, 10, 5, "yolo", Arrays.asList(2, 3));
    reg.fullforRegistrering();
  }

  @Test
  public void testHentTreningsokter() {
    //	  Use case 2
    RegistreringCtrl reg = new RegistreringCtrl();
    reg.registrerTreningsokt(
        1, "2018-03-23", "11:19", "01:11:19", 10, 10, 5, "yolo", Arrays.asList(2, 3));
    reg.registrerTreningsokt(
        2, "2018-03-23", "11:19", "01:11:19", 10, 10, 6, "yolo", Arrays.asList(2, 3));
    reg.registrerTreningsokt(
        3, "2018-03-23", "11:19", "01:11:19", 10, 10, 7, "yolo", Arrays.asList(2, 3));
    reg.fullforRegistrering();

    assertTrue(reg.senesteOkter(3).size() == 3);
    assertTrue(reg.senesteOkter(2).size() == 2);
    assertTrue(reg.senesteOkter(1).size() == 1);
    assertTrue(reg.senesteOkter(0).size() == 0);
  }

  @Test
  public void resultatloggFraTidsintervall() {
    RegistreringCtrl reg = new RegistreringCtrl();
    LocalDate startDato = LocalDate.parse("2018-03-22", DateTimeFormatter.ISO_LOCAL_DATE);
    LocalDate sluttDato = LocalDate.parse("2018-03-23", DateTimeFormatter.ISO_LOCAL_DATE);
    reg.resultatloggFraTidsintervall(startDato, sluttDato);
  }
}
