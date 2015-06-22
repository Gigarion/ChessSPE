import java.util.Stack;
import java.util.LinkedList;
import javax.swing.*;
import java.awt.*;

public class Board
{
	// board represented by 8 x 8 grid of tiles
	private Tile[][] tiles = new Tile[8][8];

	// current game
	private Game game;

	// list of white pieces in play
	private LinkedList<Piece> wPieces;

	// list of black pieces in play
	private LinkedList<Piece> bPieces;

	// used to make the board in the GUI
	private JPanel panel;

	// variables to help initialize the GUI
	private static final double tileStartX = 0;
	private static final double tileStartY = 0;
	private static final double fTileStartX = 7;
	private static final double fTileStartY = 7;
	private static final double displacement = 1;

	// automatically flips the board when a move is made
	private boolean autoflip;
	
	public Board()
	{
		panel = new JPanel(new GridLayout(8, 8));
        panel.setMinimumSize(new Dimension(480, 480));
		wPieces = new LinkedList<Piece>();
		bPieces = new LinkedList<Piece>();

		// creates 8 x 8 grid of tiles
		for (int r = 0; r < 8; r++)
		{
			for (int c = 0; c < 8; c++)
			{
				StringBuilder tileId = new StringBuilder(2);
				tileId.append(r);
				tileId.append(c);
				this.tiles[r][c] = new Tile(tileId.toString());
			}
		}
		autoflip = true;
	}

	// returns the tile at the given rank and file
	public Tile getTile(int rank, int file)
	{
		if (rank <  0 || rank > 7 || file < 0 || file > 7) return null;
		else return tiles[rank][file];
	}

	// returns the board's 8 x 8 grid of tiles
	public Tile[][] getTiles() { return tiles; }

	// sets the current board's game
	public void setGame(Game game) { this.game = game; }

	// returns the list of white pieces on the board
	public LinkedList<Piece> getWhitePieces() { return wPieces; }

	// returns the list of black pieces on the board
	public LinkedList<Piece> getBlackPieces() { return bPieces; }

	// returns the panel associated with the board's GUI
	public JPanel getPanel() { return panel; }

	// returns the status of the board's autoflip function
	public boolean getAutoFlip() { return autoflip; }

	// sets the status of the board's autoflip function
	public void setAutoFlip(boolean autoflip) { this.autoflip = autoflip; }

	// toggles the board's autoflip function
	public void toggleAutoFlip() { autoflip = !autoflip; }

	// ???
	public void show() {}

	// returns whether or not the board is flipped (black at bottom)
	public boolean isFlipped() { return game.getFlip(); }

	// undoes the most recent move
	public void takeMove() {
		if (!game.getMoveHistory().isEmpty()) {
			String thisFen = game.getMoveHistory().pop();
			game.setFEN(thisFen);
		}
	}

	// clears the board of all pieces
	public void clear()
	{
		for (int i = 0; i < 8; i++)
		{
			for (int j = 0; j < 8; j++)
			{
				tiles[i][j].empty();
			}
		}
	}

	// draws current state of the board
	public void draw()
	{
		panel.removeAll();
        panel.setLayout(new GridLayout(8, 8));
        panel.setMinimumSize(new Dimension(480, 480));
		if (!game.getFlip())
		{
			for (int r = 0; r < 8; r++)
			{
				for (int c = 0; c < 8; c++)
				{
					panel.add(tiles[7 - r][c].set(false));
				}
			}
		}
		else {
			for (int r = 0; r < 8; r++)
			{
				for (int c = 0; c < 8; c++)
				{
					panel.add(tiles[r][7 - c].set(false));
				}
			}
		}
	}

