package sk.uniza.fri.struktura;


/**
 * 8. 10. 2024 - 14:38
 *
 * @author matus
 */

// lubovolny pocet dimenzi, kluc moze byt cokolvek, definujeme kluc cez genericku triedu
    // vetvenie podla komparatorov ktore u rozne
    //pre kazdy level wsa definuje komparator
    //compareto treba pridat aj atribut level

public class KDTree<T> {
    private StromNode<T> root;
    private int depth;
    private int level;
    public KDTree() {


    }
    public void emplaceRoot(StromNode<T> paNode) {
        this.root = paNode;
    }

    public StromNode<T> getRoot() {
        return this.root;
    }

    public boolean instert(StromNode<T> paNode) {
        StromNode<T> currentNode = this.root;
        //treba prepinanie levelov vyhutať
        while (currentNode != null) {
            if (paNode.getData().compareTo(currentNode.getData()) == 1) {
                if (currentNode.getRight() == null) {
                    currentNode.setRight(paNode);
                    currentNode = null;
                    return true;
                }
                currentNode = currentNode.getRight();
            } else if (paNode.getData().compareTo(currentNode.getData()) == -1) {
                if (currentNode.getLeft() == null) {
                    currentNode.setLeft(paNode);
                    currentNode = null;
                    return true;
                }
                currentNode = currentNode.getLeft();
            } else {
                if (currentNode.getLeft() == null) {
                    currentNode.setLeft(paNode);
                    currentNode = null;
                    return true;
                }
            }

        }


        return false;
    }

}
