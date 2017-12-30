package bgu.spl.a2.sim.tasks;

import java.util.LinkedList;
import java.util.concurrent.atomic.AtomicLong;

import bgu.spl.a2.Deferred;
import bgu.spl.a2.Task;
import bgu.spl.a2.sim.Product;
import bgu.spl.a2.sim.Warehouse;
import bgu.spl.a2.sim.conf.ManufactoringPlan;
import bgu.spl.a2.sim.tools.Tool;

public class ManufactoringTask extends Task<Product>{
	private Product  product;
	private Warehouse warehouse;

	/**
	 * constructor of ManufactoringTask
	 * @param p - the product that will be manufactured
	 * @param warehouse - the warehouse of tools of the project
	 */
	public ManufactoringTask(Product p, Warehouse warehouse) {
		this.product = p;
		this.warehouse=warehouse;		
	}
	@Override
	protected void start() {
		LinkedList<ManufactoringTask> tasks=new LinkedList<ManufactoringTask>();//list of task which will hold the parts of this product
		ManufactoringPlan plan = warehouse.getPlan(product.getName());//get the plan to manufacture this product.
		String [] parts = plan.getParts();//get the parts from the plan
		for(int i=0; i<parts.length; i++){//go over all the parts and create them as new product
			Product part = new Product(product.getStartId()+1, parts[i]);
			product.addPart(part);//add the parts to the parts list of this product
			ManufactoringTask newTask = new ManufactoringTask(part, warehouse);//create new task for each part should be manufactured.
			spawn(newTask);
			tasks.add(newTask);
		}
		if(parts.length !=0 && plan.getTools().length != 0){//if this product has any part or tools wait for them to be resolved 
		whenResolved(tasks, ()->{
			String [] tools = plan.getTools();
			AtomicLong ans = new AtomicLong(product.getStartId());
			AtomicLong counter = new AtomicLong(tools.length);
			for(int i=0; i<tools.length; i++){//when all parts are ready, use the useOn function of the tool on them (when the tool is acquired)
					Deferred<Tool> tool = warehouse.acquireTool(tools[i]);
					tool.whenResolved(()->{
						ans.addAndGet(tool.get().useOn(product));//sum all of the useOn values to the startId
						spawn(new releaseTask(tool.get(), warehouse));//spawn releaseTask in order to avoid StackOverFlow exception,handling the task will release the tool.
						if(counter.decrementAndGet()==0){//when all of the tools were acquired and used set the finalId and complete the task
							product.setFinalId(ans.get());//
							complete(product);
							
						}
					});
			}	
		});		
	}
	
	else{
		this.product.setFinalId(this.product.getStartId());// if you have no tools or parts to handle with, complete the task without waiting.
		this.complete(product);
	}
		
	}	
}
