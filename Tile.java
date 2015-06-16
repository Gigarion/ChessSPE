public class Tile
{
    //instance variables for identiying the tiles,
    //establishes empty tile, and size of tile
    private String iD;
    private Game game;
    private Piece piece;
    private static double r = 0.5;
   
    //constructor of tile
    public Tile(String id)
    {
        this.iD = id;
    }

    public void setGame(Game g) {
        this.game = g;
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
        
        //int i is the first integer of iD and int j is
        //the second integer of the iD
        int i = Character.getNumericValue(iD.charAt(0));
        int j = Character.getNumericValue(iD.charAt(1));
        
        //determine the color of each tile. if sum of the
        //i and j is even (black) or odd (white)
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
        System.out.println(iD);
        StdDraw.show(5);
        drawPiece(piece, x0, y0);
        System.out.println(this.piece.getColor());
    }

    //draws the different chess pieces at a tile
    public void drawPiece(Piece p, double x0, double y0)
    {
        //if there is no piece, it will not draw
        if (piece == null) return;
        
        //if there are pieces with capital letters, it
        //will draw the white pieces for bishops, queens, kings,
        //rooks, pawns, and knights
        else if (piece.getType() == 'B')
        {
            StdDraw.picture(x0, y0, "wB.png");
        }
        else if (piece.getType() == 'K')
        {
            StdDraw.picture(x0, y0, "wK.png");
        }
        else if (piece.getType() == 'N')
        {
            StdDraw.picture(x0, y0, "wN.png");
        }
        else if (piece.getType() == 'P')
        {
            StdDraw.picture(x0, y0, "wP.png");
        }
        else if (piece.getType() == 'Q')
        {
            StdDraw.picture(x0, y0, "wQ.png");
        }
        else if (piece.getType() == 'R')
        {
            StdDraw.picture(x0, y0, "wR.png");
        }
        
        //if there are piece.getType()s with lowercase letters, it
        //will draw the black pieces for bishops, queens, kings,
        //rooks, pawns, and knights
        else if (piece.getType() == 'b')
        {
            StdDraw.picture(x0, y0, "bB.png");
        }
        else if (piece.getType() == 'k')
        {
            StdDraw.picture(x0, y0, "bK.png");
        }
        else if (piece.getType() == 'n')
        {
            StdDraw.picture(x0, y0, "bN.png");
        }
        else if (piece.getType() == 'p')
        {
            StdDraw.picture(x0, y0, "bP.png");
        }
        else if (piece.getType() == 'q')
        {
            StdDraw.picture(x0, y0, "bQ.png");
        }
        else if (piece.getType() == 'r')
        {
            StdDraw.picture(x0, y0, "bR.png");
        }
    }
    
    //place a piece on the tile
    public void place(Piece p)
    {
        this.piece = p;
    }

    // removes a piece from a tile
    public void empty() {
        piece = null;
    }

    // determines if a piece on this tile is in danger
    public boolean isAttacked() {
        int rank = Character.getNumericValue(iD.charAt(0));
        int file = Character.getNumericValue(iD.charAt(1));
        int rTemp = rank;
        int fTemp = file;
        boolean stopCatch = false;
        if (piece != null) {
            for (int i = 1; i < 8; i++) {
                fTemp = file + i;
                if (!stopCatch && game.getTile(rank, fTemp) != null) {
                    if (piece.getColor() == 'w') {
                        if (game.getTile(rank, fTemp).getPiece() != null) {
                            char type = game.getTile(rank, fTemp).getPiece().getType();
                            if (type == 'r' || type == 'q' || (type == 'k' && i ==1))
                                return true;
                            stopCatch = true;
                        }
                    }
                    else {
                        if (game.getTile(rank, fTemp).getPiece() != null) {
                            char type = game.getTile(rank, fTemp).getPiece().getType();
                            if (type == 'R' || type == 'Q' || (type == 'K' && i ==1))
                                return true;
                            stopCatch = true;
                        }
                    }
                }   
            }

            stopCatch = false;
            for (int i = 1; i < 8; i++) {
                fTemp = file - i;
                if (!stopCatch && game.getTile(rank, fTemp) != null) {
                    if (piece.getColor() == 'w') {
                        if (game.getTile(rank, fTemp).getPiece() != null) {
                            char type = game.getTile(rank, fTemp).getPiece().getType();
                            if (type == 'r' || type == 'q' || (type == 'k' && i ==1))
                                return true;
                            stopCatch = true;
                        }
                    }
                    else {
                        if (game.getTile(rank, fTemp).getPiece() != null) {
                            char type = game.getTile(rank, fTemp).getPiece().getType();
                            if (type == 'R' || type == 'Q' || (type == 'K' && i ==1))
                                return true;
                            stopCatch = true;
                        }
                    }
                }   
            }

            stopCatch = false;
            for (int i = 1; i < 8; i++) {
                rTemp = rank + i;
                if (!stopCatch && game.getTile(rank, fTemp) != null) {
                    if (piece.getColor() == 'w') {
                        if (game.getTile(rTemp, file).getPiece() != null) {
                            char type = game.getTile(rank, fTemp).getPiece().getType();
                            if (type == 'r' || type == 'q' || (type == 'k' && i ==1))
                                return true;
                            stopCatch = true;
                        }
                    }
                    else {
                        if (game.getTile(rTemp, file).getPiece() != null) {
                            char type = game.getTile(rank, fTemp).getPiece().getType();
                            if (type == 'R' || type == 'Q' || (type == 'K' && i ==1))
                                return true;
                            stopCatch = true;
                        }
                    }
                }   
            }

            stopCatch = false;
            for (int i = 1; i < 8; i++) {
                rTemp = rank - i;
                if (!stopCatch && game.getTile(rank, fTemp) != null) {
                    if (piece.getColor() == 'w') {
                        if (game.getTile(rTemp, file).getPiece() != null) {
                            char type = game.getTile(rank, fTemp).getPiece().getType();
                            if (type == 'r' || type == 'q' || (type == 'k' && i == 1))
                                return true;
                            stopCatch = true;
                        }
                    }
                    else {
                        if (game.getTile(rTemp, file).getPiece() != null) {
                            char type = game.getTile(rank, fTemp).getPiece().getType();
                            if (type == 'R' || type == 'Q' || (type == 'K' && i == 1))
                                return true;
                            stopCatch = true;
                        }
                    }
                }   
            }

            stopCatch = false;
            for (int i = 0; i < 8; i++) {
                rTemp = rank + i;
                fTemp = file + i;
                if (!stopCatch && game.getTile(rank, fTemp) != null) {
                    if (piece.getColor() == 'w') {
                        if (game.getTile(rTemp, fTemp).getPiece() != null) {
                            char type = game.getTile(rTemp, fTemp).getPiece().getType();
                            if (type == 'b' || type == 'q' || (type == 'k' && i == 1))
                                return true;
                            stopCatch = true;
                        }
                    }
                    else {
                        if (game.getTile(rTemp, fTemp).getPiece() != null) {
                            char type = game.getTile(rTemp, fTemp).getPiece().getType();
                            if (type == 'B' || type == 'Q' || (type == 'K' && i == 1))
                                return true;
                            stopCatch = true;
                        }
                    }
                }
            }

            stopCatch = false;
            for (int i = 0; i < 8; i++) {
                rTemp = rank - i;
                fTemp = file + i;
                if (!stopCatch && game.getTile(rank, fTemp) != null) {
                    if (piece.getColor() == 'w') {
                        if (game.getTile(rTemp, fTemp).getPiece() != null) {
                            char type = game.getTile(rTemp, fTemp).getPiece().getType();
                            if (type == 'b' || type == 'q' || (type == 'k' && i == 1))
                                return true;
                            stopCatch = true;
                        }
                    }
                    else {
                        if (game.getTile(rTemp, fTemp).getPiece() != null) {
                            char type = game.getTile(rTemp, fTemp).getPiece().getType();
                            if (type == 'B' || type == 'Q' || (type == 'K' && i == 1))
                                return true;
                            stopCatch = true;
                        }
                    }
                }
            }

            stopCatch = false;
            for (int i = 0; i < 8; i++) {
                rTemp = rank - i;
                fTemp = file - i;
                if (!stopCatch && game.getTile(rank, fTemp) != null) {
                    if (piece.getColor() == 'w') {
                        if (game.getTile(rTemp, fTemp).getPiece() != null) {
                            char type = game.getTile(rTemp, fTemp).getPiece().getType();
                            if (type == 'b' || type == 'q' || (type == 'k' && i == 1))
                                return true;
                            stopCatch = true;
                        }
                    }
                    else {
                        if (game.getTile(rTemp, fTemp).getPiece() != null) {
                            char type = game.getTile(rTemp, fTemp).getPiece().getType();
                            if (type == 'B' || type == 'Q' || (type == 'K' && i == 1))
                                return true;
                            stopCatch = true;
                        }
                    }
                }
            }

            stopCatch = false;
            for (int i = 0; i < 8; i++) {
                rTemp = rank + i;
                fTemp = file - i;
                if (!stopCatch && game.getTile(rank, fTemp) != null) {
                    if (piece.getColor() == 'w') {
                        if (game.getTile(rTemp, fTemp).getPiece() != null) {
                            char type = game.getTile(rTemp, fTemp).getPiece().getType();
                            if (type == 'b' || type == 'q' || (type == 'k' && i == 1))
                                return true;
                            stopCatch = true;
                        }
                    }
                    else {
                        if (game.getTile(rTemp, fTemp).getPiece() != null) {
                            char type = game.getTile(rTemp, fTemp).getPiece().getType();
                            if (type == 'B' || type == 'Q' || (type == 'K' && i == 1))
                                return true;
                            stopCatch = true;
                        }
                    }
                }
            }

            // moving clockwise from the bottom-left square...
            Tile bottomleft  = game.getTile(rank-2, file-1);
            Tile leftbottom  = game.getTile(rank-1, file-2);
            Tile lefttop     = game.getTile(rank+1, file-2);
            Tile topleft     = game.getTile(rank+2, file-1);
            Tile topright    = game.getTile(rank+2, file+1);
            Tile righttop    = game.getTile(rank+1, file+2);
            Tile rightbottom = game.getTile(rank-1, file+2);
            Tile bottomright = game.getTile(rank-2, file+1);

            if (piece.getType() == 'w') {
                if ((bottomleft != null) && ((bottomleft.getPiece().getType() == 'n'))) {
                    return true;
                }

                if ((leftbottom != null) && ((leftbottom.getPiece().getType() == 'n'))) {
                    return true;
                }

                if ((lefttop != null) && ((lefttop.getPiece().getType() == 'n'))) {
                    return true;
                }

                if ((topleft != null) && ((topleft.getPiece().getType() == 'n'))) {
                    return true;
                }

                if ((topright != null) && ((topright.getPiece().getType() == 'n'))) {
                    return true;
                }

                if ((righttop != null) && ((righttop.getPiece().getType() == 'n'))) {
                    return true;
                }

                if ((rightbottom != null) && ((rightbottom.getPiece().getType() == 'n'))) {
                    return true;
                }

                if ((bottomright != null) && ((bottomright.getPiece().getType() == 'n'))) {
                    return true;
                }
            }

            else if (piece.getType() == 'b') {
                if ((bottomleft != null) && ((bottomleft.getPiece().getType() == 'N'))) {
                    return true;
                }

                if ((leftbottom != null) && ((leftbottom.getPiece().getType() == 'N'))) {
                    return true;
                }

                if ((lefttop != null) && ((lefttop.getPiece().getType() == 'N'))) {
                    return true;
                }

                if ((topleft != null) && ((topleft.getPiece().getType() == 'N'))) {
                    return true;
                }

                if ((topright != null) && ((topright.getPiece().getType() == 'N'))) {
                    return true;
                }

                if ((righttop != null) && ((righttop.getPiece().getType() == 'N'))) {
                    return true;
                }

                if ((rightbottom != null) && ((rightbottom.getPiece().getType() == 'N'))) {
                    return true;
                }

                if ((bottomright != null) && ((bottomright.getPiece().getType() == 'N'))) {
                    return true;
                }
            }

            Tile tr = game.getTile(rank + 1, file + 1);
            Tile tl = game.getTile(rank + 1, file - 1);
            Tile bl = game.getTile(rank - 1, file - 1);
            Tile br = game.getTile(rank - 1, file + 1);

            if (piece.getType() == 'w' && (tr.getPiece().getType() == 'p' || tr.getPiece().getType() == 'p'))
                return true;

            else if (piece.getType() == 'b' && (tr.getPiece().getType() == 'P' || tr.getPiece().getType() == 'P'))
                return true;
        }
        return false;
    }

    public boolean isAttacked(Piece p) {
        int rank = Character.getNumericValue(iD.charAt(0));
        int file = Character.getNumericValue(iD.charAt(1));
        int rTemp = rank;
        int fTemp = file;
        boolean stopCatch = false;
        if (piece != null) {
            for (int i = 1; i < 8; i++) {
                fTemp = file + i;
                if (!stopCatch && game.getTile(rank, fTemp) != null) {
                    if (p.getColor() == 'w') {
                        if (game.getTile(rank, fTemp).getPiece() != null) {
                            char type = game.getTile(rank, fTemp).getPiece().getType();
                            if (type == 'r' || type == 'q' || (type == 'k' && i ==1))
                                return true;
                            stopCatch = true;
                        }
                    }
                    else {
                        if (game.getTile(rank, fTemp).getPiece() != null) {
                            char type = game.getTile(rank, fTemp).getPiece().getType();
                            if (type == 'R' || type == 'Q' || (type == 'K' && i ==1))
                                return true;
                            stopCatch = true;
                        }
                    }
                }   
            }

            stopCatch = false;
            for (int i = 1; i < 8; i++) {
                fTemp = file - i;
                if (!stopCatch && game.getTile(rank, fTemp) != null) {
                    if (p.getColor() == 'w') {
                        if (game.getTile(rank, fTemp).getPiece() != null) {
                            char type = game.getTile(rank, fTemp).getPiece().getType();
                            if (type == 'r' || type == 'q' || (type == 'k' && i ==1))
                                return true;
                            stopCatch = true;
                        }
                    }
                    else {
                        if (game.getTile(rank, fTemp).getPiece() != null) {
                            char type = game.getTile(rank, fTemp).getPiece().getType();
                            if (type == 'R' || type == 'Q' || (type == 'K' && i ==1))
                                return true;
                            stopCatch = true;
                        }
                    }
                }   
            }

            stopCatch = false;
            for (int i = 1; i < 8; i++) {
                rTemp = rank + i;
                if (!stopCatch && game.getTile(rank, fTemp) != null) {
                    if (p.getColor() == 'w') {
                        if (game.getTile(rTemp, file).getPiece() != null) {
                            char type = game.getTile(rank, fTemp).getPiece().getType();
                            if (type == 'r' || type == 'q' || (type == 'k' && i ==1))
                                return true;
                            stopCatch = true;
                        }
                    }
                    else {
                        if (game.getTile(rTemp, file).getPiece() != null) {
                            char type = game.getTile(rank, fTemp).getPiece().getType();
                            if (type == 'R' || type == 'Q' || (type == 'K' && i ==1))
                                return true;
                            stopCatch = true;
                        }
                    }
                }   
            }

            stopCatch = false;
            for (int i = 1; i < 8; i++) {
                rTemp = rank - i;
                if (!stopCatch && game.getTile(rank, fTemp) != null) {
                    if (p.getColor() == 'w') {
                        if (game.getTile(rTemp, file).getPiece() != null) {
                            char type = game.getTile(rank, fTemp).getPiece().getType();
                            if (type == 'r' || type == 'q' || (type == 'k' && i == 1))
                                return true;
                            stopCatch = true;
                        }
                    }
                    else {
                        if (game.getTile(rTemp, file).getPiece() != null) {
                            char type = game.getTile(rank, fTemp).getPiece().getType();
                            if (type == 'R' || type == 'Q' || (type == 'K' && i == 1))
                                return true;
                            stopCatch = true;
                        }
                    }
                }   
            }

            stopCatch = false;
            for (int i = 0; i < 8; i++) {
                rTemp = rank + i;
                fTemp = file + i;
                if (!stopCatch && game.getTile(rank, fTemp) != null) {
                    if (p.getColor() == 'w') {
                        if (game.getTile(rTemp, fTemp).getPiece() != null) {
                            char type = game.getTile(rTemp, fTemp).getPiece().getType();
                            if (type == 'b' || type == 'q' || (type == 'k' && i == 1))
                                return true;
                            stopCatch = true;
                        }
                    }
                    else {
                        if (game.getTile(rTemp, fTemp).getPiece() != null) {
                            char type = game.getTile(rTemp, fTemp).getPiece().getType();
                            if (type == 'B' || type == 'Q' || (type == 'K' && i == 1))
                                return true;
                            stopCatch = true;
                        }
                    }
                }
            }

            stopCatch = false;
            for (int i = 0; i < 8; i++) {
                rTemp = rank - i;
                fTemp = file + i;
                if (!stopCatch && game.getTile(rank, fTemp) != null) {
                    if (p.getColor() == 'w') {
                        if (game.getTile(rTemp, fTemp).getPiece() != null) {
                            char type = game.getTile(rTemp, fTemp).getPiece().getType();
                            if (type == 'b' || type == 'q' || (type == 'k' && i == 1))
                                return true;
                            stopCatch = true;
                        }
                    }
                    else {
                        if (game.getTile(rTemp, fTemp).getPiece() != null) {
                            char type = game.getTile(rTemp, fTemp).getPiece().getType();
                            if (type == 'B' || type == 'Q' || (type == 'K' && i == 1))
                                return true;
                            stopCatch = true;
                        }
                    }
                }
            }

            stopCatch = false;
            for (int i = 0; i < 8; i++) {
                rTemp = rank - i;
                fTemp = file - i;
                if (!stopCatch && game.getTile(rank, fTemp) != null) {
                    if (p.getColor() == 'w') {
                        if (game.getTile(rTemp, fTemp).getPiece() != null) {
                            char type = game.getTile(rTemp, fTemp).getPiece().getType();
                            if (type == 'b' || type == 'q' || (type == 'k' && i == 1))
                                return true;
                            stopCatch = true;
                        }
                    }
                    else {
                        if (game.getTile(rTemp, fTemp).getPiece() != null) {
                            char type = game.getTile(rTemp, fTemp).getPiece().getType();
                            if (type == 'B' || type == 'Q' || (type == 'K' && i == 1))
                                return true;
                            stopCatch = true;
                        }
                    }
                }
            }

            stopCatch = false;
            for (int i = 0; i < 8; i++) {
                rTemp = rank + i;
                fTemp = file - i;
                if (!stopCatch && game.getTile(rank, fTemp) != null) {
                    if (p.getColor() == 'w') {
                        if (game.getTile(rTemp, fTemp).getPiece() != null) {
                            char type = game.getTile(rTemp, fTemp).getPiece().getType();
                            if (type == 'b' || type == 'q' || (type == 'k' && i == 1))
                                return true;
                            stopCatch = true;
                        }
                    }
                    else {
                        if (game.getTile(rTemp, fTemp).getPiece() != null) {
                            char type = game.getTile(rTemp, fTemp).getPiece().getType();
                            if (type == 'B' || type == 'Q' || (type == 'K' && i == 1))
                                return true;
                            stopCatch = true;
                        }
                    }
                }
            }

            // moving clockwise from the bottom-left square...
            Tile bottomleft  = game.getTile(rank-2, file-1);
            Tile leftbottom  = game.getTile(rank-1, file-2);
            Tile lefttop     = game.getTile(rank+1, file-2);
            Tile topleft     = game.getTile(rank+2, file-1);
            Tile topright    = game.getTile(rank+2, file+1);
            Tile righttop    = game.getTile(rank+1, file+2);
            Tile rightbottom = game.getTile(rank-1, file+2);
            Tile bottomright = game.getTile(rank-2, file+1);

            if (p.getType() == 'w') {
                if ((bottomleft != null) && ((bottomleft.getPiece().getType() == 'n'))) {
                    return true;
                }

                if ((leftbottom != null) && ((leftbottom.getPiece().getType() == 'n'))) {
                    return true;
                }

                if ((lefttop != null) && ((lefttop.getPiece().getType() == 'n'))) {
                    return true;
                }

                if ((topleft != null) && ((topleft.getPiece().getType() == 'n'))) {
                    return true;
                }

                if ((topright != null) && ((topright.getPiece().getType() == 'n'))) {
                    return true;
                }

                if ((righttop != null) && ((righttop.getPiece().getType() == 'n'))) {
                    return true;
                }

                if ((rightbottom != null) && ((rightbottom.getPiece().getType() == 'n'))) {
                    return true;
                }

                if ((bottomright != null) && ((bottomright.getPiece().getType() == 'n'))) {
                    return true;
                }
            }

            else if (p.getType() == 'b') {
                if ((bottomleft != null) && ((bottomleft.getPiece().getType() == 'N'))) {
                    return true;
                }

                if ((leftbottom != null) && ((leftbottom.getPiece().getType() == 'N'))) {
                    return true;
                }

                if ((lefttop != null) && ((lefttop.getPiece().getType() == 'N'))) {
                    return true;
                }

                if ((topleft != null) && ((topleft.getPiece().getType() == 'N'))) {
                    return true;
                }

                if ((topright != null) && ((topright.getPiece().getType() == 'N'))) {
                    return true;
                }

                if ((righttop != null) && ((righttop.getPiece().getType() == 'N'))) {
                    return true;
                }

                if ((rightbottom != null) && ((rightbottom.getPiece().getType() == 'N'))) {
                    return true;
                }

                if ((bottomright != null) && ((bottomright.getPiece().getType() == 'N'))) {
                    return true;
                }
            }

            Tile tr = game.getTile(rank + 1, file + 1);
            Tile tl = game.getTile(rank + 1, file - 1);
            Tile bl = game.getTile(rank - 1, file - 1);
            Tile br = game.getTile(rank - 1, file + 1);

            if (p.getType() == 'w' && (tr.getPiece().getType() == 'p' || tr.getPiece().getType() == 'p'))
                return true;

            else if (p.getType() == 'b' && (tr.getPiece().getType() == 'P' || tr.getPiece().getType() == 'P'))
                return true;
        }
        return false;
    }
    
    //identifies the piece on the tile
    public Piece getPiece()
    {
        return piece;
    }
}