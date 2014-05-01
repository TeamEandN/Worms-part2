package worms.exceptions;


public class IllegalRadiusException extends RuntimeException {
	
	/**
	 * Initialize the new illegal radius Exception with the given value
	 * 
	 * @param value
	 * 		the value for the illegal radius exception
	 * 
	 * @post the value for this illegal radius exception is equal to the given value
	 * 		|new.getValue == value
	 * 		
	 */
	public IllegalRadiusException(double value){
		this.value = value;
	}
	/**
	 * 
	 * @return  Return the value registered for this illegal mass exception
	 */
	
	public double getValue(){
		return this.value;
	}
	/**
	 * 
	 */
	private final double value;
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
}
