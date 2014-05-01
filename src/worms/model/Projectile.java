package worms.model;

import java.util.List;

import be.kuleuven.cs.som.annotate.*;
import worms.exceptions.IllegalJumpTimeException;
import worms.exceptions.IllegalPositionException;
import worms.exceptions.IllegalWorldException;
import worms.model.position.Position;



/**
 * a class of projectiles, a special case of moving objects
 * @invar the weapon referencing this projectile is a valid weapon
 * 		| isValidWeapon(this.getWeapon)
 * @author Cleemput Enrico en Van Buggenhout Niel
 * @version 1.1
 */
public class Projectile extends MovingObjects {
	/**
	 * Initialize this new projectile in a given world, at given position, with given propulsion,
	 * 	 in given direction, with given radius and with which weapon it is fired.
	 * @param position
	 * 		the position of the projectile
	 * @param world
	 * 		the world the projectile is in
	 * @param Propulsion
	 * 		the propulsion with what the projectile is launched
	 * @param Direction
	 * 		the direction of the projectile
	 * @param radius
	 * 		the radius of the projectile
	 * @param weapon
	 * 		the weapon from which the projectile is fired
	 * @post The yield of this new projectile equals the given propulsion
	 * 		| new.getYield() == propulsion
	 * @effect The weapon with which this new projectile will be fired equals the given weapon
	 * 		| setWeapon(weapon)
	 * @effect This new projectile is initialized as a moving object with given radius, given direction,
	 * 			in given world and at the given position.
	 * 		| super(radius, position, world, weapon.getMass(),Direction)
	 * 			
	 */
	public Projectile(Position position,World world,int Propulsion,double Direction,double radius,Weapon weapon) throws IllegalArgumentException, 
			IllegalPositionException,IllegalWorldException{
		super(radius, position, world, weapon.getMass(),Direction);
		if(!isValidYield(Propulsion))
			throw new IllegalArgumentException("not a valid yield");
		
		this.setWeapon(weapon);
		
		this.yield = Propulsion ;
	}
	
	
	/**
	 * returns the weapon associated with the projectile
	 */
	@Basic
	public Weapon getWeapon(){
		return this.weapon;
	}
	
	
	/**
	 * checks whether a weapon is a valid for this projectile.
	 * @param weapon
	 * 			the weapon to be checked
	 * @return true if and only if the weapon is not null and the weapon is not terminated
	 * 		|result ==
	 * 		|	 (weapon != null &&  (!weapon.isTerminated()))
	 */
	@Raw
	public static boolean isValidWeapon(Weapon weapon){
		return ((weapon != null) &&  (! weapon.isTerminated()));
	}
	
	/**
	 * Sets the weapon from which the this projectile will be fired.
	 * @param weapon
	 * 		the weapon to be set
	 * @post this new projectile's weapon will equal the given weapon
	 * 		| new.getWeapon() == weapon
	 * @throws IllegalArgumentException
	 * 		if the given weapon is not valid.
	 * 		| (! isValidWeapon(weapon))
	 */
	@Raw @Model
	private void setWeapon(Weapon weapon) throws IllegalArgumentException{
		if (!isValidWeapon(weapon))
			throw new IllegalArgumentException("not a valid weapon");
		this.weapon = weapon;
	}
	
	/**
	 * The weapon used to fire this projectile.
	 */
	private Weapon weapon;
	
	/**
	 * Returns the yield of the projectile.
	 */
	@Basic
	public int getYield(){
		return this.yield;
	}
	
	/**
	 * Checks whether the given yield is valid.
	 * @param yield
	 * 		the yield to be checked
	 * @return true if and only if the yield is in the interval [0..100]
	 * 		| result == ((yield >= 0) && (yield <= 100))
	 */
	public static boolean isValidYield(int yield){
		return ((yield >= 0) && (yield <= 100));
	}
	
	
	/**
	 * The propulsion yield of this projectile.
	 */
	private final int yield; 
	
	
	
	
	/**
	 * Terminate this projectile.
	 * @post No weapon is attached any longer to this projectile.
	 * 		|new.getWeapon() == null
	 * @effect this projectile will be terminated 
	 * 		|super.Terminate()
	 */
	@Override
	public void Terminate(){
		this.weapon = null;
		super.Terminate();
		
	}
	
