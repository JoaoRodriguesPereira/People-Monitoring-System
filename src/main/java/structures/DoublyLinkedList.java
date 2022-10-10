package structures;

public class DoublyLinkedList <T>{

    private DoubleLinkedNode<T> head;
    private DoubleLinkedNode<T> tail;
    private int size = 0;

    public DoublyLinkedList() {
        this.head = null;
        this.tail = null;
        this.size = 0;
    }

    public DoubleLinkedNode<T> getHead() {
        return head;
    }

    public void setHead(DoubleLinkedNode<T> head) {
        this.head = head;
    }

    public DoubleLinkedNode<T> getTail() {
        return tail;
    }

    public void setTail(DoubleLinkedNode<T> tail) {
        this.tail = tail;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public void addFirst(T element){
        DoubleLinkedNode<T> newHead = new DoubleLinkedNode<>(element);

        if (this.size == 0) {
            this.head = newHead;
            this.tail = newHead;
        } else {
            newHead.setNext(this.head);
            //newHead vai ser atribuido à ref do head
            this.head.setPrevious(newHead);
            //newHead fica a ser o primeiro elemento
            this.head = newHead;
        }
        this.size++;
    }

    public void removeFirst() {
        if (this.isEmpty()) {
            return;
        }

        DoubleLinkedNode<T> newHead = this.getHead().getNext();
        if (newHead != null) {
            newHead.setPrevious(null);
            this.setHead(newHead);
        } else { //head==tail
            this.setHead(null);
            this.setTail(null);
        }
        this.size--;
    }

    public void removeLast() {
        if (this.isEmpty()) {
            return;
        }

        DoubleLinkedNode<T> newTail = this.getTail().getPrevious();
        if (newTail != null) {
            newTail.setNext(null);
            this.setTail(newTail);
        } else { //head==tail
            this.setHead(null);
            this.setTail(null);
        }
        this.size--;
    }

    public boolean isEmpty() {
        return size == 0;
    }

    public void printList() {
        DoubleLinkedNode node = this.getHead();

        while(node != null){
            System.out.print(node.getData() + "->");
            node = node.getNext();
        }
    }

    @Override
    public String toString() {
        return "DoublyLinkedList{" +
                "head=" + head.getData() +
                ", tail=" + tail.getData() +
                ", size=" + size +
                '}';
    }

}
