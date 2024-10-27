package sk.uniza.fri.aplikacia;

import sk.uniza.fri.struktura.IData;

import java.util.Random;

/**
 * 8. 10. 2024 - 14:38
 *
 * @author matus
 */

public class TestData implements IData<Integer> {

    private Integer[] suradnice;
    private String ID;
    public TestData( Integer[] ciselka) {
        Random rand = new Random();
        this.ID = String.valueOf((char) (rand.nextInt(25) + 65) + " - " + rand.nextLong());
        this.suradnice = ciselka;


    }
    @Override
    public int compareTo(IData<Integer> paData, int dimension) {
        double porovnavajuce = this.suradnice[dimension];
        double porovnavaneS = paData.getDataAtD(dimension);
        if (porovnavaneS == porovnavajuce) {
            return 0;
        } else if (porovnavajuce > porovnavaneS) {
            return 1;
        } else {
            return -1;
        }
    }

    @Override
    public boolean compareWholeTo(IData<Integer> paData, boolean compareID) {
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
    public Integer getDataAtD(int dimension) {
        return this.suradnice[dimension];
    }

    @Override
    public void printData() {
        for (Integer i : this.suradnice) {
            System.out.println("Datka - " + i);
        }
    }

    @Override
    public void deepSwapData(IData<Integer> paData) {
        return;
    }

    @Override
    public String getID() {
        return this.ID;
    }
}