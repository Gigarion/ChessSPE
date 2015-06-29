import java.util.*;

public class Move implements Comparable<Move> {
	private String origin;
	private String target;
	private int value;
	private Move bestChild;
	private boolean isPV;
	private boolean isCapture;
	private char myPiece;
	private char victim;

	private static final int[] PIECEVALS = {100, 200, 300, 400, 500, 600};

	public Move() {
		this.origin = null;
		this.target = null;
		this.bestChild = null;
		this.isPV = false;
	}
	
	public Move(Piece p, Tile t) {
		this.origin = p.getTile().getID();
		this.target = t.getID();
		this.bestChild = null;
		this.isPV = false;
		this.myPiece = p.getType();
		if (t.getPiece() != null) {
			this.isCapture = true;
			this.victim = t.getPiece().getType();
		}
		else {
			this.isCapture = false;
			this.victim = 'x';
		}
	}

	public char getMyPiece() {
		return myPiece;
	}

	public char getVictim() {
		return victim;
	}

	public String getTarget() {
		return target;
	}

	public String getOrigin() {
		return origin;
	}

	public int evaluate() {
		return value;
	}
	public void setBestChild(Move m) {
		bestChild = m;
	}

	public Move getBestChild() {
		return bestChild;
	}

	public void isPV() {
		isPV = true;
	}

	public boolean getPV() {
		return isPV;
	}

	public void setValue(int val) {
		this.value = val;
	}

	private int rankPiece(char c) {
		switch(c) {
			case 'p':
			case 'P': return PIECEVALS[0];
			case 'n': 
			case 'N': return PIECEVALS[1];
			case 'b': 
			case 'B': return PIECEVALS[2];
			case 'r': 
			case 'R': return PIECEVALS[3];
			case 'q': 
			case 'Q': return PIECEVALS[4];
			case 'k': 
			case 'K': return PIECEVALS[5];
			default:  return -100;
		}
	}

	public int compareTo(Move m) {
		double myVal = Math.random() + 1;
		double itVal = Math.random();
		int myMostValLeastVal = 0;
		int itMostValLeastVal = 0;

		//(victim + 6) - (attacker/100)
		//100, 200, 300, 400, 500, 600

		if (victim != 'x') {
			int attacker = rankPiece(myPiece);
			int vic = rankPiece(victim);
			myMostValLeastVal = (vic + 6) - (attacker/100);
		}
		myVal += myMostValLeastVal;

		if (m.getVictim() != 'x') {
			int attacker = rankPiece(m.getMyPiece());
			int vic = rankPiece(m.getVictim());
			itMostValLeastVal = (vic + 6) - (attacker/100);
		}
		itVal += myMostValLeastVal;

		if (isPV) 
			myVal += 530;

		if (m.getPV())
			itVal += 530;

		if (myVal > itVal) return 1;
		else if (myVal < itVal) return -1;
		else return 0;
	}
}