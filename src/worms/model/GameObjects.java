package worms.model;

import be.kuleuven.cs.som.annotate.Basic;
import be.kuleuven.cs.som.annotate.Model;
import be.kuleuven.cs.som.annotate.Raw;
import worms.exceptions.*;
import worms.model.position.Position;


/**
 * A class of gameobjects involving a radius, a position and a world.
 * @invar	a GameObject is situated in a valid world
 *			| isValidWorld(getWorld())
 * @invar the radius of the game object must be larger than zero and not infinity
 * 			|isValidRadius(getRadius()))
 * @invar the characteristics of the GameObject must be valid numbers
 * 			| isValidNumber(GameObject.getPosition())
 * 			| isValidNumber(GameObject.getRadius())
 * @author Cleemput Enrico en Van Buggenhout Niel
 * @version 1.2
 */
public abstract class GameObjects {
	
	/**
	 * Initialize a new gameObject with a given world, given radius and given position.
	 * @param radius
	 * 		the radius for the gameObject 
	 * @param position
	 * 		the position of the gameObject in the gameWorld
	 * @param world
	 * 		the gameWorld of the object
	 * @post this new game object's radius equals the given radius
	 * 		| new.getRadius() == radius
	 * @post this new game object's position equals the given position
	 * 		| new.getPosition() == position
	 * @effect the world for this gameObject is set to the given world
	 * 		|this.setWorldTo(world);
	 */
	public GameObjects(double radius,Position position,World world) throws IllegalArgumentException, IllegalPositionException,IllegalWorldException{
		if (!isValidPosition(position))
			throw new IllegalPositionException(position);
		
		this.setRadius(radius);
		this.setPosition(position);
		this.setWorldTo(world);
		
	}
	
// radius
	/** 
	 * Return the radius of this game object.
	 */
	@Basic @Raw
	public double getRadius(){
		return this.radius;
	}
	
	/** 
	 * 
	 * Checks whether the given radius is valid.
	 * @param radius
	 * 			the radius to check	
	 * @return True if and only if the radius isn't smaller than the lower bound for the radius of this game object and not infinity
	 * 			| result == ((radius > 0) && (radius != Double.POSITIVE_INFINITY))
	 */
	@Raw
	protected
	static boolean isValidRadius(double radius,double lowerbound){
		return ((radius >= lowerbound) && (radius != Double.POSITIVE_INFINITY));
	}		
		
			
	/** 
	 * Sets the radius of this game object to the supplied value.
	 * @param radius
	 * 		The value to which to set the radius
	 * @post The new radius of this game object is equal to the given radius.
	 * 		|new.getRadius() == radius
	 * @throws IllegalRadiusException
	 * 		 if the given radius is invalid
	 * 		| ! isValidRadius(radius)
	 */	
	 @Raw @Model
	protected void setRadius(double radius) throws IllegalRadiusException{
		if (! isValidRadius(radius,0)){
			throw new IllegalRadiusException(radius);
			}
		this.radius = radius;
	}
	
	 
	/** 
	 * The radius of this game object.
	 */
	protected double radius;
	


//world
	
	/**
	 * return the world the GameObject is in
	 */
	@Basic
	public World getWorld(){
		return this.world;
	}
	
