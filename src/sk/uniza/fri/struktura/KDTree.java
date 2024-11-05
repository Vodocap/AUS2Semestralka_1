package sk.uniza.fri.struktura;


import java.util.ArrayList;
import java.util.Stack;

/**
 * 8. 10. 2024 - 14:38
 *
 * @author matus
 */


/**
 * Trieda reprezentujúca K-D strom,
 * v konštruktore prijíma jeden paremeter,
 * int ktorý reprezentuje počet dimenzií v strome,
 * ako atribúty si uchováva ešte aj root a zoznam duplicitných dát pre neskoršie vymazanie a reinserciu.
 * V jeho nodoch sa nachádzajú sa v ňom prvky ktoré dedia interface IData
 *
 */
public class KDTree<T extends IData> {
    private TrNode<T> root;
    private final int dimensions;
    private ArrayList<IData> duplicateData;

    public KDTree(int paDimensions) {
        this.dimensions = paDimensions;
        this.duplicateData = new ArrayList<>();

    }

    /**
     * metóda emplaceRoot vloží do stromu prvok a nastací ho ako root
     * @param paNode nový node
     *
     */
    public void emplaceRoot(TrNode<T> paNode) {
        this.root = paNode;
    }

    /**
     * metóda getRoot vracia root stromu
     * @return vráti root
     *
     */
    public TrNode<T> getRoot() {
        return this.root;
    }


    /**
     * metóda insert vloží do stromu dáta, zaobalí ich do nodu a správne ho umiestni do stromu podľa pravidiel k-d stromu
     * @param data vkladané dáta
     * @return ak sa prvok správne umiestni vráti true
     *
     */

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

    /**
     * metóda find v strome nájde node s konkrétnymi dátami podľa ich interného ID
     * @param paData hľadané dáta
     * @return node s hľadanými dátami, ak sa však nenašli vráti null
     *
     */

    public TrNode<T> find(T paData) {
        TrNode<T> currentNode = this.root;
        int level = 0;



        while (currentNode != null) {
            int k = level % this.dimensions;
            if (paData.compareTo(currentNode.getData(), k) == 1) {
                currentNode = currentNode.getRight();

            } else if (paData.compareTo(currentNode.getData(), k) == -1) {
                currentNode = currentNode.getLeft();

            } else if (paData.compareTo(currentNode.getData(), k) == 0) {

                if (paData.equals(currentNode.getData(), true)) {
                    return currentNode;
                }
                currentNode = currentNode.getLeft();


            }
            level++;

        }
//
        return null;
    }

    /**
     * metóda findAll v strome nájde zoznam nodov s hľadanými dátami zhodnými na všetkých dimenziách
     * @param paData hľadané dáta
     * @return zoznam nodov s hľadanými dátami
     *
     */

