import java.awt.*;
import javax.swing.*;
import java.awt.event.*;


public class SetFEN implements ActionListener {

	private Game game;

	private int level;

	public SetFEN(Game game, int level) {
		this.game  = game;
		this.level = level;
	}

	public SetFEN(){
		this.game = null;
	}
	
	public void actionPerformed(ActionEvent e) {
		if (game != null) {
			game.setFEN(game.getTextArea().getText().toString());
			game.draw();
		}
	}
}