package structures;

import exceptions.ElementDoesntExistException;
import exceptions.EmptyCollectionException;
import exceptions.NoSuchElementsException;
import interfaces.ListADT;

import java.util.Arrays;
import java.util.Iterator;

public abstract class Arraylist<T> implements ListADT<T> {

    protected final int DEFAULTCAPACITY = 10;
    protected T[] list;
    protected int size;

    public Arraylist() {
        this.list = (T[]) new Object[DEFAULTCAPACITY];
        this.size = 0;
    }

    public Arraylist(int initialCapacity) {
        this.list = (T[]) new Object[initialCapacity];
        this.size = 0;
    }

    @Override
    public T removeFirst() throws EmptyCollectionException {
        if (isEmpty()) {
            throw new EmptyCollectionException("Lista vazia!");
        } else {
            T element = this.first();
            //System.arraycopy(this.array, 1, this.array, 0, this.size() - 1); não usar java.util
            shiftLeft(0);
            return element;
        }
    }

    public void shiftLeft(int start){
        for (int i = start; i < this.size(); i++){
            this.list[i] = this.list[i + 1];
            while (i < this.size()) {
                this.list[i + 1] = null;
                size--;
            }
        }
    }

    public void expandCapacity(){
        int tam = this.DEFAULTCAPACITY / 2;
        T[] newList = (T[]) new Object[this.DEFAULTCAPACITY + tam];
        for (int i = 0; i < this.list.length; i++) {
            newList[i] = list[i];
        }
        //System.arraycopy(this.stack, 0, newArray, 0, this.stack.length);
        this.list = newList;
    }

    @Override
    public T removeLast() throws EmptyCollectionException {
        if (isEmpty()) {
            throw new EmptyCollectionException("Lista vazia!");
        } else {
            T element = this.list[this.size - 1];
            this.list[this.size - 1] = null;
            this.size--;
            return element;
        }
    }

    @Override
    public T remove(T element) throws EmptyCollectionException, ElementDoesntExistException {
        if (isEmpty()) {
            throw new EmptyCollectionException("Lista vazia!");
        } else {
            if (!this.contains(element)){
                throw new ElementDoesntExistException("Elemento não existe");
            }
            int pos = this.find(element);
            T elementToRemove = this.list[pos];

            for (int i = pos; i < this.size - 1; i++) {
                this.list[i] = this.list[i + 1];
            }
            this.list[this.size - 1] = null;
            this.size--;
            return elementToRemove;
        }
    }

    public int find (T element){
        for (int i = 0; i < this.size; i++) {
            if (this.list[i].equals(element)) {
                return i;
            }
        }
        return -1;
    }

    @Override
    public T first() throws EmptyCollectionException {
        if (isEmpty()) {
            throw new EmptyCollectionException("Lista vazia!!");
        } else {
            return list[0];
        }
    }

    @Override
    public T last() throws EmptyCollectionException {
        if (isEmpty()) {
            throw new EmptyCollectionException("Lista vazia!!");
        } else {
            return list[this.size() - 1];
        }
    }

    @Override
    public boolean contains(T target) {
        for (int i = 0; i < this.size && this.list != null; i++) {
            if(this.list[i].equals(target)){
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean isEmpty() {
        return this.size() == 0;
    }

    @Override
    public int size() {
        return this.size;
    }

    @Override
    public Iterator<T> iterator() {
        return new MyIterator<>();
    }

    private class MyIterator<T> implements Iterator<T> {

        private int position;

        public MyIterator() {
            this.position = 0;
        }

        @Override
        public boolean hasNext() {
            return this.position < Arraylist.this.size();
        }

        @Override
        public T next() {
            if (hasNext()) {
                return (T) list[this.position++];
            } else {
                throw new NoSuchElementsException("Não existem mais elementos na lista!");
            }
        }
    }

    @Override
    public String toString() {
        return "ArrayList{" +
                "DEFAULTCAPACITY=" + DEFAULTCAPACITY +
                ", array=" + Arrays.toString(list) +
                ", size=" + size +
                '}';
    }
}
