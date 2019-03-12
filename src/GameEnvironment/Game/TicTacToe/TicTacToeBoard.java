package GameEnvironment.Game.TicTacToe;

import java.util.Arrays;
import java.util.HashMap;

import GameEnvironment.Board;
import GameEnvironment.Interaction;
import GameEnvironment.Main;
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
		// wrap in try and catch to avoid exception of accessing null object (empty pieces)

		boolean isWinner = false;

		// check winning rows 
		try {			
			int j = 0;
			for(int i = 0; i < 3; ++i) {
				if(getGridPieces()[i][j].playerNumber() == getGridPieces()[i][j+1].playerNumber() 
						&& getGridPieces()[i][j].playerNumber() == getGridPieces()[i][j+2].playerNumber()) {
					isWinner = true;
				}
			}
		} catch(Exception e) {
			//e.printStackTrace();
		}

		// check winning columns
		try {
			int j = 0;
			for(int i = 0; i < 3; ++i) {
				if(getGridPieces()[j][i].playerNumber() == getGridPieces()[j+1][i].playerNumber() 
						&& getGridPieces()[j][i].playerNumber() == getGridPieces()[j+2][i].playerNumber()) {
					isWinner = true;
				}
			}
		} catch(Exception e) {
			//e.printStackTrace();
		}


		// check winning diagonal
		try {
			if(getGridPieces()[0][0].playerNumber() == getGridPieces()[1][1].playerNumber() 
					&& getGridPieces()[0][0].playerNumber() == getGridPieces()[2][2].playerNumber()) {
				isWinner = true;

			}
		} catch(Exception e) {
			//e.printStackTrace();
		}

		// check opposing winning diagonal
		try {
			if(getGridPieces()[1][1].playerNumber() == getGridPieces()[0][2].playerNumber() 
					&& getGridPieces()[1][1].playerNumber() == getGridPieces()[2][0].playerNumber()) {
				isWinner = true;
			}
		} catch(Exception e) {
			//e.printStackTrace();
		}


		// no winner, check for draw: no more empty cells
		if(isWinner) {
			return isWinner;
		}
		else {
			for(int r = 0; r < 3; ++r) {
				for(int c = 0; c < 3; ++c) {
					if(getGridPieces()[r][c] == null) {
						return false;
					}
				}
			}
		}
		return false;
	}


	@Override
	protected void calculateScore() {
	}	
}

