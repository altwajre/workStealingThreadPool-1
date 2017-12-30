package bgu.spl.a2.sim;

import bgu.spl.a2.sim.tools.GCDScrewdriver;
import bgu.spl.a2.sim.tools.NextPrimeHammer;
import bgu.spl.a2.sim.tools.RandomSumPliers;
import bgu.spl.a2.sim.tools.Tool;
import bgu.spl.a2.sim.conf.ManufactoringPlan;

import java.util.Iterator;
import java.util.concurrent.ConcurrentLinkedDeque;

import bgu.spl.a2.Deferred;

/**
 * A class representing the warehouse in your simulation
 * 
 * Note for implementors: you may add methods and synchronize any of the
 * existing methods in this class *BUT* you must be able to explain why the
 * synchronization is needed. In addition, the methods you add to this class can
 * only be private!!!
 *
 */
public class Warehouse {
	private ConcurrentLinkedDeque<Tool> Screwdrivers;
	private ConcurrentLinkedDeque<Tool> Hammers;
	private ConcurrentLinkedDeque<Tool> Pliers;
	private ConcurrentLinkedDeque<Deferred<Tool>> defScrewdrivers;
	private ConcurrentLinkedDeque<Deferred<Tool>> defHammers;
	private ConcurrentLinkedDeque<Deferred<Tool>> defPliers;
	private ConcurrentLinkedDeque<ManufactoringPlan> plans;

	/**
	* Constructor
	*/
    public Warehouse(){
    	this.Screwdrivers=new ConcurrentLinkedDeque<Tool>();
    	this.Hammers=new ConcurrentLinkedDeque<Tool>();
    	this.Pliers=new ConcurrentLinkedDeque<Tool>();
    	this.defHammers=new ConcurrentLinkedDeque<Deferred<Tool>>();
    	this.defPliers=new ConcurrentLinkedDeque<Deferred<Tool>>();
    	this.defScrewdrivers=new ConcurrentLinkedDeque<Deferred<Tool>>();
    	this.plans=new ConcurrentLinkedDeque<ManufactoringPlan>();
    	
    }

	/**
	* Tool acquisition procedure
	* Note that this procedure is non-blocking and should return immediatly
	* @param type - string describing the required tool
	* @return a deferred promise for the requested tool
	*/
    public Deferred<Tool> acquireTool(String type){
    	Deferred<Tool> ans = new Deferred<Tool>();
    	Tool tmpTool = null;
    	boolean exists = false;
    	if((type.equals("gs-driver"))&&(!Screwdrivers.isEmpty())){
    		tmpTool = Screwdrivers.pollLast();
			exists = true;
    	}
    	
    	else if((type.equals("np-hammer"))&&(!Hammers.isEmpty())){
    		tmpTool = Hammers.pollLast();
			exists = true;
    	}
    		
    	else if((type.equals("rs-pliers"))&&(!Pliers.isEmpty())){
    		tmpTool = Pliers.pollLast();
			exists = true;
    	}
    	
    	if(exists && tmpTool!=null )
    		ans.resolve(tmpTool);
    	else{
    		if(type.equals("rs-pliers"))
    			defPliers.addFirst(ans);
    		if(type.equals("np-hammer"))
    			defHammers.addFirst(ans);
    		if(type.equals("gs-driver"))
    			defScrewdrivers.addFirst(ans);
    	}
    		
    	return ans;
    }
    	
	/**
	* Tool return procedure - releases a tool which becomes available in the warehouse upon completion.
	* @param tool - The tool to be returned
	*/
    public void releaseTool(Tool tool){
    	Deferred<Tool> tmpDefTool = null;
    	String type = tool.getType();
    	if(type.equals("GCD screwdriver")){
    		if(!defScrewdrivers.isEmpty()){
    			tmpDefTool=defScrewdrivers.pollLast();
    			if(tmpDefTool!=null)
    				tmpDefTool.resolve(tool);
    			else{
    				this.addTool(tool, 1);
        		}
    		}else{
    			this.addTool(tool, 1);
    		}
    	}
    	else if(type.equals("nextPrimeHammer")){
    		if(!defHammers.isEmpty()){
    			tmpDefTool=defHammers.pollLast();
    			if(tmpDefTool!=null)
    				tmpDefTool.resolve(tool);
    			else{
    				this.addTool(tool, 1);
        		}
    		}else{
    			this.addTool(tool, 1);
    		}
    	}
    	else if(type.equals("RandomSumPliers")){
    		if(!defPliers.isEmpty()){
    			tmpDefTool=defPliers.pollLast();
    			if(tmpDefTool!=null)
    				tmpDefTool.resolve(tool);
    			else{
    				this.addTool(tool, 1);
    			}
    		}else{
    			this.addTool(tool, 1);
    		}
    	} 
    }

	
	/**
	* Getter for ManufactoringPlans
	* @param product - a string with the product name for which a ManufactoringPlan is desired
	* @return A ManufactoringPlan for product
	*/
    public ManufactoringPlan getPlan(String product){
    	ManufactoringPlan ans = null;
    	boolean exists = false;
    	Iterator<ManufactoringPlan> it = plans.iterator();
    	while(!exists && it.hasNext()){
    		ManufactoringPlan tmpPlan = it.next();
    		if(tmpPlan.getProductName().equals(product)){
    			ans = tmpPlan;
    			exists = true;
    		}
    	}
    	
    	return ans;
    }
    
	/**
	* Store a ManufactoringPlan in the warehouse for later retrieval
	* @param plan - a ManufactoringPlan to be stored
	*/
    public void addPlan(ManufactoringPlan plan){
    	this.plans.add(plan);
    }
    
	/**
	* Store a qty Amount of tools of type tool in the warehouse for later retrieval
	* @param tool - type of tool to be stored
	* @param qty - amount of tools of type tool to be stored
	*/
    
    public void addTool(Tool tool, int qty){
    	String type = tool.getType();
    	int x = qty;
    	if(type.equals("GCD screwdriver")){
	    	while (x!=0){
	    		Screwdrivers.addFirst(new GCDScrewdriver());
	    		x--;
	    	}		
    	}
    	else if(type.equals("nextPrimeHammer")){
	    	while (x!=0){
	    		Hammers.addFirst(new NextPrimeHammer());
	    		x--;
	    	}		
    	}
    	else{
	    	while (x!=0){
	    		Pliers.addFirst(new RandomSumPliers());
	    		x--;
	    	}		
    	}
    }
    
 

}
