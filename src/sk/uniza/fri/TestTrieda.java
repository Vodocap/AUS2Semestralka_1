package sk.uniza.fri;

import sk.uniza.fri.aplikacia.GPSData;
import sk.uniza.fri.aplikacia.GPSNode;
import sk.uniza.fri.struktura.IData;
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

    private ArrayList<TrNode<IData>> vkladanePrvky;
    private KDTree<IData> kDStrom;

    public TestTrieda (int paPocetDimenziVStrome) {
        this.kDStrom = new KDTree<IData>(paPocetDimenziVStrome);
        this.vkladanePrvky = new ArrayList<TrNode<IData>>();
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
            GPSData gpsData = new GPSData(2, tempPole, tempPoleChar);


            GPSNode gpsNode = new GPSNode<>(gpsData);
            this.vkladanePrvky.add(gpsNode);
            this.kDStrom.insert(gpsData);
            }
//        TrNode<Double> testNode = this.kDStrom.getRoot();
////        TrNode<Double> resultNodeMax = this.kDStrom.inOrderOrFindMinMaxOrInsertSubtree(testNode, true, false, false);
////        TrNode<Double> resultNodeMin = this.kDStrom.inOrderOrFindMinMaxOrInsertSubtree(testNode, false, false, false);
////        System.out.println("Maxnode");
////        resultNodeMax.printNode();
////        resultNodeMax.getData().printData();
////        System.out.println("Minnode");
////        resultNodeMin.printNode();
////        resultNodeMin.getData().printData();


    }

    private void dajPocetPrvkovVKontrolnomErejLitse() {
        System.out.println("Pocet prvkov na kontrolu");
        System.out.println(this.vkladanePrvky.size());
        System.out.println("Pocet prvkov v strome");
        this.kDStrom.inOrderOrFindMinMaxOrInsertSubtree(this.kDStrom.getRoot(), false, false, false);
    }

    public void reinsertujCelyStrom() {
        TrNode trNode = this.kDStrom.getRoot().getLeft();
        trNode.setParent(null);
        this.kDStrom.inOrderOrFindMinMaxOrInsertSubtree(trNode, true, true, false);
        System.out.println("____________________________________________________________________");
        System.out.println("Kontrolna prejliadka");
        this.kDStrom.inOrderOrFindMinMaxOrInsertSubtree(this.kDStrom.getRoot(), false, false, true);
    }


    public void deletujAVypisSkontroluj(int paPocetPrvkov) {
        Random rand = new Random(10);
        for (int i = 0; i < paPocetPrvkov; i++) {
            int rand_index = rand.nextInt(this.vkladanePrvky.size());
            this.kDStrom.delete(this.vkladanePrvky.get(rand_index).getData());
            this.vkladanePrvky.remove(this.vkladanePrvky.get(rand_index));
        }
        int index = 0;
        for (TrNode<IData> doubleTrNode : this.vkladanePrvky) {
            System.out.println("Index: " + index + " Prvok");
            doubleTrNode.getData().printData();
            index++;
        }
        System.out.println("____________________________________________________________________");
        System.out.println("Kontrolna prejliadka");
        this.kDStrom.inOrderOrFindMinMaxOrInsertSubtree(this.kDStrom.getRoot(), false, false, true);
        this.dajPocetPrvkovVKontrolnomErejLitse();
    }

    public void najdiNahodnePrvky(int pocetNahodnychPrvkov) {
        Random rand = new Random(10);
        for (int i = 0; i < pocetNahodnychPrvkov; i++) {
            int rand_index = rand.nextInt(this.vkladanePrvky.size());
            System.out.println("Hladane");
            this.vkladanePrvky.get(rand_index).getData().printData();
            System.out.println("najdene");
            this.kDStrom.find(this.vkladanePrvky.get(rand_index).getData()).printNode();
        }
    }




}
