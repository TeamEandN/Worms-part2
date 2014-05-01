package worms.model;



import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import worms.*;
import worms.exceptions.*;
import worms.exceptions.*;
import worms.model.Team;
import worms.model.points.Points;
import worms.model.points.SortPoints;
import worms.model.position.Position;
import be.kuleuven.cs.som.annotate.*;


/**
 * A class of worms that involves naming, moving and turning the worm, changing it's AP and making it jump.
 * 
 * @invar a worm shall never have more AP than the maximum AP for this worm and never less than 0 AP
 * 		| (getCurrentAP() <= getMaxAP()) && (getCurrentAP() >= 0)
 * @invar the name of the worm is valid
 * 		| isValidName(getName())
 * @invar the characteristics of the worm must be valid numbers
 * 		| isValidNumber(worm.getCurrentAP())
 * 		| isValidNumber(worm.getMaxAP())
 * 		| isValidNumber(worm.getLowerBoundRadius())
 * 		| isValidNumber(worm.getTerrainAngle())
 * @invar the worm has proper weapons as it's weapons
 * 		|hasProperWeapons()
 * @invar the worm has a valid team as it's team
 * 		|isValidTeam()
 * @invar the worm has a valid radius as it's radius
 * 		|isValidRadius(this.getRadius())
 * 
 * 
 * @author van Cleemput Enrico en Van Buggenhout Niel
 * 	course of studies: civil engineer
 * @version 1.2
 **/



public class Worm extends MovingObjects{


	/**
	 * This method initializes a new Worm with a given name, radius, x and y- coordinates that make up a position, direction and places the worm in the given world .
	 * @param name
	 * 		The name of this new Worm.
	 * @param radius
	 * 		The radius of this new Worm.
	 * @param x
	 * 		The x-coordinate of the position of this new Worm.
	 * @param y
	 * 		The y-coordinate of the position of this new Worm.
	 * @param Direction
	 * 		The orientation (direction) of this new Worm.
	 * @param world
	 * 		The world to add this new worm to.
	 * 
	 * @post the lower bound for the radius of this new Worm is equal to 0.25
	 * 		| new.getLowerBoundRadius() == 0.25
	 * @post the mass of this new Worm equals p*(4/3*Pi*(the given radius)³)
	 * 		| new.getMass() == p*4/3*Math.PI*Math.pow((new.getRadius()),3)
	 * @post the maximum amount of action points of this new Worm equals the mass of this new Worm 
	 * 		|new.getMaxAP() == new.getMass()
	 * @post the given x-coordinate (y-coordinate) is set as the x-coordinate (y-coordinate) of the Position of this new Worm
	 * 		| new.getX() == x && new.getY() ==y 
	 *
	 * 
	 * @effect the given name is the name of this new Worm
	 * 		| setName(name)
	 * @effect the current action points of this new Worm equals the maximum amount of action points of this new Worm
	 * 		| this.setCurrentAP(new Points(this.getMaxPoints(),SortPoints.AP))
	 * @effect the current hit points of this new Worm equals the maximum amount of hit points for this new Worm
	 * 		| this.setCurrentHP(new Points(this.getMaxPoints(),SortPoints.HP))
	 * @effect this new worm is initialized as a moving object with given radius, given position, given world, calculated mass (via given radius) and given direction
	 * 		| super(radius, new Position(x,y),world, calculateMass(radius,0.25),Direction)
	 * @effect a bazooka is added to this new worm's weapons
	 * 		| addWeapon(new Bazooka())
	 * @effect a rifle is added to this new worm's weapons
	 * 		| addWeapon(new Rifle())
	 * 
	 */
	@Raw
	public Worm(String name, double radius,double x, double y ,double Direction, World world ) throws IllegalNameException,IllegalRadiusException,
	IllegalPositionException{
		super(radius, new Position(x,y),world, calculateMass(radius,0.25),Direction);
						
		this.setName(name);
		this.setCurrentAP(new Points(this.getMaxPoints(),SortPoints.AP));
		this.setCurrentHP(new Points(this.getMaxPoints(),SortPoints.HP));
		this.addWeapon(new Bazooka());
		this.addWeapon(new Rifle());
				
	}
	
	/**
	 * Initialize a worm in given world at given position, with default values for all the other characteristics of a worm.
	 * @param world
	 * 		the world to which this new worm belongs
	 * @param position
	 * 		the position at which this new worm will be positioned
	 * @effect this new worm is initialized as a worm with given world, given position, name "DefaultName", radius 0.25 and direction 0;
	 */
	public Worm(World world,Position position){
		this("DefaultName",0.25, position.getX(),position.getY(),0,world);
		
	}
	
	
	/**
	 * Checks whether a characteristic of the Worm is a valid number.
	 * @param characteristic
	 * 			The characteristic of the Worm to check.
	 * 
	 * @return true if and only if the characteristic is not NaN
	 * 			| result ==
	 * 			|	 (! Double.isNaN(characteristic))
	 */
	@Raw
	public static boolean isValidNumber(double characteristic){
		return (! Double.isNaN(characteristic));
	}
	
	/** 
	 * This method calculates and returns the mass given the radius of a Worm.
	 * @param radius
	 * 		the radius of the Worm
	 * 
	 * @return the mass of the Worm with the given radius
	 * 		|result == p*(4/3*PI*radius³)
	 * 
	 * @throws IllegalRadiusException
	 * 		if the given radius is not valid
	 * 		|(! isValidRadius(radius))
	 */
	@Raw
	public static double calculateMass(double radius,double lowerbound) throws IllegalRadiusException{
		if (!isValidRadius(radius,lowerbound)){
			throw new IllegalRadiusException(radius);
		}
		return p*(4*Math.PI*Math.pow(radius, 3))/3;
	}
	
	/** 
	 * Sets the mass according to the supplied radius, the mass will be calculated by the method calculateMass.
	 * @param radius
	 * 		the radius of the worm
	 * 
	 * @post The worm's mass equals the mass calculated by the method calculateMass
	 * 		| new.getMass() == this.calculateMass(radius)
	 * 
	 * @throws IllegalRadiusException
	 * 		if the given radius is not valid
	 * 		|(! isValidRadius(radius))
	 */
	@Raw 
	public void setMassGivenRadius(double radius) throws IllegalRadiusException{
		if (!isValidRadius(radius, this.getLowerBoundRadius())){
			throw new IllegalRadiusException(radius);
		}
		this.setMass(calculateMass(radius,this.getLowerBoundRadius()));
		
	}
	
	/** 
	 * This method sets the Worm's mass.
	 * @param mass
	 * 		The value to which to set the mass of the worm.
	 * 
	 * @post The mass is set to the given value
	 * 		| new.getMass() == mass
	 * @effect The maximum amount of points of the worm are adjusted accordingly
	 * 		| setMaxPoints() 
	 * 
	 * @throws IllegalMassException
	 * 		if the given mass is not valid
	 * 		| ! isValidMass(mass)
	 * 
	 */
	@Raw @Override
	protected void setMass(double mass) throws IllegalMassException{
		if (! isValidMass(mass)){
			throw new IllegalMassException(mass);
		}
		this.mass = mass;
		setMaxPoints();
		
	}
		
	
	/**
	 * The density of the Worms.
	 */
	private static final int p= 1062;


	/** 
	 * Return the lower bound for the radius assigned to this Worm.
	 * Now: LowerBoundRadius is 0.25 for every Worm
	 * 
	 * @return The lowest possible value for the Worm's radius is non-negative
	 * 			| result >=0
	 * 
	 */
	@Basic @Raw
	public double getLowerBoundRadius() {
		return this.lowerBoundRadius;
	}
	
