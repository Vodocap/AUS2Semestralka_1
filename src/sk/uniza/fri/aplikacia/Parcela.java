package sk.uniza.fri.aplikacia;

import sk.uniza.fri.struktura.TrNode;

/**
 * 8. 10. 2024 - 14:38
 *
 * @author matus
 */
public class Parcela extends TrNode<Double> {
    private int cislo;
    private String popis;

    public Parcela(GPSData gPSData, int paCislo, String paPopis) {
        super.data = gPSData;
        this.cislo = paCislo;
        this.popis = paPopis;
    }

    public int getCislo() {
        return cislo;
    }

    public void setCislo(int cislo) {
        this.cislo = cislo;
    }

    public String getPopis() {
        return popis;
    }

    public void setPopis(String popis) {
        this.popis = popis;
    }

}
