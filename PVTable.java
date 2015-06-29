import java.util.*;
public class PVTable {
	LinkedList<PVHolder> pvholders;

	private class PVHolder {
		int depth;
		LinkedList<Move> moves;

		private PVHolder(int d) {
			this.depth = d;
			this.moves = new LinkedList<Move>();
		}

		private void addMove(Move m) {
			moves.add(m);
		}

		private boolean isMove(Move m) {
			for (Move inMe : moves) {
				if (inMe.getMyPiece() == m.getMyPiece() && inMe.getTarget() == m.getTarget()) {
					System.out.println(true);
					return true;
				}
			}
			return false;
		}
	}

	public void clear() {
		pvholders = new LinkedList<PVHolder>();
	}

	public PVTable() {
		this.pvholders = new LinkedList<PVHolder>();
	}

	public void addMove(int d, Move m) {
		for (PVHolder hold : pvholders) {
			if (hold.depth == d)
				hold.addMove(m);
		}
	}

	public boolean isMove(int d, Move m) {
		for (PVHolder hold: pvholders) {
			if (hold.depth == d) {
				return hold.isMove(m);
			}
		}
		return false;
	}
}