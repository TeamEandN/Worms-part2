package worms.model;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import be.kuleuven.cs.som.annotate.*;
import worms.exceptions.*;
import worms.gui.*;
import worms.model.*; 
import worms.model.Worm.*;
import worms.model.position.Position;
import worms.util.Util;


/**
 * A class of worlds involving a height, a width, a passable map and a random number generator.
 * @invar The world can have it's dimensions as dimensions.
 * 		| isValidHeight(getHeight()) && isValidWidth(getWidth())
 * @invar The world has a valid passableMap as it's passableMap
 * 		| isValidPassableMap(getPassableMap())
 * @invar A world contains at most one live projectile at all times
 * 		| validNbOfLiveProjectiles()
 * @invar The game objects in this world are valid game objects.
 * 		| hasProperGameObjects()
 * @invar The world has proper teams
 * 		| hasPorperTeams()
 * @author Cleemput Enrico en Van Buggenhout Niel
 * @version 1.1
 */


public class World {
	/**
	 * Initialize this new world with the given dimensions, the given passableMap and the given random number generator.
	 * @param width
	 * 		The width of this new world
	 * @param height
	 * 		The height of this new world
	 * @param passableMap
	 * 		The passableMap that belongs to this new world
	 * @param random 
	 * 		The random number generator that belongs to this world.
	 * 		
	 * @post The new height of this new world is equal to the given height.
	 * 		|new.getHeight() == height
	 * @post The new width of this new world is equal to the given width.
	 * 		|new.getWidth() == width
	 * @post The new passableMap of this new world equals the given passableMap.
	 * 		| new.getPassableMap() == passableMap
	 * @post The new random number generator of this new world equals the given random number generator.
	 * 		| new.getRandom() == random
	 * 
	 * @effect The new conversion will equal the conversion corresponding to the given height, width and the given passableMap
	 * 		| setConversion()
	 * 		
	 * @throws IllegalArgumentException
	 * 		The given height (or width) is not a valid height (or width).
	 * 		| (! isValidHeight(height)) || (! isValidWidth(width))
	 * 		The given passableMap has invalid dimensions
	 * 		| (passableMap.length == 0) 
	 */
	@Raw
	public World(double width, double height, boolean[][] passableMap, Random random)throws IllegalArgumentException{
		if ((! isValidHeight(height)) || (! isValidWidth(width))){
			throw new IllegalArgumentException("Invalid world dimensions");
		}
		if (! isValidPassableMap(passableMap)){
			throw new IllegalArgumentException("invalid passable map");
		}
		
		this.random = random;
		this.width = width;
		this.height = height;
		this.passableMap = passableMap;
		setConversion();
	}
	
	/**
	 * Checks whether a position is a valid position for an entity(game object) in this world.
	 * @param radius
	 * 		The radius of the entity to check.
	 * @param position
	 * 		The position to check
	 * @param ateFood
	 * 		Boolean reflecting whether or not the entity has eaten food (only possible for worms).
	 * @return true if and only if the entity ate food and is still within the boundaries of the world
	 * 			or if the entity is on a position that is adjacent for an entity with given radius 	
	 * 		| result ==
	 * 		|	((ateFood && circleInWorld(radius,position))  || ((inWorld) && (adjacent)))
	 */
	public boolean isValidPosition(double radius, Position position, boolean ateFood){
		boolean inWorld = circleInWorld(radius,position);
		boolean adjacent =  isAdjacent(radius,position);
		if (ateFood){
			return inWorld;
		}
		return ((inWorld) && (adjacent));
	}
	
	
	/**
	 * Returns the height of this world.
	 */
	@Basic @Immutable
	public double getHeight(){
		return this.height;
	}
	
	/**
	 * The height of this world.
	 */
	private final double height;
	
	
	/**
	 * Returns the width of this world.
	 */
	@Basic @Immutable
	public double getWidth(){
		return this.width;
	}
	
	
	/**
	 * The width of this world.
	 */
	private final double width;
	
	
	

	
	/**
	 * Returns the upper bound for the width of this world.
	 */
	@Basic @Raw
	public static double getUpperBoundWidth(){
		return upperboundWidth;
	}
	
	/**
	 * Checks whether the given width is a valid width for a World.
	 * @param width
	 * 		The width to check.
	 * @return true if and only if the given width is not negative and smaller than the upperbound for width. 
	 * 		|width>=0 && width<=getUpperBoundWidth()
	 */
	@Raw
	public static boolean isValidWidth(double width){
		return ((width<=getUpperBoundWidth()) && (width>=0));		
	}
	
	
	
	/**
	 * The upper bound for the width of a world.
	 */
	private static final double upperboundWidth = Double.MAX_VALUE;
	
	/**
	 * Returns the upper bound for the height of a world.
	 */
	@Basic @Raw
	public static double getUpperBoundHeight(){
		return upperboundHeight;
	}
	
	/**
	 * Checks whether the given height is a valid height for a World
	 * @param height
	 * 		The height to check
	 * @return true if and only if the given height is not negative and smaller than the upperbound for height. 
	 * 		|height>=0 && height<=getUpperBoundHeight()
	 */
	@Raw
	public static boolean isValidHeight(double height){
		return ((height<=getUpperBoundHeight()) && (height>=0));		
	}
	
	
	/**
	 * The upper bound for the height of a world.
	 */
	private static final double upperboundHeight = Double.MAX_VALUE;
	
	
	/**
	 * Removes a given food from this world.
	 * @param wormfood
	 * 		the food to be removed
	 * @effect the given wormFood no longer references a world as it's world
	 * 			and this world no longer references this wormFood as one of it's foods
	 * 		| removeGameObject(wormfood)
	 */
	private void removeWormFood(Food wormfood){
		this.removeGameObject(wormfood);
	}
	
	/**
	 * Returns the radius of food in this world.
	 */
	public static double getFoodRadius(){
		return foodRadius;
	}
	
	/**
	 * The radius for all the food in this world.
	 */
	private static final double foodRadius = 0.2;
	
	/**
	 * The map that contains all the passable locations.
	 */
	public boolean[][] passableMap;
	
	/**
	 * Return the passableMap belonging to this world.
	 */
	@Basic @Immutable
	public boolean[][] getPassableMap(){
		return this.passableMap;
	}
	
	/**
	 * Return the number of rows (width of) in the passableMap.
	 */
	@Immutable
	public int getMapHeight(){
		return getPassableMap().length;
	}
	
	/**
	 * Return the number of columns (height of) in the passableMap.
	 */
	@Immutable
	public int getMapWidth(){
		return getPassableMap()[0].length;
	}
	
	/**
	 * Checks whether a passableMap is valid.
	 * @param passableMap
	 * 		The passableMap to check
	 * @return true if and only if he passableMap contains elements and the passableMap is effective.
	 * 		| result ==
	 * 		| 	((passableMap.length != 0) && (passableMap != null))
	 */
	@Raw
	public boolean isValidPassableMap(boolean [][] passableMap){
		return 	((passableMap.length != 0) && (passableMap != null));
	}
	
	/**
	 * Checks whether a given position is passable in this world.
	 * @param position
	 * 		The position to check.
	 * @return false if the position is not effective
	 * 		| if (position == null) 
	 * 		|		then return false
	 * 
	 * @return true if the position is not in this world
	 * 		| if (! positionInWorld(position))
	 * 		|	then return true
	 * 
	 * @return whether the position is effective and passable 
	 * 		| if ((position != null) && (positionInWorld(position))) 
	 * 		| 		then return 
	 * 		|			(isPassable(convertPositionToMap(position)[0],convertPositionToMap(position)[1]))
	 * 		|			|| (! positionInWorld(position)
	 */
	public boolean isPassable(Position position){
		if (position == null){
			return false;	
		}
		if (! positionInWorld(position)){
			return true;
		}
		int [] indexes = convertPositionToMap(position);
		return (isPassable(indexes[0],indexes[1]));
	}
	
	/**
	 * Check if a given position is impassable in this world.
	 * @param position
	 * 		The position to check.
	 * @return true if and only if the position is not passable
	 * 		| return (! isPassable(position))
	 */
	public boolean isImpassable(Position position){
		return (! isPassable(position));
	}
	
	/**
	 * Checks whether a given position is passable for a circle with a given radius.
	 * @param startRadius
	 * 		The radius from where to start the search.
	 * @param radius
	 * 		The radius of the circle.
	 * @param position
	 * 		The position of the center of the circle.
	 * @return True if and only if every position enclosed by the radius of this circle is passable, the given position is effective and the given radius is not negative.
	 * 		| result == 
	 * 		|	((position != null) && (radius >= 0) &&
	 * 		| 	for each pos for which      startRadius <= pos.distanceTo(position) <= radius:
	 * 		|		isPassable(pos)	)
	 */
	public boolean isPassableForCircle(double startRadius, double radius, Position position){
		if (position == null){
			return false;
		}
		if (radius < 0){
			return false;
		}
		double x = position.getX();
		double y = position.getY();
		
		double stepSize = Math.min(getHeightConversion(),getWidthConversion());
		double loopRadius = startRadius;
		boolean stop = false;
		while (! stop){
			if (loopRadius > radius){
				stop = true;
				loopRadius = radius;
			}
			double alpha = 0;
			double stepSizeAlpha = Math.PI/10;
			while(alpha <= 2*Math.PI-stepSizeAlpha){
				double xToCheck =  (x + loopRadius * Math.cos(alpha));
				double yToCheck =  (y + loopRadius * Math.sin(alpha));
				Position positionToCheck = new Position(xToCheck, yToCheck);
				if (isImpassable(positionToCheck)){
					return false;
				}
				
				alpha = alpha + stepSizeAlpha;
			}
			loopRadius = loopRadius + stepSize;	
		}
		return true;
	}
	
