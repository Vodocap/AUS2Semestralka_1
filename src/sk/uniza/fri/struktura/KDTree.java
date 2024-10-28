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
    private ArrayList<IData> duplicateData;
    private ArrayList<TrNode<T>> duplicateNodes;

    public KDTree(int paDimensions) {
        this.dimensions = paDimensions;
        this.duplicateData = new ArrayList<>();
        this.duplicateNodes = new ArrayList<>();

    }
    public void emplaceRoot(TrNode<T> paNode) {
        this.root = paNode;
    }

    public TrNode<T> getRoot() {
        return this.root;
    }


    public boolean insert(T data) {
        TrNode<T> paNode = new TrNode<>(data);
        System.out.println("INSERTING NODE (" + data.getDataAtD(0) + ", " + data.getDataAtD(1) + ")");
        if (this.root == null ) {
            this.emplaceRoot(paNode);
            paNode.setLevel(0);
//            System.out.println("Inserted node");
//            paNode.printNode();
//            paNode.getData().printData();
            return true;
        }
        boolean rightSubtree = false;
        TrNode<T> currentNode = this.root;
        TrNode<T> parentNode = null;
        int level = 0;

        if (paNode.getData().compareTo(currentNode.getData(), currentNode.getLevel() % this.dimensions) > 0) {
            rightSubtree = true;

        }

        while (currentNode != null) {
            int k = level % this.dimensions;
            parentNode = currentNode;


            int comparison = paNode.getData().compareTo(currentNode.getData(), k);

            if (comparison > 0) {
                currentNode = currentNode.getRight();
            }
            if (comparison < 0) {
                currentNode = currentNode.getLeft();
            }
            if (comparison == 0) {
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
        } else if (paNode.getData().compareTo(parentNode.getData(), finalDimension) < 0) {
            parentNode.setLeft(paNode);
        } else if (paNode.getData().compareTo(parentNode.getData(), finalDimension) == 0) {
            for (int i = 0; i < this.dimensions; i++) {
                if (paNode.getData().compareTo(parentNode.getData(), i) == 0) {
                    if (rightSubtree && !this.duplicateData.contains(paNode.getData())) {
                        this.duplicateData.add(paNode.getData());
                    }
                }
            }
            parentNode.setLeft(paNode);


        }

        return true;

    }


    private boolean insertNoDuplicates(T data) {
        TrNode<T> paNode = new TrNode<>(data);
        System.out.println("INSERTING NODE (" + data.getDataAtD(0) + ", " + data.getDataAtD(1) + ")");
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

            int comparison = paNode.getData().compareTo(currentNode.getData(), k);

            if (comparison > 0) {
                currentNode = currentNode.getRight();
            }
            if (comparison < 0) {
                currentNode = currentNode.getLeft();
            }
            if (comparison == 0) {
                currentNode = currentNode.getLeft();
            }

            level++;
        }


        paNode.setLevel(level);
        paNode.setParent(parentNode);
        int finalDimension = (level - 1) % this.dimensions;
        if (paNode.getData().compareTo(parentNode.getData(), finalDimension) > 0) {
            parentNode.setRight(paNode);
        } else if (paNode.getData().compareTo(parentNode.getData(), finalDimension) <= 0) {
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


    private void reinsertDuplicates() {
        System.out.println("---------------------REINSERTING DUPLICATES---------------------");
        ArrayList<TrNode<T>> rightSubtree = this.inorderMorris(this.root.getRight());
        for (TrNode<T> tTrNode : rightSubtree) {
            if (this.duplicateData.contains(tTrNode.getData())) {
                System.out.println("FOUND DUPLICATE NODE");
                this.duplicateNodes.add(tTrNode);

            }

        }
        if (!this.duplicateNodes.isEmpty()) {
            for (TrNode<T> duplicate : this.duplicateNodes) {
                System.out.println("REINSERTING DUPLICATE NODE");
                duplicate.getData().printData();
                TrNode<T> deletedNode = this.deleteNode(duplicate);
                if (this.find(duplicate.getData()) == null) {
                    this.insertNoDuplicates(duplicate.getData());
                }
            }
        }
    }

    public boolean delete(T paData) {
        TrNode<T> nodeToRemove = this.find(paData);

        if (nodeToRemove == null) {
            return false;
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
        this.duplicateData.remove(nodeToRemove.getData());
        if (!this.duplicateData.isEmpty() && this.root.hasRight() && !this.root.hasLeft()) {
            this.reinsertDuplicates();
        }

        return true;


    }

    private TrNode<T> deleteNode(TrNode<T> nodeToRemove) {
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


    }


    public ArrayList<TrNode<T>> inorderMorris(TrNode<T> paNode) {
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
                    morrisNode.getData().printData();
                    this.insert(morrisNode.getData());
                }


                if (morrisNode.getData().compareTo(minNode.getData(), level % this.dimensions) < 0) {

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
