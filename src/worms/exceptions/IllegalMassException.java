package worms.exceptions;


public class IllegalMassException extends RuntimeException {
	
	/**
	 * Initialize the new illegal Mass Exception with the given value
	 * 
	 * @param value
	 * 		the value for the illegal Mass exception
	 * 
	 * @post the value for this illegal Mass exception is equal to the given value
	 * 		|new.getValue == value
	 * 		
	 */
	public IllegalMassException(double value){
		this.value = value;
		
	}
	/**
	 * 
	 * @return  Return the value registered for this illegal mass exception
	 */
	
	public double getValue(){
		return this.value;
	}
	
private final double  value;

/**
 * 
 */
private static final long serialVersionUID = 1L;

}
