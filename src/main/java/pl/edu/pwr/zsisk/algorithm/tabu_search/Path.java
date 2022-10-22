package pl.edu.pwr.zsisk.algorithm.tabu_search;

import pl.edu.pwr.zsisk.graph.IGraph;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Path implements Solution {
    private final List<Integer> path;
    private final IGraph graph;

    public Path(List<Integer> path, IGraph graph) {
        this.path = path;
        this.graph = graph;
    }

    public Path(int startNode, IGraph graph) {
        this.graph = graph;
        this.path = new ArrayList<>();
        path.add(startNode);
        for (int i = 0; i < graph.getGraphDimension(); i++) {
            if (i != startNode)
                path.add(i);
        }
        path.add(startNode);
    }

    @Override
    public Integer getValue() {
        var sum = 0;
        for (int i = 0; i < path.size() - 1; i++) {
            sum += graph.getEdgeWeight(path.get(i), path.get(i + 1));
        }
        return sum;
    }

    @Override
    public List<Solution> getNeighbors() {
        var neighbors = new ArrayList<>(path);
        Collections.shuffle(neighbors.subList(1, neighbors.size() - 1));
        var result = new ArrayList<Solution>();
        result.add(new Path(neighbors, graph));
        return result;
    }

    @Override
    public String toString() {
        return "Path{" +
                "path=" + path +
                '}';
    }


}
