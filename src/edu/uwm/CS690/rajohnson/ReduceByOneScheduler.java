package edu.uwm.CS690.rajohnson;

public class ReduceByOneScheduler implements TempScheduler {

	@Override
	public double getTemp(int timeStep) {
		return 50 - timeStep;
	}

}
