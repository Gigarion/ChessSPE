public class Board {
	private Tile[][] tiles = new Tile[8][8];
	private boolean flip;
	private static final double tileStartX = 0;
	private static final double tileStartY = 0;
	private static final double fTileStartX = 1;
	private static final double fTileStartY = 1;
	private static final double displacement = 0.125;
	
	public Board() {
		this.flip = false;
		for (int r = 0; r < 8; r++) {
			for (int c = 0; c < 8; c++) {
				StringBuilder tileId = new StringBuilder(2);
				tileId.append(r);
				tileId.append(c);
				this.tiles[r][c] = new Tile(tileId.toString());
			}
		}
	}

	public void draw() {
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

	public boolean isFlipped() {
		return flip;
	}

	public void reset() {
		
		Piece wPawn = new Piece(); // I need to see the piece constructor for this
		Piece wKing = 
		Piece wQueen
		Piece wRook
		Piece wBishop
		Piece wKnight

		Piece bPawn = new Piece();
		
		for (int i = 0; i < 8; i++) {
			tiles[1][i].place(wPawn);
			tiles[6][i].place(bPawn);

		}
	}
}