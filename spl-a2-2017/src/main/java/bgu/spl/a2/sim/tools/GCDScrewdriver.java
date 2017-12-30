package bgu.spl.a2.sim.tools;

import bgu.spl.a2.sim.Product;
import java.math.BigInteger;



public class GCDScrewdriver implements Tool {

	public GCDScrewdriver() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public String getType() {
		return "GCD screwdriver";
	}
	
	  public long useOn(Product p){
	    	long value=0;
	    	for(Product part : p.getParts()){
	    		value+=Math.abs(func(part.getFinalId()));
	    	}
	      return value;
	    }
	  
	  
	   public long func(long id){
    	BigInteger b1 = BigInteger.valueOf(id);
        BigInteger b2 = BigInteger.valueOf(reverse(id));
        long value= (b1.gcd(b2)).longValue();
        return value;
    }
	   
  public long reverse(long n){
    long reverse=0;
    while( n != 0 ){
        reverse = reverse * 10;
        reverse = reverse + n%10;
        n = n/10;
    }
    return reverse;
  }
}
