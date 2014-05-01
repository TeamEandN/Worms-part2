package worms.model;
import java.util.ArrayList;
import java.util.List;

import be.kuleuven.cs.som.annotate.*;
import worms.exceptions.*;




/**
 * A class involving teams that belong to a world and have worms in it.
 * @invar This team has proper worms as it's worms
 * 		|this.hasProperWorms() 
 * @invar This team has a valid world as it's world
 * 		|isValidWorld(this.getWorld())
 * @author Cleemput Enrico en Van Buggenhout Niel
 * @version 1.1	
 */

public class Team{
	
	/**
	 * Initialize this new team with a name and a world.
	 * @param name
	 * 		The name for this new team
	 * @param world
	 * 		The world of this new team
	 * @effect The team's name equals the given name
	 * 		| setName(name)
	 * @effect This team is added to the given world
	 * 		| addToWorld(world)
	 */
	public Team(String name, World world) throws IllegalNameException, IllegalArgumentException{
		this.setName(name);
		this.addToWorld(world);
		
		
	}
	/**
	 * Initialize this new team with a world and a defaultname.
	 * @param world
	 * 		The world of this new team
	 * @effect The team's name equals the default name for teams
	 * 		| setName("DefaultTeam")
	 * @effect This team is added to the given world
	 * 		| addToWorld(world)
	 */
	public Team(World world){
		
		this("DefaultTeam",world);
	}
	
	
	/**
	 * Returns the name of the team.
	 */
	@Basic
	public String getName(){
		return this.name;
	}
	
	/** 
	 *  Checks whether the given string is a valid name.
	 * @param name
	 * 		the string that needs to be checked
	 * @return
	 * 		false if the length of name is smaller than 2 
	 * 		| result == (name.lenght() >=2)
	 * 
	 * 		false if not each character is either an upper/lowercase letter 
	 * 		|for each character in name:
	 * 		| ((!Character.isLetter(character)) 
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
			if ((Character.isLetter(c))){
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
	 * Sets the name of the team.
	 * @param Name
	 * 		the name to be checked
	 * @post the name of this team equals the given name
	 * 		| new.getName() == Name
	 * @throws IllegalNameException
	 * 		the given name is not a valid name for a team
	 *		| ! isValidName(Name)
	 * 
	 */
	@Raw
	private void setName(String Name) throws IllegalNameException{
		if (!isValidName(Name))
			throw new IllegalNameException(Name);
		this.name = Name;
	}

	
	/**
	 * 	The variable registering the name of this team.
	 */
	private String name;
	
	
	/**
	 * return all the worms in this team
	 */
	@Basic
	public List<Worm> getAllWorms() {
		return new ArrayList<Worm>(worms);
	}
		
	
	/**
	 * Returns the number of worms in this team
	 * @return The length of the list worms
	 * 		| result == worms.size()
	 */
	public int getNbWorms(){
		return worms.size();
	}
	
		
	
	/**
	 * Returns the worm that is situated on the given index.
	 * @param index
	 * 		the index at which the worm is to be found in the list
	 * @return returns the worm at the index given 
	 *		|result == worms.get(index)
	 * @throws IllegalArgumentException
	 * 		if the given index is not a valid index for the list of worms of this team
	 * 		| ((index<0) || (index >= getNbWorms()))
	 */
	public Worm getWormAt(int index) throws IllegalArgumentException{
		if ((index<0) || (index >= getNbWorms())){
			throw new IllegalArgumentException("invalid index");
		}
		return worms.get(index);
	}
	
	/**
	 * Return the index of the given worm.
	 * @param worm
	 * 		the worm for which the index should be searched
	 * @return if the given worm is in this team's list of worms, return the index at which the given worm is located.
	 * 		| if for one index in [0, ..., getNbworms() - 1]:
	 * 		|	worm == getWormAt(index)
	 * 		| 	then return index
	 * 			if the given worm is not in this team's list of worms, -1 will be returned
	 * 		| if for each index in [0, ..., getNbworms() - 1]:
	 * 		|	team != getwormAt(index)
	 * 		|	then return -1
	 */
	public int getIndexInTeam(Worm worm) {
		for (int i=0;i < this.getNbWorms();i++){
			if (this.getWormAt(i)==worm)
				return i;
		}
		return -1;
	
	}
	
