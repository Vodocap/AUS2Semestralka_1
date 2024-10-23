package sk.uniza.fri.aplikacia;

import sk.uniza.fri.struktura.TrNode;
import sk.uniza.fri.aplikacia.GPSData;

public class GPSNode<GPSData> extends TrNode<GPSData> {


    public GPSNode(GPSData data) {
        super(data);
    }

    @Override
    public void printNode() {
        System.out.println("Node at level: " + super.getLevel());
        System.out.println("Containing Data: ");
        GPSData gpsData = (GPSData) super.getData();
        // treba printdata

    }
}
