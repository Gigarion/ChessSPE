import java.util.Stack;
import java.util.Queue;
import java.util.LinkedList;
public class Board {
	private Tile[][] tiles = new Tile[8][8];
	private LinkedList<Piece> wPieces;
	private LinkedList<Piece> bPieces;
	private Game game;
	private static final double tileStartX = 0;
	private static final double tileStartY = 0;
	private static final double fTileStartX = 7;
	private static final double fTileStartY = 7;
	private static final double displacement = 1;
	
	public Board() { // constructor;
		wPieces = new LinkedList<Piece>();
		bPieces = new LinkedList<Piece>();
		for (int r = 0; r < 8; r++) { // creates 8 x 8 grid of tiles
			for (int c = 0; c < 8; c++) {
				StringBuilder tileId = new StringBuilder(2);
				tileId.append(r);
				tileId.append(c);
				this.tiles[r][c] = new Tile(tileId.toString());
			}
		}
	}

	public void setGame(Game g) {
		game = g;
	}

	public void draw() { // draws current state of the board
		StdDraw.setXscale(-1, 8);
		StdDraw.setYscale(-1, 8);
		if (!game.getFlip()) {
			for (int r = 0; r < 8; r++) {
				for (int c = 0; c < 8; c++) {
					tiles[r][c].draw((tileStartX + (c * displacement)), (tileStartY + (r * displacement)));
				}
			}
		}
		else {
			for (int r = 0; r < 8; r++) {
				for (int c = 0; c < 8; c++) {
					tiles[r][c].draw((fTileStartX - (c * displacement)), (fTileStartY - (r * displacement)));
				}
			}
		} 
	}

	public boolean isFlipped() { // returns whether or not the board is flipped (black at bottom)
		return game.getFlip();
	}

	public void reset() { // creates a blank gameboard
		wPieces = new LinkedList<Piece>();
		bPieces = new LinkedList<Piece>();

		Piece[] wPawn = new Piece[8]; 
		
		Piece wKing = new Piece('K', 'w', tiles[0][4]);
		tiles[0][4].place(wKing);
		game.setWhiteKing(wKing);
		wPieces.add(wKing);

		Piece wQueen = new Piece('Q', 'w', tiles[0][3]);
		tiles[0][3].place(wQueen);
		wPieces.add(wQueen);
		
		Piece wRook[] = new Piece[2];
		wRook[0] = new Piece('R', 'w', tiles[0][0]);
		tiles[0][0].place(wRook[0]);
		wPieces.add(wRook[0]);
		wRook[1] = new Piece('R', 'w', tiles[0][7]);
		tiles[0][7].place(wRook[1]);
		wPieces.add(wRook[1]);
		
		Piece[] wBishop = new Piece[2];
		wBishop[0] = new Piece('B', 'w', tiles[0][2]);
		tiles[0][2].place(wBishop[0]);
		wPieces.add(wBishop[0]);
		wBishop[1] = new Piece('B', 'w', tiles[0][5]);
		tiles[0][5].place(wBishop[1]);
		wPieces.add(wBishop[1]);

		Piece[] wKnight = new Piece[2];
		wKnight[0] = new Piece('N', 'w', tiles[0][1]);
		tiles[0][1].place(wKnight[0]);
		wPieces.add(wKnight[0]);
		wKnight[1] = new Piece('N', 'w', tiles[0][6]);
		tiles[0][6].place(wKnight[1]);
		wPieces.add(wKnight[1]);

		Piece[] bPawn = new Piece[8];

		Piece bKing = new Piece('k', 'b', tiles[7][4]);
		tiles[7][4].place(bKing);
		game.setBlackKing(bKing);
		bPieces.add(bKing);

		Piece bQueen = new Piece('q', 'b', tiles[7][3]);
		tiles[7][3].place(bQueen);
		bPieces.add(bQueen);

		Piece bRook[] = new Piece[2];
		bRook[0] = new Piece('r', 'b', tiles[7][0]);
		tiles[7][0].place(bRook[0]);
		bPieces.add(bRook[0]);
		bRook[1] = new Piece('r', 'b', tiles[7][7]);
		tiles[7][7].place(bRook[1]);
		bPieces.add(bRook[1]);

		Piece bBishop[] = new Piece[2];
		bBishop[0] = new Piece('b', 'b', tiles[7][2]);
		tiles[7][2].place(bBishop[0]);
		bPieces.add(bBishop[0]);
		bBishop[1] = new Piece('b', 'b', tiles[7][5]);
		tiles[7][5].place(bBishop[1]);
		bPieces.add(bBishop[1]);

		Piece bKnight[] = new Piece[2];
		bKnight[0] = new Piece('n', 'b', tiles[7][1]);
		tiles[7][1].place(bKnight[0]);
		bPieces.add(bKnight[0]);
		bKnight[1] = new Piece('n', 'b', tiles[7][6]);
		tiles[7][6].place(bKnight[1]);
		bPieces.add(bKnight[1]);
		
		for (int i = 0; i < 8; i++) { // placing all of the pawns

			wPawn[i] = new Piece('P', 'w', tiles[1][i]);
			bPawn[i] = new Piece('p', 'b', tiles[6][i]);

			tiles[1][i].place(wPawn[i]);
			tiles[6][i].place(bPawn[i]);

			wPieces.add(wPawn[i]);
			bPieces.add(bPawn[i]);
		}
	}

	public LinkedList<Piece> getBlackPieces() {
		return bPieces;
	}

	public LinkedList<Piece> getWhitePieces() {
		return wPieces;
	}

	public void move(Piece toMove, Tile target) { // adjusts tile and piece locations
		Stack<Tile> validTargets = toMove.moves();
		boolean canMove = false;
		for (Tile t : validTargets) {
			if (target.getID().equals(t.getID())) canMove = true;
		}

		if (canMove) {
			if (target.getPiece() != null) {
				Piece toKill = target.getPiece();
				LinkedList<Piece> removal = new LinkedList<Piece>();
				LinkedList<Piece> newSet = new LinkedList<Piece>();
				if (toKill.getColor() == 'w')
					removal = game.getWhitePieces();
				else 
					removal = game.getBlackPieces();
				
				for (Piece p : removal) {
					if (!p.getTile().getID().equals(toKill.getTile().getID()))
						newSet.add(p);
				}

				if (toKill.getColor() == 'w') {
					game.setWhitePieces(newSet);
				}
				else {
					game.setBlackPieces(newSet);
				}
				toKill.moveTo(null);
			} 
			toMove.getTile().empty();
			target.place(toMove);
			toMove.moveTo(target);
		}
	}

	public Tile getTile(int r, int c) {
		if (r <  0 || r > 7 || c < 0 || c > 7) return null;
		else return tiles[r][c];
	}

	public Tile[][] getTiles() {
		return tiles;
	}

	public static void main(String[] args) {
		Board tester = new Board();
		tester.reset();
		tester.draw();
	}
}