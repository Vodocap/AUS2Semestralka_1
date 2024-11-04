package sk.uniza.fri.struktura;

public interface IData<T> {



    int compareTo(IData<T> paData, int dimension);
    boolean equals(IData<T> paData, boolean compareID);
    T getDataAtD(int dimension);
    public void printData();
    IData<T> makeCopy();
    String getID();
    void setID(String ID);
    public TrNode<T> getCurrentNode();
    public void setCurrentNode(TrNode<T> currentNode);

}
