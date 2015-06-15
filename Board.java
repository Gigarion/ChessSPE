import java.util.Stack;
public class Board {
	private Tile[][] tiles = new Tile[8][8];
	private Stack<Piece> wPieces;
	private Stack<Piece> bPieces;
	private boolean flip;
	private static final double tileStartX = 0;
	private static final double tileStartY = 0;
	private static final double fTileStartX = 7;
	private static final double fTileStartY = 7;
	private static final double displacement = 1;
	
	public Board() { // constructor
		this.flip = false;
		wPieces = new Stack<Piece>();
		bPieces = new Stack<Piece>();
		for (int r = 0; r < 8; r++) { // creates 8 x 8 grid of tiles
			for (int c = 0; c < 8; c++) {
				StringBuilder tileId = new StringBuilder(2);
				tileId.append(r);
				tileId.append(c);
				this.tiles[r][c] = new Tile(tileId.toString());
			}
		}
	}

	public void draw() { // draws current state of the board
		StdDraw.setXBounds(-0.25, 7.25);
		StdDraw.setYBounds(-0.25, 7.25);
		if (!flip) {
			for (int r = 0; r < 8; r++) {
				for (int c = 0; c < 8; c++) {
					tiles[r][c].draw((tileStartX + (c * displacement)), (tileStartY + (r * displacement)));
				}
			}
		}
		else {
			for (int r = 0; r < 8; r++) {
				for (int c = 0; c < 8; c++) {
					tiles[r][c].draw((tileStartX - (c * displacement)), (tileStartY - (r * displacement)));
				}
			}
		} 
	}

	public boolean isFlipped() { // returns whether or not the board is flipped (black at bottom)
		return flip;
	}

	public void reset() { // creates a blank gameboard
		wPieces = new Stack<Piece>();
		bPieces = new Stack<Piece>();

		Piece[] wPawn = new Piece[8]; 
		
		Piece wKing = new Piece('K', 'w', "04");
		tiles[0][4].place(wKing);
		wPieces.push(wKing);

		Piece wQueen = new Piece('Q', 'w', "03");
		tiles[0][3].place(wQueen);
		wPieces.push(wQueen);
		
		Piece wRook[] = new Piece[2];
		wRook[0] = new Piece('R', 'w', "00");
		tiles[0][0].place(wRook[0]);
		wPieces.push(wRook[0]);
		wRook[1] = new Piece('R', 'w', "07");
		tiles[0][7].place(wRook[1]);
		wPieces.push(wRook[1]);
		
		Piece[] wBishop = new Piece[2];
		wBishop[0] = new Piece('B', 'w', "02");
		tiles[0][2].place(wBishop[0]);
		wPieces.push(wBishop[0]);
		wBishop[1] = new Piece('B', 'w', "05");
		tiles[0][5].place(wBishop[1]);
		wPieces.push(wBishop[1]);

		Piece[] wKnight = new Piece[2];
		wKnight[0] = new Piece('N', 'w', "01");
		tiles[0][1].place(wKnight[0]);
		wPieces.push(wKnight[0]);
		wKnight[1] = new Piece('N', 'w', "06");
		tiles[0][6].place(wKnight[1]);
		wPieces.push(wKnight[1]);

		Piece[] bPawn = new Piece[8];

		Piece bKing = new Piece('k', 'b', "74");
		tiles[7][4].place(bKing);
		bPieces.push(bKing);

		Piece bQueen = new Piece('q', 'b', "73");
		tiles[7][3].place(bQueen);
		bPieces.push(bQueen);

		Piece bRook[] = new Piece[2];
		bRook[0] = new Piece('r', 'b', "70");
		tiles[7][0].place(bRook[0]);
		bPieces.push(bRook[0]);
		bRook[1] = new Piece('r', 'b', "77");
		tiles[7][7].place(bRook[1]);
		bPieces.push(bRook[1]);

		Piece bBishop[] = new Piece[2];
		bBishop[0] = new Piece('b', 'b', "72");
		tiles[7][2].place(bBishop[0]);
		bPieces.push(bBishop[0]);
		bBishop[1] = new Piece('b', 'b', "75");
		tiles[7][5].place(bBishop[1]);
		bPieces.push(bBishop[1]);

		Piece bKnight[] = new Piece[2];
		bKnight[0] = new Piece('n', 'b', "71");
		tiles[7][1].place(bKnight[0]);
		bPieces.push(bKnight[0]);
		bKnight[1] = new Piece('n', 'b', "76");
		tiles[7][6].place(bKnight[1]);
		bPieces.push(bKnight[1]);
		
		for (int i = 0; i < 8; i++) { // placing all of the pawns
			StringBuilder wPawnPlace = new StringBuilder("1");
			StringBuilder bPawnPlace = new StringBuilder("6");

			wPawnPlace.append(i);
			bPawnPlace.append(i);

			wPawn[i] = new Piece('P', 'w', wPawnPlace.toString());
			bPawn[i] = new Piece('p', 'b', bPawnPlace.toString());

			tiles[1][i].place(wPawn[i]);
			tiles[6][i].place(bPawn[i]);

			wPieces.push(wPawn[i]);
			bPieces.push(bPawn[i]);
		}
	}

	public Stack<Piece> getBlackPieces() {
		return bPieces;
	}

	public Stack<Piece> getWhitePieces() {
		return wPieces;
	}

	public void move(Piece toMove, Tile target) { // adjusts tile and piece locations
		Stack<Tile> validTargets = toMove.moves();
		boolean canMove = false;
		for (Tile t : validTargets) {
			if (target.getId().equals(t.getId())) canMove = true;
		}

		if (canMove) {
			if (target.getPiece() != null) target.getPiece().moveTo(null);
			toMove.getTile().empty();
			target.place(toMove);
			toMove.moveTo(target);
		}
	}

	public Tile getTile(int r, int c) {
		if (r <  0 || r > 7 || c < 0 || c > 7) return null;
		else return tiles[r][c];
	}
}