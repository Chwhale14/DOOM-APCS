import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class Game {
	private LevelFrame gameFrame;
	private JFrame myFrame;
	public Game(int width, int height) {
		myFrame = new JFrame("DOOM");
		gameFrame = new LevelFrame(width, height);

		Container pane = myFrame.getContentPane();
		pane.add(gameFrame, BorderLayout.CENTER);

		myFrame.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
		    	System.exit(0);
		    }
		});

		myFrame.setResizable(false);
        myFrame.pack();
        myFrame.setLocation(100,100);
	}
	public void show() {
		myFrame.show();
	    gameFrame.requestFocus();
    }
    public void hide() {
		myFrame.hide();
	}
    public static void main(String[] args) {
		Game doomGame = new Game(400, 600);
		doomGame.show();
	}
}