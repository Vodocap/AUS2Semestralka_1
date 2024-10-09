package sk.uniza.fri.struktura;

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

    //abstract data (tam je comapre), abstract node a abstract strom
    //Abstract data bude interface aby bola viacnas dedicnost
    // da sa j cez genericke triedy
    // do search idu genericke data
    //kod musi byt genericky zamerat sa na robenie stromu vseobecne
    //atributy prec, ma to byt vseobecne takze tie sa definuju az v triedach co dedia
    //kluce generujeme nahodne
    //spravit si tester (náhodne vyberáme operácie s náhodnýmk hodnotami)
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
