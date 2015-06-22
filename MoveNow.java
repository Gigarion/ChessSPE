import java.awt.*;
import javax.swing.*;
import java.awt.event.*;


public class MoveNow implements ActionListener {

	private Game game;

	private int level;

	public MoveNow(Game game, int level) {
		this.game  = game;
		this.level = level;
	}

	public MoveNow(){
		this.game = null;
	}
	
	public void actionPerformed(ActionEvent e) {
		if (game != null && game.getAI() != null) game.makeAIMove();
	}
}