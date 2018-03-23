package com.mycompany.app;

import static org.junit.Assert.assertTrue;

import java.util.Arrays;

import org.junit.Test;

public class RegistreringCtrlTest {

  @Test
  public void testRegistrerCtrl() {
    //	  Use case 1
    RegistreringCtrl reg = new RegistreringCtrl();
    reg.registrerApparat(1, "rack", "very niz");
    reg.registrerOvelsePaApparat(2, "low bar", 75, 3, 1);
    reg.registrerOvelseUtenApparat(3, "high bar", "digg");
    Treningsokt okt = reg.registrerTreningsokt(4, "2018-03-23", "11:19", 50, 10, 10, 5, "yolo", Arrays.asList(2, 3));
    System.out.println(okt.getOvelseIds());
    reg.fullforRegistrering();
  }

  @Test
  public void testHentTreningsokterCtrl() {
    //	  Use case 2
    HentTreningsokterCtrl okter = new HentTreningsokterCtrl();
    RegistreringCtrl reg = new RegistreringCtrl();
    reg.registrerTreningsokt(1, "2018-03-23", "11:19", 50, 10, 10, 5, "yolo", Arrays.asList(2, 3));
    reg.registrerTreningsokt(2, "2018-03-23", "11:19", 50, 10, 10, 6, "yolo", Arrays.asList(2, 3));
    reg.registrerTreningsokt(3, "2018-03-23", "11:19", 50, 10, 10, 7, "yolo", Arrays.asList(2, 3));
    reg.fullforRegistrering();
    
    assertTrue(okter.senesteOkter(3).size() == 3);
    assertTrue(okter.senesteOkter(2).size() == 2);
    assertTrue(okter.senesteOkter(1).size() == 1);
    assertTrue(okter.senesteOkter(0).size() == 0);
  }
}
