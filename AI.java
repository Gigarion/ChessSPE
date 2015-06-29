import java.util.LinkedList;
import java.util.*;
import java.util.Stack;
import java.util.Scanner;
import java.io.*;

public class AI
{
    private static int[][] pawnTable = 
    {
        {0   ,   0   ,   0   ,   0   ,   0   ,   0   ,   0   ,   0   },
        {10  ,   10  ,   0   ,   -10 ,   -10 ,   0   ,   10  ,   10  },
        {5   ,   0   ,   0   ,   5   ,   5   ,   0   ,   0   ,   5   },
        {0   ,   0   ,   10  ,   20  ,   20  ,   10  ,   0   ,   0   },
        {5   ,   5   ,   5   ,   10  ,   10  ,   5   ,   5   ,   5   },
        {10  ,   10  ,   10  ,   20  ,   20  ,   10  ,   10  ,   10  },
        {20  ,   20  ,   20  ,   30  ,   30  ,   20  ,   20  ,   20  },
        {0   ,   0   ,   0   ,   0   ,   0   ,   0   ,   0   ,   0   },      
    };

    private static int[][] knightTable = {
        {0   ,   -10 ,   0   ,   0   ,   0   ,   0   ,   -10 ,   0   },
        {0   ,   0   ,   0   ,   5   ,   5   ,   0   ,   0   ,   0   },
        {0   ,   0   ,   10  ,   10  ,   10  ,   10  ,   0   ,   0   },
        {0   ,   0   ,   10  ,   20  ,   20  ,   10  ,   5   ,   0   },
        {5   ,   10  ,   15  ,   20  ,   20  ,   15  ,   10  ,   5   },
        {5   ,   10  ,   10  ,   20  ,   20  ,   10  ,   10  ,   5   },
        {0   ,   0   ,   5   ,   10  ,   10  ,   5   ,   0   ,   0   },
        {0   ,   0   ,   0   ,   0   ,   0   ,   0   ,   0   ,   0   },
    };

    private static int[][] bishopTable = {
        {0   ,   0   ,   -10 ,   0   ,   0   ,   -10 ,   0   ,   0   },
        {0   ,   0   ,   0   ,   10  ,   10  ,   0   ,   0   ,   0   },
        {0   ,   0   ,   10  ,   15  ,   15  ,   10  ,   0   ,   0   },
        {0   ,   10  ,   15  ,   20  ,   20  ,   15  ,   10  ,   0   },
        {0   ,   10  ,   15  ,   20  ,   20  ,   15  ,   10  ,   0   },
        {0   ,   0   ,   10  ,   15  ,   15  ,   10  ,   0   ,   0   },
        {0   ,   0   ,   0   ,   10  ,   10  ,   0   ,   0   ,   0   },
        {0   ,   0   ,   0   ,   0   ,   0   ,   0   ,   0   ,   0   },   

    };

    private static int[][] rookTable = {
        {0   ,   0   ,   5   ,   10  ,   10  ,   5   ,   0   ,   0  },
        {0   ,   0   ,   5   ,   10  ,   10  ,   5   ,   0   ,   0  },
        {0   ,   0   ,   5   ,   10  ,   10  ,   5   ,   0   ,   0  },
        {0   ,   0   ,   5   ,   10  ,   10  ,   5   ,   0   ,   0  },
        {0   ,   0   ,   5   ,   10  ,   10  ,   5   ,   0   ,   0  },
        {0   ,   0   ,   5   ,   10  ,   10  ,   5   ,   0   ,   0  },
        {25  ,   25  ,   25  ,   25  ,   25  ,   25  ,   25  ,   25 },
        {0   ,   0   ,   5   ,   10  ,   10  ,   5   ,   0   ,   0  },
    };

    private static int[][] queenTable = {
        {0   ,   0   ,   5   ,   10  ,   10  ,   5   ,   0   ,   0  },
        {0   ,   0   ,   5   ,   10  ,   10  ,   5   ,   0   ,   0  },
        {0   ,   0   ,   5   ,   10  ,   10  ,   5   ,   0   ,   0  },
        {0   ,   0   ,   5   ,   20  ,   20  ,   5   ,   0   ,   0  },
        {0   ,   0   ,   5   ,   20  ,   20  ,   5   ,   0   ,   0  },
        {0   ,   0   ,   5   ,   10  ,   10  ,   5   ,   0   ,   0  },
        {25  ,   25  ,   25  ,   25  ,   25  ,   25  ,   25  ,   25 },
        {0   ,   0   ,   5   ,   10  ,   10  ,   5   ,   0   ,   0  },
    };

