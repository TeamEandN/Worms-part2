package worms.model;

import worms.exceptions.IllegalPositionException;
import worms.exceptions.IllegalWorldException;
import worms.model.position.Position;
/**
 * a class of food a special case of static objects
 * @author Cleemput Enrico en Van Buggenhout Niel
 * @version 1.0
 */
public class Food extends StaticObjects {
	/**
	 * Intinializes this new food in the given World at the given position.
	 * @param position
	 * 		the position of this food in the given world
	 * @param world
	 * 		the world where this new food is situated in
	 * @effect This new food is initialized as a static object with given position, given world and the foodradius in the given world
	 * 		| super(World.getFoodRadius() , position, world);
	 */
	public Food(Position position, World world) throws IllegalArgumentException, 
			IllegalPositionException,IllegalWorldException{
		 
		super(World.getFoodRadius() , position, world);
		
	}
	
	
	
}
