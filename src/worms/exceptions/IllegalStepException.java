package worms.exceptions;


public class IllegalStepException extends RuntimeException {
	/**
	 * Initialize the new illegal radius Exception with the given value
	 * 
	 * @param value
	 * 		the value for the illegal Step exception
	 * 
	 * @post the value for this illegal Step exception is equal to the given value
	 * 		|new.getValue == value
	 * 		
	 */
	private static final long serialVersionUID = 1L;


    /**
     * 
     * @param value
     */
	public  IllegalStepException(int value){
		this.value = value;
	}
	/**
	 * 
	 * @return Return the value registered for this illegal mass exception
	 */
	public int getValue(){
		return this.value;
	}
	
	
	/**
	 *
	 */
	private final int value;
	
}

