package edu.uwm.CS690.rajohnson;

public class WeightedTotalDistanceToGoal implements Heuristic {

	/**
	 * Calculate the total distance of each nutrient from the goal, multiplied by the total cost required to reach this nutritional value.
	 * Therefore, the node that is closest to 100 for each value is better if it has a lower cost. 
	 * @param currentNode The node used to generate the heuristic value.
	 * @returns The integer distance to the goal generated as described above.
	 */
	@Override
	public int calculateHeuristic(FoodSelectionNode currentNode) {
		
		int[] currentNodeArray = currentNode.calculateTotalNutrientsAndCost();
		int lengthOfNutrients = currentNodeArray.length-1;
		int totalDistance = 0;
		for(int i = 0; i < lengthOfNutrients; i++)
		{
			//System.out.println("currentNodeArray[" + i + "]: " + currentNodeArray[i]);
			totalDistance += Math.abs(100-currentNodeArray[i]);
		}
		//System.out.println("Cost: " + currentNodeArray[lengthOfNutrients]);
		//System.out.println("Total distance: " + totalDistance);
		// Return the total distance for all nutrients to reach 100 multiplied by the total cost of this node. 
		/*if(currentNodeArray[lengthOfNutrients] == 0)
		{
			return 20000;
		}*/
		return  (totalDistance);
	}

}
