import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.*;
import java.awt.geom.*;

public class LevelFrame extends JPanel implements KeyListener {
	private int width;
	private int height;

	private Player gamePlayer;
	private ArrayList<Enemy> enemies;
	private ArrayList<Rectangle2D> walls;

	private Camera c;

	private long gracePeriod = 5000;
	private long startTime = System.currentTimeMillis();

	public LevelFrame(int width, int height) {
		this.width = width;
		this.height = height;

		gamePlayer = new Player(width / 2, height / 2, 0.0, 50, 50, 100.0);
		enemies = new ArrayList<Enemy>();
		enemies.add(new Enemy(width / 2 + 10, height / 2 + 10, 0.0, 50, 50, 100.0));
		enemies.add(new Enemy(width / 2 + 30, height / 2 + 30, 0.0, 50, 50, 100.0));

		walls = new ArrayList<Rectangle2D>();
		walls.add(new Rectangle2D.Double(100, 100, 210, width / 2 - 50));
		walls.add(new Rectangle2D.Double(100, height - width / 2, 210, width / 2 - 50));
		gamePlayer.setWallList(walls);

		c = new Camera(gamePlayer.getX(), gamePlayer.getY(), gamePlayer.getAngle(), walls, this);

		this.addKeyListener(this);
	}

	public int getWidth() {
		return width;
	}

	public int getHeight() {
		return height;
	}

	public boolean isFocusable() {
		return true;
    }

    public ArrayList<Enemy> getEnemies() {
		return enemies;
	}

	public Dimension getPreferredSize() {
		return new Dimension(width, height);
    }

    public Dimension getMinimumSize() {
		return new Dimension(width, height);
    }

    public Dimension getMaximumSize() {
		return new Dimension(width, height);
    }

	public void paintComponent(Graphics g) {
		Graphics2D g2d = (Graphics2D) g;
		super.paintComponent(g2d);
		g2d.setColor(Color.BLACK);
		g2d.fillRect(0, 0, width, height);

		if (gamePlayer.getCurrentHealth() > 0.0) {
			g2d.setColor(Color.DARK_GRAY);
			g2d.fillRect(0, height / 2, width, height / 2);
			for (int i = -c.getFOV(); i < c.getFOV(); i++) {
				double rayAngle = -gamePlayer.getAngle() + i;
				int centerX = gamePlayer.getX() + gamePlayer.getXSize() / 2;
				int centerY = gamePlayer.getY() + gamePlayer.getYSize() / 2;
				double distance = c.castRay(centerX, centerY, rayAngle, gamePlayer.getAngle());
				c.renderWallSlice(g, (int)((i + c.getFOV()) * (10 / 3)), distance);
				g2d.setColor(Color.LIGHT_GRAY);
				g2d.fillRect(0, 525, width, 75);
				g2d.setColor(Color.DARK_GRAY);
				g2d.fillRect(95, 540, 210, 46);
				g2d.setColor(Color.red);
				g2d.fillRect(100, 545, (int)(200 * gamePlayer.getCurrentHealth() / gamePlayer.getMaxHealth()), 36);
				for (int j = 0; j < enemies.size(); j++) {
					if (enemies.get(j).getCurrentHealth() > 0.0) {
						repaint();
						long currentTime = System.currentTimeMillis();
						if (currentTime - startTime >= gracePeriod) {
							enemies.get(j).executeBehaviors(gamePlayer);
							repaint();
						}
					} else {
						enemies.remove(j);
						repaint();
						j--;
					}
				}
			}
		} else {
				g2d.setColor(Color.red);
				g2d.drawString("GAME OVER", width / 2, height / 2);
		}

	}

	public void paintBorder(Graphics g) {
	}

	public void keyPressed(KeyEvent e) {
		if (e.getKeyChar() == 'w' || e.getKeyChar() == 'W') {
			gamePlayer.moveForward(5);
			c.update(gamePlayer);
			repaint();
		}
		else if (e.getKeyChar() == 's' || e.getKeyChar() == 'S') {
			gamePlayer.moveBackward(5);
			c.update(gamePlayer);
			repaint();
		}
		else if (e.getKeyChar() == 'a' || e.getKeyChar() == 'A') {
			gamePlayer.moveLeft(5);
			c.update(gamePlayer);
			repaint();
		}
		else if (e.getKeyChar() == 'd' || e.getKeyChar() == 'D') {
			gamePlayer.moveRight(5);
			c.update(gamePlayer);
			repaint();
		} else if (e.getKeyChar() == 'f' || e.getKeyChar() == 'F') {
			gamePlayer.shoot(enemies);
		} else if (e.getKeyChar() == 'q' || e.getKeyChar() == 'Q') {
			gamePlayer.incrementAngle(10.0);
			c.update(gamePlayer);
			repaint();
		} else if (e.getKeyChar() == 'e' || e.getKeyChar() == 'E') {
			gamePlayer.incrementAngle(-10.0);
			c.update(gamePlayer);
			repaint();
		}
	}

	public void keyReleased(KeyEvent e) {

	}

	public void keyTyped(KeyEvent e) {

	}


}
