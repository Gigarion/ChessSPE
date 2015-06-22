import java.util.*;

public class Move {
	private int value;
	private LinkedList<Move> children;
	String name;
	
	public Move(LinkedList<Move>) {

	}

	public Move(int val, LinkedList<Move> childs, String n) {
		this.name = n;
		this.children = childs;
		this.value = val;
	}

	public int evaluate() {
		return value;
	}

	public Move[] getChildren() {
		return children;
	}

	public void id() {
		System.out.println("Looking at: " + name);
	}

	public void setValue(int val) {
		this.value = val;
	}
}