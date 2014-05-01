package worms.model;

import java.util.Collection;
import java.util.Random;
import worms.exceptions.*;
import worms.model.position.*;
import worms.model.points.*;

public class Facade implements IFacade {

	@Override
	public void addEmptyTeam(World world, String newName) {
		try{ world.addTeam(newName);
		
		}
		catch(RuntimeException exc){
			throw new ModelException("addEmptyTeam");
		}
		
		
	}

	@Override
	public void addNewFood(World world) {
		try { world.addWormFood();
		
		}
		catch(RuntimeException exc){
			
		}
		
	}

	@Override
	public void addNewWorm(World world) {
			try { world.addWorm();
		
		}
		catch(RuntimeException exc){
		}
		
		
	}

	@Override
	public boolean canFall(Worm worm) {
			try{return worm.canFall();
			
			}
			catch(RuntimeException exc){
				throw new ModelException("canFall");
			}
		
	}

	@Override
	public boolean canMove(Worm worm) {
		try{return worm.canMove(worm.getTerrainAngle());
		
		}
		catch(RuntimeException exc){
			throw new ModelException("canMove");
		}
	}

	@Override
	public boolean canTurn(Worm worm, double angle) {
		try{return worm.enoughAPLeft(Worm.costOfTurn(angle));
		
		}
		catch(RuntimeException exc){
			throw new ModelException("canTurn");
		}
	}

	@Override
	public Food createFood(World world, double x, double y) {
		try{ return new Food(new Position(x,y),world);
		
		}
		
		catch(RuntimeException exc){
			throw new ModelException("createFood");
		}
	}

	@Override
	public World createWorld(double width, double height,
			boolean[][] passableMap, Random random) {
		try { return new World(width, height,passableMap,random);
		
		}
		catch (RuntimeException exc){
			throw new ModelException("createWorld");
		}
	}

	@Override
	public Worm createWorm(World world, double x, double y, double direction,
			double radius, String name) {
		try { return new Worm(name,radius,x,y,direction,world);
		
		}
		catch (RuntimeException exc){
			throw new ModelException("createWorm");
		}
		
	}

	@Override
	public void fall(Worm worm) {
		try{worm.fall(worm.getPosition());
		
		}
		catch (RuntimeException exc){
			throw new ModelException("fall");
		}
		
	}

	@Override
	public int getActionPoints(Worm worm) {
		try{ return worm.getCurrentAP().getNumeral();
		
		}
		catch (RuntimeException exc){
			throw new ModelException("getActionPoints");
		}
		
	}

	@Override
	public Projectile getActiveProjectile(World world) {
		try{return world.getActiveProjectile();
		
		}
		catch (RuntimeException exc){
			throw new ModelException("getActiveProjectile");
		}
	}

	@Override
	public Worm getCurrentWorm(World world) {
		try{return world.getCurrentWorm();
		
		}
		catch (RuntimeException exc){
			throw new ModelException("getCurrentWorm");
		}
		
	}

	@Override
	public Collection<Food> getFood(World world) {
		try{return world.getAllFood();
		
		}
		catch (RuntimeException exc){
			throw new ModelException("getFood");
		}
	}

	@Override
	public int getHitPoints(Worm worm) {
		try{ return worm.getCurrentHP().getNumeral();
		
		}
		catch (RuntimeException exc){
			throw new ModelException("getHitPoints");
		}
		
	}

	@Override
	public double[] getJumpStep(Projectile projectile, double t) {
		try{return projectile.jumpStep(t, projectile.getDirection()).getPosition();
		
		}
		catch (RuntimeException exc){
			throw new ModelException("getJumpStepProjectile");
		}
		
		
	}

	@Override
	public double[] getJumpStep(Worm worm, double t) {
		try{ return worm.jumpStep(t, worm.getDirection()).getPosition();
				
		}
		catch (RuntimeException exc){
			throw new ModelException("getJumpStepWorm");
		}
	}

	@Override
	public double getJumpTime(Projectile projectile, double timeStep) {
		try{return projectile.jumpTime(timeStep);
		
		}
		catch (RuntimeException exc){
			throw new ModelException("getJumpTimeProjectile");
		}
	}

	@Override
	public double getJumpTime(Worm worm, double timeStep) {
		try{ return worm.jumpTime(timeStep);
		
		}
		catch (RuntimeException exc){
			throw new ModelException("getJumpTimeWorm");
		}
	}

