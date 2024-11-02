package sk.uniza.fri.aplikacia;

import sk.uniza.fri.struktura.KDTree;
import sk.uniza.fri.struktura.TrNode;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;

public class TrControl {
    private KDTree<GPSData> stromGPSParciel;
    private KDTree<GPSData> stromGPSNehnutelnosti;
    private KDTree<GPSData> stromUzemnychCelkov;
    public TrControl () {
        this.stromGPSParciel = new KDTree<GPSData>(2);
        this.stromGPSNehnutelnosti = new KDTree<GPSData>(2);
        this.stromUzemnychCelkov = new KDTree<GPSData>(2);
    }
    public ArrayList<Nehnutelnost> najdiVsetkyNehnutelnosti(double poziciaX, double poziciaY, char charX, char charY) {
        double[] tempSuradnice = {poziciaX, poziciaY};
        char[] tempChary = {charX, charY};
        GPSData tempData = new GPSData(tempSuradnice, tempChary);
        ArrayList<TrNode<GPSData>> tempList = this.stromGPSNehnutelnosti.findAll(tempData);
        ArrayList<Nehnutelnost> rawResult = new ArrayList<>();

        for (TrNode<GPSData> gpsDataTrNode : tempList) {
            if (!rawResult.contains((Nehnutelnost) gpsDataTrNode.getData().getUzemnyObjekt())) {
                rawResult.add((Nehnutelnost) gpsDataTrNode.getData().getUzemnyObjekt());
            }

        }

        ArrayList<Nehnutelnost> copyResult = new ArrayList<>();
        for (Nehnutelnost nehnutelnost : rawResult) {
            Nehnutelnost returnNehnutelnost = (Nehnutelnost) nehnutelnost.makeCopy();
            GPSData returnSirka = (GPSData) nehnutelnost.getSirka().makeCopy();
            GPSData returnDlzka = (GPSData) nehnutelnost.getDlzka().makeCopy();
            returnSirka.setUzemnyObjekt(returnNehnutelnost);
            returnDlzka.setUzemnyObjekt(returnNehnutelnost);
            returnNehnutelnost.setDlzka(returnSirka);
            returnNehnutelnost.setSirka(returnDlzka);
            returnNehnutelnost.setStringObjektov(nehnutelnost.toStringObjektov());
            copyResult.add(returnNehnutelnost);

        }

        return copyResult;
    }

    private UzemnyCelok najdiKonkretnyCelok(GPSData gpsData) {
        return this.stromUzemnychCelkov.find(gpsData).getData().getUzemnyObjekt();
    }

    public ArrayList<Parcela> najdiVsetkyParcely(double poziciaX, double poziciaY, char charX, char charY) {
        double[] tempSuradnice = {poziciaX, poziciaY};
        char[] tempChary = {charX, charY};
        GPSData tempData = new GPSData(tempSuradnice, tempChary);
        ArrayList<TrNode<GPSData>> tempList = this.stromGPSParciel.findAll(tempData);
        ArrayList<Parcela> rawResult = new ArrayList<>();

        for (TrNode<GPSData> gpsDataTrNode : tempList) {
            if (!rawResult.contains((Parcela) gpsDataTrNode.getData().getUzemnyObjekt())) {
                rawResult.add((Parcela) gpsDataTrNode.getData().getUzemnyObjekt());
            }

        }

        ArrayList<Parcela> copyResult = new ArrayList<>();
        for (Parcela parcela : rawResult) {
            Parcela returnParcela = (Parcela) parcela.makeCopy();
            GPSData returnSirka = (GPSData) parcela.getSirka().makeCopy();
            GPSData returnDlzka = (GPSData) parcela.getDlzka().makeCopy();
            returnSirka.setUzemnyObjekt(returnParcela);
            returnDlzka.setUzemnyObjekt(returnParcela);
            returnParcela.setDlzka(returnSirka);
            returnParcela.setSirka(returnDlzka);
            returnParcela.setStringObjektov(parcela.toStringObjektov());

            copyResult.add(returnParcela);

        }

        return copyResult;
    }

