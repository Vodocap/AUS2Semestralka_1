package sk.uniza.fri.testovanie;

import sk.uniza.fri.aplikacia.GPSData;
import sk.uniza.fri.aplikacia.IDGenerator;
import sk.uniza.fri.struktura.IData;
import sk.uniza.fri.struktura.KDTree;
import sk.uniza.fri.struktura.TrNode;

import java.awt.image.AreaAveragingScaleFilter;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Random;

/**
 * 8. 10. 2024 - 14:38
 *
 * @author matus
 *
 *
 */
public class TestTrieda {


    private IDGenerator generator;
    private ArrayList<TrNode<IData>> vkladanePrvky;
    private KDTree<IData> kDStrom;

    public TestTrieda (int paPocetDimenziVStrome) {
        this.generator = new IDGenerator();
        this.kDStrom = new KDTree<IData>(paPocetDimenziVStrome);
        this.vkladanePrvky = new ArrayList<TrNode<IData>>();
    }

    public void generatorOperacii(int pocetoperacii, boolean inty, boolean testData) {
        this.kDStrom = new KDTree<IData>(2);
        if (!inty && !testData) {
            this.kDStrom = new KDTree<IData>(4);
        }
        this.vkladanePrvky = new ArrayList<TrNode<IData>>();
        if (inty) {
            for (int i = 0; i < 20000; i++) {
                this.naplnStromAVypisInty(1);
            }
        } else if (!testData) {
            for (int i = 0; i < 20000; i++) {
                this.naplnStromAVypis(1,false);
            }
        }

        for (int i = 0; i < 1; i++) {
            Random random = new Random();
//            System.out.println("__________________________ SEED: (" + i + ") __________________________");

            if (testData) {
                this.kDStrom = new KDTree<IData>(4);
            }

            for (int j = 0; j < pocetoperacii; j++) {
                double cislo = random.nextDouble(0.0,1.0);
                if (cislo < 0.33) {
                    if (inty) {
                        this.naplnStromAVypisInty(1);
                        this.skontrolujStrom();
                    } else {
                        if (testData) {
                            this.naplnStromAVypisTestTrieda(1);
                        } else {
                            this.naplnStromAVypis(1, false);
                        }

                    }
                    this.skontrolujStrom();
                } else if (cislo > 0.33 && cislo < 0.66) {
                    if (!(this.vkladanePrvky.isEmpty())) {
                        this.deletujAVypisSkontroluj(1);
                        this.skontrolujStrom();
                    }

                } else if (cislo > 0.66) {
                    if (this.vkladanePrvky.size() < 1) {
                        break;
                    }
                    IData hladaneData = this.vkladanePrvky.get(random.nextInt(this.vkladanePrvky.size())).getData();
                    ArrayList<IData> hladaneDataControl = new ArrayList<>();
                    for (TrNode<IData> iDataTrNode : this.vkladanePrvky) {
                        if (hladaneData.equals(iDataTrNode.getData(), false)) {
                            hladaneDataControl.add(iDataTrNode.getData());
                        }
                    }

                    ArrayList<TrNode<IData>> vysledneNody = this.kDStrom.findAll(hladaneData);
                    ArrayList<IData> vysledneData = new ArrayList<>();
                    for (TrNode<IData> iDataTrNode : vysledneNody) {
                        vysledneData.add(iDataTrNode.getData());
                    }

                    int hldane = 0;
                    for (IData iData : hladaneDataControl) {
                        if (vysledneData.contains(iData)) {
                            hldane++;
                        } else {
                            throw new RuntimeException("Nenasiel sa hladany prvok");
                        }
                    }
                    if (hldane != hladaneDataControl.size()) {
                        throw new RuntimeException("Nenasli sa vsetky prvky co sa mali");
                    }

                }
            }
            this.skontrolujStrom();
        }

    }


