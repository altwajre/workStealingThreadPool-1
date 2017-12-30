package bgu.spl.a2.sim.tasks;
import java.util.LinkedList;
import java.util.concurrent.ConcurrentLinkedQueue;

import bgu.spl.a2.Task;
import bgu.spl.a2.sim.Product;
import bgu.spl.a2.sim.Warehouse;
import bgu.spl.a2.sim.myOrder;

public class WaveTask extends Task<ConcurrentLinkedQueue<Product>>{
	private myOrder [] orders;
	private Warehouse warehouse;
	private ConcurrentLinkedQueue<Product> q;
	

	public WaveTask(myOrder[] orders, Warehouse warehouse, ConcurrentLinkedQueue<Product> q) {
		this.orders=orders;
		this.warehouse = warehouse;
		this.q = q;
	}

	/**
	 * spawn all wave's orders to be manufactured and when they are resolved add their results to the final queue.
	 */
	@Override
	protected void start() {
		LinkedList<orderTask> tasks=new LinkedList<orderTask>(); 
		for(int i=0; i<orders.length ;i++){
			orderTask ot = new orderTask(orders[i], warehouse);
			spawn(ot);
			tasks.add(ot);
			
		}
		whenResolved(tasks, ()->{
			for(int j=0; j<tasks.size(); j++){
				q.addAll((tasks.get(j).getResult().get()));
			}
			complete(q);
		});
			
					
	}

}
