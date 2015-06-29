public class Memory {
    Piece[][] pieces;
    char[][] types;
    char side;
    Tile ep;
    int halfmoves;
    boolean[] castlingrights;
    
    public Memory(Game game) {
        this.side = game.getSide();
        this.ep = game.getEP();
        this.halfmoves = game.getHalfMoves();
        this.castlingrights = new boolean[4];
        for (int i = 0; i < 4; i++) {
            this.castlingrights[i] = game.getCastlingRights()[i];
        }
        this.pieces = new Piece[8][8];
        this.types = new char[8][8];
        Tile[][] storeTiles = game.getTiles();
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                if (storeTiles[i][j].getPiece() != null) {
                    pieces[i][j] = storeTiles[i][j].getPiece();
                    types[i][j] = pieces[i][j].getType();
                }
                else {
                    pieces[i][j] = null;
                }
            }
        }
    }
    
    public Piece[][] getPieces() {
        return pieces;
    }
    
    public char[][] getTypes() {
        return types;
    }
    
    public char getSide() {
        return side;
    } 
    
    public Tile getEP() {
        return this.ep;
    }
    
    public int getHalfMoves() {
        return halfmoves;
    }
    
    public boolean[] getCastlingRights() {
        return castlingrights;
    }
    
    public static void main(String[] args) {
        Piece p = new Piece('p', 'w', null);
        Piece[] pieces = new Piece[1];
        pieces[0] = p;
        
        System.out.println(pieces[0]);
        System.out.println(p);
    }
}