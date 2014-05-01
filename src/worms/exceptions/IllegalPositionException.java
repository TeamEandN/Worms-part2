package worms.exceptions;

import worms.model.position.Position;



public class IllegalPositionException extends RuntimeException{
	/**
	 * Initialize this new illegal position exception with the given value
	 * 
	 * @param value
	 * 			The value for this new illegal position exception
	 * 
	 * @post The value of this new illegal position exception is equal to the given value
	 * 			| new.getValue() == value 
	 * 
	 */
	
	public IllegalPositionException(Position value){
		this.value = value;
		
	}
	
	/**
	 * Return the value registered for this illegal position exception
	 */
	public Position getValue(){
		return this.value;
	}
	
	private Position value;
	
	
}
