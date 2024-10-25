package sk.uniza.fri;


public class Main {

    public static void main(String[] args) {
        TestTrieda testTrieda = new TestTrieda(2);
        testTrieda.naplnStromAVypis(100000, false);

        //testTrieda.naplnStromAVypis(20, true);
        testTrieda.deletujAVypisSkontroluj(50000);
        testTrieda.skontrolujStrom();
        //testTrieda.reinsertujCelyStrom();
        //testTrieda.najdiNahodnePrvky(3);

    }
}
