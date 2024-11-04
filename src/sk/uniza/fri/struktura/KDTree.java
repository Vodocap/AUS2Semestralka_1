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

    public KDTree(int paDimensions) {
        this.dimensions = paDimensions;
        this.duplicateData = new ArrayList<>();

    }
    public void emplaceRoot(TrNode<T> paNode) {
        this.root = paNode;
    }

    public TrNode<T> getRoot() {
        return this.root;
    }


    public boolean insert(T data) {
        TrNode<T> paNode = new TrNode<>(data);
        data.setCurrentNode(paNode);
        System.out.println("INSERTING NODE (" + data.getDataAtD(0) + ", " + data.getDataAtD(1) + ")");
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

                if (paData.equals(currentNode.getData(), true)) {
                    return currentNode;
                }
                currentNode = currentNode.getLeft();
                level++;

            }

        }
//
        return null;
    }

    public ArrayList<TrNode<T>> findAll(T paData) {

        ArrayList<TrNode<T>> resultNodes = new ArrayList<>();
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

                if (paData.equals(currentNode.getData(), false)) {
                    resultNodes.add(currentNode);
                }

                currentNode = currentNode.getLeft();
                level++;


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
        } else {
            this.root = null;
        }

    }



    private void reinsertDuplicates() {
        System.out.println("---------------------REINSERTING DUPLICATES---------------------");


        while (!this.duplicateData.isEmpty()) {
            TrNode<T> deletedNode = this.deleteNode(this.duplicateData.get(0).getCurrentNode());
            this.insert(deletedNode.getData());
            this.duplicateData.remove(deletedNode.getData());


        }


    }


    public boolean delete(T paData) {
        TrNode<T> nodeToRemove = this.find(paData);

        if (nodeToRemove == null) {
            return false;
        }
        while (!nodeToRemove.isLeaf()) {

            TrNode<T> replacerNode = null;
            if (nodeToRemove.hasLeft()) {
                replacerNode = this.findExtremeOrDuplicates(nodeToRemove.getLeft(), nodeToRemove.getLeft(),true, false, false);
            }
            if (nodeToRemove.hasRight()) {
                replacerNode = this.findExtremeOrDuplicates(nodeToRemove.getRight(), nodeToRemove.getRight(), false, false, false);
                this.findExtremeOrDuplicates(replacerNode, nodeToRemove ,  false, true, false);
            }

            if (replacerNode != null) {

                TrNode<T> temnode = new TrNode<>(nodeToRemove.getData());
                temnode.getData().setCurrentNode(temnode);

                nodeToRemove.setData(replacerNode.getData());
                nodeToRemove.getData().setCurrentNode(nodeToRemove);
                replacerNode.setData(temnode.getData());
                replacerNode.getData().setCurrentNode(replacerNode);
                nodeToRemove = replacerNode;
            }

        }

        this.disconnectNodeFully(nodeToRemove);
        this.duplicateData.remove(nodeToRemove.getData());
        if (!(this.root == null)) {
            if ((!this.duplicateData.isEmpty())) {
                this.reinsertDuplicates();
            }
        }



        return true;


    }

    private TrNode<T> deleteNode(TrNode<T> nodeToRemove) {
        if (nodeToRemove == null) {
            System.out.println("NODE NOT FOUND");
            return null;
        }
        TrNode<T> replacerNode = null;
        while (!nodeToRemove.isLeaf()) {

            if (nodeToRemove.hasLeft()) {
                replacerNode = this.findExtremeOrDuplicates(nodeToRemove.getLeft(), nodeToRemove.getLeft(),true, false, false);
            }
            if (nodeToRemove.hasRight()) {
                replacerNode = this.findExtremeOrDuplicates(nodeToRemove.getRight(), nodeToRemove.getRight(), false, false, false);
                this.findExtremeOrDuplicates(replacerNode, nodeToRemove ,  false, true, false);
            }
            if (replacerNode != null) {

                TrNode<T> temnode = new TrNode<>(nodeToRemove.getData());
                temnode.getData().setCurrentNode(temnode);

                nodeToRemove.setData(replacerNode.getData());
                nodeToRemove.getData().setCurrentNode(nodeToRemove);
                replacerNode.setData(temnode.getData());
                replacerNode.getData().setCurrentNode(replacerNode);
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

    // pridat enum ako parameter ktory bude odlisovat cases
    public TrNode<T> findExtremeOrDuplicates(TrNode<T> paNode, TrNode<T> paNodeMorris , boolean minOrMax, boolean findDuplicates, boolean printTree) {
        //this.proccessNode(paNode);
        TrNode<T> minNode = paNode;
        TrNode<T> maxNode = paNode;
        int level = paNode.getLevel() - 1;
        if (paNode.isRoot()) {
            level = paNode.getLevel();
        }

        ArrayList<TrNode<T>> morrisNodes = this.inorderMorris(paNode);

        if (findDuplicates) {
            ArrayList<TrNode<T>> duplicateCandidates = this.inorderMorris(paNodeMorris);
            for (TrNode<T> duplicateCandidate : duplicateCandidates) {
                if (duplicateCandidate != paNode) {
                    if (duplicateCandidate.getData().compareTo(paNode.getData(), paNodeMorris.getLevel() % this.dimensions) == 0) {
                        System.out.println("FOUND DUPLICATE");
                        duplicateCandidate.getData().printData();
                        this.duplicateData.add(duplicateCandidate.getData());
                    }
                }
            }
            this.duplicateData.remove(paNodeMorris.getData());




        }

        for (TrNode<T> morrisNode : morrisNodes) {

            if (printTree) {
                System.out.println("-------------------------------------------------");
                morrisNode.printNode();
                morrisNode.getData().printData();
            }



            if (morrisNode != paNode) {


                if (morrisNode.getData().compareTo(minNode.getData(), level % this.dimensions) <= 0) {

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
