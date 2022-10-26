package pl.edu.pwr.zsisk.algorithm.simulated_annealing;

public class SimulatedAnnealingState {

    double temperature;
    long totalIterations;
    int iterationsPerStep;
    int iterationsPerStepCounter;
    SimulatedAnnealingSolution currentSolution;
    SimulatedAnnealingSolution bestSolution;

    public SimulatedAnnealingState(double initialTemperature, int iterationsPerStep){
        this.iterationsPerStep =iterationsPerStep;
        this.temperature = initialTemperature;
        this.totalIterations = 0;
        this.iterationsPerStepCounter = 0;
    }

    public SimulatedAnnealingSolution getCurrentSolution(){
        return this.currentSolution;
    }

    public SimulatedAnnealingSolution getBestSolution(){
        return this.bestSolution;
    }

    public void setCurrentSolution(SimulatedAnnealingSolution solution){
        this.currentSolution = solution;
        if(solution.cost < this.bestSolution.cost){
            this.bestSolution = solution;
        }
    }
}
