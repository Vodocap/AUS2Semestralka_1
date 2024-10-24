package sk.uniza.fri.aplikacia;

import sk.uniza.fri.struktura.KDTree;
import sk.uniza.fri.struktura.TrNode;

import java.util.ArrayList;

public class TrControl {
    private KDTree<GPSData> stromGPSParciel;
    private KDTree<GPSData> stromGPSNehnutelnosti;
    private KDTree<GPSData> stromUzemnychCelkov;
    public ArrayList najdiVsetkyNehnutelnosti(double poziciaX, double poziciaY) {
        double[] tempSuradnice = {poziciaX, poziciaY};
        GPSData tempData = new GPSData(tempSuradnice);
        ArrayList<TrNode<GPSData>> tempList = this.stromGPSNehnutelnosti.findAll(tempData);
        ArrayList<Nehnutelnost> resultList = new ArrayList<>();
        for (TrNode<GPSData> gpsDataTrNode : tempList) {
            resultList.add((Nehnutelnost) gpsDataTrNode.getData().getUzemnyObjekt());
        }
        return resultList;
    }

    public ArrayList najdiVsetkyParcely(double poziciaX, double poziciaY) {
        double[] tempSuradnice = {poziciaX, poziciaY};
        GPSData tempData = new GPSData(tempSuradnice);
        ArrayList<TrNode<GPSData>> tempList = this.stromGPSParciel.findAll(tempData);
        ArrayList<Parcela> resultList = new ArrayList<>();
        for (TrNode<GPSData> gpsDataTrNode : tempList) {
            resultList.add((Parcela) gpsDataTrNode.getData().getUzemnyObjekt());
        }
        return resultList;
    }

    public ArrayList najdiVsetkyObjekty(double poziciaX, double poziciaY) {
        double[] tempSuradnice = {poziciaX, poziciaY};
        GPSData tempData = new GPSData(tempSuradnice);
        ArrayList<TrNode<GPSData>> tempList = this.stromUzemnychCelkov.findAll(tempData);
        ArrayList<UzemnyCelok> resultList = new ArrayList<>();
        for (TrNode<GPSData> gpsDataTrNode : tempList) {
            resultList.add(gpsDataTrNode.getData().getUzemnyObjekt());
        }
        return resultList;
    }

    public void pridajNehnutelnost(int paCislo,String paPopis, double[] paSuradnice) {
        double[] poleSirka = {paSuradnice[0], paSuradnice[1]};
        double[] poleDlzka = {paSuradnice[2], paSuradnice[3]};
        GPSData sirkoveData = new GPSData(poleSirka);
        GPSData dlzkoveData = new GPSData(poleDlzka);
        Nehnutelnost pridavanaNehnutelnost = new Nehnutelnost(paCislo, paPopis, sirkoveData, dlzkoveData);
        sirkoveData.setUzemnyObjekt(pridavanaNehnutelnost);
        dlzkoveData.setUzemnyObjekt(pridavanaNehnutelnost);
        this.stromGPSNehnutelnosti.insert(sirkoveData);
        this.stromGPSNehnutelnosti.insert(dlzkoveData);
        this.stromUzemnychCelkov.insert(sirkoveData);
        this.stromUzemnychCelkov.insert(dlzkoveData);
    }

    public void pridajParcelu(int paCislo,String paPopis, double[] paSuradnice) {
        double[] poleSirka = {paSuradnice[0], paSuradnice[1]};
        double[] poleDlzka = {paSuradnice[2], paSuradnice[3]};
        GPSData sirkoveData = new GPSData(poleSirka);
        GPSData dlzkoveData = new GPSData(poleDlzka);
        Nehnutelnost pridavanaParcela = new Nehnutelnost(paCislo, paPopis, sirkoveData, dlzkoveData);
        sirkoveData.setUzemnyObjekt(pridavanaParcela);
        dlzkoveData.setUzemnyObjekt(pridavanaParcela);
        this.stromGPSParciel.insert(sirkoveData);
        this.stromGPSParciel.insert(dlzkoveData);
        this.stromUzemnychCelkov.insert(sirkoveData);
        this.stromUzemnychCelkov.insert(dlzkoveData);
    }


}
