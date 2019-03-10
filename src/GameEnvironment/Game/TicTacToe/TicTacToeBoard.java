package GameEnvironment.Game.TicTacToe;

import java.util.HashMap;

import GameEnvironment.Board;
import GameEnvironment.Interaction;
import GameEnvironment.Piece;

class TicTacToeBoard extends Board {
	protected TicTacToeBoard(int rows, int cols, int maxPlayer, Interaction interaction, Piece currentPiece) {
		super(rows, cols, maxPlayer, interaction, currentPiece);
	}

	@Override
	public void startGame(){
		super.currentPlayer = 1;
		return;
	}

	@Override
	public boolean validMove(int row, int col) {
		if (super.getGridPieces()[row][col] == null) {
			super.updateGrid(row, col, new TicTacToePiece("", super.currentPlayer));			
			return true;
		}
		return false;
	}

	@Override
	public HashMap<Integer, Integer> nextMove() {
		return null;
	}

	@Override
	public boolean endGame() {
		return false;
	}
	
	@Override
	protected void calculateScore() {		
	}	
}