	/**
	 * Checks whether this team contains a valid worm at the given index.
	 * @param index
	 * 		the index of the worm that has to be checked
	 * @return true when the worm is valid worm and the worm at the given index only appears once in the list of worms
	 * 		| result ==
	 * 		|	 (((getWormAt(index) != null) && (!getWormAt(index).isTerminated()))
	 * 		|	&& (for each i in [0, ..., getNbWorms() - 1] and i != index:
	 * 		|		getWormAt(i) != getWormAt(index)))
	 * @throws IllegalArgumentException
	 * 		if the given index is invalid for this team
	 * 		| ! isValidTeamIndex
	 */	
	public boolean canHaveAsWormAt(int index) throws IllegalArgumentException{
		if (! isValidTeamIndex(index)){
			throw new IllegalArgumentException("no worm at that index");
		}
		int i = 0;
		int wormFound = 0;
		while (i < (getNbWorms())){
			if (getWormAt(i) == getWormAt(index)){
				wormFound = wormFound + 1;
			}
			i = i + 1;
		}
		return  ( (isValidWorm(getWormAt(index))) && (wormFound == 1) );
	}
	/**
	 * Checks whether this team has proper worms attached to it.
	 * @return returns true if all the worms are valid worms
	 * 		|result = 
	 * 		| for each i in 1..getNbWorms
	 * 		| (!canHaveAsWormAt(i)) && ((!(getWormAt(i).getTeam() == this))
	 */
	public boolean hasProperWorms(){
		for (int i = 0; i < getNbWorms();i++){
			if (! canHaveAsWormAt(i))
				return false;
			if (! (getWormAt(i).getTeam() == this))
				return false;
		}
		return true;
				
	}
	
	/**
	 * Checks whether the given index is a valid index for a worm in this team.
	 * @param index
	 * 		The index to check
	 * @return true if and only if the index is non-negative and the index doesn't exceed the amount of worms in this team.
	 * 		| result ==
	 * 		|	((index < 0) && (index >= getNbWorms()))
	 */
	public boolean isValidTeamIndex(int index) {
		return ((index < 0) && (index >= getNbWorms()));
	}
	

	/**
	 * Checks whether the given worm is already a worm in this team.
	 * @param worm
	 * 		The worm to check.
	 * @return true if and only if the worm is already in this team and not null
	 * 		| result ==
	 * 		|	((worm !=null) 
	 * 		|	&& (for one W in getAllWorms():
	 * 		|		W == worm))
	 */
	boolean hasAsWorm(Worm worm) {
		if (worm == null){
			return false;
		}
		int index = 0;
		boolean found = false;
		while (index < getNbWorms()){
			if (getWormAt(index) == worm)
				return true;
			
			index = index + 1;
		}
		return found;
	}
	
	/**
	 * Add a new worm to the list of worms.
	 * @param index
	 * 		the index at which the worm is to be added
	 * @param worm
	 * 		the worm to be added at the given index
	 * @throws	IllegalArgumentException
	 * 		|(worm.getTeam() != this)
	 * 		
	 */
	public void addWormAt(int index,Worm worm) throws IllegalArgumentException{
		if (hasAsWorm(worm))
			throw new IllegalArgumentException("worm already in this team");
		worms.add(index, worm);
	}
	
	/**
	 * Removes a worm from the list.
	 * @param index
	 * 		the index at which the worm is to be removed
	 * @post the new list of worms will no longer contain the worm that was positioned at the given index
	 * 		| for each worm in new.getAllWorms():
	 * 		|	 worm != this.getWormAt(index)
	 * 		and all the worms before the given index will stay in their place
	 * 		|&& for each i < index (and i>=0):
	 * 		|		this.getWormAt(i) == new.getWormAt(i)
	 * 		and all the worms starting from the given index until the end of the list will be moved one index down
	 * 		|&& for each i > index (and i < new.getAllWorms().size())
	 * 		|		this.getWormAt(i) == new.getWormAt(i-1)
	 * @throws IllegalArgumentException
	 * 		if the given index is invalid for this team
	 * 		| ! isValidTeamIndex
	 */
	public void removeWormAt(int index){
		if (! isValidTeamIndex(index)){
			throw new IllegalArgumentException("invalid index");
		}
		worms.remove(index);
	}
	
	/**
	 * Remove a given worm from the list of worms.
	 * @param worm
	 * 		the worm to be removed
	 * @post The given team will no longer be in the list of teams
	 * 		| for each index in [0, new.getNbTeams - 1]
	 * 		|	new.getTeamAt(index) != team
	 * @throws IllegalArgumentException
	 * 		if the given worm does not have this team as it's team
	 * 		| worm.getTeam() != this
	 */
	public void removeWorm(Worm worm) throws IllegalArgumentException{
		if (! hasAsWorm(worm))
			throw new IllegalArgumentException("worm not in this team");
		worms.remove(worm);
		
	}
	
