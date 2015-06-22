import java.util.Stack;

public class Piece
{

/*
	Regarding Piece Type:

	Capital letters correspond to white pieces, lowercase to black.

    P = white pawn          p = black pawn
    N = white knight        n = black knight
    B = white bishop        b = black bishop
    R = white rook          r = black rook
    Q = white queen         q = black queen
    K = white king          k = black king

*/

	// piece type
	private char type;

	// piece color
	private char color;

	// piece location
	private Tile tile;

	// which game the pieces are from
	private Game game;

	// list of all the white piece types
	private static String wPieces = "PNBRQK";

	// list of all the black piece types
	private static String bPieces = "pnbrqk";

	public Piece(char type, char color, Tile tile) {
		this.type  = type;
		this.color = color;
		this.tile  = tile;
	}

	public Piece() {}

	// returns the piece type
	public char getType() { return type; }

	// sets the piece type
	public void setType(char t) { type = t; }

	// returns the piece color
	public char getColor() { return color; }

	// sets the piece color
	public void setColor(char c) { color = c; }

	// returns the piece tile
	public Tile getTile() { return tile; }

	// moves the piece to another tile
	public void moveTo(Tile tile) { this.tile = tile; }

	// returns the current piece's game
	public Game getGame() { return game; }

	// sets the piece's game
	public void setGame(Game game) { this.game = game; }

