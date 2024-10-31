package sk.uniza.fri;


import sk.uniza.fri.aplikacia.Parcela;
import sk.uniza.fri.aplikacia.TrControl;
import sk.uniza.fri.aplikacia.UzemnyCelok;

public class Main {

    public static void main(String[] args) {
        TestTrieda testTrieda = new TestTrieda(2);

        testTrieda.generatorOperacii(1000, true, false);
//        testTrieda.testovaciePripady();
//        TrControl trControl = new TrControl();
//        double [] suradnice = {10, 20, 30, 40};
//        trControl.pridajParcelu(10, "Janova parcela", suradnice);
//        double [] suradnice1 = {10, 20, 30, 40};
//        trControl.pridajNehnutelnost(10, "Janova parcela", suradnice1);
//        for (UzemnyCelok parcela : trControl.najdiVsetkyObjekty(10, 20)) {
//            System.out.println(parcela.toString());
//        }
//        testTrieda.naplnStromAVypis(1000000, false);
//        testTrieda.naplnStromAVypisInty(20);
//        testTrieda.vyvolajBalancovanie();
        //testTrieda.naplnStromAVypis(20, true);
//        testTrieda.deletujAVypisSkontroluj(19);
//        testTrieda.vyvolajBalancovanie();
//        testTrieda.naplnStromAVypisInty(3);
//        testTrieda.vyvolajBalancovanie();
//        testTrieda.skontrolujStrom();
        //testTrieda.reinsertujCelyStrom();
        //testTrieda.najdiNahodnePrvky(3);

    }
}
