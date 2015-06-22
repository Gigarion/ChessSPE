public class TreeTest {
	public static void main(String[] args) {
		Move[] empty = new Move[0];
		
		Move oneSoneSone = new Move(4, empty, "oneSoneSone");
		Move oneSoneStwo = new Move(3, empty, "oneSoneStwo");
		Move[] oneSoneC = {oneSoneSone, oneSoneStwo};
		Move oneSone = new Move(0, oneSoneC, "oneSone");

		Move oneStwoSone = new Move(5, empty, "oneStwoSone");
		Move oneStwoStwo = new Move(2, empty, "oneStwoStwo");
		Move oneStwoSthree = new Move(6, empty, "oneStwoSthree");
		Move[] oneStwoC = {oneStwoSone, oneStwoStwo, oneStwoSthree};
		Move oneStwo = new Move(17, oneStwoC, "oneStwo");
		
		Move oneSthree = new Move(6, empty, "oneSthree");

		Move[] oneC = {oneSone, oneStwo, oneSthree};
		Move one = new Move(1, oneC, "one");

		Move twoSoneSone = new Move(3, empty, "twoSoneSone");
		Move twoSoneStwo = new Move(6, empty, "twoSoneStwo");
		Move[] twoSoneC = {twoSoneSone, twoSoneStwo};
		Move twoSone = new Move(1, twoSoneC, "twoSone");

		Move twoStwoSone = new Move(3, empty, "twoStwoSone");
		Move twoStwoStwo = new Move(4, empty, "twoStwoStwo");
		Move[] twoStwoC = {twoStwoSone, twoStwoStwo};
		Move twoStwo = new Move(17, twoStwoC, "twoStwo");

		Move[] twoC = {twoSone, twoStwo};
		Move two = new Move(2, twoC, "two");

		Move three = new Move(4, empty, "three");

		Move[] children = {one, two, three};
		Move node = new Move(0, children, "node");
		System.out.println(AlphaBeta.getMove(3, node, true).evaluate());
	}
}