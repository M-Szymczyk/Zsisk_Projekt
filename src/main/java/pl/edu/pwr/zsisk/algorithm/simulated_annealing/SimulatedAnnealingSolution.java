package pl.edu.pwr.zsisk.algorithm.simulated_annealing;

import pl.edu.pwr.zsisk.graph.matrix.AdjacencyMatrix;

import java.util.List;

public class SimulatedAnnealingSolution {

    public SimulatedAnnealingSolution(List<Integer> path) {
        this.path = path;
    }

    List<Integer> path;

    public int getCost(AdjacencyMatrix matrix) {
        return matrix.getPathCost(this.path);
    }
}
