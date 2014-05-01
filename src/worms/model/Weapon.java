package worms.model;

import be.kuleuven.cs.som.annotate.*;
import worms.model.points.Points;

/**
 * a class of weapons that can shoot projectiles
 * @invar a weapon always exerts a force on a projectile that is not negative, it can have it's minimal and maximal force as it's min and max forces.
 * 		| canHaveAsForces(getMaxForce(),getMinForce())
 * @invar The mass of a weapon is a valid mass for a weapon
 * 		| isValidMass(getMass())
 * @invar the given points (both AP and HP) are valid points
 * 		| hasValidPoints()
 * @author Cleemput Enrico en Van Buggenhout Niel
 * @version 1.1
 */
public class Weapon {
	
	/**
	 * initializes a new weapon with given maximum force, minimum force, a mass, a cost in action points and a damage in hit points.
	 * @param maxForce
	 * 		the maximum force exerted by the weapon on the projectile
	 * @param minForce
	 * 		the minimum force exerted by the weapon on the projectile
	 * @param mass
	 * 		the mass of the projectile that the weapon shoots
	 * @param APCost
	 * 		the cost of action points for shooting the weapon
	 * @param HPdamage
	 * 		the damage the weapon can make
	 * @post this new weapon's maximal force will equal the given maximal force
	 * 		| new.getMaxForce() == maxForce
	 * 		 this new weapon's minimal force will equal the given minimal force
	 * 		| new.getMinForce() == minForce
	 * 		 this new weapon's mass will equal the given mass
	 * 		| new.getMass() == mass
	 * 		 this new weapon's cost of action points will equal the given cost of action points
	 * 		| new.getAPCost() == APCost
	 * 		 this new weapon's damage in hit points will equal the given damage in hit points
	 * 		| new.getHPDamage() == HPDamage
	 * @effect this new weapon's radius will equal the calculated radius
	 * 		| setRadius()
	 * 
	 * @throws IllegalArgumentException
	 * 		if the forces given are not valid an exception is thrown
	 * 		|!isValidForce(maxForce,minForce)
	 * @throws IllegalArgumentException
	 * 		if the mass is not valid an exception is thrown
	 * 		|!isValidMass(mass)
	 */
	@Raw
	public Weapon(double maxForce,double minForce,double mass,Points APCost,Points HPdamage) throws IllegalArgumentException{
		if (!canHaveAsForces(maxForce,minForce))
			throw new IllegalArgumentException("no valid forces");
		if (!isValidMass(mass))
			throw new IllegalArgumentException("not a valid mass");
		this.maxForce = maxForce;
		this.minForce = minForce;
		this.mass = mass;
		this.setRadius();
		this.APCost = APCost;
		this.HPdamage = HPdamage;
	
	}
	

// terminate
	
	/**
	 * check whether this weapon is terminated
	 */
	@Basic
	public boolean isTerminated(){
		return this.terminated;
	}
	
	/**
	 * terminates the weapon
	 * @effect the weapon will be removed from the worms arsenal
	 * 		|this.removeWorm
	 * @effect the weapon will be terminated
	 * 		|this.isTerminated = true;
	 * 
	 */
	protected void Terminate(){
		this.terminated = true;
		
	}
	
	
	/**
	 * a variable registering the state of the current projectile
	 */
	protected boolean terminated = false;
	


	
// projectile properties
	
	
	
	/**
	 * returns the maximum amount of force exerted on a projectile that is launched from this weapon
	 */
	@Basic @Immutable
	public double getMaxForce(){
		return this.maxForce;
	}
	

	/**
	 * returns the minimum amount of force exerted on a projectile that is launched from this weapon
	 */
	@Basic @Immutable
	public double getMinForce(){
		return this.minForce;
	}
	
