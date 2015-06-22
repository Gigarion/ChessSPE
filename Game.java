import java.util.Stack;
import java.util.Queue;
import java.util.LinkedList;
import javax.swing.*;
import java.awt.*;

public class Game
{
	// the playing board for this game instance
	private Board board;

	// array of all tiles on the game board
	private Tile[][] tiles;

	// stack of all white pieces
	private LinkedList<Piece> wPieces;

	// stack of all black pieces
	private LinkedList<Piece> bPieces;

	// list of all white pieces
	private static String whitePieceTypes = "PNBRQK";

	// side to move
	private char side;

	// saves the en-passant square, if one exists
	private Tile ep;

	// stores the number of half moves made in the game so far
	private int halfmoves;

	// stores the number of full moves made in the game so far
	private int fullmoves;

	// keeps track of castling rights in the form of KQkq
	private boolean[] castlingrights;

	// list of moves made in the game
	private Stack<String> movehistory;

	// each game has its own AI
	private AI ai;
	private int level;

	// only one piece's moves will be highlighted at a time
	private boolean isHighlighted;

	// holds a highlighted piece
	private Piece tempHold;

	// says if the board is flipped
	private boolean isFlipped;

	private Piece wKing;
	private Piece bKing;

	// J Swing Elements that make up the GUI
	private JFrame frame;
	private JTextArea fentext;
	private JPanel sidepanel;
	private JLabel label;

	public Game(int level)
	{
		this.side = 'w';
		this.movehistory = new Stack<String>();
		this.halfmoves = 0;
		this.ep = null;
		this.castlingrights = new boolean[4];
		this.level = level;
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
		setFEN("rnbqkbnr/pppppppp/8/8/8/8/PPPPPPPP/RNBQKBNR w KQkq - 0 1");
		if (level != 0) {
			this.ai = new AI(this, level);
			setAutoFlip(false);
		}
		setUI();
	}

