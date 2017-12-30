package bgu.spl.a2.sim;

import bgu.spl.a2.sim.conf.ManufactoringPlan;

public class myManufactoringPlan {
	private String product;
	private String[] tools;
	private String[] parts;
	
/**
 * getter
 * @return product
 */
	
	
	public String getProduct() {
		return product;
	}
	
	
	/**
	 * setter 
	 * set the product
	 */
	public void setProduct(String product) {
		this.product = product;
	}
	
	/**
	 * getter
	 * @return tools
	 */
	public String[] getTools() {
		return tools;
	}
	
	/**
	 * setter 
	 * set the tools
	 */
	public void setTools(String[] tools) {
		this.tools = tools;
	}

	/**
	 * getter
	 * @return parts
	 */
	public String[] getParts() {
		return parts;
	}
	
	/**
	 * setter 
	 * set the parts
	 */
	public void setParts(String[] parts) {
		this.parts = parts;
	}
	
	/**
	 * 
	 * @return new manufacturing plan
	 */
	public ManufactoringPlan givePlan(){
		return new ManufactoringPlan(product, parts, tools);
	}
}
