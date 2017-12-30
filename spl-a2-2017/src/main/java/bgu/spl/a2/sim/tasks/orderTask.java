package bgu.spl.a2.sim.tasks;

import java.util.LinkedList;
import java.util.concurrent.ConcurrentLinkedQueue;

import bgu.spl.a2.Task;
import bgu.spl.a2.sim.Product;
import bgu.spl.a2.sim.Warehouse;
import bgu.spl.a2.sim.myOrder;

public class orderTask extends Task<ConcurrentLinkedQueue<Product>> {
	private myOrder order;
	private Warehouse warehouse;
	private ConcurrentLinkedQueue<Product> q;
	
	public orderTask(myOrder order , Warehouse warehouse) {
		this.order=order;
		this.warehouse = warehouse;
		this.q = new ConcurrentLinkedQueue<Product>();
	}

	/**
	 * spawn all order's products to be manufactured and when they are resolved add their results to a queue.
	 */
	@Override
	protected void start() {
		long startId = order.getStartId();
		LinkedList<ManufactoringTask> tasks=new LinkedList<ManufactoringTask>(); 
		for(int j=0; j<order.getQty(); j++){
			Product p = new Product(startId + j, order.getProduct());
			ManufactoringTask mt = new ManufactoringTask(p , warehouse);
			spawn(mt);
			tasks.add(mt);
		}
		whenResolved(tasks, ()->{
			for(int j=0; j<tasks.size(); j++){
				q.add(tasks.get(j).getResult().get());
				//System.out.println("ORDER DONE");
			}
			complete(q);
		});
		
	}
	

}
