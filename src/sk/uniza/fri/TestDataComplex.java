package sk.uniza.fri;

import sk.uniza.fri.struktura.IData;
import sk.uniza.fri.struktura.TrNode;

import java.util.Random;

/**
 * 8. 10. 2024 - 14:38
 *
 * @author matus
 */
public class TestDataComplex implements IData {
    private double A;
    private String B;
    private Integer C;
    private double D;
    private String ID;
    private TrNode currentNode;

    public TestDataComplex(double a, String b, Integer c, double d) {
        Random rand = new Random();
        this.ID = String.valueOf((char) (rand.nextInt(25) + 65) + " - " + rand.nextLong());
        this.A = a;
        this.B = b;
        this.C = c;
        this.D = d;
    }

    @Override
    public int compareTo(IData paData, int dimension) {
        switch (dimension) {
            case 0:

                double porovnavajuce = this.A;
                double porovnavaneS = (double) paData.getDataAtD(dimension);
                double epsilon = 1e-9;

                if (Math.abs(porovnavaneS - porovnavajuce) < epsilon) {
                    if (Math.signum(this.B.compareTo((String) paData.getDataAtD(dimension + 1))) < 0) {
                        return -1;
                    } else if (Math.signum(this.B.compareTo((String) paData.getDataAtD(dimension + 1))) > 0) {
                        return 1;
                    } else {
                        return 0;
                    }
                } else if (porovnavajuce > porovnavaneS) {
                    return 1;
                } else {
                    return -1;
                }

            case 1:
                if ((Integer) paData.getDataAtD(dimension + 1) > this.C) {
                    return -1;
                } else if ((Integer) paData.getDataAtD(dimension + 1) < this.C) {
                    return 1;
                } else {
                    return 0;
                }


            case 2:
                double porovnavajuceD = this.D;
                double porovnavaneSD = (double) paData.getDataAtD(dimension + 1);
                double epsilonD = 1e-9;

                if (Math.abs(porovnavaneSD - porovnavajuceD) < epsilonD) {
                    return 0;
                } else if (porovnavajuceD > porovnavaneSD) {
                    return 1;
                } else {
                    return -1;
                }

            case 3:
                if (this.B.compareTo((String) paData.getDataAtD(dimension - 2)) < 0) {
                    return -1;
                } else if (this.B.compareTo((String) paData.getDataAtD(dimension - 2)) > 0) {
                    return 1;
                } else {
                    if ((Integer) paData.getDataAtD(dimension - 1) > this.C) {
                        return -1;
                    } else if ((Integer) paData.getDataAtD(dimension - 1) < this.C) {
                        return 1;
                    } else {
                        return 0;
                    }
                }

        }
        return 0;
    }

    @Override
    public boolean equals(IData paData, boolean compareID) {
        int rovneSuradnice = 0;
        for (int i = 0; i < 4; i++) {
            if (this.compareTo(paData, i) == 0) {
                rovneSuradnice++;
            }
        }
        if (compareID) {
            if (rovneSuradnice == 4 && this.ID.equals(paData.getID())) {
                return true;
            }
        } else {
            if (rovneSuradnice == 4) {
                return true;
            }
        }
        return false;
    }

    @Override
    public Object getDataAtD(int dimension) {
        switch (dimension) {
            case 0:
                return this.A;

            case 1:
                return this.B;

            case 2:
                return this.C;

            case 3:
                return this.D;

        }
        return null;
    }

    @Override
    public void printData() {
        System.out.println(this.A + " - " + this.B + " - " + this.C + " - " + this.D + " - ");
    }

    @Override
    public IData deepCopyData(IData paData) {
        return null;
    }

    @Override
    public String getID() {
        return this.ID;
    }

    @Override
    public TrNode getCurrentNode() {
        return this.currentNode;
    }

    @Override
    public void setCurrentNode(TrNode paCurrentNode) {
        this.currentNode = paCurrentNode;
    }
}