	/**
	 * Returns the distance an entity with given radius can travel in given direction from given position, the upperbound for this distance is the given maxDistance.
	 * @param radius
	 * 		The radius of the entity.
	 * @param maxDistance
	 * 		The maximum distance the entity can travel.
	 * @param direction
	 * 		The direction in which the entity wants to travel.
	 * @param position
	 * 		The position from where to determine the distance the entity can travel.
	 * @return The largest distance (between minimum distance and the given maxDistance) that an entity with given radius can travel in given direction from given position.
	 * 			In other words: the distance before(see stepSize) the first distance for which the next position in the given direction isn't passable anymore. 
	 * 		| result ==
	 * 		|	distance
	 * 		|		-New variable: (	x = position.getX()	)
	 * 		|		-New variable: (	y = position.getY()	)
	 * 		|	if (! isPassableForCircle(radius, new Position(x + (distance + stepSize)*cos(direction), y + (distance + stepSize)*sin(direction)))	&&
	 * 		|	for each d in [minDistance, minDistance + stepSize ..., distance]:
	 * 		|		isPassableForCircle(radius, new Position(x + d*cos(direction), y + d*sin(direction)))	||
	 * 		|	if ((distance == minDistance) && isPassableForCircle(radius,radius, new Position(x + minDistance*.cos(direction),y + minDistance*.sin(direction))))	||
	 * 		|	if((distance == 0) && (! isPassableForCircle(radius, radius, new Position(x + minDistance*.cos(direction),y + minDistance*.sin(direction)))))
	 * 
	 *  @note this method does not check whether the position obtained by moving a particular distance is adjacent.
	 */
	double wormCanPass(double radius, double maxDistance, double direction, Position position){
		double stepSize = 0.01; 
		double minDistance = 0.1;
		double x = position.getX();
		double y = position.getY();
		if (! isPassableForCircle(radius, radius, new Position(x + minDistance*Math.cos(direction),y + minDistance*Math.sin(direction)))){
			return 0;
		}
		minDistance = minDistance + stepSize;
		
		while (minDistance <= maxDistance){
			double xToCheck = x + minDistance*Math.cos(direction);
			double yToCheck = y + minDistance*Math.sin(direction);
			Position positionToCheck = new Position(xToCheck, yToCheck);
			if (! isPassableForCircle(radius,radius, positionToCheck)){
				return minDistance - stepSize;
			}
			minDistance = minDistance + stepSize;
		}
		return maxDistance;
	}
	
	/**
	 * Returns whether the given indexes are passable according to passableMap.
	 * @param i
	 * 		The column to check (corresponding to x-coordinates). 
	 * @param j
	 * 		The row to check (corresponding to y-coordinates).
	 * @return 
	 * 		if both the given indexes are in the map, i in [0,getMapWidth()] and j in [0,getMapHeight()], negative indexes don't exist.
	 * 			true if and only if the given indexes correspond to a passable point on the map or
	 * 		| if ( (i<=getMapWidth()) && (j<=getMapHeight()) )
	 * 		|		then return (getPassableMap[j][i])
	 * 
	 * 		if index i is out of bounds, replace i by the maximum allowed index and try again or
	 * 		| if (i>getMapWidth())
	 * 		|		then return (isPassable(getMapWidth(),j)
	 * 		| if (i<0)
	 * 		|		then return (isPassable(0,j) 
	 * 
	 * 		if index j is out of bounds, replace j by the maximum allowed index and try again 
	 * 		| if (i>getMapHeight())
	 * 		| 		then return (isPassableget(i,getMapHeight())
	 * 		| if (j<0)
	 * 		|		then return (isPassable(i,0) 
	 */
	@Model
	private boolean isPassable(int i, int j){
		if (i > getMapWidth()){
			isPassable(getMapWidth(),j);
		}
		if (i < 0){
			isPassable(0,j);
		}
		if(j > getMapHeight()){
			isPassable(i,getMapHeight());
		}
		if (i < 0){
			isPassable(i,0);
		}
		return (getPassableMap()[j][i]);
	}
	
	/**
	 * Checks if a given position is adjacent to impassable terrain for an entity with given radius.
	 * @param position
	 * 		The position to check.
	 * @param radius
	 * 		The radius of the entity to check whether the given position is adjacent.
	 * @return false if and only if
	 * 			the supplied radius is not a valid radius
	 * 			or the supplied position is not effective
	 * 			or the supplied position is not passable
	 * 			or there is no impassable position a distance 
	 * 			between radius and radius * 1.1 from the supplied position (radius of the supplied worm) 
	 * 			
	 * 			| if ( (radius <=0) ) ||  || (2*radius > Math.min(getHeight(), getWidth()) || (position == null) || (! isPassableForCircle(0,radius,position)) )
	 * 			|		then return false
	 * 			| ||
	 * 			|  for each R in [radius, radius + stepSize, radius + 2*stepSize, ... , 1.1*radius]: 
	 * 			|		(! checkAdjacent(position.getX(),position.getY(), R))
	 * 			|	then return false
	 */
	public boolean isAdjacent(double radius,Position position){
		if ((radius <= 0) || (2*radius > Math.min(getHeight(), getWidth()))){
			return false;
		}
		if (position == null){
			return false;
		}
		if (! isPassableForCircle(0,radius,position)){
			return false;
		}
		double x = position.getX();
		double y = position.getY();
		
		double stepSize = 0.001;
		double loopRadius = radius;
		while (loopRadius <= 1.1*radius){
			if (checkAdjacent(x,y, loopRadius)){
				return true;
			}
			loopRadius = loopRadius + stepSize;	
		}
		return false;
	}
	
	/**
	 * Checks whether there is an impassable position in a circle (with given raidus) around a given position.
	 * @param x
	 * 		x-coordinate of the position to check
	 * @param y
	 * 		y-coordinate of the position to check
	 * @param radius
	 * 		the radius of the circle 
	 * @return true if and only if there is exactly one impassable position a distance equal to the supplied radius
	 * 			from the position (position derived from the supplied x and y), at the bottom of the circle.
	 * 	
	 * 			| if for (exactly) one a in [6*PI/5, 6*PI/5 + stepSize, 6*PI/5 + 2*stepSize, ..., 9*PI/5-stepSize]: 
	 * 			|		-New Variable position = new Position((x + radius*cos(a), y + radius*sin(a)
	 * 			|	then return Impassable(position)
	 */
	@Model
	private boolean checkAdjacent(double x, double y, double radius){
		double alpha = Math.PI+Math.PI/5;
		double stepSize = Math.PI/50;
		int found = 0;
		while(alpha <= 2*Math.PI-Math.PI/5){
			double xToCheck =  (x + radius * Math.cos(alpha));
			double yToCheck =  (y + radius * Math.sin(alpha));
			Position positionToCheck = new Position(xToCheck, yToCheck);
			
			if (isImpassable(positionToCheck)){
				found = found + 1;
			}
			alpha = alpha + stepSize;
		}
		if (found != 1){
			return false;
		}
		return true;
	}
	
	/**
	 * checks if a given position is in the world
	 * @param position
	 * 		the position to be checked
	 * 
	 * @return true if and only if the coordinates of the given position are in the world and the given position is not null
	 * 		| result == 
	 * 		|	((position != null) && (inWorld(position.getX(),position.getY())))
	 * 		
	 */
	public boolean positionInWorld(Position position){
		if (position == null){
			return false;
		}
		return inWorld(position.getX(),position.getY());
	}
	
	/**
	 * Checks whether the given coordinates that make up a position are in the boundaries of the world.
	 * @param x
	 * 		The x-coordinate of the position to check
	 * @param y
	 * 		The y-coordinate of the position to check
	 * @return true if and only if the given x is in [0,getWidth] and the given y in [0,getHeight]
	 * 		| return (((x >= 0) &&(x <= getWidth())) && ((y <= getHeight()) && (y >= 0)))
	 */
	@Model
	private boolean inWorld(double x, double y) {
		
		return (((x >= 0) &&(x <= getWidth())) && ((y <= getHeight()) && (y >= 0)));
	}
	
	/**
	 * Checks whether a circle with given radius fits in the boundaries of this world.
	 * @param radius
	 * 		The radius of the circle.
	 * @param position
	 * 		The position of the center of the circle.
	 * @return true if and only if every position enclosed by a circle on given position with given radius
	 * 			is inside the boundaries of the world
	 * 		| result ==
	 * 		|	for each pos for which (pos.distanceTo(position) <= radius):
	 * 		|		positionInWorld(pos)
	 */
	private boolean circleInWorld(double radius, Position position){
		if (position == null){
			return false;
		}
		if (radius < 0){
			return false;
		}
		double x = position.getX();
		double y = position.getY();
		
		double stepSize = Math.min(getHeightConversion(),getWidthConversion());
		double loopRadius = 0;
		boolean stop = false;
		while (! stop){
			if (loopRadius > radius){
				stop = true;
				loopRadius = radius;
			}
			double alpha = 0;
			double stepSizeAlpha = Math.PI/10;
			while(alpha <= 2*Math.PI-stepSizeAlpha){
				double xToCheck =  (x + loopRadius * Math.cos(alpha));
				double yToCheck =  (y + loopRadius * Math.sin(alpha));
				Position positionToCheck = new Position(xToCheck, yToCheck);
				if (! positionInWorld(positionToCheck)){
					return false;
				}
				alpha = alpha + stepSizeAlpha;
			}
			loopRadius = loopRadius + stepSize;	
		}
		return true;
	}
	
	
	/**
	 * Checks whether the given indexes are indexes belonging to passableMap.
	 * @param i
	 * 		the i-index to check (corresponds to x)
	 * @param j 
	 * 		the j-index to check (corresponds to y)
	 * 
	 * @return the given indexes are both larger than/equal to 0, i-(j-)index is smaller than/equal to the width (height) of passableMap
	 * 		| 	(	(	(indexes[0] >= 0) && (indexes[0] <= getMapWidth()))	&&	((indexes[1] >= 0) && (indexes[1] <= getMapHeight())	)	)
	 */
	@Model
	private boolean inMap(int i,int j){
		return 	(	(	(i >= 0) && (i <= getMapWidth()))	&&	((j >= 0) && (j <= getMapHeight())	)	);
	}
	
