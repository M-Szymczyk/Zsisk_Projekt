package pl.edu.pwr.zsisk.algorithm.simulated_annealing;

import pl.edu.pwr.zsisk.graph.matrix.AdjacencyMatrix;

import java.util.List;

public class SimulatedAnnealingSolution {

    public SimulatedAnnealingSolution(List<Integer> path, AdjacencyMatrix matrix) {
        this.path = path;
        this.cost = matrix.getPathCost(this.path);
    }

    List<Integer> path;
    public int cost;
}
