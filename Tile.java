import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.util.Stack;
import java.io.*;

public class Tile extends JPanel implements MouseListener
{
    // two digit String ID corresponding to its rank and file
    // starting at zero i.e. tile 34 represents the square e4
    private String iD;

    // the piece on the given tile, if it exists
    private Piece piece;

    // the current game
    private Game game;

    // used to give each tile a MouseListener for the GUI
    private JLabel label;
   
    public Tile(String id) {  this.iD = id; }

    // returns the tile's id
    public String getID() { return this.iD; }

    // sets the tile's id
    public void setID(String id) { this.iD = id; }

    // returns the tile's piece, if it exists
    public Piece getPiece() { return piece; }

    // places the given piece on the tile
    public void place(Piece piece) { this.piece = piece; }

    // removes a piece from a tile
    public void empty() { piece = null; }

    // initializes the tile in the given game
    public void setGame(Game game) { this.game = game; }

    // draws the board with the given highlighted squares
    private void darken()
    {
        game.setHighlight(false);
        game.endHold();
        game.draw();
    }

    // when clicked, a tile will highlight its pieces' valid moves
    public void mouseClicked(MouseEvent e) {
    try {
            // game only highlights one piece's moves at a time
            if (!game.getHighlight()) {
                if (piece != null) {
                    Stack<Tile> moveHold = piece.moves();
                    if (piece.getColor() == game.getSide() && !moveHold.empty()) {
                        game.setHighlight(true);
                        game.draw(moveHold);
                        game.hold(piece);
                    }
                    else if (moveHold.empty()) {
                        darken();
                    }
                }
            }
            else {
                for (Tile t : game.getHold().moves()) {
                    if (t.getID().equals(iD)) {
                        game.move(game.getHold(), this);
                        darken();

                        // Ai makes a move, if it exists
                        if (game.getAI() != null) { game.getAI().makeAIMove(); }
                        else {
                            game.flip();
                            game.draw();
                        }
                    }
                }
                darken();
            }
        } catch (IOException i) {}
    }

    // highlighted squares display a mix of their current color and yellow
    public Color multiply(Color color1, Color color2)
    {
        float[] color1Components = color1.getRGBComponents(null);   
        float[] color2Components = color2.getRGBColorComponents(null);
        float[] newComponents    = new float[3]; 

        for(int i = 0; i < 3; i++) {
            newComponents[i] = color1Components[i] * color2Components[i];
        }  

        Color newcolor = new Color(newComponents[0], newComponents[1], newComponents[2], color1Components[3]);

        return newcolor;
    }

    // supports placing an image onto a tile's label
    protected ImageIcon createImageIcon(String path, String description)
    {
        java.net.URL imgURL = getClass().getResource(path);
        if (imgURL != null) { return new ImageIcon(imgURL, description); }
        else { return null; }
    }

    // makes a label for the tile to be placed on the gameboard GUI
    public JLabel set(boolean highlight)
    {
        // sets the label's image based on the tile's piece
        if (piece != null)
        {
            ImageIcon icon1 = createImageIcon(getImage(), "piece");
            label = new JLabel(icon1);
        }
        else { label = new JLabel(); }

        // adds a MouseListener to support GUI
        label.addMouseListener(this);

        // tile's rank and file determine its color
        int rank = Character.getNumericValue(iD.charAt(0));
        int file = Character.getNumericValue(iD.charAt(1));
        
        Color dark  = new Color(95,188,211);
        Color light = new Color(175,221,233);

        // color for dark squares
        if ((rank + file) % 2 == 0)
        {
            if (highlight)
            {
                Color highlighted = multiply(dark, Color.YELLOW);
                label.setBackground(highlighted);
            }
            else { label.setBackground(dark); }
        }

        // color for light squares
        else
        {
            if (highlight)
            {
                Color highlighted = multiply(light, Color.YELLOW);
                label.setBackground(highlighted);
            }
            else { label.setBackground(light); }
        }

        // sets the size for each square
        label.setPreferredSize(new Dimension(60, 60));

        label.setOpaque(true);

        return label;
    }

