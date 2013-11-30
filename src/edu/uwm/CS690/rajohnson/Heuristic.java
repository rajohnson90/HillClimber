package edu.uwm.CS690.rajohnson;

/**
 * Interface for a class that calculates the value h(n) for a given food choice in a hill-climbing or simulated annealing.
 *
 * @author Reed Johnson
 * @date 11.29.2013
 */
public interface Heuristic {
	/**
	 * @param currentNode
	 * @return
	 */
	public int calculateHeuristic(FoodSelectionNode currentNode);
}