	 /** 
	  * Increases the radius by the given amount.
	  * @param radius
	  * 		The given radius to add to the current radius of the Worm
	  * 
	  * @effect The new radius equals the old radius substracted with the given radius.
	  * 		| setRadius(getRadius() - radius)
	  * 
	  * @throws IllegalRadiusException
	  * 		The  given value for radius is negative
	  * 		| radius < 0 
	  */		
	public void increaseRadiusBy(double radius) throws IllegalRadiusException{
		if (radius < 0){
			throw new IllegalRadiusException(radius);
		}
		this.setRadius(getRadius()+radius);
	}		
		
	/** 
	 * Sets the lower bound for the radius of this Worm.
	 * @param radius
	 * 		The radius to which to set the new lower bound for the radius of this worm
	 * 
	 * @post The new lower bound for the radius of the worm equals the given radius
	 * 		| new.getLowerBoundRadius() == radius
	 * 
	 * @throws IllegalRadiusException
	 * 		if the given value for the lower bound radius is not valid for the lower bound of the raidus of this Worm
	 * 		| ! isValidLowerBoundRadius() 
	 */
	@Raw
	protected void setLowerBoundRadius(double radius) throws IllegalRadiusException{
		if (! isValidLowerBoundRadius(radius)){
			throw new IllegalRadiusException(radius);
		}
		this.lowerBoundRadius = radius;
	}
		
	
		
			
	/** 
	 * Sets the radius of this Worm to the supplied value.
	 * @param radius
	 * 		The value to which to set the radius of this Worm
	 * 
	 * @post The radius of the worm is equal to the given radius ( and the mass is corrected accordingly)
	 * 		|new.getRadius() == radius
	 * @effect the mass of this worm will be adjusted according to the new radius
	 * 		| setMassGivenRadius(radius)
	 * 			
	 * @throws IllegalRadiusException
	 * 		if the given radius is not a valid value as radius of this Worm
	 * 		| ! isValidRadius(radius)
	 */	
	 @Raw @Model @Override
	protected void setRadius(double radius) throws IllegalRadiusException{
		if (! isValidRadius(radius,this.getLowerBoundRadius())){
			throw new IllegalRadiusException(radius);
			}
		this.radius = radius;
		this.setMassGivenRadius(radius); 
		
	}
	 
	 /**
		 * Checks whether the given value is valid as the lower bound for the radius of this Worm
		 * @param LBRadius
		 * 		the value to check
		 * @return True if and only if LBRadius is in the interval ]0,getRadius()]
		 * 		| result = ((LBRadius>0) && (LBRadius <= getRadius()))
		 */
		@Raw
		private boolean isValidLowerBoundRadius(double LBRadius){
			if (LBRadius > getRadius()){
				return false;
			}
			return (LBRadius > 0);
		}
	 
	 
	/** 
	 * The minimum value for the radius of this Worm.
	 */
	private double lowerBoundRadius = 0.25; 
	
	/** 
	 * This method changes the supplied angle to a value in the interval ]-2*Math*Pi, 2*Math*PI[.
	 * @param angle
	 * 		this is the angle that has to be reduced
	 * 
	 * @pre the given value for angle is in ]-4*Pi, -2*Pi] or [2Pi,4Pi[
	 * 		|! isValidDirection(angle) && (angle >= -4*Pi) && (angle <= 4*Pi) 
	 * 
	 * @effect the angle is in the interval ]-2*Pi, 2*Pi[
	 * 		| isValidDirection(new.getDirection());
	 */
	public static double reduceAngle(double angle){
		assert(! isValidDirection(angle) && (angle >= -4*Math.PI) && (angle <= 4*Math.PI));
		if (angle >= 2*Math.PI) {
			return (angle - 2*Math.PI);
		}
		if (angle <= -2*Math.PI){
			return (angle+2*Math.PI);
		}
		return angle;
	}
	
	
	/** 
	 * Turns this Worm with a given angle.
	 * @param angle
	 * 		the angle to raise the current direction with
	 * 
	 * @pre the given value for angle must be valid 
	 * 		|isValidDirection(angle)
	 * @pre there must be enough action points left over to turn the given angle
	 * 		|this.getCurrentAP() >= costOfTurn(angle)
	 * 
	 * @effect The direction will be set to the sum of the old direction and the given angle, if that sum is in the interval ]-2*Pi,2*Pi[
	 * 		| if isValidDirection(getDirection()+angle)
	 * 		|	then setDirection(getDirection() +angle)
	 * @effect The direction will be set to the sum of the old direction and the given angle minus 2*Pi, if that sum is larger than or equal to 2*Pi
	 * 		| if (getDirection()+angle >= 2*Math.PI)
	 * 		|	then setDirection(getDirection() +angle -2*Math.PI)
	 * @effect The direction will be set to the sum of the old direction and the given angle plus 2*Pi, if that sum is smaller than or equal to -2*Pi
	 * 		| if (getDirection()+angle <= -2*Math.PI)
	 * 		|	then new.getDirection( getDirection() +angle +2*Math.PI)
	 * @effect The amount of action points will be decremented with the appropriate action points, calculated by the method costOfTurn
	 * 		| decreaseCurrentAP(costOfTurn(angle))
	 */
	public void turn(double angle){
		assert(isValidDirection(angle));
		assert(getCurrentAP().isGreaterThan(costOfTurn(angle)) || getCurrentAP().isEqualTo(costOfTurn(angle)));
		double oldDirection = this.getDirection();
		
		Points AP = costOfTurn(angle);
		if (isValidDirection(oldDirection+angle) && (enoughAPLeft(AP))){
			this.setDirection(oldDirection+angle);
			this.decreaseCurrentAP(AP);
			
		}
		if ((oldDirection == this.getDirection()) &&(enoughAPLeft(AP))){
			double adjustedAngle = reduceAngle(this.getDirection() + angle);
			this.setDirection(adjustedAngle);
			this.decreaseCurrentAP(AP);
			
		}
	
	}
		
	
	
	/** 
	 * a method that controls the given value for step
	 * @param step
	 * 		the value for step that is to be controlled
	 * 
	 * @return true if and only if step is bigger than 0
	 * 		| result == (step >0)
	 */
	public static boolean isValidStep(int step){
		return step > 0;
	}
	
	/**
	 * checks whether the difference between the angle of the terrain 
	 * and the direction is not too big so the worm can't move. 
	 * @param angle
	 * 		the slope of the terrain
	 * @throws IllegalArgumentException
	 * 		| !isValidDirection(angle)
	 * @return True if the angle is in the interval [direction-0.7875, direction + 0.7875]
	 * 		|result == Math.abs(Math.sin(this.getDirection()-angle)) <= Math.sin(0.7875)
	 */
	public boolean canMove(double angle) throws IllegalArgumentException{
		if (!isValidDirection(angle))
			throw new IllegalArgumentException("the given angle is not valid");
		double allowedDifference = Math.sin(0.7875);
		double actualDifference = Math.abs(Math.sin(getDirection() - angle));
		
		return (actualDifference <= allowedDifference);
			
		
	}
	
