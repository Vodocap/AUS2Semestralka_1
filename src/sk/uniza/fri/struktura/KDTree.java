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
        System.out.println("Inserting node");
        paNode.getData().printData();

        if (this.root == null ) {
            this.emplaceRoot(paNode);
            paNode.setLevel(0);
            return true;
        }
        TrNode<T> currentNode = this.root;
        TrNode<T> parentNode = null;
        int level = 0;


        while (currentNode != null) {
            int k = level % this.dimensions;
            parentNode = currentNode;

            System.out.println("sautusafiribulbuli level " + level);

            int comparison = paNode.getData().compareTo(currentNode.getData(), k);

            if (comparison > 0) {
                currentNode = currentNode.getRight();
            } else if (comparison < 0){
                currentNode = currentNode.getLeft();
            } else {
                currentNode = currentNode.getLeft();
            }

            level++;
        }

        paNode.setLevel(level);
        paNode.setParent(parentNode);

        int finalDimension = (level - 1) % this.dimensions;
        if (paNode.getData().compareTo(parentNode.getData(), finalDimension) > 0) {
            parentNode.setRight(paNode);
        } else {
            parentNode.setLeft(paNode);
        }

        return true;

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

        }

        System.out.println("Such a node does not exist in this tree");
        return null;
    }

    private void disconnectNodeFully(TrNode<T> paNode) {
        paNode.setParent(null);
        paNode.setLeft(null);
        paNode.setRight(null);
    }

    private void disconnectSons(TrNode<T> paNode) {
        paNode.setLeft(null);
        paNode.setRight(null);
    }

    private void connectSons(TrNode<T> paParent, TrNode<T> paLeftSon, TrNode<T> paRightSon) {
        paParent.setLeft(paLeftSon);
        paParent.setRight(paRightSon);
    }

    private void replaceNode(TrNode<T> nodeToRemove, TrNode<T> newNode) {

        if (nodeToRemove.isRoot()) {
            this.root = newNode;
        } else {
            TrNode<T> parent = nodeToRemove.getParent();
            if (parent.getLeft() == nodeToRemove) {
                parent.setLeft(newNode);
            } else {
                parent.setRight(newNode);
            }
        }

        if (newNode != null) {
            newNode.setParent(nodeToRemove.getParent());
            newNode.setLevel(nodeToRemove.getLevel());
            newNode.setLeft(nodeToRemove.getLeft());
            newNode.setRight(nodeToRemove.getRight());

            if (newNode.hasLeft()) {
                newNode.getLeft().setParent(newNode);
            }
            if (newNode.hasRight()) {
                newNode.getRight().setParent(newNode);
            }
        }


    }


    public TrNode<T> delete(IData<T> paData) {


        TrNode<T> nodeToRemove = this.find(paData);
        System.out.println("Printing node to remove");
        if (nodeToRemove != null) {

            nodeToRemove.printNode();
            nodeToRemove.getData().printData();


            if (nodeToRemove.isLeaf()) {
                this.replaceNode(nodeToRemove, null);
                return nodeToRemove;

            }

            TrNode<T> replacerNode;
            if (nodeToRemove.hasLeft()) {
                replacerNode = this.inOrderOrFindMinMaxOrInsertSubtree(nodeToRemove.getLeft(), true, false, false);

            } else {
                replacerNode = this.inOrderOrFindMinMaxOrInsertSubtree(nodeToRemove.getRight(), false, false, false);
            }

            if (replacerNode.getParent().getLeft() == replacerNode) {
                replacerNode.getParent().setLeft(null);
            } else {
                replacerNode.getParent().setRight(null);
            }

            replacerNode.getData().swapData(nodeToRemove.getData());
            replacerNode.setParent(null);

            if (!replacerNode.isLeaf()) {
                this.inOrderOrFindMinMaxOrInsertSubtree(replacerNode, false, true, false);
            }

            return nodeToRemove;

        } else {
            System.out.println("No such node in tree");
        }

        return null;


    }

    public TrNode<T> inOrderOrFindMinMaxOrInsertSubtree(TrNode<T> paNode, boolean minOrMax, boolean insertSubtree, boolean printTree) {
        //this.proccessNode(paNode);
        TrNode<T> minNode = paNode;
        TrNode<T> maxNode = paNode;
        ArrayList<TrNode<T>> nodesResult = new ArrayList<>();
        TrNode<T> currentNode = paNode;

        while (currentNode != null) {
            if (!currentNode.hasLeft()) {
                nodesResult.add(currentNode);
                currentNode = currentNode.getRight();

            } else {
                TrNode<T> previousNode = currentNode.getLeft();
                while (previousNode.getRight() != null && previousNode.getRight() != currentNode) {
                    previousNode = previousNode.getRight();
                }

                if (!previousNode.hasRight()) {
                    previousNode.setRight(currentNode);
                    currentNode = currentNode.getLeft();

                } else {
                    previousNode.setRight(null);
                    nodesResult.add(currentNode);
                    currentNode = currentNode.getRight();
                }
            }

        }

        System.out.println("Amount of elements in the tree:");
        System.out.println(nodesResult.size());


        for (TrNode<T> tTrNode : nodesResult) {
            if (printTree) {
                System.out.println("-------------------------------------------------");
                tTrNode.printNode();
                tTrNode.getData().printData();
            }
            if (tTrNode != paNode) {
                if (tTrNode.getData().compareTo(minNode.getData(), paNode.getLevel() % this.dimensions) < 0) {
                    //System.out.println("Novy minnode");
                    //if (printTree) {
                    //    tTrNode.printNode();
                    //    tTrNode.getData().printData();
                    //}
                    minNode = tTrNode;
                }

                if (tTrNode.getData().compareTo(maxNode.getData(), paNode.getLevel() % this.dimensions) > 0) {
                    //System.out.println("Novy maxnode");
                    //if (printTree) {
                    //    tTrNode.printNode();
                    //    tTrNode.getData().printData();
                    //}
                    maxNode = tTrNode;
                }



            }
            if (insertSubtree) {
                this.insert(tTrNode);
            }


            }

        if (minOrMax) {
            return maxNode;
        } else {
            return minNode;
        }


    }





}