	// initializes the game GUI
	public void setUI()
	{
		init();
		this.frame = new JFrame("Chess SPE");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setLayout(new GridBagLayout());

		// FEN text area
		fentext          = new JTextArea("rnbqkbnr/pppppppp/8/8/8/8/PPPPPPPP/RNBQKBNR w KQkq - 0 1");
		fentext.setPreferredSize(new Dimension(480, 20));

		// FEN text area layout
		GridBagConstraints textbag = new GridBagConstraints();
		textbag.gridx              = 0;
		textbag.gridy              = 0;
		textbag.gridwidth          = GridBagConstraints.RELATIVE;
		textbag.insets             = new Insets(10, 10, 0, 10);

		// FEN button
		Button fenbutton             = new Button("Set FEN");;
		SetFEN newfen                = new SetFEN(this, level);
		fenbutton.setPreferredSize(new Dimension(110, 30));
		fenbutton.addActionListener(newfen);

		// FEN button layout
		GridBagConstraints buttonbag = new GridBagConstraints();
		buttonbag.gridx              = 1;
		buttonbag.gridy              = 0;
		buttonbag.gridwidth          = GridBagConstraints.REMAINDER;
		buttonbag.insets             = new Insets(10, 0, 0, 10);
		
		// chessboard panel
		JPanel boardpanel = board.getPanel();
		boardpanel.setBorder(BorderFactory.createLineBorder(Color.black, 2));
		boardpanel.setVisible(true);

		// chessboard panel layout
		GridBagConstraints boardpanelbag = new GridBagConstraints();
		boardpanelbag.gridx              = 0;
		boardpanelbag.gridy              = 1;
		boardpanelbag.gridwidth          = GridBagConstraints.RELATIVE;
		boardpanelbag.insets             = new Insets(10, 10, 10, 10);

		// move now button
		Button movenow = new Button("Move Now");
		movenow.setPreferredSize(new Dimension(110, 30));
		MoveNow aimove = new MoveNow(this, level);
		movenow.addActionListener(aimove);

		// new game button
		Button newgame = new Button("New Game");
		newgame.setPreferredSize(new Dimension(110, 30));
		NewGame reset = new NewGame(this, level);
		newgame.addActionListener(reset);

		// flip board button
		Button flipboard = new Button("Flip Board");
		flipboard.setPreferredSize(new Dimension(110, 30));
		FlipBoard flip = new FlipBoard(this, level);
		flipboard.addActionListener(flip);

		// take move button
		Button takemove = new Button("Take Move");
		takemove.setPreferredSize(new Dimension(110, 30));
		TakeMove takeback = new TakeMove(this, level);
		takemove.addActionListener(takeback);

		// toggle button for autoflip
		Button toggle = new Button("Toggle");
		toggle.setPreferredSize(new Dimension(110, 30));
		AutoFlip auto = new AutoFlip(this, level);
		toggle.addActionListener(auto);

		// text displying autoflip status
		String autoflipstatus = "";
		if (getAutoFlip()) { autoflipstatus = "AutoFlip On"; }
		else autoflipstatus = "AutoFlip Off";
		JLabel autofliplabel = new JLabel(autoflipstatus);

		// side panel
		this.sidepanel = new JPanel();
		sidepanel.add(movenow);
		sidepanel.add(newgame);
		sidepanel.add(flipboard);
		sidepanel.add(takemove);
		sidepanel.add(toggle);
		sidepanel.add(autofliplabel);
		sidepanel.setPreferredSize(new Dimension(100, 480));
		sidepanel.setVisible(true);

		// side panel layout
		GridBagConstraints sidepanelbag = new GridBagConstraints();
		sidepanelbag.gridwidth          = GridBagConstraints.REMAINDER;
		sidepanelbag.insets             = new Insets(10, 0, 10, 10);
		sidepanelbag.gridy              = 1;
		sidepanelbag.gridx              = 1;
		
		frame.add(fentext, textbag);
		frame.add(fenbutton, buttonbag);
		frame.add(sidepanel, sidepanelbag);
		frame.add(boardpanel, boardpanelbag);
		frame.pack();
		frame.setVisible(true);
	}

	// returns the current game board
	public Board getBoard() { return board; }

	// returns the tiles array
	public Tile[][] getTiles() { return tiles; }

	// returns the tile at the given rank and file
	public Tile getTile(int rank, int file) { return board.getTile(rank, file); }

	// sets the list of white pieces on the board to the given list
	public void setWhitePieces(LinkedList<Piece> white) { wPieces = white; }

	// returns the list of white pieces on the board
	public LinkedList<Piece> getWhitePieces() { return wPieces; }

	// sets the list of black pieces on the board to the given list
	public void setBlackPieces(LinkedList<Piece> black) { bPieces = black; }

	// returns the list of black pieces on the board
	public LinkedList<Piece> getBlackPieces() { return bPieces; }

	// returns the side to move
	public char getSide() { return side; }

	// sets the side to move
	public void setSide(char side) { this.side = side; }

	// returns the en-passant square, if one exists
	public Tile getEP() { return ep; }

	// sets the en-passant square to the given en-passant square
	public void setEP(Tile ep) { this.ep = ep; }

	// returns the number of half moves made in the game
	public int getHalfMoves() { return halfmoves; }

	// sets the number of half moves made in the game to the given number
	public void setHalfMoves(int halfmoves) { this.halfmoves = halfmoves; }

	// returns the number of full moves made in the game
	public int getFullMoves() { return fullmoves; }

	// sets the number of full moves made in the game to the given number
	public void setFullMoves(int fullmoves) { this.fullmoves = fullmoves; }

	// returns the current castling rights
	public boolean[] getCastlingRights() { return castlingrights; }

	// sets the current castling rights to the given values
	public void setCastlingRights(int which, boolean tf) { castlingrights[which] = tf; }

