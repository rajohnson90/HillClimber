package edu.uwm.CS690.rajohnson;

import java.util.Arrays;

public class FoodSelectionNode {
	//An array containing the individual nutrient amounts and costs for each array. 
	public static final int[] f1 = { 1, 3, 4, 5, 6, 12 };
	public static final int[] f2 = { 2, 4, 3, 2, 5, 12 };
	public static final int[] f3 = { 2, 1, 5, 2, 4, 9 };
	public static final int[] f4 = { 2, 1, 5, 2, 4, 9 };
	public static final int[] f5 = { 6, 1, 2, 2, 3, 5 };
	public static final int[] f6 = { 1, 6, 1, 4, 7, 8 };
	public static final int[] f7 = { 4, 2, 2, 6, 1, 9 };
	public static final int[] f8 = { 1, 5, 3, 2, 5, 12 };
	public static final int[] f9 = { 3, 2, 3, 6, 2, 9 };
	public static final int[] f10 = { 7, 1, 3, 2, 3, 7 };
	public static final int[] f11 = { 2, 6, 1, 4, 1, 8 };
	public static final int[] f12 = { 2, 1, 6, 2, 3, 11 };
	public static final int[] f13 = { 2, 5, 1, 2, 7, 10 };
	public static final int[] f14 = { 6, 2, 2, 1, 2, 8 };
	public static final int[] f15 = { 3, 1, 1, 3, 6, 9 };
	public static final int[] f16 = { 5, 5, 4, 2, 1, 15 };
	public static final int[] f17 = { 2, 4, 3, 1, 2, 14 };
	public static final int[] f18 = { 5, 6, 1, 4, 0, 8 };
	public static final int[] f19 = { 3, 2, 3, 6, 2, 9 };
	public static final int[] f20 = { 2, 6, 1, 4, 1, 8 };
	private static int[][] fArray = { f1, f2, f3, f4, f5, f6, f7, 
									  f8, f9, f10, f11, f12, f13,
									  f14, f15, f16, f17, f18, f19, f20 };
	
	private int[] foodChosen;
	
	public FoodSelectionNode() {
		foodChosen = new int[20];
	}
	
	public FoodSelectionNode(int[] userFoodChoice)
	{
		if(userFoodChoice.length == 20)
		{
			foodChosen = userFoodChoice;
		}
		else
		{
			System.err.println("FoodSelectionNode given choice array of size " + userFoodChoice.length + ". This node is initialized with an empty choice.");
		}
	}
	
	/**
	 * Returns this array of food choices.
	 * @return {@code int[]} of size 20 where each index contains the number of food items (f1-f20) that are found in this selection.
	 */
	public int[] getFoodChosen()
	{
		return foodChosen;
	}
	
	/**
	 * Sets this {@code FoodSelectionNode}'s foodChosen array to be the argument passed into this function.
	 * @param foodChoice An {@code int[]} of size 20. If the array does not have size of 20, nothing changes. 
	 */
	public void setFoodChosen(int[] foodChoice)
	{
		if(foodChoice.length == 20)
		{
			foodChosen = foodChoice;
		}
	}
	
	/**
	 * Determines the total amount of nutrients and cost based on the amount of times food item f1 through f20 is included
	 * in {@foodChosen}.
	 * @return An {@code int[]} of (currently) length 7 which is the total amount of each nutrient and cost based on {@foodChosen}.
	 */
	public int[] calculateTotalNutrientsAndCost()
	{
		int[] totalNutrientsAndCost = new int[f1.length];
		
		int foodChosenLength = foodChosen.length;
		int foodItemLength = f1.length;
		for(int i = 0; i < foodChosenLength; i++ )
		{
			//If at least one of the food items at index i is represented in this choice node, then add it's amount to the total amount.
			if(foodChosen[i] != 0)
			{
				//Add the total amt for a given food selection to the overall nutrient and cost amount
				int[] foodTotalAtI = calculateNutrientsAndCostForFoodAmt(i, foodChosen[i]);
				for(int j = 0; j < foodItemLength; j++)
				{
					totalNutrientsAndCost[j] += foodTotalAtI[j];
				}
			}
		}
		
		return totalNutrientsAndCost;
	}
	
