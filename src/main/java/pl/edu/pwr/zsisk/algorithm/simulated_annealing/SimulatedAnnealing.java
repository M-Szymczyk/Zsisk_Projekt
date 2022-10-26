package pl.edu.pwr.zsisk.algorithm.simulated_annealing;

import pl.edu.pwr.zsisk.algorithm.simulated_annealing.cooling.Cooling;
import pl.edu.pwr.zsisk.algorithm.simulated_annealing.cooling.GeometricalCooling;
import pl.edu.pwr.zsisk.graph.matrix.AdjacencyMatrix;

import java.util.LinkedList;
import java.util.List;
import java.util.Random;

public class SimulatedAnnealing {

    double currentTemperature;
    int iterationsAtSameTemperature;
    AdjacencyMatrix matrix;
    Cooling cooling;
    StopCondition stopCondition;

    public SimulatedAnnealing(AdjacencyMatrix matrix, double initialTemperature, int iterationsAtSameTemperature) {
        this.currentTemperature = initialTemperature;
        this.iterationsAtSameTemperature = iterationsAtSameTemperature;
        this.matrix = matrix;
        this.cooling = new GeometricalCooling(0.9999);
        this.stopCondition = new StopCondition(1.0, 10_000);

    }


    public SimulatedAnnealingSolution solve() {
        StopReason reason = StopReason.NONE;

        SimulatedAnnealingSolution currentSolution, bestSolution;
        currentSolution = this.getRandomInitialSolution();
        bestSolution = this.getRandomInitialSolution();

        for (int iter = 0; iter < this.iterationsAtSameTemperature; iter++) {

            while ((reason = this.stopCondition.conditionMet(currentTemperature, 10_000)) == StopReason.NONE) {

                List<Integer> possibleNeighbourSolutionPath = this.permutateTwoRandomVertices(currentSolution.path);
                SimulatedAnnealingSolution possibleNeighbourSolution = new SimulatedAnnealingSolution(possibleNeighbourSolutionPath);

                double probability = this.getProbabilityOfGoingToNextState(
                        currentSolution.getCost(this.matrix),
                        possibleNeighbourSolution.getCost(this.matrix),
                        this.currentTemperature);

                if (this.evaluateProbability(probability)) {
                    currentSolution = possibleNeighbourSolution;
                }

                if (currentSolution.getCost(this.matrix) < bestSolution.getCost(this.matrix)) {
                    bestSolution = currentSolution;
                }


                this.currentTemperature = this.cooling.changeTemperature(this.currentTemperature);
            }
        }

        //System.out.println(reason);
        return bestSolution;
    }

    private SimulatedAnnealingSolution getRandomInitialSolution() {
        int graphDimension = this.matrix.getGraphDimension();

        // create list with [0,1,2,3,4,....]
        List<Integer> initialPath = new LinkedList<>();
        for (int i = 0; i < graphDimension; i++) {
            initialPath.add(i);
        }

        // permutate the list x times
        int numberOfPermutations = new Random().nextInt(100, 200);
        for (int perm = 0; perm < numberOfPermutations; perm++) {
            initialPath = this.permutateTwoRandomVertices(initialPath);
        }

        return new SimulatedAnnealingSolution(initialPath);
    }

    private boolean evaluateProbability(double probability) {
        return new Random().nextDouble(0, 1) < probability;
    }

    private List<Integer> permutateTwoRandomVertices(List<Integer> path) {
        int firstVertexToSwap = new Random().nextInt(path.size());
        int secondVertexToSwap = new Random().nextInt(path.size());
        return this.permutateTwoVertices(path, firstVertexToSwap, secondVertexToSwap);
    }

    private List<Integer> permutateTwoVertices(List<Integer> path, int idx1, int idx2) {

        List<Integer> permutatedPath = new LinkedList<>(path);
        Integer valueOfElementOnIdx1 = permutatedPath.get(idx1);
        Integer valueOfElementOnIdx2 = permutatedPath.get(idx2);

        permutatedPath.set(idx1, valueOfElementOnIdx2);
        permutatedPath.set(idx2, valueOfElementOnIdx1);

        return permutatedPath;
    }

    private double getProbabilityOfGoingToNextState(int currentGoalFunctionValue, int newGoalFunctionValue, double temperature) {
        if (newGoalFunctionValue <= currentGoalFunctionValue) {
            return 1.0;
        } else {
            double boltzmannValue = Math.exp((currentGoalFunctionValue - newGoalFunctionValue) / temperature);
            return boltzmannValue;
        }
    }


}
