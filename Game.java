import java.util.Stack;
import java.util.Queue;
import java.util.LinkedList;

public class Game {

	// list of all white pieces
	private static String whitePieceTypes = "PNBRQK";

	private boolean isHighlighted;

	// side to move
	private char side;

	private boolean isFlipped;

	// list of moves made in the game
	private Stack<String> movehistory;

	// holds a highlighted piece
	private Piece tempHold;

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
	private LinkedList<Piece> wPieces;

	// stack of all black pieces
	private LinkedList<Piece> bPieces;

	// array of all tiles on the game board
	private Tile[][] tiles;

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
		board.setGame(this);
		this.tiles = board.getTiles();
		for (int i = 0; i < 8; i++) {
			for (int j = 0; j < 8; j++) {
				tiles[i][j].setGame(this);
			}
		}
	}

	public void setHighlight(boolean b) {
		isHighlighted = b;
		System.out.println("****HL:  " + b);
	}

	public boolean getHighlight() {
		return isHighlighted;
	}

	public void hold(Piece p) {
		tempHold = p;
	}

	public Piece getHold() {
		return tempHold;
	}

	public void endHold() {
		tempHold = null;
	}

	public Tile[][] getTiles() {
		return tiles;
	}

	public void reset() {
		board.reset();
	}

	public void setWhitePieces(LinkedList<Piece> white) {
		wPieces = white;
	}

	public LinkedList<Piece> getWhitePieces() {
		return wPieces;
	}

	public void setBlackPieces(LinkedList<Piece> black) {
		bPieces = black;
	}

	public LinkedList<Piece> getBlackPieces() {
		return bPieces;
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
		if (side == 'w') side = 'b';
		else side = 'w';
	}

	public boolean getFlip() {
		return isFlipped;
	}

	public void init() {
		board.draw();
	}

	public void draw() {
		board.draw();
	}

	public void move(Piece p, Tile target) {
		board.move(p, target);
	}

			public boolean mateCheck() {
		Stack<Piece> stack = new Stack<Piece>();
		LinkedList<Piece> pieces;
		Piece oppking;
		if (getSide() == 'w') {
			pieces  = getBlackPieces();
			oppking = getBlackKing();
		} else {
			pieces  = getWhitePieces();
			oppking = getWhiteKing();
		}

		// push all pieces with valid moves onto the stack
		for (Piece piece: pieces) {
			if (!piece.moves().isEmpty()) {
				stack.push(piece);
			}
		}

		if (stack.isEmpty()) {
			if (oppking.getTile().isAttacked()) return true;
		}

		return false;
	}

	public boolean drawCheck() {
		Stack<Piece> stack = new Stack<Piece>();
		LinkedList<Piece> pieces;
		Piece oppking;
		if (getSide() == 'w') {
			pieces  = getBlackPieces();
			oppking = getBlackKing();
		} else {
			pieces  = getWhitePieces();
			oppking = getWhiteKing();
		}

		// push all pieces with valid moves onto the stack
		for (Piece piece: pieces) {
			if (!piece.moves().isEmpty()) {
				stack.push(piece);
			}
		}

		if (stack.isEmpty()) {
			if (!oppking.getTile().isAttacked()) return true;
		}

		return false;
	}

	// sets the Gameboard to the current FEN string
	public void setFEN(String fen) {
		String fenstring  = fen;
		String[] fensplit = fenstring.split(" ");
		LinkedList<Piece> whiteToSet = new LinkedList<Piece>();
		LinkedList<Piece> blackToSet = new LinkedList<Piece>();

		// first section of the fen string contains the pieces' location
		String fenrows = fensplit[0];
		String[] rowsplit = fenrows.split("/");
		int count = 0;
		// function iterates from left to right and up starting at square a1
		for (int i = 0; i < 8; i++) {
			for (int j = 0; j < 8; j++) {
				
				if (j == 0) count = 0;
				String row         = rowsplit[7-i];
				char currentChar   = row.charAt(count);
				Tile currentTile   = getTile(i, j);

				// if function encounters an integer, it skips that amount of squares
				if ((int) (currentChar) > 48 && (int) (currentChar) < 57) {
					j += Character.getNumericValue(currentChar) - 1;
					//System.out.println("Skipping: " + Character.getNumericValue(currentChar));
				} 
				else {
					
					Piece currentPiece = new Piece();
					currentPiece.setType(currentChar);

					if (whitePieceTypes.indexOf(currentChar) != -1) {
						currentPiece.setColor('w');
					}
					else currentPiece.setColor('b');

					currentTile.place(currentPiece);
					currentPiece.moveTo(currentTile);
					currentPiece.setGame(this);
					
					if (currentPiece.getColor() == 'w') {
						//System.out.println("Tile : " + currentPiece.getTile().getID()); 
						whiteToSet.add(currentPiece);
						//System.out.println("J: " + j);
					}
					else {blackToSet.add(currentPiece);}

					if (currentChar =='K') setWhiteKing(currentPiece);
					else if (currentChar == 'k') setBlackKing(currentPiece);
				}
				count++;
			}
		}

		wPieces = whiteToSet;
		bPieces = blackToSet;

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
		if (fensplit[3].length() > 1) {
			int rank = (int) (fensplit[3].charAt(0)) - 97;
			int file = Character.getNumericValue(fensplit[3].charAt(1));
			setEP(getTile(rank, file));
		}
		else setEP(null);

		// number of half moves since the last capture or pawn advance
		setHalfMoves(Integer.parseInt(fensplit[4]));
	}

	public static void main(String[] args) {
		Game game = new Game();
		game.setFEN("rnbqkbnr/pppppppp/8/8/8/8/PPPPPPPP/RNBQKBNR w KQkq - 0 1");
		
		Tile[][] tiles = game.getTiles();

		for (Piece p : game.getWhitePieces()) {
			System.out.println(p.getTile().getID());
		}
		System.out.println("end white");
		for (Piece p : game.getBlackPieces()) {
			System.out.println(p.getTile().getID());
		}
		long a = System.currentTimeMillis();

		
		System.out.println("end black");
		game.draw();
		game.draw();
		a = System.currentTimeMillis();
		do {} while (System.currentTimeMillis() - a < 1000);
		game.draw();
	}
}