package structures;

import exceptions.ElementDoesntExistException;
import interfaces.UnorderedListADT;

import java.util.Arrays;

public class UnorderedArrayList<T> extends Arraylist<T> implements UnorderedListADT<T> {

    /**
     * Creates an empty unordered list
     */
    public UnorderedArrayList() {
        super();
    }

    /**
     * Creates an unordered list with default size
     * @param initialCapacity - initial capacity
     */
    public UnorderedArrayList(int initialCapacity) {
        super(initialCapacity);
    }

    /**
     * Adds an element at the beginning
     * @param element the element to be added to this list
     */
    @Override
    public void addToFront(T element) {
        if (this.size() == this.list.length) {
            expandCapacity();
        }
        //move todos os elementos uma vez para a direita
        shiftRight();
        //adiciona o elemento no indice 0
        this.list[0] = element;
        this.size++;
    }

    /**
     * It shifts elements one time to the right
     */
    public void shiftRight() {
        for (int i = this.size - 1; i >= 0; i--) {
            this.list[i + 1] = this.list[i];
        }
    }

    /**
     * Adds element to the rear
     * @param element the element to be added to this list
     */
    @Override
    public void addToRear(T element) {
        if (this.size() == this.list.length) {
            expandCapacity();
        }
        this.list[this.size] = element;
        this.size++;
    }

    /**
     * Adds and element right after another element
     * @param element the element to be added to this list
     * @param target  the element after that want to add the element
     * @throws ElementDoesntExistException if element doesn't exist
     */
    @Override
    public void addAfter(T element, T target) throws ElementDoesntExistException {
        //se a lista estiver vazia expande a capacidade
        if (this.size() == this.list.length) {
            expandCapacity();
        }
        //verifica se o target existe
        int pos = this.find(target);

        //se o target não existir
        if (pos == -1) {
            throw new ElementDoesntExistException("Elemento não existe!");
        }

        //percorre a lista ao contrário, até encontrar o target, move os elementos uma vez para a direita
        for (int i = this.size - 1; i > pos; i--) {
            this.list[i + 1] = this.list[i];
        }

        //a lista na posição do target + 1 é ocupada por element, e aumenta-se o size
        this.list[pos + 1] = element;
        this.size++;
    }

    @Override
    public String toString() {
        return "UnorderedArrayList{" +
                "DEFAULTCAPACITY=" + DEFAULTCAPACITY +
                ", list=" + Arrays.toString(list) +
                ", size=" + size +
                '}';
    }

    /**
     * Returns the element of a position
     * @param position - position
     * @return element
     */
    @Override
    public T get(int position) {
        return this.list[position];
    }
}
