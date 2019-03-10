package GameEnvironment;
import java.util.HashMap;

public abstract class Board {

	private int rows;
	private int cols;
	protected Piece[][] gridPieces;
	final private String[] pattern = {"checkered", "line", "blank"}; // appearance of the board; like a checker
	final private String[] boardInteraction = {"board", "piece"}; // how user are interacting with the pieces
	
	
	public Board(int iRows, int iCols) {
		this.rows = iRows;
		this.cols = iCols;
		this.gridPieces = new Piece[rows][cols];
	}
	
	public abstract boolean startBoard(Piece[][] iGridPieces, String x, String y); 
	
	public abstract HashMap<Integer, Integer> validMove(); // define valid moves coordinates on board, if all cells are valid return null 
	
	public abstract boolean endGame();
	
	public abstract boolean updateBoard(int row, int col, Piece piece);
	
	
	public int getRows() {
		return this.rows;
	}
	
	public int getCols() {
		return this.cols;
	}	
	
	public Piece[][] getGrid() {
		return this.gridPieces;
	}
}
