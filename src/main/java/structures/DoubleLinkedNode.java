package structures;

public class DoubleLinkedNode <T>{

    private T data;
    private DoubleLinkedNode<T> next;
    private DoubleLinkedNode<T> previous;

    public DoubleLinkedNode() {
        this.next = null;
        this.previous = null;
    }

    public DoubleLinkedNode(T data) {
        this.data = data;
        this.next = null;
        this.previous = null;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public DoubleLinkedNode<T> getNext() {
        return next;
    }

    public void setNext(DoubleLinkedNode<T> next) {
        this.next = next;
    }

    public DoubleLinkedNode<T> getPrevious() {
        return previous;
    }

    public void setPrevious(DoubleLinkedNode<T> previous) {
        this.previous = previous;
    }

}