	/**
	 * calculates the next step the worm wants to make
	 * @param angle
	 * 		the slope of the terrain
	 * @throws IllegalArgumentException
	 * 		| (! this.canMove(angle))
	 * @effect the worm will get a new position if this position is passable, this worm will maximize the distance it travels
	 * 			and minimize the difference between the direction in which it travels and the direction in which it faces 
	 * 			whilst not exceeding a difference of 0.7875 with respect to the direction in which it faces
	 * 		|  -New variable: BiggestDistance = canPass(direction + index * stepsize, getRadius(),getPosition)
	 * 		|  -New variable: direction = getDirection()
	 * 		| 
	 * 		|bestAngle == bestDirection
	 * 		| if for each notAGoodDirection for which Math.abs(notAGoodDirection - direction) < bestDirection :
	 * 		|	canPass(notAGoodDirection,getRadius(),getPosition()) == 0
	 * 		| && for bestDirection:
	 * 		|		canPass(bestDirection, getRadius(),getPosition()) >= 0.1
	 * 		|
	 *    	| then BiggestDistance == canPass(bestDirection, getRadius(),getPosition())
	 * 		|
	 * 		| In conclusion: this.setPosition(this.calculatePosition(BiggestDistance, bestAngle))
	 * @effect the AP is lowered with the amount of AP needed for the step
	 * 		|this.decreaseCurrentAP(costOfStep(bestAngle))
	 * @effect if the worm overlaps with food the food will be eaten
	 * 		| if this.overlapsFood(this.getPosition())
	 * 		|	 then eat(getOverlappingFood(this.getPosition));
	 */
	public void step(double angle) throws IllegalArgumentException{
		if (! this.canMove(angle))
			throw new IllegalArgumentException("can not move");
				
		double direction = this.getDirection();
		
		double stepsize = 0.0175;
		int index = 0;
		double BiggestDistance = 0;
		double bestAngle = direction;
		boolean found = false;
		 
		while ((direction-index*stepsize > direction - 0.7875) && (! found)){
			
			double distance1 = this.canPass(direction+index*stepsize, this.getRadius(), this.getPosition());
					
			if(distance1 > BiggestDistance){
					BiggestDistance = distance1;
					bestAngle = direction+index*stepsize;
					found = true;
				}
			
			double distance2 = this.canPass(direction-index*stepsize, this.getRadius(), this.getPosition());
			
			
			if((! found) && (distance2 > BiggestDistance)){
					BiggestDistance = distance1;
					bestAngle = direction-index*stepsize;
					found = true;
				}
			
			index += 1;
		} 
		Points APcost = costOfStep(bestAngle);
		if (this.enoughAPLeft(APcost) && BiggestDistance != 0){
			Position newPosition = this.calculatePosition(BiggestDistance, bestAngle);
			this.setPosition(newPosition);	
			this.decreaseCurrentAP(APcost);
		}
		
		this.setOverlapsFood(this.getPosition());
		
		if (this.getOverlappingFood() != null)
			this.Eat(this.getOverlappingFood());
	}
	
	
	/**
	 * calculates a position at a given distance removed from the worm and under a given direction starting from a given position
	 * @param distance
	 * 		the distance to the next position
	 * @param direction
	 * 		the direction in which the point is situated
	 * @param start
	 * 		The position with which to add the components of the position a distance from start in a given direction
	 * @return the current position of this world incremented with the appropriate values, depends on distance and direction
	 * 		| new Position(start.getX() + distance*cos(direction),start.getY() + distance*sin(direction));
	 * @throws IllegalArgumentException
	 * 		if the given distance is negative
	 * 		| (distance < 0)
	 * @throws IllegalArgumentException
	 * 		if the given direction is invalid
	 * 		|(! isValidDirection(direction))
	 * @throws IllegalArgumentException
	 * 		if the given position is invalid
	 * 		| (! isValidPosition())
	 */
	private Position calculatePosition(double distance,double direction,Position start) throws IllegalArgumentException{
		if (distance < 0)
			throw new IllegalArgumentException("distance smaller than zero");
		if (!isValidDirection(direction))
			throw new IllegalArgumentException("not a valid direction");
		if (!isValidPosition(start))
			throw new IllegalArgumentException("not a valid position");
		double x = start.getX() + distance*Math.cos(direction);
		double y = start.getY() + distance*Math.sin(direction);
		return new Position(x,y);
	}
	
	/**
	 * calculates a position at a given distance removed from the worm and under a given direction
	 * @param distance
	 * 		the distance to the next position
	 * @param direction
	 * 		the direction in which the point is situated
	 * @return the new calculated position starting from this worm's position
	 * 		|result == 
	 * 		|	calculatePosition(distance, direction, getPosition())
	 */
	private Position calculatePosition(double distance,double direction){
		return this.calculatePosition(distance, direction,this.getPosition());
	}
	
	/** 
	 * This method moves the worm by the number of steps given in the direction it is currently facing.
	 * @param steps
	 * 		the number of steps the worm has to take
	 * @effect The worm takes 'steps' (the given value) amount of steps
	 * 		| 	for each step in 1..steps:
	 * 		| 		this.step()
	 * @throws IllegalStepException
	 * 		throws an IllegalStepException when the value for step isn't valid
	 * 		| ! isValidStep(steps)
	 */
	
	/** 
	 * Increases the position of the worm (decreases when the given parameter is negative) with the given position.
	 * @param position
	 * 		the position to add to the current position
	 * 
	 * @post the x and y coordinates are set to the sum of the corresponding given values and the old position
	 * 		|new.getX() == (this.getX() + a)
	 * 		|new.getY() == (this.getY() + b)
	 * 
	 * @throws IllegalPositionException
	 * 		if the new position will be invalid
	 * 		| ! isValidPosition(getPosition().add(Position))
	 */
	private void changePosition(Position position) throws IllegalPositionException,IllegalArgumentException{ 
		if (! isValidPosition(getPosition().add(position))){
			throw new IllegalPositionException(position);
		}
		setPosition(getPosition().add(position));
	}
	
	
	/**
	 * Returns the length of a single step of the worm.
	 */
	private double getStepLength(){
		return this.getRadius();
	}

	
	private void move(int steps) throws IllegalPositionException,IllegalStepException{
		if (!isValidStep(steps)){
			throw new IllegalStepException(steps);
		}
		
		int i = 1;
		Position oldPosition = this.getPosition();
		// let the worm take one step in the direction it is facing
		step(this.getTerrainAngle());
		
		while (( i < steps) && !(oldPosition.equals(this.getPosition()))){
				oldPosition = this.getPosition();
				step(this.getTerrainAngle());
			i = i + 1;
			}
		}
	
	/**
	 * checks whether this worm can fall.
	 * @param position
	 * 		The position for which to check wether this worm can fall.
	 * @return the worm can fall if there is no impassable terrain beneath it.
	 * 		|result ==
	 * 		| for each alpha in [7*Pi/6, 7*Pi/6 + Pi/10,... , 11*Pi/6]:
	 * 		|	getWorld().isImpassable(calculatePosition(getRadius(),alpha, position) == false
	 */
	public boolean canFall(Position position){
		double alpha = 7.0*Math.PI/6;
		double stepSizeAlpha = Math.PI/10;
		while(alpha <= 11.0*Math.PI/6){
			
			 Position positionToCheck = this.calculatePosition(this.getRadius(), alpha,position);
			if (this.getWorld().isImpassable(positionToCheck)){
				return false;
			}
			
			alpha += stepSizeAlpha;
			
		}
		return true;
	}
	/**
	 * check whether this worm can fall
	 * @return true if this worm can fall from its current position
	 * 			|result == this.canFall(this.getPosition())
	 * 
	 */
	public boolean canFall(){
		return this.canFall(this.getPosition());
	}
	
	/**
	 * a method for the worm to fall passive
	 * @param position
	 * 		the position from which the worm falls
	 * @effect the position after the fall will be set
	 * 		| if for each Y in [getY(), getY()- stepSize, ..., finalY + stepSize]:
	 * 		|	((canFall(new Position(getX(), Y)) == true) && (Y>=stepSize))
	 * 		|	&& canFall(newPosition(getX(), finalY)) == false
	 * 		|		then (setPosition(new Position(getX(),finalY))
	 * 		|			&& (decreaseCurrentHP(new Points((int) Math.ceil(3*(getY() - finalY)),SortPoints.HP))))
	 * 		|			&& setOverlapsFood(new Position(getX(),finalY))
	 * 		|			&& if (getOverlappingFood() != null):
	 * 		|				then Eat(getOverlappingFood())
	 * 
	 * 		| if for each Y in [getY(), getY()- stepSize, ..., finalY + stepSize]:
	 * 		|	((canFall(new Position(getX(), Y)) == true) && (Y < stepSize))
	 * 		|		then this.setCurrentHP(new Points(0,SortPoints.HP)) --> this worm's world will immediately terminate this worm
	 * @throws IllegalArgumentException
	 * 		if the given position is invalid
	 * 		|(!isValidPosition(position))
	 *		
	 */
	
