public class TPData {
	private int depth;
	private Move bestChild;
	private int score;
	public TPData(int currDepth, Move bestChild, int score) {
		this.depth = currDepth;
		this.bestChild = bestChild;
		this.score = score;
	}

	public int getDepth() {
		return this.depth;
	}

	public Move getBestChild() {
		return this.bestChild;
	}

	public int getScore() {
		return this.score;
	}
}