package pl.edu.pwr.zsisk.algorithm.simulated_annealing;

import pl.edu.pwr.zsisk.algorithm.simulated_annealing.cooling.Cooling;
import pl.edu.pwr.zsisk.graph.matrix.AdjacencyMatrix;

public class SimulatedAnnealingState {

    double temperature;
    long totalIterations;
    int iterationsPerStep;
    int iterationsPerStepCounter;
    SimulatedAnnealingSolution currentSolution;
    SimulatedAnnealingSolution bestSolution;

    AdjacencyMatrix matrix;
    Cooling cooling;
    StopCondition stopCondition;

    public SimulatedAnnealingState(double initialTemperature, int iterationsPerStep, Cooling cooling,
                                   StopCondition stop, AdjacencyMatrix matrix) {
        this.iterationsPerStep = iterationsPerStep;
        this.temperature = initialTemperature;
        this.totalIterations = 0;
        this.iterationsPerStepCounter = 0;
        this.cooling = cooling;
        this.matrix = matrix;
        this.stopCondition = stop;
    }

    public SimulatedAnnealingSolution getCurrentSolution() {
        return this.currentSolution;
    }

    public SimulatedAnnealingSolution getBestSolution() {
        return this.bestSolution;
    }

    public synchronized void setCurrentSolution(SimulatedAnnealingSolution solution) {
        this.currentSolution = solution;
        if (this.bestSolution == null || solution.cost < this.bestSolution.cost) {
            //System.out.println(solution.cost);
            this.bestSolution = solution;
        }
    }
    public synchronized int incrementIteration() {

        if (this.iterationsPerStepCounter == this.iterationsPerStep) {
            this.iterationsPerStepCounter = 0;
            this.changeTemperature();
        } else {
            this.iterationsPerStepCounter++;
        }

        return this.iterationsPerStepCounter;
    }

    private void changeTemperature() {
        this.temperature = this.cooling.changeTemperature(this.temperature);
    }
}
