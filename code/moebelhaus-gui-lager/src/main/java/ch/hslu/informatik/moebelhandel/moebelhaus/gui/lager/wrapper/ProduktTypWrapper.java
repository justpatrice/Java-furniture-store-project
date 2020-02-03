package ch.hslu.informatik.moebelhandel.moebelhaus.gui.lager.wrapper;

import ch.hslu.informatik.moebelhandel.moebelhaus.model.ProduktTyp;

public class ProduktTypWrapper {

    private int nr;
    private ProduktTyp produktTyp;
    // private Lieferant lieferant;
    // private String lieferantName;
    // private String produktTypCode;
    // private String beschreibung;
    // private double preis;
    // private double minBestand;
    // private int maxBestand;
    // private String ablage;
    private int anzahl;

    public ProduktTypWrapper(int nr, ProduktTyp produktTyp, int anzahl) {
        this.nr = nr;
        this.produktTyp = produktTyp;
        this.anzahl = anzahl;

    }

    public int getNr() {
        return nr;
    }

    public void setNr(int nr) {
        this.nr = nr;
    }

    public String getLieferantName() {
        return produktTyp.getLieferant().getName();
    }

    public String getProduktTypCode() {
        return produktTyp.getTypCode();
    }

    public String getBeschreibung() {
        return produktTyp.getBeschreibung();
    }

    public double getPreis() {
        return produktTyp.getPreis();
    }

    public double getMinBestand() {
        return produktTyp.getMinimalerBestand();
    }

    public int getMaxBestand() {
        return produktTyp.getMaximalerBestand();
    }

    public String getAblage() {
        return produktTyp.getAblage();
    }

    public int getAnzahl() {
        return anzahl;
    }

    public void setAnzahl(int anzahl) {
        this.anzahl = anzahl;
    }

    public ProduktTyp getProduktTyp() {
        return produktTyp;
    }

}
