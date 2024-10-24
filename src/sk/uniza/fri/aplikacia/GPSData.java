package sk.uniza.fri.aplikacia;

import sk.uniza.fri.struktura.IData;

import java.util.Random;

public class GPSData implements IData<Double> {
    private double[] suradnice;
    private UzemnyCelok uzemnyCelok;
    private String ID;

    

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
        } else if (porovnavaneS < porovnavajuce) {
            return 1;
        } else {
            return -1;
        }


    }


    @Override
    public boolean compareWholeTo(IData<Double> paData) {
        int rovneSuradnice = 0;
        for (int i = 0; i < this.suradnice.length; i++) {
            if (this.suradnice[i] == paData.getDataAtD(i)) {
                rovneSuradnice++;
            }
        }

        if (rovneSuradnice == this.suradnice.length && this.ID.equals(paData.getID())) {
            return true;
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
    public void swapData(IData<Double> paData) {
        paData = (GPSData) paData;
        ((GPSData) paData).setSuradnice(this.suradnice);
        ((GPSData) paData).setUzemnyCelok(this.uzemnyCelok);
        ((GPSData) paData).setID(this.ID);
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
}
