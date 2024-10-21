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


    public TrNode<T> remove(IData<T> paData) {
        TrNode<T> nodeToRemove = this.find(paData);
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
            TrNode<T> maxLeft = inOrderWithFindMinMax(nodeToRemove.getLeft(), true, false);
            TrNode<T> maxLeftLSon = maxLeft.getLeft();
            TrNode<T> maxLeftRSon = maxLeft.getRight();
            maxLeft.setParent(nodeToRemove.getParent());
            maxLeft.setLevel(nodeToRemove.getLevel());
            maxLeft.setRight(null);
            maxLeft.setLeft(null);
            this.inOrderWithFindMinMax(maxLeftLSon, true, true);
            this.inOrderWithFindMinMax(maxLeftRSon, true, true);
            tempNode.setParent(null);
            tempNode.setLeft(null);
            tempNode.setRight(null);
            return nodeToRemove;

        }
        if (nodeToRemove.getRight() != null) {
            TrNode<T> tempNode = nodeToRemove;
            TrNode<T> minRight = inOrderWithFindMinMax(nodeToRemove.getRight(), false, false);
            TrNode<T> minRigtLSon = minRight.getLeft();
            TrNode<T> minRightRSon = minRight.getRight();
            minRight.setParent(nodeToRemove.getParent());
            minRight.setLevel(nodeToRemove.getLevel());
            minRight.setRight(null);
            minRight.setLeft(null);
            this.inOrderWithFindMinMax(minRigtLSon, true, true);
            this.inOrderWithFindMinMax(minRightRSon, true, true);
            tempNode.setParent(null);
            tempNode.setLeft(null);
            tempNode.setRight(null);
            return nodeToRemove;
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
            if (tTrNode.getData().compareTo(minNode.getData(), paNode.getLevel() % this.dimensions) < 0) {
                System.out.println("Novy minnode");
                tTrNode.printNode();
                tTrNode.getData().printData();
                minNode = tTrNode;
            }

            if (tTrNode.getData().compareTo(maxNode.getData(), paNode.getLevel() % this.dimensions) > 0) {
                System.out.println("Novy maxnode");
                tTrNode.printNode();
                tTrNode.getData().printData();
                maxNode = tTrNode;
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
