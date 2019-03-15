package GameEnvironment.Game.Memory;

import java.awt.Color;
import java.awt.Point;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

import GameEnvironment.Board;
import GameEnvironment.Interaction;
import GameEnvironment.Pattern;
import GameEnvironment.Piece;
import GameEnvironment.Game.Checkers.CheckersPiece;
import GameEnvironment.Game.Reversi.ReversiPiece;
import GameEnvironment.Game.TicTacToe.TicTacToePiece;

class MemoryBoard extends Board 
{
	private MemoryPiece[][] gridPieces = new MemoryPiece[100][100]; //matrix for holding randomized pieces
	int player1Last = 0;
	int player2Last = 0;
	boolean player1Selecting = false;
	boolean player2Selecting = false;
	
	protected MemoryBoard(int rows, int cols, int maxPlayer, Interaction interaction, Piece currentPiece) 
	{
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
					//super.updateGrid(i, j, new MemoryPiece("", secondHalf.get(second_counter)));
					gridPieces[i][j] =  new MemoryPiece("", secondHalf.get(second_counter));
					second_counter++;
				} else {
					//super.updateGrid(i, j, new MemoryPiece("", firstHalf.get(first_counter)));
					gridPieces[i][j] =  new MemoryPiece("", firstHalf.get(first_counter));

					first_counter++;
					swap++;
				}
			}
		}
	}
	
	@Override
	public void startGame() 
	{
		super.currentPlayer = 1;
	}


	@Override
	public boolean endGame() 
	{
		int ctr = 0;
		for(int i = 1; i < 9; ++i) 
		{
			for (int j = 1; j < 9; ++j) 
			{
				if(gridPieces[i][j] == null) 
				{
					ctr++;
				}
				//If every piece is null
				if (ctr == 64)
				{
					return true;
				}
			}
		}
		return false;
	}

	@Override
	protected int calculateScore() 
	{

		return 1;
		
	}


	@Override
	public boolean isMoveValid(int row, int col)
	{
		if (super.getCurrentPlayer() == 1) {
			System.out.println("Recalled, player1Last: " + player1Last);
		} else {
			System.out.println("Recalled, player2Last: " + player2Last);
		}
		
		//Reveal piece if clicked piece is null(unflipped)
		
	    if (gridPieces[row][col] != null) 
		{
			//temp MemoryPiece
			MemoryPiece p;
			
			p = gridPieces[row][col];
			
			//reveal the piece
			super.updateGrid(row, col, gridPieces[row][col]);
			
			//Update current piece
			super.currentPiece = gridPieces[row][col];
			
			if (player1Selecting) {
				super.nextPlayer();
				int current = ((MemoryPiece) (super.currentPiece)).getPiece();
				System.out.println("Current piece: " + current + "vs. last: " + player1Last);
				System.out.println("This piece was turned over by: " + super.getCurrentPlayer());
				if (current == player1Last) {
					System.out.println("It's a match");
					return true;
				} else {
					System.out.println("Flipping back over...");
				}
			}
			
			if (super.getCurrentPlayer() == 1 && !player1Selecting) {
				player1Last = ((MemoryPiece) (super.currentPiece)).getPiece();
				gridPieces[row][col] = null;
				player1Selecting = true;
				
				return true;
			}
		    
			//System.out.println("Current piece: " + ((MemoryPiece) (super.currentPiece)).getPiece() + " turned over by player: " + super.getCurrentPlayer());
			
			//Make null to make sure player cannot choose the same piece again.
			gridPieces[row][col] = null;
			
			/*
			//if next click equals first click
			if ( super.currentPiece == gridPieces[row][col] )
			{
				
				//keep second revealed
				gridPieces[row][col] = p;
				super.updateGrid(row, col, gridPieces[row][col]);
				//Make it visited
				gridPieces[row][col] = null;
				
				//keep second revealed
				super.updateGrid(row2, col2, gridPieces[row2][col2]);
				//make it visited
				gridPieces[row2][col2] = null;
				
				
				
			}
			
			else (gridPieces[row2][col2] != gridPieces[row][col])
			{
				//Flip piece back down
				super.updateGrid(row, col, gridPieces[row][col]);

				//Reupdate piece
				gridPieces[row][col] = p;
			}
			*/
			return true;
		}
		return false;
	}

	@Override
	public List<Point> getAvailableMoves() 
	{
		return new ArrayList<Point>();
	}

	@Override
	public boolean isGameTied() 
	{
		// TODO Auto-generated method stub
		return false;
	}

	public void flipPiece(int row, int col) {
		
	}
}
