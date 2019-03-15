package GameEnvironment.Game.Memory;

import java.awt.Point;
import java.util.ArrayList;
import java.util.List;

import GameEnvironment.Board;
import GameEnvironment.Interaction;
import GameEnvironment.Piece;

class MemoryBoard extends Board {

	protected MemoryBoard(int rows, int cols, int maxPlayer, Interaction interaction, Piece currentPiece) {
		super(rows, cols, maxPlayer, interaction, currentPiece);
		
		int count = 0; 
		ArrayList<Integer> h = new ArrayList<Integer>();
		
		while (count < 32) {
			int intToAdd = (int)(Math.random() * ((32 - 1) + 1)) + 1;
			System.out.println(intToAdd);
			if (!h.contains(intToAdd)) {
				h.add(intToAdd);
				count++;
			} else {
				continue;
			}
		}
		System.out.println("===Contents of array===");
		for (int k = 0; k < count; ++k) {
			System.out.println(h.get(k));
		}

		for (int i = 0; i < rows; ++i) {
			for (int j = 0; j < cols; ++j) {
				int intToAdd = (int)(Math.random() * 32) + 1;
				
					super.updateGrid(i, j, new MemoryPiece("", intToAdd));
			}
				
		}
	}

	@Override
	public void startGame() {
		super.currentPlayer = 0;
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
	public List<Point> getAvailableMoves() {
		return new ArrayList<Point>();
	}

	@Override
	public boolean isGameTied() {
		// TODO Auto-generated method stub
		return false;
	}

}
