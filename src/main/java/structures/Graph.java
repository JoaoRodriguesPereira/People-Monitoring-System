package structures;

import exceptions.AlreadyExistsException;
import exceptions.EmptyCollectionException;
import exceptions.InvalidEdgeException;
import exceptions.VerticeAlreadyExistsException;
import interfaces.GraphADT;

import java.util.Iterator;

public class Graph<T> implements GraphADT<T> {

    protected final int DEFAULT_CAPACITY = 10;
    protected LinkedList<Integer>[] adjList;
    protected T[] vertices;
    protected int numVertices;

    /**
     * Creates a new empty graph
     */
    public Graph() {
        this.adjList = new LinkedList[DEFAULT_CAPACITY];
        this.numVertices = 0;
        this.vertices = (T[]) new Object[DEFAULT_CAPACITY];
        for (int i = 0; i < this.getAdjList().length; i++) {
            this.getAdjList()[i] = new LinkedList<>();
        }
    }

    public LinkedList<Integer>[] getAdjList() {
        return adjList;
    }

    /**
     * Return the vertices array
     *
     * @return vertices array
     */
    public T[] getVertices() {
        return vertices;
    }

    /**
     * Return vertice
     *
     * @param index position of the vertice
     * @return vertice
     */
    public T getVertice(int index) {
        T vertice = this.getVertices()[index];
        return vertice;
    }

    /**
     * Verify if index is valid
     *
     * @param index index to validate
     * @return true if index is valid else return false
     */
    protected boolean indexIsValid(int index) {
        return ((index >= 0) && (index < this.numVertices));
    }

    /**
     * Expand the capacity of vertices array and adjacency list
     */
    protected void expandCapacity() {
        T[] largerVertices = (T[]) (new Object[DEFAULT_CAPACITY * 2]);
        LinkedList<Integer>[] largerAdjList = new LinkedList[DEFAULT_CAPACITY * 2];
        for (int i = this.getAdjList().length; i < largerAdjList.length; i++) {
            largerAdjList[i] = new LinkedList<>();
        }

        for (int i = 0; i < this.numVertices; i++) {
            largerVertices[i] = this.vertices[i];
            largerAdjList[i] = this.adjList[i];
        }
        this.vertices = largerVertices;
        this.adjList = largerAdjList;
    }

    /**
     * Checks if an edge exists between two vertices
     *
     * @param startVertex  start vertice
     * @param targetVertex target vertice
     * @return true if exist or false if it doesn't
     */
    public boolean hasEdge(T startVertex, T targetVertex) {
        int index1 = getIndex(startVertex);
        int index2 = getIndex(targetVertex);

        if (indexIsValid(index1) && indexIsValid(index2)) {
            LinearNode<Integer> node = this.adjList[index1].getFront();
            while (node != null) {
                if (node.getElement() == index2) {
                    return true;
                }
                node = node.getNext();
            }
        }
        return false;
    }

    /**
     * Adds a vertex to the graph, expanding the capacity of the graph
     * if necessary. It also associates an object with the vertex.
     *
     * @param vertex the vertex to add to the graph
     * @throws VerticeAlreadyExistsException if @param vertex already exists
     */
    @Override
    public void addVertex(T vertex) throws VerticeAlreadyExistsException {
        for (int i = 0; i < this.numVertices; i++) {
            if (this.vertices[i].equals(vertex)) {
                throw new VerticeAlreadyExistsException("O vertice ja existe!");
            }
        }

        if (this.numVertices == this.vertices.length) {
            expandCapacity();
        }

        this.vertices[numVertices] = vertex;
        numVertices++;
    }

    @Override
    public void removeVertex(T vertex) {

    }

    /**
     * Returns the index of vertex received as parameter
     *
     * @param vertex is vertex to search
     * @return index of position of vertex if @param vertex has founded
     * or -1 if don't be founded
     */
    protected int getIndex(T vertex) {
        for (int i = 0; i < this.numVertices; i++) {
            if (this.vertices[i].equals(vertex)) {
                return i;
            }
        }
        return -1;
    }

