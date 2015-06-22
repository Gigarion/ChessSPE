import java.awt.*;
import javax.swing.*;
import java.awt.event.*;


public class FlipBoard implements ActionListener {

	private Game game;

	private int level;

	public FlipBoard(Game game, int level) {
		this.game  = game;
		this.level = level;
	}

	public FlipBoard(){
		this.game = null;
	}

	public void actionPerformed(ActionEvent e) {
		if (game != null) {
			game.toggleFlip();
			game.draw();
		}
	}
}