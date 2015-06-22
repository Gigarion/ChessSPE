import java.util.LinkedList;
import java.util.Stack;

public class AI
{
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
    
    // unit test of methods
    public static void main(String[] args) { /* see unit testing in Game.java for sample game */ }
}