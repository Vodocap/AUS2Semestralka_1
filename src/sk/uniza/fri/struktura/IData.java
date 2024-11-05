package sk.uniza.fri.struktura;

/**
 * Interface ktorý poskytuje porovnávacie metódy svojím potomkom ktorí reprezentujú dáta ukladané do stromu,
 * taktiež poskytuje gettery a settery pre získavanie ID atribútu,
 * nastavenie referencie na node v ktorom sa práve dáta nachádzajú a získavanie jednotlivých súradníc na zadanej dimenzií.
 */

public interface IData<T> {

    /**
     * metóda compare to porovná dve inštancie potomkov IData a podľa je implementácie v potomkoch rozhodne akú hodnotu vráti
     * @param paData inštancia potomka interface IData
     * @param dimension dimenzie na ktorej sa dáta budú porovnávať
     * @return 0 ak sa dáta na dimenzií rovnajú, 1 ak sú dáta na dimenzií väčšie alebo -1 ak sú menšie
     */
    int compareTo(IData<T> paData, int dimension);

    /**
     * metóda equals porovná dve inštancie potomkov IData a podľa je implementácie v potomkoch rozhodne či sú rovnakí
     * @param paData inštancia potomka interface IData
     * @param compareID ak je true inštancie sa porvnávajú aj podľa interného ID atribútu, ak je false tak sa porovnajú len dáta na všetkých dimenziách
     * @return vracia true ak sa prvky rovnajú na všetkých dimenziách a false ak sa nerovnajú, podľa parametra compareID vráti false,
     * ak sa na dimenziách rovnajú no ID je rozdielne a true, ak sa dáta rovnajú aj na všetkych dimenziách a aj podľa kľúča
     */
    boolean equals(IData<T> paData, boolean compareID);

    /**
     * metóda getDataATD vráti dáta danej inštancie na zadanej dimenzií
     * @param dimension zadaná dimenzia
     * @return vráti dáta inštancie potomka na zadanej dimenzií
     */
    T getDataAtD(int dimension);

    /**
     * metóda printData vypíše dáta na konzolu
     */
    public void printData();

    /**
     * metóda makeCopy vytvorí podľa implementácie v potomkoch identickú inštanciu samého seba
     * @return vracia identickú novú inštanciu objektu
     */
    IData<T> makeCopy();
    /**
     * metóda getID vráti ID string danej inštancie
     * @return String obsahujúci unikátne ID inštancie
     */
    String getID();

    /**
     * metóda setID nastaví inštancií nové ID
     * @param ID nové unikátne ID pre danú inštanciu
     */
    void setID(String ID);
    /**
     * metóda getCurrentNode vráti node v ktorom sa inštancia potomka práve nachádza
     * @return vracia node v ktorom sa inštancia potomka práve nachádza
     */
    public TrNode<T> getCurrentNode();
    /**
     * metóda setCurrentNode nastaví node v ktorom sa inštancia potomka práve nachádza
     * @param currentNode nový node v ktorom sa inštancia bude nachádzať
     */
    public void setCurrentNode(TrNode<T> currentNode);

}
