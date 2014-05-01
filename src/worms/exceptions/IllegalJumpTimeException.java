package worms.exceptions;


public class IllegalJumpTimeException extends RuntimeException{
	/**
	 * Initialize this new illegal jump time exception with the given value
	 * 
	 * @param value
	 * 			The value for this new illegal jump time exception
	 * 
	 * @post The value of this new illegal jump time exception is equal to the given value
	 * 			| new.getValue() == value 
	 * 
	 */
	
	public IllegalJumpTimeException(double value){
		this.value = value;
		
	}
	
	/**
	 * Return the value registered for this illegal jump time exception
	 */
	public double getValue(){
		return this.value;
	}
	
	private double value;
	
	
}