    public ArrayList<UzemnyCelok> najdiVsetkyObjekty(double poziciaX, double poziciaY, char charX, char charY) {
        double[] tempSuradnice = {poziciaX, poziciaY};
        char[] tempChary = {charX, charY};
        GPSData tempData = new GPSData(tempSuradnice, tempChary);
        ArrayList<TrNode<GPSData>> tempList = this.stromUzemnychCelkov.findAll(tempData);
        ArrayList<UzemnyCelok> rawResult = new ArrayList<>();

        for (TrNode<GPSData> gpsDataTrNode : tempList) {
            if (!rawResult.contains(gpsDataTrNode.getData().getUzemnyObjekt())) {
                rawResult.add(gpsDataTrNode.getData().getUzemnyObjekt());
            }

        }

        ArrayList<UzemnyCelok> copyResult = new ArrayList<>();
        for (UzemnyCelok uzemnyCelok : rawResult) {
            UzemnyCelok returnCelok = uzemnyCelok.makeCopy();
            GPSData returnSirka = (GPSData) uzemnyCelok.getSirka().makeCopy();
            GPSData returnDlzka = (GPSData) uzemnyCelok.getDlzka().makeCopy();
            returnSirka.setUzemnyObjekt(returnCelok);
            returnDlzka.setUzemnyObjekt(returnCelok);
            returnCelok.setDlzka(returnSirka);
            returnCelok.setSirka(returnDlzka);
            returnCelok.setStringObjektov(uzemnyCelok.toStringObjektov());
            copyResult.add(returnCelok);

        }

        return copyResult;
    }

    private ArrayList<UzemnyCelok> najdiVsetkyCelky(GPSData paData) {

        ArrayList<TrNode<GPSData>> tempList = this.stromUzemnychCelkov.findAll(paData);
        ArrayList<UzemnyCelok> resultList = new ArrayList<>();
        for (TrNode<GPSData> gpsDataTrNode : tempList) {
            resultList.add(gpsDataTrNode.getData().getUzemnyObjekt());
        }
        return resultList;
    }

    private void pridajPrekryvajuce(UzemnyCelok pridavanyCelok, GPSData paSirka, GPSData paDlzka) {
        ArrayList<UzemnyCelok> duplikatySirka = this.najdiVsetkyCelky(paSirka);
        ArrayList<UzemnyCelok> duplikatyDlzka = this.najdiVsetkyCelky(paDlzka);

        if (pridavanyCelok instanceof Nehnutelnost) {

            for (UzemnyCelok uzemnyCelok : duplikatySirka) {
                if (uzemnyCelok instanceof Parcela) {
                    pridavanyCelok.pridajUzemnyCelok(uzemnyCelok);
                    uzemnyCelok.pridajUzemnyCelok(pridavanyCelok);
                }
            }

            for (UzemnyCelok uzemnyCelok : duplikatyDlzka) {
                if (uzemnyCelok instanceof Parcela) {
                    pridavanyCelok.pridajUzemnyCelok(uzemnyCelok);
                    uzemnyCelok.pridajUzemnyCelok(pridavanyCelok);
                }
            }
        }
        if (pridavanyCelok instanceof Parcela) {

            for (UzemnyCelok uzemnyCelok : duplikatySirka) {
                if (uzemnyCelok instanceof Nehnutelnost) {
                    pridavanyCelok.pridajUzemnyCelok(uzemnyCelok);
                    uzemnyCelok.pridajUzemnyCelok(pridavanyCelok);
                }
            }

            for (UzemnyCelok uzemnyCelok : duplikatyDlzka) {
                if (uzemnyCelok instanceof Nehnutelnost) {
                    pridavanyCelok.pridajUzemnyCelok(uzemnyCelok);
                    uzemnyCelok.pridajUzemnyCelok(pridavanyCelok);
                }
            }
        }
    }

