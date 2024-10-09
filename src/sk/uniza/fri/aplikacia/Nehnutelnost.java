package sk.uniza.fri.aplikacia;

import sk.uniza.fri.struktura.StromNode;
import sk.uniza.fri.struktura.Kluce;

/**
 * 8. 10. 2024 - 14:38
 *
 * @author matus
 */
//ulozit do viacej stromov lebo nimi netreba setrit
    //konkretne 3 stromy v jednom parcely v druhom nehnutelnosti a v tretom oboje
public class Nehnutelnost extends StromNode {

    public Nehnutelnost(int paCislo, String paPopis, StromNode[] paZoznam, Kluce paKluc) {
        super(paCislo, paPopis, paZoznam, paKluc);
    }


}
