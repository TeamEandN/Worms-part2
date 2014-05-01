package worms.exceptions;


public class IllegalYCoordinateException extends RuntimeException{
	/**
	 * Initialize this new illegal y-coordinate exception with the given value
	 * 
	 * @param value
	 * 			The value for this new illegal y-coordinate exception
	 * 
	 * @post The value of this new illegal y-coordinate exception is equal to the given value
	 * 			| new.getValue() == value 
	 * 
	 */
	
	public IllegalYCoordinateException(double value){
		this.value = value;
		
	}
	
	/**
	 * Return the value registered for this illegal y-coordinate exception
	 */
	public double getValue(){
		return this.value;
	}
	
	private double value;
	
	
}
