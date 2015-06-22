import java.util.*;

public class AlphaBeta {

	public static Move getMove(int maxDepth, Move move, boolean isMaximizer) {
		int alpha = Integer.MIN_VALUE;
		int beta = Integer.MAX_VALUE;
		Move toReturn = getMove(maxDepth, move, isMaximizer, alpha, beta);
		return toReturn;
	}

	private static Move getMove(int maxDepth, Move move, boolean isMaximizer, int alpha, int beta) {
	
		if (maxDepth == 0) {
			// if this is a leaf, find the value of the current board,
			// set it as the value of this move, and return this move
			move.setValue(move.evaluate());
			return move;
		}

		LinkedList<Move> toSearch = move.getChildren();

		if (toSearch.length == 0) {
			move.setValue(move.evaluate());
			return move;
		}


		maxDepth--;
		// deepen

		int thisValue;

		if (isMaximizer) {
			// if this is a maximizer node, the first child must be a larger number
			// also matechecks
			thisValue = Integer.MIN_VALUE;
			search: 
				for (int i = 0; i < toSearch.length; i++) {
					int valueOfNextChild = getMove(maxDepth, toSearch[i], !isMaximizer, alpha, beta).evaluate();
					// if maximizer and child's value is higher than current value, update
					if (valueOfNextChild > thisValue) {
						thisValue = valueOfNextChild;
						alpha = Math.max(alpha, thisValue);
					}
					if (thisValue > beta) {
						break search;
					}
					if (thisValue < alpha) {
						break search;
					}
				}
		}

		else {
			thisValue = Integer.MAX_VALUE;
			search: 
				for (int i = 0; i < toSearch.length; i++) {
					int valueOfNextChild = getMove(maxDepth, toSearch[i], !isMaximizer, alpha, beta).evaluate();;
					// if minimizer and child's value is lower than current value, update
					if (valueOfNextChild < thisValue) {
						thisValue = valueOfNextChild;
						beta = Math.min(beta, thisValue);
					}
					if (thisValue < alpha) {
						break search;
					}
					if (thisValue > beta) {
						break search;
					}
				}
		}

		// set the needed value, found from iterator
		move.setValue(thisValue);
		return move;
	}
}