    public ArrayList<TrNode<T>> findAll(T paData) {

        ArrayList<TrNode<T>> resultNodes = new ArrayList<>();
        TrNode<T> currentNode = this.root;
        int level = 0;

        while (currentNode != null) {
            int k = level % this.dimensions;
            if (paData.compareTo(currentNode.getData(), k) == 1) {
                currentNode = currentNode.getRight();

            } else if (paData.compareTo(currentNode.getData(), k) == -1) {
                currentNode = currentNode.getLeft();

            } else if (paData.compareTo(currentNode.getData(), k) == 0) {

                if (paData.equals(currentNode.getData(), false)) {
                    resultNodes.add(currentNode);
                }

                currentNode = currentNode.getLeft();



            }
            level++;
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
//        System.out.println("---------------------REINSERTING DUPLICATES---------------------");


        while (!this.duplicateData.isEmpty()) {
            TrNode<T> deletedNode = this.deleteNode(this.duplicateData.get(0).getCurrentNode());
            this.insert(deletedNode.getData());
            this.duplicateData.remove(deletedNode.getData());


        }


    }

    /**
     * metóda delete v strome nájde node s konkrétnymi dátami na vymazanie a odstráni ho postupným prehadzovaním dát až do listu, ak sa strom pokazí tak
     * ho vybratím a reinsertovaním duplikátov opraví
     * @param paData hľadané dáta
     * @return true ak sa node mohol odstrániť, false ak sa v strome nenašiel
     *
     */

    public boolean delete(T paData) {
        TrNode<T> nodeToRemove = this.find(paData);

        if (nodeToRemove == null) {
            return false;
        }
        while (!nodeToRemove.isLeaf()) {

            TrNode<T> replacerNode = null;
            if (nodeToRemove.hasLeft()) {
                replacerNode = this.findExtremeOrDuplicates(nodeToRemove.getLeft(), nodeToRemove.getLeft(),true, false, true);
            }
            if (nodeToRemove.hasRight()) {
                replacerNode = this.findExtremeOrDuplicates(nodeToRemove.getRight(), nodeToRemove.getRight(), false, false, true);
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
                replacerNode = this.findExtremeOrDuplicates(nodeToRemove.getLeft(), nodeToRemove.getLeft(),true, false, true);
            }
            if (nodeToRemove.hasRight()) {
                replacerNode = this.findExtremeOrDuplicates(nodeToRemove.getRight(), nodeToRemove.getRight(), false, false, true);
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


    private ArrayList<TrNode<T>> minOrMaxSearch(TrNode<T> paNode, boolean minOrMax) {
        ArrayList<TrNode<T>> nodesResult = new ArrayList<>();
        int level = paNode.getLevel() - 1;


        Stack<TrNode<T>> stack = new Stack<>();
        TrNode<T> currentNode = paNode;

        while (currentNode != null || !stack.isEmpty()) {
            while (currentNode != null) {

                stack.push(currentNode);
                if (minOrMax) {
                    if ((level % this.dimensions) == (currentNode.getLevel() % this.dimensions)) {

                        currentNode = currentNode.getRight();

                    } else {
                        currentNode = currentNode.getLeft();
                    }

                } else {
                    currentNode = currentNode.getLeft();
                }

            }

            TrNode<T> poppedNode = stack.pop();
            nodesResult.add(poppedNode);

            if ((level % this.dimensions) != (poppedNode.getLevel() % this.dimensions)) {
                currentNode = poppedNode.getRight();
            } else {
                currentNode = null;
            }

        }


        return nodesResult;
    }


    /**
     * metóda inorder urobí prehliadku stromu podľa morrisovho algoritmu a vráti všetky nody stromu
     * @param paNode node od ktorého začne prehliadka
     * @return ArrayList všetkých nodov v strome
     *
     */

    public ArrayList<TrNode<T>> inorder(TrNode<T> paNode) {
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


        return nodesResult;
    }

    private TrNode<T> findExtremeOrDuplicates(TrNode<T> compareNode, TrNode<T> startNode , boolean minOrMax, boolean findDuplicates, boolean searchExtremes) {
        //this.proccessNode(paNode);
        TrNode<T> minNode = compareNode;
        TrNode<T> maxNode = compareNode;
        int level = compareNode.getLevel() - 1;
        if (compareNode.isRoot()) {
            level = compareNode.getLevel();
        }


        if (findDuplicates) {
            ArrayList<TrNode<T>> duplicateCandidates = this.inorder(startNode);
            for (TrNode<T> duplicateCandidate : duplicateCandidates) {
                if (duplicateCandidate != compareNode) {
                    if (duplicateCandidate.getData().compareTo(compareNode.getData(), startNode.getLevel() % this.dimensions) == 0) {

                        this.duplicateData.add(duplicateCandidate.getData());
                    }
                }
            }
            this.duplicateData.remove(startNode.getData());




        }

        if (searchExtremes) {
            ArrayList<TrNode<T>> minMaxCandidates = this.minOrMaxSearch(startNode, minOrMax);
//        System.out.println("Nova prehliadka");
//        System.out.println(minMaxCandidates.size());
//        System.out.println("Stara prehliadka");
//        System.out.println(this.inorder(startNode).size());

            for (TrNode<T> minMaxCandidate : minMaxCandidates) {



                if (minMaxCandidate != compareNode) {


                    if (minMaxCandidate.getData().compareTo(minNode.getData(), level % this.dimensions) <= 0) {

                        minNode = minMaxCandidate;
                    }

                    if (minMaxCandidate.getData().compareTo(maxNode.getData(), level % this.dimensions) >= 0) {

                        maxNode = minMaxCandidate;
                    }
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
