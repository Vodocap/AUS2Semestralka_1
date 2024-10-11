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
    private final int dimensions;
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
            return true;
        }
        StromNode<T> currentNode = this.root;
        StromNode<T> tempNode = this.root;
        int level = 0;

        while (currentNode != null) {
            int k = level % this.dimensions;
            if (paNode.getData().compareTo(currentNode.getData(), k) == 1) {
                if (currentNode.getRight() == null) {
                    currentNode.setRight(paNode);
                    //System.out.println("doprava");
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
                    //System.out.println("dolava");
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
                    //System.out.println("rovnaki");
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

    public StromNode<T> find(IData<T> paData) {
        StromNode<T> currentNode = this.root;
        int level = 0;

        while (currentNode != null) {
            int k = level % this.dimensions;
            if (paData.compareTo(currentNode.getData(), k) == 1) {
                currentNode = currentNode.getRight();
                level++;
            } else if (paData.compareTo(currentNode.getData(), k) == -1) {
                currentNode = currentNode.getLeft();
                level++;
            } else if (paData.compareTo(currentNode.getData(), k) == 0) {
                return currentNode;
            }
            level++;

        }


        return null;
    }

    public void printTree() {
        //toto treba vyhútať.. prehliadku
        StromNode<T> currentNode = this.root;
        int rootAccessedTimes = 0;
        while (rootAccessedTimes != 3 ) {
            if (currentNode == this.root) {
                rootAccessedTimes++;
            }
            if (currentNode.left == null) {
                //currentNode.getData().printData();
                currentNode = currentNode.getParent();

            } else if (currentNode.right == null) {
                //currentNode.getData().printData();
                currentNode = currentNode.getParent();

            } else if (currentNode.left != null){
                //currentNode.getData().printData();
                currentNode = currentNode.getLeft();
            } else if (currentNode.right != null && currentNode.left == null) {
                //currentNode.getData().printData();
                currentNode = currentNode.getRight();
            }


        }
    }

}
