package worms.model.position;

import java.math.BigDecimal;
import worms.exceptions.*;
import be.kuleuven.cs.som.annotate.*;


/** A class of positions involving an x- and a y- coordinate (Cartesian coordinates).
 * 
 * @invar the value for x has to be valid
 * 		| isValidsX(this.getX())
 * @invar the value for y has to be valid
 * 		| isValidY(this.getY())
 * 
 * @version 1.1
 * @author  Van Cleemput Enrico and Van Buggenhout Niel
 */

@Value
public class Position implements Comparable<Position> {
	 
	
	/** 
	 * Initialize this position with the given Cartesian coordinates.
	 * @param x
	 * 			The x-coordinate for this new position.
	 * @param y
	 * 			The y-coordinate for this new position.
	 * 
	 * @throws IllegalXCoordinateException
	 * 			The value for x is not valid.
	 * 			| ! isValidX(x)
	 * @throws IllegalYCoordinateException
	 * 			The value for y is not valid
	 * 			| ! isValidY(y))
	 */
	@Raw 
	public Position(double x, double y) throws IllegalXCoordinateException, IllegalYCoordinateException{
		if (!isValidX(x))
			throw new IllegalXCoordinateException(x);
		if (!isValidY(y))
			throw new IllegalYCoordinateException(y);
		
		this.x = x; 
		this.y = y;
	}
	
	/** 
	 * Return the x-coordinate of this position.
	 */
	@Basic @Immutable
	public double getX(){
		return this.x;
	}
	
	/**
	 * Checks whether the given x is a valid x-coordinate.
	 * @param x
	 * 		The value for x to check.
	 * @return
	 * 		True if and only if x is greater than the minimum allowed and smaller than maximum allowed value for x
	 * 		| result = ((x >= getMinX()) && (x <= getMaxX()))
	 */
	@Raw
	public static boolean isValidX(Double x){
		return ((x >= getMinX()) && (x <= getMaxX())); 
	}

	/** 
	 * The x-component of the position (x-coordinate) of the worm.
	 */
	private final double x;
	
	/**
	 * Returns the maximum allowed value for an x-coordinate.
	 */
	@Basic @Raw @Immutable
	private static Double getMaxX() {
		return maxX;
	}
	
	/**
	 * The maximum allowed value for an x-coordinate.
	 */
	private final static double maxX = Double.MAX_VALUE;
	
	/**
	 * Returns the minimum allowed value for an x-coordinate.
	 */
	@Basic @Raw @Immutable
	private static Double getMinX() {
		return minX;
	}
	
	/**
	 * The minimum allowed value for an x-coordinate.
	 */
	private final static double minX = -1 * Double.MAX_VALUE;
	
	
	
	/** 
	 * Return the y-component of the position (y-coordinate) of the terrain.
	 */
	
	 @Basic @Immutable
	public double getY(){
		return this.y;
	}
	 
	
	 /**
	  * Checks whether the given y is a valid y-coordinate.
	  * @param y
	  * 		The value for y to check.
	  * @return
	  * 		True if and only if y is greater than the minimum allowed and smaller than maximum allowed value for y.
	  * 		| result = ((y >= getMinY()) && (y <= getMaxY()))
	  */
	
	@Raw
	public static boolean isValidY(Double y){
		return ((y>=getMinY()) && (y<=getMaxY()));
		
	}
	
	/** 
	 * The y-component of the position (y-coordinate) of the worm.
	 */
	private final double y;
		
	
	/**
	 * Returns the maximum allowed value for an y-coordinate.
	 */
	@Basic @Raw @Immutable 
	private static Double getMaxY() {
		return maxY;
	}
	
	/**
	 * The maximum allowed value for an y-coordinate.
	 */
	private final static double maxY = Double.POSITIVE_INFINITY;
	
	/**
	 * Returns the minimum allowed value for an y-coordinate.
	 */
	@Basic @Raw @Immutable
	private static Double getMinY() {
		return minY;
	}
	
	/**
	 * The minimum allowed value for an y-coordinate.
	 */
	private final static double minY = Double.NEGATIVE_INFINITY;
	
	
	
	/** 
	 * Returns the position of the worm as a list, with the first element the x-coordinate and the second element the y-coordinate.
	 * 
	 * Return the x - and y-coordinate of the position of the worm as a list
	 * 			| Arrays.asList(getX(),getY())
	 */
	public double[] getPosition(){
		double a= this.getX();
		double b = this.getY();
		double[]  coordinates = {a,b};
		
		return coordinates; 
	}
	

