package structures;

import exceptions.EmptyCollectionException;
import interfaces.QueueADT;

public class LinkedQueue<T> implements QueueADT<T> {

    private int count;
    private LinearNode<T> front;
    private LinearNode<T> rear;

    /**
     * Creates an empty queue
     */
    public LinkedQueue() {
        this.count = 0;
        this.front = null;
        this.rear = null;
    }

    /**
     * Adds an element to the queue
     * @param element the element to be added to
     *                the rear of this queue
     */
    @Override
    public void enqueue(T element) {
        LinearNode<T> newNode= new LinearNode<>(element);
        if (this.isEmpty()) {
            this.front = newNode;
            this.rear = newNode;
        } else {
            this.rear.setNext(newNode);
            this.rear = newNode;
        }
        this.count++;
    }

    /**
     * Removes the first queue element
     * @return element removed
     * @throws EmptyCollectionException if queue is empty
     */
    @Override
    public T dequeue() throws EmptyCollectionException {
        if (isEmpty()) {
            throw new EmptyCollectionException("Queue vazia!");
        }

        T result = this.front.getElement();
        if (this.count == 1) {
            this.rear = null;
        }
        this.front = this.front.getNext();

        this.count--;
        return result;
    }

    /**
     * Returns the first queue element
     * @return the first element
     * @throws EmptyCollectionException if queue is empty
     */
    @Override
    public T first() throws EmptyCollectionException {
        if (isEmpty()) {
            throw new EmptyCollectionException("Queue Vazia!");
        }

        return this.front.getElement();
    }

    /**
     * Returns the queue state
     * @return true if it is empty or false if not
     */
    @Override
    public boolean isEmpty() {
        return this.count == 0;
    }

    /**
     * Returns the queue size
     * @return queue size
     */
    @Override
    public int size() {
        return this.count;
    }

    @Override
    public String toString() {
        return "LinkedQueue{" +
                "count=" + this.count +
                ", front=" + this.front +
                ", rear=" + this.rear +
                '}';
    }

    public void printLinkedQueue(){
        LinearNode<?> node = this.front;
        int count = 1;
        while (node != null){
            System.out.print(count);
            System.out.print("-" + node.getElement());
            count++;
            node = node.getNext();
            if (node != null){
                System.out.print(", ");
            }
        }
    }
}