    private static int[][] kingOpenTable = {
          {0 ,     5 ,     5 ,   -10 ,   -10 ,     0 ,    10 ,     5 },
        {-30 ,   -30 ,   -30 ,   -30 ,   -30 ,   -30 ,   -30 ,   -30 },
        {-50 ,   -50 ,   -50 ,   -50 ,   -50 ,   -50 ,   -50 ,   -50 },
        {-70 ,   -70 ,   -70 ,   -70 ,   -70 ,   -70 ,   -70 ,   -70 },
        {-70 ,   -70 ,   -70 ,   -70 ,   -70 ,   -70 ,   -70 ,   -70 },
        {-70 ,   -70 ,   -70 ,   -70 ,   -70 ,   -70 ,   -70 ,   -70 },
        {-70 ,   -70 ,   -70 ,   -70 ,   -70 ,   -70 ,   -70 ,   -70 },
        {-70 ,   -70 ,   -70 ,   -70 ,   -70 ,   -70 ,   -70 ,   -70 },
    };

    private static int[][] kingEndTable = {
        {-50 ,   -10 ,   0   ,   0   ,   0   ,   0   ,   -10 ,   -50 },
        {-10,    0   ,   10  ,   10  ,   10  ,   10  ,   0   ,   -10 },
        {0   ,   10  ,   20  ,   20  ,   20  ,   20  ,   10  ,   0   },
        {0   ,   10  ,   20  ,   40  ,   40  ,   20  ,   10  ,   0   },
        {0   ,   10  ,   20  ,   40  ,   40  ,   20  ,   10  ,   0   },
        {0   ,   10  ,   20  ,   20  ,   20  ,   20  ,   10  ,   0   },
        {-10,    0   ,   10  ,   10  ,   10  ,   10  ,   0   ,   -10 },
        {-50 ,   -10 ,   0   ,   0   ,   0   ,   0   ,   -10 ,   -50 }, 
    };

    private static final File MATEFILE = new File("mateHistory.txt");
    private HashMap<String, Integer> checkMap = new HashMap<String, Integer>();
    private HashMap<String, TPData> tpTable = new HashMap<String, TPData>();

    // records the state of the game
    private boolean endgame = false;

    // returns the state of the game
    public boolean getEndgame() { return endgame; }

    // sets the state of the game
    public void setEndgame(boolean endgame) { this.endgame = endgame; }

    // determines whether or not the endgame has been reached
    
    public AI(Game game, int level) throws IOException
    {
        this.game = game;
        this.level = level;
        this.pvtable = new PVTable();
        setCheckMap();
        if (game.getSide() == 'w') { pieces = game.getWhitePieces(); }
        else { pieces = game.getBlackPieces(); }
    }

    public void isEndgame() {

        int whitematerial = 0;
        int blackmaterial = 0;

        // counts up the total value of the white pieces
        LinkedList<Piece> wPieces = game.getWhitePieces();
        for (Piece piece: wPieces) {
            whitematerial += piece.getValue();
        }

        // counts up the total value of the black pieces
        LinkedList<Piece> bPieces = game.getBlackPieces();
        for (Piece piece: bPieces) {
            blackmaterial += piece.getValue();
        }

        // endgame defined as both sides having less than a rook, minor piece and pawn
        int endgamematerial = 500 + 300 + 100;

        if (whitematerial <= endgamematerial && blackmaterial <= endgamematerial) { endgame = true; }
        endgame = false;
    }

    private final static int MATE = 1;

    private final static int DRAW = 0;

    private PVTable pvtable;

    // level of difficulty of AI
    private int level;

    // current game
    private Game game;

    // list of pieces for the side that moves
    private LinkedList<Piece> pieces;

    // returns the current AI's level of difficulty
    public int getLevel() { return level; }

    // sets the current AI's level of difficulty
    public void setLevel(int level) { this.level = level; }

    // returns the current AI's game
    public Game getGame() { return game; }

    // sets the current AI's game
    public void setGame(Game game) { this.game = game; }

    // initializes the pieces for the AI to control
    public void setPieces()
    {
        if (game.getSide() == 'w') pieces = game.getWhitePieces();
        else pieces = game.getBlackPieces();
    }    

