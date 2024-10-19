package sk.uniza.fri.struktura;


import java.util.ArrayList;

/**
 * 8. 10. 2024 - 14:38
 *
 * @author matus
 */



public class KDTree<T> {
    private TrNode<T> root;
    private final int dimensions;
    public KDTree(int paDimensions) {
        this.dimensions = paDimensions;

    }
    public void emplaceRoot(TrNode<T> paNode) {
        this.root = paNode;
    }

    public TrNode<T> getRoot() {
        return this.root;
    }


    public boolean insert(TrNode<T> paNode) {
        int level = 0;
        if (this.root == null ) {
            this.emplaceRoot(paNode);
            level++;
            return true;
        }
        TrNode<T> currentNode = this.root;
        TrNode<T> tempNode = this.root;


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

    public TrNode<T> find(IData<T> paData) {
        TrNode<T> currentNode = this.root;
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

                if (paData.compareWholeTo(currentNode.getData())) {
                    return currentNode;
                }
                if (paData.compareWholeTo(currentNode.getLeft().getData())) {
                    return currentNode.getLeft();
                }
                currentNode = currentNode.getLeft();


            }
            level++;

        }

        System.out.println("Such a node does not exist in this tree");
        return null;
    }


    public TrNode<T> remove(IData<T> paData) {
        TrNode<T> nodeToRemove = this.find(paData);
        if (nodeToRemove.getLeft() == null && nodeToRemove.getRight() == null) {
            if (nodeToRemove.getData().compareWholeTo(nodeToRemove.getParent().getLeft().getData())) {
                nodeToRemove.getParent().setLeft(null);
            } else {
                nodeToRemove.getParent().setRight(null);
            }
            return nodeToRemove;
        };
        return nodeToRemove;
    }

    public void proccessAllNode(TrNode<T> paNode) {
        //this.proccessNode(paNode);
        ArrayList<TrNode<T>> nodesResult = new ArrayList<>();
        TrNode<T> currentNode = this.root;

        while (currentNode != null) {
            if (currentNode.left == null) {
                nodesResult.add(currentNode);
                currentNode = currentNode.right;

            } else {
                TrNode<T> previousNode = currentNode.left;
                while (previousNode.right != null && previousNode.right != currentNode) {
                    previousNode = previousNode.right;
                }

                if (previousNode.right == null) {
                    previousNode.right = currentNode;
                    currentNode = currentNode.left;

                } else {
                    previousNode.right = null;
                    nodesResult.add(currentNode);
                    currentNode = currentNode.right;
                }
            }

        }
        for (TrNode<T> tTrNode : nodesResult) {
            tTrNode.printNode();
            tTrNode.getData().printData();
        }

    }

    public void proccessNode(TrNode<T> paNode) {



        if (paNode != null) {
            this.proccessNode(paNode.getLeft());
            paNode.printNode();
            paNode.getData().printData();
            this.proccessNode(paNode.getRight());
        }

    }




}
