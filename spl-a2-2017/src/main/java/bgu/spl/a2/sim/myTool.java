package bgu.spl.a2.sim;

import bgu.spl.a2.sim.tools.GCDScrewdriver;
import bgu.spl.a2.sim.tools.NextPrimeHammer;
import bgu.spl.a2.sim.tools.RandomSumPliers;
import bgu.spl.a2.sim.tools.Tool;

public class myTool {
	private String tool;
	private int qty;
	
	/**
	 * getter
	 * @return tool should be added to warehouse.
	 */
	public String getTool() {
		return tool;
	}
	
	/**
	 * setter
	 * sets tool should be added to warehouse.
	 */
	public void setTool(String tool) {
		this.tool = tool;
	}
	/**
	 * getter
	 * @return quantity of the given tool should be added to warehouse.
	 */
	public int getQty() {
		return qty;
	}
	/**
	 * setter
	 * sets quantity of the given tool should be added to warehouse.
	 */
	public void setQty(int qty) {
		this.qty = qty;
	}
	
	/**
	 * gives new tool should be added to warehouse by the order.
	 * @return Tool.
	 */
	public Tool giveTool(){
		Tool ans = null;
		switch (tool) {
		case "gs-driver":
			ans = new GCDScrewdriver();
			break;
		case "np-hammer":
			ans = new NextPrimeHammer();
			break;
		case "rs-pliers":
			ans = new RandomSumPliers();
			break;
		default:
			break;
		}
		return ans;
	}
}