	// draws current state of the board with a stack of tiles to highlight
	public void draw(Stack<Tile> toHighlight)
	{
		panel.removeAll();
        panel.setLayout(new GridLayout(8, 8));
        panel.setMinimumSize(new Dimension(480, 480));
		if (!game.getFlip())
		{
			for (int r = 0; r < 8; r++)
			{
				for (int c = 0; c < 8; c++)
				{
					boolean tf = false;
					for (Tile t : toHighlight)
					{
						if (tiles[7 - r][c].getID().equals(t.getID())) { tf = true; }
					}
					panel.add(tiles[7 - r][c].set(tf));
				}
			}
		}
		else
		{
			for (int r = 0; r < 8; r++)
			{
				for (int c = 0; c < 8; c++)
				{
					boolean tf = false;
					for (Tile t : toHighlight)
					{
						if (tiles[r][7 - c].getID().equals(t.getID())) { tf = true; }
					}
					panel.add(tiles[r][7 - c].set(tf));
				}
			}
		}
	}

	// resets the board to the starting arrangement: can we use setFEN() here?
	public void reset()
	{
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
		
		// placing all of the pawns
		for (int i = 0; i < 8; i++)
		{

			wPawn[i] = new Piece('P', 'w', tiles[1][i]);
			bPawn[i] = new Piece('p', 'b', tiles[6][i]);

			tiles[1][i].place(wPawn[i]);
			tiles[6][i].place(bPawn[i]);

			wPieces.add(wPawn[i]);
			bPieces.add(bPawn[i]);
		}
	}

