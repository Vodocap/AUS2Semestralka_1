package sk.uniza.fri.aplikacia;

import sk.uniza.fri.struktura.IData;

public class GPSData implements IData<Double> {
    private double[] suradnice;
    private char[] smery;
    private int pocetSuradnic;

    public GPSData(int paPocetSuradnic, double[] paSuradnice, char[] paSmery) {
        this.pocetSuradnic = paPocetSuradnic;
        this.suradnice = paSuradnice;
        this.smery = paSmery;
    }
    @Override
    public int compareTo(IData<Double> paData, int dimension) {
        if (this.getDataAtD(paData, dimension) > this.suradnice[dimension]) {
            return -1;
        } else if (this.getDataAtD(paData, dimension) < this.suradnice[dimension]) {
            return 1;
        }
        return 0;
    }

    @Override
    public Double getDataAtD(IData<Double> paData, int dimension) {
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
}
