package worms.model.test;


import static org.junit.Assert.*;

import java.util.Random;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import worms.model.Bazooka;
import worms.model.Team;
import worms.model.Weapon;
import worms.model.World;
import worms.model.Worm;
import worms.model.points.Points;
import worms.model.points.SortPoints;
import worms.model.position.Position;
import worms.exceptions.*;

/**
 * 
 * @author van Cleemput Enrico en Van Buggenhout Niel
 * @version 1.2
 *
 */
public class WormTest {
	
	public static double EPS = 5*Math.pow(10, -16);
	private World world;
	private Worm myWorm_worm_1_x2y2_PI;
	private Worm myWorm_worm_1_x2y2_PIop2;
	private Worm myWorm_worm_1_x2y2_minPIop2;
	private boolean[][] passableMap = new boolean[][] {
			{ false, false, false, false }, { true, true, true, true },
			{ true, true, true, true }, { false, false, false, false } };

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}
	
	
	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	@Before
	public void setUp() throws Exception {
		world = new World(4.0, 4.0, passableMap, new Random(7357));
		myWorm_worm_1_x2y2_PI = new Worm("Worm",1,2,2,Math.PI,world);
		myWorm_worm_1_x2y2_PIop2 = new Worm("Worm",1,2,2,Math.PI/2,world);
		myWorm_worm_1_x2y2_minPIop2 = new Worm("Worm",1,2,2,-Math.PI/2,world);
		
		
		
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testConstructorLegalCase(){
		Worm wormpje = new Worm("Wormpje",2,4,5,0,world);
		assertEquals(wormpje.getName(),"Wormpje");
		assertEquals(0.25,wormpje.getLowerBoundRadius(),EPS);
		assertEquals(2,wormpje.getRadius(),EPS);
		assertEquals(4,wormpje.getX(),EPS);
		assertEquals(5,wormpje.getY(),EPS);
		assertEquals(0,wormpje.getDirection(),EPS);	
		assertEquals(11328*Math.PI,wormpje.getMass(),Math.pow(1, -10));
		}
	
	
	
	@Test(expected = IllegalNameException.class)
	public void testConstructorIllegalNameCase() throws IllegalNameException{
		new Worm("Worm1",1,1,1,2,world);
		
	}
	@Test(expected = IllegalRadiusException.class)
	public void testConstructorIllegalRadiusCase_SmallerThanLowerBound() throws IllegalRadiusException{
		new Worm("Worm",-1,1,1,2,world);
	}
	
	@Test(expected = IllegalRadiusException.class)
	public void testConstructorIllegalRadiusCase_Infinity() throws IllegalRadiusException{
		new Worm("Worm",Double.POSITIVE_INFINITY,1,1,2,world);
	}
	
		
	@Test
	public void testCalculateMass(){
		assertEquals(1416*Math.PI,myWorm_worm_1_x2y2_PI.getMass(),Math.pow(1,-10));
	}
	@Test
	public void testCalculateMass_rand(){
		assertEquals(1416*Math.PI*.25*.25*.25,myWorm_worm_1_x2y2_PI.calculateMass(0.25,0.25),Math.pow(1,-10));
		
	}
	
	/*@Test 
	public void testCanHaveAsX_TrueCase(){
		Worm.setMaxX(10);
		assertTrue(myWorm_worm_1_x2y2_PI.canHaveAsX(5));
		
	}
	
	@Test
	public void testCanHaveAsY_TrueCase(){
		Worm.setMaxY(10);
		assertTrue(myWorm_worm_1_x2y2_PI.canHaveAsY(5));
	}*/
	
//	@Test
//	public void testChangePosition_xPosition(){
//		myWorm_worm_1_x2y2_PI.changePosition(3, 3);
//		assertEquals(5,myWorm_worm_1_x2y2_PI.getX(),EPS);
//		
//	}
//	@Test
//	public void testChangePosition_yPosition(){
//		myWorm_worm_1_x2y2_PI.changePosition(3, 4);
//		assertEquals(6,myWorm_worm_1_x2y2_PI.getY(),EPS);
//	}
	@Test
	public void testDecreaseRadiusBy_TrueCase(){
		myWorm_worm_1_x2y2_PI.increaseRadiusBy(0.25);
		assertEquals(myWorm_worm_1_x2y2_PI.getRadius(),0.75,Math.pow(1,-10));
	}
	
	@Test (expected=IllegalRadiusException.class)
	public void testDecreaseRadiusBy_IllegalRadiusCase()throws IllegalRadiusException{
		myWorm_worm_1_x2y2_PI.increaseRadiusBy(0.8);
	}
	
	@Test(expected=IllegalRadiusException.class)
	public void testDecreaseRadiusByException() throws IllegalRadiusException{
		myWorm_worm_1_x2y2_PI.increaseRadiusBy(-0.25);
	}
	

	@Test
	public void testMove_enoughAP_x_dirPI(){
		//direction = PI
		//x-waarde na bewegen
		//genoeg AP
		myWorm_worm_1_x2y2_PI.step(0.0001);
		assertEquals(1,myWorm_worm_1_x2y2_PI.getX(),Math.pow(1,-10));
		
	}
	@Test
	public void testMove_enoughAP_y_dirPI(){
		//direction = PI
		//y-waarde na bewegen
		//genoeg AP
		myWorm_worm_1_x2y2_PI.step(0.0001);
		assertEquals(2,myWorm_worm_1_x2y2_PI.getY(),Math.pow(1,-10));
	}
		
	@Test
	public void testMove_enoughAP_y_dirPiOp2(){
		//direcion = PI/2
		//y-waarde na bewegen
		// genoeg AP
		myWorm_worm_1_x2y2_PIop2.step(0.0001);
		assertEquals(3,myWorm_worm_1_x2y2_PIop2.getY(),Math.pow(1,-10));
	}
		
	@Test
	public void testMove_enoughAP_x_dirPiOp2(){
		//direction = PI/2
		//x-waarde na bewegen
		//genoeg AP
		myWorm_worm_1_x2y2_PIop2.step(0.0001);
		assertEquals(2,myWorm_worm_1_x2y2_PIop2.getX(),Math.pow(1,-10));
	}
	@Test
	public void testMove_enoughAP_x_dirminPiOp2(){
		//direction = -PI/2
		//x-waarde na bewegen
		myWorm_worm_1_x2y2_minPIop2.step(0.0001);
		assertEquals(2,myWorm_worm_1_x2y2_minPIop2.getX(),Math.pow(1,-10));
	}
	@Test
	public void testMove_enoughAP_y_dirminPiOp2(){
		//direction = -PI/2
		//y-waarde na bewegen
		myWorm_worm_1_x2y2_minPIop2.step(0.0001);
		assertEquals(2,myWorm_worm_1_x2y2_minPIop2.getY(),Math.pow(1,-10));
	}
	
	
	@Test
	public void testMove_TerrainAngle_Piop2(){
		myWorm_worm_1_x2y2_PI.step(0.0001);
		assertEquals(2,myWorm_worm_1_x2y2_PI.getX(),Math.pow(1,-10));
		assertEquals(3,myWorm_worm_1_x2y2_PI.getY(),Math.pow(1,-10));
	}	
	
	
	@Test
	public void testTurn_Piop2DirectionMinPiOp2(){
		myWorm_worm_1_x2y2_minPIop2.turn(Math.PI/2);
		assertEquals(0,myWorm_worm_1_x2y2_minPIop2.getDirection(),Math.pow(1,-10));
	}
	

	
	@Test 
	public void testCostOfTurn(){
		assertEquals(30,Worm.costOfTurn(Math.PI).getNumeral());
	}
	@Test
	public void testCostOfStep_TerrainAngle_0(){
		assertEquals(1,Worm.costOfStep(0).getNumeral());
		
	}
	@Test
	public void testCostOfStep_TerrainAngle_PiOp4(){
		
		assertEquals(4,Worm.costOfStep(Math.PI/4).getNumeral());
	}
	
	@Test
	public void testCalculateVelocity(){
		assertEquals(7.403608691437949,myWorm_worm_1_x2y2_PI.calculateVelocity(),Math.pow(1,-10));
	}
	
	@Test
	public void testJump(){
		myWorm_worm_1_x2y2_PI.jump(0.0001);
		assertEquals(2,myWorm_worm_1_x2y2_PI.getX(),Math.pow(1,-10));
		assertEquals(2,myWorm_worm_1_x2y2_PI.getY(),Math.pow(1,-10));
	}
	@Test
	public void testJump2(){
		myWorm_worm_1_x2y2_PIop2.jump(0.0001);
		assertEquals(2,myWorm_worm_1_x2y2_PI.getX(),Math.pow(1,-10));
		assertEquals(2,myWorm_worm_1_x2y2_PI.getY(),Math.pow(1,-10));
		
	}
	@Test 
	public void testJumpMinPiOp2(){
		myWorm_worm_1_x2y2_minPIop2.jump(0.0001);
		assertEquals(2,myWorm_worm_1_x2y2_minPIop2.getX(),Math.pow(1,-10));
		assertEquals(2,myWorm_worm_1_x2y2_minPIop2.getY(),Math.pow(1,-10));
		
		
	}
//	@Test
//	public void testJumpStep_Pi(){
//		assertEquals(2,myWorm_worm_1_x2y2_PI.jumpStep(0)[0],Math.pow(1,-10));
//		assertEquals(2,myWorm_worm_1_x2y2_PI.jumpStep(0)[1],0.001);
//		
//	}
//	@Test
//	public void testJumpStep_PiOP2(){
//		
//		assertEquals(2,myWorm_worm_1_x2y2_PIop2.jumpStep(1)[0],Math.pow(1,-10));
//		assertEquals(4.500,myWorm_worm_1_x2y2_PIop2.jumpStep(1)[1],0.001);
//	}
	@Test
	public void testStep_Pi(){
		myWorm_worm_1_x2y2_PI.step(myWorm_worm_1_x2y2_PI.getTerrainAngle());
		assertEquals(1,myWorm_worm_1_x2y2_PI.getX(),Math.pow(1,-10));
		
	}
	@Test
	public void testStep_Piop2(){
		myWorm_worm_1_x2y2_PIop2.step(myWorm_worm_1_x2y2_PIop2.getTerrainAngle());
		assertEquals(3,myWorm_worm_1_x2y2_PIop2.getY(),Math.pow(1,-10));
		
	}
	
	
	@Test
	public void testIsValidDirection_trueCase(){
		assertTrue(Worm.isValidDirection(Math.PI));
	}
	@Test
	public void testIsValidDirection_FalseCase(){
		assertFalse(Worm.isValidDirection(3*Math.PI));
	}
	@Test
	public void testIsValidMass_trueCase(){
		assertTrue(Worm.isValidMass(100));
		
	}
	@Test
	public void testIsValidMass_FalseCase(){
		assertFalse(Worm.isValidMass(-10));
	}
	@Test
	public void testIsValidName_TrueCase(){
		assertTrue(Worm.isValidName("Worm ' "));
	}
	@Test
	public void testIsValidName_FalseCase(){
		assertFalse(Worm.isValidName("worm 12"));
	}
	@Test
	public void testIsValidName_TrueCase_james(){
		assertTrue(Worm.isValidName("James o' Hara'"));
	}
	
	@Test
	public void testIsValidStep_TrueCase(){
		assertTrue(Worm.isValidStep(12));
		
	}
	
	@Test
	public void testIsValidStep_FalseCase(){
		assertFalse(Worm.isValidStep(-10));
	}
		
	@Test
	public void testReduceAngle_PositiveInteger(){
		assertEquals(Worm.reduceAngle(Math.PI*3),Math.PI,Math.pow(1,-10));
	}
	@Test
	public void testReduceAngle_NegativeInteger(){
		assertEquals(Worm.reduceAngle(-Math.PI*3),-Math.PI,Math.pow(1,-10));
	}

	@Test
	public void testIsValidJumpTime_TrueCase(){
		assertTrue(myWorm_worm_1_x2y2_PI.isValidJumpTime(0));
		
	}
	@Test
	public void testIsValidJumpTime_falseCase(){
		assertFalse(myWorm_worm_1_x2y2_PI.isValidJumpTime(-20));
	}
	@Test
	public void testIsValidJumpTime_falseCase2(){
		assertFalse(myWorm_worm_1_x2y2_PIop2.isValidJumpTime(-20));
	}
	@Test
	public void testIsValidJumpTime_TrueCase2(){
		assertTrue(myWorm_worm_1_x2y2_PIop2.isValidJumpTime(1));
		
	}

	
	@Test
	public void testSelectNextWeapon(){
		myWorm_worm_1_x2y2_PIop2.selectNextWeapon();
		assertEquals(1,myWorm_worm_1_x2y2_PIop2.getActiveWeaponIndex());
		myWorm_worm_1_x2y2_PIop2.selectNextWeapon();
		assertEquals(0,myWorm_worm_1_x2y2_PIop2.getActiveWeaponIndex());
	}
	@Test 
	public void testTerminate(){
		myWorm_worm_1_x2y2_PIop2.terminate();
		assertTrue(myWorm_worm_1_x2y2_PIop2.isTerminated());
		assertEquals(null, myWorm_worm_1_x2y2_PIop2.getWorld());
	}
	@Test 
	public void testCalculateForce(){
		assertEquals(5*myWorm_worm_1_x2y2_PIop2.getCurrentAP().getNumeral() + myWorm_worm_1_x2y2_PIop2.getMass()*9.80665
				,myWorm_worm_1_x2y2_PIop2.calculateForce(),Math.pow(10, -6));
		
	}
	
	@Test 
	public void testCanFall(){
		Worm worm = new Worm(world,new Position(2,2));
		assertTrue(worm.canFall());
		
	}
	
	@Test 
	public void testCanMove_TrueCase(){
		assertTrue(myWorm_worm_1_x2y2_PIop2.canMove(Math.PI-0.2));
	}
	@Test 
	public void testCanMove_falseCase(){
		assertFalse(myWorm_worm_1_x2y2_PIop2.canMove(-Math.PI/2));
	}
	
	@Test
	public void testDecreaseCurrentHP(){
		myWorm_worm_1_x2y2_PIop2.decreaseCurrentHP(new Points(48,SortPoints.HP));
		assertEquals(4400,myWorm_worm_1_x2y2_PIop2.getCurrentHP().getNumeral());
	}
	 
	@Test
	public void testIncreaseCurrentHP(){
		myWorm_worm_1_x2y2_PIop2.decreaseCurrentHP(new Points(48,SortPoints.HP));
		myWorm_worm_1_x2y2_PIop2.increaseCurrentHP(new Points(40,SortPoints.HP));
		assertEquals(4440,myWorm_worm_1_x2y2_PIop2.getCurrentHP().getNumeral());
	}
//	@Test
//	public void testShoot(){
//		Worm worm = new Worm(world,new Position(2,2));
//		worm.shoot(100);
//		assertEquals(4448-myWorm_worm_1_x2y2_PIop2.getActiveWeapon().getAPCost().getNumeral(),myWorm_worm_1_x2y2_PIop2.getCurrentAP().getNumeral(),EPS);
//		assertEquals(null,world.getActiveProjectile());
//		
//	}
	
	
	
	
	
	
	
	
		
}
