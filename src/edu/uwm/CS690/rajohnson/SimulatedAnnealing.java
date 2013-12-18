package edu.uwm.CS690.rajohnson;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Random;

public class SimulatedAnnealing {

	Heuristic h;
	TempScheduler ts;
	private int nodesGenerated = 0;
	
	public SimulatedAnnealing(Heuristic heur, TempScheduler scheduler) {
		h = heur;
		ts = scheduler;
	}

	/**
	 * Performs simulated annealing using a random starting state generated such that 10 random
	 * food items are selected as part of the starting state.
	 * @return The best solution that could be found before the temperature reached 0.
	 */
	public FoodSelectionNode findBestSolutionWhenCool()
	{
		return findBestSolutionWhenCool(new FoodSelectionNode(generateRandomStartingState()));
	}
	
	/**
	 * Performs simulated annealing starting from the node {@code startingState}.
	 * @param startingState A {@link FoodSelectionNode} to start the solution from.
	 * @return The best solution that could be found before the temperature reached 0.
	 */
	public FoodSelectionNode findBestSolutionWhenCool(FoodSelectionNode startingState)
	{
		//ArrayList<FoodSelectionNode> startingNeighbors = generateNeighbors(startingState);
		FoodSelectionNode previousBest = startingState;
		//FoodSelectionNode nextBestNeighbor = findBestNeighbor(startingNeighbors);

		HashSet<FoodSelectionNode> visitedNodes = new HashSet<FoodSelectionNode>();
		visitedNodes.add(startingState);

		/*System.out.println("nextBestNeighbor state: " + nextBestNeighbor);
		System.out.println("\t" + nextBestNeighbor.totalNutrientsAndCostAsString());*/
		
		//visitedNodes.add(nextBestNeighbor);
		int timeStep = 0;
		Random r = new Random();
		// While the nutrients don't exceed 95% or the nextBestNeighbor is better than the previous best state 
		while(true)
		{
			double temperature = ts.getTemp(timeStep);
			timeStep++;
			if(temperature == 0.0)
			{
				return previousBest;
			}
			ArrayList<FoodSelectionNode> currentNeighbors = generateNeighbors(previousBest);
			
			//System.out.println(nextNeighbor);
			currentNeighbors = findValidNeighbors(currentNeighbors);
			int nextNeighbor = r.nextInt(currentNeighbors.size());
			FoodSelectionNode nextChosenNeighbor = currentNeighbors.get(nextNeighbor);
			
			//System.out.println("HVal of next chosen neighbor: " + getHValOfNode(nextChosenNeighbor));
			//System.out.println("\tHVal of previous best: " + getHValOfNode(previousBest));
			
			double delta = (double)(getHValOfNode(nextChosenNeighbor) - getHValOfNode(previousBest));
			//System.out.println("Delta: " + delta);
			//System.out.println("current node: " + previousBest.totalNutrientsAndCostAsString());
			//System.out.println("nextBest: " + nextChosenNeighbor.totalNutrientsAndCostAsString());
			
			if(shouldAcceptNode(temperature, delta))
			{
				previousBest = nextChosenNeighbor;
			}
			
			
			/*System.out.println("nextBestNeighbor state: " + nextBestNeighbor);
		System.out.println("\t" + nextBestNeighbor.totalNutrientsAndCostAsString());*/
			visitedNodes.add(previousBest);
		}
	}
	
	/**
	 * Takes the current state of the search and generates all neighbors. These neighbors are the states where one of each food item has been added
	 * or one existing food choice has been decremented by one.
	 * @param currentState The current state of the search.
	 * @return An {@code ArrayList} of all the neighbor states as defined above. 
	 */
	private ArrayList<FoodSelectionNode> generateNeighbors(FoodSelectionNode currentState)
	{
		ArrayList<FoodSelectionNode> neighbors = new ArrayList<FoodSelectionNode>();
		int[] currentChoiceArray = currentState.getFoodChosen();
		for(int i = 0; i < currentChoiceArray.length; i++)
		{
			int[] currentStateClone = (int[])currentChoiceArray.clone();
			// Choose to include this neighbor
			currentStateClone[i]++;
			neighbors.add(new FoodSelectionNode(currentStateClone));
			
			// If there already is a non-zero amount of food item i+1 in the currentState, 
			// also choose to include an instance where this food choice is taken away.
			if(currentChoiceArray[i] > 0)
			{
				currentStateClone = (int[])currentChoiceArray.clone();
				currentStateClone[i]--;
				neighbors.add(new FoodSelectionNode(currentStateClone));
			}
		}
		
		return neighbors;
	}
	
	/**
	 * A function that calculates the probability that a given {@link FoodSelectionNode}
	 * will be accepted as the next valid solution the simulated annealing search.
	 * This is determined by {@code exp(delta/temp)}.
	 * @param temperature The temperature of the simulated annealing at time {@code timeStep}
	 * @param delta The difference in heuristic value between the current state and the next state (next - current)
	 * @return The probabilty of acceptance. 
	 */
	private double probabilityToAccept(double temperature, double delta)
	{
		return Math.exp(delta / temperature);
	}
	
	/**
	 * A function which determines whether or not a given {@link FoodSelectionNode} should
	 * be accepted as the next node in the search. 
	 * @param temperature The "temperature" at time {@code timeStep}
	 * @param delta The difference in heuristic value between the possible next state and the current state.
	 * @return
	 */
	private boolean shouldAcceptNode(double temperature, double delta)
	{
		Random r = new Random();
		// If delta <= 0.0, then we are moving down the hill.
		// Therefore, if this first condition evaluates to false we only accept the 
		// worse node based on our probability to accept. 
		boolean letItSlip = (r.nextDouble() <= probabilityToAccept(temperature,delta));
		return ((delta > 0.0) || letItSlip);
	}
	
	/**
	 * Calculates the heuristic value for the node {@code node} and then negates it because 
	 * we want values closer to the goal to be greater, but the heuristics as implemented return
	 * a smaller value the closer they are to the goal. 
	 * @param node
	 * @return
	 */
	private int getHValOfNode(FoodSelectionNode node)
	{
		return -h.calculateHeuristic(node);
	}
	

	/**
	 * Creates an integer array of size 20 that contains a random set of integers that total to 10. 
	 * @return An {@code int[]} of size 20 where the total of all values in the array add up to 10.
	 */
	private int[] generateRandomStartingState()
	{
		int[] startingState = new int[20];
		
		while(!(new FoodSelectionNode(startingState)).nutrientsGreaterThan95Percent())
		{
			int newFoodChoice = (int)Math.round(Math.random()*19);
			startingState[newFoodChoice]++;
		}
		return startingState;
	}
	
	private ArrayList<FoodSelectionNode> findValidNeighbors(ArrayList<FoodSelectionNode> generatedNeighbors)
	{
		ArrayList<FoodSelectionNode> unvisitedNeighbors = new ArrayList<FoodSelectionNode>(generatedNeighbors);
		for(int i = 0; i < unvisitedNeighbors.size();)
		{
			if(!unvisitedNeighbors.get(i).nutrientsGreaterThan95Percent())
			{
				unvisitedNeighbors.remove(i);
			}
			else
			{
				nodesGenerated++;
				i++;
			}
		}
		return unvisitedNeighbors;
	}
	
	public int getNodesGenerated()
	{
		return nodesGenerated;
	}
	
}
