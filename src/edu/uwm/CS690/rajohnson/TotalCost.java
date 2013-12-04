package edu.uwm.CS690.rajohnson;

public class TotalCost implements Heuristic {
	@Override
	public int calculateHeuristic(FoodSelectionNode currentNode) {
		return currentNode.getFoodCost();
	}

}