	/**
	 * Calculates the conversion between coordinate x on the world and index i in the passableMap (aka widthConversion)
	 * 						and  between coordinate y on the world and index j in the passableMap (aka heightConversion).
	 * @return the calculated heightConversion and widthConversion in form of a list
	 * 		| result ==
	 * 		| 	[W,H]
	 * 		|		with W = getWidth()/getMapWidth()
	 * 		|		with H = getHeight()/getMapHeight()
	 */
	public double [] calculateConversionWorldToMap(){
		
		double worldHeight = getHeight();
		int mapHeight =getMapHeight();
		
		double worldWidth = getWidth();
		int mapWidth = getMapWidth();
		
		double heightConversion = worldHeight / mapHeight;
		double widthConversion = worldWidth / mapWidth;
		double [] result = {widthConversion,heightConversion};
		return result;
	}
	
	/**
	 * Converts a given position in the world to the corresponding position in passableMap.
	 * @param position
	 * 		The position to convert.
	 * @return The indexes in passableMap corresponding to the supplied position, 
	 * 			each index in passableMap describes a rectangle of height: getHeightConversion() (in meter)
	 * 				and width: getWidthConversion() (in meter)
	 * 		| return {Math.floor(x/getWidthConversion()), Math.ceil(getMapHeight() - y/getHeightConversion())}  
	 * 		
	 * @throws IllegalArgumentException
	 * 		if the supplied position is not effective
	 * 		| if (position == null)
	 * 		if the supplied position is not in the world
	 * 		| ! positionInWorld(position)
	 * 
	 * @note the 4 corrections here are the values at the border of the world, 
	 * 			normal conversion as listed above does not convert them properly.
	 * 		So there are extra if-statements to prevent wrong convertion.
	 */
	public int [] convertPositionToMap(Position position) throws IllegalArgumentException{
		if (position == null)
			throw new IllegalArgumentException("non-effective position");
		if (! positionInWorld(position)){
			throw new IllegalArgumentException("position not in world");
		}
		double x = position.getX();
		if (x == 0){ //correction 1
			x = EPS;
		}
		if (x == getWidth()){ // correction 2
			x = x - EPS;
		}
		
		int i = (int) Math.floor(x/(getWidthConversion()));
		double y = position.getY();
		
		if (y == 0){ // correction 3
			y = EPS;
		}
		if (y >= getMapHeight()){ // correction 4 
			y = y - EPS;
		}
		
		int inverseJ = (int) Math.ceil(y/(getHeightConversion()));
		int j = getMapHeight() - inverseJ;
		
		
		int [] indexes = {i,j};	
		return indexes;
	}
	
	/**
	 * Converts the indexes in passableMap to a position in the world.
	 * @param indexes
	 * 		The indexes to convert into position.
	 * @return	A position corresponding to the given indexes
	 * @throws IllegalArgumentException
	 * 
	 * @note because the indexes describe a rectangle in world, there are multiple positions that belong to a single index
	 * 			this method returns one of those positions.
	 */
	public Position convertMapToPosition(int[] indexes) throws IllegalArgumentException{
		if ( (indexes[0] < 0 ) || (indexes[0] > getMapWidth()) || (indexes[1] < 0 ) || (indexes[1] > getMapHeight())){
			throw new IllegalArgumentException("indexes out of bounds");
		}
		int i = indexes[0];
		double x = i*getWidthConversion();
		int j = indexes[1];
		double inverseY = j * getHeightConversion();
		double y = getHeight() - inverseY;
		Position position = new Position(x,y);
		return position;
	}
	
	/**
	 * Returns the conversion of height between the world's height and the passableMap's height (number of rows).
	 */
	@Basic @Immutable
	public double getHeightConversion(){
		return this.conversion[1];
	}
	
	/**
	 * Returns the conversion of width between the world's width and the passableMap's width (number of columns).
	 */
	@Basic @Immutable
	public double getWidthConversion(){
		return this.conversion[0];
	}

	/**
	 * Sets the conversions between the world's dimensions and the map's dimensions to the values calculated by calculateConversionWorldToMap().
	 * @post The new conversion will the calculated conversion.
	 * 		| new.getHeightConversion() == calculateConversionWorldToMap()[0]
	 * 		|	&&	new.getWidthConversion() == calculateConversionWorldToMap()[1]
	 */
	@Raw @Model
	private void setConversion(){
		this.conversion = calculateConversionWorldToMap();
	}
	
	/**
	 * The conversions between the world's dimensions and the map's dimensions.
	 */
	private double [] conversion;
	
	// startGame en startTurn
	
	/**
	 * Checks whether the game is finished.
	 * @return if the game hasn't started yet, the game cannot be finished
	 * 		| if (start == false)
	 * 		|	then return false
	 * @return true if there's only 1 team left in this world and there are no other worms in the world (teamless worms)
	 * 		| if ((getNbTeams() == 1 ) && (getWormsWithoutTeam().size() == 0))
	 * 		|	then return true
	 * @return true if there's no team left in this world and only 1 worm
	 * 		| if ((getNbTeams() == 0) && (getWormsWithoutTeam().size() == 1)){
			|		the return true
	 */
	public boolean isGameFinished(){
		if (! this.start)
			return false;
		if ((getNbTeams() == 1 ) && (getWormsWithoutTeam().size() == 0)){
			return true;
		}
		if ((getNbTeams() == 0) && (getWormsWithoutTeam().size() == 1)){
			return true;
		}
		return false;
		
	}
	
	/**
	 * Checks whether the current worm's turn is over.
	 * @return true if and only if the current worm has either a current AP equal to 0 or a current HP equal to 0
	 * 			and there's no active projectile in this world
	 * 		| result ==
	 * 		|	((checkPoints(getCurrentWorm())) && (getActiveProjectile() == null)) 
	 */
	protected boolean isTurnOver(){
		if (getActiveProjectile() != null){
			return false;
		}
		return checkPoints(getCurrentWorm());
	}
	
	protected void startGame(){
		eraseEmptyTeams();
		start=true;
		if (getAllWorms().size() < 2){
			throw new IllegalCommandException("can't play with less than 2 worms");
		}
		setTeamlessWorms();
		if (getNbTeams() == 0){
			setCurrentWorm(getTeamlessWorms().get(0));
		}
		else{ 
			setCurrentWorm(getTeamAt(0).getWormAt(0));
		}
		setCurrentTeam();
	}
	
	/**
	 * All the teams without worms in it will be erased from this world and the teams will no longer reference this world
	 * @effect if there's a team with no worms in it, 
	 * 			it will no longer reference this world as it's world and
	 * 			this world will no longer reference it as one of it's teams
	 * 		| for each team for which team.getNbWorms() == 0:
	 * 		|		then removeAsTeam(team)
	 */
	protected void eraseEmptyTeams() {
		int index = 0;
		while (index < getNbTeams()){
			Team team = this.getTeamAt(index);
			if (team.getNbWorms() == 0){
				this.removeAsTeam(team);
			}
			index = index + 1;
		}
	}

	/**
	 * Start the next turn and removes worms and teams if necessary.
	 * @post the new current worm equals the next worm
	 * 		| new.getCurrentWorm() == nextWorm()
	 * 
	 * @effect the new current worm's AP and HP will be set to the values for AP and HP at the start of a new turn
	 * 		| raiseAPAndHP(new.getCurrentWorm())
	 * @effect the new current team equals the team of the current worm
	 * 		| setCurrentTeam()
	 * @effect if this current worm has HP equal to 0 and has no team, this current worm will no longer reference a world as it's world
	 * 			and this world will no longer reference this current worm as one of it's game objects
	 * 		| if ((this.getCurrentWorm().getCurrentHP().getNumeral() == 0) && (this.getCurrentWorm().getTeam() == null)) 
	 * 		|	then this.getCurrentWorm().removeWorld()
	 * @effect if this current worm has HP equal to 0 and this worm has a team,
	 * 			this current worm will no longer reference this current team as it's team
	 * 			and this current team will no longer reference this current worm as one of it's worms
	 * 			and if this current team has no worms left, 
	 * 				this current team will no longer reference this world as it's world
	 * 				and this world will no longer reference this current team as one of it's teams
	 * 		| if ((this.getCurrentWorm().getCurrentHP().getNumeral() == 0) && (this.getCurrentTeam() != null))
	 * 		|	then ((this.getCurrentWorm().removeTeam() and eraseEmptyTeams())
	 * 
	 * 
	 * 		and this current worm will no longer reference this world as it's world
	 * 			and this world will no longer reference this current worm as one of it's worms
	 * 		|  and (this.getCurrentWorm().removeWorld()))
	 * 
	 * @throws IllegalCommandException
	 * 		the turn isn't over yet
	 * 		| ! isTurnOver()
	 */
	protected void startNextTurn() throws IllegalCommandException{
		if (isTurnOver() == false){
			throw new IllegalCommandException("Turn is not over");
		}
		Worm oldWorm = getCurrentWorm();
		Worm nextWorm = nextWorm();
		setCurrentWorm(nextWorm);
		setCurrentTeam();
		raiseAPAndHP(nextWorm);
		
		if (oldWorm.getCurrentHP().getNumeral() == 0){
			if (oldWorm.getTeam() == null){
				oldWorm.terminate();
				setTeamlessWorms();
			}
			else{
				oldWorm.terminate();
				eraseEmptyTeams();
			}
		}
	}
	
