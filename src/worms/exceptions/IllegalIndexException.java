package worms.exceptions;

import worms.model.position.Position;

public class IllegalIndexException extends RuntimeException {
	
	/**
	 * Initialize this new illegal position exception with the given value
	 * 
	 * @param string
	 * 			The value to describe this new illegal position exception
	 * 
	 * @post The value of this new illegal position exception is equal to the given value
	 * 			| new.getValue() == value
	 * 
	 */
	
	public IllegalIndexException(int [] value){
		this.value = value;
		
	}
	
	

	/**
	 * Return the value registered for this illegal position exception
	 */
	public int[] getValue(){
		return this.value;
	}
	
	private int[] value;

}
