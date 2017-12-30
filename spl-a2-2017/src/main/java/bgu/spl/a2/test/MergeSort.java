/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/*
package bgu.spl.a2.test;

import bgu.spl.a2.Task;
import bgu.spl.a2.WorkStealingThreadPool;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.concurrent.CountDownLatch;

public class MergeSort extends Task<int[]> {

    private final int[] array;

    public MergeSort(int[] array) {
    	super();
        this.array = array;
    }

    @Override
    protected void start() {
    	if(this.array.length > 1){
    		int[]fHalfArr = new int[array.length/2];
    		for(int i=0; i<fHalfArr.length; i++)
    			fHalfArr[i] = array[i];
    		int[]lHalfArr = new int[array.length - array.length/2];
    		for(int i=0; i<lHalfArr.length; i++)
    			lHalfArr[i] = array[fHalfArr.length+i];
    		MergeSort fHalf = new MergeSort(fHalfArr);
    		MergeSort lHalf = new MergeSort(lHalfArr);
    		List<Task<int[]>> tasks = new ArrayList<>();
    		tasks.add(fHalf);
    		tasks.add(lHalf);
    		this.spawn(fHalf,lHalf);
    		this.whenResolved(tasks, ()->{
    			int[]first = tasks.get(0).getResult().get();
    			int[]last = tasks.get(1).getResult().get();
    			int[]ans = new int[first.length + last.length];
    			int i=0,j=0,k=0;
    			while(i!=first.length && j!=last.length){
    				if(first[i] <= last[j]){
    					
    					ans[k] = first[i];
    					i++;
    					k++;
    				}
    				
    				else{
    					ans[k] = last[j];
    					j++;
    					k++;
    				}
    			}
    				while(i!=first.length){
        					ans[k] = first[i];
        					i++;
        					k++;
        				}
    				
    				while(j!=last.length){
    					ans[k] = last[j];
    					j++;
    					k++;
    				}
    				
    				complete(ans);
    		});
    	}
    	
    	else
    		complete(array);
      
    }
    
    
    
    */
    
    
    /* To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bgu.spl.a2.test;

import bgu.spl.a2.Task;
import bgu.spl.a2.WorkStealingThreadPool;

import java.util.ArrayList;
import java.util.Arrays;
/*import java.util.Arrays;
import java.util.LinkedList;*/
import java.util.List;
import java.util.Random;
import java.util.concurrent.CountDownLatch;

public class MergeSort extends Task<int[]> {

    private final int[] array;

    public MergeSort(int[] array) {
    	super();
        this.array = array;
    }

    @Override
    protected void start() {
    	if(this.array.length > 1){
    		int[]fHalfArr = new int[array.length/2];
    		for(int i=0; i<fHalfArr.length; i++)
    			fHalfArr[i] = array[i];
    		int[]lHalfArr = new int[array.length - array.length/2];
    		for(int i=0; i<lHalfArr.length; i++)
    			lHalfArr[i] = array[fHalfArr.length+i];
    		MergeSort fHalf = new MergeSort(fHalfArr);
    		MergeSort lHalf = new MergeSort(lHalfArr);
    		List<Task<int[]>> tasks = new ArrayList<Task<int[]>>();
    		tasks.add(fHalf);
    		tasks.add(lHalf);
    		this.spawn(fHalf,lHalf);
    		this.whenResolved(tasks, ()->{
    			int[]first = tasks.get(0).getResult().get();
    			int[]last = tasks.get(1).getResult().get();
    			int[]ans = new int[first.length + last.length];
    			int i=0,j=0,k=0;
    			while(i!=first.length && j!=last.length){
    				if(first[i] <= last[j]){
    					
    					ans[k] = first[i];
    					i++;
    					k++;
    				}
    				
    				else{
    					ans[k] = last[j];
    					j++;
    					k++;
    				}
    			}
    				while(i!=first.length){
        					ans[k] = first[i];
        					i++;
        					k++;
        				}
    				
    				while(j!=last.length){
    					ans[k] = last[j];
    					j++;
    					k++;
    				}
    				
    				complete(ans);
    		});
    	}
    	
    	else
    		complete(array);
      
    }

    

    public static void main(String[] args) throws InterruptedException {
    	WorkStealingThreadPool pool;
    	
    	for(int k=0;k<20000;k++){
        pool = new WorkStealingThreadPool(4);
        int n = 1000; //you may check on different number of elements if you like
        int[] array = new Random().ints(n).toArray();

        MergeSort task = new MergeSort(array);
        
        CountDownLatch l = new CountDownLatch(1);
        pool.start();
        pool.submit(task);
        task.getResult().whenResolved(() -> {
            //warning - a large print!! - you can remove this line if you wish
           // System.out.println(Arrays.toString(task.getResult().get()));
            l.countDown();
        });

        l.await();
        
        pool.shutdown();
    }
    	
    	
    }
}
