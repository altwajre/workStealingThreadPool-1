package bgu.spl.a2.sim;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * A class that represents a product produced during the simulation.
 */
public class Product  implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private long startId;
	private String name;
	private List<Product>parts;
	private long finalId;
	
	
	/**
	* Constructor 
	* @param startId - Product start id
	* @param name - Product name
	*/
    public Product(long startId, String name){
    	this.startId = startId;
    	this.name = name;
    	this.finalId = startId;
    	this.parts = new ArrayList<>();
    }

	/**
	* @return The product name as a string
	*/
    public String getName(){
    	return this.name;
    }

	/**
	* @return The product start ID as a long. start ID should never be changed.
	*/
    public long getStartId(){
    	return startId;
    }
    
	/**
	* @return The product final ID as a long. 
	* final ID is the ID the product received as the sum of all UseOn(); 
	*/
    public long getFinalId(){
    	return this.finalId;
    }
    
	/**
	* @return Returns all parts of this product as a List of Products
	*/
    public List<Product> getParts(){
    	return parts;
    }

	/**
	* Add a new part to the product
	* @param p - part to be added as a Product object
	*/
    public void addPart(Product p){
    	parts.add(p);
    }
    
    
    /**
	* set the final Id of the product
	* @param finalId - the id to be set as the finalId of this product.
	*/
    public void setFinalId(long finalId){
    	this.finalId=finalId;
    }


}