    public void pridajNehnutelnost(int paCislo,String paPopis, double[] paSuradnice, char[] paChary, boolean urobPrekryvy) {
        double[] poleSirka = {paSuradnice[0], paSuradnice[1]};
        double[] poleDlzka = {paSuradnice[2], paSuradnice[3]};
        char[] charySirka = {paChary[0], paChary[1]};
        char[] charyDlzka = {paChary[2], paChary[3]};
        GPSData sirkoveData = new GPSData(poleSirka, charySirka);
        GPSData dlzkoveData = new GPSData(poleDlzka, charyDlzka);
        Nehnutelnost pridavanaNehnutelnost = new Nehnutelnost(paCislo, paPopis, sirkoveData, dlzkoveData);
        sirkoveData.setUzemnyObjekt(pridavanaNehnutelnost);
        dlzkoveData.setUzemnyObjekt(pridavanaNehnutelnost);

        if (urobPrekryvy) {
            this.pridajPrekryvajuce(pridavanaNehnutelnost, sirkoveData, dlzkoveData);
            this.vyytvorVsetkyPrekryvy();
        }

        this.stromGPSNehnutelnosti.insert(sirkoveData);
        this.stromGPSNehnutelnosti.insert(dlzkoveData);
        this.stromUzemnychCelkov.insert(sirkoveData);
        this.stromUzemnychCelkov.insert(dlzkoveData);
    }

    public void pridajParcelu(int paCislo,String paPopis, double[] paSuradnice, char[] paChary, boolean urobPrekryvy) {
        double[] poleSirka = {paSuradnice[0], paSuradnice[1]};
        double[] poleDlzka = {paSuradnice[2], paSuradnice[3]};
        char[] charySirka = {paChary[0], paChary[1]};
        char[] charyDlzka = {paChary[2], paChary[3]};
        GPSData sirkoveData = new GPSData(poleSirka, charySirka);
        GPSData dlzkoveData = new GPSData(poleDlzka, charyDlzka);
        Parcela pridavanaParcela = new Parcela(paCislo, paPopis, sirkoveData, dlzkoveData);
        sirkoveData.setUzemnyObjekt(pridavanaParcela);
        dlzkoveData.setUzemnyObjekt(pridavanaParcela);
        if (urobPrekryvy) {
            this.pridajPrekryvajuce(pridavanaParcela, sirkoveData, dlzkoveData);

        }


        this.stromGPSParciel.insert(sirkoveData);
        this.stromGPSParciel.insert(dlzkoveData);
        this.stromUzemnychCelkov.insert(sirkoveData);
        this.stromUzemnychCelkov.insert(dlzkoveData);
    }


    public void upravParcelu(Parcela originalParcela, int noveCislo, String novyPopis, double[] noveSuradnice, char[] noveChary) {
        if (noveCislo != originalParcela.getCislo()) {
            this.najdiKonkretnyCelok(originalParcela.getDlzka()).setCislo(noveCislo);
        }
        if (!novyPopis.equals(originalParcela.getPopis())) {
            this.najdiKonkretnyCelok(originalParcela.getDlzka()).setPopis(novyPopis);
        }
        double[] surSirka = {noveSuradnice[0], noveSuradnice[1]};
        double[] surDlzka = {noveSuradnice[2], noveSuradnice[3]};
        char[] charySirka = {noveChary[0], noveChary[1]};
        char[] charyDlzka = {noveChary[2], noveChary[3]};
        GPSData novaSirka = new GPSData(surSirka, charySirka);
        GPSData novaDlzka = new GPSData(surDlzka, charyDlzka);


        if (!novaSirka.equals(originalParcela.getSirka(), false) || !novaDlzka.equals(originalParcela.getDlzka(), false)) {
            this.vymazUzemnyCelok(originalParcela);
            this.pridajParcelu(noveCislo, novyPopis, noveSuradnice, noveChary,true);

        }
    }

    public void upravNehnutelnost(Nehnutelnost originalNehnutelnost, int noveCislo, String novyPopis, double[] noveSuradnice, char[] noveChary) {
        if (noveCislo != originalNehnutelnost.getCislo()) {
            this.najdiKonkretnyCelok(originalNehnutelnost.getDlzka()).setCislo(noveCislo);
        }
        if (!novyPopis.equals(originalNehnutelnost.getPopis())) {
            this.najdiKonkretnyCelok(originalNehnutelnost.getDlzka()).setPopis(novyPopis);
        }
        double[] surSirka = {noveSuradnice[0], noveSuradnice[1]};
        double[] surDlzka = {noveSuradnice[2], noveSuradnice[3]};
        char[] charySirka = {noveChary[0], noveChary[1]};
        char[] charyDlzka = {noveChary[2], noveChary[3]};
        GPSData novaSirka = new GPSData(surSirka, charySirka);
        GPSData novaDlzka = new GPSData(surDlzka, charyDlzka);


        if (!novaSirka.equals(originalNehnutelnost.getSirka(), false) || !novaDlzka.equals(originalNehnutelnost.getDlzka(), false)) {
            this.vymazUzemnyCelok(originalNehnutelnost);
            this.pridajNehnutelnost(noveCislo, novyPopis, noveSuradnice, noveChary, false);

        }
    }