	/**
	 * Checks whether the worm has HP or AP zero.
	 * @param worms
	 * 		The worm to check.
	 * @return true if and only worm has either AP or HP equal to 0.
	 * 		|result == 
	 * 		|	((worm.getCurrentAP().getPoints() == 0) || (worm.getCurrentHP().getPoints() ==0))
	 */
	@Model
	private boolean checkPoints(Worm worm){
		int APWorm = worm.getCurrentAP().getNumeral();
		int HPWorm = worm.getCurrentHP().getNumeral(); 
		if ((APWorm != 0) && (HPWorm !=0)){
				return false;
			}
		return true;
	}
	
	
	/**
	 * Returns the worm who's next in line to start it's turn.
	 * @return If the current worm is not the last worm in the current team, the next worm is the next worm in the current team
	 * 		| if (! getIndexInTeam(getCurrentWorm()) ==   (getCurrentTeam().getNbWorms() - 1))
	 * 		|		then nextWorm == getCurrentTeam().getWormAt(getIndexInTeam(getCurrentWorm) + 1)
	 * 
	 * 			If the current worm is the last worm in the current team, then the next worm is the first worm of the next team
	 * 		| if (indexCurrentWorm == (currentTeam.getNbWorms() - 1))
	 * 		|		then nextWorm == getNextTeam().getWormAt(0)
	 */
	private Worm nextWorm(){
		Worm currentWorm = getCurrentWorm();
		Team currentTeam = getCurrentTeam();
		int indexCurrentWorm = getIndexInTeam(currentWorm, currentTeam == null);
		int maxIndex;
		
		try {
			maxIndex = currentTeam.getNbWorms() - 1;
		}
		catch (NullPointerException exc){
			maxIndex = getTeamlessWorms().size() -1;
		}
		
		Worm nextWorm;
		if (indexCurrentWorm == maxIndex){
			Team nextTeam = getNextTeam();
			if (nextTeam == null){
				nextWorm = getTeamlessWorms().get(0);
			}
			else {
				nextWorm = nextTeam.getWormAt(0);
			}
		}
		else{
			try {
				nextWorm = currentTeam.getWormAt(indexCurrentWorm + 1);
			}
			catch (NullPointerException exc){
				nextWorm = getTeamlessWorms().get(indexCurrentWorm + 1);
			}
		}
		return nextWorm;
	}
	
	/**
	 * Returns the index belonging to the given worm.
	 * @param worm
	 * 		The worm to search the index of
	 * @param teamless
	 * 		Whether or not the worm's team is null
	 * @return if teamless is true, the index will be searched in the localy saved list of teamless worms
	 * 		| if (teamless)
	 * 		|	then getIndexForTeamless(worm)
	 * 		
	 * 			if teamless is false, the index will be searched by using the worm's team
	 * 		| if (! teamless)
	 * 		|	then worm.getTeam().getIndexInTeam(worm)
	 */
	private int getIndexInTeam(Worm worm, boolean teamless) {
		if (teamless){
			return getIndexForTeamless(worm);
		}
		else{
			return worm.getTeam().getIndexInTeam(worm);
		}
	}

	/**
	 * Returns the index of a worm without a team in the list of teamless worms.
	 * @param worm
	 * 		the worm to search in the teamless list
	 * @returns the index of the given worm in the list of teamless worms
	 * 		|for index in [0, ..., getTeamLessWorms().size() - 1]:
	 * 		|	if (getTeamLessWorms().get(index) == worm)
	 * 		|		then result == index 
	 * @throws IllegalArgumentExeption
	 * 		The worm has a team.
	 * 		| worm.getTeam() != null
	 */
	private int getIndexForTeamless(Worm worm) throws IllegalArgumentException{
		if (worm.getTeam() != null){
			throw new IllegalArgumentException("worm not in teamless list");
		}
		int index = 0;
		List<Worm> teamless = getTeamlessWorms();
		int result = -1;
		while (index < (teamless.size()-1)){
			if (teamless.get(index) == worm){
				result = index;
			}
			index = index + 1;
		}
		return result;
	}

	/**
	 * Returns which team will have it's turn next.
	 * @return if the current team in this world is null (which means teamless worms), the next team is the first
	 * 			team is the list of teams in this world, if there are teams in this world.
	 * 		| if ((current == null) && (this.getNbTeams() != 0)){
	 * 		|	then return getTeamAt(0)
	 * @return if the current team in this world is null and there are no teams in this world, the next team is null.
	 * 		| if ((current == null) && (this.getNbTeams() == 0)){
			| 	then return null;
	 * @return if the current team is not null, the next team is the next team in the list of teams in this world.
	 * 		| if (getCurrentTeam() != null)
	 * 		|	then return( getTeamAt(getIndexOfTeam(current) + 1))
	 */
	@Model
	private Team getNextTeam() {
		Team current = getCurrentTeam();
		if ((current == null) && (this.getNbTeams() != 0)){
			return getTeamAt(0);
		}
		if ((current == null) && (this.getNbTeams() == 0)){
			return null;
		}
		int index = getIndexOfTeam(current);
		if (index == (getNbTeams() - 1)){
			return null;  // teamless worms
		}
		return getTeamAt(index + 1);
		
	}

	/**
	 * Sets the current AP and HP of every worm in this world to the appropriate value for the start of a new turn.
	 * @effect every worm's AP will be set to the appropriate value
	 * 		| for each worm in getAllWorms():
	 * 		|	worm.raiseAPNewTurn()
	 * 		
	 * 			every worm's HP will be set to the appropriate value
	 * 		| for each worm in getAllWorms():
	 * 		|	worm.raiseHPNewTurn()
	 */
	@Model
	private void raiseAPAndHP(Worm worm) throws IllegalCommandException{
			worm.raiseAPNewTurn();
			worm.raiseHPNewTurn();
	}

	
	/**
	 * Returns all the worms that don't belong to a team as a list.
	 * @return every worm that doesn't have a team
	 * 		| for each worm in result:
	 * 		|	worm.getTeam() == null
	 */
	private List<Worm> getWormsWithoutTeam() {
		List<Worm> teamless = getAllWorms();
		int index = getAllWorms().size()-1;
		while (index >= 0){
			if (teamless.get(index).getTeam() != null){
				teamless.remove(index);
			}
			index = index - 1;
		}
		return teamless;
	}
	
	/**
	 * Returns a list containing all the worms that do not belong to a team
	 */
	private List<Worm> getTeamlessWorms(){
		return this.teamLessWorms;
	}
	
	/**
	 * Sets the list of teamless worms to all the worms without a team.
	 * @effect teamlessWorms equals all the worms that do not belong to a team.
	 * 		| new.getTeamlessWorms() == getWormsWithoutTeams()
	 */
	protected void setTeamlessWorms(){
		this.teamLessWorms = getWormsWithoutTeam();
	}
	
	/**
	 * A list containing all teamless worms.
	 */
	private List <Worm> teamLessWorms;
	
	/**
	 * Returns the worm who's turn it is.
	 */
	public Worm getCurrentWorm(){
		return this.currentWorm;
	}
	
	/**
	 * Sets the current worm to the given worm.
	 * @param worm
	 * 		The worm to which to set current worm / the worm who's turn starts.
	 * @post The new current worm equals the given worm.
	 * 		| new.getCurrentWorm() == worm
	 */
	private void setCurrentWorm(Worm worm){
		this.currentWorm = worm;
	}
	
	/**
	 * Variable that contains the worm which is currently under control of the user (it's this worm's turn).
	 */
	private Worm currentWorm;
	
	/**
	 * Sets the current team to the team of the current worm.
	 * @post The new current team will equal the team of the current worm of this world.
	 * 		| new.getCurrentTeam() == getCurrentWorm().getTeam()
	 */
	private void setCurrentTeam(){
			this.currentTeam = getCurrentWorm().getTeam();
	}
	
	/**
	 * Returns the team to which the current worm belongs.
	 */
	private Team getCurrentTeam(){
		return this.currentTeam;
	}
	
	/**
	 * Variable that contains the team to which the currentWorm belongs
	 */
	private Team currentTeam;
	
	/**
	 * Returns the active projectile or null if there isn't any.
	 * @return null if and only if there's no projectile in the list of game objects currently in this world
	 * 		| if for each object in getAllGameObjects():
	 * 		|	object.getClass() != Projectile.class
	 * 		|		then result == null
	 * 		
	 * 			the active projectile otherwise
	 * 		| if for one object in getAllGameObjects():
	 * 		|	object.getClass() == Projectile.class
	 * 		|		then result == object
	 */
	public Projectile getActiveProjectile(){
		List<GameObjects> allGameObjects = getAllGameObjects();
		int index = 0;
		Projectile activeProjectile = null;
		while(index < (allGameObjects.size())){
			if (allGameObjects.get(index).getClass() == Projectile.class){
				activeProjectile = (Projectile) allGameObjects.get(index);
			}
			index = index + 1;
		}
		return activeProjectile;
	}
	
	/**
	 * Checks whether there is a valid number of live projectiles in this world.
	 * @return true if and only there are less than 2 game objects of the class Projectile in the list containing all game objects.
	 * 		| result ==
	 * 		|	for each obj in getAllGameObjects():
	 * 		|		obj.getClass() != Projectile.class
	 * 		|	||
	 * 		|	for one obj in getAllGameObjects():
	 * 		|		obj.getClass() == Projectile.class
	 */
	public boolean validNbOfLiveProjectiles(){
		List<GameObjects> allGameObjects = getAllGameObjects();
		int index = 0;
		int nbOfProjectiles = 0;
		while(index < (allGameObjects.size() - 1)){
			if (allGameObjects.get(index).getClass() == Projectile.class){
				nbOfProjectiles = nbOfProjectiles + 1;
			}
			index = index + 1;
		}
		return ((nbOfProjectiles == 0) || (nbOfProjectiles == 1));
	}
	
