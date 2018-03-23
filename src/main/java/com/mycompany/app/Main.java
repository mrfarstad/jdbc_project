package com.mycompany.app;


import javafx.util.Pair;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;


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
        String tidspunkt = getString("Dato");
        int varighet = getInteger("ØktId");
        int form = getInteger("ØktId");
        int prestasjon = getInteger("ØktId");
        int notatId = getInteger("ØktId");
        String beskrivelse = getString("Dato");
        Integer ovelseId = 0;
        List<Integer> ovelser = new ArrayList<>();
        while (ovelseId != 0) {
            ovelseId = getInteger("Øvelser");
            ovelser.add(ovelseId);
        }
        ctrl.registrerTreningsokt(oktId, dato, tidspunkt, varighet, form, prestasjon, notatId, beskrivelse, ovelser);
    }
}

class OvelserInGruppe extends UiBaseElement {
    @Override
    public void execute(RegistreringCtrl ctrl) {
        OvelsesGruppe.listAll(ctrl.conn).forEach(ovels->System.out.printf(" - %s (id: %d)\n", OvelsesGruppe.muskelGruppeToString(ovels.getMuskelGruppe()), ovels.getGruppeId()));
        String gruppeId = getString("GruppeId");
        List<String> ovelser = OvelsesGruppe.getOvelsesInGruppe(ctrl.conn, gruppeId);

        System.out.println("Fant følgende Øvelser: ");
        ovelser.stream().forEach(ovelse -> System.out.printf(" - %s\n", ovelse));
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

        elements.add(new Pair("Registrer apparat", new RegistrerApparat()));
        elements.add(new Pair("Registrer øvelse på apparat", new RegistrerOvelsePaApparat()));
        elements.add(new Pair("Registrer øvelse uten apparat", new RegistrerOvelseUtenApparat()));
        elements.add(new Pair("Registrer treningsøkt", new RegistrerTreningsøkt()));
        elements.add(new Pair("Registrer ny øvelsesgruppe", new RegistrerOvelsesGruppe()));
        elements.add(new Pair("Registrer TrenderMuskelGruppe", new AddTrenerMuskelGruppe()));
        elements.add(new Pair("List øvelser i en gruppe", new OvelserInGruppe()));
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