	/**
	 * This method calculates the sum of two position.
	 * @param other
	 * 		The other position to add
	 * @return The resulting position's 
	 * 			x-coordinate will equal the sum of the x-coordinates of other and of this position
	 * 			y-coordinate will equal the sum of the y-coordinates of other and of this position.
	 * 		| newPos.getX() = this.getX()+other.getX()
	 * 		| newPos.getY() = this.getY()+other.getY()
	 * @throws IllegalArgumentException
	 * 		The other position is not valid.
	 * 		| !isValidPosition(other)
	 */
	public Position add(Position other) throws IllegalArgumentException, IllegalXCoordinateException,IllegalYCoordinateException {
		if (!isValidPosition(other)){
			throw new IllegalArgumentException("Non-effective position");
		}
		double x = getX()+other.getX();
		double y = getY()+other.getY();
		Position newPos = new Position(x,y);
		return newPos;
	}

	/**
	 * Checks whether the given position is valid.
	 * @param position
	 *		The position to be checked.
	 * @return True if and only if the position is not equal to null.
	 * 		|result = (position != null)
	 */
	public static boolean isValidPosition(Position position){
		return ((position != null) && (isValidX(position.getX()) && (isValidY(position.getY()))));
	}
	
	/**
	 * Calculates and returns the distance between two positions.
	 * @param other
	 * 		The other position to which the distance is to be calculated.
	 * @return the distance between this position and other position
	 * 		|result == 
	 * 		|	 sqrt((this.getX()-other.getX())² + (this.getY()-other.getY())²)
	 */
	public double calculateDistance(Position other){
		if (!isValidPosition(other))
			throw new IllegalArgumentException("not a valid position");
		double x1 = this.getX();
		double y1 = this.getY();
		
		double x2 = other.getX();
		double y2 = other.getY();
		
		double distanceX = x1-x2;
		double distanceY = y1 -y2;
		
		return Math.sqrt(Math.pow(distanceX, 2)+Math.pow(distanceY, 2));
	}
	
	/**
	 * Compare this position with the other position
	 * 
	 * @param other
	 * 		The other Position to compare with.
	 * @return 0, if both the x and y of this Position are the same as the x and y of other
	 * 
	 * 		   1, if x of this position is larger than x of other
	 * 			
	 * 		   -1, if x of this position is smaller than x of other 
	 */
	
	@Override
	public int compareTo(Position other) throws ClassCastException{
	if (other == null)
		throw new ClassCastException("Non-effective position");
	if (this.getX()==other.getX()){
		if (this.getY()==other.getY())
			return 0;
		if (this.getY()<other.getY())
			return -1;
		else
			return 1;
	}
	else {
		if (this.getX()<other.getX())
			return -1;
	}
	return 1;
	
	}
	
	
	/**
	 * Check whether this Position is equal to the given object
	 * 
	 * @return True if and only if the given object is effective,
	 * 			if this Position and the given object belong to the same class,
	 * 			and if this Position has the same coordinates (x and y value) as the given object.
	 * 			|result ==
	 * 			|	(	(other != null)
	 * 			|	&& (this.getClass() == other.getClass())
	 * 			| 	&& (this.getX() == (Position other).getX())
	 * 			|	&& (this.getY() == (Position other).getY())	)
	 */
	@Override
	public boolean equals(Object other){
		if (other==null)
			return false;
		if (this.getClass() != other.getClass())
			return false;
		Position otherPosition = (Position)other;
		return((this.getX()==otherPosition.getX()) && this.getY()==otherPosition.getY());
	}
	
	
	/**
	 * Return the hash code for this position
	 */
	@Override
	public int hashCode(){
		BigDecimal bigX = BigDecimal.valueOf(getX());
		BigDecimal bigY = BigDecimal.valueOf(getY());
		BigDecimal multiplicatorX = BigDecimal.valueOf(464556.812);
		BigDecimal multiplicatorY = BigDecimal.valueOf(77894.45);
		BigDecimal maxInt = BigDecimal.valueOf(Integer.MAX_VALUE);
		
		BigDecimal afterOperation = (bigY.multiply(multiplicatorY).add(bigX.multiply(multiplicatorX)));
		BigDecimal afterModulo = afterOperation.remainder(maxInt);
		int hashCode = afterModulo.intValue();
		return hashCode;
	}
	
	
	/**
	 * Return a textual representation of this Position.
	 * 
	 * @return A string consisting of the textual representation of the x-value of the position 
	 * 			followed by the textual representation of the y-value of the position
	 * 			seperated by a comma enclosed by round brackets.
	 */
	@Override
	public String toString(){
		return "(" + String.valueOf(this.getX()) + "," + String.valueOf(this.getY()) + ")";
	}



	
	
	
	
}
