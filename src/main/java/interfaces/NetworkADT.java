package interfaces;

import exceptions.ElementDoesntExistException;

public interface NetworkADT<T> extends GraphADT<T> {

    /**
     * Inserts an edge between two vertices of this network.
     *
     * @param vertex1 the first vertex
     * @param vertex2 the second vertex
     * @param weight  the weight of this edge
     */
    public void addEdge(T vertex1, T vertex2, int weight) throws Exception;

    /**
     * Sets the weight of the edge between two vertices of this network.
     *
     * @param vertex1  the first vertex
     * @param vertex2  the second vertex
     * @param newWeight the weight of this edge
     */
    public void setEdgeWeight(T vertex1, T vertex2, int newWeight) throws Exception;

    /**
     * Returns the weights of an edge.
     *
     * @return the weights
     */
    public int getEdgeWeight(T vertex1, T vertex2) throws ElementDoesntExistException;

    /**
     * Returns the weight of the shortest path in this network.
     *
     * @param vertex1 the first vertex
     * @param vertex2 the second vertex
     */
    public int shortestPathWeight(T vertex1, T vertex2);
}