	// returns the list of moves made in the game in the form of FEN position strings
	public Stack<String> getMoveHistory() { return movehistory; }

	// lists the positions reached in the game so far
	public void listHistory()
	{
		Stack<String> holder = new Stack<String>();
		while (!movehistory.isEmpty()) {
			String temp = movehistory.pop();
			holder.push(temp);
		}
		while (!holder.isEmpty()) {
			movehistory.push(holder.pop());
		}
	}

	// sets the AI to the given AI
	public void setAI(AI ai) { this.ai = ai; }

	// returns the current AI
	public AI getAI() { return ai; }

	// returns the current AI level
	public int getLevel() { return level; }

	// sets the current AI level
	public void setLevel(int level) { this.level = level; }

	// makes random move with current AI
	public void makeAIMove()
	{
		ai.makeAIMove();
		draw();
	}

	// sets the board's highlighted squares status (for highlighting a piece's moves)
	public void setHighlight(boolean b) { isHighlighted = b; }

	// returns the board's highlighted squares status
	public boolean getHighlight() { return isHighlighted; }

	// supports highlighting piece moves function
	public void hold(Piece piece) { tempHold = piece; }
	public Piece getHold() { return tempHold; }
	public void endHold() { tempHold = null; }

	// returns the board orientation
	public boolean getFlip() { return isFlipped; }

	// flips the board orientation and the side to move
	public void flip()
	{
		isFlipped = !isFlipped;
		if (side == 'w') side = 'b';
		else side = 'w';
	}

	// toggles the board orientation
	public void toggleFlip() { isFlipped = !isFlipped; }

	// returns the board's autoflip status
	public boolean getAutoFlip() { return board.getAutoFlip(); }

	// sets the board's autoflip function
	public void setAutoFlip(boolean autoflip) { board.setAutoFlip(autoflip); }

	// toggles the board's autoflip function
	public void toggleAutoFlip() { board.toggleAutoFlip(); }

	// sets the white king to the given king piece
	public void setWhiteKing(Piece wK) { wKing = wK; }

	// returns the board's white king
	public Piece getWhiteKing() { return wKing; }

	// sets the black king to the given king piece
	public void setBlackKing(Piece bK) { bKing = bK; }

	// returns the board's black king
	public Piece getBlackKing() { return bKing; }

	// returns the current board frame from the GUI
	public JFrame getFrame() { return frame; }

	// returns the FEN text area
	public JTextArea getTextArea() { return fentext; }

	// returns the board's JPanel used for the GUI
	public JPanel getBoardPanel() { return board.getPanel(); }

	// ???
	public void show() { board.show(); }

	// draws the current state of the board
	public void init() { board.draw(); }

	// resets the board to the starting arrangement
	public void reset() { board.reset(); }

	// draws the current state of the board for the GUI
	public void draw()
	{
		board.draw();
		frame.pack();
		frame.setVisible(true);
	}

	// draws the current state of the board with a given piece's moves highlighted
	public void draw(Stack<Tile> toHighlight)
	{
		board.draw(toHighlight);
		frame.pack();
		frame.setVisible(true);
	}

	// moves the given piece to the given square
	public void move(Piece piece, Tile target)
	{
		
		// mate and draw checks before a move is made to ensure players to not try to make illegal moves
		if (mateCheck()) return;
		if (drawCheck()) return;

		board.move(piece, target);

		// mate a draw checks after a move is made
		if (mateCheck()) {
			if (getSide() == 'b') {
				System.out.println("White Wins!");
			}
			else System.out.println("Black Wins!");
		}
		if (drawCheck()) { System.out.println("Draw"); }
	}

	// undoes the most recent move
	public void takeMove()
	{
		board.takeMove();
		draw();
	}

