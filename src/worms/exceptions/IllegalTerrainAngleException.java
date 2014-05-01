package worms.exceptions;


public class IllegalTerrainAngleException extends RuntimeException{
	/**
	 * Initialize this new illegal terrain angle exception with the given value
	 * 
	 * @param value
	 * 			The value for this new illegal terrain angle exception
	 * 
	 * @post The value of this new illegal terrain angle exception is equal to the given value
	 * 			| new.getValue() == value 
	 * 
	 */
	
	public IllegalTerrainAngleException(double value){
		this.value = value;
		
	}
	
	/**
	 * Return the value registered for this illegal terrain angle exception
	 */
	public double getValue(){
		return this.value;
	}
	
	private double value;
	
	
}
