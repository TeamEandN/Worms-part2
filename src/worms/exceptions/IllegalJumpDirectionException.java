package worms.exceptions;


public class IllegalJumpDirectionException extends RuntimeException{
	/**
	 * Initialize this new illegal jump direction exception with the given value
	 * 
	 * @param value
	 * 			The value for this new illegal jump direction exception
	 * 
	 * @post The value of this new illegal jump direction exception is equal to the given value
	 * 			| new.getValue() == value 
	 * 
	 */
	
	public IllegalJumpDirectionException(double value){
		this.value = value;
		
	}
	
	/**
	 * Return the value registered for this illegal jump direction exception
	 */
	public double getValue(){
		return this.value;
	}
	
	private double value;
	
	
}