	/**
	 * adds a worm to the list of worms
	 * @param worm
	 * 		the worm to be added
	 * @post this new list of worms will be expanded with the given worm
	 * 		| for each index in [0, 1, ..., this.getNbWorms()-1]:
	 * 		|	this.getWormAt(index) == new.getWormAt(index)
	 * 		| &&
	 * 		| new.getWormAt(this.getNbWorms()) == worm
	 * @effect the given worm's team has this team as it's team
	 * 		| worm.setTeam(this)
	 * @throws	IllegalArgumentException	
	 * 		the given worm is not a valid worm
	 * 		| (!isValidWorm(worm))
	 * 		the given worm is already a worm in this world
	 * 		| hasAsWorm(worm)
	 */
	public void addWorm(Worm worm) throws IllegalArgumentException{
		if ((! isValidWorm(worm)) || (hasAsWorm(worm)))
			throw new IllegalArgumentException("invalid Worm");
		worm.setTeam(this);
		worms.add(worm);
	}	
	
	/**
	 * Checks whether a worm is valid.	
	 * @param worm
	 * 		the worm to be checked
	 * @return true if and only if the worm is effective and not terminated
	 * 		|result ==
	 * 		|	((!worm.isTerminated()) && (worm != null))
	 */
	public static boolean isValidWorm(Worm worm){
		return  ((worm != null) && (!worm.isTerminated()));
	}
	
	/**
	 * a list referencing all the worms in a worm	
	 */
	private final List<Worm> worms = new ArrayList<Worm>();

	// world


	/**
	 * Return the world which this team references. 
	 */
	@Basic
	public World getWorld(){
		return this.world;
	}
	
	/**
	 * Check whether the given world is a valid world.
	 * @param world
	 * 		The world to check
	 * @return true if and only if the given world is effective and not terminated
	 * 		| result ==
	 * 		|	((world != null) && (!world.isTerminated()))
	 */
	@Raw
	public static boolean isValidWorld(World world){
		return ((world != null) && (!world.isTerminated()));
	}
	
	
	
	
	
	/**
	 * Adds a world to the team and adds the team to the world.
	 * @param world
	 * 		the world in which the team has to be added
	 * @post The new world this team references is the given world
	 * 		| new.getWorld() == world
	 * @effect The given world references this team as one of it's teams
	 * 		| world.addAsteam(this)
	 * @throws IllegalCommandException
	 * 		The given world already references this team as one of it's teams.
	 * 		| world.inTeams(this)
	 */
	@Raw
	public void addToWorld(World world) throws IllegalArgumentException, IllegalCommandException{
		if (hasWorld()){
			throw new IllegalCommandException("team already has a world");
		}
		this.setWorld(world);
		world.addAsTeam(this);
	}

	
	/**
	 * Remove world from team and team form world.
	 * @effect this team's world no longer references this team as on of it's teams
	 * 			this new team no longer references a world as it's world
	 * 		| this.getWorld().removeAsteam(this)
	 * @throws IllegalCommandException
	 * 		The team still has worms in it.
	 * 		| (team.getNbWorms() != 0))
	 * 
	 */
	public void removeWorld(){
		World formerWorld = this.getWorld();
		if (this.getNbWorms() != 0){
			throw new IllegalCommandException("Team still contains worms");
		}
	
		this.world = null;
		formerWorld.removeAsTeam(this);
	}

	/**
	 * Checks whether this team already has a world.
	 * @return true if and only if this team's world equals null
	 * 		| result ==
	 * 		|	getWorld() != null 
	 */
	@Raw @Model
	private boolean hasWorld() {
		return (this.getWorld() != null);
	}
	
	/**
	 * Set the world to which this team belongs.
	 * @param world
	 * 		The world to which to set this team's world
	 * @post This team references the given world as it's world.
	 * 		| new.getWorld() == world
	 * @throws IllegalArgumentException
	 * 		given world is not a valid world
	 * 		| ! isValidWorld(world)
	 */
	@Raw
	public void setWorld(World world) throws IllegalArgumentException{
		if (!isValidWorld(world))
			throw new IllegalArgumentException("not a valid world");
		this.world = world;
	}
	
	/**
	 * The world the team references (belongs to).
	 */
	private World world;
	
//termination



	
	/**
	 * Checks whether a team is terminated.
	 */
	public boolean isTerminated(){
		return this.isTerminated;
	}
	
	/**
	 * Terminates this team
	 * @effect all the worms referencing this team will no longer reference a team as it's team
	 * 			this new team will no longer reference a worm as it's worms
	 * 		|for each worm in this.getAllWorms():
	 * 		|	worm.removeTeam()
	 * @effect this team's world no longer references this team as on of it's teams
	 * 			this new team no longer references a world as it's world
	 * 		|this.removeWorld()
	 * @effect this new team is terminated
	 * 		|this.isTerminated = true
	 */
	public void terminate(){
		if (! this.isTerminated()){
			for (Worm worm: this.getAllWorms())
				worm.removeTeam();
		}
		this.removeWorld();
		this.isTerminated = true;
	}

	/**
	 * A variable registering whether this team is terminated.
	 */
	private boolean isTerminated = false;
	

}
