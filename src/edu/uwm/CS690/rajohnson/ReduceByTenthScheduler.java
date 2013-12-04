package edu.uwm.CS690.rajohnson;

public class ReduceByTenthScheduler implements TempScheduler {

	@Override
	public double getTemp(int timeStep) {
		return 50 - (0.1 * timeStep);
	}

}
