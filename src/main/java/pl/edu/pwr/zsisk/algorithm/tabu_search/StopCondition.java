package pl.edu.pwr.zsisk.algorithm.tabu_search;

public interface StopCondition {

    /**
     * Check if the tabu search algorithm must be stoped
     *
     * @param currentIteration  the current algorithm iteration
     * @param bestSolutionFound the best solution found so far
     * @return true if the algorithm must stop, false otherwise
     */
    Boolean mustStop(Integer currentIteration, Solution bestSolutionFound);
}