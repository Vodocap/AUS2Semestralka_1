package sk.uniza.fri;


import sk.uniza.fri.aplikacia.GPSData;
import sk.uniza.fri.aplikacia.Nehnutelnost;
import sk.uniza.fri.struktura.KDTree;
import sk.uniza.fri.struktura.StromNode;

import java.util.ArrayList;
import java.util.Random;

public class Main {

    public static void main(String[] args) {
        TestTrieda testTrieda = new TestTrieda(2);
        testTrieda.naplnStrom(100);
        testTrieda.najdiNahodnePrvky(4);

    }
}
