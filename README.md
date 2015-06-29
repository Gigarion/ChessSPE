# ChessSPE
Files for the Chess engine/AI for 2015 SPE


To run the program, download the repository and compile the following files:

AI.java
Board.java
FlipBoard.java
Game.java
Memory.java
Move.java
MoveNow.java
NewGame.java
Piece.java
PVTable.java
SetFEN.java
TakeMove.java
Tile.java

After compilation, you can run the program from the command line with %Game 0 - 5:

%Game 0 has no AI and can be played with two players

%Game 1 has an AI which makes random moves

%Game 2 is the Victus engine, which goes to depth 4 and utilizes alpha-beta pruning, move ordering and a principle variation table

%Game 3 is the Nemo eninge, which goes to depth 5, utilizes alpha-beta pruning, null move pruning, move ordering, and a PV table

%Game 4 is the Quiet Nemo engine, which goes to depth 4 and attempts to implement quiescence as an addition to the Nemo engine.  Very slow

%Game 5 is the Charitable Nemo engine, which uses Nemo to attempt to optimize the opponent's score.  It should be physically impossible for this engine to win a game.
