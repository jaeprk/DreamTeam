package GameEnvironment.Game.Checkers;

import java.awt.Point;
import java.util.ArrayList;
import java.util.List;

import GameEnvironment.Board;
import GameEnvironment.Interaction;
import GameEnvironment.Piece;

public class CheckersBoard extends Board{
	
	Point currentPieceLocation;
	
	protected CheckersBoard(int rows, int cols, int maxPlayer, Interaction interaction, Piece currentPiece) {
		super(rows, cols, maxPlayer, interaction, currentPiece);
		this.currentPieceLocation = null;
	}

	@Override
	public void startGame() {
		for(int i=0; i<8; ++i) {
			if (i != 3 && i != 4) {
				for(int j=0; j<8; ++j) {
					CheckersPiece piece = new CheckersPiece(i<3?"black":"white",i<3?1:2);
					if(i%2 == 0 && j%2 == 0)
						this.updateGrid(i, j, piece);
					if(i%2 != 0 && j%2 != 0) {
						this.updateGrid(i, j, piece);
					}
				}
			}
		}
		super.currentPlayer = 1;
	}

	@Override
	public boolean endGame() {
		// TODO Auto-generated method stub
		//end game rules.
		//check score
		return false;
	}

	@Override
	protected int calculateScore() {
		return currentPlayer;
		// TODO Auto-generated method stub
		//+ one for every piece taken?
		
	}
	
	@Override
	protected void nextPlayer() {
		
		
	}

	@Override
	public boolean isMoveValid(int row, int col) {
		if(getGridPieces()[row][col] != null) {
			this.currentPiece = getGridPieces()[row][col];
			if(this.currentPiece.playerNumber() == this.currentPlayer) {
				this.currentPieceLocation = new Point(row, col);
				return true;
			}
		}
		if(getGridPieces()[row][col] == null && this.currentPieceLocation != null) {
			
		}
		return false;
	}

	@Override
	public List<Point> getAvailableMoves() {
		List<Point> availableMoves = new ArrayList<Point>();
		for(int i=0; i<8; ++i)
			for(int j=0; j<8; ++j)
				if(super.getGridPieces()[i][j]==null)
					if(isMoveValid(i,j))
						availableMoves.add(new Point(i,j));
		return availableMoves;
		
//		List<Point> moves = currentPiece.getMoves();
//		List<Point> availableMoves = new ArrayList<Point>();
//		Point nextMove;
//		
//		if(this.currentPieceLocation == null) {
//			for(int x = 0; x <= 7; x++) {
//				for(int y = 0; y <= 7; y++) {
//					// DEBUG : temporary pass
//					if(getGridPieces()[x][y] == null)
//						break;
//					if(getGridPieces()[x][y].playerNumber() == this.currentPlayer) {
//						availableMoves.add(new Point(x,y));
//					}		
//				}
//			}
//		}
//		else {
//		for(int i = 0; i < moves.size(); i++) {
//			Point currentCell = moves.get(i);
//			nextMove = new Point (this.currentPieceLocation.x + currentCell.x,
//								  this.currentPieceLocation.y + currentCell.y);
//			if(getGridPieces()[nextMove.x][nextMove.y] == null &&
//				nextMove.x > 0 && nextMove.x < 8 &&
//				nextMove.y > 0 && nextMove.y < 8){
//				availableMoves.add(nextMove);
//			}
//			if(getGridPieces()[nextMove.x][nextMove.y] != null &&
//			   getGridPieces()[nextMove.x][nextMove.y].playerNumber() != this.currentPlayer &&
//			   getGridPieces()[nextMove.x + currentCell.x][nextMove.y + currentCell.y] == null) {
//				availableMoves.add(nextMove);		
//			}
//		}
//		}
//		return new ArrayList<Point>();
	}

	@Override
	public boolean isGameTied() {
		return false;
	}
	
	

}
