package pl.edu.pwr.zsisk.graph.matrix;

import pl.edu.pwr.zsisk.graph.Edge;
import pl.edu.pwr.zsisk.graph.GraphException;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.List;

public class AdjacencyMatrix extends AbstractMatrixGraph {
    public AdjacencyMatrix(String fileName) {
        super(fileName);
    }

    @Override
    public void addEdge(Edge edge) throws GraphException {
        if (matrix[edge.beginNode()][edge.endNode()] != -1 || matrix[edge.endNode()][edge.beginNode()] != -1) {
            throw new GraphException("Edge: " + edge + " already in graph!");
        }
        matrix[edge.beginNode()][edge.endNode()] = edge.weight();
        matrix[edge.endNode()][edge.beginNode()] = edge.weight();
    }

    @Override
    public void removeEdge(Edge edge) throws GraphException {
        if (matrix[edge.beginNode()][edge.endNode()] == -1 || matrix[edge.endNode()][edge.beginNode()] == -1) {
            throw new GraphException("Edge: " + edge + " removed from Graph!");
        }
        matrix[edge.beginNode()][edge.endNode()] = -1;
        matrix[edge.endNode()][edge.beginNode()] = -1;
    }

    @Override
    public List<Edge> getEdgeListSortedByWeight() {
        return getEdgeCollection().stream().sorted(Comparator.comparingInt(Edge::weight)).toList();
    }

    @Override
    public Collection<Edge> getEdgeCollection() {
        Collection<Edge> edges = new ArrayList<>();
        for (int i = 0; i < getGraphDimension(); i++) {
            for (int j = 0; j < getGraphDimension(); j++) {
                if (matrix[i][j] != -1) {
                    edges.add(new Edge(i, j, matrix[i][j]));
                }
            }
        }
        return edges;
    }

    @Override
    public Collection<Edge> getNeighbours(int nodeId) {
        Collection<Edge> edges = new ArrayList<>();
        for (int i = 0; i < getGraphDimension(); i++) {
            if (matrix[nodeId][i] != -1) {
                edges.add(new Edge(nodeId, i, matrix[nodeId][i]));
            }
        }
        return edges;
    }
}
