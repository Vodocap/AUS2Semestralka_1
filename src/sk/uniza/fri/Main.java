package sk.uniza.fri;


import sk.uniza.fri.aplikacia.GPSData;
import sk.uniza.fri.aplikacia.Nehnutelnost;
import sk.uniza.fri.struktura.KDTree;

import java.util.Random;

public class Main {

    public static void main(String[] args) {
        KDTree<Double> kdTree = new KDTree<>(2);
        Random rand = new Random(10);

        kdTree.insert(new Nehnutelnost(new GPSData(2,)))
    }
}