	/** 
	 * Returns the time it would take for a potential jump from the current position in the direction this Worm is facing.
	 * @effect
	 * 		
	 * 
	 * @return returns the time needed for a moving object to make a jump 
	 * 		|		- New method: positionAtTime(T) = new Position(getX() + calculateVelocity() * cos(getDirection)*T, getY() + calculateVelocity()*sin(getDirection())*T -g/2*T²)
	 * 		|	if for each T in [0,time-stepSize]:
	 * 		|		(getWorld().isPassableForCircle(getRadius(),getRadius(), positionAtTime(T)) == true
	 * 		|	&& getWorld().isPassableForCircle(getRadius(),getRadius(), positionAtTime(time)) == false)
	 * 		|	&& 
	 * 		|	if for one t in [0,time-stepSize]: 
	 * 		|		(this.getOverlappingWorm() != null) 
	 * 		|			then result == t
	 * @return returns the time needed for a moving object to make a jump 
	 * 		|		- New method: positionAtTime(T) = new Position(getX() + calculateVelocity() * cos(getDirection)*T, getY() + calculateVelocity()*sin(getDirection())*T -g/2*T²)
	 * 		|	if for each T in [0,time-stepSize]:
	 * 		|		(getWorld().isPassableForCircle(getRadius(),getRadius(), positionAtTime(T)) == true
	 * 		|	&& getWorld().isPassableForCircle(getRadius(),getRadius(), positionAtTime(time)) == false)
	 * 		|	&& 
	 * 		|	if for each t in [0,time-stepSize]: 
	 * 		|		(this.getOverlappingWorm() == null) 
	 * 		|			then result == time  			
	 * 		|
	 * @effect if this projectile overlaps with a worm then the worm is set as overlappingworm
	 * 		|	this.setOverlapsWorm(newPosition)
	 */	
	@Override
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
			
			this.setOverlapsWorm(newPosition);
			
