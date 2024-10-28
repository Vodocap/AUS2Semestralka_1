package sk.uniza.fri.aplikacia;

import java.util.ArrayList;

/**
 * 8. 10. 2024 - 14:38
 *
 * @author matus
 */
public abstract class UzemnyCelok {

    private int cislo;
    private String popis;
    private ArrayList<UzemnyCelok> uzemneObjekty;

    private GPSData sirka;
    private GPSData dlzka;

    public UzemnyCelok(int paCislo, String paPopis, GPSData paSirka, GPSData paDlzka) {
        this.cislo = paCislo;
        this.popis = paPopis;
        this.sirka = paSirka;
        this.dlzka = paDlzka;
        this.uzemneObjekty = new ArrayList<>();
    }

    public int getCislo() {
        return this.cislo;
    }


    public void setCislo(int cislo) {
        this.cislo = cislo;
    }

    public String getPopis() {
        return this.popis;
    }

    public void setPopis(String popis) {
        this.popis = popis;
    }

    public ArrayList<UzemnyCelok> getParcely() {
        return this.uzemneObjekty;
    }

    public void pridajUzemnyCelok(UzemnyCelok paUzemnyCelok) {
        this.uzemneObjekty.add(paUzemnyCelok);
    }

    public void removeUzemnyObjekt(UzemnyCelok paUzemnyCelok) {
        this.uzemneObjekty.removeIf(hladanaNehnutelnost -> hladanaNehnutelnost == paUzemnyCelok);
    }

    public GPSData getSirka() {
        return this.sirka;
    }

    public GPSData getDlzka() {
        return this.dlzka;
    }

    public UzemnyCelok makeDeepCopy() {
        return null;
    }




}
