import java.util.Stack;
import java.util.LinkedList;
import java.io.*;
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
    
    // list of moves made in the game, used for 3rep
    private Stack<String> movehistory;
    
    // This holds the game's series of memory objects, used for loading the board
    private Stack<Memory> memory;

    // Return value for a checkmate
    private final static int MATE = 1;

    // return value for a draw
    private final static int DRAW = 0;
    
    // each game has its own AI
    private AI ai;

    // determines the level of the AI, if present
    private int level;
    
    // only one piece's moves will be highlighted at a time
    private boolean isHighlighted;
    
    // holds a highlighted piece
    private Piece tempHold;
    
    // says if the board is flipped
    private boolean isFlipped;
    
    // pointer to each king
    private Piece wKing;
    private Piece bKing;
    
    // J Swing Elements that make up the GUI
    private JFrame frame;
    private JTextArea fentext;
    private JPanel sidepanel;
    
    public Game(int level)
    {
        this.side = 'w';
        this.movehistory = new Stack<String>();
        this.memory = new Stack<Memory>();
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
        //setFEN("8/8/8/8/6q1/8/5k2/7K w - - 0 1");
        setFEN("rnbqkbnr/pppppppp/8/8/8/8/PPPPPPPP/RNBQKBNR w KQkq - 0 1");
        //setFEN("r3k2r/pppppppp/8/8/8/8/PPPPPPPP/R3K2R w KQkq - 0 1");

        if (level != 0) {
            this.ai = new AI(this, level);
        }
        setUI();
    }
    
    // initializes the game GUI
    public void setUI()
    {
        init();
        String frameName = "Chess SPE";
        switch (level) {
            case 0: break;
            case 1: frameName += " -- Puck";   break;
            case 2: frameName += " -- Victus"; break;
            case 3: frameName += " -- Nemo"; break;
            case 4: frameName += " -- Quiet Nemo"; break;
            case 5: frameName += " -- Charitable Nemo"; break;
            default:; break;
        };
        this.frame = new JFrame(frameName);
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
        
        // side panel
        this.sidepanel = new JPanel();
        sidepanel.add(movenow);
        sidepanel.add(newgame);
        sidepanel.add(flipboard);
        sidepanel.add(takemove);
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
    public void setBlackPieces(LinkedList<Piece> black) { bPieces = black;}
    
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
    
    public void emptyMoveHistory() { movehistory = new Stack<String>();}
    
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
    public void makeAIMove() throws IOException
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

    public void move(Move m) {
        int oRank = Character.getNumericValue(m.getOrigin().charAt(0));
        int oFile = Character.getNumericValue(m.getOrigin().charAt(1));
        int tRank = Character.getNumericValue(m.getTarget().charAt(0));
        int tFile = Character.getNumericValue(m.getTarget().charAt(1));

        move(getTile(oRank, oFile).getPiece(), getTile(tRank, tFile));
    }
    
    // moves the given piece to the given square
    public void move(Piece piece, Tile target)
    {
        
        setMemory();
        // mate and draw checks before a move is made to ensure players to not try to make illegal moves
        if (mateDrawCheck() != -1) return;
        
        board.move(piece, target);
        halfmoves++;
        flip();

        if (mateDrawCheck() != -1 ) return;
    }
    
    // undoes the most recent move
    public void takeMove()
    {
        Memory toLoad = memory.pop();
        //this.side = toLoad.getSide();
        this.ep = toLoad.getEP();
        this.halfmoves = toLoad.getHalfMoves();
        this.castlingrights = toLoad.getCastlingRights();
        Piece[][] toPlace = toLoad.getPieces();
        board.clear();
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                if (toPlace[i][j] != null) {
                    this.tiles[i][j].place(toPlace[i][j]);
                    toPlace[i][j].setTile(tiles[i][j]);
                    toPlace[i][j].setType(toLoad.getTypes()[i][j]);
                }
                else this.tiles[i][j].empty();
            }
        }
        
        if (!movehistory.isEmpty()) {
            movehistory.pop();
        }
        flip();
    }
    
    // determines whether or not one side is checkmated or stalemated
    public int mateDrawCheck()
    {
        // checks for mate or stalemate
        Stack<Piece> whitemoves = new Stack<Piece>();
        Stack<Piece> blackmoves = new Stack<Piece>();
        Piece wKing = getWhiteKing();
        Piece bKing = getBlackKing();
        for (Piece piece: wPieces) {
            if (piece.getTile() != null) {
                if (!piece.moves().isEmpty()) {
                    whitemoves.push(piece);
                }
            }
        }
        for (Piece piece: bPieces) {
            if (piece.getTile() != null) {
                if (!piece.moves().isEmpty()) {
                    blackmoves.push(piece);
                }
            }
        }
        if (whitemoves.isEmpty()) {
            if (side == 'w') {
                if (wKing.getTile().isAttacked()) {
                    return MATE;
                } 
                return DRAW;
            }
        }

        else if (blackmoves.isEmpty()) {
            if (side == 'b') {
                if (bKing.getTile().isAttacked()) {
                    return MATE;
                } 
                return DRAW;
            }
        }

        // checks the fifty move rule
        if (halfmoves == 100) return DRAW;
        
        // checks three repetition rule
        if (!movehistory.isEmpty()) {
            String currentPosition    = movehistory.peek();
            int threerep              = 0;
            for (String position: movehistory) {
                if (position.equals(currentPosition)) threerep++;
            }
            if (threerep >= 3) return DRAW;
        }
        
        // checks for material draws
        int nbrofwp = 0;
        int nbrofwn = 0;
        int nbrofwb = 0;
        int nbrofwr = 0;
        int nbrofwq = 0;
        
        int nbrofbp = 0;
        int nbrofbn = 0;
        int nbrofbb = 0;
        int nbrofbr = 0;
        int nbrofbq = 0;
        
        for (Piece piece: wPieces) {
            if (piece.getType() == 'P') { nbrofwp++; }
            else if (piece.getType() == 'N') { nbrofwn++; }
            else if (piece.getType() == 'B') { nbrofwb++; }
            else if (piece.getType() == 'R') { nbrofwr++; }
            else if (piece.getType() == 'Q') { nbrofwq++; }
        }
        
        for (Piece piece: bPieces) {
            if (piece.getType() == 'p') { nbrofbp++; }
            else if (piece.getType() == 'n') { nbrofbn++; }
            else if (piece.getType() == 'b') { nbrofbb++; }
            else if (piece.getType() == 'r') { nbrofbr++; }
            else if (piece.getType() == 'q') { nbrofbq++; }
        }    
        
        // material draws by definition
        if (nbrofwp == 0 && nbrofbp == 0) {
            if (0 == nbrofwr && 0 == nbrofbr && 0 == nbrofwq && 0 == nbrofbq) {
                if (0 == nbrofbb && 0 == nbrofwb) {
                    if (nbrofwn < 3 && nbrofbn < 3) {  return DRAW; }
                } else if (0 == nbrofwn && 0 == nbrofbn) {
                    if (Math.abs(nbrofwb - nbrofbb) < 2) { return DRAW; }
                } else if ((nbrofwn < 3 && 0 == nbrofwb) || (nbrofwb == 1 && 0 == nbrofwn)) {
                    if ((nbrofbn < 3 && 0 == nbrofbb) || (nbrofbb == 1 && 0 == nbrofbn))  { return DRAW; }
                }
            } else if (0 == nbrofwq && 0 == nbrofbq) {
                if (nbrofwr == 1 && nbrofbr == 1) {
                    if ((nbrofwn + nbrofwb) < 2 && (nbrofbn + nbrofbb) < 2) { return DRAW; }
                } else if (nbrofwr == 1 && 0 == nbrofbr) {
                    if ((nbrofwn + nbrofwb == 0) && (((nbrofbn + nbrofbb) == 1) || ((nbrofbn + nbrofbb) == 2))) { return DRAW; }
                } else if (nbrofbr == 1 && 0 == nbrofwr) {
                    if ((nbrofbn + nbrofbb == 0) && (((nbrofwn + nbrofwb) == 1) || ((nbrofwn + nbrofwb) == 2))) { return DRAW; }
                }
            }
        }
        
        return -1;
    }
    
    // sets the Gameboard to the current FEN string
    public void setFEN(String fen)
    { 
        board.clear();
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
                    else { blackToSet.add(currentPiece); }
                    
                    if (currentChar =='K') setWhiteKing(currentPiece);
                    else if (currentChar == 'k') setBlackKing(currentPiece);
                }
                count++;
            }
        }
        
        wPieces = whiteToSet;
        setBlackPieces(blackToSet);
        
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
        fen.append(" " + side + " ");
        
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
        if (ep != null) {
            char epfile = (char) (Character.getNumericValue(ep.getID().charAt(1)) + 97);
            char eprank =(char) (Character.getNumericValue(ep.getID().charAt(0)) + 49);
            fen.append(" " + epfile + eprank + " ");
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
    
    public void setMemory() {
        Memory toStore = new Memory(this);
        memory.push(toStore);
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