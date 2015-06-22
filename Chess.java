import java.util.Stack;
import java.awt.*;
import javax.swing.*;
public class Chess {
	public static void main(String[] args) {
		Game game = new Game(1);
		game.setFEN("rnbqkbnr/pppppppp/8/8/8/8/PPPPPPPP/RNBQKBNR w KQkq - 0 1");
		game.draw();
		JFrame frame = new JFrame("Chess SPE");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setLayout(new GridLayout(1, 2));
		frame.setPreferredSize(new Dimension(680, 480));

		JLabel label = new JLabel();
		label.setBackground(Color.YELLOW);
		label.setPreferredSize(new Dimension (200, 480));
		label.setOpaque(true);

		JPanel board = game.getBoardPanel();
		board.setVisible(true);
		frame.add(board);
		frame.add(label);

		frame.pack();
		frame.setVisible(true);

		while (true) {
			frame.pack();
			frame.setVisible(true);
		}
	}
}