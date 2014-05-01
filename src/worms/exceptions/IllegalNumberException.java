package worms.exceptions;


public class IllegalNumberException extends RuntimeException{
	/**
	 * 
	 */

	/**
	 * Initialize this new illegal Number exception with NaN
	 * 
	 * 
	 * @post The value of this new illegal number exception is NaN
	 * 			| new.getValue() == Double.NaN
	 * 
	 */
	
	public IllegalNumberException(){
		this.value = Double.NaN;
		
	}
	
	/**
	 * Return the value registered for this illegal AP exception
	 */
	public double getValue(){
		return this.value;
	}
	
	private double value;
	
	
}
