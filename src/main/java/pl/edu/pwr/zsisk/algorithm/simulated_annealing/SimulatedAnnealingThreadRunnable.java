package pl.edu.pwr.zsisk.algorithm.simulated_annealing;

import java.util.List;

public class SimulatedAnnealingThreadRunnable implements Runnable {

    SimulatedAnnealingState state;

    SimulatedAnnealingThreadRunnable(SimulatedAnnealingState state) {
        this.state = state;
    }

    @Override
    public void run() {
        StopReason reason = StopReason.NONE;
        while ((reason = this.state.stopCondition.conditionMet(this.state.temperature, 10_000)) == StopReason.NONE) {
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
            this.state.incrementIteration();
        }
    }
}
