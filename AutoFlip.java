import java.awt.*;
import javax.swing.*;
import java.awt.event.*;


public class AutoFlip implements ActionListener {

	private Game game;

	private int level;

	public AutoFlip(Game game, int level) {
		this.game  = game;
		this.level = level;
	}

	public AutoFlip(){
		this.game = null;
	}

	public void actionPerformed(ActionEvent e) {
		if (game != null) {
			game.toggleAutoFlip();
			game.draw();
		}
	}
}