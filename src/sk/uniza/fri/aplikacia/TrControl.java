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



/**
 * Trieda reprezentujúca jadro aplikácie,
 * je zodpovedná za správu troch stromov obsahujúcich územné celky.
 * Poskytuje metódy ktoré plnia úlohy mazania, pridávania a vyhľadávania v systéme
 */

public class TrControl {
    public KDTree<GPSData> stromGPSParciel;
    private KDTree<GPSData> stromGPSNehnutelnosti;
    private KDTree<GPSData> stromUzemnychCelkov;
    private IDGenerator idGenerator;

    public TrControl () {
        this.idGenerator = new IDGenerator();
        this.stromGPSParciel = new KDTree<GPSData>(4);
        this.stromGPSNehnutelnosti = new KDTree<GPSData>(4);
        this.stromUzemnychCelkov = new KDTree<GPSData>(4);
    }


    /**
     * metóda najdiVestkyNehnutelnosti podľa zadaných súradníc vráti kópie všetkých nehnuteľností pre výpis na GUI
     * @param poziciaX vyhľadávaná pozícia x nehnuteľnosti
     * @param poziciaY vyhľadávaná pozícia y nehnuteľnosti
     * @param charX vyhľadávaný smer pozície x nehnuteľnosti (Ak N tak pozícia x * 1 a ak E tak pozícia x * (-1))
     * @param charY vyhľadávaný smer pozície y nehnuteľnosti (Ak E tak pozícia y * 1 a ak W tak pozícia y * (-1))
     * @return zoznam kópií nájdených nehnuteľností
     */
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

    /**
     * metóda najdiVestkyParcely podľa zadaných súradníc vráti kópie všetkých parciel pre výpis na GUI
     * @param poziciaX vyhľadávaná pozícia x parcely
     * @param poziciaY vyhľadávaná pozícia y parcely
     * @param charX vyhľadávaný smer pozície x parcely (Ak N tak pozícia x * 1 a ak E tak pozícia x * (-1))
     * @param charY vyhľadávaný smer pozície y parcely (Ak E tak pozícia y * 1 a ak W tak pozícia y * (-1))
     * @return zoznam kópií nájdených parciel
     */
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

    /**
     * metóda najdiVestkyObjekty podľa zadaných súradníc vráti kópie všetkých nádjených územných celkov pre výpis na GUI
     * @param poziciaX vyhľadávaná pozícia x územného celku
     * @param poziciaY vyhľadávaná pozícia y územného celku
     * @param charX vyhľadávaný smer pozície x územného celku (Ak N tak pozícia x * 1 a ak E tak pozícia x * (-1))
     * @param charY vyhľadávaný smer pozície y územného celku (Ak E tak pozícia y * 1 a ak W tak pozícia y * (-1))
     * @return zoznam kópií nájdených územných celkov
     */
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

    private ArrayList<Parcela> privNajdiVsetkyParcely(GPSData paData) {

        ArrayList<TrNode<GPSData>> tempList = this.stromGPSParciel.findAll(paData);
        ArrayList<Parcela> resultList = new ArrayList<>();
        for (TrNode<GPSData> gpsDataTrNode : tempList) {
            resultList.add((Parcela) gpsDataTrNode.getData().getUzemnyObjekt());
        }
        return resultList;
    }

