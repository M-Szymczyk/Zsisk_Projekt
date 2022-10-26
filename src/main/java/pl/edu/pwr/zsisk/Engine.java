package pl.edu.pwr.zsisk;

import pl.edu.pwr.zsisk.algorithm.simulated_annealing.SimulatedAnnealing;
import pl.edu.pwr.zsisk.algorithm.simulated_annealing.SimulatedAnnealingSolution;
import pl.edu.pwr.zsisk.algorithm.tabu_search.*;
import pl.edu.pwr.zsisk.graph.IGraph;
import pl.edu.pwr.zsisk.graph.matrix.AdjacencyMatrix;
import pl.edu.pwr.zsisk.graph.matrix.DirectedAdjacencyMatrix;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class Engine {
    private static IGraph graph;

    public static void main(String[] args) {
        graph = new DirectedAdjacencyMatrix("br17.atsp");

        performSimulatedAnnealingTest(100, 10);

        performTabuSearchTest(100, 100000);
        performThreadTabuSearchTest(10, 100, 100000);

    }

    private static void displayResult(long start, Integer result, long finish) {
        System.out.println("Obliczono w:" + ((finish - start) / Math.pow(10, 9)) + " Wynik:" + result + " Błąd:" + ((((double) result) / 43) - 1.0));
    }

    static void performTabuSearchTest(int tabuSize, int iterations) {
        long start = System.nanoTime();
        var result = (new TabuSearch(new StaticTabuList(tabuSize), new IterationsStopCondition(iterations),
                new BasicNeighborSolutionLocator())).run(new Path(0, graph)).getValue();
        long finish = System.nanoTime();
        displayResult(start, result, finish);
    }

    static void performThreadTabuSearchTest(int threadNumber, int tabuSize, int iterations) {
        long start = System.nanoTime();
        var bestResult = Integer.MAX_VALUE;
        ExecutorService executor = Executors.newSingleThreadExecutor();
        ArrayList<Future<Integer>> futureList = new ArrayList<>();
        for (int i = 0; i < threadNumber; i++) {
            futureList.add(executor.submit(() -> new TabuSearch(new StaticTabuList(tabuSize), new IterationsStopCondition(iterations),
                    new BasicNeighborSolutionLocator()).run(new Path(0, graph)).getValue()));
        }
        for (int i = 0; i < threadNumber; i++) {
            try {
                if (futureList.get(i).isDone()) {
                    if (futureList.get(i).get() < bestResult) {
                        bestResult = futureList.get(i).get();
                    }
                }
            } catch (InterruptedException | ExecutionException e) {
                throw new RuntimeException(e);
            }
        }

        long finish = System.nanoTime();
        displayResult(start, bestResult, finish);
    }

    static void performSimulatedAnnealingTest(int initialTemperature, int iterationsAtSameTemperature) {
        long start = System.nanoTime();
        SimulatedAnnealing sim = new SimulatedAnnealing((AdjacencyMatrix) graph, initialTemperature, iterationsAtSameTemperature);
        SimulatedAnnealingSolution sol = sim.solve();

        long finish = System.nanoTime();
        displayResult(start, sol.getCost((AdjacencyMatrix) graph), finish);
    }
}