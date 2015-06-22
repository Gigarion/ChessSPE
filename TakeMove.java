import java.awt.*;
import javax.swing.*;
import java.awt.event.*;

// supports the take move button in the GUI
public class TakeMove implements ActionListener {

	private Game game;

	private int level;

	public TakeMove(Game game, int level) {
		this.game  = game;
		this.level = level;
	}

	public TakeMove(){
		this.game = null;
	}
	public void actionPerformed(ActionEvent e) {
		if (game != null) game.takeMove();
	}
}