    // makes a move with the current AI
    public void makeAIMove() throws IOException {

        if (game.mateDrawCheck() == MATE) {
            if (game.getSide() == 'b') {
                System.out.println("White Wins!");
                return;
            }
            else {
                System.out.println("Black Wins!");
                return;
            }
        }

        else if (game.mateDrawCheck() == DRAW) {
            System.out.println("Draw");
            return;
        }

        File tempFile = new File("temp.txt");
        File file = new File("AIFile.txt");
        file.createNewFile();
        tempFile.createNewFile();
        PrintWriter tempWriter = new PrintWriter(tempFile);
        Scanner saveMe = new Scanner(file); 
        while (saveMe.hasNextLine()) {
            tempWriter.println(saveMe.nextLine());
        }
        saveMe.close();
        tempWriter.close();

        Scanner reWrite = new Scanner(tempFile);

        PrintWriter printwriter = new PrintWriter(file);

        while (reWrite.hasNextLine()) {
            printwriter.println(reWrite.nextLine());
        }

        long starttime = System.currentTimeMillis();

        switch(level) {
            case 1: makeRandomAIMove(); break;
            default: makeSmartMove(); break;
        }

        long endtime = System.currentTimeMillis();

        printwriter.println("*****************");
        printwriter.println("Time: " + (endtime - starttime));
        printwriter.println("*****************");

        printwriter.close();

        if (game.mateDrawCheck() == MATE) {
            if (game.getSide() == 'b') {
                System.out.println("White Wins!");
                return;
            }
            else {
                System.out.println("Black Wins!");
                return;
            }
        }

        else if (game.mateDrawCheck() == DRAW) {
            System.out.println("Draw");
            return;
        }

    }

    //chooses random tile from random pieces in the linkedlist
    public void makeRandomAIMove() throws IOException {

        // to keep track of pieces with legal moves
        Stack<Piece> stack = new Stack<Piece>();

        setPieces();
        int size = 0;
        
        // places all pieces with valid moves onto the stack
        for (Piece currentPiece: pieces)
        {
            if (!currentPiece.moves().isEmpty())
            {
                stack.push(currentPiece);
                size++;
            }
        }
        
        Piece targetPiece = stack.pop();
        
        int randPiece = (int) (Math.random()*size);
        
        // chooses random piece from the stack
        for (int i = 0; i < randPiece; i++) { targetPiece = stack.pop(); }
        
        // list of tiles for the random piece
        Stack<Tile> tiles = targetPiece.moves();
        Tile targetTile = tiles.pop();
        int count = 0;
        
        for (Tile currentTile: tiles) { count++; }
        
        int randTile = (int) (Math.random() * count);

        // choose random tile
        for (int i = 0; i < randTile; i++) { targetTile = tiles.pop(); }

        // makes move with random piece and random tile
        game.move(targetPiece, targetTile);

    }

    public Move getMove(int maxDepth, Move move, boolean isMaximizer) throws IOException {
        int alpha = Integer.MIN_VALUE;
        int beta  = Integer.MAX_VALUE;
        int temp = 0;
        tpTable.clear();
        switch(level) {
            case 0: temp = 0; break;
            case 1: temp = getNextLevelChild(maxDepth, move, isMaximizer, alpha, beta); System.out.println("NextLevel"); break;
            // vanilla victus
            case 2: temp = getCVVictus(maxDepth, move, isMaximizer, alpha, beta); System.out.println("Victus"); break;
            // victus with pv and sorting
            case 3: temp = getNemoValue(maxDepth, move, isMaximizer, alpha, beta); System.out.println("NullMove");break;
            // null move with pv and sorting
            case 4: temp = quietNemo(maxDepth, move, isMaximizer, alpha, beta); System.out.println("Quiet Victus"); break;
            // quiescencde w/null move
            case 5: temp = getNemoValue(maxDepth, move, isMaximizer, alpha, beta); System.out.println("Charitable Nemo"); break;
        }
        System.out.println((1.0 * fhf / fh) * 100);
        System.out.println(temp);
        return move.getBestChild();
    }

    int fhf = 0;
    int fh  = 0;
    int test = 0;

