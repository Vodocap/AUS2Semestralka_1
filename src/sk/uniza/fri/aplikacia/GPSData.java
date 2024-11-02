package sk.uniza.fri.aplikacia;

import sk.uniza.fri.struktura.IData;
import sk.uniza.fri.struktura.TrNode;

import java.util.Random;

public class GPSData implements IData<Double> {
    private double[] suradnice;
    private UzemnyCelok uzemnyCelok;
    private String ID;
    private TrNode currentNode;

    

    public GPSData( double[] paSuradnice) {
        Random rand = new Random();
        this.ID = String.valueOf((char) (rand.nextInt(25) + 65) + " - " + rand.nextLong());
        this.suradnice = paSuradnice;


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
        for (double suradnica : this.suradnice) {
            System.out.println(suradnica);
        }
        System.out.println("Smery: ");

    }

    @Override
    public IData<Double> deepCopyData(IData<Double> paData) {
        paData = (GPSData) paData;
        double[] tempSuradnice = ((GPSData) paData).getSuradnice();
        UzemnyCelok tempUzemnyCelok = ((GPSData) paData).getUzemnyCelok();
        String tempID = paData.getID();
        double[] suradniceCopy;
        suradniceCopy = new double[2];
        suradniceCopy[0] = paData.getDataAtD(0);
        suradniceCopy[1] = paData.getDataAtD(1);

        GPSData copiedData = new GPSData(suradniceCopy);
        copiedData.setID(paData.getID());
        if (((GPSData) paData).getUzemnyObjekt() instanceof Parcela) {

//            copiedData.setUzemnyObjekt(new Parcela());
        }

        ((GPSData) paData).setSuradnice(this.suradnice);
        ((GPSData) paData).setUzemnyCelok(this.uzemnyCelok);
        ((GPSData) paData).setID(this.ID);
        this.suradnice = tempSuradnice;
        this.uzemnyCelok = tempUzemnyCelok;
        this.ID = tempID;

        return copiedData;
    }

    @Override
    public String getID() {
        return this.ID;
    }
    public void setID(String ID) {
        this.ID = ID;
    }

    public UzemnyCelok getUzemnyCelok() {
        return this.uzemnyCelok;
    }

    public void setUzemnyCelok(UzemnyCelok uzemnyCelok) {
        this.uzemnyCelok = uzemnyCelok;
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
        return uzemnyCelok;
    }

    public void setUzemnyObjekt(UzemnyCelok uzemnyCelok) {
        this.uzemnyCelok = uzemnyCelok;
    }

    public TrNode getCurrentNode() {
        return currentNode;
    }

    public void setCurrentNode(TrNode currentNode) {
        this.currentNode = currentNode;
    }
}
