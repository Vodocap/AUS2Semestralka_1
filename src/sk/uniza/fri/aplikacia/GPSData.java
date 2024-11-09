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
public class GPSData implements IData {
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

        this.suradnice = new double[2];
        this.suradnice[0] = paSuradnice[0];
        this.suradnice[1] = paSuradnice[1];
        if (this.smery[0] == 'S' && this.suradnice[0] > 0) {
            this.suradnice[0] = this.suradnice[0] * (-1);
        } else if (this.smery[0] == 'N' && this.suradnice[0] < 0) {
            this.suradnice[0] = this.suradnice[0] * (-1);
        }
        if (this.smery[1] == 'W' && this.suradnice[1] > 0) {
            this.suradnice[1] = this.suradnice[1] * (-1);
        } else if (this.smery[1] == 'E' && this.suradnice[1] < 0) {
            this.suradnice[1] = this.suradnice[1] * (-1);
        }


    }
    @Override
    public int compareTo(IData paData, int dimension) {

        switch (dimension) {
            case 0:
                double porovnavajuce = this.suradnice[dimension];
                double porovnavaneS = (Double) paData.getDataAtD(dimension);
                double epsilon = 1e-9;

                if (Math.abs(porovnavaneS - porovnavajuce) < epsilon) {
                    return 0;
                } else if (porovnavajuce > porovnavaneS) {
                    return 1;
                } else {
                    return -1;
                }

            case 1:
                if (this.smery[0] < (Character)paData.getDataAtD(1)) {
                    return 1;
                } else if (this.smery[0] > (Character)paData.getDataAtD(1)) {
                    return -1;
                } else if (this.smery[0] == (Character)paData.getDataAtD(1)) {
                    return 0;
                }

            case 2:
                porovnavajuce = this.suradnice[1];
                porovnavaneS = (Double) paData.getDataAtD(2);
                epsilon = 1e-9;

                if (Math.abs(porovnavaneS - porovnavajuce) < epsilon) {
                    return 0;
                } else if (porovnavajuce > porovnavaneS) {
                    return 1;
                } else {
                    return -1;
                }

            case 3:
                if (this.smery[1] < (Character)paData.getDataAtD(3)) {
                    return 1;
                } else if (this.smery[1] > (Character)paData.getDataAtD(3)) {
                    return -1;
                } else if (this.smery[1] == (Character)paData.getDataAtD(3)) {
                    return 0;
                }

        }
        return 2;


    }


    @Override
    public boolean equals(IData paData, boolean compareID) {
        int rovneSuradnice = 0;
        if (this.compareTo(paData, 0) == 0) {
            rovneSuradnice++;
        }
        if (this.compareTo(paData, 2) == 0) {
            rovneSuradnice++;
        }


        int rovneSmery = 0;
        if (this.compareTo(paData, 1) == 0) {
            rovneSmery++;
        }
        if (this.compareTo(paData, 3) == 0) {
            rovneSmery++;
        }

        if (compareID) {
            if (rovneSuradnice == this.suradnice.length && this.ID.equals(paData.getID()) && rovneSmery == this.smery.length) {
                return true;
            }
        } else {
            if (rovneSuradnice == this.suradnice.length && rovneSmery == this.smery.length) {
                return true;
            }
        }
        return false;
    }


    @Override
    public Object getDataAtD(int dimension) {
        switch (dimension) {
            case 0:
                return this.suradnice[0];

            case 1:
                return this.smery[0];

            case 2:
                return this.suradnice[1];

            case 3:
                return this.smery[1];

        }
        return null;
    }
    


    @Override
    public void printData() {

        System.out.println("Suradnice: ");

            if (this.smery[0] == 'N') {
                System.out.println(this.suradnice[0] + " N ");
            } else {
                System.out.println(this.suradnice[0] + " S ");
            }
            if (this.smery[1] == 'E') {
                System.out.println(this.suradnice[0] + " E ");
            } else {
                System.out.println(this.suradnice[1] + " W ");
            }



    }

    @Override
    public IData makeCopy() {
        double[] suradniceCopy;
        suradniceCopy = new double[2];
        char[] smeryCopy = new char[2];
        suradniceCopy[0] = (Double) this.getDataAtD(0);
        suradniceCopy[1] = (Double) this.getDataAtD(2);
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

    public String toString() {
        return ("Súradnice (" + this.suradnice[0] + ", " + this.suradnice[1] + ") + (" + this.smery[0] + ", " + this.smery[1] + ")");
    }
}
