package com.mycompany.app;

import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import javafx.util.Pair;

abstract class UiBaseElement {
  private static void printInputThing(String hint) {
    System.out.print("Skriv inn \"" + hint + "\": ");
  }

  public static int getInteger(String text) {
    return Integer.parseInt(getString(text));
  }

  public static String getString(String text) {
    printInputThing(text);
    return new Scanner(System.in).nextLine();
  }

  abstract void execute(RegistreringCtrl ctrl) throws SQLException;
}

class RegistrerApparat extends UiBaseElement {
  @Override
  public void execute(RegistreringCtrl ctrl) throws SQLException {
    int apparatId = getInteger("ApperatId");
    String navn = getString("Navn");
    String beskrivelse = getString("Beskrivelse");
    ctrl.registrerApparat(apparatId, navn, beskrivelse);
  }
}

class RegistrerOvelsePaApparat extends UiBaseElement {
  @Override
  public void execute(RegistreringCtrl ctrl) throws SQLException {
    int ovelseId = getInteger("ØvelsesId");
    String navn = getString("Navn");
    int antallKilo = getInteger("Antall kilo");
    int antallSett = getInteger("Antall sett");
    int apparatId = getInteger("ApparatId");
    ctrl.registrerOvelsePaApparat(ovelseId, navn, antallKilo, antallSett, apparatId);
  }
}

class RegistrerOvelseUtenApparat extends UiBaseElement {
  @Override
  public void execute(RegistreringCtrl ctrl) {
    int ovelseId = getInteger("ØvelsesId");
    String navn = getString("Navn");
    String beskrivelse = getString("Antall sett");
    ctrl.registrerOvelseUtenApparat(ovelseId, navn, beskrivelse);
  }
}

class HentTreningsokter extends UiBaseElement {
  @Override
  public void execute(RegistreringCtrl ctrl) {
    int antallTreningsokter = getInteger("Antall treningsøkter");
    System.out.println(ctrl.senesteOkter(antallTreningsokter));
  }
}

class Resultatlogg extends UiBaseElement {
  @Override
  public void execute(RegistreringCtrl ctrl) {
    LocalDate startDato =
        LocalDate.parse(getString("Fra dato (yyyy-MM-dd):"), DateTimeFormatter.ISO_LOCAL_DATE);
    LocalDate sluttDato =
        LocalDate.parse(getString("Til dato (yyyy-MM-dd):"), DateTimeFormatter.ISO_LOCAL_DATE);
    ctrl.resultatloggFraTidsintervall(startDato, sluttDato);
  }
}

class ExitApp extends UiBaseElement {
  @Override
  public void execute(RegistreringCtrl ctrl) {
    System.exit(0);
  }
}

class RegistrerTreningsøkt extends UiBaseElement {
  @Override
  public void execute(RegistreringCtrl ctrl) {
    int oktId = getInteger("ØktId");
    String dato = getString("Dato");
    String tidspunkt = getString("Tidspunkt(hh:mm:)");
    String varighet = getString("Varighet(hh:mm:ss)");
    int form = getInteger("Form");
    int prestasjon = getInteger("Prestasjon");
    int notatId = getInteger("NotatId");
    String beskrivelse = getString("Beskrivelse");
    Integer ovelseId = -1;
    List<Integer> ovelser = new ArrayList<>();
    while (ovelseId != 0) {
      ovelseId = getInteger("Øvelser - id - øvelsene må eksistere - trykk 0 for å gå videre");
      if (ovelseId != 0) {
        ovelser.add(ovelseId);
      }
    }
    ctrl.registrerTreningsokt(
        oktId, dato, tidspunkt, varighet, form, prestasjon, notatId, beskrivelse, ovelser);
  }
}

class OvelserInGruppe extends UiBaseElement {
  @Override
  public void execute(RegistreringCtrl ctrl) {
    OvelsesGruppe.listAll(ctrl.conn)
        .forEach(
            ovels ->
                System.out.printf(
                    " - %s (id: %d)\n",
                    OvelsesGruppe.muskelGruppeToString(ovels.getMuskelGruppe()),
                    ovels.getGruppeId()));
    String gruppeId = getString("GruppeId");
    List<String> ovelser = OvelsesGruppe.getOvelsesInGruppe(ctrl.conn, gruppeId);

    System.out.println("Fant følgende Øvelser: ");
    ovelser.stream().forEach(ovelse -> System.out.printf(" - %s\n", ovelse));
  }
}

class GrupperForOvelse extends UiBaseElement {
    @Override
    public void execute(RegistreringCtrl ctrl) {
        int ovelsesId = getInteger("ovelsesId");
        System.out.println("Fant følgende Grupper: ");
        OvelsesGruppe.allGroupsFromOvelse(ctrl.conn, ovelsesId).forEach(gruppe->System.out.printf(" - %s\n", gruppe));
    }
}


class RegistrerOvelsesGruppe extends UiBaseElement {
  @Override
  public void execute(RegistreringCtrl ctrl) {
    int gruppeId = getInteger("gruppeId");
    String muskelGruppe = getString("muskelGruppe");
    ctrl.registrerOvelsesGruppe(gruppeId, muskelGruppe);
  }
}

class AddTrenerMuskelGruppe extends UiBaseElement {
  @Override
  public void execute(RegistreringCtrl ctrl) {
    int gruppeId = getInteger("gruppeId");
    int ovelsesId = getInteger("ovelsesId");
    OvelsesGruppe.addOvelseToGruppe(ctrl.conn, ovelsesId, gruppeId);
  }
}

public class Main {

    public static void main(String[] args) {

        RegistreringCtrl reg = new RegistreringCtrl();
        List<Pair<String, UiBaseElement>> elements = new ArrayList<>();

        elements.add(new Pair("USECASE 1 - Registrer apparat", new RegistrerApparat()));
        elements.add(new Pair("USECASE 1 - Registrer øvelse på apparat", new RegistrerOvelsePaApparat()));
        elements.add(new Pair("USECASE 1 - Registrer øvelse uten apparat", new RegistrerOvelseUtenApparat()));
        elements.add(new Pair("USECASE 1 - Registrer treningsøkt", new RegistrerTreningsøkt()));
        elements.add(new Pair("USECASE 2 - Hent siste økter", new HentTreningsokter()));
        elements.add(new Pair("USECASE 3 - Resultatlogg", new Resultatlogg()));
        elements.add(new Pair("USECASE 4 - Registrer ny øvelsesgruppe", new RegistrerOvelsesGruppe()));
        elements.add(new Pair("USECASE 4 - Registrer TrenderMuskelGruppe", new AddTrenerMuskelGruppe()));
        elements.add(new Pair("USECASE 4 - List øvelser i en gruppe", new OvelserInGruppe()));
        elements.add(new Pair("USECASE 5 - List grouppene til en øvelse", new GrupperForOvelse()));
        elements.add(new Pair("Exit app", new ExitApp()));

        while (true) {
            System.out.println("Du har følgende valg");
            for (int i = 0; i < elements.size(); i++) {
                System.out.printf("[%d] - %s\n", i, elements.get(i).getKey());
            }
            System.out.print("Velg et tall: ");
            Scanner in = new Scanner(System.in);
            try {
                elements.get(in.nextInt()).getValue().execute(reg);
            } catch (SQLException e) {
                System.err.printf("Feil ved utføring: %s\n", e.getMessage());
            } catch (IndexOutOfBoundsException e){
                System.err.println("Ugyldig valg.....!!");
            } finally {
                System.out.println("Operasjon utført");
            }
            reg.fullforRegistrering();
        }
    }
  }
