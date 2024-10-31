package sk.uniza.fri;

import sk.uniza.fri.aplikacia.GPSData;
import sk.uniza.fri.aplikacia.TestData;
import sk.uniza.fri.struktura.IData;
import sk.uniza.fri.struktura.KDTree;
import sk.uniza.fri.struktura.TrNode;

import java.nio.charset.Charset;
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

    public void generatorOperacii(int pocetoperacii, boolean inty, boolean testData) {
        for (int i = 0; i < 100000; i++) {
            Random random = new Random(i);
            System.out.println("__________________________ SEED: (" + i + ") __________________________");
            this.kDStrom = new KDTree<IData>(2);
            this.vkladanePrvky = new ArrayList<TrNode<IData>>();
            for (int j = 0; j < pocetoperacii; j++) {
                double cislo = random.nextDouble(0.0,1.0);
                if (cislo < 0.5) {
                    if (inty) {
                        this.naplnStromAVypisInty(1);
                    } else {
                        if (testData) {
                            this.naplnStromAVypisTestTrieda(1);
                        } else {
                            this.naplnStromAVypis(1, false);
                        }

                    }
                } else if (cislo > 0.5 ) {
                    if (!(this.vkladanePrvky.isEmpty())) {
                        this.deletujAVypisSkontroluj(1);
                    }

                }
            }
            this.skontrolujStrom();
        }

    }


    public void skontrolujStrom() {
        long najdene = 0;
        System.out.println("VKLADANE PRVKY:");
        for (TrNode<IData> iDataTrNode : this.vkladanePrvky) {
            System.out.println(iDataTrNode.getData().toString());
        }
        System.out.println("STROM");
        for (TrNode<IData> trNode : this.kDStrom.inorderMorris(this.kDStrom.getRoot())) {
            System.out.println(trNode.getData().toString());
        }

        for (TrNode<IData> iDataTrNode : this.vkladanePrvky) {
            if (this.kDStrom.find(iDataTrNode.getData()) == null) {
                continue;
            } else if (this.kDStrom.find(iDataTrNode.getData()).getData().getID().equals(iDataTrNode.getData().getID())) {
                najdene++;
            }
        }

        System.out.println("Pocet hladanych " + this.vkladanePrvky.size() + " Pocet najdenych - " + najdene);
        if (najdene != this.vkladanePrvky.size()) {
            throw new RuntimeException("dovidenia");
        }
    }

    public void naplnStromAVypis(int paPocetPrvkov, boolean paAllowDuplicates) {
        Random rand = new Random();

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




    }
    private String vygenerujRandomString() {
        Random random = new Random();
        byte[] array = new byte[7]; // length is bounded by 7
        random.nextBytes(array);
        if (random.nextDouble() < 0.2) {
            String duplikatnyString = "duplikatnyString";
            return duplikatnyString;
        }
        String generatedString = new String(array, Charset.forName("UTF-8"));
        return generatedString;
    }

    public void naplnStromAVypisTestTrieda(int paPocetPrvkov) {
        Random rand = new Random();

        for (int i = 0; i < paPocetPrvkov; i++) {
            TestDataComplex testDataComplex = new TestDataComplex(rand.nextDouble(), this.vygenerujRandomString(), rand.nextInt(3), rand.nextDouble());
            TrNode testnode = new TrNode(testDataComplex);
            this.vkladanePrvky.add(testnode);
            this.kDStrom.insert(testDataComplex);

        }



    }

    public void naplnStromAVypisInty(int paPocetPrvkov) {
        Random rand = new Random();

        for (int i = 0; i < paPocetPrvkov; i++) {
            Integer[] tempPole = {rand.nextInt(5), rand.nextInt(5)};
            TestData gpsData = new TestData(tempPole);
            TrNode testnode = new TrNode(gpsData);
            this.vkladanePrvky.add(testnode);
            this.kDStrom.insert(gpsData);

        }
    }

    private void dajPocetPrvkovVKontrolnomErejLitse() {
        System.out.println("Pocet prvkov na kontrolu");
        System.out.println(this.vkladanePrvky.size());
        System.out.println("Pocet prvkov v strome");
        this.kDStrom.findExtremeOrDuplicates(this.kDStrom.getRoot(),this.kDStrom.getRoot() ,false, false, false);
    }

    public void reinsertujCelyStrom() {
        TrNode trNode = this.kDStrom.getRoot().getLeft();
        trNode.setParent(null);
        this.kDStrom.findExtremeOrDuplicates(trNode, trNode ,true, true, false);
        System.out.println("____________________________________________________________________");
        System.out.println("Kontrolna prejliadka");
        this.kDStrom.findExtremeOrDuplicates(this.kDStrom.getRoot(), trNode,false, false, true);
    }


    public void deletujAVypisSkontroluj(int paPocetPrvkov) {
        Random rand = new Random();
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
        Random rand = new Random();
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

    public void testovaciePripady() {
        this.kDStrom = new KDTree<>(2);
        Integer[] integers = {0,1};
        Integer[] integers1 = {4,0};
        Integer[] integers2 = {2,1}
                ;
        Integer[] integers3 = {4,1};
        Integer[] integers4 = {2,4};

        Integer[] integers5 = {3,2}
                ;
        Integer[] integers6 = {3,3};
        Integer[] integers7 = {4,0};

        TestData testData = new TestData(integers);
        TestData testData1 = new TestData(integers1);
        TestData testData2 = new TestData(integers2);
        TestData testData3 = new TestData(integers3);
        TestData testData4 = new TestData(integers4);
        TestData testData5 = new TestData(integers5);
        TestData testData6 = new TestData(integers6);
        TestData testData7 = new TestData(integers7);


        this.kDStrom.insert(testData);
        this.kDStrom.insert(testData1);
        this.kDStrom.insert(testData2);
        this.kDStrom.delete(testData1);

        this.kDStrom.insert(testData3);
        this.kDStrom.insert(testData4);
        this.kDStrom.delete(testData);


        this.kDStrom.insert(testData7);
        this.kDStrom.delete(testData);

        this.kDStrom.delete(testData);
        this.kDStrom.insert(testData3);
        this.kDStrom.insert(testData4);
        this.kDStrom.insert(testData5);
        this.kDStrom.insert(testData6);
        this.kDStrom.delete(testData1);

        this.kDStrom.insert(testData4);
        this.kDStrom.insert(testData5);
        this.kDStrom.delete(testData);

        this.kDStrom.insert(testData6);
        this.kDStrom.delete(testData6);

        this.kDStrom.insert(testData2);
        this.kDStrom.insert(testData3);
        this.kDStrom.insert(testData4);
        this.kDStrom.insert(testData5);
        this.kDStrom.delete(testData1);
        this.kDStrom.delete(testData3);




    }




}
