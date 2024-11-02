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
    public ArrayList<Nehnutelnost> najdiVsetkyNehnutelnosti(double poziciaX, double poziciaY) {
        double[] tempSuradnice = {poziciaX, poziciaY};
        GPSData tempData = new GPSData(tempSuradnice);
        ArrayList<TrNode<GPSData>> tempList = this.stromGPSNehnutelnosti.findAll(tempData);
        ArrayList<Nehnutelnost> resultList = new ArrayList<>();
        for (TrNode<GPSData> gpsDataTrNode : tempList) {
            if (!resultList.contains((Nehnutelnost) gpsDataTrNode.getData().getUzemnyObjekt())) {
                resultList.add((Nehnutelnost) gpsDataTrNode.getData().getUzemnyObjekt());
            }
        }
        return resultList;
    }

    public ArrayList<Parcela> najdiVsetkyParcely(double poziciaX, double poziciaY) {
        double[] tempSuradnice = {poziciaX, poziciaY};
        GPSData tempData = new GPSData(tempSuradnice);
        ArrayList<TrNode<GPSData>> tempList = this.stromGPSParciel.findAll(tempData);
        ArrayList<Parcela> resultList = new ArrayList<>();
        for (TrNode<GPSData> gpsDataTrNode : tempList) {
            if (!resultList.contains((Parcela) gpsDataTrNode.getData().getUzemnyObjekt())) {
                resultList.add((Parcela) gpsDataTrNode.getData().getUzemnyObjekt());
            }

        }
        return resultList;
    }

    public ArrayList<UzemnyCelok> najdiVsetkyObjekty(double poziciaX, double poziciaY) {
        double[] tempSuradnice = {poziciaX, poziciaY};
        GPSData tempData = new GPSData(tempSuradnice);
        ArrayList<TrNode<GPSData>> tempList = this.stromUzemnychCelkov.findAll(tempData);
        ArrayList<UzemnyCelok> resultList = new ArrayList<>();
        for (TrNode<GPSData> gpsDataTrNode : tempList) {

            resultList.add(gpsDataTrNode.getData().getUzemnyObjekt());
            System.out.println(gpsDataTrNode.getData().getUzemnyObjekt().toString());


        }
        return resultList;
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
                }
            }

            for (UzemnyCelok uzemnyCelok : duplikatyDlzka) {
                if (uzemnyCelok instanceof Parcela) {
                    pridavanyCelok.pridajUzemnyCelok(uzemnyCelok);
                }
            }
        }
        if (pridavanyCelok instanceof Parcela) {

            for (UzemnyCelok uzemnyCelok : duplikatySirka) {
                if (uzemnyCelok instanceof Nehnutelnost) {
                    pridavanyCelok.pridajUzemnyCelok(uzemnyCelok);
                }
            }

            for (UzemnyCelok uzemnyCelok : duplikatyDlzka) {
                if (uzemnyCelok instanceof Nehnutelnost) {
                    pridavanyCelok.pridajUzemnyCelok(uzemnyCelok);
                }
            }
        }
    }

    public void pridajNehnutelnost(int paCislo,String paPopis, double[] paSuradnice, boolean urobPrekryvy) {
        double[] poleSirka = {paSuradnice[0], paSuradnice[1]};
        double[] poleDlzka = {paSuradnice[2], paSuradnice[3]};
        GPSData sirkoveData = new GPSData(poleSirka);
        GPSData dlzkoveData = new GPSData(poleDlzka);
        Nehnutelnost pridavanaNehnutelnost = new Nehnutelnost(paCislo, paPopis, sirkoveData, dlzkoveData);
        sirkoveData.setUzemnyObjekt(pridavanaNehnutelnost);
        dlzkoveData.setUzemnyObjekt(pridavanaNehnutelnost);

        if (urobPrekryvy) {
            this.pridajPrekryvajuce(pridavanaNehnutelnost, sirkoveData, dlzkoveData);
        }

        this.stromGPSNehnutelnosti.insert(sirkoveData);
        this.stromGPSNehnutelnosti.insert(dlzkoveData);
        this.stromUzemnychCelkov.insert(sirkoveData);
        this.stromUzemnychCelkov.insert(dlzkoveData);
    }

    public void pridajParcelu(int paCislo,String paPopis, double[] paSuradnice, boolean urobPrekryvy) {
        double[] poleSirka = {paSuradnice[0], paSuradnice[1]};
        double[] poleDlzka = {paSuradnice[2], paSuradnice[3]};
        GPSData sirkoveData = new GPSData(poleSirka);
        GPSData dlzkoveData = new GPSData(poleDlzka);
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


    public void upravParcelu(Parcela originalParcela, int noveCislo, String novyPopis, double[] noveSuradnice) {
        if (noveCislo != originalParcela.getCislo()) {
            originalParcela.setCislo(noveCislo);
        }
        if (!novyPopis.equals(originalParcela.getPopis())) {
            originalParcela.setPopis(novyPopis);
        }
        double[] surSirka = {noveSuradnice[0], noveSuradnice[1]};
        double[] surDlzka = {noveSuradnice[2], noveSuradnice[3]};
        GPSData novaSirka = new GPSData(surSirka);
        GPSData novaDlzka = new GPSData(surDlzka);
//        novaSirka.setID(originalParcela.getSirka().getID());
//        novaDlzka.setID(originalParcela.getDlzka().getID());

        if (!novaSirka.equals(originalParcela.getSirka(), false) || !novaDlzka.equals(originalParcela.getDlzka(), false)) {
            this.vymazUzemnyCelok(originalParcela);
            this.pridajParcelu(noveCislo, novyPopis, noveSuradnice, true);

        }
    }

    public void upravNehnutelnost(Nehnutelnost originalNehnutelnost, int noveCislo, String novyPopis, double[] noveSuradnice) {
        if (noveCislo != originalNehnutelnost.getCislo()) {
            originalNehnutelnost.setCislo(noveCislo);
        }
        if (!novyPopis.equals(originalNehnutelnost.getPopis())) {
            originalNehnutelnost.setPopis(novyPopis);
        }
        double[] surSirka = {noveSuradnice[0], noveSuradnice[1]};
        double[] surDlzka = {noveSuradnice[2], noveSuradnice[3]};
        GPSData novaSirka = new GPSData(surSirka);
        GPSData novaDlzka = new GPSData(surDlzka);
        novaSirka.setID(originalNehnutelnost.getSirka().getID());
        novaDlzka.setID(originalNehnutelnost.getDlzka().getID());

        if (!novaSirka.equals(originalNehnutelnost.getSirka(), false) || !novaDlzka.equals(originalNehnutelnost.getDlzka(), false)) {
            this.vymazUzemnyCelok(originalNehnutelnost);
            this.pridajNehnutelnost(noveCislo, novyPopis, noveSuradnice, false);

        }
    }

    public void vymazUzemnyCelok(UzemnyCelok uzemnyCelok) {
        if (uzemnyCelok instanceof Parcela) {
            this.stromGPSParciel.delete(uzemnyCelok.getSirka());
            this.stromGPSParciel.delete(uzemnyCelok.getDlzka());

            ArrayList<Nehnutelnost> nehnutelnostPrekryvS = najdiVsetkyNehnutelnosti(uzemnyCelok.getSirka().getDataAtD(0), uzemnyCelok.getSirka().getDataAtD(1));
            ArrayList<Nehnutelnost> nehnutelnostPrekryvD = najdiVsetkyNehnutelnosti(uzemnyCelok.getDlzka().getDataAtD(0), uzemnyCelok.getDlzka().getDataAtD(1));
            for (Nehnutelnost nehnutelnost : nehnutelnostPrekryvS) {
                if (nehnutelnost != null && nehnutelnost.prekryvaSaSCelkom(uzemnyCelok)) {
                    nehnutelnost.removeUzemnyObjekt(uzemnyCelok);
                }
            }
            for (Nehnutelnost nehnutelnost : nehnutelnostPrekryvD) {
                if (nehnutelnost != null && nehnutelnost.prekryvaSaSCelkom(uzemnyCelok)) {
                    nehnutelnost.removeUzemnyObjekt(uzemnyCelok);
                }
            }

        } else if (uzemnyCelok instanceof Nehnutelnost) {
            this.stromGPSNehnutelnosti.delete(uzemnyCelok.getSirka());
            this.stromGPSNehnutelnosti.delete(uzemnyCelok.getDlzka());

            ArrayList<Parcela> parcelaPrekryvS = najdiVsetkyParcely(uzemnyCelok.getSirka().getDataAtD(0), uzemnyCelok.getSirka().getDataAtD(1));
            ArrayList<Parcela> parcelaPrekryvD = najdiVsetkyParcely(uzemnyCelok.getDlzka().getDataAtD(0), uzemnyCelok.getDlzka().getDataAtD(1));
            for (Parcela parcela : parcelaPrekryvS) {
                if (parcela != null && parcela.prekryvaSaSCelkom(uzemnyCelok)) {
                    parcela.removeUzemnyObjekt(uzemnyCelok);
                }
            }
            for (Parcela parcela : parcelaPrekryvD) {
                if (parcela != null && parcela.prekryvaSaSCelkom(uzemnyCelok)) {
                    parcela.removeUzemnyObjekt(uzemnyCelok);
                }
            }

        }
        this.stromUzemnychCelkov.delete(uzemnyCelok.getSirka());
        this.stromUzemnychCelkov.delete(uzemnyCelok.getDlzka());
    }

    public void generujData(int pocetParciel, int pocetNehnutelnosti, double percentoPrekryvu) {
        Random randomDup = new Random();
        double surXPrexryv = randomDup.nextDouble(-90, 90);
        double surYPrexryv = randomDup.nextDouble(-90, 90);
        for (int i = 0; i < pocetParciel; i++) {
            Random random = new Random();
            if (random.nextDouble() < (percentoPrekryvu / 100.0)) {
                double[] suradnicePrekryvu = {random.nextDouble(-90, 90), random.nextDouble(-90, 90), 20, 30};
                this.pridajParcelu(random.nextInt(), "Prekryvajuca parcela", suradnicePrekryvu, false);
            } else {
                double[] suradnice = {random.nextDouble(-90, 90), random.nextDouble(-90, 90), random.nextDouble(-90, 90), random.nextDouble(-90, 90)};
                this.pridajParcelu(random.nextInt(), "Obycajna parcela " + (char) (random.nextInt(25) + 65), suradnice, false);
            }

        }

        for (int i = 0; i < pocetNehnutelnosti; i++) {
            Random random = new Random();
            if (random.nextDouble() < (percentoPrekryvu / 100.0)) {
                double[] suradnicePrekryvu = {20, 30, random.nextDouble(-90, 90), random.nextDouble(-90, 90)};
                this.pridajNehnutelnost(random.nextInt(), "Prekryvajuca Nehnutelnost", suradnicePrekryvu, false);
            } else {
                double[] suradnice = {random.nextDouble(-90, 90), random.nextDouble(-90, 90), random.nextDouble(-90, 90), random.nextDouble(-90, 90)};
                this.pridajNehnutelnost(random.nextInt(), "Obycajna Nehnutelnost " + (char) (random.nextInt(25) + 65), suradnice, false);
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
                        this.pridajNehnutelnost(Integer.parseInt(nehnutelnostString[1]), nehnutelnostString[2], suradnice, false);
                    }
                    riadokN = readerNehnutelnosti.readLine();
                }
                readerNehnutelnosti.close();

                while (riadokP != null) {
                    String[] parcelaString = riadokP.split(";");
                    if (parcelaString[0].equals("Parcela")) {
                        double[] suradnice = {Double.parseDouble(parcelaString[3]), Double.parseDouble(parcelaString[4]), Double.parseDouble(parcelaString[5]), Double.parseDouble(parcelaString[6])};
                        this.pridajParcelu(Integer.parseInt(parcelaString[1]), parcelaString[2], suradnice, false);
                    }
                    riadokP = readerParciel.readLine();
                }
                readerParciel.close();

                this.vyytvorVsetkyPrekryvy();
            } catch (IOException e) {
                e.printStackTrace();
            }

        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }


    }

    private void vyytvorVsetkyPrekryvy() {
        for (Parcela parcela : this.dajVsetkyParcely()) {
            this.pridajPrekryvajuce(parcela, parcela.getSirka(), parcela.getDlzka());
        }

        for (Nehnutelnost nehnutelnost : this.dajVsetkyNehnutelnosti()) {
            this.pridajPrekryvajuce(nehnutelnost, nehnutelnost.getSirka(), nehnutelnost.getDlzka());
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
