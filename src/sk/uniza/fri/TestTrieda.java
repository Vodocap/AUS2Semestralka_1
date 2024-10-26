package sk.uniza.fri;

import sk.uniza.fri.aplikacia.GPSData;
import sk.uniza.fri.aplikacia.TestData;
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

    public void generatorOperacii(int pocetoperacii, boolean inty) {
        Random random = new Random();

        for (int i = 0; i < pocetoperacii; i++) {
            double cislo = random.nextDouble(0.0,1.0);
            if (cislo < 0.33) {
                if (inty) {
                    this.naplnStromAVypisInty(10);
                } else {
                    this.naplnStromAVypis(10, false);
                }
            } else if (cislo > 0.33 && cislo < 0.66) {
                if (!(this.vkladanePrvky.isEmpty())) {
                    this.deletujAVypisSkontroluj(5);
                }

            } else {
                if (this.kDStrom.getRoot() != null) {
                    this.vyvolajBalancovanie();
                }
            }
        }
        if (this.kDStrom.getRoot() != null) {
            this.vyvolajBalancovanie();
        }
        this.skontrolujStrom();
    }

    public void skontrolujStrom() {
        long najdene = 0;
        for (TrNode<IData> iDataTrNode : this.vkladanePrvky) {
            if (this.kDStrom.find(iDataTrNode.getData()) == null) {
                continue;
            } else if (this.kDStrom.find(iDataTrNode.getData()).getData().getID().equals(iDataTrNode.getData().getID())) {
                najdene++;
            }
        }

        System.out.println("Pocet hladanych " + this.vkladanePrvky.size() + " Pocet najdenych - " + najdene);
    }

    public void naplnStromAVypis(int paPocetPrvkov, boolean paAllowDuplicates) {
        Random rand = new Random(10);

        for (int i = 0; i < paPocetPrvkov; i++) {
            double[] tempPole = {rand.nextDouble(), rand.nextDouble()};
            double[] dupPole = {0.3, 0.8};
            if (paAllowDuplicates && i % 2 == 1) {
                tempPole = dupPole;
            }
            GPSData gpsData = new GPSData( tempPole);
            if (paAllowDuplicates) {
                gpsData = new GPSData(dupPole);
            }



            TrNode gpsNode = new TrNode(gpsData);
            System.out.println("Inserting Node at (" + tempPole[0] + "), (" + tempPole[1] + ")");
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

    public void vyvolajBalancovanie() {
        if (this.kDStrom.getRoot().hasRight() && !this.kDStrom.getRoot().hasLeft()) {
            this.kDStrom.rebalanceTree();
        }
    }

    public void naplnStromAVypisInty(int paPocetPrvkov) {
        Random rand = new Random(10);

        for (int i = 0; i < paPocetPrvkov; i++) {
            Integer[] tempPole = {rand.nextInt(5), rand.nextInt(5)};
            TestData gpsData = new TestData( tempPole);
            TrNode testnode = new TrNode(gpsData);
            this.vkladanePrvky.add(testnode);
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
            TrNode<IData> vkladanyNode = this.vkladanePrvky.get(rand_index);
            System.out.println("Deleting Node at (" + vkladanyNode.getData().getDataAtD(0) + "), (" + vkladanyNode.getData().getDataAtD(1) + ")");
            this.kDStrom.delete(this.vkladanePrvky.get(rand_index).getData());
            this.vkladanePrvky.remove(this.vkladanePrvky.get(rand_index));

        }
//        this.skontrolujStrom();
//        int index = 0;
//        for (TrNode<IData> doubleTrNode : this.vkladanePrvky) {
//            System.out.println("Index: " + index + " Prvok");
//            doubleTrNode.getData().printData();
//            index++;
//        }
//        System.out.println("____________________________________________________________________");
//        System.out.println("Kontrolna prejliadka");
//        this.kDStrom.inOrderOrFindMinMaxOrInsertSubtree(this.kDStrom.getRoot(), false, false, true);
//        this.dajPocetPrvkovVKontrolnomErejLitse();
    }

    public void najdiNahodnePrvky(int pocetNahodnychPrvkov) {
        Random rand = new Random(10);
        for (int i = 0; i < pocetNahodnychPrvkov; i++) {
            int rand_index = rand.nextInt(this.vkladanePrvky.size());
            System.out.println("Hladane");
            this.vkladanePrvky.get(rand_index).getData().printData();
            System.out.println("najdene");
            IData gpsData = this.vkladanePrvky.get(rand_index).getData();
            this.kDStrom.find(gpsData).printNode();
        }
    }

    public void najdiSetkyPrvky () {
        Random rand = new Random(10);
        ArrayList<TrNode<IData>> zoznamDup = this.kDStrom.findAll(this.vkladanePrvky.get(rand.nextInt(this.vkladanePrvky.size())).getData());
        for (TrNode<IData> iDataTrNode : zoznamDup) {
            iDataTrNode.printNode();
            iDataTrNode.getData().printData();
        }
    }




}
