package sk.uniza.fri.aplikacia;

/**
 * 8. 10. 2024 - 14:38
 *
 * @author matus
 */
public class Parcela extends UzemnyCelok {

    public Parcela(int paCislo, String paPopis, GPSData paSirka, GPSData paDlzka) {
        super(paCislo, paPopis, paSirka, paDlzka);
    }

    public String toString () {
        return  ("Parcela - cislo (" + super.getCislo() + ") "
                + "Popis: " + super.getPopis() + "\n"
                + "Suradnice: " + "(" + (super.getSirka().getDataAtD(0)) + ", " + (super.getSirka().getDataAtD(1)) + ") "
                + "(" + (super.getDlzka().getDataAtD(0)) + ", " + (super.getDlzka().getDataAtD(1)) + ") ") + "\n";
    }

}
