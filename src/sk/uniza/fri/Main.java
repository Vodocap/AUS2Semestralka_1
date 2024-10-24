package sk.uniza.fri;


public class Main {

    public static void main(String[] args) {
        TestTrieda testTrieda = new TestTrieda(2);
        testTrieda.naplnStromAVypis(20, false);

        //testTrieda.naplnStromAVypis(20, true);
        testTrieda.deletujAVypisSkontroluj(5);
        testTrieda.skontrolujStrom();
        //testTrieda.reinsertujCelyStrom();
        //testTrieda.najdiNahodnePrvky(3);

    }
}
