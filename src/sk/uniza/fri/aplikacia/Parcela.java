package sk.uniza.fri.aplikacia;

import sk.uniza.fri.struktura.Kluce;
import sk.uniza.fri.struktura.StromNode;

/**
 * 8. 10. 2024 - 14:38
 *
 * @author matus
 */
public class Parcela extends StromNode {

    public Parcela(int paCislo, String paPopis, StromNode[] paZoznam, Kluce paKluc) {
        super(paCislo, paPopis, paZoznam, paKluc);
    }
}
