package worms.exceptions;


public class IllegalNameException extends RuntimeException {

	
	
	/**
	 * Initialize the IllegalNameException with a parameter name
	 * @param name
	 * 		the string for the new IllegalNameException
	 * @post the object must have the given string
	 * 		| name = new.getName()
	 */
	public IllegalNameException(String name){
		 this.name = name;
	}
	/**
	 * @return return the value for the exception
	 * 		|result == name
	 */
	public String getName(){
		return this.name;
	}
	
private final String name;


/**
 * 
 */
private static final long serialVersionUID = 1L;


}

