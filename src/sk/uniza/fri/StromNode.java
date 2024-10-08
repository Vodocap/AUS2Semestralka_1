package sk.uniza.fri;

/**
 * 8. 10. 2024 - 14:38
 *
 * @author matus
 */
public abstract class StromNode {
    private int cislo;
    private String popis;
    private StromNode[] zoznam;
    private Kluce kluc;

    public StromNode (int paCislo, String paPopis, StromNode[] paZoznam, Kluce paKluc) {
        this.cislo = paCislo;
        this.popis = paPopis;
        this.zoznam = paZoznam;
        this.kluc = paKluc;
    }
    private int compare(StromNode paNode) {
        return 0;
    }
}