	// moves the given piece to the given square
	public void move(Piece toMove, Tile target)
	{
		
		// saves the current board onto the move history stack
		// to support the take move function
		game.getMoveHistory().push(game.createFEN());

		// removes the captured piece from the board, if it exists
		if (target.getPiece() != null)
		{

			// the fifty move rule is reset after every capture
			game.setHalfMoves(0);

			Piece toKill = target.getPiece();
			LinkedList<Piece> removal = new LinkedList<Piece>();
			LinkedList<Piece> newSet = new LinkedList<Piece>();
			if (toKill.getColor() == 'w') { removal = game.getWhitePieces(); }
			else { removal = game.getBlackPieces(); }
				
			for (Piece p : removal)
			{
				if (!p.getTile().getID().equals(toKill.getTile().getID()))
				{
					newSet.add(p);
				}
			}

			if (toKill.getColor() == 'w') { game.setWhitePieces(newSet); }
			else { game.setBlackPieces(newSet); }
			toKill.moveTo(null);
		} 

		else if (toMove.getType() == 'P' || toMove.getType() == 'p') {
			if (game.getEP() != null) {
				if (target.getID().equals(game.getEP().getID())) {
					int rank = Character.getNumericValue(game.getEP().getID().charAt(0));
					int file = Character.getNumericValue(game.getEP().getID().charAt(1));
					Piece toKill;
					if (toMove.getType() == 'P') toKill = game.getTile(rank - 1, file).getPiece();

					else toKill = game.getTile(rank + 1, file).getPiece();

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
					toKill.getTile().empty();
					toKill.moveTo(null);
				}
			}
		}


		int piecerank  = Character.getNumericValue(toMove.getTile().getID().charAt(0));
		int piecefile  = Character.getNumericValue(toMove.getTile().getID().charAt(1));
		int targetrank = Character.getNumericValue(target.getID().charAt(0));
		int targetfile = Character.getNumericValue(target.getID().charAt(1));

		// sets the EP square if the pawn move is a pawn start
		if (toMove.getType() == 'P' && (targetrank - piecerank) == 2) {
			game.setEP(getTile(targetrank - 1, piecefile));
			game.setHalfMoves(0);
		}

		else if (toMove.getType() == 'P' && targetfile == piecefile) {
			game.setEP(null);
		}

		if (toMove.getType() == 'p' && (targetrank - piecerank) == -2) {
			game.setEP(getTile(targetrank + 1, piecefile));
			game.setHalfMoves(0);
		}

		else if (toMove.getType() == 'p' && targetfile == piecefile) {
			game.setEP(null);
		}

		if (toMove.getType() != 'p' && toMove.getType() != 'P')
			game.setEP(null);
		
		// move the appropriate rook if the king castles
		if (toMove.getType() == 'K' && piecefile - targetfile == -2) { // white kingside castling
			getTile(0, 7).getPiece().moveTo(getTile(0, 5));
			getTile(0, 5).place(getTile(0, 7).getPiece());
			getTile(0, 7).empty();
		} else if (toMove.getType() == 'K' && piecefile - targetfile == 2) { // white queenside castling
			getTile(0, 0).getPiece().moveTo(getTile(0, 3));
			getTile(0, 3).place(getTile(0, 0).getPiece());
			getTile(0, 0).empty();
		}

		if (toMove.getType() == 'k' && piecefile - targetfile == -2) { // black kingside castling
			getTile(7, 7).getPiece().moveTo(getTile(7, 5));
			getTile(7, 5).place(getTile(7, 7).getPiece());
			getTile(7, 7).empty();
		} else if (toMove.getType() == 'k' && piecefile - targetfile == 2) { // black queenside castling
			getTile(7, 0).getPiece().moveTo(getTile(7, 3));
			getTile(7, 3).place(getTile(7, 0).getPiece());
			getTile(7, 0).empty();
		}


		toMove.getTile().empty();
		target.place(toMove);
		toMove.moveTo(target);

		// pawns autopromote to queens
		if (toMove.getType() == 'P' && targetrank == 7) {
			toMove.setType('Q');
			game.setHalfMoves(0);
		}

		if (toMove.getType() == 'p' && Character.getNumericValue(target.getID().charAt(0)) == 0) {
			toMove.setType('q');
			game.setHalfMoves(0);
		}

		// if the king moves, castling rights are forfeited
		if (toMove.getType() == 'K') {
			game.setCastlingRights(0, false);
			game.setCastlingRights(1, false);
		} 
		else if (toMove.getType() == 'k') {
			game.setCastlingRights(2, false);
			game.setCastlingRights(3, false);
		}

		// if the rook moves, then that side's castling is forfeited
		if (toMove.getType() == 'R' && Character.getNumericValue(toMove.getTile().getID().charAt(1)) == 7) { game.setCastlingRights(0, false); } 
		else if (toMove.getType() == 'R' && Character.getNumericValue(toMove.getTile().getID().charAt(1)) == 0) { game.setCastlingRights(1, false); } 
		else if (toMove.getType() == 'r' && Character.getNumericValue(toMove.getTile().getID().charAt(1)) == 7) { game.setCastlingRights(2, false); } 
		else if (toMove.getType() == 'r' && Character.getNumericValue(toMove.getTile().getID().charAt(1)) == 0) { game.setCastlingRights(3, false); }

		// flips the board if the autoflip function is on, otherwise the board simply switches the side to move
		if (autoflip) { game.flip(); }
		else {
			if (game.getSide() == 'w') game.setSide('b');
			else game.setSide('w');
		}

		// Ai makes a move, if it exists
		if (game.getAI() != null) { game.getAI().makeAIMove(); }
	}