	public void fall(Position position) throws IllegalArgumentException{
		if (!isValidPosition(position))
				throw new IllegalArgumentException("not a valid position");
		
		// choose stepsize for a good fall
		double stepSize = 0.1*this.getRadius();
		
		double x = position.getX();
		double y = position.getY();
		
		Position oldPosition = position;
		Position newPosition = position;
		
		while (canFall(newPosition) && (y >= 0)){
			y = y - stepSize;
			if (y > 0)
				newPosition = new Position(x,y);
			
		}
		if (y <0){
			this.setCurrentHP(new Points(0,SortPoints.HP));
		}
		
		
		else{
			y+= stepSize;
			newPosition = new Position(x,y);
			
			this.setOverlapsFood(newPosition);
			if (this.getOverlappingFood() != null)
				this.Eat(this.getOverlappingFood());
			
			this.setPosition(new Position(x,y));
		    this.decreaseCurrentHP(new Points((int) Math.ceil(3*(oldPosition.getY() - this.getPosition().getY())),SortPoints.HP));
		    
		}
	}
	/**
	 * Calculates the maximum distance a worm can travel in the given direction from the given position, with an upperbound on the distance: maxDistance.
	 * @param direction
	 * 		The direction is which to calculate the distance
	 * @param maxDistance
	 * 		The upperbound for the distance
	 * @param position
	 * 		The position from which the worm wants to move in the given direction
	 * @return The maximum distance smaller than/ equal to maxDistance that a worm can move in the given direction from the given position.
	 * 		| result == 
	 * 		| 	getWorld().wormCanPass(getRadius(),direction,maxDistance,position)
	 */
	private double canPass(double direction, double maxDistance, Position position){
		return (getWorld().wormCanPass(getRadius(),maxDistance,direction,position));
	}
	
	/** 
	 * Returns the maximum number of points.
	 */
	@Raw
	public int getMaxPoints(){
		return this.MaxPoints;
	}
	
	/** 
	 * Sets the maximum amount of points, which is equal to the mass of the worm.
	 * 
	 * @post if the mass of the worm has a valid value, the value of MaxPoints will be equal to the mass rounded to the nearest integer.
	 * 		| if isValidNumber(getMass()) 
	 * 		|	 then new.getMaxPoints() == Math.round(new.getMass())
	 * @post if the mass has an invalid value, the MaxPoints will be equal to 0.
	 * 		| if ((! isValidNumber(getMass())
	 * 		|	 then new.getMaxPoints() == 0
	 */
	
	@Raw @Model
	protected void setMaxPoints(){
		if (this.getMass()>Math.pow(2, 31)-1){ 
			this.MaxPoints = Integer.MAX_VALUE;
		}
	  else if(isValidNumber(this.getMass())){
			this.MaxPoints = (int) Math.round(this.getMass());
		}
		else{
			this.MaxPoints = 0;
		}
		
		try{
			if (this.getCurrentAP().getNumeral() > (this.getMaxPoints())){
		
				setCurrentAP(new Points(getMaxPoints(),SortPoints.AP));
			}
			if (this.getCurrentHP().getNumeral() > (this.getMaxPoints())){
				setCurrentHP(new Points(getMaxPoints(),SortPoints.HP));
			}
		}
		catch (NullPointerException exc){
			//System.out.println("No current AP yet");
		}
			
	}
	
	
	/** 
	 * The variable for the maximum amount of  points.
	 */
	private int MaxPoints;
	
	/** 
	 * Returns the current number of action points.
	 */
	@Basic
	public Points getCurrentAP(){
		return this.CurrentAP;
	}
	
	/** 
	 * Checks whether the worm has enough action points to execute a given command that costs AP action points.
	 * @param pointsNeeded
	 * 		the cost of action points for the command the player wants to give
	 * @return whether the current AP of this worm subtracted with the given AP is still a valid amount for this worm's AP
	 * 		|result ==
	 * 		|	( getCurrentAP().enoughPointsLeft(pointsNeeded)
	 * 		or the given points has a non-positive numeral.
	 * 		| || (pointsNeeded.getNumeral() <= 0))
	 */
	public boolean enoughAPLeft(Points pointsNeeded){
		if (pointsNeeded.getNumeral() <= 0){
			return true;
		}
		return this.getCurrentAP().enoughPointsLeft(pointsNeeded);
	}
	
	/** 
	 * Decreases the current AP with the given value (points).
	 * @param points 
	 * 		the amount of action points to subtract from the current AP
	 * 
	 * @effect if points is larger than the current AP, set the new AP to zero
	 * 		|if (this.getCurrentAP().isSmallerThan(points))
	 * 		|	then setCurrentAP(0) 
	 * @effect if points is greater than zero and smaller than current AP then points is subtracted from current AP
	 * 		|if ((points >= 0) && (this.getCurrentAP().isGreaterThan(points) || this.getCurrentAP().isEqualTo(points)))
	 * 		|	then setCurrentAP(currentAP.substract(points));
	 * 
	 */
	public void decreaseCurrentAP(Points points){
		if (points.getNumeral() >= 0){
			Points currentAP = this.getCurrentAP();
			Points result = currentAP.substract(points);
			this.setCurrentAP(result);
		}
	}
	
	
	/** 
	 * This method calculates and returns the cost of turning over a given angle.
	 * @param angle
	 * 		the angle over which there has to be turned
	 * 
	 * @return the amount of AP it costs to turn over the given angle, calculated with the formula:  60 * (given angle) / (2*Pi) 
	 * 		|result == Math.round(Math.ceil(Math.abs(angle / (2*Math.PI))*60));
	 */
	public static Points costOfTurn(double angle){
		return new Points((int) Math.round(Math.ceil(Math.abs(angle / (2*Math.PI))*60)),SortPoints.AP);
	}
	
	
	/** 
	 * Calculates and returns the cost for 1 step for the given angle of the terrain.
	 * @param alpha
	 * 			The angle of the terrain.
	 * 
	 * @return a corrected version of the cost of a step
	 * 			| result ==
	 * 			|	new Points( Math.abs(Math.cos(alpha)) + 4*Math.abs(Math.sin(alpha)), SortPoints.AP) 
	 * 
	 * @throws IllegalTerrainAngleException
	 * 			if the given alpha is not a valid value for the angle of the terrain
	 * 			| ! isValidTerrainAngle(alpha)
	 */
	public static Points costOfStep(double alpha) throws IllegalTerrainAngleException{
		if (! isValidTerrainAngle(alpha)){			
			throw new IllegalTerrainAngleException(alpha);
		}
		
		double cost = Math.ceil(Math.abs(Math.cos(alpha))+ 4*Math.abs(Math.sin(alpha)));
		return new Points((int)cost, SortPoints.AP);
		
	}
	 /**
	  * Sets the current AP to it's maximum value.
	  * @post the new current AP equals the maximum value of current AP
	  * 	| new.getCurrentAP() == getMaxAP()
	  */
	 protected void raiseAPNewTurn(){
		 setCurrentAP(new Points(getMaxPoints(),SortPoints.AP));
	 }
	
	
	/** 
	 * Sets the current number of action points of this Worm.
	 * @param points
	 * 		the current action points
	 * 
	 * @post If the given value AP is less than or equal to the maximum number of AP of this Worm and larger than or equal to zero, than the currentAP will be set to the given AP
	 * 		|if ((points.isSmallerThan(maxAP) || points.isEqualTo(maxAP) && (points.getPoints() >0)))
	 * 		|	then this.CurrentAP = points;
	 * @post If the given value of AP is larger than the maximum number of AP of this Worm, set currentAP to maximum number of AP
	 *		|if (points.isGreaterThan(maxAP))
	 *		|	then this.CurrentAP = this.getMaxAP();
	 * @post If the given value of AP is negative, set the current amount of AP to 0
	 * 		|if (points.getPoints() <=0)
	 * 		|	then this.CurrentAP = new Points(0,SortPoints.AP);	
	 */	
	 @Raw @Model
	protected void setCurrentAP(Points points){ 
		 Points maxAP = new Points(this.getMaxPoints(),SortPoints.AP); 
		 
		 
		if ((points.isSmallerThan(maxAP) || points.isEqualTo(maxAP) && (points.getNumeral() >0))){
			this.CurrentAP = points;
			}
		if (points.isGreaterThan(maxAP)){
			this.CurrentAP = new Points(this.getMaxPoints(),SortPoints.AP);
		}
		if (points.getNumeral() <=0) {
			this.CurrentAP = new Points(0,SortPoints.AP);
			
		}
	}	
	
	
	 
	 
	/** 
	 * The current amount of action points this Worm possesses.
	 * Is set to 0 by default, the worm has to change its action points in the beginning of the game
	 */
	private Points CurrentAP = new Points(0,SortPoints.AP);
	
	
	
