package worms.model;

import java.util.List;

import worms.exceptions.IllegalAPException;
import worms.exceptions.IllegalJumpTimeException;
import worms.exceptions.IllegalMassException;
import worms.exceptions.IllegalPositionException;
import worms.exceptions.IllegalStepException;
import worms.exceptions.IllegalTerrainAngleException;
import worms.exceptions.IllegalWorldException;
import worms.model.points.Points;
import worms.model.position.Position;
import be.kuleuven.cs.som.annotate.Basic;
import be.kuleuven.cs.som.annotate.Model;
import be.kuleuven.cs.som.annotate.Raw;

/**
 * a class of moving objects as a special case of gameObjects,
 * the moving objects are objects in the game that can move
 * @invar the moving object must have a valid direction
 * 		|isValidDirection(this.getDirection())
 * @invar the moving object must have a valid mass
 * 		|isValidMass(this.getMass()) 
 * @invar he characteristics of the moving objects must be valid numbers
 * 		| isValidNumber(MovingObject.getMass())
 * 		| isValidNumber(MovingObject.getDirection())
 * @author Cleemput Enrico en Van Buggenhout Niel
 * @version 1.1 
 */
public abstract class MovingObjects extends GameObjects {
	
	/**
	 * Initialize this new moving object with given radius, at a given position, in a given world, with a given mass and a given direction.
	 * @param radius
	 * 		the radius for the moving object
	 * @param position
	 * 		the position of the moving object
	 * @param world
	 * 		the world the moving object is in
	 * @param mass
	 * 		the mass of the moving object
	 * @param Direction
	 * 		the direction of the moving object
	 * @post this new moving object's mass equals the given mass
	 * 		| new.getMass() == mass
	 * @post this new moving object's direction equals the given direction
	 * 		| new.getDirection() == direction
	 * @effect Initialize this new moving object as a game object with given radius, in a given world and at a given position.
	 * 		| super(radius,position,world)
	 */
	@Raw
	public MovingObjects(double radius,Position position,World world,double mass,double Direction) throws IllegalArgumentException, 
			IllegalPositionException,IllegalWorldException,IllegalMassException{
		
		super(radius,position,world);
		this.setMass(mass);
		this.setDirection(Direction);
		}
	
	
// mass

	/** 
	 * Returns the mass of the Worm.
	 */
	@Raw @Basic
	public double getMass(){
		
		return this.mass;
	}
	
	/** 
	 * Checks whether the given mass is a valid value for the Moving Objects mass.
	 * @return
	 * 		true if the mass is bigger than 0 and smaller than infinity
	 * 		|result == ((mass>=0) && (mass != Double.POSITIVE_INFINITY))
	 * 
	 */
	@Raw
	public static boolean isValidMass(double mass){
		return((mass>=0) && (mass != Double.POSITIVE_INFINITY));
		}
	
	/** 
	 * This method sets the Moving Objects mass.
	 * @param mass
	 * 		The value to which to set the mass of the worm.
	 * 
	 * @post The mass is set to the given value
	 * 			| new.getMass() == mass
	 * @post The maximum amount of action points of the worm are adjusted accordingly
	 * 			| setMaxAP() 
	 * 
	 * @throws IllegalMassException
	 * 			the method throws the exception when the given mass is not valid
	 * 			| ! isValidMass(mass)
	 * 
	 */
	@Raw
	protected void setMass(double mass) throws IllegalMassException{
		if (! isValidMass(mass)){
			throw new IllegalMassException(mass);
		}
		this.mass = mass;
		
	}

	/**
	 * The mass of the Worm.
	 */
	protected double mass;
			
// jump	
	
	/** 
	 * Calculates and returns the velocity of the jump if this Worm would jump from the current position in the direction this Worm is facing. 
	 * 
	 * @return the velocity with which this Worm would jump if it did
	 * 		| result ==
	 * 		|	calculateForce()*0.5/getMass()
	 */	
	public double calculateVelocity(){
		
		double F = this.calculateForce();
		double v0 = F*.5/this.getMass();
		return v0;		
	}
	
	/**
	 * a methode to calculate the force that is needed to jump with the Moving Object
	 * 	@return the force excerted on the moving object	
	 */
	public abstract double calculateForce();
	
	
	/**
	 * calculates the position of the jump of a moving object 
	 * at the given time the given direction
	 * @param time
	 * 		the time at which the position of the moving object in the jump is asked
	 * @param direction
	 * 		the direction of the moving object in the jump
	 * @return
	 * 		returns the position on the moment time
	 */
	public abstract Position jumpStep(double time,double direction) throws IllegalJumpTimeException,IllegalArgumentException;
	
