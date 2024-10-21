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
        paNode.printNode();
        paNode.getData().printData();
        System.out.println("********************************");
        int level = 0;
        if (this.root == null ) {
            this.emplaceRoot(paNode);
            paNode.setLevel(level);
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
                    level++;
                    paNode.setLevel(level);
                    paNode.setParent(currentNode);
                    //System.out.println("doprava");
                    currentNode = null;
                    return true;
                }

                tempNode = currentNode;
                currentNode.setLevel(level);
                currentNode = currentNode.getRight();
                level++;
                currentNode.setLevel(level);
                currentNode.setParent(tempNode);

            } else if (paNode.getData().compareTo(currentNode.getData(), k) == -1) {
                if (currentNode.getLeft() == null) {
                    currentNode.setLeft(paNode);
                    level++;
                    paNode.setLevel(level);
                    paNode.setParent(currentNode);
                    currentNode = null;
                    return true;
                }

                tempNode = currentNode;
                currentNode.setLevel(level);
                currentNode = currentNode.getLeft();
                level++;
                currentNode.setLevel(level);
                currentNode.setParent(tempNode);

            } else if (paNode.getData().compareTo(currentNode.getData(), k) == 0) {
                if (currentNode.getLeft() == null) {
                    currentNode.setLeft(paNode);
                    level++;
                    paNode.setLevel(level);
                    paNode.setParent(currentNode);
                    currentNode = null;
                    return true;
                }
                tempNode = currentNode;
                currentNode.setLevel(level);
                currentNode = currentNode.getLeft();
                level++;
                currentNode.setLevel(level);
                currentNode.setParent(tempNode);


            }


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

    private void disconnectNodeFully(TrNode<T> paNode) {
        paNode.setParent(null);
        paNode.setLeft(null);
        paNode.setRight(null);
    }

    private void disconnectSons(TrNode<T> paNode) {
        paNode.setLeft(null);
        paNode.setRight(null);
    }


    public TrNode<T> remove(IData<T> paData) {
        TrNode<T> nodeToRemove = this.find(paData);
        System.out.println("Printing node to remove");
        if (nodeToRemove != null) {
            nodeToRemove.printNode();
            nodeToRemove.getData().printData();
            if (nodeToRemove.getLeft() == null && nodeToRemove.getRight() == null) {
                if (nodeToRemove.getData().compareWholeTo(nodeToRemove.getParent().getLeft().getData())) {
                    nodeToRemove.getParent().setLeft(null);
                } else {
                    nodeToRemove.getParent().setRight(null);
                }
                return nodeToRemove;
            }

            if (nodeToRemove.getLeft() != null) {
                TrNode<T> tempNode = nodeToRemove;
                TrNode<T> maxLeft = this.inOrderWithFindMinMax(nodeToRemove.getLeft(), true, false);
                if (maxLeft.isLeaf() && nodeToRemove.isRoot()) {
                    maxLeft.getData().swapData(nodeToRemove.getData());
                    this.disconnectNodeFully(maxLeft);
                    return nodeToRemove;

                }
                TrNode<T> maxLeftLSon = maxLeft.getLeft();
                TrNode<T> maxLeftRSon = maxLeft.getRight();
                maxLeft.setParent(nodeToRemove.getParent());
                maxLeft.setLevel(nodeToRemove.getLevel());
                this.disconnectSons(maxLeft);
                this.inOrderWithFindMinMax(maxLeftLSon, true, true);
                this.inOrderWithFindMinMax(maxLeftRSon, true, true);
                this.disconnectNodeFully(tempNode);
                return nodeToRemove;

            }
            if (nodeToRemove.getRight() != null) {
                TrNode<T> tempNode = nodeToRemove;
                TrNode<T> minRight = this.inOrderWithFindMinMax(nodeToRemove.getRight(), false, false);
                if (minRight.isLeaf() && nodeToRemove.isRoot()) {
                    minRight.getData().swapData(nodeToRemove.getData());
                    this.disconnectNodeFully(minRight);
                    return nodeToRemove;
                }
                TrNode<T> minRigtLSon = minRight.getLeft();
                TrNode<T> minRightRSon = minRight.getRight();
                minRight.setParent(nodeToRemove.getParent());
                minRight.setLevel(nodeToRemove.getLevel());
                this.disconnectSons(minRight);
                this.inOrderWithFindMinMax(minRigtLSon, true, true);
                this.inOrderWithFindMinMax(minRightRSon, true, true);
                this.disconnectNodeFully(tempNode);
                return nodeToRemove;
            }
        } else {
            System.out.println("taky node tam neni");
        }


        return nodeToRemove;
    }

    public TrNode<T> inOrderWithFindMinMax(TrNode<T> paNode, boolean minOrMax, boolean insertSubtree) {
        //this.proccessNode(paNode);
        TrNode<T> minNode = paNode;
        TrNode<T> maxNode = paNode;
        ArrayList<TrNode<T>> nodesResult = new ArrayList<>();
        TrNode<T> currentNode = paNode;

        while (currentNode != null) {
            if (currentNode.getLeft() == null) {
                nodesResult.add(currentNode);
                currentNode = currentNode.getRight();

            } else {
                TrNode<T> previousNode = currentNode.getLeft();
                while (previousNode.getRight() != null && previousNode.getRight() != currentNode) {
                    previousNode = previousNode.getRight();
                }

                if (previousNode.getRight() == null) {
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
            tTrNode.printNode();
            tTrNode.getData().printData();
            System.out.println("-------------------------------------------------");
            if (tTrNode != paNode) {
                if (tTrNode.getData().compareTo(minNode.getData(), paNode.getLevel() % this.dimensions) < 0) {
                    //System.out.println("Novy minnode");
                    tTrNode.printNode();
                    tTrNode.getData().printData();
                    minNode = tTrNode;
                }

                if (tTrNode.getData().compareTo(maxNode.getData(), paNode.getLevel() % this.dimensions) > 0) {
                    //System.out.println("Novy maxnode");
                    tTrNode.printNode();
                    tTrNode.getData().printData();
                    maxNode = tTrNode;
                }

                if (insertSubtree) {
                    this.insert(tTrNode);
                }

            }


            }

        if (minOrMax) {
            return maxNode;
        } else {
            return minNode;
        }


    }





}
