package sk.uniza.fri.struktura;

/**
 * 8. 10. 2024 - 14:38
 *
 * @author matus
 */
public abstract class StromNode<T> {
    protected StromNode<T> right;
    protected StromNode<T> left;
    protected StromNode<T> parent;
    protected int level;

    public int getLevel() {
        return this.level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    protected IData<T> data;

    //abstract data (tam je comapre), abstract node a abstract strom
    //Abstract data bude interface aby bola viacnas dedicnost
    // da sa j cez genericke triedy
    // do search idu genericke data
    //kod musi byt genericky zamerat sa na robenie stromu vseobecne
    //atributy prec, ma to byt vseobecne takze tie sa definuju az v triedach co dedia
    //kluce generujeme nahodne
    //spravit si tester (náhodne vyberáme operácie s náhodnýmk hodnotami)

    public void setRight(StromNode<T> right) {
        this.right = right;
    }
    public void setLeft(StromNode<T> left) {
        this.left = left;
    }

    public StromNode<T> getLeft() {
        return this.left;
    }

    public StromNode<T> getRight() {
        return this.right;
    }

    public IData<T> getData() {
        return this.data;
    }

    public void setData(IData<T> data) {
        this.data = data;
    }

    public StromNode<T> getParent() {
        return this.parent;
    }

    public void setParent(StromNode<T> parent) {
        this.parent = parent;
    }

    public void printNode() {
        System.out.println(this.level);
    }
}