    private int getCVVictus(int maxDepth, Move move, boolean isMaximizer, int alpha, int beta) throws IOException {
        if (maxDepth <= 0) {
            // if this is a leaf, find the value of the current board,
            // set it as the value of this move, and return this move
            //System.out.println(game.createFEN());
            return evaluateScore();
        }

        setPieces();

        ArrayList<Move> moveList = new ArrayList<Move>();

        for (Piece p : pieces) {
            if (p.getTile() != null) {
                for (Tile t : p.moves()) {
                    Move toAdd = new Move(p, t);
                    moveList.add(toAdd);
                }
            }
        }
        Collections.sort(moveList);
        if (moveList.isEmpty()) {
            int whiteMoveCount = 0;
            int blackMoveCount = 0;
            Piece wKing = game.getWhiteKing();
            Piece bKing = game.getBlackKing();
            for (Piece piece: game.getWhitePieces()) {
                if (piece.getTile() != null) {
                    if (!piece.moves().isEmpty()) {
                        whiteMoveCount++;                    }
                }
            }
            for (Piece piece: game.getBlackPieces()) {
                if (piece.getTile() != null) {
                    if (!piece.moves().isEmpty()) {
                        blackMoveCount++;
                    }
                }
            }
            if (whiteMoveCount == 0) {
                if (wKing.getTile().isAttacked()) return -10000;
                return 0;
            }
            else if (blackMoveCount == 0) {
                if (bKing.getTile().isAttacked()) return 10000;
                return 0;
            }
        }


        maxDepth--;
        // deepen

        int thisValue = 0;
        int count = 0;

        if (isMaximizer) {
            // if this is a maximizer node, the first child must be a larger number
            // also matechecks
            thisValue = Integer.MIN_VALUE;
            for (Move m : moveList) {
                game.move(m);
                count++;
                thisValue = getCVVictus(maxDepth, m, !isMaximizer, alpha, beta);
                game.takeMove();
                // if maximizer and child's value is higher than current value, update
                if (thisValue >= beta) {
                    if (count == 1) {
                        fhf++;
                    }
                    fh++;
                    //System.out.println("BETA CUTOFF MINIMIZER >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
                    return beta;
                }
                if (thisValue > alpha) {
                    move.setBestChild(m);
                    alpha = thisValue;
                    pvtable.addMove(maxDepth, m);
                }
            }
            return alpha;
        }

        else {
            thisValue = Integer.MAX_VALUE;
            for (Move m: moveList) {
                game.move(m);
                count++;
                thisValue = getCVVictus(maxDepth, m, !isMaximizer, alpha, beta);
                game.takeMove();
                if (thisValue <= alpha) {
                    if (count == 1) {
                        fhf++;
                    }
                    fh++;
                    //System.out.println("**********************8ALPHA CUTOFF MINIMIZER");
                    return alpha;
                }
                // if minimizer and child's value is lower than current value, update
                if (thisValue < beta) {
                    move.setBestChild(m);
                    beta = thisValue;
                    pvtable.addMove(maxDepth, m);
                }
            }
            return beta;
        }
    }

    private int getNextLevelChild(int maxDepth, Move move, boolean isMaximizer, int alpha, int beta) throws IOException {
        if (maxDepth == 0) {
            // if this is a leaf, find the value of the current board,
            // set it as the value of this move, and return this move
            //System.out.println(game.createFEN());
            return evaluateScore();
        }

        setPieces();

        ArrayList<Move> moveList = new ArrayList<Move>();

        for (Piece p : pieces) {
            if (p.getTile() != null) {
                for (Tile t : p.moves()) {
                    Move toAdd = new Move(p, t);
                    moveList.add(toAdd);
                }
            }
        }

        if (moveList.isEmpty()) {
            int whiteMoveCount = 0;
            int blackMoveCount = 0;
            Piece wKing = game.getWhiteKing();
            Piece bKing = game.getBlackKing();
            for (Piece piece: game.getWhitePieces()) {
                if (piece.getTile() != null) {
                    if (!piece.moves().isEmpty()) {
                        whiteMoveCount++;                    }
                }
            }
            for (Piece piece: game.getBlackPieces()) {
                if (piece.getTile() != null) {
                    if (!piece.moves().isEmpty()) {
                        blackMoveCount++;
                    }
                }
            }
            if (whiteMoveCount == 0) {
                if (wKing.getTile().isAttacked()) return -10000;
                return 0;
            }
            else if (blackMoveCount == 0) {
                if (bKing.getTile().isAttacked()) return 10000;
                return 0;
            }
        }


        maxDepth--;
        // deepen

        int thisValue = 0;
        int count = 0;

        if (isMaximizer) {
            // if this is a maximizer node, the first child must be a larger number
            // also matechecks
            thisValue = Integer.MIN_VALUE;
            for (Move m : moveList) {
                game.move(m);
                count++;
                thisValue = getNextLevelChild(maxDepth, m, !isMaximizer, alpha, beta);
                game.takeMove();
                // if maximizer and child's value is higher than current value, update
                if (thisValue >= beta) {
                    if (count == 1) {
                        fhf++;
                    }
                    fh++;
                    //System.out.println("BETA CUTOFF MINIMIZER >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
                    return beta;
                }
                if (thisValue > alpha) {
                    move.setBestChild(m);
                    alpha = thisValue;
                }
            }
            return alpha;
        }

        else {
            thisValue = Integer.MAX_VALUE;
            for (Move m: moveList) {
                game.move(m);
                count++;
                thisValue = getNextLevelChild(maxDepth, m, !isMaximizer, alpha, beta);
                game.takeMove();
                if (thisValue <= alpha) {
                    if (count == 1) {
                        fhf++;
                    }
                    fh++;
                    //System.out.println("**********************8ALPHA CUTOFF MINIMIZER");
                    return alpha;
                }
                // if minimizer and child's value is lower than current value, update
                if (thisValue < beta) {
                    move.setBestChild(m);
                    beta = thisValue;
                }
                else {
                    m = null;
                }
            }
            return beta;
        }
    }