    // resets tile image in GUI
    public void update() {

        if (piece != null)
        {
            ImageIcon icon1 = createImageIcon(getImage(), "piece");
            label = new JLabel(icon1);
        }
        else { label = new JLabel(); }

        label.addMouseListener(this);

        int rank = Character.getNumericValue(iD.charAt(0));
        int file = Character.getNumericValue(iD.charAt(1));

        Color dark        = new Color(95,188,211);
        Color light       = new Color(175,221,233);

        if ((rank + file) % 2 == 0) { label.setBackground(dark); }
        else { label.setBackground(light); }

        label.setPreferredSize(new Dimension(60, 60));
        label.setOpaque(true);
    }

    // returns the png image (taken from Wikipedia) for a tile's piece
    public String getImage()
    {
        if (piece == null) return null;

        // png files list piece color, then type i.e. wB = white bishop
        else if (piece.getType() == 'P') { return "images/wP.png"; }
        else if (piece.getType() == 'N') { return "images/wN.png"; }
        else if (piece.getType() == 'B') { return "images/wB.png"; }
        else if (piece.getType() == 'R') { return "images/wR.png"; }
        else if (piece.getType() == 'Q') { return "images/wQ.png"; }
        else if (piece.getType() == 'K') { return "images/wK.png"; }

        else if (piece.getType() == 'p') { return "images/bP.png"; }
        else if (piece.getType() == 'n') { return "images/bN.png"; }
        else if (piece.getType() == 'b') { return "images/bB.png"; }
        else if (piece.getType() == 'r') { return "images/bR.png"; }
        else if (piece.getType() == 'q') { return "images/bQ.png"; }
        else if (piece.getType() == 'k') { return "images/bK.png"; }

        else return null;
    }

