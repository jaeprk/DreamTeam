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
	public boolean endGame() {
		for(int i = 0; i < 8; ++i) {
			for (int j = 0; j < 8; ++j) {
				if(getGridPieces()[i][j] == null) {
					return false;
				}
			}
		}
		return true;
	}

	@Override
	protected int calculateScore() {
		// TODO Auto-generated method stub
		return 0;
		
	}


	@Override
	public boolean isMoveValid(int row, int col) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public HashMap<Integer, Integer> getAvailableMoves() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean isGameTied() {
		// TODO Auto-generated method stub
		return false;
	}

}
