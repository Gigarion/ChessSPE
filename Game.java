import java.util.Stack;

public class Game {

	// side to move
	private char side;

	private boolean isFlipped;

	// list of moves made in the game
	private Stack<String> movehistory;

	// 50 move rule: 50 moves without a
	// capture constitutes a draw
	private int halfMoves;

	// saves the en-passant square, if one exists
	private Tile ep;

	// keeps track of castling rights in the form of KQkq
	private boolean[] castlingrights;

	// the playing board for this game instance
	private Board board;

	// stack of all white pieces
	private Stack<Piece> wPieces;

	// stack of all black pieces
	private Stack<Piece> bPieces;

	private Piece wKing;
	private Piece bKing;

	public Game() {
		this.side = 'w';
		this.movehistory = new Stack<String>();
		this.halfMoves = 0;
		this.ep = null;
		this.castlingrights = new boolean[4];
		for (int i = 0; i < castlingrights.length; i++) {
			castlingrights[i] = true;
		}
		this.board = new Board();
	}

	public Game(char side, Stack<String> movehistory, int half, int threerep, Tile ep, boolean[] castlingrights) {
		this.side           = side;
		this.isFlipped      = false;
		this.movehistory    = movehistory;
		this.halfMoves      = half;
		this.ep             = ep;
		this.castlingrights = castlingrights;
		this.board = new Board();
	}

	public void setWhitePieces(Stack<Piece> white) {
		wPieces = white;
	}

	public void setBlackPieces(Stack<Piece> black) {
		bPieces = black;
	}

	// returns the side to move
	public char getSide() {
		return side;
	}

	public void setSide(char s) {
		side = s;
	}

	public Board getBoard() {
		return board;
	}

	public Tile getTile(int x, int y) {
		return board.getTile(x, y);
	}

	// returns the list of moves made in the game
	public Stack<String> getMoveHistory() {
		return movehistory;
	}

	// returns the half move integer
	public int getHalfMoves() {
		return halfMoves;
	}

	public void setHalfMoves(int f) {
		halfMoves = f;
	}

	// returns the ep square, if one exists
	public Tile getEP() {
		return ep;
	}

	public void setEP(Tile t) {
		ep = t;
	}

	// returns the current castling rights
	public boolean[] getCastlingRights() {
		return castlingrights;
	}

	public void setCastlingRights(boolean wkc, boolean wqc, boolean bkc, boolean bqc) {
		castlingrights[0] = wkc;
		castlingrights[1] = wqc;
		castlingrights[2] = bkc;
		castlingrights[3] = bqc;
	}

	public void setWhiteKing(Piece wk) {
		wKing = wk;
	}

	public Piece getWhiteKing() {
		return wKing;
	}

	public void setBlackKing(Piece bk) {
		bKing = bk;
	}

	public Piece getBlackKing() {
		return bKing;
	}

	public void flip() {
		isFlipped = !isFlipped;
	}

	public boolean getFlip() {
		return isFlipped;
	}

	// unit test of methods
	public void main(String[] args) {
	}

	// sets the Gameboard to the current FEN string
	public void setFEN(String fen) {
		String fenstring  = fen;
		String[] fensplit = fenstring.split(" ");

		// first section of the fen string contains the pieces' location
		String fenrows = fensplit[0];
		String[] rowsplit = fenrows.split("/");

		// function iterates from left to right and up starting at square a1
		for (int i = 0; i < 8; i++) {
			for (int j = 0; j < 8; j++) {
				String row         = rowsplit[7-i];
				char currentchar   = row.charAt(j);
				Tile currenttile   = getTile(i, j);
				Piece currentpiece = currenttile.getPiece();

				// if function encounters an integer, it skips that amount of squares
				if ((int) (currentchar) > 48 && (int) (currentchar) < 57) {
					j += Character.getNumericValue(currentchar) - 1;
				} else {
					currentpiece.setType(currentchar);
					currenttile.place(currentpiece);
				}
			}
		}

		// side to move
		setSide(fensplit[1].charAt(0));

		// castling rights
		if (fensplit[2].charAt(0) == '-') {
			setCastlingRights(false, false, false, false);
		}
		boolean wkc = (fensplit[2].indexOf('K') != -1);
		boolean wqc = (fensplit[2].indexOf('Q') != -1);
		boolean bkc = (fensplit[2].indexOf('k') != -1);
		boolean bqc = (fensplit[2].indexOf('q') != -1);
		setCastlingRights(wkc, wqc, bkc, bqc);

		// the en-passant square, if one exists
		if (getEP() == null) {
			int rank = (int) (fensplit[3].charAt(0)) - 97;
			int file = Character.getNumericValue(fensplit[3].charAt(1));
			setEP(getTile(rank, file));
		}

		// number of half moves since the last capture or pawn advance
		setHalfMoves(Integer.parseInt(fensplit[4]));
	}
}