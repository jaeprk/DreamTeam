package GameEnvironment;
import java.util.HashMap;

public abstract class Board {

	private Piece[][] gridPieces;
	final private String[] pattern = {"checkered", "line", "blank"}; // appearance of the board; like a checker
	final private String[] boardInteraction = {"board", "piece"}; // how user are interacting with the pieces
	
	
	public Board(int rows, int cols) {}
	
	public abstract boolean startBoard(Piece[][] iGridPieces, String x, String y);
	
	public abstract boolean didBoardChange(); 
	
	public abstract HashMap<Integer, Integer> validMove(); // define valid moves coordinates on board, if all cells are valid return null 
	
	public abstract boolean endGame();
}
