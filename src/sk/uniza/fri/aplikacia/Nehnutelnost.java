package sk.uniza.fri.aplikacia;

import sk.uniza.fri.struktura.TrNode;

/**
 * 8. 10. 2024 - 14:38
 *
 * @author matus
 */

public class Nehnutelnost extends TrNode<Double> {
    private int cislo;
    private String popis;

    public Nehnutelnost(GPSData gPSData, int paCislo, String paPopis) {
        super.data = gPSData;
        this.cislo = paCislo;
        this.popis = paPopis;
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
}
