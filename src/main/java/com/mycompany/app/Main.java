package com.mycompany.app;

import java.sql.SQLException;

public class Main {

  public static void main(String[] args) {

    // Use case 1
    try {
      RegistreringCtrl reg = new RegistreringCtrl();
      
      Apparat app = reg.registrerApparat(5, "rack", "very niz");
      System.out.println(app);
      
      OvelsePaApparat ovelse = reg.registrerOvelsePaApparat(4, "low bar", 75, 3, app.getApparatId());
      System.out.println(ovelse);
      
      reg.fullforRegistrering();
    } catch (SQLException e) { // TODO Auto-generated catch block
      e.printStackTrace();
    }

    // Use case 2
//    HentTreningsokterCtrl okter = new HentTreningsokterCtrl();
//    okter.senesteOkter(3);
  }
}
