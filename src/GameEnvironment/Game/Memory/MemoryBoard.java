package GameEnvironment.Game.Memory;

import java.util.HashMap;

import GameEnvironment.Board;
import GameEnvironment.Interaction;
import GameEnvironment.Piece;

class MemoryBoard extends Board {

	protected MemoryBoard(int rows, int cols, int maxPlayer, Interaction interaction, Piece currentPiece) {
		super(rows, cols, maxPlayer, interaction, currentPiece);
	}

	@Override
	public void startGame() {
		super.currentPlayer = 1;
	}

	@Override
	public boolean validMove(int row, int col) {
		if (super.getGridPieces()[row][col] == null) {
			super.updateGrid(row, col, new MemoryPiece("", super.currentPlayer));			
			return true;
		}
		return false;
	}

	@Override
	public HashMap<Integer, Integer> nextMove() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean endGame() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	protected int calculateScore() {
		// TODO Auto-generated method stub
		return 0;
		
	}

}
