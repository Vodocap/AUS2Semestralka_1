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
    private int dimensions;
    public KDTree(int paDimensions) {
        this.dimensions = paDimensions;

    }
    public void emplaceRoot(StromNode<T> paNode) {
        this.root = paNode;
    }

    public StromNode<T> getRoot() {
        return this.root;
    }

    public boolean insert(StromNode<T> paNode) {
        if (this.root == null ) {
            this.emplaceRoot(paNode);
        }
        StromNode<T> currentNode = this.root;
        StromNode<T> tempNode = this.root;
        int level = 0;
        //treba prepinanie levelov vyhutať
        while (currentNode != null) {
            int k = level % this.dimensions;
            if (paNode.getData().compareTo(currentNode.getData(), k) == 1) {
                if (currentNode.getRight() == null) {
                    currentNode.setRight(paNode);
                    currentNode = null;
                    return true;
                }

                currentNode = currentNode.getRight();
                currentNode.setParent(tempNode);
                tempNode = currentNode;
                level++;
            } else if (paNode.getData().compareTo(currentNode.getData(), k) == -1) {
                if (currentNode.getLeft() == null) {
                    currentNode.setLeft(paNode);
                    currentNode = null;
                    return true;
                }
                level++;
                currentNode = currentNode.getLeft();
                currentNode.setParent(tempNode);
                tempNode = currentNode;
            } else if (paNode.getData().compareTo(currentNode.getData(), k) == 0) {
                if (currentNode.getLeft() == null) {
                    currentNode.setLeft(paNode);
                    currentNode = null;
                    return true;
                }
                currentNode = currentNode.getLeft();
                currentNode.setParent(tempNode);
                tempNode = currentNode;
            }
            level++;

        }


        return false;
    }

    public void printTree() {
        StromNode<T> currentNode = this.root;
        while (true) {
            currentNode.getData().printData();

        }
    }

}
