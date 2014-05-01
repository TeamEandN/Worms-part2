package worms.exceptions;


public class IllegalAPException extends RuntimeException{
	/**
	 * Initialize this new illegal AP exception with the given value
	 * 
	 * @param value
	 * 			The value for this new illegal AP exception
	 * 
	 * @post The value of this new illegal AP exception is equal to the given value
	 * 			| new.getValue() == value 
	 * 
	 */
	
	public IllegalAPException(double value){
		this.value = value;
		
	}
	
	/**
	 * Return the value registered for this illegal AP exception
	 */
	public double getValue(){
		return this.value;
	}
	
	private double value;
	
	
}