    private ArrayList<Nehnutelnost> privNajdiVsetkyNehnutelnosti(GPSData paData) {

        ArrayList<TrNode<GPSData>> tempList = this.stromGPSNehnutelnosti.findAll(paData);
        ArrayList<Nehnutelnost> resultList = new ArrayList<>();
        for (TrNode<GPSData> gpsDataTrNode : tempList) {
            resultList.add((Nehnutelnost) gpsDataTrNode.getData().getUzemnyObjekt());
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


        if (pridavanyCelok instanceof Nehnutelnost) {
            ArrayList<Parcela> duplikatySirka = this.privNajdiVsetkyParcely(paSirka);
            ArrayList<Parcela> duplikatyDlzka = this.privNajdiVsetkyParcely(paDlzka);

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
            ArrayList<Nehnutelnost> duplikatySirka = this.privNajdiVsetkyNehnutelnosti(paSirka);
            ArrayList<Nehnutelnost> duplikatyDlzka = this.privNajdiVsetkyNehnutelnosti(paDlzka);

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


    /**
     *
     * metóda pridajNehnutelnost podľa zadaných informácií pridá do systému novú nehnuteľnosť, ak sa nehnuteľnosť prekrýva s dákymi parcelami,
     * pridá sa im do zoznamu a ona si ich pridá do zoznamu tiež
     * @param paCislo číslo pridávanej nehnuteľnosti
     * @param paPopis popis pridávanej nehnuteľnosti
     * @param paSuradnice pole všetkých súradníc novej nehnuteľnosti
     * @param paChary pole všetkých smerov súradníc novej nehnuteľnosti
     * @param urobPrekryvy ak je true tak sa pri pridávaní zavolá aj metóda na pridanie parciel s ktorými sa prekrýva a pridá sa tiež aj im do ich zoznamov
     *
     */
    public void pridajNehnutelnost(int paCislo,String paPopis, double[] paSuradnice, char[] paChary, boolean urobPrekryvy) {
        double[] poleSirka = {paSuradnice[0], paSuradnice[1]};
        double[] poleDlzka = {paSuradnice[2], paSuradnice[3]};
        char[] charySirka = {paChary[0], paChary[1]};
        char[] charyDlzka = {paChary[2], paChary[3]};
        GPSData sirkoveData = new GPSData(poleSirka, charySirka);
        GPSData dlzkoveData = new GPSData(poleDlzka, charyDlzka);
        sirkoveData.setID(this.idGenerator.generateUniqueID());
        dlzkoveData.setID(this.idGenerator.generateUniqueID());
        GPSData sirkoveDataVseaob = new GPSData(poleSirka, charySirka);
        GPSData dlzkoveDataVseaob = new GPSData(poleDlzka, charyDlzka);
        sirkoveDataVseaob.setID(sirkoveData.getID());
        dlzkoveDataVseaob.setID(dlzkoveData.getID());
        Nehnutelnost pridavanaNehnutelnost = new Nehnutelnost(paCislo, paPopis, sirkoveData, dlzkoveData);
        sirkoveData.setUzemnyObjekt(pridavanaNehnutelnost);
        dlzkoveData.setUzemnyObjekt(pridavanaNehnutelnost);
        sirkoveDataVseaob.setUzemnyObjekt(pridavanaNehnutelnost);
        dlzkoveDataVseaob.setUzemnyObjekt(pridavanaNehnutelnost);

        if (urobPrekryvy) {
            this.pridajPrekryvajuce(pridavanaNehnutelnost, sirkoveData, dlzkoveData);
//            this.vyytvorVsetkyPrekryvy();
        }

        this.stromGPSNehnutelnosti.insert(sirkoveData);
        this.stromGPSNehnutelnosti.insert(dlzkoveData);
        this.stromUzemnychCelkov.insert(sirkoveDataVseaob);
        this.stromUzemnychCelkov.insert(dlzkoveDataVseaob);
    }

    /**
     *
     * metóda pridajparcelu podľa zadaných informácií pridá do systému novú parcelu, ak sa parcela prekrýva s dákymi nehnuteoľnosťami,
     * pridá sa im do zoznamu a ona si ich pridá do zoznamu tiež
     * @param paCislo číslo pridávanej parcely
     * @param paPopis popis pridávanej parcely
     * @param paSuradnice pole všetkých súradníc novej parcely
     * @param paChary pole všetkých smerov súradníc novej parcely
     * @param urobPrekryvy ak je true tak sa pri pridávaní zavolá aj metóda na pridanie nehnuteľností s ktorými sa prekrýva a pridá sa tiež aj im do ich zoznamov
     *
     */
    public void pridajParcelu(int paCislo,String paPopis, double[] paSuradnice, char[] paChary, boolean urobPrekryvy) {
        double[] poleSirka = {paSuradnice[0], paSuradnice[1]};
        double[] poleDlzka = {paSuradnice[2], paSuradnice[3]};
        char[] charySirka = {paChary[0], paChary[1]};
        char[] charyDlzka = {paChary[2], paChary[3]};
        GPSData sirkoveData = new GPSData(poleSirka, charySirka);
        GPSData dlzkoveData = new GPSData(poleDlzka, charyDlzka);
        sirkoveData.setID(this.idGenerator.generateUniqueID());
        dlzkoveData.setID(this.idGenerator.generateUniqueID());
        GPSData sirkoveDataVseaob = new GPSData(poleSirka, charySirka);
        GPSData dlzkoveDataVseaob = new GPSData(poleDlzka, charyDlzka);
        sirkoveDataVseaob.setID(sirkoveData.getID());
        dlzkoveDataVseaob.setID(dlzkoveData.getID());
        Parcela pridavanaParcela = new Parcela(paCislo, paPopis, sirkoveData, dlzkoveData);
        sirkoveData.setUzemnyObjekt(pridavanaParcela);
        dlzkoveData.setUzemnyObjekt(pridavanaParcela);
        sirkoveDataVseaob.setUzemnyObjekt(pridavanaParcela);
        dlzkoveDataVseaob.setUzemnyObjekt(pridavanaParcela);
        if (urobPrekryvy) {
            this.pridajPrekryvajuce(pridavanaParcela, sirkoveData, dlzkoveData);

        }


        this.stromGPSParciel.insert(sirkoveData);
        this.stromGPSParciel.insert(dlzkoveData);
        this.stromUzemnychCelkov.insert(sirkoveDataVseaob);
        this.stromUzemnychCelkov.insert(dlzkoveDataVseaob);
    }

    /**
     *
     * metóda upravParcelu upraví používateľom vybranú parcelu, ak sa nezmenia kľúčové parametre (súradnice) tak sa hodnoty len zmenia v systéme,
     * ak sa však zmení aspoň jedna zo súradníc tak sa parcela zo systému vymaže a upravená za insertne naspäť
     * @param noveCislo nové číslo parcely
     * @param originalParcela referencia na originálnu parcelu
     * @param novyPopis nový popis parcely
     * @param noveSuradnice nové pole všetkých súradníc parcely
     * @param noveChary nové pole všetkých smerov súradníc parcely
     *
     */
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
            this.vyradUzemnyCelok(originalParcela);
            this.pridajParcelu(noveCislo, novyPopis, noveSuradnice, noveChary,true);

        }
    }

    /**
     *
     * metóda upravNehnutelnost upraví používateľom vybranú nehnuteľnosť, ak sa nezmenia kľúčové parametre (súradnice) tak sa hodnoty len zmenia v systéme,
     * ak sa však zmení aspoň jedna zo súradníc tak sa nehnuteľnosť zo systému vymaže a upravená za insertne naspäť
     * @param noveCislo nové číslo nehnuteľnosti
     * @param originalNehnutelnost referencia na originálnu nehnuteľnosť
     * @param novyPopis nový popis nehnuteľnosti
     * @param noveSuradnice nové pole všetkých súradníc nehnuteľnosti
     * @param noveChary nové pole všetkých smerov súradníc nehnuteľnosti
     *
     */
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
            this.vyradUzemnyCelok(originalNehnutelnost);
            this.pridajNehnutelnost(noveCislo, novyPopis, noveSuradnice, noveChary, false);

        }
    }

    /**
     *
     * metóda vyradUzemnyCelok upraví používateľom vybraný územný celok zo všetkých miest na ktorých sa nachádza teda aj zo zonamov územných celkov s ktorými sa prekrýva
     * @param uzemnyCelok referencia na územný celok ktorý sa vymaže
     *
     */
    public void vyradUzemnyCelok(UzemnyCelok uzemnyCelok) {
        UzemnyCelok uzemnyCelokMazany = this.najdiKonkretnyCelok(uzemnyCelok.getDlzka());
        if (uzemnyCelokMazany instanceof Parcela) {
            this.stromGPSParciel.delete(uzemnyCelokMazany.getSirka());
            this.stromGPSParciel.delete(uzemnyCelokMazany.getDlzka());

            ArrayList<Nehnutelnost> nehnutelnostPrekryvS = this.najdiVsetkyNehnutelnosti((Double) uzemnyCelokMazany.getSirka().getDataAtD(0), (Double) uzemnyCelokMazany.getSirka().getDataAtD(2),
                    uzemnyCelokMazany.getSirka().getSmerAtD(0), uzemnyCelokMazany.getSirka().getSmerAtD(1));
            ArrayList<Nehnutelnost> nehnutelnostPrekryvD = this.najdiVsetkyNehnutelnosti((Double) uzemnyCelokMazany.getDlzka().getDataAtD(0), (Double) uzemnyCelokMazany.getDlzka().getDataAtD(1),
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

            ArrayList<Parcela> parcelaPrekryvS = this.najdiVsetkyParcely((Double) uzemnyCelokMazany.getSirka().getDataAtD(0), (Double) uzemnyCelokMazany.getSirka().getDataAtD(1),
                    uzemnyCelokMazany.getSirka().getSmerAtD(0), uzemnyCelokMazany.getSirka().getSmerAtD(1));
            ArrayList<Parcela> parcelaPrekryvD = this.najdiVsetkyParcely((Double) uzemnyCelokMazany.getDlzka().getDataAtD(0), (Double) uzemnyCelokMazany.getDlzka().getDataAtD(1),
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

    /**
     *
     * metóda generujData vygeneruje dáta podľa zadaných parametrov
     * @param pocetParciel počet parciel na vygenerovanie
     * @param pocetNehnutelnosti počet nehnuteľností na vygenerovanie
     * @param percentoPrekryvu percento vygenerovaných celkov ktoré sa budú prekrývať s ďalšími
     *
     */
    public void generujData(int pocetParciel, int pocetNehnutelnosti, double percentoPrekryvu) {
        ArrayList<double[]> pouzite = new ArrayList<>();
        Random rand = new Random();
        for (int i = 0; i < pocetParciel; i++) {
            Random random = new Random();
            if (random.nextDouble() < (percentoPrekryvu / 100.0)) {
                double[] pouziteSirky = {Math.round(random.nextDouble(-90, 90)*100.0)/100.0, Math.round(random.nextDouble(-90, 90)*100.0)/100.0};
                double[] pouziteDlzky = {Math.round(random.nextDouble(-90, 90)*100.0)/100.0, Math.round(random.nextDouble(-90, 90)*100.0)/100.0};
                pouzite.add(pouziteSirky);
                pouzite.add(pouziteDlzky);
                double[] suradnicePrekryvu = {pouziteSirky[0], pouziteSirky[1], pouziteDlzky[0], pouziteDlzky[1]};
                char[] dupChary = {'N', 'E', 'N', 'E'};
                this.pridajParcelu(random.nextInt(), "Prekryvajuca parcela", suradnicePrekryvu, dupChary , false);
            } else {
                char[] randChary = {(char) (74 + (random.nextInt(2) * 9)), (char) (69 + (random.nextInt(2) * 18)),
                        (char) (74 + (random.nextInt(2) * 9)), (char) (69 + (random.nextInt(2) * 18))};
                double[] suradnice = {Math.round(random.nextDouble(-90, 90)*100.0)/100.0, Math.round(random.nextDouble(-90, 90)*100.0)/100.0,
                        Math.round(random.nextDouble(-90, 90)*100.0)/100.0, Math.round(random.nextDouble(-90, 90)*100.0)/100.0};
                this.pridajParcelu(random.nextInt(), "Obycajna parcela " + (char) (random.nextInt(25) + 65), suradnice, randChary,true);
            }

        }

        for (int i = 0; i < pocetNehnutelnosti; i++) {
            Random random = new Random();

            if (random.nextDouble() < (percentoPrekryvu / 100.0)) {
                int indexPouzitychS = random.nextInt(pouzite.size());
                int indexPouzitychD = random.nextInt(pouzite.size());
                double[] suradnicePrekryvu = {pouzite.get(indexPouzitychS)[0], pouzite.get(indexPouzitychS)[1], pouzite.get(indexPouzitychD)[0], pouzite.get(indexPouzitychD)[1]};
                char[] dupChary = {'N', 'E', 'N', 'E'};
                this.pridajNehnutelnost(random.nextInt(), "Prekryvajuca Nehnutelnost", suradnicePrekryvu, dupChary,false);
            } else {
                char[] randChary = {(char) (74 + (random.nextInt(2) * 9)), (char) (69 + (random.nextInt(2) * 18)),
                        (char) (74 + (random.nextInt(2) * 9)), (char) (69 + (random.nextInt(2) * 18))};
                double[] suradnice = {Math.round(random.nextDouble(-90, 90)*100.0)/100.0, Math.round(random.nextDouble(-90, 90)*100.0)/100.0,
                        Math.round(random.nextDouble(-90, 90)*100.0)/100.0, Math.round(random.nextDouble(-90, 90)*100.0)/100.0};
                this.pridajNehnutelnost(random.nextInt(), "Obycajna Nehnutelnost " + (char) (random.nextInt(25) + 65), suradnice,randChary,false);
            }

        }

        this.vyytvorVsetkyPrekryvy();

    }

    /**
     *
     * metóda dajVsetkyParcely slúži na účely overenia správnosti fungovania systému, vráti všetky parcely kotré sa nachádzajú v systéme
     * @param copies ak je true tak vráti iba kópie inak vráti priamo objekty
     *
     */
    public ArrayList<Parcela> dajVsetkyParcely(boolean copies) {
        ArrayList<Parcela> parcely = new ArrayList<>();
        ArrayList<Parcela> parcelyKopie = new ArrayList<>();
        ArrayList<TrNode<GPSData>> parcelyGPS = this.stromGPSParciel.inorder(this.stromGPSParciel.getRoot());
        for (TrNode<GPSData> parcelaNode : parcelyGPS) {
            if (!parcely.contains((Parcela) parcelaNode.getData().getUzemnyObjekt())) {
                parcely.add((Parcela) parcelaNode.getData().getUzemnyObjekt());
            }
        }

        if (copies) {
            for (Parcela parcelaOriginal : parcely) {
                Parcela returnParcela = (Parcela) parcelaOriginal.makeCopy();
                GPSData returnSirka = (GPSData) parcelaOriginal.getSirka().makeCopy();
                GPSData returnDlzka = (GPSData) parcelaOriginal.getDlzka().makeCopy();
                returnSirka.setUzemnyObjekt(returnParcela);
                returnDlzka.setUzemnyObjekt(returnParcela);
                returnParcela.setDlzka(returnSirka);
                returnParcela.setSirka(returnDlzka);
                returnParcela.setStringObjektov(parcelaOriginal.toStringObjektov());

                parcelyKopie.add(returnParcela);

            }
            return parcelyKopie;

        }
        return parcely;
    }

    /**
     *
     * metóda dajVsetkyNehnutelnosti slúži na účely overenia správnosti fungovania systému, vráti všetky nehnuteľnosti ktoré sa nachádzajú v systéme
     * @param copies ak je true tak vráti iba kópie inak vráti priamo objekty
     *
     */
    public ArrayList<Nehnutelnost> dajVsetkyNehnutelnosti(boolean copies) {
        ArrayList<Nehnutelnost> nehnutelnosti = new ArrayList<>();
        ArrayList<Nehnutelnost> nehnutelnostiKopie = new ArrayList<>();
        ArrayList<TrNode<GPSData>> nehnutelnostiGPS = this.stromGPSNehnutelnosti.inorder(this.stromGPSNehnutelnosti.getRoot());
        for (TrNode<GPSData> parcelaNode : nehnutelnostiGPS) {
            if (!nehnutelnosti.contains((Nehnutelnost) parcelaNode.getData().getUzemnyObjekt())) {
                nehnutelnosti.add((Nehnutelnost) parcelaNode.getData().getUzemnyObjekt());
            }
        }
        if (copies) {
            for (Nehnutelnost nehnutelnostOriginal : nehnutelnosti) {
                Nehnutelnost returnNehnutelnost = (Nehnutelnost) nehnutelnostOriginal.makeCopy();
                GPSData returnSirka = (GPSData) nehnutelnostOriginal.getSirka().makeCopy();
                GPSData returnDlzka = (GPSData) nehnutelnostOriginal.getDlzka().makeCopy();
                returnSirka.setUzemnyObjekt(returnNehnutelnost);
                returnDlzka.setUzemnyObjekt(returnNehnutelnost);
                returnNehnutelnost.setDlzka(returnSirka);
                returnNehnutelnost.setSirka(returnDlzka);
                returnNehnutelnost.setStringObjektov(nehnutelnostOriginal.toStringObjektov());

                nehnutelnostiKopie.add(returnNehnutelnost);

            }
            return nehnutelnostiKopie;

        }
        return nehnutelnosti;
    }

    /**
     *
     * metóda nacitajDataZoSuboru zo zadaných súborov načíta a rozparsuje dáta ktoré potom vloží do systému
     * @param suborNehnutelnosti string cesta k súboru nehnuteľností
     * @param suborParcely string cesta k súboru parciel
     */
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
        for (Nehnutelnost nehnutelnost : this.dajVsetkyNehnutelnosti(false)) {
            this.pridajPrekryvajuce(nehnutelnost, nehnutelnost.getSirka(), nehnutelnost.getDlzka());
        }
        for (Parcela parcela : this.dajVsetkyParcely(false)) {
            this.pridajPrekryvajuce(parcela, parcela.getSirka(), parcela.getDlzka());
        }

    }

    /**
     *
     * metóda zapisDataDoSuboru do zadaných súborov zapíše všetky dáta zo systému csv formáte
     * @param suborNehnutelnostiCesta string cesta k súboru nehnuteľností
     * @param suborparcielCesta string cesta k súboru parciel
     */
    public void zapisDataDoSuboru(String suborparcielCesta, String suborNehnutelnostiCesta) {
        File parcelySubor = new File(suborparcielCesta);
        File nehnutelnostiSubor = new File(suborNehnutelnostiCesta);
        BufferedWriter writerParciel = null;
        BufferedWriter writerNehnutelnosti = null;
        try {

            writerParciel = new BufferedWriter(new FileWriter(parcelySubor));
            writerNehnutelnosti = new BufferedWriter(new FileWriter(nehnutelnostiSubor));

        for (Parcela parcela : this.dajVsetkyParcely(false)) {
            String parcelaRiadok = "Parcela;" + parcela.getCislo() + ";" + parcela.getPopis() + ";" + parcela.getSirka().getDataAtD(0) + ";" + parcela.getSirka().getDataAtD(2) +
                    ";" + parcela.getDlzka().getDataAtD(0) + ";" + parcela.getDlzka().getDataAtD(2) + ";\n";
            System.out.println("Zapisane:");
            System.out.println(parcelaRiadok);
            writerParciel.write(parcelaRiadok);
        }
        writerParciel.close();
        for (Nehnutelnost nehnutelnost : this.dajVsetkyNehnutelnosti(false)) {
            String nehnutelnostRiadok = "Nehnutelnost;" + nehnutelnost.getCislo() + ";" + nehnutelnost.getPopis() + ";" + nehnutelnost.getSirka().getDataAtD(0) + ";" + nehnutelnost.getSirka().getDataAtD(2) +
                    ";" + nehnutelnost.getDlzka().getDataAtD(0) + ";" + nehnutelnost.getDlzka().getDataAtD(2) + ";\n";
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