    public void vymazUzemnyCelok(UzemnyCelok uzemnyCelok) {
        UzemnyCelok uzemnyCelokMazany = this.najdiKonkretnyCelok(uzemnyCelok.getDlzka());
        if (uzemnyCelokMazany instanceof Parcela) {
            this.stromGPSParciel.delete(uzemnyCelokMazany.getSirka());
            this.stromGPSParciel.delete(uzemnyCelokMazany.getDlzka());

            ArrayList<Nehnutelnost> nehnutelnostPrekryvS = najdiVsetkyNehnutelnosti(uzemnyCelokMazany.getSirka().getDataAtD(0), uzemnyCelokMazany.getSirka().getDataAtD(1),
                    uzemnyCelokMazany.getSirka().getSmerAtD(0), uzemnyCelokMazany.getSirka().getSmerAtD(1));
            ArrayList<Nehnutelnost> nehnutelnostPrekryvD = najdiVsetkyNehnutelnosti(uzemnyCelokMazany.getDlzka().getDataAtD(0), uzemnyCelokMazany.getDlzka().getDataAtD(1),
                    uzemnyCelokMazany.getDlzka().getSmerAtD(0), uzemnyCelokMazany.getDlzka().getSmerAtD(1));
            for (Nehnutelnost nehnutelnost : nehnutelnostPrekryvS) {
                if (nehnutelnost != null && this.najdiKonkretnyCelok(nehnutelnost.getDlzka()).prekryvaSaSCelkom(uzemnyCelokMazany)) {
                    this.najdiKonkretnyCelok(nehnutelnost.getDlzka()).removeUzemnyObjekt(uzemnyCelokMazany);
                }
            }
            for (Nehnutelnost nehnutelnost : nehnutelnostPrekryvD) {
                if (nehnutelnost != null && this.najdiKonkretnyCelok(nehnutelnost.getDlzka()).prekryvaSaSCelkom(uzemnyCelokMazany)) {
                    this.najdiKonkretnyCelok(nehnutelnost.getDlzka()).removeUzemnyObjekt(uzemnyCelokMazany);
                }
            }

        } else if (uzemnyCelokMazany instanceof Nehnutelnost) {
            this.stromGPSNehnutelnosti.delete(uzemnyCelokMazany.getSirka());
            this.stromGPSNehnutelnosti.delete(uzemnyCelokMazany.getDlzka());

            ArrayList<Parcela> parcelaPrekryvS = najdiVsetkyParcely(uzemnyCelokMazany.getSirka().getDataAtD(0), uzemnyCelokMazany.getSirka().getDataAtD(1),
                    uzemnyCelokMazany.getSirka().getSmerAtD(0), uzemnyCelokMazany.getSirka().getSmerAtD(1));
            ArrayList<Parcela> parcelaPrekryvD = najdiVsetkyParcely(uzemnyCelokMazany.getDlzka().getDataAtD(0), uzemnyCelokMazany.getDlzka().getDataAtD(1),
                    uzemnyCelokMazany.getDlzka().getSmerAtD(0), uzemnyCelokMazany.getDlzka().getSmerAtD(1));
            for (Parcela parcela : parcelaPrekryvS) {
                if (parcela != null && this.najdiKonkretnyCelok(parcela.getDlzka()).prekryvaSaSCelkom(uzemnyCelokMazany)) {
                    this.najdiKonkretnyCelok(parcela.getDlzka()).removeUzemnyObjekt(uzemnyCelokMazany);
                }
            }
            for (Parcela parcela : parcelaPrekryvD) {
                if (parcela != null && this.najdiKonkretnyCelok(parcela.getDlzka()).prekryvaSaSCelkom(uzemnyCelokMazany)) {
                    this.najdiKonkretnyCelok(parcela.getDlzka()).removeUzemnyObjekt(uzemnyCelokMazany);
                }
            }

        }
        this.stromUzemnychCelkov.delete(uzemnyCelokMazany.getSirka());
        this.stromUzemnychCelkov.delete(uzemnyCelokMazany.getDlzka());
    }

