package worms.model;

import worms.model.points.Points;
import worms.model.points.SortPoints;

/**
 * a class of rifles, a special case of weapon
 * @author Cleemput Enrico en Van Buggenhout Niel
 * @version 1.0
 */
public class Rifle extends Weapon {
	/**
	 * Intializes this new rifle with the characteristics of a rifle.
	 * @effect This new rifle is initialized as a weapon, with it's specific characteristics
	 * 		| super(1.5,1.5,0.01,new Points(10,SortPoints.AP),new Points(20,SortPoints.HP))

	 */
	public Rifle(){
		super(1.5,1.5,0.01,new Points(10,SortPoints.AP),new Points(20,SortPoints.HP));
	}
	
}

