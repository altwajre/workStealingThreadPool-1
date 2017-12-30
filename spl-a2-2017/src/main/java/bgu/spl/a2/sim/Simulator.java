/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bgu.spl.a2.sim;
import bgu.spl.a2.WorkStealingThreadPool;
import bgu.spl.a2.sim.tasks.WaveTask;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.CountDownLatch;


import java.io.BufferedReader;
import java.io.FileNotFoundException;

import com.google.gson.Gson;


/**
 * A class describing the simulator for part 2 of the assignment
 */
public class Simulator {
	private static WorkStealingThreadPool pool;
	private static Warehouse warehouse = new Warehouse();
	private static myOrder[][] waves;

	/**
	* Begin the simulation
	* Should not be called before attachWorkStealingThreadPool()
	*/
	   public static ConcurrentLinkedQueue<Product> start() {
			ConcurrentLinkedQueue<Product> ans = new ConcurrentLinkedQueue<>();
			pool.start();
			for(int i=0;i<waves.length;i++){
				CountDownLatch cdl = new CountDownLatch(1);
				WaveTask waveTask = new WaveTask(waves[i], warehouse,ans);
				pool.submit(waveTask);
				waveTask.getResult().whenResolved(()->{
					cdl.countDown();
				});
				
				try {
					cdl.await();
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}

			try {
				pool.shutdown();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			return ans;
	}
	
	   /**
	    * start getting waves.
	    * @param orders - waves of orders should be manufactured.
	    */
	private static void startGettingWaves(myOrder[][] orders) {
		waves = orders;
	}

	/**
	 * adds the plans to the warehouse
	 * @param plans to be added.
	 */
	private static void addPlansToWarehouse(myManufactoringPlan[] plans) {
		for(int i=0;i<plans.length;i++){
			warehouse.addPlan(plans[i].givePlan());
		}	
	}

	/**
	 * adds the tools to warehouse
	 * @param tools to be added.
	 */
	private static void addToolsToWarehouse(myTool[] tools) {
		for(int i=0;i<tools.length;i++){
			warehouse.addTool(tools[i].giveTool(), tools[i].getQty());
		}		
	}

	/**
	* attach a WorkStealingThreadPool to the Simulator, this WorkStealingThreadPool will be used to run the simulation
	* @param myWorkStealingThreadPool - the WorkStealingThreadPool which will be used by the simulator
	*/
	public static void attachWorkStealingThreadPool(WorkStealingThreadPool myWorkStealingThreadPool){
		pool = myWorkStealingThreadPool;
	}

	public static void main (String [] args){
		mySimulator file = null;
		try{
			Gson gson = new Gson();  
			BufferedReader reader = new BufferedReader(new FileReader(args[0]));
			 file = gson.fromJson(reader, mySimulator.class);
			attachWorkStealingThreadPool(new WorkStealingThreadPool(file.getThreads()));
		}catch(FileNotFoundException e){
			System.out.println("couldn't open the file.");
		}
		
		addToolsToWarehouse(file.getTools());
		addPlansToWarehouse(file.getPlans());
		startGettingWaves(file.getWaves());
		ConcurrentLinkedQueue<Product> SimulationResult = start();
		
		try{
		FileOutputStream fout = new FileOutputStream("result.ser");
		ObjectOutputStream oos = new ObjectOutputStream(fout);
		oos.writeObject(SimulationResult);
		oos.close();
		}catch(IOException e){
			System.out.println("couldn't create file");
		}
			
	}
}