	/**
	 * Generates a random adjacent position that is adjacent for an entity with given radius.
	 * @param radius
	 * 		The radius of the entity that has to be placed on an adjacent position in the world.
	 * @return an adjacent position in the world, if such a position is found
	 * 		| if for one position in [...]:
	 * 		|	isAdjacent(radius, position) == true
	 * 		|		then result == position
	 * 		
	 * 		| if for each position in [...]:
	 * 		| 	isAdjacent(radius,position) != true
	 * 		|		then result == null
	 */
	@Model
	private Position generateRandomAdjacentPosition(double radius){
		Position beginPosition = randomPosition();
		Position toPosition = new Position(getWidth()/2,getHeight()/2);
		double distance = beginPosition.calculateDistance(toPosition);	
		double stepSize = distance/100; 
		Position result = null;
		while ((distance >= 0) && (result == null)){
			result = findAdjacentOnCircle(toPosition, distance, radius);
			distance = distance - stepSize;
		}
		return result;
	}
	
	/**
	 * Find an adjacent position on the circumference of a cirlce with given radius for an entity with given radius.
	 * @param center
	 * 		The center of the circle.
	 * @param radiusCircle
	 * 		The radius of the circle.
	 * @param radiusEntity
	 * 		The radius of the entity.
	 * @return if for a position on the given circle, the entity with given radius is adjacent to that position, 
	 * 			then return that position
	 * 		|	-New method: newPos(A) = new Position(center.getX() + radiusCircle*cos(A),center.getY() + radiusCircle*sin(A))
	 * 		| if for one pos in [newPos(0),newPos(stepSize), ...,  newPos(2*PI - stepSize)]:
	 * 		|		isAdjacent(radiusEntity,pos)
	 * 		|	then return pos
	 */
	private Position findAdjacentOnCircle(Position center, double radiusCircle, double radiusEntity){
		double alpha = 0;
		double stepSize = Math.PI/50;
		while (alpha <= 2*Math.PI-stepSize){
			double newX = center.getX() + radiusCircle*Math.cos(alpha);
			double newY = center.getY() + radiusCircle*Math.sin(alpha);
			if (! inWorld(newX,newY)){
				alpha = alpha + stepSize;
			}
			else {
				Position newPosition = new Position(newX,newY);
				if (isAdjacent(radiusEntity,newPosition)){
					return newPosition;				
				}
				alpha = alpha + stepSize;
			}
		}
		return null;
	}
	
	/**
	 * Returns the random number generator.
	 */
	@Basic @Model @Immutable
	private Random getRandom(){
		return this.random;
	}

	/**
	 * Generates a random position inside this world.
	 * @return a randomly generated position inside this world
	 * 		| result ==
	 * 		|		position
	 * 		| for position: positionInWorld(position) == true
	 */
	private Position randomPosition(){
		double x = (random.nextDouble() + random.nextInt()) % getWidth();
		if (x<0){
			x = -1*x;
		}
		double y = (random.nextDouble() + random.nextInt()) % getHeight();
		if(y<0){
			y = -1*y;
		}
		return new Position(x,y);
	}
	
	/**
	 * Random number generator
	 */
	private Random random;
	
	/**
	 * Variable that stores whether the game has started.
	 */
	private boolean start = false;
	
	/**
	 * Calculates the slope at the current position in the given direction.
	 * @param position
	 * 		The position at which to calculate the slope
	 * @param direction
	 * 		The direction in which to calculate the slope
	 * @return the angle of the slope
	 * 		| if ((xEnd-xBegin) >= 0) 
	 * 		| 	then result == 
	 * 		|	Arctan(
	 * 		|	((position.getY()-radiusWorm)-findNextPositionOnSlope(new Position(nextX,position.getY-radiusWorm))).getY()) /
	 * 		|	(position.getX()-findNextPositionOnSlope(new Position(nextX,position.getY-radiusWorm)).getX()))
	 * 
	 * 		Due to the defenition of Arctan however there have to be made some corrections in 2 cases.
	 * 		| if (((xEnd - xBegin) < 0) && ((yEnd - yBegin) < 0))
	 * 		|	then result 	==	 Arctan(
	 * 		|	((position.getY()-radiusWorm)-findNextPositionOnSlope(new Position(nextX,position.getY-radiusWorm))).getY()) /
	 * 		|	(position.getX()-findNextPositionOnSlope(new Position(nextX,position.getY-radiusWorm)).getX())) 
	 * 		|		- Pi
	 * 		or
	 * 		| if (((xEnd - xBegin) < 0) && ((yEnd - yBegin) >= 0))
	 * 		|	then result 	==	 Arctan(
	 * 		|	((position.getY()-radiusWorm)-findNextPositionOnSlope(new Position(nextX,position.getY-radiusWorm))).getY()) /
	 * 		|	(position.getX()-findNextPositionOnSlope(new Position(nextX,position.getY-radiusWorm)).getX())) 
	 * 		|		+ Pi
	 * @throws IllegalArgumentException
	 * 		if the given position is not effective
	 * 		|	position == null
	 * @note because of the rough structure on a small scale, the average of the 10 next pixels are used to determine an average next position on the slope
	 */
	public double calculateSlope(Position position, double direction,double radiusWorm) throws IllegalArgumentException,IllegalPositionException{
		if (position == null){
			throw new IllegalArgumentException("non-effective position");
		}
		int totalPositions = 10;
		int loop = totalPositions;
		Position total = new Position(0,0);
		while (loop > 0){
			double nextX = nextX(position, direction, 1*loop);
			Position nextPosition = new Position(nextX,position.getY()-radiusWorm);
			Position nextPositionOnSlope = findNextPositionOnSlope(nextPosition);
			total = total.add(nextPositionOnSlope);

			loop = loop - 1;
		}
		
		Position nextPositionOnSlopeAverage = new Position(total.getX()/totalPositions,total.getY()/totalPositions);
		
		double xBegin = position.getX();
		double xEnd = nextPositionOnSlopeAverage.getX();
		double yBegin = position.getY()-radiusWorm;
		double yEnd = nextPositionOnSlopeAverage.getY();
		double s = Math.atan((yBegin-yEnd)/(xBegin-xEnd));
		if ((xEnd - xBegin) < 0){
			if ((yEnd - yBegin) < 0){
				s = s - Math.PI;
			}
			else {
				s = s + Math.PI;
			}
		}
		return s;
	}
	
	
	/**
	 * Check whether the direction is to the left or to the right.
	 * @return true if an only if the direction is to the right or straight up or straight down
	 * 		 | result == ((direction >= 3*Math.PI/2) || ((direction <= Math.PI/2) && (direction >= Math.PI/(-2))) || (direction <= -3*Math.PI/2))
	 */
	@Model
	private boolean facingRight(double direction)throws IllegalArgumentException{
		if ((direction <= -2*Math.PI) || (direction >= 2*Math.PI)){
			throw new IllegalArgumentException("invalid direction");
		}
		return ((direction >= 3*Math.PI/2) || ((direction <= Math.PI/2) && (direction >= Math.PI/(-2))) || (direction <= -3*Math.PI/2));
	}
	
	/**
	 * Calculates the position on the slope, according to the given position, the given direction 
	 * 	and whether or not the given position is passable.
	 * @param position
	 * 		A position on the slope with the same x-coordinate as the given position.
	 * @return the first position that satisfies the conditions of a position on the slope.
	 * 		| result == (findPositionOnSlope(position.getX(),position.getY(), isImpassable(position)))
	 * @throws IllegalPositionException
	 * 		if the given position is not a position in this world
	 * 		| ! positionInWorld(position)
	 */
	@Model
	private Position findNextPositionOnSlope(Position position) throws IllegalPositionException{
		if (! positionInWorld(position)){
			throw new IllegalPositionException(position);
		}
		Position nextPositionOnSlope;
		if (isPassable(position)){
			nextPositionOnSlope = findPositionOnSlope(position.getX(),position.getY(),false);
		}
		else {
			nextPositionOnSlope = findPositionOnSlope(position.getX(),position.getY(),true);
		}
		return nextPositionOnSlope;
	}

	/**
	 * Determines, starting from this position, what the position is( with the same x-coordinate) in this world located on the slope.
	 * @param x
	 * 		x-coordinate of this position, constant for the whole method, only y varies.
	 * @param y
	 * 		y-coordinate of this position.
	 * @param up
	 * 		Boolean indicating whether to search at an higher(lower) y than given y.
	 * 		up --> higher
	 * 		down --> lower 
	 * @return the first position for which there is a transition from passable to impassable (next position)
	 * 			|	-notation: (x,y) = new Position(x,y)
	 * 			| if ((! up) && (for pos in [(x,y),(x,y-getHeightConversion()), ..., (x,0)]:
	 * 			|					for each p in [(x,y),(x,y-getHeightConversion()), ..., (x,yEnd)] :
	 * 			|						isPassable(p)
	 * 			|					&& isImpassable(pos))		-With pos = (x,yEnd - getHeightConversion()) --> which is the position after the last p
	 * 			|	then return pos
	 * 
	 * 			or when there's no such position found and the search is downward, the y-coordinate is minus infinity
	 * 			| if ((! up) && (for each pos in [(x,y),(x,y-getHeightConversion()), ..., (x,0)]:
	 * 			|					isPassable(pos)))
	 * 			|		then return Position(x,-infinity)
	 * 
	 * 			the first position for which this position is impassable and the nxt one is passable
	 * 			|	-notation: (x,y) = new Position(x,y)
	 * 			| if ((up) && (for pos in [(x,y),(x,y+getHeightConversion()), ..., (x,getHeight())]:
	 * 			|					for each p in [(x,y),(x,y+getHeightConversion()), ..., (x,yEnd)] :
	 * 			|						isImpassable(p)
	 * 			|					&& isPassable(pos))		-With pos = (x,yEnd + getHeightConversion()) --> which is the position after the last p
	 * 			|	then return new Position(x,yEnd)
	 * 
	 * 			or when there's no such position found and the search is upward, the y-coordinate is infinity
	 * 			| if ((! up) && (for each pos in [(x,y),(x,y-getHeightConversion()), ..., (x,getHeight())]:
	 * 			|					isImpassable(pos)))
	 * 			|		then return Position(x,infinity)
	 * 
	 * 
	 * @throws IllegalPositionException
	 * 		if the this position is such that the next position to check is not a valid position in this world
	 * 		|((up) && (y == getHeight()) || ((!up) && (y == 0))
	 * 		
	 */
	@Model
	private Position findPositionOnSlope(double x, double y, boolean up) throws IllegalPositionException{
		if ((up) && (y == getHeight())){
			throw new IllegalPositionException(new Position(x,y+1));
		}
		if ((!up) && (y == 0)){
			throw new IllegalPositionException(new Position(x,y-1));
		}
		double stepSize;
		if (up){
			stepSize = 1*getHeightConversion();
		}
		else {
			stepSize = -1*getHeightConversion();
		}
		boolean thisOne = isPassable(new Position(x,y));
		Position position = new Position(x,y + stepSize);
		while ((isPassable(position) == thisOne)){ 
			position = new Position(x,y);
			y = y + stepSize;
			
			if(! positionInWorld(position)){		// Pi/2 and -Pi/2   
				double infinity = Double.POSITIVE_INFINITY * stepSize;
				return new Position(x,infinity);			
			}
		}
		if (stepSize > 0){
			return new Position(position.getX(),position.getY() - stepSize);
		}
		return position;
	}

