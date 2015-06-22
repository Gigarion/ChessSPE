import java.util.LinkedList;
import java.util.Stack;

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

    // level of difficulty of AI
    private int level;

    // current game
    private Game game;

    // list of pieces for the side that moves
    private LinkedList<Piece> pieces;
    
    public AI(Game game, int level)
    {
        this.game = game;
        this.level = level;
        if (game.getSide() == 'w') { pieces = game.getWhitePieces(); }
        else { pieces = game.getBlackPieces(); }
    }

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
    public void makeAIMove() {
        switch(level) {
            case 2: makeLevelOneMove();
            default: makeRandomAIMove();
        }
    }

    //chooses random tile from random pieces in the linkedlist
    public void makeRandomAIMove()
    {
        if (game.mateCheck()) return;

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
        game.aiMove(targetPiece, targetTile);

        // flips the board and side to move if autoflip is on
        if (game.getAutoFlip()) { game.flip(); }

        // simple changes side to move if autoflip is off
        else {
            if (game.getSide() == 'w') { game.setSide('b'); }
            else game.setSide('w');
        }
    }
    
    public void makeLevelOneMove() {

    }

    // unit test of methods
    public static void main(String[] args) { /* see unit testing in Game.java for sample game */ }
}