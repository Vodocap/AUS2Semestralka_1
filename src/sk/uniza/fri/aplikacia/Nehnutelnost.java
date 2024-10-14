package sk.uniza.fri.aplikacia;

import sk.uniza.fri.struktura.StromNode;

/**
 * 8. 10. 2024 - 14:38
 *
 * @author matus
 */
//ulozit do viacej stromov lebo nimi netreba setrit
    //konkretne 3 stromy v jednom parcely v druhom nehnutelnosti a v tretom oboje
    //treba vediet aj profiler
public class Nehnutelnost extends StromNode<Double> {
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
