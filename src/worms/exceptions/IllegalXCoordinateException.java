package worms.exceptions;


public class IllegalXCoordinateException extends RuntimeException{
	/**
	 * Initialize this new illegal x-coordinate exception with the given value
	 * 
	 * @param value
	 * 			The value for this new illegal X-coordinate exception
	 * 
	 * @post The value of this new illegal x-coordinate exception is equal to the given value
	 * 			| new.getValue() == value 
	 * 
	 */
	
	public IllegalXCoordinateException(double value){
		this.value = value;
		
	}
	
	/**
	 * Return the value registered for this illegal x-coordinate exception
	 */
	public double getValue(){
		return this.value;
	}
	
	private double value;
	
	
}
