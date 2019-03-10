package GameEnvironment.Game.TicTacToe;

import java.util.HashMap;

import GameEnvironment.Board;
import GameEnvironment.GameFactory;
import GameEnvironment.Piece;
import GameEnvironment.Player;
import GameEnvironment.Score;


public class TicTacToeGame implements GameFactory {

	private TicTacToeBoard gameBoard;
	private TicTacToePiece[] gamePieces;
	private TicTacToePlayer[] gamePlayers;
	
	private String currentPlayer;

	final private int rows = 3;
	final private int cols = 3;
	final private int piecesCount = 2;
	final private int playersCount = 2;


	@Override
	public boolean buildGame() {		

		// start game board with empty pieces
		TicTacToePiece[][] initialState = new TicTacToePiece[rows][cols];
		for(int row = 0; row < rows; ++row ) {
			for(int col = 0; col < cols; ++col) {
				initialState[row][col] = null;
			}
		}

		this.gameBoard.startBoard(initialState, null, null);
		
		// set first turn to X player
		this.currentPlayer = this.gamePlayers[0].getPlayer();
		
		return true;
	}


	@Override
	public Board buildBoard() {		
		this.gameBoard = new TicTacToeBoard(rows, cols);
		return this.gameBoard;
	}


	@Override
	public Piece[] buildPieces() {
		this.gamePieces = new TicTacToePiece[piecesCount];

		// add game pieces to array of pieces
		TicTacToePiece xPiece = new TicTacToePiece("X", "src/TicTacToe/x_Icon.png");
		TicTacToePiece oPiece = new TicTacToePiece("O", "src/TicTacToe/o_Icon.png");	
		this.gamePieces[0] = xPiece;
		this.gamePieces[1] = oPiece;

		return this.gamePieces;
	}


	@Override
	public Player[] buildPlayers() {
		this.gamePlayers = new TicTacToePlayer[playersCount];
		
		TicTacToePlayer xPlayer = new TicTacToePlayer("X", "Black");
		TicTacToePlayer oPlayer = new TicTacToePlayer("O", "White");
		this.gamePlayers[0] = xPlayer;
		this.gamePlayers[1] = oPlayer;
		
		return this.gamePlayers;
	}


	@Override
	public Score[] buildScore() {
		return null;
	}


	@Override
	public String getTitle() {
		return "Tic Tac Toe";
	}


	@Override
	public int getFrameSize() {
		return 600;
	}


	@Override
	public void playGame(int x, int y) {
		System.out.printf("row selected: %d\tcol selected: %d\n", x, y);
		
		// update board
		if(this.currentPlayer == "X") {
			this.gameBoard.updateBoard(x, y, new TicTacToePiece("X", "src/TicTacToe/x_Icon.png"));
		}
		else {
			this.gameBoard.updateBoard(x, y, new TicTacToePiece("O", "src/TicTacToe/o_Icon.png"));
		}
		
		// change player turn
		this.currentPlayer = (this.currentPlayer == "X") ? "O" : "X";
		return;
	}


	@Override
	public String getGameStatus() {
		return "Current Player: " + this.currentPlayer;
	}

}



///////////////////////////////////////////////////
//// Board Class
class TicTacToeBoard extends Board{

	public TicTacToeBoard(int rows, int cols) {
		super(rows, cols);
	}


	@Override
	public boolean startBoard(Piece[][] iGridPieces, String x, String y) {
		for(int row = 0; row < this.getRows(); ++row ) {
			for(int col = 0; col < this.getCols(); ++col) {
				this.gridPieces[row][col] = iGridPieces[row][col];
			}
		}
		return true;
	}


	@Override
	public HashMap<Integer, Integer> validMove() {
		return null;
	}


	@Override
	public boolean endGame() {
		
		// wrap in try and catch to avoid exception of accessing null object (empty pieces)
		try {
			// check winning conditions first
			int j = 0;
			for(int i = 0; i < 3; ++i) {
				if(this.gridPieces[i][j].getPieceType() == this.gridPieces[i][j+1].getPieceType() 
						&& this.gridPieces[i][j].getPieceType() == this.gridPieces[i][j+2].getPieceType()) {
					return true;
				}
			}

			for(int i = 0; i < 3; ++i) {
				if(this.gridPieces[j][i].getPieceType() == this.gridPieces[j+1][i].getPieceType() 
						&& this.gridPieces[j][i].getPieceType() == this.gridPieces[j+2][i].getPieceType()) {
					return true;
				}
			}

			if((this.gridPieces[0][0] == this.gridPieces[1][1] && this.gridPieces[0][0] == this.gridPieces[2][2]) ||
					(this.gridPieces[1][1] == this.gridPieces[0][2] && this.gridPieces[1][1] == this.gridPieces[2][0]))
			{
				return true;
			}
			
			// no winning condition found, check for draw
			// draw defined if no empty pieces are left
			for(int r = 0; r < this.getRows(); ++r) {
				for(int c = 0; c < this.getCols(); ++c) {
					if(this.gridPieces[r][c] == null) {
						return false;
					}
				}
			}
			return true;
		}
		catch(Exception e) {
			//e.printStackTrace();
			return false;
		}
	}


	@Override
	public boolean updateBoard(int row, int col, Piece piece) {

		this.gridPieces[row][col] = piece;
		return true;
	}
}



///////////////////////////////////////////////////
//// Piece Class
class TicTacToePiece extends Piece {

	public TicTacToePiece(String iPieceName, String iPieceIconLocation) {
		super(iPieceName, iPieceIconLocation);
	}

	@Override
	public void initialPosition(int x, int y) {
		return;		
	}

	@Override
	public boolean isValidMove() {
		return false;
	}
}



///////////////////////////////////////////////////
////Player Class
class TicTacToePlayer extends Player {

	public TicTacToePlayer(String iName, String iColor) {
		super(iName, iColor);
	}
}
