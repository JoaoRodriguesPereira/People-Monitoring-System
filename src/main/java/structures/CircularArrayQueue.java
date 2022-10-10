package structures;

import exceptions.EmptyCollectionException;
import interfaces.QueueADT;

import java.util.Arrays;

public class CircularArrayQueue<T> implements QueueADT<T> {

    /**
     * constant to represent the default capacity of the array
     */
    private final int DEFAULT_CAPACITY = 4;

    private int count;
    /**
     * int that represents the front of the array
     */
    private int front;
    /**
     * int that represents the rear of the array
     */
    private int rear;
    /**
     * array of generic elements to represent the queue
     */
    private T[] queue;

    public CircularArrayQueue(int initialCapacity) {
        this.count = 0;
        this.front = 0;
        this.rear = 0;
        this.queue = (T[]) new Object[initialCapacity];
    }

    public CircularArrayQueue() {
        this.count = 0;
        this.front = 0;
        this.rear = 0;
        this.queue = (T[]) new Object[DEFAULT_CAPACITY];
    }

    @Override
    public void enqueue(T element) throws EmptyCollectionException {
        if (this.size() == this.queue.length) {
            expandCapacity();
            //throw new EmptyCollectionException("CircularArrayQueue cheia");
        }

        this.queue[this.rear] = element;
        this.rear = (this.rear + 1) % this.queue.length;
        this.count++;
    }

    public void expandCapacity() {
        int tam = DEFAULT_CAPACITY / 2;
        T[] newArray = (T[]) new Object[this.size() + tam];
        for (int i = 0; i < this.queue.length; i++) {
            newArray[i] = this.queue[this.front];
            this.front = (this.front + 1) % this.queue.length;
        }
        //System.arraycopy(this.queue, 0, newArray, 0, this.queue.length);
        this.front = 0;
        this.rear = this.count;
        this.queue = newArray;
    }

    @Override
    public T dequeue() throws EmptyCollectionException {
        if (isEmpty()) {
            throw new EmptyCollectionException("CircularArrayQueue vazia");
        }

        T result = this.queue[this.front];
        this.queue[this.front] = null;
        this.front = (this.front + 1) % this.queue.length;
        this.count--;
        return result;
    }

    @Override
    public T first() throws EmptyCollectionException {
        return this.queue[this.front];
    }

    @Override
    public boolean isEmpty() {
        return this.count == 0;
    }

    @Override
    public int size() {
        return this.count;
    }

    @Override
    public String toString() {
        return "CircularArrayQueue{" +
                "DEFAULT_CAPACITY=" + this.DEFAULT_CAPACITY +
                ", front=" + this.front +
                ", rear=" + this.rear +
                ", queue=" + Arrays.toString(this.queue) +
                '}';
    }
}
