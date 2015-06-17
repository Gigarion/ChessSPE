import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class Tile extends JPanel implements MouseListener
{
    //instance variables for identiying the tiles,
    //establishes empty tile, and size of tile
    private String iD;
    private Game game;
    private Piece piece;
    private JLabel label;
    private static final double r = 0.5;
   
    //constructor of tile
    public Tile(String id)
    {
        this.iD = id;
    }

    private void darken() {
        game.setHighlight(false);
        game.endHold();
        game.draw();
    }

    private void highlightMoves() {
        for (Tile t : piece.moves()) {
            System.out.println(t.getID());
        }
    }

    public void mouseClicked(MouseEvent e) {
        System.out.println("Mouse Clicked at " + e.getX() + "," + e.getY() + " " + iD);
        if (!game.getHighlight()) {
            if (piece != null) {
                System.out.println("Piece has moves: " + !piece.moves().empty());
                if (piece.getColor() == game.getSide() && !piece.moves().empty()) {
                    game.setHighlight(true);
                    highlightMoves();
                    game.hold(piece);
                }
                else if (piece.moves().empty()) {
                    darken();
                }
            }
        }
        else {
            for (Tile t : game.getHold().moves()) {
                if (t.getID().equals(iD)) {
                    game.move(game.getHold(), this);
                    darken();
                }
            }
            darken();
        }
    }

    public void mouseEntered(MouseEvent e) {
        //System.out.println("Mouse Entered at " + e.getX() + "," + e.getY());
    }

    public void mouseExited(MouseEvent e) {
        //System.out.println("Mouse Exited at " + e.getX() + "," + e.getY());
    }

    public void mousePressed(MouseEvent e) {
        //System.out.println("Mouse Pressed at " + e.getX() + "," + e.getY());
    }
    
    public void mouseReleased(MouseEvent e) {
        System.out.println("Mouse Released at " + e.getX() + "," + e.getY() + " " + iD);
    }

    public void setGame(Game g) {
        this.game = g;
    }
    
    //return the id of the tile
    public String getID()
    {
        return this.iD;
    }

    public boolean occupied() {
        if (piece == null) {
            return false;
        }
        else return true;
    }

    //draw the tile based on its id
    public JLabel set()
    {
        if (piece != null) {
            ImageIcon icon1 = createImageIcon(getImage(), "piece");
            label = new JLabel(icon1);
        }
        else {
            label = new JLabel();
        }

        label.addMouseListener(this);

        int rank = Character.getNumericValue(iD.charAt(0));
        int file = Character.getNumericValue(iD.charAt(1));
        Color color;

        if ((rank + file) % 2 == 0) {
            color = Color.BLUE;
        }
        else {
            color = Color.WHITE;
        }

        label.setBackground(color);
        label.setOpaque(true);

        // set the square size
        Dimension dimension = new Dimension(60, 60);
        label.setPreferredSize(dimension);
        //frame.add(label);
        return label;
    }

    public void update() {
        if (piece != null) {
            ImageIcon icon1 = createImageIcon(getImage(), "piece");
            label = new JLabel(icon1);
        }
        else {
            label = new JLabel();
        }

        label.addMouseListener(this);

        int rank = Character.getNumericValue(iD.charAt(0));
        int file = Character.getNumericValue(iD.charAt(1));
        Color color;

        if ((rank + file) % 2 == 0) {
            color = Color.BLUE;
        }
        else {
            color = Color.WHITE;
        }

        label.setBackground(color);
        label.setOpaque(true);

        // set the square size
        Dimension dimension = new Dimension(60, 60);
        label.setPreferredSize(dimension);
    }

    protected ImageIcon createImageIcon(String path, String description) {
        java.net.URL imgURL = getClass().getResource(path);
        if (imgURL != null) {
            return new ImageIcon(imgURL, description);
        }
        else {
            return null;
        }
    }



    //draws the different chess pieces at a tile
    public String getImage()
    {
        //if there is no piece, it will not draw
        if (piece == null) return null;
        
        //if there are pieces with capital letters, it
        //will draw the white pieces for bishops, queens, kings,
        //rooks, pawns, and knights
        //if there are pieces with capital letters, it
        //will draw the white pieces for bishops, queens, kings,
        //rooks, pawns, and knights
        else if (piece.getType() == 'B')
        {
            return "images/wB.png";
        }
        else if (piece.getType() == 'K')
        {
            return "images/wK.png";
        }
        else if (piece.getType() == 'N')
        {
            return "images/wN.png";
        }
        else if (piece.getType() == 'P')
        {
            return "images/wP.png";
        }
        else if (piece.getType() == 'Q')
        {
            return "images/wQ.png";
        }
        else if (piece.getType() == 'R')
        {
            return "images/wR.png";
        }
        
        //if there are piece.getType()s with lowercase letters, it
        //will draw the black pieces for bishops, queens, kings,
        //rooks, pawns, and knights
        else if (piece.getType() == 'b')
        {
            return "images/bB.png";
        }
        else if (piece.getType() == 'k')
        {
            return "images/bK.png";
        }
        else if (piece.getType() == 'n')
        {
            return "images/bN.png";
        }
        else if (piece.getType() == 'p')
        {
            return "images/bP.png";
        }
        else if (piece.getType() == 'q')
        {
            return "images/bQ.png";
        }
        else if (piece.getType() == 'r')
        {
            return "images/bR.png";
        } else return null;
    }
    
    //place a piece on the tile
    public void place(Piece p) {
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

    public static void main(String[] args) {
    }
}