	/**
	 * Calculate the total amount of nutrients and cost for a given food item based on how many times the food item is included
	 * in {@code foodChosen}. 
	 * @param foodItem The index of the food item (f1 to f20, indices 0 to 19 respectively)
	 * @param foodCount The number of times {@code foodItem} is included in the food choice.
	 * @return The total amount of each nutrient from this food item given how many times it appears in the food choice.
	 */
	private int[] calculateNutrientsAndCostForFoodAmt(int foodItem, int foodCount)
	{
		//foodItem is the index of a valid food item to use
		if(foodItem < fArray.length)
		{
			int numFoodItemElts = fArray[foodItem].length;
			int[] nutrientsAndCostTotal = new int[numFoodItemElts];
		
			//Total amt of nutrients is foodCount * (per item amt of nutrients/cost)
			for(int i = 0; i < numFoodItemElts; i++)
			{
				nutrientsAndCostTotal[i] = foodCount * fArray[foodItem][i];
			}
		
			return nutrientsAndCostTotal;
		}
		else
		{
			return new int[1];
		}
	}
	
	/**
	 * Determines whether or not the nutrients in this {@code FoodSelectionNode} exceed 95.
	 * @return True if all the nutrient values are greater than or equal to 95, false if not. 
	 */
	public boolean totalNutrientsExceeds95Percent()
	{
		int[] totalNutrientsAndCost = calculateTotalNutrientsAndCost();
		//Only calculate the length of the array that includes nutrients. The final element is cost and is not relevant
		int nutrientLength = totalNutrientsAndCost.length - 1;
		for(int i = 0; i < nutrientLength; i++ )
		{
			if(totalNutrientsAndCost[i] < 95)
			{
				return false;
			}
		}
		
		return true;
	}
	
	@Override
	public String toString()
	{
		String result = "[";
		int arrayLengthMinusLast = foodChosen.length-1;
		for(int i = 0; i < arrayLengthMinusLast; i++)
		{
			result += (Integer.toString(foodChosen[i]) + ",");
		}
		result += foodChosen[arrayLengthMinusLast];
		result += "]";
		
		return result;
	}
	
	public String totalNutrientsAndCostAsString()
	{
		int[] totalNutrientsAndCost = calculateTotalNutrientsAndCost();
		
		String nutrientCostString = "Carbohydrates: " + Integer.toString(totalNutrientsAndCost[0]);
		nutrientCostString += (" Protein: " + Integer.toString(totalNutrientsAndCost[1]));
		nutrientCostString += (" Fat: " + Integer.toString(totalNutrientsAndCost[2]));
		nutrientCostString += (" Vitamin: " + Integer.toString(totalNutrientsAndCost[3]));
		nutrientCostString += (" Mineral: " + Integer.toString(totalNutrientsAndCost[4]));
		nutrientCostString += (" Cost: " + Integer.toString(totalNutrientsAndCost[5]));
		
		
		return nutrientCostString;
		
	}
	
	@Override
	public boolean equals(Object obj) {
	    if (obj == null) {
	        return false;
	    }
	    if (getClass() != obj.getClass()) {
	        return false;
	    }
	    final FoodSelectionNode other = (FoodSelectionNode) obj;
	    for(int i = 0; i < foodChosen.length; i++)
	    {
	    	if(foodChosen[i] != other.foodChosen[i])
	    	{
	    		return false;
	    	}
	    }
	    return true;
	}
	
	@Override
	public int hashCode() {
	    int hash = 3;
	    hash += Arrays.hashCode(foodChosen);
	    return hash;
	}
	
	/**
	 * Returns the total cost of a food selection. 
	 * @return
	 */
	public int getFoodCost()
	{
		int[] nutrientsAndCost = calculateTotalNutrientsAndCost();
		
		//
		return nutrientsAndCost[5];
	}
	
	/**
	 * Checks whether or not the nutrients are all greater than 95%. (The minimum qualification for a solution).
	 * @return
	 */
	public boolean nutrientsGreaterThan95Percent()
	{
		int[] nutrientAndCost = calculateTotalNutrientsAndCost();
		for(int i = 0; i < nutrientAndCost.length-1; i++)
		{
			if(nutrientAndCost[i] < 95)
			{
				return false;
			}
		}
		return true;
	}
	
	/**
	 * Checks whether or not the nutrients are all greater than 110%. (The minimum qualification for a starting point in my implementation.).
	 * @return
	 */
	public boolean nutrientsGreaterThan110Percent()
	{
		int[] nutrientAndCost = calculateTotalNutrientsAndCost();
		for(int i = 0; i < nutrientAndCost.length-1; i++)
		{
			if(nutrientAndCost[i] < 110)
			{
				return false;
			}
		}
		return true;
	}
}
