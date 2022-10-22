package pl.edu.pwr.zsisk.algorithm.tabu_search;

import org.apache.commons.collections4.IteratorUtils;

import java.util.List;

public class TabuSearch {
    private final TabuList tabuList;
    private final StopCondition stopCondition;
    private final BestNeighborSolutionLocator solutionLocator;

    /**
     * Construct a {@link TabuSearch} object
     *
     * @param tabuList        the tabu list used in the algorithm to handle tabus
     * @param stopCondition   the algorithm stop condition
     * @param solutionLocator the best neighbor solution locator to be used in each algorithm iteration
     */
    public TabuSearch(TabuList tabuList, StopCondition stopCondition, BestNeighborSolutionLocator solutionLocator) {
        this.tabuList = tabuList;
        this.stopCondition = stopCondition;
        this.solutionLocator = solutionLocator;
    }

    /**
     * Execute the algorithm to perform a minimization.
     *
     * @param initialSolution the start point of the algorithm
     * @return the best solution found in the given conditions
     */
    public Solution run(Solution initialSolution) {
        Solution bestSolution = initialSolution;
        Solution currentSolution = initialSolution;

        Integer currentIteration = 0;
        while (!stopCondition.mustStop(++currentIteration, bestSolution)) {

            List<Solution> candidateNeighbors = currentSolution.getNeighbors();
            List<Solution> solutionsInTabu = IteratorUtils.toList(tabuList.iterator());

            Solution bestNeighborFound = solutionLocator.findBestNeighbor(candidateNeighbors, solutionsInTabu);
            if (bestNeighborFound.getValue() < bestSolution.getValue()) {
                bestSolution = bestNeighborFound;
            }

            tabuList.add(currentSolution);
            currentSolution = bestNeighborFound;

            tabuList.updateSize(currentIteration, bestSolution);
        }

        return bestSolution;
    }
}