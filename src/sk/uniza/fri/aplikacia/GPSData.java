package sk.uniza.fri.aplikacia;

import sk.uniza.fri.struktura.IData;
import sk.uniza.fri.struktura.TrNode;

public class GPSData<T> extends TrNode<Double> implements IData<Double> {
    private double[] suradnice;
    private char[] smery;
    private int pocetSuradnic;
    private T uzemnyCelok;

    public GPSData(int paPocetSuradnic, double[] paSuradnice, char[] paSmery) {
        this.pocetSuradnic = paPocetSuradnic;
        this.suradnice = paSuradnice;
        this.smery = paSmery;

    }
    @Override
    public int compareTo(IData<Double> paData, int dimension) {
        if (paData.getDataAtD(dimension) > this.suradnice[dimension]) {
            return -1;
        } else if (paData.getDataAtD(dimension) < this.suradnice[dimension]) {
            return 1;
        }

        return 0;
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

    public double getSuradnicaAtD(int dimension) {
        return this.suradnice[dimension];
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
    public IData<Double> getData() {
        return this;
    }

    @Override
    public void setDataAtD(int dimension, double value) {
        this.suradnice[dimension] = value;
    }

    public T getUzemnyCelok() {
        return this.uzemnyCelok;
    }

    public void setUzemnyCelok(T uzemnyCelok) {
        this.uzemnyCelok = uzemnyCelok;
    }

}
