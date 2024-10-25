package sk.uniza.fri.struktura;


import java.util.ArrayList;

/**
 * 8. 10. 2024 - 14:38
 *
 * @author matus
 */



public class KDTree<T extends IData> {
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


    public boolean insert(T data) {
        TrNode<T> paNode = new TrNode<>(data);

        if (this.root == null ) {
            this.emplaceRoot(paNode);
            paNode.setLevel(0);
            System.out.println("Inserted node");
            paNode.printNode();
            paNode.getData().printData();
            return true;
        }
        TrNode<T> currentNode = this.root;
        TrNode<T> parentNode = null;
        int level = 0;


        while (currentNode != null) {
            int k = level % this.dimensions;
            parentNode = currentNode;

            //System.out.println("sautusafiribulbuli level " + level);

            int comparison = paNode.getData().compareTo(currentNode.getData(), k);

            if (comparison > 0) {
                currentNode = currentNode.getRight();
            }
            if (comparison <= 0) {
                currentNode = currentNode.getLeft();
            }

            level++;
        }


        paNode.setLevel(level);
        paNode.setParent(parentNode);
        System.out.println("Inserted node");
        paNode.printNode();
        paNode.getData().printData();

        int finalDimension = (level - 1) % this.dimensions;
        if (paNode.getData().compareTo(parentNode.getData(), finalDimension) > 0) {
            parentNode.setRight(paNode);
        } else {
            parentNode.setLeft(paNode);
        }

        return true;

    }

    public TrNode<T> find(T paData) {
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

                if (paData.compareWholeTo(currentNode.getData(), true)) {
                    return currentNode;
                }
                currentNode = currentNode.getLeft();
                level++;

            }

        }

        System.out.println("Such a node does not exist in this tree");
        return null;
    }

    public ArrayList<TrNode<T>> findAll(T paData) {

        ArrayList<TrNode<T>> resultNodes = new ArrayList<TrNode<T>>();
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

                if (paData.compareWholeTo(currentNode.getData(), false)) {
                    resultNodes.add(currentNode);
                }
                //if (paData.compareWholeTo(currentNode.getLeft().getData())) {
                //    resultNodes.add(currentNode.getLeft());
                //}
                currentNode = currentNode.getLeft();


            }

        }
        return resultNodes;
    }

    private void disconnectNodeFully(TrNode<T> paNode) {
        if (paNode.getParent().getLeft() == paNode) {
            paNode.getParent().setLeft(null);
        } else if (paNode.getParent().getRight() == paNode) {
            paNode.getParent().setRight(null);
        }
        paNode.setParent(null);
        paNode.setLeft(null);
        paNode.setRight(null);
    }


    private void replaceNode(TrNode<T> nodeToRemove, TrNode<T> newNode) {

        if (nodeToRemove.isRoot()) {
            this.emplaceRoot(newNode);
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
            newNode.setLeft(nodeToRemove.getLeft());
            newNode.setRight(nodeToRemove.getRight());

            if (newNode.hasLeft()) {
                newNode.getLeft().setParent(newNode);
            }
            if (newNode.hasRight()) {
                newNode.getRight().setParent(newNode);
            }
        }

        nodeToRemove.setParent(null);


    }


    public TrNode<T> delete(T paData) {

        TrNode<T> nodeToRemove = this.find(paData);
        System.out.println("Printing node to remove");

        while (!nodeToRemove.isLeaf()) {

            TrNode<T> replacerNode = null;
            if (nodeToRemove.hasLeft()) {
                replacerNode = this.inOrderOrFindMinMaxOrInsertSubtree(nodeToRemove.getLeft(), true, false, false);
            }
            if (nodeToRemove.hasRight()) {
                replacerNode = this.inOrderOrFindMinMaxOrInsertSubtree(nodeToRemove.getRight(), false, false, false);
            }
            if (replacerNode != null) {

                TrNode<T> temnode = new TrNode<>(nodeToRemove.getData());
                nodeToRemove.setData(replacerNode.getData());
                replacerNode.setData(temnode.getData());
                nodeToRemove = replacerNode;

            }

        }

        this.disconnectNodeFully(nodeToRemove);
        return nodeToRemove;


//        if (nodeToRemove != null) {
//
//            nodeToRemove.printNode();
//            //nodeToRemove.getData().printData();
//
//
//            if (nodeToRemove.isLeaf()) {
//                if (!nodeToRemove.getParent().isRoot()) {
//
//                    if (nodeToRemove.getParent() != null) {
//
//                        if (nodeToRemove.getParent().getLeft() == nodeToRemove) {
//
//                            nodeToRemove.getParent().setLeft(null);
//                        } else {
//
//                            nodeToRemove.getParent().setRight(null);
//                        }
//                    }
//                } else {
//                    this.emplaceRoot(null);
//                }
//                return nodeToRemove;
//
//            }
//
//            TrNode<T> replacerNode;
//            if (nodeToRemove.hasLeft()) {
//                replacerNode = this.inOrderOrFindMinMaxOrInsertSubtree(nodeToRemove.getLeft(), true, false, true);
//
//            } else {
//                replacerNode = this.inOrderOrFindMinMaxOrInsertSubtree(nodeToRemove.getRight(), false, false, true);
//            }
//
//            if (replacerNode.getParent().getLeft() == replacerNode) {
//                replacerNode.getParent().setLeft(null);
//            } else {
//                replacerNode.getParent().setRight(null);
//            }
//
//            //replacerNode.getData().swapData(nodeToRemove.getData());
//            replacerNode.setParent(null);
//
//            if (!replacerNode.isLeaf()) {
//                this.inOrderOrFindMinMaxOrInsertSubtree(replacerNode, false, true, true);
//            }
//
//            return nodeToRemove;
//
//        } else {
//            System.out.println("No such node in tree");
//        }
//
//        return null;


    }

    // pridat enum ako parameter ktory bude odlisovat casy
    public TrNode<T> inOrderOrFindMinMaxOrInsertSubtree(TrNode<T> paNode, boolean minOrMax, boolean insertSubtree, boolean printTree) {
        //this.proccessNode(paNode);
        TrNode<T> minNode = paNode;
        TrNode<T> maxNode = paNode;
        ArrayList<TrNode<T>> nodesResult = new ArrayList<>();
        TrNode<T> currentNode = paNode;
        int level = paNode.getLevel() - 1;
        if (paNode.isRoot()) {
            level = paNode.getLevel();
        }

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
            //this.disconnectSons(tTrNode);
            if (printTree) {
                System.out.println("-------------------------------------------------");
                tTrNode.printNode();
                tTrNode.getData().printData();
            }
            
            if (tTrNode != paNode) {
                if (insertSubtree) {
                    System.out.println("Inserting subtree into tree, node:");
                    tTrNode.getData().printData();
                    this.insert(tTrNode.getData());
                }
                System.out.println();


                if (tTrNode.getData().compareTo(minNode.getData(), level % this.dimensions) < 0) {

                    if (printTree) {
//                        System.out.println("Novy minnode");
//                        tTrNode.printNode();
//                        tTrNode.getData().printData();
                    }
                    minNode = tTrNode;
                }

                if (tTrNode.getData().compareTo(maxNode.getData(), level % this.dimensions) >= 0) {

                    if (printTree) {
                        System.out.println("Novy maxnode");
                        System.out.println("Urovne " + paNode.getLevel());
                        tTrNode.printNode();
                        tTrNode.getData().printData();
                    }
                    maxNode = tTrNode;
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
