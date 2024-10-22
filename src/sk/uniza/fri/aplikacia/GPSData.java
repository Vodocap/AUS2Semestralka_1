package sk.uniza.fri.aplikacia;

import sk.uniza.fri.struktura.IData;
import sk.uniza.fri.struktura.TrNode;

public class GPSData extends TrNode<Double> implements IData<Double> {
    private double[] suradnice;
    private char[] smery;
    private int pocetSuradnic;
    private IUzemnyCelok uzemnyCelok;

    public GPSData(int paPocetSuradnic, double[] paSuradnice, char[] paSmery) {
        this.pocetSuradnic = paPocetSuradnic;
        this.suradnice = paSuradnice;
        this.smery = paSmery;

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
        for (int i = 0; i < this.pocetSuradnic; i++) {
            if (this.suradnice[i] == paData.getDataAtD(i)) {
                rovneSuradnice++;
            }
        }

        if (rovneSuradnice == this.pocetSuradnic) {
            return true;
        }
        return false;
    }


    @Override
    public Double getDataAtD(int dimension) {
        return this.suradnice[dimension];
    }

    public char getSmerAtD(int dimension) {
        return this.smery[dimension];
    }

    public void printData() {
        System.out.println("Suradnice: ");
        for (double suradnica : this.suradnice) {
            System.out.println(suradnica);
        }
        System.out.println("Smery: ");
        for (char smer : this.smery) {
            System.out.println(smer);
        }
    }

    @Override
    public void swapData(IData<Double> paData) {
        paData = (GPSData) paData;
        ((GPSData) paData).setPocetSuradnic(this.pocetSuradnic);
        ((GPSData) paData).setSuradnice(this.suradnice);
        ((GPSData) paData).setSmery(this.smery);
        ((GPSData) paData).setUzemnyCelok(this.uzemnyCelok);
    }


    @Override
    public IData<Double> getData() {
        return this;
    }

    @Override
    public void setDataAtD(int dimension, double value) {
        this.suradnice[dimension] = value;
    }

    public IUzemnyCelok getUzemnyCelok() {
        return this.uzemnyCelok;
    }

    public void setUzemnyCelok(IUzemnyCelok uzemnyCelok) {
        this.uzemnyCelok = uzemnyCelok;
    }

    public double[] getSuradnice() {
        return this.suradnice;
    }

    public char[] getSmery() {
        return this.smery;
    }

    private int getPocetSuradnic() {
        return this.pocetSuradnic;
    }

    public void setSmery(char[] smery) {
        this.smery = smery;
    }

    public void setPocetSuradnic(int pocetSuradnic) {
        this.pocetSuradnic = pocetSuradnic;
    }

    public void setSuradnice(double[] suradnice) {
        this.suradnice = suradnice;
    }

}
