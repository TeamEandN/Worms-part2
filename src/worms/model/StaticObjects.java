package worms.model;

import be.kuleuven.cs.som.annotate.Raw;
import worms.exceptions.IllegalPositionException;
import worms.exceptions.IllegalWorldException;
import worms.model.position.Position;
/**
 * A class of static objects as a special case of gameobjects,
 * these objects can not move.
 * @author Cleemput Enrico en Van Buggenhout Niel
 * @version 1.0
 */
public class StaticObjects extends GameObjects {
/**
 * Initialize this new static object in a given world, at a given position and with a given radius.  
 * @param radius
 * 		the radius of this new static object
 * @param position
 * 		the position of this new static object
 * @param world
 * 		the world this new static object is in
 * @effect This new static object is initialized as a game object with given radius, given position and given world.
 * 		|super(radius, position, world)
 */
	@Raw
	public StaticObjects(double radius, Position position, World world) throws IllegalArgumentException, IllegalPositionException,IllegalWorldException {
		super(radius, position, world);
		
	}

}
