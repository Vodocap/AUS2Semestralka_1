package sk.uniza.fri.struktura;

/**
 * 8. 10. 2024 - 14:38
 *
 * @author matus
 */

/**
 *
 * Trieda TrNode reprezentuje node v strome, node si uchováva refernciu na dáta ktoré obsahuje,
 * referencie na synov a otca a tiež aj hĺbku v ktorej sa v strome nachádza.
 *
 */
public class TrNode<T> {
    private TrNode<T> right;
    private TrNode<T> left;
    private TrNode<T> parent;
    private int level;
    private T data;

    public TrNode(T data) {
        this.data = data;
    }

    /**
     *
     * metóda getlevel vráti úroveň na akej sa koreň aktuálne nachádza
     * @return int celé číslo reprezentujúce úroveň
     *
     */

    public int getLevel() {
        return this.level;
    }

    /**
     *
     * metóda seTLevel nastaví nodu úroveň na akej sa koreň aktuálne nachádza
     * @param level celé číslo reprezentujúce novú úroveň nodu
     *
     */
    public void setLevel(int level) {
        this.level = level;
    }

    /**
     *
     * metóda setRight nastaví nodu nového pravého syna
     * @param right referencia na nového pravého syna
     *
     */
    public void setRight(TrNode<T> right) {
        this.right = right;
    }

    /**
     *
     * metóda setLeft nastaví nodu nového ľavého syna
     * @param left referencia na nového ľavého syna
     *
     */
    public void setLeft(TrNode<T> left) {
        this.left = left;
    }

    /**
     *
     * metóda getLeft vráti ľavého syna nodu
     * @return referencia na ľavého syna nodu
     *
     */
    public TrNode<T> getLeft() {
        return this.left;
    }

    /**
     *
     * metóda getRight vráti pravého syna nodu
     * @return referencia na pravého syna nodu
     *
     */
    public TrNode<T> getRight() {
        return this.right;
    }

    /**
     *
     * metóda getData vráti inštanciu dát ktoré sa v node nachádzajú
     * @return inštancia dát ktoré sa v node nachádzajú
     *
     */
    public T getData() {
        return this.data;
    }

    /**
     *
     * metóda setData do nodu pridá novú referenciu na prislúchajúcu inštanciu dát
     * @param paData referencia na inštanciu nových dát ktoré sa do nodu vložia
     *
     */
    public void setData(T paData) {
        this.data = paData;
    }


    /**
     *
     * metóda getParent vráti referenciu na otca daného nodu
     * @return referencia na node ktorý je otcom daného nodu
     *
     */
    public TrNode<T> getParent() {
        return this.parent;
    }

    /**
     *
     * metóda getParent nastaví nodu novú referenciu na otca
     * @param parent referencia na node ktorý bude novým otcom daného nodu
     *
     */
    public void setParent(TrNode<T> parent) {
        this.parent = parent;
    }

    /**
     *
     * metóda printNode na konzolu vypíše úroveň daného nodu
     *
     */
    public void printNode() {
        System.out.println("Node at level: " + this.level);
    }

    /**
     *
     * metóda isLeaf zistí či je node listom, tj. nemá žiadne referencie na ani jedného zo synov
     * @return true ak done je listom, inak false
     *
     */
    public boolean isLeaf() {
        return this.right == null && this.left == null;
    }

    /**
     *
     * metóda isRooot zistí či je node rootom, tj. nemá žiadnu referenciu na otca
     * @return true ak done je listom, inak false
     *
     */
    public boolean isRoot() {
        return this.parent == null;
    }

    /**
     *
     * metóda hasLeft zistí či node má referenciu na ľavého syna
     * @return true ak node má referenciu na ľavého syna, inak false
     *
     */
    public boolean hasLeft() {
        return this.getLeft() != null;
    }

    /**
     *
     * metóda hasRight zistí či node má referenciu na pravého syna
     * @return true ak node má referenciu na pravého syna, inak false
     *
     */
    public boolean hasRight() {
        return this.getRight() != null;
    }
}