    public void generujData(int pocetParciel, int pocetNehnutelnosti, double percentoPrekryvu) {
        for (int i = 0; i < pocetParciel; i++) {
            Random random = new Random();
            if (random.nextDouble() < (percentoPrekryvu / 100.0)) {
                double[] suradnicePrekryvu = {random.nextDouble(0, 90), random.nextDouble(0, 90), 20, 30};
                char[] dupChary = {'N', 'E', 'N', 'E'};
                this.pridajParcelu(random.nextInt(), "Prekryvajuca parcela", suradnicePrekryvu, dupChary , false);
            } else {
                char[] randChary = {(char) (74 + (random.nextInt(2) * 9)), (char) (69 + (random.nextInt(2) * 18)),
                        (char) (74 + (random.nextInt(2) * 9)), (char) (69 + (random.nextInt(2) * 18))};
                double[] suradnice = {random.nextDouble(0, 90), random.nextDouble(0, 90), random.nextDouble(0, 90), random.nextDouble(0, 90)};
                this.pridajParcelu(random.nextInt(), "Obycajna parcela " + (char) (random.nextInt(25) + 65), suradnice, randChary,false);
            }

        }

        for (int i = 0; i < pocetNehnutelnosti; i++) {
            Random random = new Random();
            if (random.nextDouble() < (percentoPrekryvu / 100.0)) {
                double[] suradnicePrekryvu = {20, 30, random.nextDouble(-90, 90), random.nextDouble(-90, 90)};
                char[] dupChary = {'N', 'E', 'N', 'E'};
                this.pridajNehnutelnost(random.nextInt(), "Prekryvajuca Nehnutelnost", suradnicePrekryvu, dupChary,false);
            } else {
                char[] randChary = {(char) (74 + (random.nextInt(2) * 9)), (char) (69 + (random.nextInt(2) * 18)),
                        (char) (74 + (random.nextInt(2) * 9)), (char) (69 + (random.nextInt(2) * 18))};
                double[] suradnice = {random.nextDouble(-90, 90), random.nextDouble(-90, 90), random.nextDouble(-90, 90), random.nextDouble(-90, 90)};
                this.pridajNehnutelnost(random.nextInt(), "Obycajna Nehnutelnost " + (char) (random.nextInt(25) + 65), suradnice,randChary,false);
            }

        }

        this.vyytvorVsetkyPrekryvy();

    }

    private ArrayList<Parcela> dajVsetkyParcely() {
        ArrayList<Parcela> parcely = new ArrayList<>();
        for (TrNode<GPSData> parcelaNode : this.stromGPSParciel.inorderMorris(this.stromGPSParciel.getRoot())) {
            if (!parcely.contains((Parcela) parcelaNode.getData().getUzemnyObjekt())) {
                parcely.add((Parcela) parcelaNode.getData().getUzemnyObjekt());
            }
        }
        return parcely;
    }

    private ArrayList<Nehnutelnost> dajVsetkyNehnutelnosti() {
        ArrayList<Nehnutelnost> nehnutelnosti = new ArrayList<>();
        for (TrNode<GPSData> parcelaNode : this.stromGPSNehnutelnosti.inorderMorris(this.stromGPSNehnutelnosti.getRoot())) {
            if (!nehnutelnosti.contains((Nehnutelnost) parcelaNode.getData().getUzemnyObjekt())) {
                nehnutelnosti.add((Nehnutelnost) parcelaNode.getData().getUzemnyObjekt());
            }
        }
        return nehnutelnosti;
    }

