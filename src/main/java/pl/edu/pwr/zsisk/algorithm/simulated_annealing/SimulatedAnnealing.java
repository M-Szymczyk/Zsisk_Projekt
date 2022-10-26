package pl.edu.pwr.zsisk.algorithm.simulated_annealing;

import pl.edu.pwr.zsisk.algorithm.simulated_annealing.cooling.GeometricalCooling;
import pl.edu.pwr.zsisk.graph.matrix.AdjacencyMatrix;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

public class SimulatedAnnealing {


    SimulatedAnnealingState state;

    public SimulatedAnnealing(AdjacencyMatrix matrix, double initialTemperature, int iterationsAtSameTemperature) {
        this.state = new SimulatedAnnealingState(
                initialTemperature,
                iterationsAtSameTemperature,
                new GeometricalCooling(0.999),
                new StopCondition(1.0, 10_000),
                matrix
        );
    }

    public SimulatedAnnealingSolution solveWithThreads(int numOfThreads) {
        this.state.setCurrentSolution(SimulatedAnnealing.getRandomInitialSolution(this.state.matrix));

        List<Thread> threads = new ArrayList<>(numOfThreads);
        for (int t = 0; t < numOfThreads; t++) {
            Thread executeThread = new Thread(new SimulatedAnnealingThreadRunnable(this.state));
            threads.add(executeThread);
        }
        threads.forEach(t -> t.start());

        threads.forEach(t -> {
            try {
                t.join();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        });

        return this.state.getBestSolution();
    }

    public SimulatedAnnealingSolution solve() {
        StopReason reason = StopReason.NONE;

        this.state.setCurrentSolution(SimulatedAnnealing.getRandomInitialSolution(this.state.matrix));

        while ((reason = this.state.stopCondition.conditionMet(this.state.temperature, 10_000)) == StopReason.NONE) {
            for (int iter = 0; iter < this.state.iterationsPerStep; iter++) {
                SimulatedAnnealingSolution currentSolution = this.state.getCurrentSolution();
                List<Integer> possibleNeighbourSolutionPath = SimulatedAnnealing.permutateTwoRandomVertices(currentSolution.path);
                SimulatedAnnealingSolution possibleNeighbourSolution = new SimulatedAnnealingSolution(possibleNeighbourSolutionPath, this.state.matrix);

                double probability = SimulatedAnnealing.getProbabilityOfGoingToNextState(
                        currentSolution.cost,
                        possibleNeighbourSolution.cost,
                        this.state.temperature);

                if (SimulatedAnnealing.evaluateProbability(probability)) {
                    this.state.setCurrentSolution(possibleNeighbourSolution);
                }

            }
            this.state.temperature = this.state.cooling.changeTemperature(this.state.temperature);
        }

        return this.state.getBestSolution();
    }

    static SimulatedAnnealingSolution getRandomInitialSolution(AdjacencyMatrix matrix) {
        int graphDimension = matrix.getGraphDimension();

        // create list with [0,1,2,3,4,....]
        List<Integer> initialPath = new LinkedList<>();
        for (int i = 0; i < graphDimension; i++) {
            initialPath.add(i);
        }

        // permutate the list x times
        int numberOfPermutations = new Random().nextInt(100, 200);
        for (int perm = 0; perm < numberOfPermutations; perm++) {
            initialPath = SimulatedAnnealing.permutateTwoRandomVertices(initialPath);
        }

        return new SimulatedAnnealingSolution(initialPath, matrix);
    }

    static boolean evaluateProbability(double probability) {
        return new Random().nextDouble(0, 1) < probability;
    }

    static List<Integer> permutateTwoRandomVertices(List<Integer> path) {
        int firstVertexToSwap = new Random().nextInt(path.size());
        int secondVertexToSwap = new Random().nextInt(path.size());
        return SimulatedAnnealing.permutateTwoVertices(path, firstVertexToSwap, secondVertexToSwap);
    }

    static List<Integer> permutateTwoVertices(List<Integer> path, int idx1, int idx2) {

        List<Integer> permutatedPath = new LinkedList<>(path);
        Integer valueOfElementOnIdx1 = permutatedPath.get(idx1);
        Integer valueOfElementOnIdx2 = permutatedPath.get(idx2);

        permutatedPath.set(idx1, valueOfElementOnIdx2);
        permutatedPath.set(idx2, valueOfElementOnIdx1);

        return permutatedPath;
    }

    static double getProbabilityOfGoingToNextState(int currentGoalFunctionValue, int newGoalFunctionValue, double temperature) {
        if (newGoalFunctionValue <= currentGoalFunctionValue) {
            return 1.0;
        } else {
            double boltzmannValue = Math.exp((currentGoalFunctionValue - newGoalFunctionValue) / temperature);
            return boltzmannValue;
        }
    }


}