    // determines if the tile is attacked by an opponent's piece
    public boolean isAttacked() {
        int rank = Character.getNumericValue(iD.charAt(0));
        int file = Character.getNumericValue(iD.charAt(1));
        int rTemp = rank;
        int fTemp = file;
        boolean stopCatch = false;
        if (piece != null) {
            for (int i = 1; fTemp < 8; i++) {
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

            rTemp = rank;
            fTemp = file;

            stopCatch = false;
            for (int i = 1; fTemp >= 0; i++) {
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

            rTemp = rank;
            fTemp = file;

            stopCatch = false;
            for (int i = 1; rTemp < 8; i++) {
                rTemp = rank + i;
                if (!stopCatch && game.getTile(rTemp, file) != null) {
                    if (piece.getColor() == 'w') {
                        if (game.getTile(rTemp, file).getPiece() != null) {
                            char type = game.getTile(rTemp, file).getPiece().getType();
                            if (type == 'r' || type == 'q' || (type == 'k' && i == 1))
                                return true;
                            stopCatch = true;
                        }
                    }
                    else {
                        if (game.getTile(rTemp, file).getPiece() != null) {
                            char type = game.getTile(rTemp, file).getPiece().getType();
                            if (type == 'R' || type == 'Q' || (type == 'K' && i ==1))
                                return true;
                            stopCatch = true;
                        }
                    }
                }   
            }

            rTemp = rank;
            fTemp = file;

            stopCatch = false;
            for (int i = 1; rTemp >= 0; i++) {
                rTemp = rank - i;
                if (!stopCatch && game.getTile(rTemp, file) != null) {
                    if (piece.getColor() == 'w') {
                        if (game.getTile(rTemp, file).getPiece() != null) {
                            char type = game.getTile(rTemp, file).getPiece().getType();
                            if (type == 'r' || type == 'q' || (type == 'k' && i == 1))
                                return true;
                            stopCatch = true;
                        }
                    }
                    else {
                        if (game.getTile(rTemp, file).getPiece() != null) {
                            char type = game.getTile(rTemp, file).getPiece().getType();
                            if (type == 'R' || type == 'Q' || (type == 'K' && i == 1))
                                return true;
                            stopCatch = true;
                        }
                    }
                }   
            }

            rTemp = rank;
            fTemp = file;

            stopCatch = false;
            for (int i = 1; (rTemp < 8 && fTemp < 8); i++) {
                rTemp = rank + i;
                fTemp = file + i;
                if (!stopCatch && game.getTile(rTemp, fTemp) != null) {
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

            rTemp = rank;
            fTemp = file;

            stopCatch = false;
            for (int i = 1; (fTemp < 8 && rTemp >= 0); i++) {
                rTemp = rank - i;
                fTemp = file + i;
                if (!stopCatch && game.getTile(rTemp, fTemp) != null) {
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

            rTemp = rank;
            fTemp = file;

            stopCatch = false;
            for (int i = 1; (rTemp >= 0 && fTemp >= 0); i++) {
                rTemp = rank - i;
                fTemp = file - i;
                if (!stopCatch && game.getTile(rTemp, fTemp) != null) {
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

            rTemp = rank;
            fTemp = file;

            stopCatch = false;
            for (int i = 1; (rTemp < 8 && fTemp >= 0); i++) {
                rTemp = rank + i;
                fTemp = file - i;
                if (!stopCatch && game.getTile(rTemp, fTemp) != null) {
                    if (piece.getColor() == 'w') {
                        if (game.getTile(rTemp, fTemp).getPiece() != null) {
                            char type = game.getTile(rTemp, fTemp).getPiece().getType();
                            if (type == 'b' || type == 'q' || (type == 'k' && i == 1)) {
                                return true;
                            }
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

            if (piece.getColor() == 'w') {
                if ((bottomleft != null) && (bottomleft.getPiece() != null)) {
                    if (bottomleft.getPiece().getType() == 'n')
                        return true;
                }

                if ((leftbottom != null) && (leftbottom.getPiece() != null)) {
                    if (leftbottom.getPiece().getType() == 'n')
                        return true;
                }

                if ((lefttop != null) && (lefttop.getPiece() != null)) {
                    if (lefttop.getPiece().getType() == 'n')
                        return true;
                }

                if ((topleft != null) && (topleft.getPiece() != null)) {
                    if (topleft.getPiece().getType() == 'n')
                        return true;
                }

                if ((topright != null) && (topright.getPiece() != null)) {
                    if (topright.getPiece().getType() == 'n')
                        return true;
                }

                if ((righttop != null) && (righttop.getPiece() != null)) {
                    if (righttop.getPiece().getType() == 'n')
                        return true;
                }

                if ((rightbottom != null) && (rightbottom.getPiece() != null)) {
                    if (rightbottom.getPiece().getType() == 'n')
                        return true;
                }

                if ((bottomright != null) && (bottomright.getPiece() != null)) {
                    if (bottomright.getPiece().getType() == 'n')
                        return true;
                }
            }

            else if (piece.getColor() == 'b') {
                if ((bottomleft != null) && (bottomleft.getPiece() != null)) {
                    if (bottomleft.getPiece().getType() == 'N')
                        return true;
                }

                if ((leftbottom != null) && (leftbottom.getPiece() != null)) {
                    if (leftbottom.getPiece().getType() == 'N')
                        return true;
                }

                if ((lefttop != null) && (lefttop.getPiece() != null)) {
                    if (lefttop.getPiece().getType() == 'N')
                        return true;
                }

                if ((topleft != null) && (topleft.getPiece() != null)) {
                    if (topleft.getPiece().getType() == 'N')
                        return true;
                }

                if ((topright != null) && (topright.getPiece() != null)) {
                    if (topright.getPiece().getType() == 'N')
                        return true;
                }

                if ((righttop != null) && (righttop.getPiece() != null)) {
                    if (righttop.getPiece().getType() == 'N')
                        return true;
                }

                if ((rightbottom != null) && (rightbottom.getPiece() != null)) {
                    if (rightbottom.getPiece().getType() == 'N')
                        return true;
                }

                if ((bottomright != null) && (bottomright.getPiece() != null)) {
                    if (bottomright.getPiece().getType() == 'N')
                        return true;
                }
            }

            Tile tr = game.getTile(rank + 1, file + 1);
            Tile tl = game.getTile(rank + 1, file - 1);
            Tile bl = game.getTile(rank - 1, file - 1);
            Tile br = game.getTile(rank - 1, file + 1);

            if (tr != null) {
                if (tr.getPiece() != null) {
                    if (piece.getColor() == 'w' && tr.getPiece().getType() == 'p')
                        return true;
                }
            }
            if (tl != null) {
                if (tl.getPiece() != null) {
                    if (piece.getColor() == 'w' && tl.getPiece().getType() == 'p')
                        return true;
                }
            }
            if (br != null) {
                if (br.getPiece() != null) {
                    if (piece.getColor() == 'b' && br.getPiece().getType() == 'P')
                        return true;
                }
            }
            if (bl != null) {
                if (bl.getPiece() != null) {
                    if (piece.getColor() == 'b' && bl.getPiece().getType() == 'P')
                        return true;
                }
            }
        }
        return false;
    }

    // determines if the given piece is attacked by an opponent's pieces
    public boolean isAttacked(Piece p) {
        int rank = Character.getNumericValue(iD.charAt(0));
        int file = Character.getNumericValue(iD.charAt(1));
        int rTemp = rank;
        int fTemp = file;
        boolean stopCatch = false;
        for (int i = 1; fTemp < 8; i++) {
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

            rTemp = rank;
            fTemp = file;

            stopCatch = false;
            for (int i = 1; fTemp >= 0; i++) {
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

            rTemp = rank;
            fTemp = file;

            stopCatch = false;
            for (int i = 1; rTemp < 8; i++) {
                rTemp = rank + i;
                if (!stopCatch && game.getTile(rTemp, file) != null) {
                    if (p.getColor() == 'w') {
                        if (game.getTile(rTemp, file).getPiece() != null) {
                            char type = game.getTile(rTemp, file).getPiece().getType();
                            if (type == 'r' || type == 'q' || (type == 'k' && i ==1))
                                return true;
                            stopCatch = true;
                        }
                    }
                    else {
                        if (game.getTile(rTemp, file).getPiece() != null) {
                            char type = game.getTile(rTemp, file).getPiece().getType();
                            if (type == 'R' || type == 'Q' || (type == 'K' && i ==1))
                                return true;
                            stopCatch = true;
                        }
                    }
                }   
            }

            rTemp = rank;
            fTemp = file;

            stopCatch = false;
            for (int i = 1; rTemp >= 0; i++) {
                rTemp = rank - i;
                if (!stopCatch && game.getTile(rTemp, file) != null) {
                    if (p.getColor() == 'w') {
                        if (game.getTile(rTemp, file).getPiece() != null) {
                            char type = game.getTile(rTemp, file).getPiece().getType();
                            if (type == 'r' || type == 'q' || (type == 'k' && i == 1))
                                return true;
                            stopCatch = true;
                        }
                    }
                    else {
                        if (game.getTile(rTemp, file).getPiece() != null) {
                            char type = game.getTile(rTemp, file).getPiece().getType();
                            if (type == 'R' || type == 'Q' || (type == 'K' && i == 1))
                                return true;
                            stopCatch = true;
                        }
                    }
                }   
            }

            rTemp = rank;
            fTemp = file;

            stopCatch = false;
            for (int i = 1; (rTemp < 8 && fTemp < 8); i++) {
                rTemp = rank + i;
                fTemp = file + i;
                if (!stopCatch && game.getTile(rTemp, fTemp) != null) {
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
            rTemp = rank;
            fTemp = file;
            stopCatch = false;
            for (int i = 1; (rTemp >= 0 && fTemp < 8); i++) {

                rTemp = rank - i;
                fTemp = file + i;

                if (!stopCatch && game.getTile(rTemp, fTemp) != null) {
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

            rTemp = rank;
            fTemp = file;

            stopCatch = false;
            for (int i = 1; (rTemp >= 0 && fTemp >= 0); i++) {
                rTemp = rank - i;
                fTemp = file - i;
                if (!stopCatch && game.getTile(rTemp, fTemp) != null) {
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

            rTemp = rank;
            fTemp = file;

            stopCatch = false;
            for (int i = 1; (rTemp < 8 && fTemp >= 0); i++) {
                rTemp = rank + i;
                fTemp = file - i;
                if (!stopCatch && game.getTile(rTemp, fTemp) != null) {
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

            if (p.getColor() == 'w') {
            if ((bottomleft != null) && (bottomleft.getPiece() != null)) {
                    if (bottomleft.getPiece().getType() == 'n')
                        return true;
                }

                if ((leftbottom != null) && (leftbottom.getPiece() != null)) {
                    if (leftbottom.getPiece().getType() == 'n')
                        return true;
                }

                if ((lefttop != null) && (lefttop.getPiece() != null)) {
                    if (lefttop.getPiece().getType() == 'n')
                        return true;
                }

                if ((topleft != null) && (topleft.getPiece() != null)) {
                    if (topleft.getPiece().getType() == 'n')
                        return true;
                }

                if ((topright != null) && (topright.getPiece() != null)) {
                    if (topright.getPiece().getType() == 'n')
                        return true;
                }

                if ((righttop != null) && (righttop.getPiece() != null)) {
                    if (righttop.getPiece().getType() == 'n')
                        return true;
                }

                if ((rightbottom != null) && (rightbottom.getPiece() != null)) {
                    if (rightbottom.getPiece().getType() == 'n')
                        return true;
                }

                if ((bottomright != null) && (bottomright.getPiece() != null)) {
                    if (bottomright.getPiece().getType() == 'n')
                        return true;
                }
        }

        else if (p.getColor() == 'b') {
            if ((bottomleft != null) && (bottomleft.getPiece() != null)) {
                    if (bottomleft.getPiece().getType() == 'N')
                        return true;
                }

                if ((leftbottom != null) && (leftbottom.getPiece() != null)) {
                    if (leftbottom.getPiece().getType() == 'N')
                        return true;
                }

                if ((lefttop != null) && (lefttop.getPiece() != null)) {
                    if (lefttop.getPiece().getType() == 'N')
                        return true;
                }

                if ((topleft != null) && (topleft.getPiece() != null)) {
                    if (topleft.getPiece().getType() == 'N')
                        return true;
                }

                if ((topright != null) && (topright.getPiece() != null)) {
                    if (topright.getPiece().getType() == 'N')
                        return true;
                }

                if ((righttop != null) && (righttop.getPiece() != null)) {
                    if (righttop.getPiece().getType() == 'N')
                        return true;
                }

                if ((rightbottom != null) && (rightbottom.getPiece() != null)) {
                    if (rightbottom.getPiece().getType() == 'N')
                        return true;
                }

                if ((bottomright != null) && (bottomright.getPiece() != null)) {
                    if (bottomright.getPiece().getType() == 'N')
                        return true;
                }
        }

        Tile tr = game.getTile(rank + 1, file + 1);
        Tile tl = game.getTile(rank + 1, file - 1);
        Tile bl = game.getTile(rank - 1, file - 1);
        Tile br = game.getTile(rank - 1, file + 1);
        if (tr != null) {
            if (tr.getPiece() != null) {
                if (p.getColor() == 'w' && tr.getPiece().getType() == 'p')
                    return true;
            }
        }
        if (tl != null) {
            if (tl.getPiece() != null) {
                if (p.getColor() == 'w' && tl.getPiece().getType() == 'p')
                    return true;
            }
        }
        if (br != null) {
            if (br.getPiece() != null) {
                if (p.getColor() == 'b' && br.getPiece().getType() == 'P')
                    return true;
            }
        }
        if (bl != null) {
            if (bl.getPiece() != null) {
                if (p.getColor() == 'b' && bl.getPiece().getType() == 'P')
                    return true;
            }
        }
        return false;    
    }

    // method necessary to support MouseListener object, but not used
    public void mouseEntered(MouseEvent e){}

    // method necessary to support MouseListener object, but not used
    public void mouseExited(MouseEvent e) {}

    // method necessary to support MouseListener object, but not used
    public void mousePressed(MouseEvent e) {}

    // method necessary to support MouseListener object, but not used
    public void mouseReleased(MouseEvent e) {}

    public static void main(String[] args) { /* see unit testing in Game.java for sample game */ }
}