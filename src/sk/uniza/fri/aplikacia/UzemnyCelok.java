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
    private String stringObjektov;

    public UzemnyCelok(int paCislo, String paPopis, GPSData paSirka, GPSData paDlzka) {
        this.cislo = paCislo;
        this.popis = paPopis;
        this.sirka = paSirka;
        this.dlzka = paDlzka;
        this.uzemneObjekty = new ArrayList<>();
        this.stringObjektov = this.toStringObjektov();

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
        if (!this.uzemneObjekty.contains(paUzemnyCelok)) {
            this.uzemneObjekty.add(paUzemnyCelok);
        }
    }

    public boolean prekryvaSaSCelkom(UzemnyCelok paUzemnyCelok) {
        return this.uzemneObjekty.contains(paUzemnyCelok);
    }

    public void removeUzemnyObjekt(UzemnyCelok paUzemnyCelok) {
        this.uzemneObjekty.removeIf(hladanaNehnutelnost -> hladanaNehnutelnost == paUzemnyCelok);
    }

    public ArrayList<UzemnyCelok> getuzemneCelky() {
        return this.uzemneObjekty;
    }

    public void setUzemneObjekty(ArrayList<UzemnyCelok> uzemneObjekty) {
        this.uzemneObjekty = uzemneObjekty;
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

    public String toStringObjektov() {
        String resultString = "";
        for (UzemnyCelok uzemnyCelok : this.uzemneObjekty) {
            resultString += uzemnyCelok.toString();
        }
        return resultString;
    }

    public void setSirka(GPSData sirka) {
        this.sirka = sirka;
    }

    public void setDlzka(GPSData dlzka) {
        this.dlzka = dlzka;
    }

    public UzemnyCelok makeCopy() {
        if (this instanceof Parcela) {
            return new Parcela(this.getCislo(), this.getPopis(), this.getSirka(), this.getDlzka());
        } else if (this instanceof Nehnutelnost) {
            return new Nehnutelnost(this.getCislo(), this.getPopis(), this.getSirka(), this.getDlzka());
        }
        return null;
    }


    public String getStringObjektov() {
        return this.stringObjektov;
    }

    public void setStringObjektov(String stringObjektov) {
        this.stringObjektov = stringObjektov;
    }
}
