package GameEnvironment.Game.Memory;

import java.awt.Point;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

import GameEnvironment.Board;
import GameEnvironment.Interaction;
import GameEnvironment.Piece;

class MemoryBoard extends Board {

	protected MemoryBoard(int rows, int cols, int maxPlayer, Interaction interaction, Piece currentPiece) {
		super(rows, cols, maxPlayer, interaction, currentPiece);
		
		ArrayList<Integer> firstHalf = new ArrayList<Integer>();
		ArrayList<Integer> secondHalf = new ArrayList<Integer>();
		
		for (int i = 1; i < 33; ++i) {
			firstHalf.add(i);
		}
		
		for (int i = 1; i < 33; ++i) {
			secondHalf.add(i);
		}
		
		Collections.shuffle(firstHalf);
		Collections.shuffle(secondHalf);
			
	
		System.out.println("===Contents of 1===");
		for (Integer k : firstHalf) {
			System.out.println(k);
		}
		System.out.println("===Contents of 2===");
		for (Integer k : secondHalf) {
			System.out.println(k);
		}
		
		int swap = 0;
		int first_counter = 0;
		int second_counter = 0;
		for (int i = 0; i < rows; ++i) {
			for (int j = 0; j < cols; ++j) {
				if (swap >= 32) {
					super.updateGrid(i, j, new MemoryPiece("", secondHalf.get(second_counter)));
					second_counter++;
				} else {
					super.updateGrid(i, j, new MemoryPiece("", firstHalf.get(first_counter)));
					first_counter++;
					swap++;
				}
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