	/** 
	 * Decreases the current HP with the given value (points).
	 * @param amount
	 * 		the amount of hit points to subtract from the current HP
	 * 
	 * @effect if points is larger than the current HP, set the new HP to zero
	 * 		|if (this.getCurrentHP().isSmallerThan(points))
	 * 		|	then setCurrentHP(new Points(0,SortPoints HP)) 
	 * @effect if points is greater than zero and smaller than current HP then points is subtracted from current HP
	 * 		|if ((points.getNumeral() >= 0) && ( ! this.getCurrentHP().isSmallerThan(points)))
	 * 		|	setCurrentHP(this.getCurrentHP().substract(amount));
	 * 
	 */
	public void decreaseCurrentHP(Points amount) throws IllegalArgumentException{
		if (amount.getNumeral() >= 0){
			this.setCurrentHP(this.getCurrentHP().substract(amount));
		}
	}
	
	/**
	 * returns the hit Points of the worm
	 */
	public  Points getCurrentHP(){
		return this.CurrentHP;
	}
	
	/**
	 * increases the current HP of the worm
	 * @param amount
	 * 		the amount of points that has to be added to this worm's current HP
	 * @effect This worm's new current HP is set to the sum of this worms old current AP incremented with the given amount
	 * 			if that sum does not exceed the maximum allowed points and amount is non-negative
	 * 		| this.setCurrentHP(this.getCurrentHP().add(amount));
	 *			if that sum does exceed the maximum allowed points and amount is non-negative
	 *		| this.setCurrentHP(new Points(getMaxPoints(),SortPoints.HP))
	 * 
	 */
	public void increaseCurrentHP(Points amount)throws IllegalArgumentException{
		if ((amount.getNumeral() >= 0) &&(getCurrentHP().getNumeral() + amount.getNumeral() > this.getMaxPoints())){
			this.setCurrentHP(new Points(getMaxPoints(),SortPoints.HP));
		}
		else if (amount.getNumeral() >=0 ){
			this.setCurrentHP(this.getCurrentHP().add(amount));
		}
	}
	
	/**
	 * Increases the current amount of HP with 10.
	 * @post The new current HP is equal to this current HP incremented by 10		
	 * 		|new.getCurrentHP().getPoints == this.getCurrentHP().getPoints()+10 
	 */
	protected void raiseHPNewTurn(){
		increaseCurrentHP(Points.HPNEWTURN);
		
	}
	
	/**
	 * set the currentHP to a given value
	 * @param points
	 * 		the new value for the currentHP
	 * @post the new current HP of this worm equals the given points
	 * 		| new.getCurrentHP().isEqualTo(points) == true
	 * @post if the current value of HP is smaller than the given value of points then the new current HP is zero
	 * 		|new.CurrentHP = new Points(0,SortPoints.HP)
	 * @effect if this worm is not the current worm in this worm's world and its new HP is zero then this worm is termintated and
	 * 			the teamless worms of this worm's world are set.
	 * 		| if ((this.getCurrentHP().getNumeral() == 0) &&  (this.getWorld().getCurrentWorm() != this))
	 * 		| 	then this.Terminate() && this.getWorld().setTeamlessWorms()
	 * @effect if this worm is the current worm in this worm's world and its new HP is zero then this worm's AP is set to zero.
	 * 		| if ((this.getCurrentHP().getNumeral() == 0) &&  (this.getWorld().getCurrentWorm() == this))
	 * 		| 	then this.setCurrentAP(new Points(0,SortPoints.AP))
	 */
	private void setCurrentHP(Points points){
		if (this.getCurrentHP().isSmallerThan(points))
			this.CurrentHP = new Points(0,SortPoints.HP);
		this.CurrentHP = points;
		if (this.getCurrentHP().getNumeral() == 0){
			if (this.getWorld().getCurrentWorm() != this){
				World formerWorld = this.getWorld();
				this.Terminate();
				formerWorld.setTeamlessWorms();
			}
			else {
				this.setCurrentAP(new Points(0,SortPoints.AP));
			}
		}
			
			
	}
	
	
	
	
	
	/**
	 * the number of Hit Points a worm has
	 */
	private Points CurrentHP = new Points(0,SortPoints.HP);
	
	
	
	
	/** 
	 * Return the name of this Worm.
	 */
	@Basic
	public String getName(){
		return this.name;
	}
	
	/** 
	 *  Checks whether the given string is a valid name.
	 * @param name
	 * 		the string that needs to be checked
	 * 
	 * @return
	 * 		false if the length of name is smaller than 2 
	 * 		| result == (name.lenght() >=2)
	 *
	 * 		false if not each character is either an upper/lowercase letter, a single/double quotes or a whitespace 
	 * 		|for each character in name:
	 * 		| ((!Character.isLetter(character)) || (!Character.isWhitespace(character)) || (character!=39) || (character!=34)  || Character.isDigit(c) )
	 * 
	 * 		false if the first letter of the name is not an uppercase letter
	 * 		| Character.isUpperCase(name.charAt(0))
	 * 			
	 */
	@Raw
	public static boolean isValidName(String name){
		boolean result = true;
		int i = 0;
		if (name.length() <2){
			result = false;
		}
		
		char firstLetter =  name.charAt(0);
		if( ! Character.isUpperCase(firstLetter)){
			result =false;
		}

		while (( i < name.length()) && result){
			char c = name.charAt(i);
			if ((Character.isLetter(c)) || (Character.isWhitespace(c)) || (c==39) || (c==34) || Character.isDigit(c) ){
				result = true;
				i = i + 1;
			}
			else{
				result = false;
			}
		}
		return result;
	}
	
	
	/** 
	 * Sets the name of this Worm to the given string.
	 * @param name
	 * 			the string to which to set the name of the worm
	 * @post the new name of this worm equals the given name
	 * 		| new.getName() == name
	 * @throws IllegalNameException
	 * 			if the given string (name) is not valid
	 * 			| ! isValidName(name)
	 */	
	 @Raw
	public void setName(String name) throws IllegalNameException {
		if (! isValidName(name)){
			throw new IllegalNameException(name);
			}
		this.name = name;
	}		
	
