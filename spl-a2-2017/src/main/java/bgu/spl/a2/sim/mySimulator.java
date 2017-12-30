package bgu.spl.a2.sim;

public class mySimulator {
	private int threads;
	private myTool [] tools;
	private myManufactoringPlan [] plans;
	private myOrder [][] waves;
	

	/**
	 * getter
	 * @return number of threads.
	 */
	public int getThreads() {
		return threads;
	}
	
	/**
	 * setter
	 * sets number of waves.
	 */
	public void setThreads(int threads) {
		this.threads = threads;
	}

	/**
	 * getter
	 * @return tools
	 */
	public myTool[] getTools() {
		return tools;
	}
	
	/**
	 * setter
	 * sets tools
	 */
	public void setTools(myTool[] tools) {
		this.tools = tools;
	}

	/**
	 * getter
	 * @return manufacturing plans.
	 */
	public myManufactoringPlan[] getPlans() {
		return plans;
	}
	
	/**
	 * setter
	 * sets manufacturing plans
	 */
	public void setPlans(myManufactoringPlan[] plans) {
		this.plans = plans;
	}
	

	/**
	 * getter
	 * @return waves of orders
	 */
	public myOrder[][] getWaves() {
		return waves;
	}
	
	/**
	 * setter
	 * sets waves of orders
	 */
	public void setOrders(myOrder[][] waves) {
		this.waves = waves;
	}
}
