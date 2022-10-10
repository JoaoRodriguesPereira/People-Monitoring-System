package interfaces;

import exceptions.ElementDoesntExistException;

public interface UnorderedListADT<T> extends ListADT<T> {

    /**
     * Adds the specified element
     * to the front of this list
     *
     * @param element the element to be added to this list
     */
    public void addToFront(T element);

    /**
     * Adds the specified element
     * to the rear of this list
     *
     * @param element the element to be added to this list
     */
    public void addToRear(T element);

    /**
     * Adds the specified element
     * to after an elements of this list
     *
     * @param element the element to be added to this list
     * @param target  the element after that want to add the element
     */
    public void addAfter(T element, T target) throws ElementDoesntExistException;

}
