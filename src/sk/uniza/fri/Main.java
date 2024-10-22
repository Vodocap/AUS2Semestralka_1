package sk.uniza.fri;


public class Main {

    public static void main(String[] args) {
        TestTrieda testTrieda = new TestTrieda(2);
        testTrieda.naplnStromAVypis(100, false);
        testTrieda.deletujAVypisSkontroluj(50);
        //testTrieda.najdiNahodnePrvky(3);

    }
}
