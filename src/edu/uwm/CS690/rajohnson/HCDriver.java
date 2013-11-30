package edu.uwm.CS690.rajohnson;

public class HCDriver {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		RandomRestartHillClimb rrhc = new RandomRestartHillClimb(new WeightedTotalDistanceToGoal());
		
		System.out.println("Starting random restart hill climbing...");
		int t = 20;
		System.out.println(t + " iterations being performed...");
		FoodSelectionNode bestSolution = rrhc.findBestSolutionAfterTIterations(t);

		System.out.println("The best solution is " + bestSolution);
		System.out.println("The amounts are " + bestSolution.totalNutrientsAndCostAsString());
	}

	
}
