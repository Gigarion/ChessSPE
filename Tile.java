public class Tile
{
    //instance variables for identiying the tiles,
    //establishes empty tile, and size of tile
    private String iD;
    private Piece piece = null;
    private static double r = 0.25;
   
    //constructor of tile
    public Tile(String id)
    {
        this.iD = id;
    }
    
    //return the id of the tile
    public String getID()
    {
        return this.iD;
    }

    //draw the tile based on its id
    public void draw(double x0, double y0)
    {
        StdDraw.setPenColor(StdDraw.BLACK);
        StdDraw.square(x0, y0, r);
        
        int i = Character.getNumericValue(iD.charAt(0));
        int j = Character.getNumericValue(iD.charAt(1));
        
        if((i+j) % 2 == 0)
        {
            StdDraw.setPenColor(StdDraw.BLACK);
            StdDraw.filledSquare(x0, y0, r);
        }
        else
        {
            StdDraw.setPenColor(StdDraw.WHITE);
            StdDraw.filledSquare(x0, y0, r);
        }
    }
    
    //place a piece on the tile
    public void place(Piece p)
    {
        piece = p;
    }
    
    //identifies the piece on the tile
    public Piece getPiece(int x, int y)
    {
        return piece;
    }
}