	/** 
	 * The name of this Worm.
	 */
	private String name;
	
	
	
	
	
		
	/** 
	 * Return the angle of the terrain for this Worm.
	 * @return the calculated angle of the terrain
	 * 				| result = this.getWorld().calculateSlope(this.getPosition(), this.getDirection(),getRadius())
	 */
	@Basic
	public double getTerrainAngle(){
			
		return this.getWorld().calculateSlope(this.getPosition(), this.getDirection(),this.getRadius());
	}
		
	/** 
	 * Checks whether the given angle is a valid terrain angle for a Worm to walk on.
	 * @param angle
	 * 			The angle to check.
	 * @return true, no restrictions yet
	 */
	private static boolean isValidTerrainAngle(double angle){
		return true;
	}	
	
	/** 
	 * Sets the angle of the terrain to the given value.
	 * @param alpha
	 * 		The value to which to set the angle of the terrain
	 * 
	 * @post the new value of the terrain angle equals the given value
	 * 		| new.getTerrainAngle() = alpha
	 * 
	 * @throws IllegalTerrainAngleException
	 * 		if the value of alpha is not valid for the angle of the terrain
	 * 		|! isValidTerrainAngle(alpha)
	 */	
	private void setTerrainAngle(double alpha) throws IllegalTerrainAngleException{
		if (! isValidTerrainAngle(alpha)){
			throw new IllegalTerrainAngleException(alpha);
		}
		this.terrainAngle = alpha;		
	}
	
	/** 
	 * The variable which represents the angle of the terrain.
	 */
	private double terrainAngle = 0;	
	
	
	/** 
	 * Calculates and returns the position of a jumping Worm at the given time.
	 * @param t
	 * 		The time at which we want to know the location of this Worm during the jump.
	 * 
	 * @return The position where this Worm lands after a potential jump.
	 * 			This Worm is not facing down
	 * 		| if ( ! isFacingDown())
	 * 		| 	result == this.jumpStep(this.jumpTime())
	 * 
	 * 			This Worm is facing down, so this Worm doesn't jump
	 * 		| if ( isFacingDown())
	 * 		| 	result == this.jumpStep(0)
	 * 			if the given jumpTime is 0 the current position of the worm is returned
	 * 		|if (t == 0)
	 * 		| then result == this.getPosition();
	 * @effect if the worm overlaps with food, the worm will eat it
	 * 		| if this.overlapsFood(newPosition)
	 * 		|	 then this.eat(this.getOverlappingWorm)
	 * @throws IllegalArgumentException
	 * 		the given value for t is not in the interval [0, jumpTime()].
	 * 		| ! isValidJumpTime(t)
	 * @throws IllegalJumpTimeExcecption
	 * 		if for the given t the position is no longer passable for the movingObject
	 * 		|	-New variable: newPosition = new Position(getX() + calculateVelocity()*cos(alpha)*t, getY() + calculateVelocity*sin(alpha)*t);
	 * 		| ! this.getWorld().isPassableForCircle(this.getRadius(), newPosition)
	 */	
	@Override
	public Position jumpStep(double t,double direction) throws IllegalJumpTimeException,IllegalArgumentException{
		
		if (! isValidJumpTime(t)){
			throw new IllegalArgumentException("negative time");
		}
		if (t == 0)
			return this.getPosition();
		
		else{
			
		
		if (isFacingDown(direction)){
			return this.getPosition();
		}
		double alpha = getDirection();
		double v0 = calculateVelocity();
		double v0x = v0*Math.cos(alpha);
		double v0y = v0*Math.sin(alpha);
		double x = getX() + v0x*t;
		double y = getY() + v0y*t - g/2*t*t;
		Position newPosition = new Position(x,y);
		
		return newPosition; 
		}
		
	}	
	
	/** 
	 * Sets the position to the position where this Worm lands. 
	 * @post The worm uses up all of it's AP.
	 * 		| new.getCurrentAP() == 0
	 * @effect if the AP of the worm is bigger than zero and the calculated Jumptime for this worm is bigger than zero 
	 * 			then the new position of this Worm equals the position after a jump from the current position in the direction this worm is facing
	 * 		|if ((getCurrentAP().getNumeral() !=0 ))
	 * 		|	if (time > 0)
	 * 		| 		then setPosition(jumpStep(jumpTime()))
	 * @effect if the AP of the worm is bigger than zero and the calculated Jumptime for this worm is bigger than zero
	 *			then overlapping food in searched 
	 *		|if ((getCurrentAP().getNumeral() !=0 ))
	 * 		|	if (time > 0)
	 * 		|		this.setOverlapsFood(position)
	 * @effect if the AP of the worm is bigger than zero and the calculated Jumptime for this worm is bigger than zero
	 * 			and the overlapping food is not null then the overlapping food is eaten
	 * 		|if ((getCurrentAP().getNumeral() !=0 ))
	 * 		|	if (time > 0)
	 * 		|		if (this.getOverlappingFood() != null)
	 * 		|			this.Eat(this.getOverlappingFood())
	 * @effect if the AP of the worm is bigger than zero and the calculated Jumptime for this worm is bigger than zero
	 * 			and this worm can fall than this worm will fall
	 * 		|if ((getCurrentAP().getNumeral() !=0 ))
	 * 		|	if (time > 0)
	 * 		|		if (this.canFall())
	 * 		|			then this.fall(this.getPosition());
	 * @effect if the AP of the worm is bigger than zero and the calculated Jumptime for this worm is bigger than zero
	 * 			then the current AP will be set to zero
	 * 		|if ((getCurrentAP().getNumeral() !=0 ))
	 * 		|	if (time > 0)
	 * 		|		this.setCurrentAP(new Points(0,SortPoints.AP)) 			
	 */
	public void jump(double timeStep) throws IllegalJumpTimeException, IllegalPositionException{	
		
		if (getCurrentAP().getNumeral() !=0 ){
			
			double time = jumpTime(timeStep);
			
			if (time > 0){
			
				Position position = jumpStep(time,this.getDirection());
				this.setPosition(position);
				
				this.setOverlapsFood(position);

				
				if (this.getOverlappingFood() != null){
					this.Eat(this.getOverlappingFood());
				}
				
				if (this.canFall())
					this.fall(this.getPosition());
					
				this.setCurrentAP(new Points(0,SortPoints.AP));	
			}
			}
	}

	
	/**
	 * 	Return the minimum stepdistance for a worm.
	 */
	private static double getMinStepDistance(){
		return minStepDistance;
	}
		
	/**
	 * The minimum stepsize a worm can take.	
	 */
	private static final double minStepDistance = 0.1;
	

// Team
	
	/**
	 * returns the team of the worm
	 */
	public Team getTeam(){
		return this.team;
	}
	/**
	 * returns the name of the team this worm is in 
	 * @return	if the team of the worm is null then return "no team"
	 * 			|if (this.getTeam()== null)
	 * 			|	then result = "no team"
	 * 			|if (this.getTeam()=! null)
	 * 			|	then result = this.getTeam.getName()
	 * 
	 */
	public String getTeamName(){
		if (this.getTeam()== null)
			return "no team";
		return this.getTeam().getName();
	}

	
	/**
	 * Removes the the worm from the team it was in.
	 * @effect the team of the worm is set to null
	 * 		|this.setTeam(null);
	 * @effect the worm is removed from the team it was in 
	 * 		|formerTeam.removeWorm(this);
	 */
	protected void removeTeam(){
		if (team != null){
		Team formerTeam = this.getTeam();
		this.setTeam(null);
		formerTeam.removeWorm(this);
		}
	}
	