	// makes a move with the current AI
	public void aiMove(Piece piece, Tile target)
	{
		
		// mate and draw checks before AI moves to ensure it does not try to make an illegal move
		if (mateCheck()) {
			if (getSide() == 'b') {
				System.out.println("White Wins!");
				return;
			}
			else {
				System.out.println("Black Wins!");
				return;
			}
		}
		if (drawCheck()) { System.out.println("Draw"); }

		board.aiMove(piece, target);

		// mate and draw checks after AI moves
		if (mateCheck()) {
			if (getSide() == 'b') {
				System.out.println("White Wins!");
			}
			else System.out.println("Black Wins!");
		}
		if (drawCheck()) { System.out.println("Draw"); }
	}

	// determines whether or not one side is checkmated
	public boolean mateCheck()
	{
		Stack<Piece> stack = new Stack<Piece>();
		LinkedList<Piece> pieces;
		Piece oppking;
		if (getSide() == 'b') {
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

	// determines whether or not the game is a draw
	public boolean drawCheck()
	{
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
	public void setFEN(String fen)
	{
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

				if ((int) (currentChar) > 48 && (int) (currentChar) < 57) {
					
					int k = j;
					j += Character.getNumericValue(currentChar) - 1;
					do {
						tiles[i][k].empty();
						k++;
					}
					while (k <= j);
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
						whiteToSet.add(currentPiece);
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
			for (int i = 0; i < 4; i++) {
				setCastlingRights(i, false);
			}
		}
		boolean wkc = (fensplit[2].indexOf('K') != -1);
		boolean wqc = (fensplit[2].indexOf('Q') != -1);
		boolean bkc = (fensplit[2].indexOf('k') != -1);
		boolean bqc = (fensplit[2].indexOf('q') != -1);
		setCastlingRights(0, wkc);
		setCastlingRights(1, wqc);
		setCastlingRights(2, bkc);
		setCastlingRights(3, bqc);

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

	// creates a FEN string to store in the move history stack
	public String createFEN()
	{
		StringBuilder fen = new StringBuilder();

		// iterate through the board from left to right and down starting at a8
		for (int rank = 7; rank >= 0; rank--) {
			int count = 0;
			for (int file = 0; file < 8; file++) {
				Tile currentTile = tiles[rank][file];
				if (currentTile.getPiece() != null) {
					if (count != 0) {
						fen.append((char)(count+48));
						count = 0;
					}
					char pieceType = currentTile.getPiece().getType();
					fen.append(pieceType);
				} else {
					count++;
					if (file == 7) {
						fen.append((char)(count+48));
					}
				}
			}
			if (rank > 0) { fen.append('/'); }
		}

		// the side to move
		fen.append(" " + getSide() + " ");

		// castling rights
		if (castlingrights[0]) {
			fen.append('K');
		}
		if (castlingrights[1]) {
			fen.append('Q');
		}
		if (castlingrights[2]) {
			fen.append('k');
		}
		if (castlingrights[3]) {
			fen.append('q');
		}
		if (!castlingrights[0] && !castlingrights[1] && !castlingrights[2] && !castlingrights[3]) {
			fen.append('-');
		}

		// ep square, if one exists
		if (getEP() != null) {
			char ep = (char) ((int) (getEP().getID().charAt(0)) + 97);
			fen.append(" " + ep + " ");
		} else {
			fen.append(" " + '-' + " ");
		}

		// the number of half moves
		char halfmoves = (char) (getHalfMoves()+48);
		fen.append(halfmoves + " ");

		// the number of full moves
		int fullmoves = (int) (getHalfMoves()/2);
		if (fullmoves < 1) {
			fen.append('1');
		} else {
			fen.append((char)(fullmoves+48));
		}
		return fen.toString();
	}

	// unit testing of ChessSPE classes
	public static void main(String[] args)
	{
		int level = Integer.parseInt(args[0]);
		Game game = new Game(level);
		while(true) {
			JFrame toShow = game.getFrame();
			toShow.pack();
			toShow.setVisible(true);
		}
	}
}