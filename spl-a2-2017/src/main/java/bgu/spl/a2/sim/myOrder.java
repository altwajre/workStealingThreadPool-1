package bgu.spl.a2.sim;

public class myOrder {
	private String product;
	private int qty;
	private long startId;

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
	 * @return quantity
	 */
	public int getQty() {
		return qty;
	}
	/**
	 * setter 
	 * set the quantity
	 */

	public void setQty(int qty) {
		this.qty = qty;
	}


	/**
	 * getter
	 * @return start id
	 */
	public long getStartId() {
		return startId;
	}
	
	/**
	 * setter 
	 * set the start id 
	 */

	public void setStartId(long startId) {
		this.startId = startId;
	}
	
	
}