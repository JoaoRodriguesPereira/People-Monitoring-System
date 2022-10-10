package structures;

import exceptions.ComparableException;
import interfaces.OrderedListADT;

import java.util.Arrays;

public class OrderedArrayList<T> extends Arraylist<T> implements OrderedListADT<T> {

    public OrderedArrayList() {
        super();
    }

    public OrderedArrayList(int initialCapacity) {
        super(initialCapacity);
    }
    //elemento em si tem um comparable default. adicionar overload de add com um comparable T

    @Override
    public void add(T element) {
        if (element instanceof Comparable) {
            Comparable<T> x = (Comparable<T>) element;

            //Se a lista estiver cheia
            if (this.size == this.list.length) {
                this.expandCapacity();
            }

            //Inserção no início com o array vazio
            if (this.isEmpty()) {
                this.list[this.size] = element;
                this.size++;

                return;
            } else {
                for (int i = 0; i < this.size; i++) {
                    if (x.compareTo(this.list[i]) < 0) {
                        for (int j = this.size - 1; j >= i; j--) {
                            this.list[j + 1] = this.list[j];
                        }

                        this.list[i] = element;
                        this.size++;

                        return;
                    }
                }
            }

            //Inserção no fim
            this.list[this.size] = element;
            this.size++;
        } else {
            try {
                throw new ComparableException("O elemento não é comparavel!");
            } catch (ComparableException e) {
                e.printStackTrace();
            }
        }

    }

    @Override
    public String toString() {
        return "OrderedArrayList{" +
                "DEFAULTCAPACITY=" + DEFAULTCAPACITY +
                ", list=" + Arrays.toString(list) +
                ", size=" + size +
                '}';
    }

    @Override
    public T get(int position) {
        return this.list[position];
    }
}
