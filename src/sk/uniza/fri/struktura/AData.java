package sk.uniza.fri.struktura;

public abstract class AData<T> {
    protected T data;

    public AData(T data) {
        this.data = data;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public abstract int compareTo(AData<T> paData);
}