	/**
	 * Determines what the best x-coordinate is for the position to calculate the slope. 
	 * @param position
	 * 		The position for which to determine the next x-coordinate
	 * @param direction
	 * 		The direction in which to search the next x-coordinate
	 * @param distance
	 * 		The distance between the given position's x-coordinate and the the next.(in pixels)
	 * @return
	 * 		The x-coordinate of this position + (1*WidthConversion) if the direction is to the right, 
	 * 			widthConversion is the amount of meters along the x-axis that 1 index in passableMapcovers (meters/i-index).
	 * 		| if(facingRight(direction)):
	 * 		|	then return new Position(position.getX() +(1*getWidthConversion())
	 * 
	 * 		The x-coordinate of this position - (1*WidthConversion) if the direction is to the left, 
	 * 			widthConversion is the amount of meters along the x-axis that 1 index in passableMapcovers (meters/i-index).
	 * 		| if(! facingRight(direction)):
	 * 		|	then return new Position(position.getX() -(1*getWidthConversion())
	 */
	@Model
	private double nextX(Position position, double direction, int distance) {
		if (facingRight(direction)){
			return (position.getX() + (distance*getWidthConversion()));
		}
		return (position.getX() - (distance*getWidthConversion()));
	}

// Teams	
	/**
	 * A method to call the number of teams in this world.
	 * @return returns the length of the list teams
	 * 		| result == teams.size()
	 */
	public int getNbTeams(){
		return teams.size();
	}
		
	/**
	 * Returns all the teams in this world.
	 */
	@Basic
	public List<Team> getAllTeams(){
		return this.teams;
	}
	
	
	/**
	 * Returns the maximum allowed number of teams for a world.
	 */
	@Basic @Immutable
	public static int getMaxNbTeams() {
		return maxAllowedNbOfTeams;
	}
	
	/**
	 * Checks whether there aren't too much teams in this world.
	 * @return returns true if the number of teams is smaller than maximum allowed number of teams 
	 * 		|result == 
	 * 		|	(getNbTeams <= getMaxNbTeam()) 
	 */
	public boolean hasProperNbTeams(){
		return (getNbTeams() <= getMaxNbTeams());
	}
	
	
	/**
	 * Checks whether teams contains a valid team at the index given.
	 * @param index
	 * 		the index at which the team is checked
	 * @return true if and only if the index is smaller then the maximum allowed number of teams for a world and the team on the given index is valid
	 * 				and the team at the given index only appears once in the list.
	 * 		| result ==
	 * 		|	((index < getMaxNbTeams()) && (isValidTeam(getTeamAt(index)))
	 * 		|	 && for exactly one i in [0, ..., getNbTeams()]:
	 * 		| 		getTeamAt(i) == getTeamAt(index))
	 */
	public boolean canHaveAsTeamAt(int index) throws IllegalArgumentException{
		int i = 0;
		int teamsFound = 0;
		while (i < getNbTeams()){
			if (getTeamAt(index) == getTeamAt(i)){
				teamsFound = teamsFound + 1;
			}
			i = i + 1;
		}
		return ((index <= getMaxNbTeams()) && (isValidTeam(getTeamAt(index))) && (teamsFound == 1) );
	}
	
	/**
	 * Checks whether a team is valid.
	 * @param team
	 * 		the team to be checked
	 * @return true if and only if the team is not null and not terminated
	 * 		|result ==
	 * 		|	 (team != null) && (! team.isTerminated())
	 */
	public static boolean isValidTeam(Team team){
		return (team != null) && (! team.isTerminated());
	}
		
	/**
	 * Checks whether the given index is a valid index for the list of teams of this world.
	 * @param index
	 * 		The index to check.
	 * @return true if and only if the given index does not exceed the maximum allowed number of teams and the given index is not negative
	 * 		| result == 
	 * 		|	((index < getMaxNbTeams()) && (index >= 0))
	 */
	public boolean isValidTeamIndex(int index){
		return ((index < getMaxNbTeams()) && (index >= 0));
	}
	
	
	/**
	 * Checks Whether world has proper teams referencing it.
	 * @return true if and only all the teams are valid teams and this world can have it's teams as it's team at their index
	 * 		| result ==
	 * 		| 	((getNbTeams() <= getMaxNbTeams())
	 * 		| 	&& for each i in [0, ..., getNbTeams()-1]:
	 * 		|		 (canHaveAsTeamAt(i)))
	 */
	public boolean hasProperTeams() throws IllegalArgumentException{
		if (getNbTeams() > getMaxNbTeams())
			return false;
		for (int i = 0; i < getNbTeams();i++){
			if (! canHaveAsTeamAt(i))
				return false;
		}
		return true;
	}
	
	/**
	 * Sets the number of teams in this world
	 * 
	 * @note not applicable for the chosen type with which teams are saved  
	 */
	public void setNbTeams(int size){
	}
	
	/**
	 * Adds a team with the given name to this world.
	 * @param name
	 * 		The name to give this new team.
	 * @effect A new team will be created for this world, the team will reference this world as it's world
	 * 			and this world will reference the new team as one of it's teams
	 * 		| Team(name,this)
	 * 
	 * @throws IllegalCommandException
	 * 		The maximum allowed number of teams has been reached
	 * 		| 
	 */
	public void addTeam(String name) throws IllegalCommandException{
		
		if (this.getNbTeams()>= 10)
			throw new IllegalArgumentException("there cannot be more then 10 teams");
		
		new Team(name,this);
		
	}
	
	/**
	 * Checks whether the given team is allready a team in this world.
	 * @param team
	 * 		The team to check.
	 * @return true if and only if the team isn't allready in this world and the given team is not null
	 * 		| result ==
	 * 		|	((getIndexOfTeam(team)== -1 ) && (team !=null))
	 */
	@Model
	boolean hasAsTeam(Team team) {
		if (team == null){
			return false;
		}
		return (getIndexOfTeam(team) != -1 );
	}
	
	
	/**
	 * Remove a given team from the list of teams.
	 * @param team
	 * 		The team that has to be removed.
	 * @post The given team will no longer be in the list of teams
	 * 		| for each index in [0, new.getNbTeams - 1]
	 * 		|	new.getTeamAt(index) != team
	 * @throws IllegalCommandException
	 * 		The given team is not one of the teams referencing this world
	 * 		| ((! hasAsTeam(team))
	 */
	protected void removeAsTeam(Team team) throws IllegalCommandException{
		if (! hasAsTeam(team)){
			throw new IllegalCommandException("Team does not reference this world");
		}
		teams.remove(team);
	}
		
	/**
	 * Adds a team to the list of teams.
	 * @param team
	 * 		the team to be added
	 * @post the new list of teams has the given team as one of it's teams
	 * 		| new.hasAsTeam(team) == true
	 * @throws IllegalCommandException
	 * 		if the team is already in this world an excpetion is thrown
	 * 		|hasAsTeam(team)
	 */
	protected void addAsTeam(Team team) throws IllegalCommandException{
		if (hasAsTeam(team)){
			throw new IllegalCommandException("team already in this world");
		}
		teams.add(team);
	}
	
		
	/**
	 * Returns the team that is situated in on the given index.
	 * @param index
	 * 		the index at which the team is to be found in the list
	 * @return returns the element at the index given 
	 *		|result ==
	 *		|	 teams.get(index)
	 * @throws IllegalArgumentException
	 *		the given index is an invalid index for list of teams
	 *	 	| ! isValidTeamIndex(index)	
	 */
	@Basic
	private Team getTeamAt(int index){
		if (! isValidTeamIndex(index)){
			throw new IllegalArgumentException("invalid index for list of teams");
		}
		return teams.get(index);
	}
	
	
	/**
	 * Returns the index of a team in the list of teams.
	 * @param team
	 * 		The team to find the index of.
	 * @return if the given team is in this world's list of teams the index at which the given team is located.
	 * 		| if for one index in [0, ..., getNbTeams() - 1]:
	 * 		|	team == getTeamAt(index)
	 * 		| 	then return index
	 * 			if the given team is not in this world's list of teams, -1 will be returned
	 * 		| if for each index in [0, ..., getNbTeams() - 1]:
	 * 		|	team != getTeamAt(index)
	 * 		|	then return -1
	 * @throws IllegalArgumentException
	 * 		the given team is not effective
	 * 		| team == null
	 */
	private int getIndexOfTeam(Team team) throws IllegalArgumentException{
		if (team == null){
			throw new IllegalArgumentException("non-effective team");
		}
		int index = 0;
		while (index < getNbTeams()){
			if (team == getTeamAt(index)){
				return index;
			}
			index  = index + 1;
		}
		return -1;
	}
		

	
	/**
	 * Add a new team to the list teams.
	 * @param index
	 * 		the index at which the team is to be added
	 * @param team
	 * 		The team to be added to the list of team at the given index
	 * @post the new list of teams will have the given team at the given index
	 * 		| new.getTeamAt(index) == team
	 * 		and all the teams before the given index will stay in their place
	 * 		|&& for each i < index (and i>=0):
	 * 		|		this.getTeamAt(i) == new.getTeamAt(i)
	 * 		and all the teams starting from the given index untill the end of the list will be moved one index up
	 * 		|&& for each i >= index (and i < getAllTeams().size())
	 * 		|		this.getTeamAt(i) == new.getTeamAt(i+1)
	 * @throws IllegalCommandException
	 * 		The given index a valid index for the list of teams in this world or the maximum number of teams allowed for a world has already been reached.
	 * 		| ((! isValidTeamIndex(index))  ||	(getNbTeams() >= getMaxNbTeams()))
	 * 		 Or the given team already is a team in the team's of this world
	 * 		| || hasAsTeam(team)
	 */
	private void addAsTeamAt(int index, Team team) throws IllegalCommandException{
		if ((getNbTeams() >= getMaxNbTeams()) || (! isValidTeamIndex(index)))
			throw new IllegalCommandException("can not add as team");
		if (hasAsTeam(team)){
			throw new IllegalCommandException("team already a team in this world's teams");
		}
		teams.add(index, team);
	}

