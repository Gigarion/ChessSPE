import java.util.*;

public class MinMax {

	public static Move getMove(int maxDepth, Move move, boolean isMaximizer) {
		move.id();
		if (maxDepth == 0) {
			// if this is a leaf, find the value of the current board,
			// set it as the value of this move, and return this move
			System.out.println("evaluating");
			move.setValue(move.evaluate());
			return move;
		}

		maxDepth--;
		// deepen

		int thisValue;

		LinkedList<Move> toSearch = move.getChildren();
		// getChildren has to return an EMPTY LIST if there are no children, cant return null

		if (isMaximizer) {
			// if this is a maximizer node, the first child must be a larger number
			// also matechecks
			thisValue = Integer.MIN_VALUE;
		}

		else {
			// if this is a minimizer node, the first child must be a smaller number
			// also matechecks
			thisValue = Integer.MAX_VALUE;
		}

		for (Move m: toSearch) {
			// for each of this move's children, repeat

			int valueOfNextChild = getMove(maxDepth, m, !isMaximizer).evaluate();
			// recursively find the value of this child

			if (isMaximizer) {
				// if maximizer and child's value is higher than current value, update
				if (valueOfNextChild > thisValue) {
					thisValue = valueOfNextChild;
				}
			}
			else {
				// if minimizer and child's value is lower than current value, update
				if (valueOfNextChild < thisValue) {
					thisValue = valueOfNextChild;
				}
			}
		}
		// set the needed value, found from iterator
		move.setValue(thisValue);
		return move;
	}
}