    /**
     * Adds an edge
     *
     * @param vertex1 the first vertex
     * @param vertex2 the second vertex
     * @throws Exception if some vertice was invalid
     */
    @Override
    public void addEdge(T vertex1, T vertex2) throws Exception {
        int index1 = getIndex(vertex1);
        int index2 = getIndex(vertex2);

        if (indexIsValid(index1) && indexIsValid(index2)) {
            LinearNode<Integer> node = this.adjList[index1].getFront();
            while (node != null) {
                if (node.getElement() == index2) {
                    throw new AlreadyExistsException();
                }
                node = node.getNext();
            }
            this.getAdjList()[index1].add(index2);
            this.getAdjList()[index2].add(index1);
        } else {
            throw new Exception("Algum vertice e invalido");
        }
    }

    @Override
    public void removeEdge(T vertex1, T vertex2) throws InvalidEdgeException {

    }

    @Override
    public Iterator iteratorBFS(T startVertex) {
        return null;
    }

    @Override
    public Iterator iteratorDFS(T startVertex) throws EmptyCollectionException {
        return null;
    }

    /**
     * Returns an iterator with the list of the shortest path between two vertices
     *
     * @param startVertex  the starting vertex
     * @param targetVertex the ending vertex
     * @return an iterator
     */
    @Override
    public Iterator iteratorShortestPath(T startVertex, T targetVertex) {
        Integer x = getIndex(startVertex);
        LinkedQueue<Integer> traversalQueue = new LinkedQueue<>();
        UnorderedArrayList<T> resultList = new UnorderedArrayList<>();

        if (!indexIsValid(getIndex(startVertex)) /*|| (entrance_exit.size == 0)*/)
            return resultList.iterator();

        int[] pathLength = new int[numVertices];
        int[] predecessor = new int[numVertices];

        boolean[] visited = new boolean[numVertices];
        for (int i = 0; i < numVertices; i++)
            visited[i] = false;

        traversalQueue.enqueue(getIndex(startVertex));
        visited[getIndex(startVertex)] = true;
        pathLength[getIndex(startVertex)] = 0;
        predecessor[getIndex(startVertex)] = -1;

        while (!traversalQueue.isEmpty() && x != getIndex(targetVertex)) {
            try {
                x = traversalQueue.dequeue();

                /** Find all vertices adjacent to x that have
                 not been visited and queue them up */
                LinearNode<Integer> node = this.getAdjList()[x].getFront();
                while (node != null) {
                    if (!visited[node.getElement()]) {
                        visited[node.getElement()] = true;
                        traversalQueue.enqueue(node.getElement());
                        pathLength[node.getElement()] = pathLength[x] + 1;
                        predecessor[node.getElement()] = x;
                    }
                    node = node.getNext();
                }
            } catch (EmptyCollectionException e) {
                return resultList.iterator();
            }
        }

        LinkedStack<T> stack = new LinkedStack<>();
        int index = getIndex(targetVertex);
        stack.push(getVertice(index));

        if (predecessor[index] != -1) {
            do {
                if (pathLength[predecessor[index]] < pathLength[index]) {
                    index = predecessor[index];
                }
                stack.push(getVertice(index));
            } while (index != getIndex(startVertex));
        }

        while (!stack.isEmpty()) {
            try {
                resultList.addToRear(stack.pop());
            } catch (EmptyCollectionException e) {
                throw new RuntimeException(e);
            }
        }

        return resultList.iterator();
    }

    /**
     * Return the state of vertices array
     * @return true if it is empty or false if not
     */
    @Override
    public boolean isEmpty() {
        return numVertices == 0;
    }

    @Override
    public boolean isConnected() {
        return false;
    }

    /**
     * Return vertices array size
     * @return vertices array size
     */
    @Override
    public int size() {
        return this.numVertices;
    }

    @Override
    public String toString() {
        return null;
    }

    /**
     * Prints graph data
     */
    public void printGraph() {
        if (numVertices == 0) {
            System.out.println("O mapa está vazio");
        } else {

            System.out.print("\nLista de adjacências\n----------------\nÍndices\n\n");

            for (int i = 0; i < this.numVertices; i++) {
                System.out.print(i + "-> ");
                LinearNode<Integer> node = this.getAdjList()[i].getFront();
                while (node != null) {
                    System.out.print(node.getElement());
                    node = node.getNext();
                    if (node != null) {
                        System.out.print(" -> ");
                    }
                }
                System.out.print("\n");
            }

            System.out.print("\nVértices\n-------------\nÍndices\tDivisões\n\n");

            for (int i = 0; i < this.numVertices; i++) {
                System.out.print(i + "\t\t");
                System.out.print(this.vertices[i] + "\n");
            }
        }
    }
}
