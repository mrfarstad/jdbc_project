package com.mycompany.app;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class OvelsesGruppe extends ActiveDomainObject {

    private int gruppeId;
    private MuskelGruppe muskelGruppe;

    public OvelsesGruppe(int gruppeId, String muskelGruppe) {
        this.gruppeId = gruppeId;
        this.muskelGruppe = stringToMuskelGruppe(muskelGruppe);
    }

    public int getGruppeId() {
        return gruppeId;
    }

    public void setGruppeId(int gruppeId) {
        this.gruppeId = gruppeId;
    }

    public MuskelGruppe getMuskelGruppe() {
        return muskelGruppe;
    }

    public void setMuskelGruppe(MuskelGruppe muskelGruppe) {
        this.muskelGruppe = muskelGruppe;
    }

    public static List<OvelsesGruppe> listAll(Connection conn) {
        List<OvelsesGruppe> grupper = new ArrayList<>();
        try {
            Statement stmt = conn.createStatement();
            ResultSet rs =
                    stmt.executeQuery("select gruppeId, muskelGruppe from OvelsesGruppe");

            while (rs.next()) {
                int gruppeId = rs.getInt("gruppeId");
                String muskelGruppe = (rs.getString("muskelGruppe"));
                grupper.add(new OvelsesGruppe(gruppeId, muskelGruppe));
            }
        } catch (Exception e) {
            System.err.println("db error during select of OvelsesGruppe= " + e);
        }
        return grupper;
    }

    @Override
    public void initialize(Connection conn) {
        try {
            Statement stmt = conn.createStatement();
            ResultSet rs =
                    stmt.executeQuery("select gruppeId, muskelGruppe from OvelsesGruppe where gruppeId=" + gruppeId);
            while (rs.next()) {
                gruppeId = rs.getInt("gruppeId");
                muskelGruppe = stringToMuskelGruppe(rs.getString("muskelGruppe"));
            }
        } catch (Exception e) {
            System.out.println("db error during select of OvelsesGruppe= " + e);
            return;
        }
    }

    public static String muskelGruppeToString(MuskelGruppe mskl) {
        switch (mskl) {
            case BEIN:
                return "bein";
            case BICEPS:
                return "biceps";
            case TRICEPS:
                return "triceps";
            case MAGE:
                return "mage";
            case RYGG:
                return "rygg";
            case BRYST:
                return "bryst";
            case SKULDRE:
                return "skuldre";
            default:
                return null;
        }
    }

    public static MuskelGruppe stringToMuskelGruppe(String str) {
        switch (str) {
            case "bein":
                return MuskelGruppe.BEIN;
            case "biceps":
                return MuskelGruppe.BICEPS;
            case "triceps":
                return MuskelGruppe.TRICEPS;
            case "mage":
                return MuskelGruppe.MAGE;
            case "rygg":
                return MuskelGruppe.RYGG;
            case "bryst":
                return MuskelGruppe.BRYST;
            case "skuldre":
                return MuskelGruppe.SKULDRE;
            default:
                return null;
        }
    }

    @Override
    public void refresh(Connection conn) {
        initialize(conn);
    }

    public void deleteRow(Connection conn, Integer gruppeId) {
        try {
            Statement stmt = conn.createStatement();
            stmt.executeUpdate("delete from Ovelse where gruppeId = " + gruppeId);
        } catch (Exception e) {
            System.out.println("db error during deltion of Ovelse=" + e);
            return;
        }
    }

    @Override
    public void save(Connection conn) {
        try {
            Statement stmt = conn.createStatement();
            stmt.executeUpdate(
                    "insert into OvelsesGruppe values (" + gruppeId + ", \"" + muskelGruppeToString(muskelGruppe) + "\")");

        } catch (Exception e) {
            System.out.println("db error during insert of OvelsesGruppe=" + e);
            return;
        }
    }

    public static List<String> getOvelsesInGruppe(Connection conn, String gruppeId) {
        List<String> ovelser = new ArrayList<>();
        try {
            Statement stmt = conn.createStatement();
            ResultSet rs =
                    stmt.executeQuery("select navn from Ovelse join  TrenerMuskelGruppe on Ovelse.ovelseId = TrenerMuskelGruppe.ovelseId where TrenerMuskelGruppe.gruppeId =" + gruppeId);

            String name;
            while (rs.next()) {
                name = rs.getString("navn");
                ovelser.add(name);
            }

        } catch (Exception e) {
            System.out.println("db error during select of OvelsesGruppe= " + e);
        }
        return ovelser;

    }

    public static List<String> allGroupsFromOvelse(Connection conn, int ovelsesId){
        List<String> all = new ArrayList<>();

        try {
            Statement stmt = conn.createStatement();
            ResultSet rs =
                    stmt.executeQuery("select muskelGruppe from OvelsesGruppe join  TrenerMuskelGruppe on OvelsesGruppe.gruppeId = TrenerMuskelGruppe.gruppeId where TrenerMuskelGruppe.ovelseId =" + ovelsesId);

            while (rs.next()) {
                String name = rs.getString("muskelGruppe");
                all.add(name);
            }
        } catch (Exception e) {
            System.out.println("db error during select of OvelsesGruppe=" + e);
        }
        return all;
    }

    public static void addOvelseToGruppe(Connection conn, int ovelsesId, int gruppeId) {
        try {
            Statement stmt = conn.createStatement();
            stmt.executeUpdate(
                    "insert into TrenerMuskelGruppe values(" + ovelsesId + ", " +gruppeId+")");
        } catch (Exception e) {
            System.out.println("db error during insert of TrenerMuskelgruppe=" + e);
            return;
        }

    }
}
