package edu.uwm.CS690.rajohnson;

public class HCDriver {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		RandomRestartHillClimb rrhc = new RandomRestartHillClimb(new TotalCost());
		
		System.out.println("Starting random restart hill climbing...");
		int t = 50;
		System.out.println(t + " iterations being performed...");
		FoodSelectionNode bestSolution = rrhc.findBestSolutionAfterTIterations(t);

		System.out.println("The best solution is " + bestSolution);
		System.out.println("The amounts are " + bestSolution.totalNutrientsAndCostAsString());
		System.out.println(rrhc.getNodesGenerated() + " nodes were generated");
		
		System.out.println("Starting simulated annealing...");
		
		SimulatedAnnealing sa = new SimulatedAnnealing(new TotalCost(), new ReduceByTenthScheduler());
		
		FoodSelectionNode bestSASolution = sa.findBestSolutionWhenCool();
		
		System.out.println("The best solution is " + bestSASolution);
		System.out.println("The amounts are " + bestSASolution.totalNutrientsAndCostAsString());
		System.out.println(sa.getNodesGenerated() + " nodes were generated.");
		
		System.out.println("Starting simulated annealing with weighted total cost...");
		SimulatedAnnealing sa2 = new SimulatedAnnealing(new WeightedTotalDistanceToGoal(), new ReduceByTenthScheduler());
		
		FoodSelectionNode bestSA2Solution = sa2.findBestSolutionWhenCool();
		System.out.println("The best solution is " + bestSA2Solution);
		System.out.println("The amounts are " + bestSA2Solution.totalNutrientsAndCostAsString());
		System.out.println(sa2.getNodesGenerated() + " nodes were generated.");
		
	}

	
}
