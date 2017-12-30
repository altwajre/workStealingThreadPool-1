package bgu.spl.a2.sim.tasks;

import bgu.spl.a2.Task;
import bgu.spl.a2.sim.Warehouse;
import bgu.spl.a2.sim.tools.Tool;

public class releaseTask extends Task<Tool>{
	private Tool tool;
	private Warehouse warehouse;

	public releaseTask(Tool tool, Warehouse warehouse){
		this.tool=tool;
		this.warehouse=warehouse;
	}
	
	/**
	 * releases tool that was acquired from the warehouse.
	 */
	@Override
	protected void start() {
		warehouse.releaseTool(tool);
		
		
	}
	

}
