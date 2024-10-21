package sk.uniza.fri.aplikacia;

import sk.uniza.fri.struktura.TrNode;

import java.util.ArrayList;

/**
 * 8. 10. 2024 - 14:38
 *
 * @author matus
 */

public class Nehnutelnost implements IUzemnyCelok{
    private int cislo;
    private String popis;
    private ArrayList<Parcela> parcely;

    public Nehnutelnost(int paCislo, String paPopis) {
        this.cislo = paCislo;
        this.popis = paPopis;
        this.parcely = new ArrayList<>();
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

    public ArrayList<Parcela> getParcely() {
        return this.parcely;
    }

    public void addParcela(Parcela paParcela) {
        this.parcely.add(paParcela);
    }

    public void removeParcela(Parcela paParcela) {
        this.parcely.removeIf(hladanaNehnutelnost -> hladanaNehnutelnost == paParcela);
    }
}
