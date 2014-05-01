package worms.model.points;

import be.kuleuven.cs.som.annotate.*;

/**
 * A class of points involving an amount of points and a sort of points.
 * @invar the amount of each point has to be a valid numeral
 * 		|this.hasProperPoints(getNumeral())
 * @invar the sort of each point has to be a valid sort.
 * 		|this.isValidSort(getSort())
 * @author Cleemput Enrico en Van Buggenhout Niel
 */


@Value
public class Points implements Comparable<Points>{
	

	/**
	 * Initialize this new points with given numeral and given sort.
	 * 
	 * @param numeral
	 * 		the amount of points to be given to the object
	 * @param sort
	 * 		the sort to be given to the object
	 * @post The numeral of this new points is equal to the given numeral
	 * 		| new.getNumeral() == numeral
	 * @post The sort of this new points is equal to the given sort
	 * 		| new.getSort() == sort
	 * @throws IllegalArgumentException
	 * 		the sort has to be valid
	 * 		|if (! isValidSort(sort))
	 * 		the amount of points has to be valid
	 * 		|if (! isValidNumeral(numeral))
	 * 
	 */
	public Points(int numeral, SortPoints sort) throws IllegalArgumentException{
		if (! isValidSort(sort))
				throw new IllegalArgumentException("not a valid sort");
		if (!isValidNumeral(numeral))
			throw new IllegalArgumentException("not valid points");
		
		this.numeral = numeral;
		this.sort = sort;
		
		
	}
	
	
	public final static Points HPNEWTURN = new Points(10,SortPoints.HP);
	
	
	
	/**
	 * returns the sort for the points
	 */
	@Basic @Immutable
	public SortPoints getSort(){
		return this.sort;
	}
	
	/**
	 * Checks whether the given sort of points is a valid sort for any points.
	 * @param sort
	 * 		the sort of points to be checked
	 * @return return true if sort is not null
	 * 		|result = (sort != null)
	 */
	public static boolean isValidSort(SortPoints sort){
		return (sort != null);
	}
	
	/**
	 * Variable referencing the sort of points of this points.
	 */
	private final SortPoints sort;
	
	/**
	 * Returns the amount of points. 
	 */
	@Basic @Immutable
	public int getNumeral(){
		return this.numeral;
	}
	
	/**
	 * checks whether the given amount of points is valid
	 * @param numeral
	 * 		the amount of points to be checked
	 * @return
	 * 		returns true if the amount of points is bigger than zero
	 * 		|result = (numeral >= 0)
	 */
	public static boolean isValidNumeral(int numeral){
		return (numeral >= 0);
	}
	
	/**
	 * Variable referencing the numeral of this points.
	 */
	private final int numeral;
	
	/**
	 * Checks if the points is valid.
	 * @param points
	 * 		The points to be checked.
	 * @return true if and only is point is not null and the numeral of points is greater than 0. 
	 * 		| result = (points != null)
	 */
	public static boolean isValidPoint(Points points){
		//System.out.println(points);
		return ((points != null) && (points.getNumeral() >= 0));
	}
	
	
	
	/**
	 * Compute the sum of this points and the other points.
	 * @param other
	 * 		The other points to add.
	 * @return The resulting points has the same sort.
	 * 		| result.getSort() == this.getSort()
	 * @return The numeral of the resulting points equals the sum of the numerals of both points.
	 * 		| result = new Points(sum,this.getSort())
	 * @throws IllegalArgumentException
	 * 		The sort of points of other does not equal the sort of points of this or other is not a valid points
	 * 		|if (((this.getSort() != points.getSort()) && isValidPoint(other)
	 * 
	 */
	public Points add(Points other) throws IllegalArgumentException{
		if (isValidPoint(other) && (! this.isComparableTo(other)))
			throw new IllegalArgumentException("the sort of points has to be the same");
		
		int sum = other.getNumeral() + this.getNumeral();
		return new Points(sum,this.getSort());
	}
	
	/**
	 * Compute the subtraction of the other points from this points.
	 * @param other
	 * 		The points to subtract.
	 * @return If the sort of other equals the sort of this, the resulting numeral will equal
	 * 			the numeral of this decremented by the numeral of other. 
	 * 		|result = new Points(difference,this.getSort());
	 * @throws IllegalArgumentException
	 		The sort of points of other does not equal the sort of points of this or other is not valid points. 
	 * 		|if (((this.getSort() != points.getSort()) && isValidPoint(points)
	 * 		
	 */
	public Points substract(Points points) throws IllegalArgumentException{
		if (isValidPoint(points) && (! this.isComparableTo(points)))
			throw new IllegalArgumentException("the sort of points has to be the same");
		
		int difference = this.getNumeral() - points.getNumeral();
		
		if (difference < 0)
			return new Points (0,this.sort);
		
		return new Points(difference,this.getSort());
	}
	/**
	 * Checks if there are enough points left.
	 * @param pointsNeeded
	 * 		The points needed. 
	 * @return True if and only if there are more points than needed or there are just enough points (this is equal to points needed).
	 * 		|result ==
	 * 		|	 (this.isGreaterThan(PointsNeeded) || this.isEqualTo(pointsNeeded))
	 * @throws	IllegalArgumentException 
	 * 		The points are not comparable or pointsNeeded is not valid
	 * 		| if (!(this.isComparableTo(pointsNeeded)) || !(isValidPoint(pointsNeeded)))
	 */
	public boolean enoughPointsLeft(Points pointsNeeded) throws IllegalArgumentException{
		if ((! this.isComparableTo(pointsNeeded)) || (! isValidPoint(pointsNeeded)))
			throw new IllegalArgumentException("the sort of points has to be the same");
		return (this.isGreaterThan(pointsNeeded) || this.isEqualTo(pointsNeeded));
	}
	
