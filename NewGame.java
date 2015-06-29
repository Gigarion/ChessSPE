import java.awt.*;
import javax.swing.*;
import java.awt.event.*;


public class NewGame implements ActionListener {

	private Game game;

	private int level;

	public NewGame(Game game, int level) {
		this.game  = game;
		this.level = level;
	}

	public NewGame(){
		this.game = null;
	}

	public void actionPerformed(ActionEvent e) {
		if (game != null) {
			game.emptyMoveHistory();
			game.setFEN("rnbqkbnr/pppppppp/8/8/8/8/PPPPPPPP/RNBQKBNR w KQkq - 0 1");
			game.draw();
		}
	}
}