package com.mycompany.app;

public class Main {

  public static void main(String[] args) {
	  RegistreringCtrl reg = new RegistreringCtrl();
	  Apparat app = reg.registrerApparat(1, "rack", "very niz");
	  System.out.println(app);
	  OvelsePaApparat ovelse = reg.registrerOvelsePaApparat(1, "low bar", 75, 3, app.getApparatId());
	  System.out.println(ovelse);
  }
}