			if (this.getOverlappingWorm() != null)
					found = true;
					t = t + stepSize;
			}
		return (t - stepSize);
	}
	
	
	
	/** 
	 * Calculates and returns the position of a jumping projectile at the given time.
	 * @param t
	 * 			The time at which we want to know the location of this projectile during the jump.
	 * @param direction
	 * 			the direction in which the projectile is launched 
	 * @pre 	the direction given must be valid
	 * 			|isValidDirection(direction)
	 * @return The position where this projectile lands after a potential jump.
	 * 				This projectile is not facing down
	 * 			| if ( ! isFacingDown())
	 * 			| 	result == this.jumpStep(this.jumpTime())
	 * 
	 * 				This projectile is facing down, so this projectile doesn't jump
	 * 			| if ( isFacingDown(direction))
	 * 			| 	result == this.jumpStep(0)
	 * @effect if the projectile overlaps with a worm the worm will be set as the overlappingWorm
	 * 			| this.setOverlapsWorm(newPosition);	 
	 * @throws IllegalArgumentException
	 * 			the given value for t is not in the interval [0, jumpTime()].
	 * 			| ! isValidJumpTime(t)
	 * @throws IllegalJumpTimeExcecption
	 * 			if for the given t the position is no longer passable for the movingObject
	 * 			|	-New variable: newPosition = new Position(getX() + calculateVelocity()*cos(alpha)*t, getY() + calculateVelocity*sin(alpha)*t);
	 * 			| ! this.getWorld().isPassableForCircle_BruteForce(this.getRadius(), newPosition)
	 * @throws IllegalJumptTimeException
	 * 			if the projectile overlaps with a worm for the given t
	 * 			|	-New variable: newPosition = new Position(getX() + calculateVelocity()*cos(alpha)*t, getY() + calculateVelocity*sin(alpha)*t);
	 * 			| 	overlapsWorm(newPosition); 
	 */	
	@Override
	public Position jumpStep(double t,double direction) throws IllegalJumpTimeException,IllegalArgumentException{
		assert isValidDirection(direction);
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
		
		
		this.setOverlapsWorm(newPosition);
		
		
		return newPosition; 
		}	
		
	}
	
	
	/**
	 * The projectile jumps from one position to another.
	 * @effect if the jumptime of this projectile is larger than zero then the position will be set to the position 
	 * 			at the end of the jump for the given jumptime
	 * 		|if (time > 0)
	 * 		|	then this.setPosition(jumpstep(this.jumpTime(timestep),this.getDirection()))
	 * @effect if the jumptime of this projectile is larger than zero and the overlappingworm is effective then the overlapping worm 
	 * 			gets hit by this projectile
	 * 		|if (time > 0) && (this.getOverlappingWorm() != null)
	 * 		|		then this.hitWorm(this.getOverlappingWorm())
	 * @effect if the jumptime of this projectile is larger than zero and the overlappingworm is not effective then this 
	 * 			projectile is terminated
	 * 		|if (time > 0) && (this.getOverlappingWorm() == null)
	 * 		|		then this.Terminate()
	 */
	@Override
	public void jump(double timestep)  throws IllegalJumpTimeException,IllegalArgumentException{

		double time = this.jumpTime(timestep);
		
		if (time > 0){
			Position newPosition = jumpStep(time,this.getDirection());
				
			this.setPosition(newPosition);
			
			
			// checken of de worm die geraakt wordt dood is --> termineren 
			if (this.getOverlappingWorm() != null)
				this.hitWorm(this.getOverlappingWorm());
			
			else
				this.Terminate();
			
		}
		
	}
	
	/**
	 * returns the worm that overlaps with this moving object
	 */
	public Worm getOverlappingWorm(){
		return this.overlappingWorm;
	}
	
	/**
	 * sets the worm that overlaps with this moving object
	 * @param worm 
	 * 		the worm that overlaps with this projectile
	 * @post the new value for overlappinWorm equals the given worm	
	 * 		| new.overlappingWorm == worm
	 */
	protected void setOverlappingWorm(Worm worm){
		this.overlappingWorm= worm;
	}
	
	/**
	 * the worm the projectile overlaps with in his jump
	 */
	private Worm overlappingWorm;
	
	/**
	 * sets the worm overlapping with the current Projectile
	 * @param position
	 * 		the position at which this projectile currenlty is
	 * @effect if the distance between this projectile and a worm is smaller than the sum of the radius of the 
	 * 			worm and the radius of the projectile
	 * 		|for each worm in this.getWorld().getAllWorm():
	 * 		|	if (position.calculateDistance(worm.getPosition()) < worm.getRadius()+ this.getRadius())
	 * 		|		then this.setOverlappingWorm(worm)
	 * 			
	 * 
	 */
	public void setOverlapsWorm(Position position){
		List<Worm> worms = this.getWorld().getAllWorms();
		for (Worm worm: worms){
			
			if (position.calculateDistance(worm.getPosition()) < worm.getRadius()+ this.getRadius()){
				this.setOverlappingWorm(worm);
			}

		}
	}
	
	
	
	/**
	 * a worm is hit by a projectile
	 * @param worm
	 * 		the worm that is hit
	 * @effect the worm loses HP according to the damage of the projectile
	 * 			|worm.decreaseCurrentHP(this.getWeapon().getHPDamage());
	 * @effect the overlapping worm is set to null
	 * 			|this.setOverlappingWorm(null);
	 * @effect the projectile is terminated
	 * 			|this.Terminate();
	 */
	protected void hitWorm(Worm worm){
		worm.decreaseCurrentHP(this.getWeapon().getHPDamage());
		this.setOverlappingWorm(null);
		this.Terminate();
		
	}
	
	
	
	/**
	 * Calculates the force exerted on this projectile during launch.
	 * @return The difference between the minimal and maximal force multiplied by the yield divided by 100 plus minimal force.
	 * 		|(this.getWeapon().getMinForce() + (this.getWeapon().getMaxForce() - this.getWeapon().getMinForce())* this.getYield()/100)
	 */
	@Override
	public double calculateForce() {
		double maxForce = this.getWeapon().getMaxForce();
		double minForce = this.getWeapon().getMinForce();
		
		return (minForce + (maxForce - minForce)* this.getYield()/100);
	}
	

	
}

	