    public void nacitajDataZoSuboru(String suborParcely, String suborNehnutelnosti)  {
        try {
            BufferedReader readerParciel = new BufferedReader(new FileReader(suborParcely));
            BufferedReader readerNehnutelnosti = new BufferedReader(new FileReader(suborNehnutelnosti));

            try {
                String riadokN = readerNehnutelnosti.readLine();
                String riadokP = readerParciel.readLine();
                while (riadokN != null) {
                    String[] nehnutelnostString = riadokN.split(";");
                    if (nehnutelnostString[0].equals("Nehnutelnost")) {
                        double[] suradnice = {Double.parseDouble(nehnutelnostString[3]), Double.parseDouble(nehnutelnostString[4]), Double.parseDouble(nehnutelnostString[5]), Double.parseDouble(nehnutelnostString[6])};
                        char[] smery = {this.surandiceNaSmer(suradnice[0], true), this.surandiceNaSmer(suradnice[1], false),
                                this.surandiceNaSmer(suradnice[2],true), this.surandiceNaSmer(suradnice[3], false)};
                        this.pridajNehnutelnost(Integer.parseInt(nehnutelnostString[1]), nehnutelnostString[2], suradnice, smery,false);
                    }
                    riadokN = readerNehnutelnosti.readLine();
                }
                readerNehnutelnosti.close();

                while (riadokP != null) {
                    String[] parcelaString = riadokP.split(";");
                    if (parcelaString[0].equals("Parcela")) {
                        double[] suradnice = {Double.parseDouble(parcelaString[3]), Double.parseDouble(parcelaString[4]), Double.parseDouble(parcelaString[5]), Double.parseDouble(parcelaString[6])};
                        char[] smery = {this.surandiceNaSmer(suradnice[0], true), this.surandiceNaSmer(suradnice[1], false),
                                this.surandiceNaSmer(suradnice[2],true), this.surandiceNaSmer(suradnice[3], false)};
                        this.pridajParcelu(Integer.parseInt(parcelaString[1]), parcelaString[2], suradnice, smery ,false);
                    }
                    riadokP = readerParciel.readLine();
                }
                readerParciel.close();

            } catch (IOException e) {
                e.printStackTrace();
            }

        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
        this.vyytvorVsetkyPrekryvy();


    }

    private char surandiceNaSmer(double suradnica, boolean xSuradnica) {
        if (xSuradnica) {
            if (suradnica > 0) {
                return 'N';
            } else {
                return 'S';
            }
        } else {
            if (suradnica > 0) {
                return 'E';
            } else {
                return 'W';
            }
        }
    }

    private void vyytvorVsetkyPrekryvy() {
        for (Nehnutelnost nehnutelnost : this.dajVsetkyNehnutelnosti()) {
            this.pridajPrekryvajuce(nehnutelnost, nehnutelnost.getSirka(), nehnutelnost.getDlzka());
        }
        for (Parcela parcela : this.dajVsetkyParcely()) {
            this.pridajPrekryvajuce(parcela, parcela.getSirka(), parcela.getDlzka());
        }

    }

    public void zapisDataDoSuboru(String suborparcielCesta, String suborNehnutelnostiCesta) {
        File parcelySubor = new File(suborparcielCesta);
        File nehnutelnostiSubor = new File(suborNehnutelnostiCesta);
        BufferedWriter writerParciel = null;
        BufferedWriter writerNehnutelnosti = null;
        try {

            writerParciel = new BufferedWriter(new FileWriter(parcelySubor));
            writerNehnutelnosti = new BufferedWriter(new FileWriter(nehnutelnostiSubor));

        for (Parcela parcela : this.dajVsetkyParcely()) {
            String parcelaRiadok = "Parcela;" + parcela.getCislo() + ";" + parcela.getPopis() + ";" + parcela.getSirka().getDataAtD(0) + ";" + parcela.getSirka().getDataAtD(1) +
                    ";" + parcela.getDlzka().getDataAtD(0) + ";" + parcela.getDlzka().getDataAtD(1) + ";\n";
            System.out.println("Zapisane:");
            System.out.println(parcelaRiadok);
            writerParciel.write(parcelaRiadok);
        }
        writerParciel.close();
        for (Nehnutelnost nehnutelnost : this.dajVsetkyNehnutelnosti()) {
            String nehnutelnostRiadok = "Nehnutelnost;" + nehnutelnost.getCislo() + ";" + nehnutelnost.getPopis() + ";" + nehnutelnost.getSirka().getDataAtD(0) + ";" + nehnutelnost.getSirka().getDataAtD(1) +
                    ";" + nehnutelnost.getDlzka().getDataAtD(0) + ";" + nehnutelnost.getDlzka().getDataAtD(1) + ";\n";
            writerNehnutelnosti.write(nehnutelnostRiadok);
            System.out.println("Zapisane:");
            System.out.println(nehnutelnostRiadok);
        }
        writerNehnutelnosti.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }




}
