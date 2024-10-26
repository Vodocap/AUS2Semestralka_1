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
//            System.out.println("Inserted node");
//            paNode.printNode();
//            paNode.getData().printData();
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
//        System.out.println("Inserted node");
//        paNode.printNode();
//        paNode.getData().printData();

        int finalDimension = (level - 1) % this.dimensions;
        if (paNode.getData().compareTo(parentNode.getData(), finalDimension) > 0) {
            parentNode.setRight(paNode);
        } else {
            parentNode.setLeft(paNode);
        }
//        if (this.root.hasRight() && !this.root.hasLeft()) {
//            this.rebalanceTree();
//        }

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

//        System.out.println("Such a node does not exist in this tree");
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
        if (!paNode.isRoot()) {
            if (paNode.getParent().getLeft() == paNode) {
                paNode.getParent().setLeft(null);
            } else if (paNode.getParent().getRight() == paNode) {
                paNode.getParent().setRight(null);
            }
            paNode.setParent(null);
            paNode.setLeft(null);
            paNode.setRight(null);
        }
    }


    public ArrayList<TrNode<T>> subtreeHasDuplicates (TrNode<T> subtreeRoot) {
        ArrayList<TrNode<T>> morrisNodes = this.inorderMorris(subtreeRoot);
        ArrayList<TrNode<T>> duplicates = new ArrayList<>();
        for (int i = 0; i < morrisNodes.size(); i++) {
            TrNode<T> currentNode = morrisNodes.get(i);
            for (int j = 0; j < morrisNodes.size(); j++) {
                TrNode<T> tempNode = morrisNodes.get(j);
                for (int d = 0; d < this.dimensions; d++) {
                    if (currentNode.getData().getDataAtD(d).equals(tempNode.getData().getDataAtD(d))) {
                        duplicates.add(currentNode);
                        duplicates.add(tempNode);
                    }
                }
            }
        }
        return duplicates;
    }

    public void rebalanceTree() {
        ArrayList<TrNode<T>> duplicates  = this.subtreeHasDuplicates(this.root.getRight());
        System.out.println("BEGINING REPALANCING");
        for (TrNode<T> duplicate : duplicates) {
            this.insert(this.delete(duplicate.getData()).getData());
        }

    }

    public TrNode<T> delete(T paData) {
        TrNode<T> nodeToRemove = this.find(paData);
//        System.out.println("Printing node to remove");
        if (nodeToRemove == null) {
            System.out.println("NODE NOT FOUND");
            return null;
        }
        while (!nodeToRemove.isLeaf()) {

            TrNode<T> replacerNode = null;
            if (nodeToRemove.hasRight()) {
                replacerNode = this.inOrderOrFindMinMaxOrInsertSubtree(nodeToRemove.getRight(), false, false, false);
            }
            if (nodeToRemove.hasLeft()) {
                replacerNode = this.inOrderOrFindMinMaxOrInsertSubtree(nodeToRemove.getLeft(), true, false, false);
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

    private ArrayList<TrNode<T>> inorderMorris(TrNode<T> paNode) {
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

//        System.out.println("Amount of elements in the tree:");
//        System.out.println(nodesResult.size());

        return nodesResult;
    }

    // pridat enum ako parameter ktory bude odlisovat casy
    public TrNode<T> inOrderOrFindMinMaxOrInsertSubtree(TrNode<T> paNode, boolean minOrMax, boolean insertSubtree, boolean printTree) {
        //this.proccessNode(paNode);
        TrNode<T> minNode = paNode;
        TrNode<T> maxNode = paNode;
        int level = paNode.getLevel() - 1;
        if (paNode.isRoot()) {
            level = paNode.getLevel();
        }
        ArrayList<TrNode<T>> morrisNodes = this.inorderMorris(paNode);

        for (TrNode<T> morrisNode : morrisNodes) {

            if (printTree) {
                System.out.println("-------------------------------------------------");
                morrisNode.printNode();
                morrisNode.getData().printData();
            }
            
            if (morrisNode != paNode) {
                if (insertSubtree) {
                    System.out.println("--------- ACHTUNG ACHTUNG ---------");
                    System.out.println("********* STROM SA BALANCUJE *********");
                    morrisNode.getData().printData();
                    this.insert(morrisNode.getData());
                }


                if (morrisNode.getData().compareTo(minNode.getData(), level % this.dimensions) < 0) {

                    if (printTree) {
//                        System.out.println("Novy minnode");
//                        tTrNode.printNode();
//                        tTrNode.getData().printData();
                    }
                    minNode = morrisNode;
                }

                if (morrisNode.getData().compareTo(maxNode.getData(), level % this.dimensions) >= 0) {

                    if (printTree) {
                        System.out.println("Novy maxnode");
                        System.out.println("Urovne " + paNode.getLevel());
                        morrisNode.printNode();
                        morrisNode.getData().printData();
                    }
                    maxNode = morrisNode;
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
