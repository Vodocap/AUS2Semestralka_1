package sk.uniza.fri.struktura;

public interface IData<T> {



    int compareTo(IData<T> paData, int dimension);
    T getDataAtD(int dimension);
    public void printData();
}
