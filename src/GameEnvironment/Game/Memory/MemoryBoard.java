package GameEnvironment.Game.Memory;

import java.awt.Color;
import java.awt.Point;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.TimeUnit;

import GameEnvironment.Board;
import GameEnvironment.Interaction;
import GameEnvironment.Pattern;
import GameEnvironment.Piece;
import GameEnvironment.Game.Checkers.CheckersPiece;
import GameEnvironment.Game.Reversi.ReversiPiece;
import GameEnvironment.Game.TicTacToe.TicTacToePiece;
import javafx.util.Pair;

class MemoryBoard extends Board 
{
	private MemoryPiece[][] gridPieces = new MemoryPiece[100][100]; //matrix for holding randomized pieces
	int player1Last = 0;
	int player2Last = 0;
	MemoryPiece lastp1;
	MemoryPiece lastp2;
	Pair<Integer, Integer> lastp1Coord;
	Pair<Integer, Integer> lastp2Coord;
	
	int playerSelecting = 0; // Up to 4
	
	int p1score = 0;
	int p2score = 0;
	boolean tied = false;
	
	boolean gameStart = false;
	protected MemoryBoard(int rows, int cols, int maxPlayer, Interaction interaction, Piece currentPiece) 
	{
		super(rows, cols, maxPlayer, interaction, currentPiece);
		super.pattern = Pattern.BLANK;
		ArrayList<Integer> firstHalf = new ArrayList<Integer>();
		ArrayList<Integer> secondHalf = new ArrayList<Integer>();
		
		for (int i = 1; i < 9; ++i) {
			firstHalf.add(i);
		}
		
		for (int i = 1; i < 9; ++i) {
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
				if (swap >= 8) {
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
		
		for (int i = 0; i < rows; ++i) {
			System.out.println();
			for (int j = 0; j < cols; ++j) {
				System.out.print(gridPieces[i][j].getPiece() + " ");
			}
		}
		System.out.println();
		
	}
	
	@Override
	public void startGame() 
	{
		super.currentPlayer = 1;
		gameStart = true;
	}


	@Override
	public boolean endGame() 
	{
		int ctr = 0;
		boolean end = false;
		for (int i = 0; i < 4; ++i) {
			for (int j = 0; j < 4; ++j) {
				if (gridPieces[i][j].isFlipped()) {
					ctr++;
				}
			}
		}
		if (ctr == 16) {
			if (p1score == p2score) {
				this.tied = true;
				System.out.println("Tied");
			}
			else 
			{
				end = true;
			}
		}
		return end;
	}

	@Override
	protected int calculateScore() 
	{
		if ( p1score > p2score)
		{
			return p1score;
		}
		else
		{
			return p2score;
		}
	}


	@Override
	public boolean isMoveValid(int row, int col)
	{
		
		if (gameStart == true) {
			super.nextPlayer();
			this.gameStart = false;
			return true;
		}
		
	    // Piece that was selected on THIS method call
		MemoryPiece current = gridPieces[row][col];
		if (current.isFlipped()) {
			return false;
		}
		System.out.println("Current player: " + this.playerSelecting);

		if (this.playerSelecting <= 2) {
			super.currentPlayer = 1;
			this.playerSelecting++;
		} else {
			super.currentPlayer = 2;
			if (this.playerSelecting < 4) {
				this.playerSelecting++;
			} else {
				this.playerSelecting = 1;
			}
		}
		
		//Reveal piece if clicked piece is unflipped
	    if (!current.isFlipped()) 
		{
	    	// Player 1's first selection 
	    	
	    	if (this.playerSelecting == 1) {
	    		lastp1 = current;
	        	player1Last = current.getPiece();
	        	lastp1Coord = new Pair<Integer, Integer>(row, col);
	        	super.updateGrid(row, col, current);
	        	current.setFlipped(true);
	        	
	        } else if (this.playerSelecting == 2) { // Player 1's second selection, then check
	        	if (player1Last == current.getPiece()) {
	        		System.out.println("Player 1 Matched: " + player1Last + " with " + current.getPiece());
	        		super.updateGrid(row, col, current);
		        	current.setFlipped(true);
		        	p1score++;
		        	current.setPlayer(1);
		        	lastp1.setPlayer(1);
		        	System.out.println("---Scoreboard---");
		        	System.out.println("P1: " + p1score + " | P2: " + p2score);
		        		
	        	} else {
	                System.out.println("No match for player 1");
	                super.updateGrid(row, col, null);
	                super.updateGrid(lastp1Coord.getKey(), lastp1Coord.getValue(), null);
		        	current.setFlipped(false);
		        	lastp1.setFlipped(false);
	        	}
	        	// Player 2's selection
	        } else if (this.playerSelecting == 3) {
	        	lastp2 = current;
	        	player2Last = current.getPiece();
	        	lastp2Coord = new Pair<Integer, Integer>(row, col);
	        	super.updateGrid(row, col, current);
	        	current.setFlipped(true);
	        } else {
	        	if (player2Last == current.getPiece()) {
	        		System.out.println("Player 2 Matched: " + player2Last + " with " + current.getPiece());
	        		super.updateGrid(row, col, current);
		        	current.setFlipped(true);
		        	p2score++;
		        	System.out.println("---Scoreboard---");
		        	System.out.println("P1: " + p1score + " | P2: " + p2score);
		        	current.setPlayer(2);
		        	lastp1.setPlayer(2);
	        	} else {
	                System.out.println("No match for player 2");
	                /*
	                ***** SHOW SECOND PIECE *********
	               
	                super.updateGrid(row, col, current);
	                current.setFlipped(true);
	                
	                ***** DELAY FOR A FEW SECONDS ***
	                
	                ******** MOVE ON ****************
	                */
	                super.updateGrid(row, col, null);
	                super.updateGrid(lastp2Coord.getKey(), lastp2Coord.getValue(), null);
		        	current.setFlipped(false);
		        	lastp2.setFlipped(false);
	                
	        	}
	        	
	        }
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
		if (p1score == 4 && p2score == 4)
		{

			return true;
		
		}
		return false;
	}
}