	/**
	 * Removes the team from the list at the given index.
	 * @param index
	 * 		The index at which the team is to be removed. 
	 * @post the new list of teams will no longer contain the team that was positioned at the given index
	 * 		| for each team in new.getAllTeams():
	 * 		|	 team != this.getTeamAt(index)
	 * 		and all the teams before the given index will stay in their place
	 * 		|&& for each i < index (and i>=0):
	 * 		|		this.getTeamAt(i) == new.getTeamAt(i)
	 * 		and all the teams starting from the given index untill the end of the list will be moved one index down
	 * 		|&& for each i > index (and i < new.getAllTeams().size())
	 * 		|		this.getTeamAt(i) == new.getTeamAt(i-1)
	 * @throws IllegalCommandException
	 * 		the given index isn't a valid index for a team in this world or the given index exceeds the list (out of bounds).
	 * 		| ((! isValidTeamIndex(index)) || (index >= getNbTeams())
	 */
	private void removeAsTeamAt(int index) throws IllegalCommandException{
		if ((! isValidTeamIndex(index)) || (index >= getNbTeams()))
			throw new IllegalCommandException("can not remove as team at given index");
		getTeamAt(index).removeWorld();
	}
		
	
	/**
	 * Variable registering the maximum allowed number of teams in a world
	 */
	private static final int maxAllowedNbOfTeams = 10;

	
	/**
	 * Variable registering a list containing all the teams in this world.
	 */
	private  List<Team> teams = new ArrayList<Team>();
		
// gameobjects
		
	/**
	 * Return a list of all gameobjects.
	 */
	@Basic
	public List<GameObjects> getAllGameObjects(){
		return this.gameobjects;
	}
	
	/**
	 * Checks whether a gameobject is valid.
	 * @param gameobject
	 * 		the gameobject to be checked
	 * @return true if and only if the gameobject is effective and not terminated
	 * 		|result ==
	 * 		|	(gameobject != null) && (! gameobject.isTerminated())
	 */
	public static boolean isValidGameObject(GameObjects gameobject){
		return (gameobject != null) && (!gameobject.isTerminated());		
	}
		
	/**
	 * Adds food to this world.
	 * @post food is added to this world at a random adjacent position in this world
	 * 		| new.getNbGameObjects() == this.getNbGameObjects() + 1
	 * 		| new.getAllFood().size() == this.getAllFood().size() + 1
	 * @throws IllegalCommandException
	 * 		The game has already started
	 * 		| start == true
	 * @throws IllegalPositionException
	 * 		No adjacent position could be generated
	 * 		| generateRandomAdjacentPosition() == null 
	 */
	public void addWormFood() throws IllegalCommandException, IllegalPositionException{
		if (start == true){
			throw new IllegalCommandException("Game has already started, Food cannot be added");
		}
		Position position = generateRandomAdjacentPosition(getFoodRadius());
		if (position == null)
			throw new IllegalPositionException(position);
		new Food(position, this);
			
	}
	
	
	/**
	 * Checks whether the given game object is already a game object in this world.
	 * @param object
	 * 		The game object to check.
	 * @return true if and only if the object is allready in this world and the object is not null
	 * 		| result ==
	 * 		|	((object !=null) 
	 * 		|	&& (for one obj in getAllGameObjects():
	 * 		|		obj == object))
	 */
	boolean hasAsGameObject(GameObjects object) {
		if (object == null){
			return false;
		}
		int index = 0;
		boolean found = false;
		while (index < getNbGameObjects()){
			if (getGameObjectAt(index) == object)
				return true;
			
			index = index + 1;
		}
		return found;
	}
	
	/**
	 * Remove a given gameobject from the list of gameobjects.
	 * @param gameobject
	 * 		the gameobject to be removed
	 * @post this world no longer reference the given game object as on of it's game objects
	 * 		| for each index in [0, 1, ...,new.getNbGameObjects()-1]:
	 * 		|	new.getGameObjectAt(index) != gameobject
	 * @throws IllegalArgumentException
	 * 		the given gameobject is not in this world's game objects
	 * 		| ! hasAsGameObject(gameobject)
	 */
	protected void removeGameObject(GameObjects gameobject) throws IllegalArgumentException{
		if (! hasAsGameObject(gameobject)){
			throw new IllegalArgumentException("gameobject not in this world's game objects");
		}
		gameobjects.remove(gameobject);
	}
		
	/**
	 * Adds a gameobject to the list of gameobjects.
	 * @param gameobject
	 * 		the gameobject to be added
	 * @post this new list of gameobjects will be expanded with the given gameobject
	 * 		| for each index in [0, 1, ..., this.getNbGameObjects()-1]:
	 * 		|	this.getGameObjectAt(index) == new.getGameObjectAt(index)
	 * 		| &&
	 * 		| new.getGameObjectAt(this.getNbGameObjects()) == gameobject
	 * @throws	IllegalArgumentException	
	 * 		the given gameobject is not a valid game object
	 * 		| (!isValidGameObject(gameobject))
	 * 		the given game object is already a game object in this world
	 * 		| hasAsGameObject(gameobject)
	 */
	protected void addGameObject(GameObjects gameobject) throws IllegalArgumentException{
		if (!isValidGameObject(gameobject))
			throw new IllegalArgumentException("not a valid gameobject");
		if (hasAsGameObject(gameobject)){
			throw new IllegalArgumentException("gameobject already a game object in this world");
		}
		gameobjects.add(gameobject);
	}
		
	
	/**
	 * Returns the game object that is situated at the given index.
	 * @param index
	 * 		the index at which the game object is requested
	 * @return the element at the index given 
	 *		|result ==
	 *		|	 teams.get(index)
	 * @throws IllegalArgumentException 
	 * 		the given index is not a valid index for the list of game objects
	 * 		| ! isValidGameObjectIndex(index)
	 */
	private GameObjects getGameObjectAt(int index) throws IllegalArgumentException{
		if (! isValidGameObjectIndex(index)){
			throw new IllegalArgumentException("not a valid game object index");
		}
		return gameobjects.get(index);
	}
	
	/**
	 * Return the number of game objects in this world.
	 * @return returns the length of the list teams
	 * 		| result ==
	 * 		|	 gameobjects.size()
	 */
	private  int getNbGameObjects(){
		return gameobjects.size();
	}
		
		
	/**
	 * Checks whether the given index is a valid index for the list of game objects.
	 * @param index
	 * 		The index to check
	 * @return true if and only if the index is non-negative and smaller than the number of game objects in this world.
	 * 		| result ==
	 * 		|  ((index >= 0) && (index < getNbGameObjects()))
	 */
	@Model
	private boolean isValidGameObjectIndex(int index) {
		return ((index >= 0) && (index < getNbGameObjects()));
	}

	/**
	 * Checks whether gameobjects contains a valid gameobject at the index given.
	 * @param index
	 * 		the index at which the gameobject is checked
	 * @return true if the gameobject on the given index is not null
	 * 		| (getGameObjectAt(index) != null)) && (!getGameObjectAt(index).isTerminated())
	 * 			and the team at the given index only appears once in the list
	 * 		| && for exactly one i in [0, ..., getNbGameObjects()]:
	 * 		| 		getGameObjectAt(i) == getGameObjectAt(index)
	 * @throws IllegalArgumentException 
	 * 		the given index is not a valid index for the list of game objects
	 * 		| ! isValidGameObjectIndex(index)
	 */
	private boolean canHaveAsGameObjectAt(int index) throws IllegalArgumentException{ 
		if (! isValidGameObjectIndex(index)){
			throw new IllegalArgumentException("not a valid game object index");
		}
		int i = 0;
		int GOFound = 0;
		while (i < getNbGameObjects()){
			if (getGameObjectAt(index) == getGameObjectAt(i)){
				GOFound = GOFound + 1;
			}
			i = i +1;
		}
		return ((getGameObjectAt(index) != null) && (!getGameObjectAt(index).isTerminated()) && (GOFound == 1));
	}
			