    private int getNemoValue(int maxDepth, Move move, boolean isMaximizer, int alpha, int beta) throws IOException {

        if (maxDepth <= 0) {
            // if this is a leaf, find the value of the current board,
            // set it as the value of this move, and return this move
            //System.out.println(game.createFEN());
            return evaluateScore();
        }


        if (tpTable.get(game.createFEN()) != null) {
            if (tpTable.get(game.createFEN()).getDepth() <= maxDepth) {
                TPData current = tpTable.get(game.createFEN());
                if (isMaximizer) {
                    if (current.getScore() >= beta)
                        return beta;
                    else if (current.getScore() < alpha)
                        return alpha;
                    else {
                        move.setBestChild(current.getBestChild());
                        alpha = current.getScore();
                    } 
                }

                else {
                    if (current.getScore() <= alpha)
                        return alpha;
                    else if (current.getScore() > beta)
                        return beta;
                    else {
                       move.setBestChild(current.getBestChild());
                       beta = current.getScore(); 
                    } 
                }
            }
        }

        int thisValue = 0;
        if (game.getSide() == 'w' && !game.getWhiteKing().getTile().isAttacked()) {
            int nm = nullMoveTest(maxDepth - 2, move, !isMaximizer, alpha, beta);

            if (isMaximizer && nm > beta) return beta;
            else if (!isMaximizer && nm < alpha) return alpha;
        }

        else if (game.getSide() == 'b' && !game.getBlackKing().getTile().isAttacked()) {
            int nm = nullMoveTest(maxDepth - 2, move, !isMaximizer, alpha, beta);

            if (isMaximizer && nm > beta) return beta;
            else if (!isMaximizer && nm < alpha) return alpha;
        }


        setPieces();

        ArrayList<Move> moveList = new ArrayList<Move>();

        for (Piece p : pieces) {
            if (p.getTile() != null) {
                for (Tile t : p.moves()) {
                    Move toAdd = new Move(p, t);
                    if (move.getBestChild() != null) {
                        if (move.getBestChild().getTarget().equals(toAdd.getTarget()) && move.getBestChild().getOrigin().equals(toAdd.getOrigin()))
                            toAdd.isTP();
                    }
                    if (pvtable.isMove(maxDepth, toAdd))
                        toAdd.isPV();
                    moveList.add(toAdd);
                }
            }
        }

        Collections.sort(moveList);

        if (moveList.isEmpty()) {
            int whiteMoveCount = 0;
            int blackMoveCount = 0;
            Piece wKing = game.getWhiteKing();
            Piece bKing = game.getBlackKing();
            for (Piece piece: game.getWhitePieces()) {
                if (piece.getTile() != null) {
                    if (!piece.moves().isEmpty()) {
                        whiteMoveCount++;                    }
                }
            }
            for (Piece piece: game.getBlackPieces()) {
                if (piece.getTile() != null) {
                    if (!piece.moves().isEmpty()) {
                        blackMoveCount++;
                    }
                }
            }
            if (whiteMoveCount == 0) {
                if (wKing.getTile().isAttacked()) {
                    saveMove(-10000);
                    return -10000;
                }
                return 0;
            }
            else if (blackMoveCount == 0) {
                if (bKing.getTile().isAttacked()) {
                    saveMove(10000);
                    return 10000;
                }
                return 0;
            }
        }


        maxDepth--;
        // deepen

        if (isMaximizer) {
            // if this is a maximizer node, the first child must be a larger number
            // also matechecks
            thisValue = Integer.MIN_VALUE;
            for (Move m : moveList) {
                game.move(m);
                thisValue = getNemoValue(maxDepth, m, !isMaximizer, alpha, beta);
                game.takeMove();
                // if maximizer and child's value is higher than current value, update
                if (thisValue >= beta) {
                    //System.out.println("BETA CUTOFF MINIMIZER >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
                    return beta;
                }
                if (thisValue > alpha) {
                    move.setBestChild(m);
                    alpha = thisValue;
                    pvtable.addMove(maxDepth, m);
                }
            }
            TPData toSave = new TPData(maxDepth, move.getBestChild(), thisValue);
            tpTable.put(game.createFEN(), toSave);
            return alpha;
        }

        else {
            thisValue = Integer.MAX_VALUE;
            for (Move m: moveList) {
                game.move(m);
                thisValue = getNemoValue(maxDepth, m, !isMaximizer, alpha, beta);
                game.takeMove();
                if (thisValue <= alpha) {
                    //System.out.println("**********************8ALPHA CUTOFF MINIMIZER");
                    return alpha;
                }
                // if minimizer and child's value is lower than current value, update
                if (thisValue < beta) {
                    move.setBestChild(m);
                    beta = thisValue;
                    pvtable.addMove(maxDepth, m);
                }
            }
            TPData toSave = new TPData(maxDepth, move.getBestChild(), thisValue);
            tpTable.put(game.createFEN(), toSave);
            return beta;
        }
    }

