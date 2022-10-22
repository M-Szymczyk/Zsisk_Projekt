package pl.edu.pwr.zsisk.algorithm.tabu_search;


import org.apache.commons.collections4.CollectionUtils;

import java.util.Comparator;
import java.util.List;

/**
 * Basic implementation of {@link BasicNeighborSolutionLocator}, that doensn't have any Aspiration Criteria
 * and simply returns the non-tabu {@link Solution} with the lowest value.
 */
public class BasicNeighborSolutionLocator implements BestNeighborSolutionLocator {

    /**
     * Find the non-tabu {@link Solution} with the lowest value.<br>
     * This method doesn't use any Aspiration Criteria.
     */
    @Override
    public Solution findBestNeighbor(List<Solution> neighborsSolutions, final List<Solution> solutionsInTabu) {
        //remove any neighbor that is in tabu list
        CollectionUtils.filterInverse(neighborsSolutions, solutionsInTabu::contains);

        //sort the neighbors
        neighborsSolutions.sort(Comparator.comparingInt(Solution::getValue));

        //get the neighbor with the lowest value
        return neighborsSolutions.get(0);
    }

}