	/**
	 * checks if world has proper GameObjects attached to it
	 * @return true if and only if all the gameobjects are valid GameObjects and they are located at a valid position.
	 * 		|result == 
	 * 		| for each i in 1..getNbGameObjects:
	 * 		| 	((canHaveAsGameObjectAt(i)) && (isValidPosition(getGameObjectAt(i).getRadius(), getGameObjectAt(i).getPosition(),ateFood(getGameObjectAt(i))))
	 */
	private boolean hasProperGameObjects() throws IllegalArgumentException{
		for (int i = 0; i < getNbGameObjects();i++){
			boolean ateFood = ateFood(getGameObjectAt(i));
			if ((! canHaveAsGameObjectAt(i)) || (! isValidPosition(getGameObjectAt(i).getRadius(), getGameObjectAt(i).getPosition(),ateFood)))
				return false;
		}
		return true;
	}
	
	
	/**
	 * Checks whether the given game object ate food
	 * @param object
	 * 		The game objet to check
	 * @return true if and only if the object is an instance of the class Worm and the object overlaps with food.
	 * 		| result ==
	 * 		|	((object isinstanceof Worm) && ((worm)object.getOverlappingFood() != null))
	 */
	private boolean ateFood(GameObjects object){
		boolean ateFood = false;
		if (object instanceof Worm){
			Worm worm = (Worm) object;
			ateFood = (worm.getOverlappingFood() != null);
		}
		return ateFood;
	}
	
	/**
	 * Add a new gameobject to the list gameobjects.
	 * @param index
	 * 		the index at which the gameobject is to be added
	 * @param gameobject
	 * 		The game object to add to the list of this world game objects.
	 * @post the new list of game objects will now contain the given game object at the given index
	 * 		| new.getGameObjectAt(index) == gameobject 
	 * 		and all the game objects before the given index will stay in their place
	 * 		|&& for each i < index (and i>=0):
	 * 		|		this.getGameObjectAt(i) == new.getGameObjectAt(i)
	 * 		and all the game objects starting from the given index untill the end of the list will be moved one index up
	 * 		|&& for each i >= index (and i < new.getAllTeams().size())
	 * 		|		this.getGameObjectAt(i) == new.getGameObjectAt(i+1)
	 * @throws IllegalArgumentException		
	 * 		the given game object is not a valid game object
	 * 		|(!isValidGameObject(gameobject))
	 * @throws IllegalArgumentException		
	 * 		the given game object is already one of the game objects in this world
	 * 		|(hasAsGameObject(gameobject))
	 */
	private void addGameObjectAt(int index, GameObjects gameobject) throws IllegalArgumentException{
		if (!isValidGameObject(gameobject))
			throw new IllegalArgumentException("not a valid gameobject");
		if (this.hasAsGameObject(gameobject)){
			throw new IllegalArgumentException("gameobject already a gameobject in this world");
		}
		gameobjects.add(index, gameobject);
	}
		
	/**
	 * Removes the gameobject with the given index in the list from the list.
	 * @param index
	 * 		the index at which the gameobject is to be removed
	 * @post the new list of game objects will no longer contain the game object that was positioned at the given index
	 * 		| for each object in new.getAllGameObjects():
	 * 		|	 object != this.getGameObjectAt(index)
	 * 		and all the game objects before the given index will stay in their place
	 * 		|&& for each i < index (and i>=0):
	 * 		|		this.getGameObjectAt(i) == new.getGameObjectAt(i)
	 * 		and all the game objects starting from the given index untill the end of the list will be moved one index down
	 * 		|&& for each i > index (and i < new.getAllGameObjects().size())
	 * 		|		this.getGameObjectAt(i) == new.getGameObjectAt(i-1) 
	 * @throws IllegalArgumentException
	 * 		the given index is not a valid index for game object
	 * 		| ! isValidGameObjectIndex(index)
	 */
	private void removeGameObjectAt(int index) throws IllegalArgumentException{
		if (! this.isValidGameObjectIndex(index)){
			throw new IllegalArgumentException("invalid game object index");
		}
		gameobjects.remove(index);
	}
	
	/**
	 * A list referencing all the gameObjects in a world.
	 */
	private List<GameObjects> gameobjects = new ArrayList<GameObjects>();
	
// Worms
	
	/**
	 * Returns als the worms in this world.
	 * @return a list with all the worms of this world
	 * 		| result ==
	 * 		|	List<Worm>
	 * 		|	with for each object in List<Worm>:
	 * 		|		((object isinstanceof Worm) == true)
	 */
	public List<Worm>  getAllWorms(){
		List<Worm> worms = new ArrayList<Worm>();
		for (GameObjects gameobject: gameobjects){
			if (gameobject instanceof Worm)
				worms.add((Worm) gameobject);
			}
		return worms;
			
	}
	
	/**
	 * Returns a list of al the food this world contains.
	 * @return a list with all the food of this world
	 * 		| result ==
	 * 		|	List<Food>
	 * 		|	with for each object in List<Worm>:
	 * 		|		((object isinstanceof Food) == true)
	 */
	public List<Food> getAllFood(){
		List<Food> food = new ArrayList<Food>();
		for (GameObjects gameobject: gameobjects){
			if (gameobject instanceof Food)
				food.add((Food) gameobject);
			}
		
		return food;
	}
	
	/**
	 * Returns a list of the worm(s) that are currently winning.
	 * @return The worm with the highest HP if the number of teams is equal to 0
	 * 		| if (getNbTeams() == 0)
	 * 		|	then return getWormWithHighestHP()
	 * 			The winning team if there are still teams left
	 * 		| else
	 * 		|	return getWinningTeam()
	 */
	public List<Worm> getWinningWorms(){
		if (getNbTeams() == 0){
			return getWormWithHighestHP();
		}
		return getWinningTeam();
	}
	
	/**
	 * return all the names of the worms that won.
	 */
	public String getNameWinners(){
		String winners = "";
		for (Worm worm : getWinningWorms()){
			winners = winners + worm.getName() + ",";
			
		}
		return winners;
	}
	
	/**
	 * Adds a worm to this world.
	 * @post The number of worms is incremented by 1.
	 * 		| new.getNbWorms() == this.getNbWorms() + 1
	 * @effect The new game objects will contain the newly created worm, this worm will have a valid name, a radius of 0.25, a random position that is adjacent, ...
	 * 		| Worm(this,generateRandomPosition(0.25))
	 * @throws IllegalPositionException
	 * 		if there's no effective position generated
	 * 		| generateRandomAdjacentPosition(0.25) == null
	 */
	public void addWorm() throws IllegalCommandException, IllegalPositionException{	
		if (start == true){
			throw new IllegalCommandException("Game has already started, worms cannot be added");
		}
		Position position;
		position = generateRandomAdjacentPosition(0.25);
		if (position == null){
			throw new IllegalPositionException(position);
		}
		new Worm(this,position);
	}
	
	
	/**
	 * Returns the team with the most worms in it or if the teams all have only 1 worm, return the worm with the highest HP.
	 * @return the worm with the highest HP if there are no teams left with more than 1 worm
	 * 		| if for each team in getAllTeams():
	 * 		|	team.getNbWorms() < 2
	 * 		| || getAllTeams() == null 
	 * 		|	then return getWormWithHighestHP()
	 * 
	 * 		one of the teams with the most worms
	 * 		| if for one or more team in getAllTeams():
	 * 		|	team.getNbWorms() >= 2
	 * 		|	then return winningTeam.getAllWorms() for which winningTeam.getNbWorms() >= (each possibleWinningTeam in getAllTeams())
	 */
	private List<Worm> getWinningTeam() {
		Team winningTeam = getTeamAt(0);
		int index = 1;
		while (index < getNbTeams()){
			if (getTeamAt(index).getNbWorms() > winningTeam.getNbWorms()){
				winningTeam = getTeamAt(index);
			}
			index = index + 1;
		}
		if (winningTeam.getNbWorms() == 1){
			return getWormWithHighestHP();
		}
		return winningTeam.getAllWorms();
	}
	
	/**
	 * Returns the worm with the highest current hit points in this world.
	 * @return one of the worms with the highest HP
	 * 		| if for worm:  for each otherWorm in getAllWorms():
	 * 		|	worm.getCurrentHP().isGreaterThan(otherWorm.getCurrentHP()) 
	 * 		|	or worm.getCurrentHP().isEqualTo(otherWorm.getCurrentHP())
	 * 		|		then return worm
	 */
	private List<Worm> getWormWithHighestHP(){
		List<Worm> worms = getAllWorms();
		Worm wormWithHighestHP = worms.get(0);
		int index = 1;
		while (index < worms.size()){
			if (worms.get(index).getCurrentHP().isGreaterThan(wormWithHighestHP.getCurrentHP())){
				wormWithHighestHP = worms.get(index);
			}
			index = index +1;
		}
		List<Worm> result = new ArrayList<Worm>();
		result.add(wormWithHighestHP);
		return result;
	}
	
	/**
	 * Deletes the given worm from this world.
	 * @param worm
	 * 		The worm that has to be deleted.
	 * @effect the given worm will be removed as a game object of this world
	 * 		| removeGameObject(worm)
	 */
	private void removeWorm(Worm worm){
		this.removeGameObject(worm);
	}
	
	

	// termination
	/**
	 * Terminates the world.
	 * @post this world is terminated
	 * 		| new.isTerminated() == true
	 * @effect this world will no longer reference a team as one of it's teams
	 * 			this world's teams will no longer reference a world as it's world
	 * 			the teams will be terminated
	 * 		and this world will no longer reference a game object as one of it's game objects
	 * 			this world's game objects will no longer reference a world as it's world
	 * 			all the gameobject in this world will be terminated
	 * 		| for each team in this.getAllTeams():
	 * 		|	team.Terminate()
	 * 		| && for each gameobject in this.getAllGameObjects():
	 * 		|		gameobject.Terminate()
	 */
	private void terminate(){
		for(Team team:this.getAllTeams()){
			team.terminate();
		}
		for(GameObjects gameobject: this.getAllGameObjects()){
			gameobject.Terminate();		
		}
		this.isTerminated = true;
	}
	
	/**
	 * Checks whether this world is terminated.
	 */
	public boolean isTerminated(){
		return isTerminated;
	}
	
	/**
	 * Variable registering whether this world is terminated.
	 */
	private boolean isTerminated;
	
	
	/**
	 * Allowed error.
	 */
		private static final double EPS = Util.DEFAULT_EPSILON;
		
}