    private int nullMoveTest(int maxDepth, Move move, boolean isMaximizer, int alpha, int beta) throws IOException {
        game.flip();
        setPieces();
        Tile temp = null;
        if (game.getEP() != null)
            temp = game.getEP();
        game.setEP(null);
        Move protector = new Move();
        int toReturn = getCVVictus(maxDepth, protector, isMaximizer, alpha, beta);
        game.flip();
        if (temp != null) 
            game.setEP(temp);
        return toReturn;
    }

    private int getQueiscence(int maxDepth, Move move, boolean isMaximizer, int alpha, int beta) throws IOException {
        game.flip();
        setPieces();
        Tile temp = null;
        if (game.getEP() != null)
            temp = game.getEP();
        game.setEP(null);
        Move protector = new Move();
        int toReturn = quietNemo(maxDepth, protector, isMaximizer, alpha, beta);
        game.flip();
        if (temp != null) 
            game.setEP(temp);
        return toReturn;
    }

    private int quietNemo(int maxDepth, Move move, boolean isMaximizer, int alpha, int beta) throws IOException {
        if (maxDepth <= 0) {
            // if this is a leaf, find the value of the current board,
            // set it as the value of this move, and return this move
            //System.out.println(game.createFEN());
            return quiescence(move, isMaximizer, alpha, beta);
        }

        int thisValue = 0;
        if (game.getSide() == 'w' && !game.getWhiteKing().getTile().isAttacked()) {
            int nm = nullMoveTest(maxDepth - 2, move, !isMaximizer, alpha, beta);

            if (isMaximizer && nm > beta) return beta;
            else if (!isMaximizer && nm < alpha) return alpha;
        }

        else if (game.getSide() == 'b' && !game.getBlackKing().getTile().isAttacked()) {
            int nm = nullMoveTest(maxDepth - 2, move, !isMaximizer, alpha, beta);

            if (isMaximizer && nm > beta) return beta;
            else if (!isMaximizer && nm < alpha) return alpha;
        }


        setPieces();

        ArrayList<Move> moveList = new ArrayList<Move>();

        for (Piece p : pieces) {
            if (p.getTile() != null) {
                for (Tile t : p.moves()) {
                    Move toAdd = new Move(p, t);
                    moveList.add(toAdd);
                }
            }
        }
        Collections.sort(moveList);
        if (moveList.isEmpty()) {
            int whiteMoveCount = 0;
            int blackMoveCount = 0;
            Piece wKing = game.getWhiteKing();
            Piece bKing = game.getBlackKing();
            for (Piece piece: game.getWhitePieces()) {
                if (piece.getTile() != null) {
                    if (!piece.moves().isEmpty()) {
                        whiteMoveCount++;                    }
                }
            }
            for (Piece piece: game.getBlackPieces()) {
                if (piece.getTile() != null) {
                    if (!piece.moves().isEmpty()) {
                        blackMoveCount++;
                    }
                }
            }
            if (whiteMoveCount == 0) {
                if (wKing.getTile().isAttacked()) return -10000;
                return 0;
            }
            else if (blackMoveCount == 0) {
                if (bKing.getTile().isAttacked()) return 10000;
                return 0;
            }
        }


        maxDepth--;
        // deepen

        if (isMaximizer) {
            // if this is a maximizer node, the first child must be a larger number
            // also matechecks
            thisValue = Integer.MIN_VALUE;
            for (Move m : moveList) {
                game.move(m);
                thisValue = quietNemo(maxDepth, m, !isMaximizer, alpha, beta);
                game.takeMove();
                // if maximizer and child's value is higher than current value, update
                if (thisValue >= beta) {
                    //System.out.println("BETA CUTOFF MINIMIZER >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
                    return beta;
                }
                if (thisValue > alpha) {
                    move.setBestChild(m);
                    alpha = thisValue;
                    pvtable.addMove(maxDepth, m);
                }
                else {m = null;}
            }
            return alpha;
        }

        else {
            thisValue = Integer.MAX_VALUE;
            for (Move m: moveList) {
                game.move(m);
                thisValue = quietNemo(maxDepth, m, !isMaximizer, alpha, beta);
                game.takeMove();
                if (thisValue <= alpha) {
                    //System.out.println("**********************8ALPHA CUTOFF MINIMIZER");
                    return alpha;
                }
                // if minimizer and child's value is lower than current value, update
                if (thisValue < beta) {
                    move.setBestChild(m);
                    beta = thisValue;
                    pvtable.addMove(maxDepth, m);
                }
                else m = null;
            }
            return beta;
        }
    }