	/**
	 * checks is the world is valid
	 * @param world
	 * 		the world of the GameObject
	 * @return
	 * 		true if and only if world is not null and is not terminated
	 * 		|result = (world != null) && (!world.isTerminated())
	 */
	@Raw
	public boolean isValidWorld(World world){
		return (world != null) && (! world.isTerminated());
	}

	
	/**
	 * sets the world associated with the gameObjects 
	 * @param world
	 * 		the world to be set
	 * @effect 
	 * 		the game object is added to the list of gameObjects in the world of this gameObject
	 * 		| world.addGameObject(this);
	 * @effect 
	 * 		sets the world for this object to the given world
	 * 		|this.setWorld(world)
	 * @throws llegalArgumentException
	 * 		the given world in invalid
	 * 		| ! isValidWorld(world)
	 * 		the given world already references this game object
	 * 		| world.hasAsGameObjects(this)
	 */
	@Raw
	public void setWorldTo(World world) throws IllegalArgumentException{
		if (! isValidWorld(world)){
			throw new IllegalArgumentException("invalid world");
		}
		if (world.hasAsGameObject(this)){
			throw new IllegalArgumentException("given world already references this game object");
		}
		
		this.setWorld(world);
		world.addGameObject(this);
	}
	
	
	/**
	 * remove the world from the GameObject
	 * @post this new game object's world equals null 
	 * 		| new.getWorld() == null
	 * @effect remove this game object from this game object's world
	 * 		| this.getWorld().removeGameObject(this)
	 * 	@throws IllegalCommandException
	 * 		The world this game object references does not reference this gameobject back
	 * 		| ! getWorld().hasAsGameObject(this)
	 */
	public void removeWorld() throws IllegalCommandException{
			World formerWorld = this.getWorld();
			if (! formerWorld.hasAsGameObject(this)){
				throw new IllegalCommandException("the world does not reference this game object");
			}
			this.world = null;
			formerWorld.removeGameObject(this);
	}
	
	/**
	 * Sets the world of the GameObject.
	 * @param world
	 * 		the world the gameObject is in
	 * @post the new world this game object belongs to equals the given world
	 * 		| new.getWorld() == world
	 * @throws IllegalWorldException
	 * 		if the given world is not valid
	 * 		|! isValidWorld(world)
	 */
	@Raw
	public void setWorld(World world) throws IllegalWorldException{
		if (!isValidWorld(world))
			throw new IllegalWorldException(world);
		this.world = world;
		
	}
	
	
	/**
	 * the world a game object is in
	 */
	private World world;
	
// termination
	
	
	
	
	/**
	 * checks if the world is terminated
	 */
	@Basic
	public boolean isTerminated(){
		return this.isTerminated;
	}
	/**
	 * terminates the given gameObject
	 * @post This game object is terminated
	 * 		| new.isTerminated() == true
	 * @effect remove references between this game object's world and this game object
	 * 		| this.removeWorld();
	 */
	public void Terminate(){
		this.removeWorld();
		this.isTerminated = true;
		
	}
	
	/**
	 * a variable referencing the state of the world
	 */
	protected boolean isTerminated;
	
	
//Position	
	
	
	/** 
	 * Return the x-component of the position (x-coordinate) of the game object.
	 */
	public double getX(){
		return position.getX();
	}
	
	/** 
	 * Return the y-component of the position (y-coordinate) of the terrain.
	 */
	public double getY(){
		return position.getY();
	}
		
	
	/**
	 * Returns the position of this game object.
	 */
	@Basic
	public Position getPosition(){
		return this.position;
	}
	
	/**
	 * checks if a position is valid
	 * @param position
	 * 		the position to be checked
	 * @return true and only if the value for x and y are greater than 0
	 * 		|result == 
	 * 		|	(position.getX() >= 0 && position.getY() >= 0)
	 */
	@Raw
	public static boolean isValidPosition(Position position){
		return (position.getX() >= 0) && (position.getY() >= 0);
		
	}
	
	/** 
	 * Sets the Position of the GameObect to the supplied Position.
	 * @param position
	 * 			The Position to which to set the Position of the gameObject
	 * @post the new position of this game object equals the given position
	 * 		| new.getPosition() == position 
	 * @throws IllegalPositionException
	 * 		if the given position is an invalid position
	 * 		| ! world.isValidPosition(position)
	 */
	@Raw @Model
	protected void setPosition(Position position) throws IllegalPositionException{
				
		this.position = position;
	}
	
	/**
	 * the position of the gameObject
	 */
	private Position position;
	
}
	
