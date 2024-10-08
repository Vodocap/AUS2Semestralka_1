package sk.uniza.fri;

/**
 * 8. 10. 2024 - 14:38
 *
 * @author matus
 */
public class Kluce {
    private double[] kluce;
    private int aktIndex;
    public Kluce(int paPocetKlucov) {
        this.kluce = new double[paPocetKlucov];
        this.aktIndex = 0;
    }
    public boolean pridajKluc(double paKluc) {
        if (this.aktIndex < this.kluce.length) {
            this.kluce[this.aktIndex] = paKluc;
            this.aktIndex++;
            return true;
        }
        System.out.println("Uz sa nedaju pridat dalsie kluce");
        return false;

    }

    public double getKlucNaI(int i) {
        return this.kluce[i];
    }
}
