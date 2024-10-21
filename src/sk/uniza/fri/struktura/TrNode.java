package sk.uniza.fri.struktura;

/**
 * 8. 10. 2024 - 14:38
 *
 * @author matus
 */
public abstract class TrNode<T> {
    private TrNode<T> right;
    private TrNode<T> left;
    private TrNode<T> parent;
    private int level;

    public int getLevel() {
        return this.level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public void setRight(TrNode<T> right) {
        this.right = right;
    }
    public void setLeft(TrNode<T> left) {
        this.left = left;
    }

    public TrNode<T> getLeft() {
        return this.left;
    }

    public TrNode<T> getRight() {
        return this.right;
    }

    public abstract IData<T> getData();

    public abstract void setDataAtD(int dimension, double value);

    public TrNode<T> getParent() {
        return this.parent;
    }

    public void setParent(TrNode<T> parent) {
        this.parent = parent;
    }

    public void printNode() {
        System.out.println("Node at level: ");
        System.out.println(this.level);
    }

    public boolean isLeaf() {
        if (this.right == null && this.left == null) {
            return true;
        }
        return false;
    }

    public boolean isRoot() {
        if (this.parent == null) {
            return true;
        }
        return false;
    }
}
