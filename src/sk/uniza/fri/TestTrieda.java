package sk.uniza.fri;

import sk.uniza.fri.aplikacia.GPSData;
import sk.uniza.fri.aplikacia.Nehnutelnost;
import sk.uniza.fri.struktura.KDTree;
import sk.uniza.fri.struktura.TrNode;

import java.util.ArrayList;
import java.util.Random;

/**
 * 8. 10. 2024 - 14:38
 *
 * @author matus
 */
public class TestTrieda {

    private ArrayList<TrNode<Double>> vkladanePrvky;
    private KDTree<Double> kDStrom;

    public TestTrieda (int paPocetDimenziVStrome) {
        this.kDStrom = new KDTree<Double>(paPocetDimenziVStrome);
        this.vkladanePrvky = new ArrayList<TrNode<Double>>();
    }

    public void naplnStromAVypis(int paPocetPrvkov, boolean paAllowDuplicates) {
        Random rand = new Random(10);

        for (int i = 0; i < paPocetPrvkov; i++) {
            double[] tempPole = {rand.nextDouble(), rand.nextDouble()};
            double[] dupPole = {0.3, rand.nextDouble()};
            if (paAllowDuplicates && i % 2 == 1) {
                tempPole = dupPole;
            }
            char[] tempPoleChar = {'N', 'E'};
            Nehnutelnost tempN = new Nehnutelnost(new GPSData(2, tempPole, tempPoleChar), 10, "Nehnutelnost");
            this.vkladanePrvky.add(tempN);
            this.kDStrom.insert(tempN);
        }
        this.kDStrom.proccessAllNode(this.kDStrom.getRoot());
    }

    public void najdiNahodnePrvky(int pocetNahodnychPrvkov) {
        Random rand = new Random(10);
        for (int i = 0; i < pocetNahodnychPrvkov; i++) {
            int rand_index = rand.nextInt(10);
            System.out.println("Hladane");
            this.vkladanePrvky.get(rand_index).getData().printData();
            System.out.println("najdene");
            this.kDStrom.find(this.vkladanePrvky.get(rand_index).getData()).getData().printData();
        }
    }




}
