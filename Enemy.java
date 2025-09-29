import java.awt.geom.*;
import java.util.*;
import java.time.*;

public class Enemy extends Character{
		private long lastShot = 0;

		public Enemy(int x, int y, double orientation, int width, int height, double health) {
			super(x, y, orientation, width, height, health);
		}

		public void shoot(Player p) {
			Line2D ray = new Line2D.Double(getX(), getY(), getX() + (int)(getRange() * Math.cos(Math.toRadians(getAngle()))), getY() + (int)(getRange() * Math.sin(Math.toRadians(getAngle()))));
			if (ray.intersects(p.getBoundingBox())) {
				p.updateHealth(10.0);
			}
		}
		//adjust angle based on player movement
		public void updateAngle(Player p) {
			Ellipse2D aoe = new Ellipse2D.Double(getX() - 4 * getXSize(), getY() - 4 * getYSize(), 8 * getXSize(), 8 * getYSize());
			if (aoe.intersects(p.getBoundingBox())) {
				int playerCenterX = p.getX() + p.getXSize() / 2;
				int playerCenterY = p.getY() + p.getYSize() / 2;
				int enemyCenterX = getX() + getXSize() / 2;
				int enemyCenterY = getY() + getYSize() / 2;

				int xDist = playerCenterX - enemyCenterX;
				int yDist = playerCenterY - enemyCenterY;

				double theta = Math.toDegrees(Math.atan2(yDist, xDist));
				setAngle(theta);
			}

		}
		public void executeBehaviors(Player p) {
			int playerCenterX = p.getX() + p.getXSize() / 2;
			int playerCenterY = p.getY() + p.getYSize() / 2;
			int enemyCenterX = getX() + getXSize() / 2;
			int enemyCenterY = getY() + getYSize() / 2;
			double distFromPlayer = Math.sqrt(Math.pow(playerCenterX - enemyCenterX, 2) + Math.pow(playerCenterY - enemyCenterY, 2));

			long currentTime = System.currentTimeMillis();
			final long COOLDOWN = 1000;

			if (distFromPlayer > getRange() && checkCollisions(getX() + (int)(5 * Math.cos(Math.toRadians(getAngle()))), getY() + (int)(5 * Math.sin(Math.toRadians(getAngle()))), getWallList()) == false) {
				updateAngle(p);
				setX(getX() + (int)(5 * Math.cos(Math.toRadians(getAngle()))));
				setY(getY() + (int)(5 * Math.sin(Math.toRadians(getAngle()))));
				playerCenterX = p.getX() + p.getXSize() / 2;
				playerCenterY = p.getY() + p.getYSize() / 2;
				enemyCenterX = getX() + getXSize() / 2;
				enemyCenterY = getY() + getYSize() / 2;
				distFromPlayer = Math.sqrt(Math.pow(playerCenterX - enemyCenterX, 2) + Math.pow(playerCenterY - enemyCenterY, 2));
			} else {
				if (currentTime - lastShot >= COOLDOWN) {
					lastShot = currentTime;
					shoot(p);
				}
			}
		}
}