	// move function for the AI
	public void aiMove(Piece toMove, Tile target) {
		
		// saves the current board onto the move history stack
		// to support the take move function
		game.getMoveHistory().push(game.createFEN());

		// removes the captured piece from
		if (target.getPiece() != null) {

			// the fifty move rule is reset after every capture
			game.setHalfMoves(0);

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

		else if (toMove.getType() == 'P' || toMove.getType() == 'p') {
			if (game.getEP() != null) {
				if (target.getID().equals(game.getEP().getID())) {
					int rank = Character.getNumericValue(game.getEP().getID().charAt(0));
					int file = Character.getNumericValue(game.getEP().getID().charAt(1));
					Piece toKill;
					if (toMove.getType() == 'P') toKill = game.getTile(rank - 1, file).getPiece();

					else toKill = game.getTile(rank + 1, file).getPiece();

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
					toKill.getTile().empty();
					toKill.moveTo(null);
				}
			}
		}


		int piecerank  = Character.getNumericValue(toMove.getTile().getID().charAt(0));
		int piecefile  = Character.getNumericValue(toMove.getTile().getID().charAt(1));
		int targetrank = Character.getNumericValue(target.getID().charAt(0));
		int targetfile = Character.getNumericValue(target.getID().charAt(1));

		// sets the EP square if the pawn move is a pawn start
		if (toMove.getType() == 'P' && (targetrank - piecerank) == 2) {
			game.setEP(getTile(targetrank - 1, piecefile));
			game.setHalfMoves(0);
		}

		else if (toMove.getType() == 'P' && targetfile == piecefile) {
			game.setEP(null);
		}

		if (toMove.getType() == 'p' && (targetrank - piecerank) == -2) {
			game.setEP(getTile(targetrank + 1, piecefile));
			game.setHalfMoves(0);
		}

		else if (toMove.getType() == 'p' && targetfile == piecefile) {
			game.setEP(null);
		}

		if (toMove.getType() != 'p' && toMove.getType() != 'P')
			game.setEP(null);
		
		// move the appropriate rook if the king castles
		if (toMove.getType() == 'K' && piecefile - targetfile == -2) { // white kingside castling
			getTile(0, 7).getPiece().moveTo(getTile(0, 5));
			getTile(0, 5).place(getTile(0, 7).getPiece());
			getTile(0, 7).empty();
		} else if (toMove.getType() == 'K' && piecefile - targetfile == 2) { // white queenside castling
			getTile(0, 0).getPiece().moveTo(getTile(0, 3));
			getTile(0, 3).place(getTile(0, 0).getPiece());
			getTile(0, 0).empty();
		}

		if (toMove.getType() == 'k' && piecefile - targetfile == -2) { // black kingside castling
			getTile(7, 7).getPiece().moveTo(getTile(7, 5));
			getTile(7, 5).place(getTile(7, 7).getPiece());
			getTile(7, 7).empty();
		} else if (toMove.getType() == 'k' && piecefile - targetfile == 2) { // black queenside castling
			getTile(7, 0).getPiece().moveTo(getTile(7, 3));
			getTile(7, 3).place(getTile(7, 0).getPiece());
			getTile(7, 0).empty();
		}


		toMove.getTile().empty();
		target.place(toMove);
		toMove.moveTo(target);

		// pawns autopromote to queens
		if (toMove.getType() == 'P' && targetrank == 7) {
			toMove.setType('Q');
			game.setHalfMoves(0);
		}

		if (toMove.getType() == 'p' && Character.getNumericValue(target.getID().charAt(0)) == 0) {
			toMove.setType('q');
			game.setHalfMoves(0);
		}

		// if the king moves, castling rights are forfeited
		if (toMove.getType() == 'K') {
			game.setCastlingRights(0, false);
			game.setCastlingRights(1, false);
		} 
		else if (toMove.getType() == 'k') {
			game.setCastlingRights(2, false);
			game.setCastlingRights(3, false);
		}

		// if the rook moves, then that side's castling
		// is forfeited
		if (toMove.getType() == 'R' && Character.getNumericValue(toMove.getTile().getID().charAt(1)) == 7) { game.setCastlingRights(0, false); } 
		else if (toMove.getType() == 'R' && Character.getNumericValue(toMove.getTile().getID().charAt(1)) == 0) { game.setCastlingRights(1, false); } 
		else if (toMove.getType() == 'r' && Character.getNumericValue(toMove.getTile().getID().charAt(1)) == 7) { game.setCastlingRights(2, false); } 
		else if (toMove.getType() == 'r' && Character.getNumericValue(toMove.getTile().getID().charAt(1)) == 0) { game.setCastlingRights(3, false); }
	}

	// unit test of methods
    public static void main(String[] args) { /* see unit testing in Game.java for sample game */ }
}