    public void skontrolujStrom() {
        long najdene = 0;
//        System.out.println("VKLADANE PRVKY:");
//        for (TrNode<IData> iDataTrNode : this.vkladanePrvky) {
//            System.out.println(iDataTrNode.getData().toString());
//        }
//        System.out.println("STROM");
//        for (TrNode<IData> trNode : this.kDStrom.inorder(this.kDStrom.getRoot())) {
//            System.out.println(trNode.getData().toString());
//        }

        for (TrNode<IData> iDataTrNode : this.vkladanePrvky) {
            if (this.kDStrom.find(iDataTrNode.getData()) == null) {
                continue;
            } else if (this.kDStrom.find(iDataTrNode.getData()).getData().getID().equals(iDataTrNode.getData().getID())) {
                najdene++;
            }
        }

        System.out.println("Pocet hladanych " + this.vkladanePrvky.size() + " Pocet najdenych - " + najdene);
        if (najdene != this.vkladanePrvky.size()) {
            throw new RuntimeException("Nepodarilo sa nájsť všetky kontrolné prvky");
        }
    }

    public void naplnStromAVypis(int paPocetPrvkov, boolean paAllowDuplicates) {
        Random random = new Random();


        for (int i = 0; i < paPocetPrvkov; i++) {
            double[] tempPole = {random.nextDouble(0, 90), random.nextDouble(0, 90)};
            double[] dupPole = {0.3, 0.8};

            char[] dupChary = {'N', 'E'};
            char[] randChary = {(char) (78 + (random.nextInt(2) * 5)), (char) (69 + (random.nextInt(2) * 18)),
                    (char) (78 + (random.nextInt(2) * 5)), (char) (69 + (random.nextInt(2) * 18))};
            double[] suradnice = {Math.round(random.nextDouble(-90, 90)*100.0)/100.0, Math.round(random.nextDouble(-90, 90)*100.0)/100.0,
                    Math.round(random.nextDouble(-90, 90)*100.0)/100.0, Math.round(random.nextDouble(-90, 90)*100.0)/100.0};
            if (paAllowDuplicates && i % 2 == 1) {
                tempPole = dupPole;
            }
            GPSData gpsData = new GPSData( suradnice, randChary);

            if (paAllowDuplicates) {
                gpsData = new GPSData(dupPole, dupChary);
            }
            gpsData.setID(this.generator.generateUniqueID());



            TrNode gpsNode = new TrNode(gpsData);
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
            TestDataComplex testDataComplex = new TestDataComplex(rand.nextDouble(), this.vygenerujRandomString(), rand.nextInt(5), rand.nextDouble());
            TrNode testnode = new TrNode(testDataComplex);
            this.vkladanePrvky.add(testnode);
            this.kDStrom.insert(testDataComplex);

        }



    }

    public void naplnStromAVypisInty(int paPocetPrvkov) {
        Random rand = new Random();

        for (int i = 0; i < paPocetPrvkov; i++) {
            Integer[] tempPole = {rand.nextInt(51), rand.nextInt(51)};
            TestData gpsData = new TestData(tempPole);
            gpsData.setID(this.generator.generateUniqueID());
            gpsData.printData();
            TrNode testnode = new TrNode(gpsData);
            this.vkladanePrvky.add(testnode);
            this.kDStrom.insert(gpsData);

        }
    }



    public void deletujAVypisSkontroluj(int paPocetPrvkov) {
        Random rand = new Random();
        for (int i = 0; i < paPocetPrvkov; i++) {
            int rand_index = rand.nextInt(this.vkladanePrvky.size());
            TrNode<IData> vkladanyNode = this.vkladanePrvky.get(rand_index);
            System.out.println("DELETING NODE AT (" + vkladanyNode.getData().toString() + ")");
            this.kDStrom.delete(this.vkladanePrvky.get(rand_index).getData());
            this.vkladanePrvky.remove(this.vkladanePrvky.get(rand_index));

        }
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


    public void testovaciePripady() {
        this.kDStrom = new KDTree<>(2);
        Integer[] integers = {0,0};
        Integer[] integers1 = {2,1};
        Integer[] integers2 = {2,3};
        Integer[] integers3 = {2,1};
        Integer[] integers4 = {3,3};
        Integer[] integers5 = {3,2};
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
        this.kDStrom.insert(testData3);
        this.kDStrom.insert(testData4);
        this.kDStrom.insert(testData5);
        this.kDStrom.insert(testData6);
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