	/**
	 * set a team for the worm
	 * @param team
	 * 		the team to be set
	 * @post the new team of this worm equals the given team
	 * 		| new.getTeam() == team
	 * @throws IllegalArgumentException
	 * 		if the given team is invalid or if the worm already has a team
	 * 		| ((! isValidTeam(team)) || (hasTeam()))
	 */
	protected void setTeam(Team team) throws IllegalArgumentException{
		if (! isValidTeam(team)) 
			throw new IllegalArgumentException("invalid team");
		if (this.hasTeam()){
			throw new IllegalArgumentException("worm already has team");
		}
		this.team = team;
	}
	
	/**
	 * Checks whether this worm has a team.
	 * @return true if and only if this worm's team is not null
	 * 		| result ==
	 * 		|	(this.team != null)
	 */
	private boolean hasTeam(){
		return (this.getTeam() != null);
	}
	
	
	/**
	 * checks if a team is valid
	 * @param team
	 * 		the team to be checked
	 * @return true if and only if the given team is not terminated
	 * 		|result ==  (!team.isTerminated())
	 */
	private static boolean isValidTeam(Team team){
		return   (!team.isTerminated());
	}
	

	
	
	/**
	 * the team the worm is in
	 */
	private Team team;
	
	
	
// terminate
	
	/**
	 * Terminate the worm.
	 * @post This worm is terminated.
	 * 		| new.isTerminated() == true
	 * @effect this worm will no longer reference a world as it's world
	 * 			and this worm's world will no longer reference this worm as one of it's worms
	 * 		| super.Terminate()
	 * @effect this worm will no longer reference a team as it's team
	 * 			and this worm's team will no longer reference this worm as one of it's worms
	 * 		|this.removeTeam()
	 * @effect this worm will no longer reference it's weapons as one of it's weapons
	 * 			and all the worm's weapons will no longer reference this worm as it's worm
	 * 		| for each weapon in this.getAllWeapons():
	 * 		|	weapon.removeWorm() 
	 */
	public void terminate(){
		if (! isTerminated()){
			
		this.removeTeam();
		
		for (Weapon weapon : this.getAllWeapons())
			this.removeWeapon(weapon);
		
		super.Terminate();
		}
	}
	
	
// weapons
	
	/**
	 * return all the weapons in this worm
	 */
	public List<Weapon> getAllWeapons() {
		return new ArrayList<Weapon>(weapons);
	}
	
	
	/**
	 * Returns the weapon that is situated  on the given index.
	 * @param index
	 * 		the index at which the weapon is to be found in the list
	 * @return returns the element at the index given 
	 *		|result ==
	 *		|	 weapons.get(index)
	 * @throws IllegalArgumentException
	 * 		if the given index is invalid
	 * 		| ((index < 0) || (index >= getNbWeapons()))
	 */
	public Weapon getWeaponAt(int index) throws IllegalArgumentException{
		if ((index < 0) || (index >= getNbWeapons())) {
			throw new IllegalArgumentException("not a valid index");
		}
		return weapons.get(index);
	}
	/**
	 * select the next weapon
	 * @effect the new active weapon is the next weapon in the list of weapons if the next index is not out of bounds
	 * 		| if (! (this.getActiveWeaponIndex() == this.getNbWeapons()-1))
	 * 		|	then setActiveWeaponIndex(this.getActiveWeaponIndex()+1)
	 * 			if the next index is out of bounds
	 * 		| if (this.getActiveWeaponIndex() == this.getNbWeapons()-1)
	 * 		|	then this.setActiveWeaponIndex(0)
	 */
	public void selectNextWeapon(){
		if (this.getActiveWeaponIndex() == this.getNbWeapons()-1)
			this.setActiveWeaponIndex(0);
		else
			this.setActiveWeaponIndex(this.getActiveWeaponIndex()+1);
	}
	
	/**
	 * Returns the active weapon of this worm.
	 */
	public Weapon getActiveWeapon() throws IllegalArgumentException{
		return this.getWeaponAt(this.getActiveWeaponIndex());
	}
	
	/**
	 * A method to call the number of weapons in the list of weapons.
	 * @return returns the length of the list weapons
	 * 		| result == weapons.size()
	 */
	private int getNbWeapons(){
		return weapons.size();
	}
	
	/**
	 * Checks if weapons contains a valid weapon at the index given.
	 * @param index
	 * 		the index at which the weapon is checked
	 * @return true if and only if the weapon on the given index is not null and not terminated
	 * 		| result = (getWeaponAt(index) != null) && (!getWeaponAt(index).isTerminated())
	 */
	private boolean canHaveAsWeaponAt(int index) throws IllegalArgumentException{
		return  (getWeaponAt(index) != null) && (! getWeaponAt(index).isTerminated());
	}
	/**
	 * Checks whether worm has proper weapons attached to it.
	 * @return true if and only if all the weapons are valid weapons
	 * 		|result ==
	 * 		| (getNbWeapons <= 2)
	 * 		| && for each i in 1..getNbWeapons
	 * 		| 		(!canHaveAsWeaponAt(i)) && ((!(getWeaponAt(i).getWorm() == this))
	 */
	private boolean hasProperWeapons(){
		if (getNbWeapons() > 2)
			return false;
		
		for (int i = 0; i < getNbWeapons();i++){
			if (!canHaveAsWeaponAt(i))
				return false;
		}
		return true;
				
	}
	/**
	 * Add a new weapon to the list weapons.
	 * @param index
	 * 		the index at which the weapon is to be added
	 * @param weapon
	 * 		The weapon to add to the list of this world weapons.
	 * @post the new list of weapons will now contain the given weapon at the given index
	 * 		| new.getWeaponAt(index) == weapon 
	 * 		and all the weapons before the given index will stay in their place
	 * 		|&& for each i < index (and i>=0):
	 * 		|		this.getWeaponAt(i) == new.getWeaponAt(i)
	 * 		and all the weapons starting from the given index untill the end of the list will be moved one index up
	 * 		|&& for each i >= index (and i < new.getAllTeams().size())
	 * 		|		this.getWeaponAt(i) == new.getWeaponAt(i+1)
	 * @throws IllegalArgumentException		
	 * 		the given weapon is not a valid weapon
	 * 		|(!isValidWeapon(weapon))
	 * @throws IllegalArgumentException		
	 * 		the given weapon is already one of the weapons in this world
	 * 		|(hasAsWeapon(weapon))
	 */
	private void addWeaponAt(int index,Weapon weapon) throws IllegalArgumentException, IndexOutOfBoundsException{
		if ((! isValidWeapon(weapon)) || (hasAsWeapon(weapon)))
			throw new IllegalArgumentException("invalid Weapon");
		weapons.add(index, weapon);
	}
	
	
	/**
	 * Checks whether this worm has the given weapon as one of it's weapons.
	 * @param weapon
	 * 		the weapon to check
	 * @return true if and only if the weapon is already in this world and the weapon is not null
	 * 		| result ==
	 * 		|	((weapon!=null) 
	 * 		|	&& (for one obj in getAllWeapons():
	 * 		|		obj == weapon))
	 */
	private boolean hasAsWeapon(Weapon weapon) {
		if (weapon == null){
			return false;
		}
		int index = 0;
		boolean found = false;
		while (index < getNbWeapons()){
			if (getWeaponAt(index) == weapon)
				return true;
			
			index = index + 1;
		}
		return found;
	}

	/**
	 * Removes the weapon with the given index in the list from the list of weapons.
	 * @param index
	 * 		the index at which the weapon is to be removed
	 * @post the new list of weapons will no longer contain the weapon that was positioned at the given index
	 * 		| for each object in new.getAllWeapons():
	 * 		|	 object != this.getWeaponAt(index)
	 * 		and all the weapons before the given index will stay in their place
	 * 		|&& for each i < index (and i>=0):
	 * 		|		this.getWeaponAt(i) == new.getWeaponAt(i)
	 * 		and all the weapons starting from the given index untill the end of the list will be moved one index down
	 * 		|&& for each i > index (and i < new.getAllWeapons().size())
	 * 		|		this.getweaponAt(i) == new.getweaponAt(i-1) 
	 */
	private void removeWeaponAt(int index){
		weapons.remove(index);
	}
	