	/**
	 * let the moving object jump
	 * @param timestep
	 * 		the timestep needed to find the next position in the jump
	 */
	public abstract void jump(double timestep) throws IllegalJumpTimeException,IllegalArgumentException;
	
	
	/** 
	 * Returns the time it would take for a potential jump from the current position in the direction this Worm is facing.
	 * @effect
	 * 		
	 * 
	 * @return returns the time needed for a moving object to make a jump 
	 * 		|result == 
	 * 		|	time
	 * 		|		- New method: positionAtTime(T) = new Position(getX() + calculateVelocity() * cos(getDirection)*T, getY() + calculateVelocity()*sin(getDirection())*T -g/2*T²)
	 * 		|	for which for each T in [0,time-stepSize]:
	 * 		|		getWorld().isPassableForCircle(getRadius(),getRadius(), positionAtTime(T)) == true
	 * 		|	&& getWorld().isPassableForCircle(getRadius(),getRadius(), positionAtTime(time)) == false
	 */	

	public double jumpTime(double stepSize) throws IllegalJumpTimeException,IllegalArgumentException{
		double t = 0;
		boolean found = false;
		while (!found){
			double alpha = getDirection();
			double v0 = calculateVelocity();
			double v0x = v0*Math.cos(alpha);
			double v0y = v0*Math.sin(alpha);
			double x = getX() + v0x*t;
			double y = getY() + v0y*t - g/2*t*t;
			
			Position newPosition = new Position(x,y);
			t = t + stepSize;
			if (! this.getWorld().isPassableForCircle(this.getRadius(),this.getRadius(), newPosition))
				found = true;
			
		}
		return (t - stepSize);
	}
	
	
	
	
	
	/** 
	 * Checks whether the given t represents a time during which this Worm is jumping. 
	 * @param t
	 * 		the time to check
	 * @return True if 	and only if the time non-negative.
	 * 		| result ==
	 * 		|	 (t>=0)
	 */	
	public boolean isValidJumpTime(double t){
		return (t >= 0);
	}	
	
	
	/** 
	 * Return the direction in which this Worm is facing.
	 */
	@Basic  
	public double getDirection(){
		return this.Direction;
		
	}
	
	 /** 
	  * Checks whether the given value is a valid direction for a movingobject.
	  * @param direction
	  * 	The direction to check
	  * 
	  * @return True if and only if the direction is in ]-2*Math.PI,2*Math.PI[
	  * 	|result ==
	  * 	|	 ((direction < 2*Math.PI) && (direction >-2*Math.PI));
	  */
		@Raw
		public static boolean isValidDirection(double direction){
			return ((direction < 2*Math.PI) && (direction >-2*Math.PI));
		}

	
	/**
	 * Checks whether the moving object is facing down.
	 * @param direction
	 * 		the direction to be checked
	 * @pre the direction given must be valid 
	 * 		| (isValidDirection(direction)
	 * @return true if and only if the direction is in the interval [Pi..2PI] or in [-Pi,0]
	 * 		| result ==
	 * 		|	(((direction > Math.PI) && (direction < 2*Math.PI)) || ((direction > -1*Math.PI) && (direction < 0)))
	 */
	@Model
	protected boolean isFacingDown(double direction){
		assert (isValidDirection(direction));
		return (((direction > Math.PI) && (direction < 2*Math.PI)) || ((direction > -1*Math.PI) && (direction < 0)));
	}
	
	
	/** 
	 * Sets the direction of the worm.
	 * @param direction
	 * 		the direction in which the worm is to be set
	 * @pre the given direction is in ]-2*PI,2*PI[ (valid)
	 * 		|isValidDirection(direction)
	 * @post the new direction equals the given direction
	 *		|new.getDirection() == direction
	 */
	 @Raw @Model
	protected void setDirection(double direction){
		assert(isValidDirection(direction));
		this.Direction = direction;
	}
		
		
	/** 
	 * Direction of the worm.
	 */
	private double Direction = 0;
	
	
	/** 
	 * The variable that represents the gravitational constant.
	 */
	protected static final double g = 9.80665;

	
	
}