	/**
	 * Checks if two points are comparable.
	 * @param other
	 * 		The other points to compare with.
	 * @return True if and only if the two points have the same sort.	
	 * 		| result = (this.getSort() == other.getSort());
	 */
	public boolean isComparableTo(Points points){
		return (this.getSort() == points.getSort());
	}

	
	/**
	 * Checks whether this points is greater than other points.
	 * @param other
	 * 		The other points to compare with.
	 * @return True if and only if the numeral of this is greater than the numeral of other.
	 * 		| result ==
	 * 		|	this.getNumeral() > other.getNumeral()
	 * @throws ClassCastException
	 * 		The other points is not effective or the other is not comparable to this
	 * 		| ( (other == null)
	 * 		| || (! this.isComparableTo(other)))
	 */
	public boolean isGreaterThan(Points other) throws ClassCastException{
		if (other == null){
			throw new ClassCastException("non-effective Points");
		}
		if (! this.isComparableTo(other))
			throw new ClassCastException("incompatible Points");
		
		return (this.getNumeral() > other.getNumeral());
	}
	
	/**
	 * Checks whether this points is equal to other points.
	 * @param other
	 * 		The other points to compare with.
	 * @return True if and only if the numeral of this is equal the numeral of other.
	 * 		| result ==
	 * 		|	this.getNumeral() == other.getNumeral()
	 * @throws ClassCastException
	 * 		The other points is not effective or the other is not comparable to this
	 * 		| ( (other == null)
	 * 		| || (! this.isComparableTo(other)))
	 */
	public boolean isEqualTo(Points other) throws IllegalArgumentException{
		if (other == null){
			throw new ClassCastException("non-effective Points");
		}
		if (! this.isComparableTo(other))
			throw new ClassCastException("incompatible Points");
		
		return (this.getNumeral() == other.getNumeral());
	}
	
	/**
	 * Checks whether this points is smaller than other points.
	 * @param other
	 * 		The other points to compare with.
	 * @return True if and only if the numeral of this is smaller than the numeral of other.
	 * 			In other words: if this is not greater than and not equal to other
	 * 		| result ==
	 * 		|	((! isGreaterThan(other)) && (! isEqualTo(other)))
	 */
	public boolean isSmallerThan(Points other) throws ClassCastException{
		return ((! isGreaterThan(other)) && (! isEqualTo(other)));
	}
	
	
	/**
	 * Checks whether this points is equal to the given object.
	 * @param other
	 * 		The object to check.
	 * @return True if and only if the given object is effective,
	 * 			if this points and the given object belong to the same class 
	 * 			and if this points and the other object interpreted as points are equal to each other.
	 * 		| result ==
	 * 		|	( (other != null)
	 * 		|	&& (this.getClass() == other.getClass())
	 * 		|	&& (this.isComparableTo( (Points other)))
	 * 		|	&& (this.isEqualTo( (Points other))) )
	 */
	@Override
	public boolean equals(Object other){
		if (other == null)
			return false;
		if (this.getClass() != other.getClass())
			return false;
		
		Points otherPoints = (Points) other;
		
		if (! this.isComparableTo(otherPoints)){
			return false;
		}
		
		return (this.isEqualTo(otherPoints));
	}
	
	/**
	 * Return the hash code of this points.
	 */
	@Override
	public int hashCode(){
		
		return (this.getNumeral() + this.getSort().hashCode()); 
	}

	/**
	 * Return a textual representation of this points.
	 * 
	 * @return A string consisting of the textual representation of the value of the points 
	 * 			followed by the textual representation of the sort of the Points
	 * 			seperated by a comma encolsed by square brackets.
	 */
	@Override
	public String toString(){
		return "[" + this.getNumeral() + " " + this.getSort().toString() + "]";
	}

	
	/**
	 * Compare this points with the other points.
	 * @param other
	 * 		The other points to compare with
	 * @return 1, if this is greater than other
	 * 		| if (this.isGreaterThan(other))
	 * 		| 	then result == 1
	 * 			0, if this is equal to other
	 * 		| if (this.isEqualTo(other))
	 * 		|	then result == 0
	 * 			-1, if this is smaller than other
	 * 		| if (this.isSmallerThan(other))
	 * 		|	then result == -1 
	 */
	@Override
	public int compareTo(Points other) {
		if (this.isGreaterThan(other)){
			return 1;
		}
		if (this.isEqualTo(other)){
			return 0;
		}
		return -1;
	}

}