	/**
	 * checks if the given forces are valid
	 * @param MaxForce
	 * 		the maximal force exerted on the projectile by this weapon
	 * @param MinForce
	 * 		the minimal force exerted on the projectile by this weapon
	 * @return
	 * 		true if and only if the minimal force is greater or equal to 0
	 * 		and the maximal force is greater than or equal to the minimal force
	 * 		|result ==
	 * 		|	 ((MaxForce >= MinForce) && (MinForce >= 0))
	 */
	@Raw
	public static boolean canHaveAsForces(double MaxForce,double MinForce){
		return ((MaxForce >= MinForce) && (MinForce >= 0));
		
	}
	
	/**
	 * the maximum force this weapon can exert on projectiles
	 */
	private final double maxForce;
	
	
	/**
	 * the minimum force this weapon can exert 
	 */
	private final double minForce;
	
	
	
	
// APCost	
	
	/**
	 * returns the amount of action points it costs to shoot this weapon
	 */
	@Basic @Immutable
	public Points getAPCost(){
		return this.APCost;
	}
	
	/**
	 * Checks whether the given points are valid points
	 * @return The numerals of the APCost and HPDamage of this weapon are valid numerals.
	 * 		| (Points.isValidNumeral(this.getAPCost().getNumeral()) && Points.isValidNumeral(this.getHPDamage().getNumeral()))
	 */
	public boolean hasValidPoints(){
		return (Points.isValidNumeral(this.getAPCost().getNumeral()) && Points.isValidNumeral(this.getHPDamage().getNumeral()));
	}
		
	/**
	 * the amount of action points it costs to shoot this weapon
	 */
	private final Points APCost ;
	
	
//	HP damage
	
	
	/**
	 * return the HP damage this weapon does to a worm
	 */
	@Basic @Immutable
	public Points getHPDamage(){
		return this.HPdamage;
	}
	
	/**
	 * the HP damage this weapon does to a worm
	 */
	private final Points HPdamage;
	
	

// mass
	
	/**
	 * return the mass of the projectile launched from this weapon
	 */
	@Basic @Immutable
	public double getMass(){
		return this.mass;
	}
	
	/**
	 * checks if the given mass is valid
	 * @param mass
	 * 		the mass to be checked
	 * @return
	 * 		the mass has to be larget than 0
	 * 		|result = (mass > 0)
	 */
	@Raw
	public static boolean isValidMass(double mass){
		return (mass > 0);
	}
		
	/**
	 * the mass of the projectile launched from this weapon
	 */
	private final double mass;
	
	
// Radius
	
	/**
	 * calculates the radius of the projectile fired from this weapon from a given mass
	 * @param mass
	 * 		the mass from which the radius is derrived
	 * @param q
	 * 		the density of the projectile fired from the weapon
	 * @return The radius compute out of the given mass, the constant density q and the formula for a sphere.
	 * 		|result = Math.pow((3*mass)/(4*q*Math.PI), 1/3);
	 * @throws IllegalArgumentException
	 * 		if the mass is not valid
	 * 		| !isValidMass(mass)
	 */
	@Raw
	public  static double calculateRadius(double mass,double q) throws IllegalArgumentException{
		if (!isValidMass(mass))
			throw new IllegalArgumentException("not a valid mass");
		return Math.pow((3*mass)/(4*q*Math.PI), 1.0/3);
	}
	
	@Immutable
	public double getRadius(){
		return this.radius;	
	}
	
	/**
	 * Sets the radius of this weapon to the appropriate radius according to this weapon's mass and density.
	 * @effect the new radius of this weapon equals the calculated radius of this weapon
	 * 		| new.getRadius() == this.calculateRadius(getMass(),getQ())
	 */
	@Raw
	private void setRadius(){
		this.radius = calculateRadius(getMass(),getQ());
	}
	
	/**
	 * Variable referencing the radius of this weapon
	 */
	private double radius;
	
	/**
	 * returns the density of the projectile fired from a weapon
	 */
	@Basic @Immutable
	public static double getQ(){
		return q;
	}
	
	/**
	 * the density of the projectile coming form the bazooka
	 */
	private static final double q = 7800;
	
}




