package worms.model.test;

import static org.junit.Assert.*;

import java.util.List;
import java.util.Random;

import worms.exceptions.*;
import worms.model.*;
import worms.model.position.Position;
import worms.util.Util;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 * A class containing tests for the class world
 * @author Van Cleemput Enrico and Van Buggenhout Niel
 * @version 1.2
 */

public class WorldTest {
	
	private World world;
	private Random random;
	private static final double EPS = Util.DEFAULT_EPSILON;

	private boolean[][] passableMap = new boolean[][] {
			{ true, true, true, true, true, true, true, true, true, true, true, false },
			{ true, true, true, true, true, true, true, true, true, true, true, false },
			{ true, true, true, false, false, false, false, true, false, false, false, false },
			{ true, true, false, true, true, true, true, true, true, true, true, false },
			{ false, false, false, true, true, true, true, true, true, true, true, false },
			{ true, true, true, true, true, true, true, true, true, true, true, false },
			};
	@Before
	public void setUp() throws Exception {
		Random random = new Random(); // wordt nog niet gebruikt
		world = new World(4,3, passableMap, random);
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testWorldConstructor() {
		World w = new World(12,6,passableMap,random);
		assertEquals(6,w.getHeight(), EPS);
		assertEquals(12,w.getWidth(), EPS);
		assertEquals(1,w.getHeightConversion(), EPS);
		assertEquals(1,w.getWidthConversion(), EPS);
		assert(passableMap == w.getPassableMap());
	}

	@Test
	public void testIsValidWidth_TrueCase() {
		assertEquals(true,world.isValidWidth(12));		
	}
	
	@Test
	public void testIsValidWidth_FalseCase_Negative() {
		assertEquals(false,world.isValidWidth(-5));		
	}
	

	@Test
	public void testIsValidHeight_TrueCase() {
		assertEquals(true,World.isValidHeight(1));
	}

	@Test
	public void testIsValidHeight_FalseCase_Negative() {
		assertEquals(false,world.isValidHeight(-1));
	}
	
	@Test
	public void testIsPassableForCircle_TrueCase1() {
		Position position = new Position(4.0/3-EPS,2.5-EPS);
		assertEquals(true, world.isPassableForCircle(0,1.0/4,position));
	}
	
	@Test
	public void testIsPassableForCircle() {
		Position position = new Position(4.0/3-EPS,2.5-EPS);
		assertEquals(false, world.isPassableForCircle(0,1.0/4,null));
	}
	
	@Test
	public void testIsPassableForCircle_FalseCase_ImpossiblePositionEnclosedByCircle() {
		boolean[][] passableMap = new boolean[][] {
				{ true, true, true, true, true },
				{ true, true, true, true, true },
				{ true, true, false, true, true},
				{ true, true, true, true, true},
				{ true, true, true, true, true},
				{ true, true, true, true,true},
				};
		World w = new World(5,6,passableMap,random);
		Position position = new Position(2.0,2.9);
		assertEquals(true, w.isPassable(position));
		assertEquals(false, w.isPassableForCircle(0,2,position));
	}
	
	@Test
	public void testIsPassableForCircle_FalseCase_CircleOutsideWorld() {
		boolean[][] passableMap = new boolean[][] {
				{ true, true, true, true, true },
				{ true, true, true, true, true },
				{ true, true, false, true, true},
				{ true, true, true, true, true},
				{ true, true, true, true, true},
				{ true, true, true, true,true},
				};
		World w = new World(5,6,passableMap,random);
		Position position = new Position(2.0,2.0);
		assertEquals(true, w.isPassable(position));
		assertEquals(false, w.isPassableForCircle(0,2,position));
	}
	
	@Test
	public void testIsPassableForCircle_TrueCase2() {
		Position position = new Position(4.0/3-EPS,2.25+EPS);
		assertEquals(true, world.isPassableForCircle(0,1.0/4,position));
	}
	
	
	@Test
	public void testisPassableForCircle_TrueCase() {
		Position position = new Position(4.0/3-EPS,2.5-EPS);
		assertEquals(true,world.isPassableForCircle(0,1.0/2.5,position));
	}
	
	@Test
	public void testisPassableForCircle_FalseCase1() {
		Position position = new Position(4.0/3-EPS,2.1-EPS);
		boolean b = world.isPassableForCircle(0,0.4,position);
		assertEquals(false, b); // radius overlaps with impassable position
	}
	
	@Test
	public void testisPassableForCircle_FalseCase2() {
		Position position = new Position(4.0/3-EPS,1.5-EPS);
		assertEquals(false,world.isPassableForCircle(0,1.0/2.5,position));
	}
	
	
	@Test
	public void testIsPassable() {
		Position position = new Position(4.0/3-EPS,2.5-EPS);
		assertEquals(true,world.isPassable(position));
	}

//	@Test
//	public void testWormCanPass_Case1() {
//		assertEquals(0.3,world.wormCanPass(0.3, 0.3, 0, new Position(4.0/3,2.31)),EPS);
//	}
//	
//	@Test
//	public void testWormCanPass_Case2() {
//		assertEquals(0.1,world.wormCanPass(0.3, 0.3, 0, new Position(0.6-EPS,1.9)),EPS);
//	}
	
	
	@Test
	public void testIsAdjacent_TrueCase1() {
		Position position = new Position(4.0/3,2.26);
		boolean b = world.isAdjacent(0.25, position);
		assertEquals(true, b);
	}
	
	@Test
	public void testIsAdjacent_TrueCase2() {
		Position position = new Position(4.0/3,2.26);
		boolean b = world.isAdjacent(0.25, position);
		assertEquals(true, b);
	}
	
	@Test
	public void testIsAdjacent_TrueCase3() {
		Position position = new Position(4.0/3,2.54);
		assertEquals(true, world.isAdjacent(0.5,position));
	}
	
	@Test
	public void testIsAdjacent_FalseCase_RadiusTooSmall() {
		Position position = new Position(4.0/3-EPS,2.5-EPS);
		assertEquals(false, world.isAdjacent(0.25,position));
	}
	
	

//	@Test
//	public void testCheckAdjacent_TrueCase1() {
//		Position position = new Position(4.0/3,2.5);
//		assertEquals(true, world.checkAdjacent(position.getX(), position.getY(), 0.5));
//	}
//	
//	@Test
//	public void testCheckAdjacent_TrueCase() {
//		Position position = new Position(4.0/3,2.5);
//		assertEquals(true, world.checkAdjacent(position.getX(), position.getY(), 0.5));
//	}
//	
//	@Test
//	public void testCheckAdjacent_FalseCase_RadiusTooBig() {
//		Position position = new Position(4.0/3,2.5);
//		assertEquals(false, world.checkAdjacent(position.getX(), position.getY(), 0.6));
//	}
//	
//	@Test
//	public void testCheckAdjacent_FalseCase_Impassable() {
//		Position position = new Position(4.0/3-EPS,2-EPS);
//		assertEquals(false, world.checkAdjacent(position.getX(), position.getY(), 0.6));
//	}

	@Test
	public void testCalculateConversionWorldToMap() {
		double solutionWidth = 1.0/3;
		double solutionHeight = 1.0/2;
		double width = world.calculateConversionWorldToMap()[0];
		double height = world.calculateConversionWorldToMap()[1];
		assertEquals(solutionWidth, width,EPS);
		assertEquals(solutionHeight,height,EPS);
	}

	@Test
	public void testConvertPositionToMap_Random() {
		Position position = new Position(1,1);
		int [] solution = {3, world.getMapHeight()-2};
		assertEquals(solution[0], world.convertPositionToMap(position)[0]);
		assertEquals(solution[1],world.convertPositionToMap(position)[1]);
	}
	
	@Test
	public void testConvertPositionToMap_SmallestLeft() {
		Position position = new Position(0,0); 
		int [] solution = {0, 5};
		assertEquals(solution[0], world.convertPositionToMap(position)[0]);
		assertEquals(solution[1],world.convertPositionToMap(position)[1]);
	}
	
	
	@Test
	public void testConvertPositionToMap_LargestRight() {
		Position position = new Position(4,3);
		int [] solution = {11, 0};
		assertEquals(solution[0], world.convertPositionToMap(position)[0]);
		assertEquals(solution[1],world.convertPositionToMap(position)[1]);
	}
	
	@Test
	public void testConvertPositionToMap_SmallestRight() {
		Position position = new Position(4,0); 
		int [] solution = {11, 5};
		assertEquals(solution[0], world.convertPositionToMap(position)[0]);
		assertEquals(solution[1],world.convertPositionToMap(position)[1]);
	}
	
	
	
	@Test
	public void testConvertPositionToMap_LargestLeft() {
		Position position = new Position(0,3);
		int [] solution = {0,0};
		assertEquals(solution[0], world.convertPositionToMap(position)[0]);
		assertEquals(solution[1],world.convertPositionToMap(position)[1]);
	}
	
	@Test
	public void testConvertPositionToMap_Random1() {
		Position position = new Position(6.9*1.0/3,3.8*1.0/2);
		int [] solution = {6,2};
		assertEquals(solution[0], world.convertPositionToMap(position)[0]);
		assertEquals(solution[1],world.convertPositionToMap(position)[1]);
	}

	@Test
	public void testConvertMapToPosition_Random() {
		int [] indexes = {3,4};
		assertEquals(new Position(1,1),world.convertMapToPosition(indexes));
	}

	@Test
	public void testConvertMapToPosition_SmallestLeft(){
		int [] indexes = {0,5};
		assertEquals(new Position(0,0.5),world.convertMapToPosition(indexes));
	}

	@Test
	public void testConvertMapToPosition_SmallestRight(){
		int [] indexes = {11,5};
		assertEquals(new Position(11.0/3,0.5),world.convertMapToPosition(indexes));
	}
	
	
	@Test
	public void testConvertion_SmallestRight(){
		int [] indexes = {11,5};
		Position pos = world.convertMapToPosition(indexes);
		
		assertEquals(indexes[0],world.convertPositionToMap(pos)[0]);
		assertEquals(indexes[1],world.convertPositionToMap(pos)[1]);
	}
	
	
	@Test
	public void testHasProperNbTeams_TrueCase() {
		world.addTeam("TeamOne");
		world.addTeam("TeamTwo");
		world.addTeam("TeamThree");
		world.addTeam("TeamFour");
		world.addTeam("TeamFive");
		world.addTeam("TeamSix");
		world.addTeam("TeamSeven");
		world.addTeam("TeamEight");
		world.addTeam("TeamNine");
		world.addTeam("TeamTen");
		assertEquals(true, world.hasProperNbTeams());
	}
	
	@Test (expected= IllegalArgumentException.class)
	public void testHasProperNbTeams_FalseCase() {
		world.addTeam("TeamOne");
		world.addTeam("TeamTwo");
		world.addTeam("TeamThree");
		world.addTeam("TeamFour");
		world.addTeam("TeamFive");
		world.addTeam("TeamSix");
		world.addTeam("TeamSeven");
		world.addTeam("TeamEight");
		world.addTeam("TeamNine");
		world.addTeam("TeamTen");
		world.addTeam("TeamEleven");
		
	}

	@Test
	public void testCanHaveAsTeamAt_TrueCase() {
		world.addTeam("TeamOne");
		world.addTeam("TeamTwo");
		world.addTeam("TeamThree");
		world.addTeam("TeamFour");
		world.addTeam("TeamFive");
		assertEquals(true,world.canHaveAsTeamAt(4));
	}
	
	@Test
	public void testCanHaveAsTeamAt_IllegalCase() {
		//world.addTeam("TeamOne");
		assertEquals(false,world.canHaveAsTeamAt(11));
	}

	@Test
	public void testHasProperTeams_TrueCase() {
		world.addTeam("TeamOne");
		world.addTeam("TeamTwo");
		world.addTeam("TeamThree");
		assertEquals(true,world.hasProperTeams());
	}


//	@Test
//	public void testAddTeam() {
//		world.addTeam("Check");
//		List<Team> l = world.getAllTeams();
//		Team team = l.get(0); 
//		assertEquals(true,team.getWorld() == world);
//	}
//	
//	@Test
//	public void testRemoveTeam() {
//		world.addTeam("Check");
//		List<Team> l = world.getAllTeams();
//		Team team = l.get(0);
//		team.removeWorld();
//		assertEquals(true,team.getWorld() == null);
//		assertEquals(true,world.getNbTeams() == 0);
//	}

//	@Test
//	public void testStartGame_LegalCase() {
//		world.addWorm();
//		List<Worm> l = world.getAllWorms();
//		Worm worm = l.get(0);
//		world.addWorm();
//		world.addWorm();
//		world.startGame();
//		assertEquals(true,world.getCurrentWorm() == worm);
//	}

	

	@Test
	public void testIsPositionInWorld_TrueCase() {
		Position position = new Position(1,2);
		assertEquals(true,world.positionInWorld(position));
	}

	@Test 
	public void testIsPositionInWorld_FalseCase_Negative() {
		Position position = new Position(-1,2);
		assertEquals(false,world.positionInWorld(position));
	}
	
	@Test
	public void testIsPositionInWorld_IllegalCase_TooBig() {
		Position position = new Position(world.getWidth()+1,2);
		assertEquals(false,world.positionInWorld(position));
	}

	
	/**
	 * To use calculateSlope tests, modifications have to be made in the method calculateslope, totalpositions has to be decreased 
	 */
	@Test 
	public void testCalculateSlope_minPiOp2(){
		Position position = new Position(6.2*1.0/3,2.6);
		double slope = world.calculateSlope(position, 0.0,0);
		assertEquals(-1*Math.PI/2,slope,EPS);
	}

	
	@Test 
	public void testCalculateSlope_PiOp2(){
		Position position = new Position(10.1*1.0/3,2.5);
		double slope = world.calculateSlope(position, 0.0,0);
		assertEquals(Math.PI/2,slope,EPS);
	}
	
	@Test 
	public void testCalculateSlope_Random(){
		Position position = new Position(1.9*1.0/3,0.9);
		double slope = world.calculateSlope(position, 0.0,0);
		assertEquals(0.98279,slope,EPS);
	}
	
	@Test (expected = IllegalPositionException.class)
	public void testCalculateSlope_IllegalCase(){
		Position position = new Position(11.1*1.0/3,2.5);
		double slope = world.calculateSlope(position, 0.0,0);
	}

	@Test
	public void testIsValidPosition_TrueCase1() {
		Position position = new Position(3.5*1.0/3,2.1);
		assertEquals(true,world.isValidPosition(0.095,position,false));		
	}

	
	@Test
	public void testIsValidPosition_TrueCase2() {
		Position position = new Position(3.5*1.0/3,2.1);
		assertEquals(true,world.isValidPosition(0.75,position,true));		
	}

	
	@Test
	public void testIsValidPosition_FalseCase1() {
		Position position = new Position(3.5*1.0/3,2.1);
		assertEquals(false,world.isValidPosition(0.1,position,false));		
	}

	
	@Test
	public void testIsValidPosition_FalseCase2() {
		Position position = new Position(3.5*1.0/3,2.5);
		assertEquals(false,world.isValidPosition(0.75,position,true));		
	}
	
	@Test
	public void testAddWorm_legalCase(){
		world.addWorm();
		//can throw IllegalPositionException, because of the used world, no valid position can be generated from time to time.
		assertEquals(true, world.getAllWorms().size() == 1);
	}
	
//	@Test (expected = IllegalCommandException.class)
//	public void testAddWorm_IllegalCase(){
//		world.addWorm();
//		world.addWorm();
//		world.startGame();
//		world.addWorm();
//		//world.addWormFood();
//	}
//	
//	
//	@Test (expected = IllegalCommandException.class)
//	public void testStartGame_FalseCase(){
//		world.addWorm();
//		world.startGame();
//	}
//	
//	@Test 
//	public void testStartGame_FalseCase1(){
//		world.addWorm(); // can throw IllegalPositionException due to randomness of position
//		world.addWorm();
//		world.addTeam("Team");
//		world.startGame();
//		assertEquals(true, world.getAllTeams().size() == 0); //tests eraseEmptyteams()
//		assertEquals(true, world.getAllWorms().size() == 2);
//	}
//	
//	@Test 
//	public void testIsGameFinished_FalseCase(){
//		world.addWorm();
//		world.addWorm();
//		world.startGame();
//		assertEquals(false,world.isGameFinished());
//		
//	}
	
//	@Test 
//	public void testIsGameFinished_TrueCase(){
//		world.addWorm();
//		List<Worm> l = world.getAllWorms();
//		Worm worm = l.get(0);
//		world.addWorm();
//		world.startGame();
//		worm.removeWorld();
//		assertEquals(true,world.isGameFinished());
//	}
//	
	@Test
	public void testIsValidTeam_TrueCase(){
		Team team = new Team("Team",world);
		assertEquals(true, World.isValidTeam(team));
		
	}
	
	@Test
	public void testIsValidTeam_FalseCase(){
		assertEquals(false, World.isValidTeam(null));
	}
	
	@Test
	public void testIsValidTeamIndex(){
		assertEquals(true, world.isValidTeamIndex(4));
		assertEquals(false, world.isValidTeamIndex(-4));
		assertEquals(false, world.isValidTeamIndex(14));
	}
	
	
	@Test
	public void testIsTerminated_FalseCase(){
		assertEquals(false, world.isTerminated());
	}
	
}
