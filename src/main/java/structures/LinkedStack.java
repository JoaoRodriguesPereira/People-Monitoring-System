package structures;

import exceptions.EmptyCollectionException;
import interfaces.StackADT;

public class LinkedStack<T> implements StackADT<T> {

    private int count;
    private LinearNode<T> node;

    /**
     * Creates an empty linked stack
     */
    public LinkedStack() {
        this.count = 0;
        this.node = null;
    }

    /**
     * Adds new element to the linked stack
     * @param element element to be pushed onto stack
     */
    @Override
    public void push(T element) {
        LinearNode<T> newNode = new LinearNode<>(element);
        if (count == 0) {
            this.node = newNode;
        } else {
            newNode.setNext(this.node);
            this.node = newNode;
        }
        count++;
    }

    /**
     * Removes the element at the top of the stack and returns the element
     * @return element
     * @throws EmptyCollectionException if stack is empty
     */
    @Override
    public T pop() throws EmptyCollectionException {
        if (isEmpty()) throw new EmptyCollectionException("Empty Stack");
        T result = this.node.getElement();
        this.node = this.node.getNext();
        count--;
        return result;
    }

    /**
     * Returns the element at the top of the stack
     * @return element at the top
     * @throws EmptyCollectionException if stack is empty
     */
    @Override
    public T peek() throws EmptyCollectionException {
        if (isEmpty())
            throw new EmptyCollectionException("Empty Stack");
        return this.node.getElement();
    }

    /**
     * Checks if stack is empty
     * @return count
     */
    @Override
    public boolean isEmpty() {
        return this.count == 0;
    }

    /**
     * Returns the stack size
     * @return stack size
     */
    @Override
    public int size() {
        return count;
    }

    @Override
    public String toString() {
        return "LinkedStack{" +
                "top=" + count +
                ", node=" + node +
                '}';
    }

    public void printLinkedStack(){
        LinearNode<?> node = this.node;

        while (node != null) {
            System.out.println(node.getElement());
            node = node.getNext();
        }
    }

}