	// returns stack of possible moves
	public Stack<Tile> moves() {

		Stack<Tile> stack = new Stack<Tile>();
		char piecetype    = type;
		int rank          = Character.getNumericValue(tile.getID().charAt(0));
		int file          = Character.getNumericValue(tile.getID().charAt(1));

		// possible moves for a white pawn
		if (piecetype == 'P') {

			// diagonal left
			Tile left = game.getTile(rank+1, file-1);

			// diagonal right
			Tile right = game.getTile(rank+1, file+1);

			// middle
			Tile middle = game.getTile(rank+1, file);

			if (left != null && left.getPiece() != null) {
				if (left.getPiece().getColor() == 'b') {
					stack.push(left);
				}
			}

			if (right != null && right.getPiece() != null) {
				if (right.getPiece().getColor() == 'b')
					stack.push(right);
			}

			if (middle != null && middle.getPiece() == null) {
				stack.push(middle);
			}
			
			Tile lunge = game.getTile(rank + 2, file);
			if (lunge != null && middle != null && middle.getPiece() == null && lunge.getPiece() == null && rank == 1) {
				stack.push(lunge);
			}

			// adds the ep square if one exists
			if (game.getEP() != null) {
				Tile eptile = game.getEP();
				int eprank  = Character.getNumericValue(eptile.getID().charAt(0));
				int epfile  = Character.getNumericValue(eptile.getID().charAt(1));
				if ((Math.abs(epfile-file) == 1) && (eprank-rank == 1)) {
					stack.push(eptile);
				}
			}

		}

		// possible moves for a black pawn
		else if (piecetype == 'p') {
			
			// diagonal left
			Tile left = game.getTile(rank-1, file-1);

			// diagonal right
			Tile right = game.getTile(rank-1, file+1);

			// middle
			Tile middle = game.getTile(rank-1, file);

			if (left != null && left.getPiece() != null) {
				if (left.getPiece().getColor() == 'w')
					stack.push(left);
			}

			if (right != null && right.getPiece() != null) {
				if (right.getPiece().getColor() == 'w')
					stack.push(right);
			}

			if (middle != null && middle.getPiece() == null) {
				stack.push(middle);
			}

			Tile lunge = game.getTile(rank - 2, file);
			if (lunge != null && middle != null && middle.getPiece() == null && lunge.getPiece() == null && rank == 6) {
				stack.push(lunge);
			}

			// adds the ep square if one exists
			if (game.getEP() != null) {
				Tile eptile = game.getEP();
				int eprank  = Character.getNumericValue(eptile.getID().charAt(0));
				int epfile  = Character.getNumericValue(eptile.getID().charAt(1));
				if ((Math.abs(epfile-file) == 1) && (eprank - rank == -1)) {
					stack.push(eptile);
				}
			}

		}

		// possible moves for a white knight
		else if (piecetype == 'N') {

			// moving clockwise from the bottom-left square...
			Tile bottomleft  = game.getTile(rank-2, file-1);
			Tile leftbottom  = game.getTile(rank-1, file-2);
			Tile lefttop     = game.getTile(rank+1, file-2);
			Tile topleft     = game.getTile(rank+2, file-1);
			Tile topright    = game.getTile(rank+2, file+1);
			Tile righttop    = game.getTile(rank+1, file+2);
			Tile rightbottom = game.getTile(rank-1, file+2);
			Tile bottomright = game.getTile(rank-2, file+1);

			if ((bottomleft != null) && ((bottomleft.getPiece() == null) || (bottomleft.getPiece().getColor() == 'b'))) { stack.push(bottomleft); }

			if ((leftbottom != null) && ((leftbottom.getPiece() == null) || (leftbottom.getPiece().getColor() == 'b'))) { stack.push(leftbottom); }

			if ((lefttop != null) && ((lefttop.getPiece() == null) || (lefttop.getPiece().getColor() == 'b'))) { stack.push(lefttop); }

			if ((topleft != null) && ((topleft.getPiece() == null) || (topleft.getPiece().getColor() == 'b'))) { stack.push(topleft); }

			if ((topright != null) && ((topright.getPiece() == null) || (topright.getPiece().getColor() == 'b'))) { stack.push(topright); }

			if ((righttop != null) && ((righttop.getPiece() == null) || (righttop.getPiece().getColor() == 'b'))) { stack.push(righttop); }

			if ((rightbottom != null) && ((rightbottom.getPiece() == null) || (rightbottom.getPiece().getColor() == 'b'))) { stack.push(rightbottom); }

			if ((bottomright != null) && ((bottomright.getPiece() == null) || (bottomright.getPiece().getColor() == 'b'))) { stack.push(bottomright); }
		}

		// possible moves for a black knight
		else if (piecetype == 'n') {

			// moving clockwise from the bottom-left square...
			Tile bottomleft  = game.getTile(rank-2, file-1);
			Tile leftbottom  = game.getTile(rank-1, file-2);
			Tile lefttop     = game.getTile(rank+1, file-2);
			Tile topleft     = game.getTile(rank+2, file-1);
			Tile topright    = game.getTile(rank+2, file+1);
			Tile righttop    = game.getTile(rank+1, file+2);
			Tile rightbottom = game.getTile(rank-1, file+2);
			Tile bottomright = game.getTile(rank-2, file+1);

			if ((bottomleft != null) && ((bottomleft.getPiece() == null) || (bottomleft.getPiece().getColor() == 'w'))) { stack.push(bottomleft); }

			if ((leftbottom != null) && ((leftbottom.getPiece() == null) || (leftbottom.getPiece().getColor() == 'w'))) { stack.push(leftbottom); }

			if ((lefttop != null) && ((lefttop.getPiece() == null) || (lefttop.getPiece().getColor() == 'w'))) { stack.push(lefttop); }

			if ((topleft != null) && ((topleft.getPiece() == null) || (topleft.getPiece().getColor() == 'w'))) { stack.push(topleft); }

			if ((topright != null) && ((topright.getPiece() == null) || (topright.getPiece().getColor() == 'w'))) { stack.push(topright); }

			if ((righttop != null) && ((righttop.getPiece() == null) || (righttop.getPiece().getColor() == 'w'))) { stack.push(righttop); }

			if ((rightbottom != null) && ((rightbottom.getPiece() == null) || (rightbottom.getPiece().getColor() == 'w'))) { stack.push(rightbottom); }

			if ((bottomright != null) && ((bottomright.getPiece() == null) || (bottomright.getPiece().getColor() == 'w'))) { stack.push(bottomright); }
		}

		// possible moves for a white bishop
		else if (piecetype == 'B') {
			boolean stopCatch = false;
			int fTemp = file;
			int rTemp = rank;
			for (int f = 1; (fTemp < 8 && rTemp < 8); f++) {
				fTemp = file + f;
				rTemp = rank + f;
				if (!stopCatch) {
					Tile toStack = game.getTile(rTemp, fTemp);
					if (toStack != null) {
						if (toStack.getPiece() != null) {
							if (wPieces.indexOf(toStack.getPiece().getType()) != -1) {
								stopCatch = true;
							}
							else if (bPieces.indexOf(toStack.getPiece().getType()) != -1) {
								stack.push(toStack);
								stopCatch = true;
							}
						}
						else {
							stack.push(toStack);
						}
					}
				}
			}

			stopCatch = false;
			fTemp = file;
			rTemp = rank;
			for (int f = 1; (fTemp < 8 && rTemp >= 0); f++) {
				fTemp = file + f;
				rTemp = rank - f;
				if (!stopCatch) {
					Tile toStack = game.getTile(rTemp, fTemp);
					if (toStack != null) {
						if (toStack.getPiece() != null) {				
							if (wPieces.indexOf(toStack.getPiece().getType()) != -1) {
									stopCatch = true;
								}
							else if (bPieces.indexOf(toStack.getPiece().getType()) != -1) {
								stack.push(toStack);
								stopCatch = true;
							}
						}
						else {
							stack.push(toStack);
						}
					}
				}
			}

			stopCatch = false;
			rTemp = rank;
			for (int f = 1; (rTemp < 8 && fTemp >= 0); f++) {
				fTemp = file - f;
				rTemp = rank + f;
				if (!stopCatch) {
					Tile toStack = game.getTile(rTemp, fTemp);
					if (toStack != null) {	
						if (toStack.getPiece() != null) {
							if (wPieces.indexOf(toStack.getPiece().getType()) != -1) {
								stopCatch = true;
							}
							else if (bPieces.indexOf(toStack.getPiece().getType()) != -1) {
								stack.push(toStack);
								stopCatch = true;
							}
						}
						else {
							stack.push(toStack);
						}
					}
				}
			}

			stopCatch = false;
			rTemp = rank;
			for (int f = 1; (rTemp >= 0 && fTemp >= 0); f++) {
				fTemp = file - f;
				rTemp = rank - f;
				if (!stopCatch) {
					Tile toStack = game.getTile(rTemp, fTemp);
					if (toStack != null) {	
						if (toStack.getPiece() != null) {					
							if (wPieces.indexOf(toStack.getPiece().getType()) != -1) {
								stopCatch = true;
							}
							else if (bPieces.indexOf(toStack.getPiece().getType()) != -1) {
								stack.push(toStack);
								stopCatch = true;
							}
						}
						else {
							stack.push(toStack);
						}
					}
				}
			}
		}

		// possible moves for a black bishop
		else if (piecetype == 'b') {
			boolean stopCatch = false;
			int fTemp = file;
			int rTemp = rank;
			for (int f = 1; (fTemp < 8 && rTemp < 8); f++) {
				fTemp = file + f;
				rTemp = rank + f;
				if (!stopCatch) {
					Tile toStack = game.getTile(rTemp, fTemp);
					if (toStack != null) {
						if (toStack.getPiece() != null) {										
							if (bPieces.indexOf(toStack.getPiece().getType()) != -1) {
								stopCatch = true;
							}
							else if (wPieces.indexOf(toStack.getPiece().getType()) != -1) {
								stack.push(toStack);
								stopCatch = true;
							}
						}
						else {
							stack.push(toStack);
						}
					}
				}
			}

			stopCatch = false;
			fTemp = file;
			rTemp = rank;
			for (int f = 1; (fTemp < 8 && rTemp >= 0); f++) {
				fTemp = file + f;
				rTemp = rank - f;
				if (!stopCatch) {
					Tile toStack = game.getTile(rTemp, fTemp);
					if (toStack != null) {
						if (toStack.getPiece() != null) {	
							if (bPieces.indexOf(toStack.getPiece().getType()) != -1) {
									stopCatch = true;
								}
							else if (wPieces.indexOf(toStack.getPiece().getType()) != -1) {
								stack.push(toStack);
								stopCatch = true;
							}
						}
						else {
							stack.push(toStack);
						}
					}
				}
			}

			stopCatch = false;
			rTemp = rank;
			for (int f = 1; (rTemp < 8 && fTemp >= 0); f++) {
				fTemp = file - f;
				rTemp = rank + f;
				if (!stopCatch) {
					Tile toStack = game.getTile(rTemp, fTemp);
					if (toStack != null) {
						if (toStack.getPiece() != null) {
							if (bPieces.indexOf(toStack.getPiece().getType()) != -1) {
								stopCatch = true;
							}
							else if (wPieces.indexOf(toStack.getPiece().getType()) != -1) {
								stack.push(toStack);
								stopCatch = true;
							}
						}
						else {
							stack.push(toStack);
						}
					}
				}
			}

			stopCatch = false;
			rTemp = rank;
			for (int f = 1; (fTemp >= 0 && rTemp >= 0); f++) {
				fTemp = file - f;
				rTemp = rank - f;
				if (!stopCatch) {
					Tile toStack = game.getTile(rTemp, fTemp);
					if (toStack != null) {
						if (toStack.getPiece() != null) {
							if (bPieces.indexOf(toStack.getPiece().getType()) != -1) {
								stopCatch = true;
							}
							else if (wPieces.indexOf(toStack.getPiece().getType()) != -1) {
								stack.push(toStack);
								stopCatch = true;
							}
						}
						else {
							stack.push(toStack);
						}
					}
				}
			}
		}

		// possible moves for a white rook
		else if (piecetype == 'R') {
			boolean stopCatch = false;
			int fTemp = file;
			for (int f = 1; fTemp < 8; f++) {
				fTemp = file + f;
				if (!stopCatch) {
					Tile toStack = game.getTile(rank, fTemp);
					if (toStack != null) {
						if (toStack.getPiece() != null) {
							if (wPieces.indexOf(toStack.getPiece().getType()) != -1) {
								stopCatch = true;
							}
							else if (bPieces.indexOf(toStack.getPiece().getType()) != -1) {
								stack.push(toStack);
								stopCatch = true;
							}
						}
						else {
							stack.push(toStack);
						}
					}
				}
			}

			stopCatch = false;
			fTemp = file;
			for (int f = 1; fTemp >= 0; f++) {
				fTemp = file - f;
				if (!stopCatch) {
					Tile toStack = game.getTile(rank, fTemp);
					if (toStack != null) {
						if (toStack.getPiece() != null) {
							if (wPieces.indexOf(toStack.getPiece().getType()) != -1) {
									stopCatch = true;
								}
							else if (bPieces.indexOf(toStack.getPiece().getType()) != -1) {
								stack.push(toStack);
								stopCatch = true;
							}
						}
						else {
							stack.push(toStack);
						}
					}
				}
			}

			stopCatch = false;
			int rTemp = rank;
			for (int r = 1; rTemp < 8; r++) {
				rTemp = rank + r;
				if (!stopCatch) {
					Tile toStack = game.getTile(rTemp, file);
					if (toStack != null) {
						if (toStack.getPiece() != null) {
							if (wPieces.indexOf(toStack.getPiece().getType()) != -1) {
								stopCatch = true;
							}
							else if (bPieces.indexOf(toStack.getPiece().getType()) != -1) {
								stack.push(toStack);
								stopCatch = true;
							}
						}
						else {
							stack.push(toStack);
						}
					}
				}
			}

			stopCatch = false;
			rTemp = rank;
			for (int r = 1; rTemp >= 0; r++) {
				rTemp = rank - r;
				if (!stopCatch) {
					Tile toStack = game.getTile(rTemp, file);
					if (toStack != null) {
						if (toStack.getPiece() != null) {
							if (wPieces.indexOf(toStack.getPiece().getType()) != -1) {
								stopCatch = true;
							}
							else if (bPieces.indexOf(toStack.getPiece().getType()) != -1) {
								stack.push(toStack);
								stopCatch = true;
							}
						}
						else {
							stack.push(toStack);
						}
					}
				}
			}
		}

		// possible moves for a black rook
		else if (piecetype == 'r') {
			boolean stopCatch = false;
			int fTemp = file;
			for (int f = 1; fTemp < 8; f++) {
				fTemp = file + f;
				if (!stopCatch) {
					Tile toStack = game.getTile(rank, fTemp);
					if (toStack != null) {
						if (toStack.getPiece() != null) {
							if (bPieces.indexOf(toStack.getPiece().getType()) != -1) {
								stopCatch = true;
							}
							else if (wPieces.indexOf(toStack.getPiece().getType()) != -1) {
								stack.push(toStack);
								stopCatch = true;
							}
						}
						else {
							stack.push(toStack);
						}
					}
				}
			}

			stopCatch = false;
			fTemp = file;
			for (int f = 1; fTemp >= 0; f++) {
				fTemp = file - f;
				if (!stopCatch) {
					Tile toStack = game.getTile(rank, fTemp);
					if (toStack != null) {
						if (toStack.getPiece() != null) {
							if (bPieces.indexOf(toStack.getPiece().getType()) != -1) {
									stopCatch = true;
								}
							else if (wPieces.indexOf(toStack.getPiece().getType()) != -1) {
								stack.push(toStack);
								stopCatch = true;
							}
						}
						else {
							stack.push(toStack);
						}
					}
				}
			}

			stopCatch = false;
			int rTemp = rank;
			for (int r = 1; rTemp < 8; r++) {
				rTemp = rank + r;
				if (!stopCatch) {
					Tile toStack = game.getTile(rTemp, file);
					if (toStack != null) {
						if (toStack.getPiece() != null) {
							if (bPieces.indexOf(toStack.getPiece().getType()) != -1) {
								stopCatch = true;
							}
							else if (wPieces.indexOf(toStack.getPiece().getType()) != -1) {
								stack.push(toStack);
								stopCatch = true;
							}
						}
						else {
							stack.push(toStack);
						}
					}
				}
			}

			stopCatch = false;
			rTemp = rank;
			for (int r = 1; rTemp >= 0; r++) {
				rTemp = rank - r;
				if (!stopCatch) {
					Tile toStack = game.getTile(rTemp, file);
					if (toStack != null) {
						if (toStack.getPiece() != null) {
							if (bPieces.indexOf(toStack.getPiece().getType()) != -1) {
								stopCatch = true;
							}
							else if (wPieces.indexOf(toStack.getPiece().getType()) != -1) {
								stack.push(toStack);
								stopCatch = true;
							}
						}
						else {
							stack.push(toStack);
						}
					}
				}
			}
		}

		// possible moves for a white queen
		else if (piecetype == 'Q') {
			boolean stopCatch = false;
			int fTemp = file;
			int rTemp = rank;
			for (int f = 1; (fTemp < 8 && rTemp < 8); f++) {
				fTemp = file + f;
				rTemp = rank + f;
				if (!stopCatch) {
					Tile toStack = game.getTile(rTemp, fTemp);
					if (toStack != null) {
						if (toStack.getPiece() != null) {					
							if (wPieces.indexOf(toStack.getPiece().getType()) != -1) {
								stopCatch = true;
							}
							else if (bPieces.indexOf(toStack.getPiece().getType()) != -1) {
								stack.push(toStack);
								stopCatch = true;
							}
						}
						else {
							stack.push(toStack);
						}
					}
				}
			}

			stopCatch = false;
			fTemp = file;
			rTemp = rank;
			for (int f = 1; (rTemp >= 0 && fTemp < 8); f++) {
				fTemp = file + f;
				rTemp = rank - f;
				if (!stopCatch) {
					Tile toStack = game.getTile(rTemp, fTemp);
					if (toStack != null) {
						if (toStack.getPiece() != null) {
							if (wPieces.indexOf(toStack.getPiece().getType()) != -1) {
								stopCatch = true;
							}
							else if (bPieces.indexOf(toStack.getPiece().getType()) != -1) {
								stack.push(toStack);
								stopCatch = true;
							}
						}
						else {
							stack.push(toStack);
						}
					}
				}
			}

			stopCatch = false;
			rTemp = rank;
			fTemp = file;
			for (int f = 1; (fTemp >= 0 && rTemp < 8); f++) {
				fTemp = file - f;
				rTemp = rank + f;
				if (!stopCatch) {
					Tile toStack = game.getTile(rTemp, fTemp);
					if (toStack != null) {
						if (toStack.getPiece() != null) {
							if (wPieces.indexOf(toStack.getPiece().getType()) != -1) {
								stopCatch = true;
							}
							else if (bPieces.indexOf(toStack.getPiece().getType()) != -1) {
								stack.push(toStack);
								stopCatch = true;
							}
						}
						else {
							stack.push(toStack);
						}
					}
				}
			}

			stopCatch = false;
			rTemp = rank;
			fTemp = file;
			for (int f = 1; (fTemp >= 0 && rTemp >= 0); f++) {
				fTemp = file - f;
				rTemp = rank - f;
				if (!stopCatch) {
					Tile toStack = game.getTile(rTemp, fTemp);
					if (toStack != null) {
						if (toStack.getPiece() != null) {
							if (wPieces.indexOf(toStack.getPiece().getType()) != -1) {
								stopCatch = true;
							}
							else if (bPieces.indexOf(toStack.getPiece().getType()) != -1) {
								stack.push(toStack);
								stopCatch = true;
							}
						}
						else {
							stack.push(toStack);
						}
					}
				}
			}

			stopCatch = false;
			fTemp = file;
			for (int f = 1; fTemp < 8; f++) {
				fTemp = file + f;
				if (!stopCatch) {
					Tile toStack = game.getTile(rank, fTemp);
					if (toStack != null) {
						if (toStack.getPiece() != null) {
							if (wPieces.indexOf(toStack.getPiece().getType()) != -1) {
								stopCatch = true;
							}
							else if (bPieces.indexOf(toStack.getPiece().getType()) != -1) {
								stack.push(toStack);
								stopCatch = true;
							}
						}
						else {
							stack.push(toStack);
						}
					}
				}
			}

			stopCatch = false;
			fTemp = file;
			for (int f = 1; fTemp >= 0; f++) {
				fTemp = file - f;
				if (!stopCatch) {
					Tile toStack = game.getTile(rank, fTemp);
					if (toStack != null) {
						if (toStack.getPiece() != null) {
							if (wPieces.indexOf(toStack.getPiece().getType()) != -1) {
									stopCatch = true;
								}
							else if (bPieces.indexOf(toStack.getPiece().getType()) != -1) {
								stack.push(toStack);
								stopCatch = true;
							}
						}
						else {
							stack.push(toStack);
						}
					}
				}
			}

			stopCatch = false;
			rTemp = rank;
			for (int r = 1; rTemp < 8; r++) {
				rTemp = rank + r;
				if (!stopCatch) {
					Tile toStack = game.getTile(rTemp, file);
					if (toStack != null) {
						if (toStack.getPiece() != null) {
							if (wPieces.indexOf(toStack.getPiece().getType()) != -1) {
								stopCatch = true;
							}
							else if (bPieces.indexOf(toStack.getPiece().getType()) != -1) {
								stack.push(toStack);
								stopCatch = true;
							}
						}
						else {
							stack.push(toStack);
						}
					}
				}
			}

			stopCatch = false;
			rTemp = rank;
			for (int r = 1; rTemp >= 0; r++) {
				rTemp = rank - r;
				if (!stopCatch) {
					Tile toStack = game.getTile(rTemp, file);
					if (toStack != null) {
						if (toStack.getPiece() != null) {
							if (wPieces.indexOf(toStack.getPiece().getType()) != -1) {
								stopCatch = true;
							}
							else if (bPieces.indexOf(toStack.getPiece().getType()) != -1) {
								stack.push(toStack);
								stopCatch = true;
							}
						}
						else {
							stack.push(toStack);
						}
					}
				}
			}
		}

		// possible moves for a black queen
		else if (piecetype == 'q') {
			boolean stopCatch = false;
			int fTemp = file;
			int rTemp = rank;
			for (int f = 1; (fTemp < 8 && rTemp < 8); f++) {
				fTemp = file + f;
				rTemp = rank + f;
				if (!stopCatch) {
					Tile toStack = game.getTile(rTemp, fTemp);
					if (toStack != null) {
						if (toStack.getPiece() != null) {
							if (bPieces.indexOf(toStack.getPiece().getType()) != -1) {
								stopCatch = true;
							}
							else if (wPieces.indexOf(toStack.getPiece().getType()) != -1) {
								stack.push(toStack);
								stopCatch = true;
							}
						}
						else {
							stack.push(toStack);
						}
					}
				}
			}

			stopCatch = false;
			fTemp = file;
			rTemp = rank;
			for (int f = 1; (rTemp >= 0 && fTemp < 8); f++) {
				fTemp = file + f;
				rTemp = rank - f;
				if (!stopCatch) {
					Tile toStack = game.getTile(rTemp, fTemp);
					if (toStack != null) {
						if (toStack.getPiece() != null) {
							if (bPieces.indexOf(toStack.getPiece().getType()) != -1) {
									stopCatch = true;
								}
							else if (wPieces.indexOf(toStack.getPiece().getType()) != -1) {
								stack.push(toStack);
								stopCatch = true;
							}
						}
						else {
							stack.push(toStack);
						}
					}
				}
			}

			stopCatch = false;
			rTemp = rank;
			fTemp = file;
			for (int f = 1; (fTemp >= 0 && rTemp < 8); f++) {
				fTemp = file - f;
				rTemp = rank + f;
				if (!stopCatch) {
					Tile toStack = game.getTile(rTemp, fTemp);
					if (toStack != null) {
						if (toStack.getPiece() != null) {
							if (bPieces.indexOf(toStack.getPiece().getType()) != -1) {
								stopCatch = true;
							}
							else if (wPieces.indexOf(toStack.getPiece().getType()) != -1) {
								stack.push(toStack);
								stopCatch = true;
							}
						}
						else {
							stack.push(toStack);
						}
					}
				}
			}

			stopCatch = false;
			rTemp = rank;
			for (int f = 1; (fTemp >= 0 && rTemp >=0); f++) {
				fTemp = file - f;
				rTemp = rank - f;
				if (!stopCatch) {
					Tile toStack = game.getTile(rTemp, fTemp);
					if (toStack != null) {
						if (toStack.getPiece() != null) {
							if (bPieces.indexOf(toStack.getPiece().getType()) != -1) {
								stopCatch = true;
							}
							else if (wPieces.indexOf(toStack.getPiece().getType()) != -1) {
								stack.push(toStack);
								stopCatch = true;
							}
						}
						else {
							stack.push(toStack);
						}
					}
				}
			}

			stopCatch = false;
			fTemp = file;
			for (int f = 1; fTemp < 8; f++) {
				fTemp = file + f;
				if (!stopCatch) {
					Tile toStack = game.getTile(rank, fTemp);
					if (toStack != null) {
						if (toStack.getPiece() != null) {
							if (bPieces.indexOf(toStack.getPiece().getType()) != -1) {
								stopCatch = true;
							}
							else if (wPieces.indexOf(toStack.getPiece().getType()) != -1) {
								stack.push(toStack);
								stopCatch = true;
							}
						}
						else {
							stack.push(toStack);
						}
					}
				}
			}

			stopCatch = false;
			fTemp = file;
			for (int f = 1; fTemp >= 0; f++) {
				fTemp = file - f;
				if (!stopCatch) {
					Tile toStack = game.getTile(rank, fTemp);
					if (toStack != null) {
						if (toStack.getPiece() != null) {
							if (bPieces.indexOf(toStack.getPiece().getType()) != -1) {
									stopCatch = true;
								}
							else if (wPieces.indexOf(toStack.getPiece().getType()) != -1) {
								stack.push(toStack);
								stopCatch = true;
							}
						}
						else {
							stack.push(toStack);
						}
					}
				}
			}

			stopCatch = false;
			rTemp = rank;
			for (int r = 1; rTemp < 8; r++) {
				rTemp = rank + r;
				if (!stopCatch) {
					Tile toStack = game.getTile(rTemp, file);
					if (toStack != null) {
						if (toStack.getPiece() != null) {
							if (bPieces.indexOf(toStack.getPiece().getType()) != -1) {
								stopCatch = true;
							}
							else if (wPieces.indexOf(toStack.getPiece().getType()) != -1) {
								stack.push(toStack);
								stopCatch = true;
							}
						}
						else {
							stack.push(toStack);
						}
					}
				}
			}

			stopCatch = false;
			rTemp = rank;
			for (int r = 1; rTemp >= 0; r++) {
				rTemp = rank - r;
				if (!stopCatch) {
					Tile toStack = game.getTile(rTemp, file);
					if (toStack != null) {
						if (toStack.getPiece() != null) {
							if (bPieces.indexOf(toStack.getPiece().getType()) != -1) {
								stopCatch = true;
							}
							else if (wPieces.indexOf(toStack.getPiece().getType()) != -1) {
								stack.push(toStack);
								stopCatch = true;
							}
						}
						else {
							stack.push(toStack);
						}
					}
				}
			}
		}

		// possible moves for a white king
		else if (piecetype == 'K') {
			int fTemp = file;
			int rTemp = rank;
			fTemp = file + 1;
			rTemp = rank + 1;
			Tile toStack = game.getTile(rTemp, fTemp);
			
			if (toStack != null) {
				if (toStack.getPiece() != null) {
					if (bPieces.indexOf(toStack.getPiece().getType()) != -1) {
						stack.push(toStack);
					}
				}
				else {
					stack.push(toStack);
				}
			}

			fTemp = file + 1;
			rTemp = rank - 1;
			toStack = game.getTile(rTemp, fTemp);
			
			if (toStack != null) {
				if (toStack.getPiece() != null) {
					if (bPieces.indexOf(toStack.getPiece().getType()) != -1) {
						stack.push(toStack);
					}
				}
				else {
					stack.push(toStack);
				}
			}

			fTemp = file - 1;
			rTemp = rank - 1;
			toStack = game.getTile(rTemp, fTemp);
			
			if (toStack != null) {
				if (toStack.getPiece() != null) {
					if (bPieces.indexOf(toStack.getPiece().getType()) != -1) {
						stack.push(toStack);
					}
				}
				else {
					stack.push(toStack);
				}
			}

			fTemp = file - 1;
			rTemp = rank + 1;
			toStack = game.getTile(rTemp, fTemp);
			
			if (toStack != null) {
				if (toStack.getPiece() != null) {
					if (bPieces.indexOf(toStack.getPiece().getType()) != -1) {
						stack.push(toStack);
					}
				}
				else {
					stack.push(toStack);
				}
			}

			fTemp = file + 1;
			toStack = game.getTile(rank, fTemp);
			if (toStack != null) {
				if (toStack.getPiece() != null) {
					if (bPieces.indexOf(toStack.getPiece().getType()) != -1) {
						stack.push(toStack);
					}
				}
				else {
					stack.push(toStack);
				}
			}

			fTemp = file - 1;
			toStack = game.getTile(rank, fTemp);
			if (toStack != null) {
				if (toStack.getPiece() != null) {
					if (bPieces.indexOf(toStack.getPiece().getType()) != -1) {
						stack.push(toStack);
					}
				}
				else {
					stack.push(toStack);
				}
			}

			rTemp = rank - 1;
			toStack = game.getTile(rTemp, file);
			if (toStack != null) {
				if (toStack.getPiece() != null) {
					if (bPieces.indexOf(toStack.getPiece().getType()) != -1) {
						stack.push(toStack);
					}
				}
				else {
					stack.push(toStack);
				}
			}

			rTemp = rank + 1;
			toStack = game.getTile(rTemp, file);
			if (toStack != null) {
				if (toStack.getPiece() != null) {
					if (bPieces.indexOf(toStack.getPiece().getType()) != -1) {
						stack.push(toStack);
					}
				}
				else {
					stack.push(toStack);
				}
			}

			if (!tile.isAttacked() && game.getCastlingRights()[0] && !game.getTile(0, 5).isAttacked(this) 
        	&& !game.getTile(0, 6).isAttacked(this) && game.getTile(0, 5).getPiece() == null
			        && game.getTile(0, 6).getPiece() == null)
			{
    			stack.push(game.getTile(0, 6));
			}

			if (!tile.isAttacked() && game.getCastlingRights()[1] && !game.getTile(0, 3).isAttacked(this) 
			        && !game.getTile(0, 2).isAttacked(this) && game.getTile(0, 1).getPiece() == null
			        && game.getTile(0, 2).getPiece() == null && game.getTile(0, 3).getPiece() == null)
			{
			    stack.push(game.getTile(0, 2));
			}
		}

		// possible moves for a black king
		else if (piecetype == 'k') {
			boolean stopCatch = false;
			int fTemp = file;
			int rTemp = rank;
			fTemp = file + 1;
			rTemp = rank + 1;
			Tile toStack = game.getTile(rTemp, fTemp);
			
			if (toStack != null) {
				if (toStack.getPiece() != null) {
					if (bPieces.indexOf(toStack.getPiece().getType()) != -1) {
						stopCatch = true;
					}
					else if (wPieces.indexOf(toStack.getPiece().getType()) != -1) {
						stack.push(toStack);
						stopCatch = true;
					}
				}
				else {
					if (game.getTile(rTemp, fTemp).isAttacked()) {
						stopCatch = true;
					} 
					else stack.push(toStack);
				}
			}

			fTemp = file + 1;
			rTemp = rank - 1;
			toStack = game.getTile(rTemp, fTemp);
			
			if (toStack != null) {
				if (toStack.getPiece() != null) {
					if (bPieces.indexOf(toStack.getPiece().getType()) != -1) {
						stopCatch = true;
					}
					else if (wPieces.indexOf(toStack.getPiece().getType()) != -1) {
						stack.push(toStack);
						stopCatch = true;
					}
				}
				else {
					if (game.getTile(rTemp, fTemp).isAttacked()) {
						stopCatch = true;
					} 
					else stack.push(toStack);
				}
			}

			fTemp = file - 1;
			rTemp = rank - 1;
			toStack = game.getTile(rTemp, fTemp);
			
			if (toStack != null) {
				if (toStack.getPiece() != null) {
					if (bPieces.indexOf(toStack.getPiece().getType()) != -1) {
						stopCatch = true;
					}
					else if (wPieces.indexOf(toStack.getPiece().getType()) != -1) {
						stack.push(toStack);
						stopCatch = true;
					}
				}
				else {
					if (game.getTile(rTemp, fTemp).isAttacked()) stopCatch = true;
					else stack.push(toStack);
				}
			}

			fTemp = file - 1;
			rTemp = rank + 1;
			toStack = game.getTile(rTemp, fTemp);
			
			if (toStack != null) {
				if (toStack.getPiece() != null) {
					if (bPieces.indexOf(toStack.getPiece().getType()) != -1) {
						stopCatch = true;
					}
					else if (wPieces.indexOf(toStack.getPiece().getType()) != -1) {
						stack.push(toStack);
						stopCatch = true;
					}
				}
				else {
					if (game.getTile(rTemp, fTemp).isAttacked()) stopCatch = true;
					else stack.push(toStack);
				}
			}

			fTemp = file + 1;
			toStack = game.getTile(rank, fTemp);
			if (toStack != null) {
				if (toStack.getPiece() != null) {
					if (wPieces.indexOf(toStack.getPiece().getType()) != -1) {
						stack.push(toStack);
					}
				}
				else {
					stack.push(toStack);
				}
			}

			fTemp = file - 1;
			toStack = game.getTile(rank, fTemp);
			if (toStack != null) {
				if (toStack.getPiece() != null) {
					if (wPieces.indexOf(toStack.getPiece().getType()) != -1) {
						stack.push(toStack);
					}
				}
				else {
					stack.push(toStack);
				}
			}

			rTemp = rank - 1;
			toStack = game.getTile(rTemp, file);
			if (toStack != null) {
				if (toStack.getPiece() != null) {
					if (wPieces.indexOf(toStack.getPiece().getType()) != -1) {
						stack.push(toStack);
					}
				}
				else {
					stack.push(toStack);
				}
			}

			rTemp = rank + 1;
			toStack = game.getTile(rTemp, file);
			if (toStack != null) {
				if (toStack.getPiece() != null) {
					if (wPieces.indexOf(toStack.getPiece().getType()) != -1) {
						stack.push(toStack);
					}
				}
				else {
					stack.push(toStack);
				}
			}

		      if (!tile.isAttacked() && game.getCastlingRights()[2] && !game.getTile(7, 5).isAttacked(this) 
		              && !game.getTile(7, 6).isAttacked(this) && game.getTile(7, 5).getPiece() == null
			        && game.getTile(7, 6).getPiece() == null)
		      {
		          stack.push(game.getTile(7, 6));
		      }
		      
		      if (!tile.isAttacked() && game.getCastlingRights()[3] && !game.getTile(7, 3).isAttacked(this) 
		              && !game.getTile(7, 2).isAttacked(this) && game.getTile(7, 1).getPiece() == null
			        && game.getTile(7, 2).getPiece() == null && game.getTile(7, 3).getPiece() == null)
		      {
		          stack.push(game.getTile(7, 2));
		      }
		}

		Stack<Tile> thinStack = new Stack<Tile>();

		if (type == 'K' || type == 'k') { 
			for (Tile t : stack) {
				tile.empty();
				if (!t.isAttacked(this))
					thinStack.push(t);
				tile.place(this);
			}
		}

		// makes sure that piece moves do not put the king in check
		else if (color == 'w') {
			Piece king = game.getWhiteKing();
			Tile kingSpot = king.getTile();
			Piece temp = null;
			for (Tile t : stack) {
				if (t.getPiece() != null) temp = t.getPiece();
				else temp = null;
				tile.empty();
				t.place(this);
				if (!kingSpot.isAttacked()) thinStack.push(t);
				t.empty();
				t.place(temp);
				tile.place(this);
			}
		}

		else {
			Piece king = game.getBlackKing();
			Tile kingSpot = king.getTile();
			Piece temp = null;
			for (Tile t : stack) {
				if (t.getPiece() != null) temp = t.getPiece();
				else temp = null;
				tile.empty();
				t.place(this);
				if (!kingSpot.isAttacked()) thinStack.push(t);
				t.empty();
				t.place(temp);
				tile.place(this);
			}
		}
		return thinStack;
	}

	// unit test of methods
	public static void main(String[] args) { /* see unit testing in Game.java for sample game */ }
}