    private int quiescence(Move move, boolean isMaximizer, int alpha, int beta) throws IOException {
        int thisValue = evaluateScore();

        if (isMaximizer) {
            if (thisValue >= beta) return beta;
            if (thisValue > alpha) alpha = thisValue;
        }

        else {
            if (thisValue <= alpha) return alpha;
            if (thisValue < beta) beta = thisValue;
        }

        setPieces();

        ArrayList<Move> moveList = new ArrayList<Move>();

        for (Piece p : pieces) {
            if (p.getTile() != null) {
                for (Tile t : p.moves()) {
                    Move toAdd = new Move(p, t);
                    moveList.add(toAdd);
                }
            }
        }
        Collections.sort(moveList);
        if (moveList.isEmpty()) {
            int whiteMoveCount = 0;
            int blackMoveCount = 0;
            Piece wKing = game.getWhiteKing();
            Piece bKing = game.getBlackKing();
            for (Piece piece: game.getWhitePieces()) {
                if (piece.getTile() != null) {
                    if (!piece.moves().isEmpty()) {
                        whiteMoveCount++;                    }
                }
            }
            for (Piece piece: game.getBlackPieces()) {
                if (piece.getTile() != null) {
                    if (!piece.moves().isEmpty()) {
                        blackMoveCount++;
                    }
                }
            }
            if (whiteMoveCount == 0) {
                if (wKing.getTile().isAttacked()) return -10000;
                return 0;
            }
            else if (blackMoveCount == 0) {
                if (bKing.getTile().isAttacked()) return 10000;
                return 0;
            }
        }

        int count = 0;

        if (isMaximizer) {
            // if this is a maximizer node, the first child must be a larger number
            // also matechecks
            thisValue = Integer.MIN_VALUE;
            for (Move m : moveList) {
                game.move(m);
                count++;
                thisValue = quiescence(m, !isMaximizer, alpha, beta);
                game.takeMove();
                // if maximizer and child's value is higher than current value, update
                if (thisValue >= beta) {
                    if (count == 1) {
                        fhf++;
                    }
                    fh++;
                    //System.out.println("BETA CUTOFF MINIMIZER >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
                    return beta;
                }
                if (thisValue > alpha) {
                    move.setBestChild(m);
                    alpha = thisValue;
                }
                else m = null;
            }
            return alpha;
        }

        else {
            thisValue = Integer.MAX_VALUE;
            for (Move m: moveList) {
                game.move(m);
                count++;
                thisValue = quiescence(m, !isMaximizer, alpha, beta);
                game.takeMove();
                if (thisValue <= alpha) {
                    if (count == 1) {
                        fhf++;
                    }
                    fh++;
                    //System.out.println("**********************ALPHA CUTOFF MINIMIZER");
                    return alpha;
                }
                // if minimizer and child's value is lower than current value, update
                if (thisValue < beta) {
                    move.setBestChild(m);
                    beta = thisValue;
                }
                else m = null;
            }
            return beta;
        }
    }
    
