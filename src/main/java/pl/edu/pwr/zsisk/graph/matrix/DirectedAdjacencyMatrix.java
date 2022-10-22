package pl.edu.pwr.zsisk.graph.matrix;

import pl.edu.pwr.zsisk.graph.Edge;
import pl.edu.pwr.zsisk.graph.GraphException;

import java.util.Collection;

public class DirectedAdjacencyMatrix extends AdjacencyMatrix {
    public DirectedAdjacencyMatrix(String fileName) {
        super(fileName);
    }

    @Override
    public void addEdge(Edge edge) throws GraphException {
        if (matrix[edge.beginNode()][edge.endNode()] != -1) {
            throw new GraphException("Edge: " + edge + " already in graph!");
        }
        matrix[edge.beginNode()][edge.endNode()] = edge.weight();
    }

    @Override
    public void removeEdge(Edge edge) throws GraphException {
        if (matrix[edge.beginNode()][edge.endNode()] == -1) {
            throw new GraphException("Edge: " + edge + " removed from Graph!");
        }
        matrix[edge.beginNode()][edge.endNode()] = -1;
    }

    @Override
    public Collection<Edge> getEdgeCollection() {
        return super.getEdgeCollection();
    }

    @Override
    public Collection<Edge> getNeighbours(int nodeId) {
        return super.getNeighbours(nodeId);
    }
}
