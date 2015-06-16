public class Piece {

	// type of piece
	private char type;

	// color of piece
	private char color;

	// location of piece
	private Tile tile;

	// list of all white pieces
	private static String wPieces = "PNBRQK";

	// list of all black pieces
	private static String bPieces = "pnbrqk";

	public Piece(char type, char color, Tile tile) {
		this.type  = type;
		this.color = color;
		this.tile  = tile;
	}

	// returns the piece type
	public char getType() {
		return type;
	}

	// returns the piece color
	public char getColor() {
		return color;
	}

	// returns the piece tile
	public Tile getTile() {
		return tile;
	}

	public void moveTo(Tile tile) {
		this.tile = tile;
	}

	// returns stack of possible moves
	public Stack<Tile> moves() {

		Stack<Tile> stack = new Stack<Tile>();
		char piecetype    = type;
		int rank          = Character.getNumericValue(tile.getID().charAt(0));
		int file          = Character.getNumericValue(tile.getID().charAt(1));

		// possible moves for a white pawn
		if (piecetype == 'P') {

			// diagonal left
			Tile left = Board.getTile(rank+1, file-1);

			// diagonal right
			Tile right = Board.getTile(rank+1, file+1);

			// middle
			Tile middle = Board.getTile(rank+1, file);

			if (left != null && left.getPiece().getColor() == 'b') {
				stack.push(left);
			}

			if (right != null && right.getPiece().getColor() == 'b') {
				stack.push(right);
			}

			if (middle != null && middle.getPiece() == null) {
				stack.push(middle);
			}

			// adds the ep square if one exists
			if (Game.getEP() != null) {
				Tile eptile = Board.getEP();
				int eprank  = Character.getNumericValue(eptile.getID().charAt(0));
				int epfile  = Character.getNumericValue(eptile.getID().charAt(1));
				if (Math.abs(epfile-file) == 1 && (eprank-rank) == 1) {
					stack.push(eptile);
				}
			}

		}

		// possible moves for a black pawn
		else if (piecetype == 'p') {
			
			// diagonal left
			Tile left = Board.getTile(rank-1, file-1);

			// diagonal right
			Tile right = Board.getTile(rank-1, file+1);

			// middle
			Tile middle = Board.getTile(rank-1, file);

			if (left != null && left.getPiece().getColor() == 'w') {
				stack.push(left);
			}

			if (right != null && right.getPiece().getColor() == 'w') {
				stack.push(right);
			}

			if (middle != null && middle.getPiece() == null) {
				stack.push(middle);
			}

			// adds the ep square if one exists
			if (Game.getEP() != null) {
				Tile eptile = Board.getEP();
				int eprank  = Character.getNumericValue(eptile.getID().charAt(0));
				int epfile  = Character.getNumericValue(eptile.getID().charAt(1));
				if (Math.abs(epfile-file) == 1 && (rank-eprank) == 1) {
					stack.push(eptile);
				}
			}

		}

		// possible moves for a white knight
		else if (piecetype == 'N') {

			// moving clockwise from the bottom-left square...
			Tile bottomleft  = Board.getTile(rank-2, file-1);
			Tile leftbottom  = Board.getTile(rank-1, file-2);
			Tile lefttop     = Board.getTile(rank+1, file-2);
			Tile topleft     = Board.getTile(rank+2, file-1);
			Tile topright    = Board.getTile(rank+2, file+1);
			Tile righttop    = Board.getTile(rank+1, file+2);
			Tile rightbottom = Board.getTile(rank-1, file+2);
			Tile bottomright = Board.getTile(rank-2, file+1);

			// either the square should be empty or contain an opposing piece
			if (bottomleft != null && (bottomleft.getPiece() == null || bottomleft.getPiece().getColor() == 'b')) {
				stack.push(bottomleft);
			}

			if (leftbottom != null && (leftbottom.getPiece() == null || leftbottom.getPiece().getColor() == 'b')) {
				stack.push(leftbottom);
			}

			if (lefttop != null && (lefttop.getPiece() == null || lefttop.getPiece().getColor() == 'b')) {
				stack.push(lefttop);
			}

			if (topleft != null && (topleft.getPiece() == null || topleft.getPiece().getColor() == 'b')) {
				stack.push(topleft);
			}

			if (topright != null && (topright.getPiece() == null || topright.getPiece().getColor() == 'b')) {
				stack.push(topright);
			}

			if (righttop != null && (righttop.getPiece() == null || righttop.getPiece().getColor() == 'b')) {
				stack.push(righttop);
			}

			if (rightbottom != null && (rightbottom.getPiece() == null || rightbottom.getPiece().getColor() == 'b')) {
				stack.push(rightbottom);
			}

			if (bottomright != null && (bottomright.getPiece() == null || bottomright.getPiece().getColor() == 'b')) {
				stack.push(bottomright);
			}
		}

		// possible moves for a black knight
		else if (piecetype == 'n') {

			// moving clockwise from the bottom-left square...
			Tile bottomleft  = Board.getTile(rank-2, file-1);
			Tile leftbottom  = Board.getTile(rank-1, file-2);
			Tile lefttop     = Board.getTile(rank+1, file-2);
			Tile topleft     = Board.getTile(rank+2, file-1);
			Tile topright    = Board.getTile(rank+2, file+1);
			Tile righttop    = Board.getTile(rank+1, file+2);
			Tile rightbottom = Board.getTile(rank-1, file+2);
			Tile bottomright = Board.getTile(rank-2, file+1);

			// either the square should be empty or contain an opposing piece
			if (bottomleft != null && (bottomleft.getPiece() == null || bottomleft.getPiece().getColor() == 'w')) {
				stack.push(bottomleft);
			}

			if (leftbottom != null && (leftbottom.getPiece() == null || leftbottom.getPiece().getColor() == 'w')) {
				stack.push(leftbottom);
			}

			if (lefttop != null && (lefttop.getPiece() == null || lefttop.getPiece().getColor() == 'w')) {
				stack.push(lefttop);
			}

			if (topleft != null && (topleft.getPiece() == null || topleft.getPiece().getColor() == 'w')) {
				stack.push(topleft);
			}

			if (topright != null && (topright.getPiece() == null || topright.getPiece().getColor() == 'w')) {
				stack.push(topright);
			}

			if (righttop != null && (righttop.getPiece() == null || righttop.getPiece().getColor() == 'w')) {
				stack.push(righttop);
			}

			if (rightbottom != null && (rightbottom.getPiece() == null || rightbottom.getPiece().getColor() == 'w')) {
				stack.push(rightbottom);
			}

			if (bottomright != null && (bottomright.getPiece() == null || bottomright.getPiece().getColor() == 'w')){
				stack.push(bottomright);
			}
		}

		// possible moves for a white bishop
		else if (piecetype == 'B') {
			boolean stopCatch = false;
			int fTemp = file;
			int rTemp = rank;
			for (int f = 1; fTemp < 8; f++) {
				fTemp = file + f;
				rTemp = rank + f;
				if (!stopCatch) {
					Tile toStack = Board.getTile(rTemp, fTemp);
					if (toStack != null) {
						if (toStack.getTile() != null) {
							if (wPieces.contains(toStack.getPiece().getType())) {
								stopCatch = true;
							}
							else if (bPieces.contains(toStack.getPiece().getType())) {
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
			for (int f = 1; fTemp >= 0; f++) {
				fTemp = file + f;
				rTemp = file - f;
				if (!stopCatch) {
					Tile toStack = Board.getTile(rTemp, fTemp);
					if (toStack != null) {
						if (toStack.getTile() != null) {
							if (wPieces.contains(toStack.getPiece().getType())) {
									stopCatch = true;
								}
							else if (bPieces.contains(toStack.getPiece().getType())) {
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
			for (int f = 1; rTemp < 8; f++) {
				fTemp = file - f;
				rTemp = rank + f;
				if (!stopCatch) {
					Tile toStack = Board.getTile(rTemp, fTemp);
					if (toStack != null) {
						if (toStack.getTile() != null) {
							if (wPieces.contains(toStack.getPiece().getType())) {
								stopCatch = true;
							}
							else if (bPieces.contains(toStack.getPiece().getType())) {
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
			for (int f = 1; rTemp < 8; f++) {
				fTemp = file - f;
				rTemp = rank - f;
				if (!stopCatch) {
					Tile toStack = Board.getTile(rTemp, fTemp);
					if (toStack != null) {
						if (toStack.getTile() != null) {
							if (wPieces.contains(toStack.getPiece().getType())) {
								stopCatch = true;
							}
							else if (bPieces.contains(toStack.getPiece().getType())) {
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
			for (int f = 1; fTemp < 8; f++) {
				fTemp = file + f;
				rTemp = rank + f;
				if (!stopCatch) {
					Tile toStack = Board.getTile(rTemp, fTemp);
					if (toStack != null) {
						if (toStack.getTile() != null) {
							if (bPieces.contains(toStack.getPiece().getType())) {
								stopCatch = true;
							}
							else if (wPieces.contains(toStack.getPiece().getType())) {
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
			for (int f = 1; fTemp >= 0; f++) {
				fTemp = file + f;
				rTemp = file - f;
				if (!stopCatch) {
					Tile toStack = Board.getTile(rTemp, fTemp);
					if (toStack != null) {
						if (toStack.getTile() != null) {
							if (bPieces.contains(toStack.getPiece().getType())) {
									stopCatch = true;
								}
							else if (wPieces.contains(toStack.getPiece().getType())) {
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
			for (int f = 1; rTemp < 8; f++) {
				fTemp = file - f;
				rTemp = rank + f;
				if (!stopCatch) {
					Tile toStack = Board.getTile(rTemp, fTemp);
					if (toStack != null) {
						if (toStack.getTile() != null) {
							if (bPieces.contains(toStack.getPiece().getType())) {
								stopCatch = true;
							}
							else if (wPieces.contains(toStack.getPiece().getType())) {
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
			for (int f = 1; rTemp < 8; f++) {
				fTemp = file - f;
				rTemp = rank - f;
				if (!stopCatch) {
					Tile toStack = Board.getTile(rTemp, fTemp);
					if (toStack != null) {
						if (toStack.getTile() != null) {
							if (bPieces.contains(toStack.getPiece().getType())) {
								stopCatch = true;
							}
							else if (wPieces.contains(toStack.getPiece().getType())) {
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
					Tile toStack = Board.getTile(rank, fTemp);
					if (toStack != null) {
						if (toStack.getPiece() != null) {
							if (wPieces.contains(toStack.getPiece().getType())) {
								stopCatch = true;
							}
							else if (bPieces.contains(toStack.getPiece().getType())) {
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
					Tile toStack = Board.getTile(rank, fTemp);
					if (toStack != null) {
						if (toStack.getTile() != null) {
							if (wPieces.contains(toStack.getPiece().getType())) {
									stopCatch = true;
								}
							else if (bPieces.contains(toStack.getPiece().getType())) {
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
					Tile toStack = Board.getTile(rTemp, file);
					if (toStack != null) {
						if (toStack.getTile() != null) {
							if (wPieces.contains(toStack.getPiece().getType())) {
								stopCatch = true;
							}
							else if (bPieces.contains(toStack.getPiece().getType())) {
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
				rTemp = rank - r;
				if (!stopCatch) {
					Tile toStack = Board.getTile(rTemp, file);
					if (toStack != null) {
						if (toStack.getTile() != null) {
							if (wPieces.contains(toStack.getPiece().getType())) {
								stopCatch = true;
							}
							else if (bPieces.contains(toStack.getPiece().getType())) {
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
					Tile toStack = Board.getTile(rank, fTemp);
					if (toStack != null) {
						if (toStack.getTile() != null) {
							if (bPieces.contains(toStack.getPiece().getType())) {
								stopCatch = true;
							}
							else if (wPieces.contains(toStack.getPiece().getType())) {
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
					Tile toStack = Board.getTile(rank, fTemp);
					if (toStack != null) {
						if (toStack.getTile() != null) {
							if (bPieces.contains(toStack.getPiece().getType())) {
									stopCatch = true;
								}
							else if (wPieces.contains(toStack.getPiece().getType())) {
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
					Tile toStack = Board.getTile(rTemp, file);
					if (toStack != null) {
						if (toStack.getTile() != null) {
							if (bPieces.contains(toStack.getPiece().getType())) {
								stopCatch = true;
							}
							else if (wPieces.contains(toStack.getPiece().getType())) {
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
				rTemp = rank - r;
				if (!stopCatch) {
					Tile toStack = Board.getTile(rTemp, file);
					if (toStack != null) {
						if (toStack.getTile() != null) {
							if (bPieces.contains(toStack.getPiece().getType())) {
								stopCatch = true;
							}
							else if (wPieces.contains(toStack.getPiece().getType())) {
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
			for (int f = 1; fTemp < 8; f++) {
				fTemp = file + f;
				rTemp = rank + f;
				if (!stopCatch) {
					Tile toStack = Board.getTile(rTemp, fTemp);
					if (toStack != null) {
						if (toStack.getTile() != null) {
							if (wPieces.contains(toStack.getPiece().getType())) {
								stopCatch = true;
							}
							else if (bPieces.contains(toStack.getPiece().getType())) {
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
			for (int f = 1; fTemp >= 0; f++) {
				fTemp = file + f;
				rTemp = file - f;
				if (!stopCatch) {
					Tile toStack = Board.getTile(rTemp, fTemp);
					if (toStack != null) {
						if (toStack.getTile() != null) {
							if (wPieces.contains(toStack.getPiece().getType())) {
									stopCatch = true;
								}
							else if (bPieces.contains(toStack.getPiece().getType())) {
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
			for (int f = 1; rTemp < 8; f++) {
				fTemp = file - f;
				rTemp = rank + f;
				if (!stopCatch) {
					Tile toStack = Board.getTile(rTemp, fTemp);
					if (toStack != null) {
						if (toStack.getTile() != null) {
							if (wPieces.contains(toStack.getPiece().getType())) {
								stopCatch = true;
							}
							else if (bPieces.contains(toStack.getPiece().getType())) {
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
			for (int f = 1; rTemp < 8; f++) {
				fTemp = file - f;
				rTemp = rank - f;
				if (!stopCatch) {
					Tile toStack = Board.getTile(rTemp, fTemp);
					if (toStack != null) {
						if (toStack.getTile() != null) {
							if (wPieces.contains(toStack.getPiece().getType())) {
								stopCatch = true;
							}
							else if (bPieces.contains(toStack.getPiece().getType())) {
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
			int fTemp = file;
			for (int f = 1; fTemp < 8; f++) {
				fTemp = file + f;
				if (!stopCatch) {
					Tile toStack = Board.getTile(rank, fTemp);
					if (toStack != null) {
						if (toStack.getTile() != null) {
							if (wPieces.contains(toStack.getPiece().getType())) {
								stopCatch = true;
							}
							else if (bPieces.contains(toStack.getPiece().getType())) {
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
					Tile toStack = Board.getTile(rank, fTemp);
					if (toStack != null) {
						if (toStack.getTile() != null) {
							if (wPieces.contains(toStack.getPiece().getType())) {
									stopCatch = true;
								}
							else if (bPieces.contains(toStack.getPiece().getType())) {
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
					Tile toStack = Board.getTile(rTemp, file);
					if (toStack != null) {
						if (toStack.getTile() != null) {
							if (wPieces.contains(toStack.getPiece().getType())) {
								stopCatch = true;
							}
							else if (bPieces.contains(toStack.getPiece().getType())) {
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
				rTemp = rank - r;
				if (!stopCatch) {
					Tile toStack = Board.getTile(rTemp, file);
					if (toStack != null) {
						if (toStack.getTile() != null) {
							if (wPieces.contains(toStack.getPiece().getType())) {
								stopCatch = true;
							}
							else if (bPieces.contains(toStack.getPiece().getType())) {
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
			for (int f = 1; fTemp < 8; f++) {
				fTemp = file + f;
				rTemp = rank + f;
				if (!stopCatch) {
					Tile toStack = Board.getTile(rTemp, fTemp);
					if (toStack != null) {
						if (toStack.getTile() != null) {
							if (bPieces.contains(toStack.getPiece().getType())) {
								stopCatch = true;
							}
							else if (wPieces.contains(toStack.getPiece().getType())) {
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
			for (int f = 1; fTemp >= 0; f++) {
				fTemp = file + f;
				rTemp = file - f;
				if (!stopCatch) {
					Tile toStack = Board.getTile(rTemp, fTemp);
					if (toStack != null) {
						if (toStack.getTile() != null) {
							if (bPieces.contains(toStack.getPiece().getType())) {
									stopCatch = true;
								}
							else if (wPieces.contains(toStack.getPiece().getType())) {
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
			for (int f = 1; rTemp < 8; f++) {
				fTemp = file - f;
				rTemp = rank + f;
				if (!stopCatch) {
					Tile toStack = Board.getTile(rTemp, fTemp);
					if (toStack != null) {
						if (toStack.getTile() != null) {
							if (bPieces.contains(toStack.getPiece().getType())) {
								stopCatch = true;
							}
							else if (wPieces.contains(toStack.getPiece().getType())) {
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
			for (int f = 1; rTemp < 8; f++) {
				fTemp = file - f;
				rTemp = rank - f;
				if (!stopCatch) {
					Tile toStack = Board.getTile(rTemp, fTemp);
					if (toStack != null) {
						if (toStack.getTile() != null) {
							if (bPieces.contains(toStack.getPiece().getType())) {
								stopCatch = true;
							}
							else if (wPieces.contains(toStack.getPiece().getType())) {
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
			int fTemp = file;
			for (int f = 1; fTemp < 8; f++) {
				fTemp = file + f;
				if (!stopCatch) {
					Tile toStack = Board.getTile(rank, fTemp);
					if (toStack != null) {
						if (toStack.getTile() != null) {
							if (bPieces.contains(toStack.getPiece().getType())) {
								stopCatch = true;
							}
							else if (wPieces.contains(toStack.getPiece().getType())) {
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
					Tile toStack = Board.getTile(rank, fTemp);
					if (toStack != null) {
						if (toStack.getTile() != null) {
							if (bPieces.contains(toStack.getPiece().getType())) {
									stopCatch = true;
								}
							else if (wPieces.contains(toStack.getPiece().getType())) {
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
					Tile toStack = Board.getTile(rTemp, file);
					if (toStack != null) {
						if (toStack.getTile() != null) {
							if (bPieces.contains(toStack.getPiece().getType())) {
								stopCatch = true;
							}
							else if (wPieces.contains(toStack.getPiece().getType())) {
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
				rTemp = rank - r;
				if (!stopCatch) {
					Tile toStack = Board.getTile(rTemp, file);
					if (toStack != null) {
						if (toStack.getTile() != null) {
							if (bPieces.contains(toStack.getPiece().getType())) {
								stopCatch = true;
							}
							else if (wPieces.contains(toStack.getPiece().getType())) {
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
		else if (piecetype = 'K') {
			boolean stopCatch = false;
			int fTemp = file;
			int rTemp = rank;
			fTemp = file + f;
			rTemp = rank + f;
			Tile toStack = Board.getTile(rTemp, fTemp);
			
			if (toStack != null) {
				if (toStack.getTile() != null) {
					if (wPieces.contains(toStack.getPiece().getType())) {
						stopCatch = true;
					}
					else if (bPieces.contains(toStack.getPiece().getType())) {
						stack.push(toStack);
						stopCatch = true;
					}
				}
				else {
					if (Board.getTile(rTemp, fTemp).isAttacked()) {
						stopCatch = true;
					} 
					else stack.push(toStack);
				}
			}

			fTemp = file + 1;
			rTemp = rank - 1;
			Tile toStack = Board.getTile(rTemp, fTemp);
			
			if (toStack != null) {
				if (toStack.getTile() != null) {
					if (wPieces.contains(toStack.getPiece().getType())) {
						stopCatch = true;
					}
					else if (bPieces.contains(toStack.getPiece().getType())) {
						stack.push(toStack);
						stopCatch = true;
					}
				}
				else {
					if (Board.getTile(rTemp, fTemp).isAttacked()) {
						stopCatch = true;
					} 
					else stack.push(toStack);
				}
			}

			fTemp = file - 1;
			rTemp = rank - 1;
			Tile toStack = Board.getTile(rTemp, fTemp);
			
			if (toStack != null) {
				if (toStack.getTile() != null) {
					if (wPieces.contains(toStack.getPiece().getType())) {
						stopCatch = true;
					}
					else if (bPieces.contains(toStack.getPiece().getType())) {
						stack.push(toStack);
						stopCatch = true;
					}
				}
				else {
					if (Board.getTile(rTemp, fTemp).isAttacked()) stopCatch = true;
					else stack.push(toStack);
				}
			}

			fTemp = file - 1;
			rTemp = rank + 1;
			Tile toStack = Board.getTile(rTemp, fTemp);
			
			if (toStack != null) {
				if (toStack.getTile() != null) {
					if (wPieces.contains(toStack.getPiece().getType())) {
						stopCatch = true;
					}
					else if (bPieces.contains(toStack.getPiece().getType())) {
						stack.push(toStack);
						stopCatch = true;
					}
				}
				else {
					if (Board.getTile(rTemp, fTemp).isAttacked()) stopCatch = true;
					else stack.push(toStack);
				}
			}
		}

		// possible moves for a black king
		else if (piecetype = 'k') {
			boolean stopCatch = false;
			int fTemp = file;
			int rTemp = rank;
			fTemp = file + f;
			rTemp = rank + f;
			Tile toStack = Board.getTile(rTemp, fTemp);
			
			if (toStack != null) {
				if (toStack.getTile() != null) {
					if (bPieces.contains(toStack.getPiece().getType())) {
						stopCatch = true;
					}
					else if (wPieces.contains(toStack.getPiece().getType())) {
						stack.push(toStack);
						stopCatch = true;
					}
				}
				else {
					if (Board.getTile(rTemp, fTemp).isAttacked()) {
						stopCatch = true;
					} 
					else stack.push(toStack);
				}
			}

			fTemp = file + 1;
			rTemp = rank - 1;
			Tile toStack = Board.getTile(rTemp, fTemp);
			
			if (toStack != null) {
				if (toStack.getTile() != null) {
					if (bPieces.contains(toStack.getPiece().getType())) {
						stopCatch = true;
					}
					else if (wPieces.contains(toStack.getPiece().getType())) {
						stack.push(toStack);
						stopCatch = true;
					}
				}
				else {
					if (Board.getTile(rTemp, fTemp).isAttacked()) {
						stopCatch = true;
					} 
					else stack.push(toStack);
				}
			}

			fTemp = file - 1;
			rTemp = rank - 1;
			Tile toStack = Board.getTile(rTemp, fTemp);
			
			if (toStack != null) {
				if (toStack.getTile() != null) {
					if (bPieces.contains(toStack.getPiece().getType())) {
						stopCatch = true;
					}
					else if (wPieces.contains(toStack.getPiece().getType())) {
						stack.push(toStack);
						stopCatch = true;
					}
				}
				else {
					if (Board.getTile(rTemp, fTemp).isAttacked()) stopCatch = true;
					else stack.push(toStack);
				}
			}

			fTemp = file - 1;
			rTemp = rank + 1;
			Tile toStack = Board.getTile(rTemp, fTemp);
			
			if (toStack != null) {
				if (toStack.getTile() != null) {
					if (bPieces.contains(toStack.getPiece().getType())) {
						stopCatch = true;
					}
					else if (wPieces.contains(toStack.getPiece().getType())) {
						stack.push(toStack);
						stopCatch = true;
					}
				}
				else {
					if (Board.getTile(rTemp, fTemp).isAttacked()) stopCatch = true;
					else stack.push(toStack);
				}
			}
		}
	}

	// unit test of methods
	public void main(String[] args) {
	}
}