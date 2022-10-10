package structures;

public class LinkedList <T>{

    private LinearNode<T> front;

    /**
     * Creates an empty linked list
     */
    public LinkedList() {
        this.front = null;
    }

    public LinearNode getFront() {
        return front;
    }

    public void setFront(LinearNode front) {
        this.front = front;
    }

    /**
     * Adds a new object to the linked list
     * @param newobject - new element
     */
    public void add(T newobject) {
        LinearNode<T> node = new LinearNode<T>(newobject);
        node.setNext(this.getFront());

        this.setFront(node);
    }

    /**
     * Removes the first element
     */
    public void remove() {
        if (this.getFront() == null){
            return;
        }
        this.setFront(this.getFront().getNext());
    }

    @Override
    public String toString() {
        return "LinkedList{" +
                "front=" + front +
                '}';
    }
}
