import java.util.LinkedList;
import java.util.Stack;

public class AI
{
    //level of difficulty of AI
    private int level;
    //list of pieces for the side that moves
    private LinkedList<Piece> pieces;
    //current game
    private Game game;
        
    //initializes AI with current game and given level
    public AI(Game game, int level)
    {
        this.game = game;
        this.level = level;
        if (game.getSide == 'w')
        {
            pieces = game.getWhitePieces();
        }
        else
        {
            pieces = game.getBlackPieces();
        }
    }
    
    //chooses random tile from random pieces in the linkedlist
    public void makeRandomMove()
    {
        Piece targetPiece;
        Tile targetTile;
        //keeps track of pieces with legal moves
        Stack<Piece> stack = new Stack<Piece>();
        
        int size = 0;
        
        //all pieces with valid moves onto the stack
        for (Piece currentPiece: pieces)
        {
            if (!currentPiece.moves().isEmpty())
            {
                stack.push(currentPiece);
                size++;
            }
        }
        
        
        int randPiece = (int) (Math.random()*size);
        
        //choose random piece
        for (int i = 0; i < randPiece; i++)
        {
            targetPiece = stack.pop();
        }
        
        //list of tiles for the random piece
        Stack<Tile> tiles = targetPiece.moves();
        
        int count = 0;
        
        
        for (Tile currentTile: tiles)
        {
           count++;
        }
        
        int randTile = (int) (Math.random() * count);
        
        //choose random tile
        for (int i = 0; i < count; i++)
        {
            targetTile = tiles.pop();
        }

        //make move with random piece and random tile
        game.move(targetPiece, targetTile);
    }
    
    public static void main(String[] args)
    {
        Game game = new Game();
        AI ai = new AI(game, 1);
        ai.makeRandomMove();
    }
}