	/**
	 * Remove a given weapon from the list of weapons.
	 * @param weapon
	 * 		the weapon to be removed
	 * @post the given weapon will be removed from the list of weapons of this worm
	 * 		| new.getNbWeapons() == this.getNbWeapons() - 1
	 * 		| && for each index in new.getNbWeapons():
	 * 		|		new.getWeaponAt(index) != weapon
	 * 
	 * @throws IllegalArgumentException
	 * 		the given weapon is not valid or this worm doesn't have the given weapon as one of it's weapons
	 * 		| ((! isValidWeapon(weapon)) || (! hasAsWeapon(weapon)))
	 */
	private void removeWeapon(Weapon weapon) throws IllegalArgumentException{
		if ((! isValidWeapon(weapon)) || (! hasAsWeapon(weapon)))
			throw new IllegalArgumentException("invalid Weapon");
		weapons.remove(weapon);
		
	}
	
	/**
	 * adds a weapon to the list of weapons
	 * @param weapon
	 * 		the weapon to be added
	 * @throws IllegalArgumentException
	 * 		if the weapon is not valid or this worm already has the given weapon as one of it's weapons
	 * 		| (((!isValidWeapon(weapon)) || (hasAsWeapon(weapon))))
	 */
	private void addWeapon(Weapon weapon) throws IllegalArgumentException{
		if (((!isValidWeapon(weapon)) || (hasAsWeapon(weapon))))
			throw new IllegalArgumentException("invalid Weapon");
		weapons.add(weapon);
	}	
	
	/**
	 * checks if a weapon is valid	
	 * @param weapon
	 * 		the weapon to be checked
	 * @return
	 * 		|result = (!weapon.isTerminated()) && (weapon != null)
	 */
	private static boolean isValidWeapon(Weapon weapon){
		return  (weapon != null) && (! weapon.isTerminated());
	}
	
	
	/**
	 * a list containing the worms projectiles
	 */
	private List<Weapon> weapons = new ArrayList<Weapon>();
	
	
	/**
	 * Returns the index of the currently active weapon of this worm.
	 */
	public int getActiveWeaponIndex(){
		return this.activeWeaponIndex;
	}
	
	/**
	 * Sets the index of the active weapon to the given index.
	 * @param i
	 * 		The index to which to set the index of the active weapon.
	 */
	private void setActiveWeaponIndex(int i){
		this.activeWeaponIndex = i;
	}
	
	
	/**
	 * Variable registering the index of the currently active weapon.
	 */
	private int activeWeaponIndex= 0;
	

	
	
	/**
	 * Calculates the force with which the worm jumps.
	 */
	@Override
	public double calculateForce() {
		return (5*this.getCurrentAP().getNumeral() + this.getMass()*g);
		
	}
	
	
	
	
	//shoot
	/**
	 * This worm uses it's currently active weapon to shoot a projectile.
	 * @param yield
	 * 		the propulsion yield of the projectile
	 * @effect a new projectile is added to the game world
	 * 			|new Projectile(this.calculatePosition(this.getRadius()+ projectileRadius,this.getDirection())
	 * 				,this.getWorld(),yield,this.getDirection(),projectileRadius, activeWeapon)
	 * @throws IllegalAPException
	 * 			if there is not enough AP for firing the weapon a Exception will be thrown
	 * 			| (!enoughAPleft(this.getActiveWeapon().getAPCost())
	 * @throws IllegalPositionException
	 * 			if the worm is located on impassable terrain it must not shoot
	 * 			|!this.getWorld().isPassableForCircle(this.getRadius(),this.getPosition())
	 */
	public void shoot(int yield) throws IllegalAPException,IllegalPositionException,IllegalArgumentException{
		Weapon activeWeapon = this.getActiveWeapon();
		
		
		if (!this.enoughAPLeft(activeWeapon.getAPCost()))
			throw new IllegalAPException(activeWeapon.getAPCost().getNumeral());
		
		if (!this.getWorld().isPassableForCircle(this.getRadius(),this.getRadius(),this.getPosition()))
			throw new IllegalPositionException(this.getPosition());
		
		this.decreaseCurrentAP(activeWeapon.getAPCost());
		
		double projectileRadius = Weapon.calculateRadius(activeWeapon.getMass(), Weapon.getQ());
		
		Position projposition = this.calculatePosition(this.getRadius() + projectileRadius,this.getDirection());
		
		new Projectile(projposition,this.getWorld(),yield,this.getDirection(),projectileRadius, activeWeapon);
		
		
	}
	
	/**
	 * This worm eats the overlapping food.
	 * @param food
	 * 		The food that's eaten by the worm
	 * @effect the worm's radius increases with 10%
	 * 		| increaseRadius(getRadius()*0.1)
	 * @effect this worm no longer overlaps with food
	 * 		| setOverlappingFood(null)
	 * @effect the food is eaten, so it'll be terminated
	 * 		| food.Terminate()
	 */
	public void Eat(Food food) throws IllegalArgumentException{
		if (!isValidFood(food))
			throw new IllegalArgumentException("not a valid food");
		
		this.increaseRadiusBy(this.getRadius()*0.1);
		this.setOverlappingFood(null);
		food.Terminate();
	}
	
	
	
	/**
	 * Returns the food with which this worm overlaps.
	 */
	protected Food getOverlappingFood(){
		return this.overlappingFood;
	}
	
	/**
	 * Checks whether the given food is valid food.
	 * @param food
	 * 		The food to check
	 * @return true if and only if the food is effective and not terminated.
	 * 		| ((food != null) && (!food.isTerminated()))
	 */		
	private boolean isValidFood(Food food){
		return ((food != null) && (!food.isTerminated()));
	}
	
	/**
	 * Sets the food with which this worm overlaps at a given position
	 * @param position
	 * 		position of the worm
	 * @effect The overlapping food will be set to the food for which the distance between this worm's position and one of the foods in this world position 
	 * 			is smaller than the sum of the radius of the worm and the radius of the food.
	 * 		|	if for one food in getAllFood():
	 * 		|		food.getPosition().calculateDistance(position) <= food.getRadius()+this.getRadius()
	 * 		| 	then setOverlappingFood(food)
	 * 		if this worm doesn't overlap with food, the overlapping food will be set to null
	 * 		| 	if for each food in getAllFood():
	 * 		|		food.getPosition().calculateDistance(position) > food.getRadius()+this.getRadius()
	 * 		|	then setOverlappingFood(null)
	 * 		
	 */
	private void setOverlapsFood(Position position){
		List<Food> foods = this.getWorld().getAllFood();
		boolean overlaps = false;
		for (Food food: foods){
			
			if (position.calculateDistance(food.getPosition()) <= food.getRadius()+this.getRadius()){
				this.setOverlappingFood(food);
				overlaps = true;
			}
		}
		if (overlaps == false){
			this.setOverlappingFood(null);
		}
		
	}
	
	/**
	 * Sets the food with which this worm overlaps.
	 * @param food
	 * 		the food with which the worm overlaps
	 * @post the new food with which this worm overlaps equals the given food
	 * 		| new.getOverlappingFood() == food
	 */
	private void setOverlappingFood(Food food){
		this.overlappingFood = food;
	}
	
	
	/**
	 * The food with which this worm is overlapping.
	 */
	private Food overlappingFood;


	
	
}
	
		
	
	
		
