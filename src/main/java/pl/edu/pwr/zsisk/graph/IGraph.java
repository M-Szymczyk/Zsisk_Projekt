package pl.edu.pwr.zsisk.graph;

import java.util.Collection;
import java.util.List;

public interface IGraph {
    /**
     * Method add edge to graph
     *
     * @param edge added edge
     * @throws GraphException if added edge is already in graph
     */
    void addEdge(Edge edge) throws GraphException;

    /**
     * Method remove edge from graph
     *
     * @param edge to be removed from graph
     * @throws GraphException if removed edge isn't in graph
     */
    void removeEdge(Edge edge) throws GraphException;

    /**
     * Method print graph in terminal
     */
    void printInTerminal();

    List<Edge> getEdgeListSortedByWeight();

    Collection<Edge> getEdgeCollection();

    Collection<Edge> getNeighbours(int nodeId);
}