   private void makeSmartMove() throws IOException {    
        int maxDepth;
        switch(level) {
            case 2: maxDepth = 4 ; break;
            case 3: {
                maxDepth = 5; 
            } break;
            case 4: maxDepth = 3; break;
            case 5: maxDepth = 4; break;
            default: maxDepth = 2; break;
        }

        setPieces();

        boolean isMaximizer;
        if (game.getSide() == 'w') isMaximizer = true;
        else isMaximizer = false;

        Move root = new Move();
        Move toMake = getMove(maxDepth, root, isMaximizer);

        game.move(toMake);
        Move currentMove = toMake;
        while(currentMove.getBestChild() != null) {
            currentMove = currentMove.getBestChild();
        }      
    }

    public int evaluateScore() {
        int score = 0;
        LinkedList<Piece> wPieces = game.getWhitePieces();
        LinkedList<Piece> bPieces = game.getBlackPieces();

        for (Piece piece: wPieces) {
            if (piece.getTile() != null) {
                score += piece.getValue();
                int rank = Character.getNumericValue(piece.getTile().getID().charAt(0));
                int file = Character.getNumericValue(piece.getTile().getID().charAt(1));
                char pieceType = piece.getType();

                if (pieceType == 'P') { score += pawnTable[rank][file]; }
                else if (pieceType == 'R') { score += rookTable[rank][file]; }
                else if (pieceType == 'K') {
                    if (getEndgame()) { score += kingEndTable[rank][file]; }
                    else { score += kingOpenTable[rank][file]; }
                }
                else if (pieceType == 'Q' ) { score += queenTable[rank][file]; }
                else if (pieceType == 'N') { score += knightTable[rank][file]; }
                else if (pieceType == 'B') { score += bishopTable[rank][file]; }
            }
        }

        for (Piece piece: bPieces) {
            if (piece.getTile() != null) {
                score -= piece.getValue();

                int rank = Character.getNumericValue(piece.getTile().getID().charAt(0));
                int file = Character.getNumericValue(piece.getTile().getID().charAt(1));
                char pieceType = piece.getType();

                if (pieceType == 'p') { score -= pawnTable[7 - rank][file]; }
                else if (pieceType == 'n') { score -= knightTable[7 - rank][file]; }
                else if (pieceType == 'b') { score -= bishopTable[7 - rank][file]; }
                else if (pieceType == 'r') { score -= rookTable[7 - rank][file]; }
                else if (pieceType == 'q') { score -= queenTable[7 - rank][file]; }
                else if (pieceType == 'k') {
                    if (getEndgame()) { score += kingEndTable[rank][file]; }
                    else { score += kingOpenTable[rank][file]; }
                }
            }
        }
        return score;
    }

    private void saveMove(int value) {
        String currFEN = game.createFEN();
        checkMap.put(currFEN, value);
    }

    public void printMoves() throws IOException {
        Set<String> keys = checkMap.keySet();
        MATEFILE.createNewFile(); 
        PrintWriter addMates = new PrintWriter(MATEFILE);
        
        for (String fen : keys) {
            int score = checkMap.get(fen);
            String toStore = fen + "," + score;
            addMates.println(toStore);
        }
        addMates.close();
    }

    private void setCheckMap() throws IOException {
        if (!MATEFILE.createNewFile()) {
            Scanner saveMates = new Scanner(MATEFILE);
            while (saveMates.hasNextLine()) {
                String currString = saveMates.nextLine();
                String[] keyVal = currString.split(",");
                Integer score = Integer.parseInt(keyVal[1]);
                checkMap.put(keyVal[0], score);
            }
            saveMates.close();
        }
    }

    // unit test of methods
    public static void main(String[] args) { /* see unit testing in Game.java for sample game */ }
}