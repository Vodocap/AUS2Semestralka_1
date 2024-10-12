package sk.uniza.fri;


import sk.uniza.fri.aplikacia.GPSData;
import sk.uniza.fri.aplikacia.Nehnutelnost;
import sk.uniza.fri.struktura.KDTree;
import sk.uniza.fri.struktura.StromNode;

import java.util.ArrayList;
import java.util.Random;

public class Main {

    public static void main(String[] args) {
        KDTree<Double> kdTree = new KDTree<>(2);
        Random rand = new Random(10);
        ArrayList<StromNode<Double>> vlozene = new ArrayList<StromNode<Double>>();
        for (int i = 0; i < 10; i++) {
            double[] tempPole = {0.3, rand.nextDouble()};

            char[] tempPoleChar = {'N', 'E'};
            Nehnutelnost tempN = new Nehnutelnost(new GPSData(2, tempPole, tempPoleChar), 10, "Domec");
            vlozene.add(tempN);
            kdTree.insert(tempN);
        }
        kdTree.proccessAllNode(kdTree.getRoot());
        for (int i = 0; i < 4; i++) {
            int rand_index = rand.nextInt(10);
            System.out.println("Hladane");
            vlozene.get(rand_index).getData().printData();
            System.out.println("najdene");
           kdTree.find(vlozene.get(rand_index).getData()).getData().printData();
        }

    }
}