	@Override
	public double getMass(Worm worm) {
		return worm.getMass();
	}

	@Override
	public int getMaxActionPoints(Worm worm) {
		return worm.getMaxPoints();
	}

	@Override
	public int getMaxHitPoints(Worm worm) {
		return worm.getMaxPoints();
	}

	@Override
	public double getMinimalRadius(Worm worm) {
		return worm.getLowerBoundRadius();
	}

	@Override
	public String getName(Worm worm) {
		return worm.getName();
	}

	@Override
	public double getOrientation(Worm worm) {
		return worm.getDirection();
	}

	@Override
	public double getRadius(Food food) {
		return food.getRadius();
	}

	@Override
	public double getRadius(Projectile projectile) {
		return projectile.getRadius();
	}

	@Override
	public double getRadius(Worm worm) {
		return worm.getRadius();
	}

	@Override
	public String getSelectedWeapon(Worm worm) {
		return ""+ worm.getWeaponAt(worm.getActiveWeaponIndex());
	}

	@Override
	public String getTeamName(Worm worm) {
		return worm.getTeamName();
		
		
	}

	@Override
	public String getWinner(World world) {
		return world.getNameWinners();
	}

	@Override
	public Collection<Worm> getWorms(World world) {
		return world.getAllWorms();
	}

	@Override
	public double getX(Food food) {
		return food.getX();
	}

	@Override
	public double getX(Projectile projectile) {
		return projectile.getX();
	}

	@Override
	public double getX(Worm worm) {
		return worm.getX();
	}

	@Override
	public double getY(Food food) {
		return food.getY();
	}

	@Override
	public double getY(Projectile projectile) {
		return projectile.getY();
	}

	@Override
	public double getY(Worm worm) {
		return worm.getY();
	}

	@Override
	public boolean isActive(Food food) {
		return (!food.isTerminated());
	}

	@Override
	public boolean isActive(Projectile projectile) {
		return (!projectile.isTerminated());
	}

	@Override
	public boolean isAdjacent(World world, double x, double y, double radius) {
		return world.isAdjacent(radius,new Position(x,y));
	}

	@Override
	public boolean isAlive(Worm worm) {
		return (!worm.isTerminated());
	}

	@Override
	public boolean isGameFinished(World world) {
		return world.isGameFinished();
	}

	@Override
	public boolean isImpassable(World world, double x, double y, double radius) {
		return (!world.isPassableForCircle(0,radius, new Position(x,y)));
	}

	@Override
	public void jump(Projectile projectile, double timeStep) {
		try{projectile.jump(timeStep);
		
		}
		catch(RuntimeException exc){
			throw new ModelException("jumpprojectile");
		}
		
	}

	@Override
	public void jump(Worm worm, double timeStep) {
		try{ worm.jump(timeStep);
		
		}
		catch(RuntimeException exc){
			throw new ModelException("jumpworm");
		}
		
	}

	@Override
	public void move(Worm worm) {
		try{worm.step(worm.getTerrainAngle());
		
		}
		catch(RuntimeException exc){
			throw new ModelException("move");
		}
	}

	@Override
	public void rename(Worm worm, String newName) {
		worm.setName(newName);
		
	}

	@Override
	public void selectNextWeapon(Worm worm) {
		worm.selectNextWeapon();
		
	}

	@Override
	public void setRadius(Worm worm, double newRadius) {
		try {worm.setRadius(newRadius);
		
		}
		catch(RuntimeException exc){
			throw new ModelException("setradius");
		}
		
	}

	@Override
	public void shoot(Worm worm, int yield) {
		try{ worm.shoot(yield);
		
		}
		catch(RuntimeException exc){
		}
		
	}

	@Override
	public void startGame(World world) {
		try{world.startGame();
		
		}
		catch(RuntimeException exc){
			throw new ModelException("startGame");
		}
		
	}

	@Override
	public void startNextTurn(World world) {
		try{ world.startNextTurn();
		
		}
		catch(RuntimeException exc){
			//throw new ModelException("startNextTurn");
		}
		
	}

	@Override
	public void turn(Worm worm, double angle) {
		try{worm.turn(angle);
		
		}
		catch(RuntimeException exc){
			throw new ModelException("turn");
		}
		
	}
	
	

}
