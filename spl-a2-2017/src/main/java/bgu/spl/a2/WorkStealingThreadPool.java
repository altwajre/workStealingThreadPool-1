package bgu.spl.a2;

import java.util.Random;
import java.util.Vector;
import java.util.concurrent.ConcurrentLinkedDeque;

/**
 * represents a work stealing thread pool - to understand what this class does
 * please refer to your assignment.
 *
 * Note for implementors: you may add methods and synchronize any of the
 * existing methods in this class *BUT* you must be able to explain why the
 * synchronization is needed. In addition, the methods you add can only be
 * private, protected or package protected - in other words, no new public
 * methods
 */
public class WorkStealingThreadPool {
	private Thread [] threads;
	private  Vector<ConcurrentLinkedDeque<Task<?>>> queues;
	private VersionMonitor vm;
    /**
     * creates a {@link WorkStealingThreadPool} which has n threads
     * {@link Processor}s. Note, threads should not get started until calling to
     * the {@link #start()} method.
     *
     * Implementors note: you may not add other constructors to this class nor
     * you allowed to add any other parameter to this constructor - changing
     * this may cause automatic tests to fail..
     *
     * @param nthreads the number of threads that should be started by this
     * thread pool
     */
    public WorkStealingThreadPool(int nthreads) {
    	vm = new VersionMonitor();
        threads = new Thread[nthreads];
        queues = new Vector<ConcurrentLinkedDeque<Task<?>>>();
        for(int i=0; i< threads.length; i++){
        	threads[i] = new Thread(new Processor(i, this));
        	queues.add(i, new ConcurrentLinkedDeque<Task<?>>());
        }
        
    }

    /**
     * submits a task to be executed by a processor belongs to this thread pool
     *
     * @param task the task to execute
     */
    public void submit(Task<?> task) {
    	Random r = new Random();
    	queues.get(r.nextInt(queues.size())).addFirst(task);
    	vm.inc();
    }
    
    void addToQueue(int id,Task<?>... task){
    	for(int i=0;i<task.length;i++){
    		queues.get(id).addFirst(task[i]);
    		vm.inc();
    	}
    }

    /**
     * closes the thread pool - this method interrupts all the threads and wait
     * for them to stop - it is returns *only* when there are no live threads in
     * the queue.
     *
     * after calling this method - one should not use the queue anymore.
     *
     * @throws InterruptedException if the thread that shut down the threads is
     * interrupted
     * @throws UnsupportedOperationException if the thread that attempts to
     * shutdown the queue is itself a processor of this queue
     */
    public void shutdown() throws InterruptedException {
        for(int i=0; i<threads.length; i++)
        	if(Thread.currentThread().equals(threads[i]))
        		throw new UnsupportedOperationException();
        	else
        		threads[i].interrupt(); 	
    }
    
   /**
    * manage the asking processor, gives him some tasks to handle or send the processor to wait.
    * @param id - the asking processor id.
    * @return task to the asking processor, in case there are no tasks to handle sends the current thread to wait
    */
    Task<?> giveTask(int id){
    	while(!Thread.currentThread().isInterrupted()){
    		int currentVersion = vm.getVersion();
	    	if(!queues.get(id).isEmpty())
	    		return queues.get(id).pollFirst();
	    	
	    	else if(tryToSteal(id))
	    	   	return queues.get(id).pollFirst();
	    	
			else
				try {
					vm.await(currentVersion);
				} catch (InterruptedException e) {
					Thread.currentThread().interrupt(); 
				}
    	}
    	
    	return null;
    }
    /**
     * this method attempts to steal tasks from another processor to the given processor.
     * @param ProcessorId - the id of the processor that is currently trying to steal some tasks.
     * @return true if there was a successful stealing performed, false otherwise.
     */
    private boolean tryToSteal(int ProcessorId) {
		boolean ans = false;
		int x = (ProcessorId+1)%(queues.size());
		while(x!=ProcessorId && !ans){
			if(queues.get(x).size()/2 > 0){
				for(int i=0;i<queues.get(x).size()/2;i++){
					Task<?> tmpTask = queues.get(x).pollLast();
					if(tmpTask!=null){
						queues.get(ProcessorId).add(tmpTask);
					}
				}
				ans = queues.get(ProcessorId).size() != 0;
			}
				else
					x = (x+1)%(queues.size());
		}
			
		return ans;
	}

	/**
     * start the threads belongs to this thread pool
     */
    public void start() {
       for(int i=0; i<threads.length; i++)
    	   threads[i].start();
    }

}
