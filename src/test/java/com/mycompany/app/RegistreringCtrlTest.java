package com.mycompany.app;

import org.junit.Test;

public class RegistreringCtrlTest {

  @Test
  public void testRegistrerCtrl() {
    //	  Use case 1
    RegistreringCtrl reg = new RegistreringCtrl();
    reg.registrerApparat(1, "rack", "very niz");
    reg.registrerOvelsePaApparat(2, "low bar", 75, 3, 1);
    reg.registrerOvelseUtenApparat(3, "high bar", "digg");
    reg.registrerTreningsokt(4, "2018-03-23", "11:19", 50, 10, 10, 5, "yolo");
    reg.fullforRegistrering();
  }

//  @Test
//  public void testHentTreningsokterCtrl() {
//    //	  Use case 2
//    HentTreningsokterCtrl okter = new HentTreningsokterCtrl();
//    okter.senesteOkter(3);
//  }
}
