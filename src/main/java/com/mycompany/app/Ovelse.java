package com.mycompany.app;

public abstract class Ovelse extends ActiveDomainObject {

  protected Integer ovelseId;
  protected String navn;

  public Integer getOvelseId() {
	  return ovelseId;
  }
  
  public String getNavn() {
	  return navn;
  }
}
