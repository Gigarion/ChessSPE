import java.util.Stack;
public class Chess {
	public static void main(String[] args) {
		Game game = new Game();
		game.getBoard().setGame(game);
		Stack<Piece> white = game.getBoard().getWhitePieces();
		Stack<Piece> black = game.getBoard().getBlackPieces();
		Tile[][] tiles = game.getBoard().getTiles();
		for (Piece w : white) {
			w.setGame(game);
		}
		for (Piece b : black) {
			b.setGame(game);
		}
		for (int i = 0; i < 8; i++) {
			for (int j = 0; j < 8; j++) {
				tiles[i][j].setGame(game);
			}
		}
		game.setWhitePieces(white);
		game.setBlackPieces(black);
		game.getBoard().reset();
		game.getBoard().draw();
	}
}