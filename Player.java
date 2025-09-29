import java.awt.geom.*;
import java.util.*;

public class Player extends Character{
	/*
	x = Player's x coordinate
	y = Player's y coordinate
	orientation = angle of player
	width = the width of the player's bounding box
	height = the height of the player's bounding box
	health = player's health
	*/
	public Player(int x, int y, double orientation, int width, int height, double health) {
		super(x, y, orientation, width, height, health);
	}
	//take all enemies in game, see if the shot intersects the enemy, and damage the first one intersected
	public void shoot(ArrayList<Enemy> enemyList) {
		ArrayList<Enemy> intersected = enemiesIntersected(enemyList);
		if (!intersected.isEmpty() && findNearestEnemy(intersected).getCurrentHealth() > 0.0) {
			findNearestEnemy(intersected).updateHealth(20.0);

		}
	}
	//find the nearest enemy out of every enemy alive in the game
	private Enemy findNearestEnemy(ArrayList<Enemy> enemyList) {
		double shortestDist = Math.sqrt(Math.pow(this.getX() + enemyList.get(0).getX(), 2) + Math.pow(this.getY() + enemyList.get(0).getY(), 2));
		double currentDist = 0;
		Enemy nearest = enemyList.get(0);
		for (Enemy e : enemyList) {
			currentDist = Math.sqrt(Math.pow(this.getX() + e.getX(), 2) + Math.pow(this.getY() + e.getY(), 2));
			if (currentDist < shortestDist) {
				shortestDist = currentDist;
				nearest = e;
			}
		}
		return nearest;
	}
	//find all enemies intersected by the player's shot
	private ArrayList<Enemy> enemiesIntersected(ArrayList<Enemy> enemyList) {
		int xPos = this.getX() + this.getXSize() / 2;
		int yPos = this.getY() + this.getYSize() / 2;
		Line2D ray = new Line2D.Double(xPos, yPos, xPos + getRange() * Math.cos(Math.toRadians(getAngle())), yPos + getRange() * Math.sin(Math.toRadians(-getAngle())));
		ArrayList<Enemy> intersected = new ArrayList<Enemy>();
		for (Enemy e : enemyList) {
			if (ray.intersects(e.getBoundingBox())) {
				intersected.add(e);
			}
		}
		return intersected;
	}

}