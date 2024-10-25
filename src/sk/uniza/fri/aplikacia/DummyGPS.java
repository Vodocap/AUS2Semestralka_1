package sk.uniza.fri.aplikacia;

import sk.uniza.fri.struktura.IData;

public class DummyGPS implements IData {
    private double surX;
    private double surY;

    public DummyGPS(double surY, double surX) {
        this.surY = surY;
        this.surX = surX;
    }

    @Override
    public int compareTo(IData paData, int dimension) {
        return 0;
    }

    @Override
    public boolean compareWholeTo(IData paData, boolean compareID) {
        return false;
    }

    @Override
    public Object getDataAtD(int dimension) {
        return null;
    }

    @Override
    public void printData() {

    }

    @Override
    public void deepSwapData(IData paData) {

    }

    @Override
    public String getID() {
        return "";
    }
}
