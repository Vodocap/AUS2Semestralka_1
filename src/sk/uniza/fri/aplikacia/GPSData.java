package sk.uniza.fri.aplikacia;

import sk.uniza.fri.struktura.IData;
import sk.uniza.fri.struktura.TrNode;

import java.util.Random;


/**
 * Trieda reprezentujúca súradnicu parcely,
 * udržuje si informácie o súčastnom node,
 * Unikátnom ID atribúte,
 * územnom objekte ktorému prislúcha,
 * samotných súradniciach a ich smeroch.
 */
public class GPSData implements IData<Double> {
    private double[] suradnice;
    private UzemnyCelok uzemnyObjekt;
    private String ID;
    private TrNode currentNode;
    private char[] smery;

    

    public GPSData(double[] paSuradnice, char[] paSmery) {
        this.smery = new char[2];
        this.smery[0] = paSmery[0];
        this.smery[1] = paSmery[1];
        Random rand = new Random();
        this.ID = String.valueOf((char) (rand.nextInt(25) + 65) + " - " + rand.nextLong());
        this.suradnice = new double[2];
        if (paSmery[0] == 'N') {
            this.suradnice[0] = paSuradnice[0];
        } else {
            this.suradnice[0] = -1 * paSuradnice[0];
            if (paSuradnice[0] < 0) {
                this.suradnice[0] = paSuradnice[0];
            }
        }
        if (paSmery[1] == 'E') {
            this.suradnice[1] = paSuradnice[1];
        } else {
            this.suradnice[1] = -1 * paSuradnice[1];
            if (paSuradnice[1] < 0) {
                this.suradnice[1] = paSuradnice[1];
            }

        }



    }
    @Override
    public int compareTo(IData<Double> paData, int dimension) {
        double porovnavajuce = this.suradnice[dimension];
        double porovnavaneS = paData.getDataAtD(dimension);
        double epsilon = 1e-9;

        if (Math.abs(porovnavaneS - porovnavajuce) < epsilon) {
            return 0;
        } else if (porovnavajuce > porovnavaneS) {
            return 1;
        } else {
            return -1;
        }


    }


    @Override
    public boolean equals(IData<Double> paData, boolean compareID) {
        int rovneSuradnice = 0;
        for (int i = 0; i < this.suradnice.length; i++) {
            if (this.compareTo(paData, i) == 0) {
                rovneSuradnice++;
            }
        }
        if (compareID) {
            if (rovneSuradnice == this.suradnice.length && this.ID.equals(paData.getID())) {
                return true;
            }
        } else {
            if (rovneSuradnice == this.suradnice.length) {
                return true;
            }
        }
        return false;
    }


    @Override
    public Double getDataAtD(int dimension) {
        return this.suradnice[dimension];
    }

    @Override
    public void printData() {
        System.out.println("ID: " + this.ID);

        System.out.println("Suradnice: ");

            if (this.smery[0] == 'N') {
                System.out.println(this.suradnice[0] + " N ");
            } else {
                System.out.println(-1 * this.suradnice[0] + " S ");
            }
            if (this.smery[1] == 'E') {
                System.out.println(this.suradnice[0] + " E ");
            } else {
                System.out.println(-1 * this.suradnice[1] + " W ");
            }
            System.out.println(this.suradnice[0]);



    }

    @Override
    public IData<Double> makeCopy() {
        double[] suradniceCopy;
        suradniceCopy = new double[2];
        char[] smeryCopy = new char[2];
        suradniceCopy[0] = this.getDataAtD(0);
        suradniceCopy[1] = this.getDataAtD(1);
        smeryCopy[0] = this.getSmerAtD(0);
        smeryCopy[1] = this.getSmerAtD(1);
        GPSData copiedData = new GPSData(suradniceCopy, smeryCopy);
        copiedData.setID(this.getID());


        return copiedData;
    }

    @Override
    public String getID() {
        return this.ID;
    }
    public void setID(String ID) {
        this.ID = ID;
    }


    public double[] getSuradnice() {
        return this.suradnice;
    }



    private int getPocetSuradnic() {
        return this.suradnice.length;
    }



    public void setSuradnice(double[] suradnice) {
        this.suradnice = suradnice;
    }

    public UzemnyCelok getUzemnyObjekt() {
        return uzemnyObjekt;
    }

    public void setUzemnyObjekt(UzemnyCelok uzemnyCelok) {
        this.uzemnyObjekt = uzemnyCelok;
    }

    public TrNode getCurrentNode() {
        return currentNode;
    }

    public void setCurrentNode(TrNode currentNode) {
        this.currentNode = currentNode;
    }


    public void setSmery(char[] smery) {
        this.smery = smery;
    }

    public char getSmerAtD(int dimension) {
        return this.smery[dimension];
    }

    public void setSmerAtD(int dimension, char smer) {
        this.smery[dimension] = smer;
    }
}
