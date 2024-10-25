package sk.uniza.fri.struktura;

public interface IData<T> {



    int compareTo(IData<T> paData, int dimension);
    boolean compareWholeTo(IData<T> paData, boolean compareID);
    T getDataAtD(int dimension);
    public void printData();
    void deepSwapData(IData<T> paData);
    String getID();

}
