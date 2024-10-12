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
        int level = 0;
        if (this.root == null ) {
            this.emplaceRoot(paNode);
            level++;
            return true;
        }
        StromNode<T> currentNode = this.root;
        StromNode<T> tempNode = this.root;


        while (currentNode != null) {
            int k = level % this.dimensions;
            if (paNode.getData().compareTo(currentNode.getData(), k) == 1) {
                if (currentNode.getRight() == null) {
                    currentNode.setRight(paNode);
                    //System.out.println("doprava");
                    currentNode = null;
                    return true;
                }

                currentNode.setLevel(level);
                currentNode = currentNode.getRight();
                currentNode.setParent(tempNode);
                tempNode = currentNode;
                level++;
                currentNode.setLevel(level);
            } else if (paNode.getData().compareTo(currentNode.getData(), k) == -1) {
                if (currentNode.getLeft() == null) {
                    currentNode.setLeft(paNode);
                    //System.out.println("dolava");
                    currentNode = null;
                    return true;
                }
                currentNode.setLevel(level);
                level++;
                currentNode = currentNode.getLeft();
                currentNode.setParent(tempNode);
                tempNode = currentNode;
                currentNode.setLevel(level);
            } else if (paNode.getData().compareTo(currentNode.getData(), k) == 0) {
                if (currentNode.getLeft() == null) {
                    currentNode.setLeft(paNode);
                    //System.out.println("rovnaki");
                    currentNode = null;
                    return true;
                }
                currentNode.setLevel(level);
                currentNode = currentNode.getLeft();
                currentNode.setParent(tempNode);
                tempNode = currentNode;
                currentNode.setLevel(level);
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

                if (paData.compareTo(currentNode.getData(),(k + 1) % 2) == 0) {
                    return currentNode;
                }
                if (paData.compareTo(currentNode.getLeft().getData(), k) == 0) {
                    if (paData.compareTo(currentNode.getLeft().getData(), (k + 1) % 2) == 0) {
                        return currentNode.getLeft();
                    }
                    currentNode = currentNode.getLeft();
                }


            }
            level++;

        }


        return null;
    }

    public void proccessAllNode(StromNode<T> paNode) {
        this.proccessNode(paNode);
    }

    public void proccessNode(StromNode<T> paNode) {
        //toto treba vyhútať.. prehliadku

        if (paNode != null) {
            this.proccessNode(paNode.getLeft());
            paNode.getData().printData();
            paNode.printNode();
            this.proccessNode(paNode.getRight());
        }

    }




}
