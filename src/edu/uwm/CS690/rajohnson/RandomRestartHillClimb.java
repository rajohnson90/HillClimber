package edu.uwm.CS690.rajohnson;

public class RandomRestartHillClimb {

	private Heuristic h;
	private HillClimb hill;
	
	public RandomRestartHillClimb(Heuristic heuristic) {
		h = heuristic;
		hill = new HillClimb(h);
	}
	
	/**
	 * Perform random restart hill climbing search that restarts {@code t} times. 
	 * @param t The number of times to restart the during the random restart hill climbing search.
	 * @return The best {@link FoodSelectionNode} found after performing {@code t} instances of hill climbing starting at a random location. 
	 */
	public FoodSelectionNode findBestSolutionAfterTIterations(int t)
	{
		FoodSelectionNode bestSolution = new FoodSelectionNode();
		
		FoodSelectionNode startState = new FoodSelectionNode(generateRandomStartingState());
		
		bestSolution = hill.findMaximumFromStartState(startState);
		
		for(int i = 1; i < t; i++)
		{
			startState = new FoodSelectionNode(generateRandomStartingState());
			FoodSelectionNode currentState = hill.findMaximumFromStartState(startState);
			// If the result of the most recent hill climbing search is better than the previous result,
			// it is now the best solution.
			if(h.calculateHeuristic(currentState) <= h.calculateHeuristic(bestSolution))
			{
				bestSolution = currentState;
			}
		}
		
		return bestSolution;
	}

	/**
	 * Creates an integer array of size 20 that contains a random set of integers that total to 10. 
	 * @return An {@code int[]} of size 20 where the total of all values in the array add up to 10.
	 */
	private int[] generateRandomStartingState()
	{
		int[] startingState = new int[20];
		
		while(!(new FoodSelectionNode(startingState)).nutrientsGreaterThan110Percent())
		{
			int newFoodChoice = (int)Math.round(Math.random()*19);
			startingState[newFoodChoice]++;
		}
		return startingState;
	}
	
	public int getNodesGenerated()
	{
		return hill.getNodesGenerated();
	}
	
}
