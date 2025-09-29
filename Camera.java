import java.awt.geom.*;
import java.util.*;
import java.awt.*;

public class Camera {
	private int xPos;
	private int yPos;
	private double angle;
	private int FOV;
	private ArrayList<Rectangle2D> walls;
	private LevelFrame frame;

	public Camera(int x, int y, double ang, ArrayList<Rectangle2D> wallList, LevelFrame lf) {
		xPos = x;
		yPos = y;
		angle = ang;
		walls = wallList;
		FOV = 60;
		frame = lf;
	}

	public double castRay(double startX, double startY, double angle, double playerAngle) {
		double rayX = startX;
		double rayY = startY;
		double stepSize = 0.05;
		double distance = 0;
		while (rayX > 0 && rayX < frame.getWidth() && rayY > 0 && rayY < frame.getHeight() && !isWall(rayX, rayY) && !isEnemy(rayX, rayY)) {
			rayX += Math.cos(Math.toRadians(angle)) * stepSize;
			rayY += Math.sin(Math.toRadians(angle)) * stepSize;
			distance += stepSize;
		}
		return distance * Math.cos(Math.toRadians(angle + playerAngle));
	}
	public void renderWallSlice(Graphics g, int x, double distance) {
		int screenHeight = 600;
		int wallHeight = (int)(screenHeight / distance * 50);
		int startY = (screenHeight - wallHeight) / 2;

		//int brightness = (int)(255 / (1 + distance * .1));
		//Color wallColor = new Color(brightness, brightness, brightness);
		//g.setColor(wallColor);

		g.setColor(Color.blue);
		g.fillRect(x, startY, 1, wallHeight);
	}

	private boolean isWall(double x, double y) {
		Point2D point = new Point2D.Double(x, y);
		for (Rectangle2D wall : walls) {
			if (wall.contains(point)) {
				return true;
			}
		}
		return false;
	}

	private boolean isEnemy(double x, double y) {
		Point2D point = new Point2D.Double(x, y);
		for (Enemy e : frame.getEnemies()) {
			if (e.getBoundingBox().contains(point)) {
				return true;
			}
		}
		return false;
	}

	public void update(Player p) {
		xPos = p.getX();
		yPos = p.getY();
		angle = p.getAngle();
	}

	public int getFOV() {
		return FOV;
	}

}
