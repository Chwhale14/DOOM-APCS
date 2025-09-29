import java.awt.geom.Rectangle2D;
import java.util.*;

public class Character {
	private int xPos;
	private int yPos;
	private double angle;
	private int xSize;
	private int ySize;
	private double currentHealth;
	private double maxHealth;
	private double range;
	private ArrayList<Rectangle2D> wallList;
	/*
	x = Character's x coordinate
	y = Character's y coordinate
	orientation = angle of character
	width = the width of the character's bounding box
	height = the height of the character's bounding box
	charHealth = character's health
	*/
	public Character(int x, int y, double orientation, int width, int height, double charHealth) {
		xPos = x;
		yPos = y;
		angle = orientation;
		xSize = width;
		ySize = height;
		currentHealth = charHealth;
		maxHealth = charHealth;
		range = 100.0;
		wallList = new ArrayList<Rectangle2D>();
	}
	public int getX() {
		return xPos;
	}

	public int getY() {
		return yPos;
	}

	public double getAngle() {
		return angle;
	}

	public int getXSize() {
		return xSize;
	}

	public int getYSize() {
		return ySize;
	}

	public double getRange() {
		return range;
	}

	public double getCurrentHealth() {
		return currentHealth;
	}

	public double getMaxHealth() {
		return maxHealth;
	}

	public void setX(int xVal) {
		xPos = xVal;
	}

	public void setY(int yVal) {
		yPos = yVal;
	}

	public void updateHealth(double dmg) {
		currentHealth -= dmg;
	}

	public void setWallList(ArrayList<Rectangle2D> walls) {
		wallList = new ArrayList<Rectangle2D>(walls);
	}

	public ArrayList<Rectangle2D> getWallList() {
		return wallList;
	}

	public void moveForward(int dist) {
		int dx = (int)(dist * Math.cos(Math.toRadians(angle)));
		int dy = (int)(dist * Math.sin(Math.toRadians(angle)));

		if (checkCollisions(xPos + dx, yPos - dy, wallList) == false) {
			xPos += dx;
			yPos -= dy;
		}
	}

	public void moveBackward(int dist) {
		int dx = (int)(dist * Math.cos(Math.toRadians(angle)));
		int dy = (int)(dist * Math.sin(Math.toRadians(angle)));

		if (checkCollisions(xPos - dx, yPos + dy, wallList) == false) {
			xPos -= dx;
			yPos += dy;
		}
	}

	public void moveLeft(int dist) {
		double newAngle = Math.toRadians(90 - angle);
		int dx = (int)(dist * Math.cos(newAngle));
		int dy = (int)(dist * Math.sin(newAngle));

		if (checkCollisions(xPos - dx, yPos - dy, wallList) == false) {
			xPos -= dx;
			yPos -= dy;
		}
	}
	public void moveRight(int dist) {
		double newAngle = Math.toRadians(90 - angle);
		int dx = (int)(dist * Math.cos(newAngle));
		int dy = (int)(dist * Math.sin(newAngle));

		if (checkCollisions(xPos + dx, yPos + dy, wallList) == false) {
			xPos += dx;
			yPos += dy;
		}
	}

	public void incrementAngle(double ang) {
		angle += ang;
	}

	public void setAngle(double newAngle) {
		angle = newAngle;
	}

	public Rectangle2D getBoundingBox() {
		return new Rectangle2D.Double(xPos, yPos, xSize, ySize);
	}

	public void shoot() {}

	//check if the character will collide with a wall with their next move
	public boolean checkCollisions(int nextX, int nextY, ArrayList<Rectangle2D> walls) {
		Rectangle2D movedPlayer = new Rectangle2D.Double(nextX, nextY, xSize, ySize);
		for (Rectangle2D wall : walls) {
			if (wall.intersects(movedPlayer)) {
				return true;
			}
		}
		return false;
	}

}