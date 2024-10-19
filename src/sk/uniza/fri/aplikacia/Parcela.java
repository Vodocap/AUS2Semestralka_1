package sk.uniza.fri.aplikacia;

import sk.uniza.fri.struktura.TrNode;

import java.util.ArrayList;
import java.util.List;

/**
 * 8. 10. 2024 - 14:38
 *
 * @author matus
 */
public class Parcela {
    private int cislo;
    private String popis;
    private ArrayList<Nehnutelnost> nehnutelnosti;

    public Parcela(int paCislo, String paPopis) {
        this.nehnutelnosti = new ArrayList<>();
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

    public ArrayList<Nehnutelnost> getNehnutelnosti() {
        return this.nehnutelnosti;
    }

    public void addNehnutelnost(Nehnutelnost paNehnutelnost) {
        this.nehnutelnosti.add(paNehnutelnost);
    }

    public void removeNehnutelnost(Nehnutelnost panehnutelnost) {
        this.nehnutelnosti.removeIf(hladanaNehnutelnost -> hladanaNehnutelnost == panehnutelnost);
    }
}
