package worms.model;

import worms.model.points.Points;
import worms.model.points.SortPoints;
/**
 * a class of bazookas, a special case of weapon
 * @author Cleemput Enrico en Van Buggenhout Niel
 * @version 1.0
 */
public class Bazooka extends Weapon {


	/**
	 * Intializes this new bazooka with the characteristics of a bazooka.
	 * @effect This new bazooka is initialized as a weapon, with it's specific characteristics
	 * 		| super(9.5,2.5,0.3,new Points(50,SortPoints.AP),new Points(80,SortPoints.HP));
	 */
	public Bazooka(){
		super(9.5,2.5,0.3,new Points(50,SortPoints.AP),new Points(80,SortPoints.HP));
		
	
	}
	
	
}



	