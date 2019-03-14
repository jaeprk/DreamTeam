package GameEnvironment.Game.Reversi;

import java.util.HashMap;
import java.util.List;
import java.util.*;

import GameEnvironment.Board;
import GameEnvironment.Interaction;
import GameEnvironment.Piece;

public class ReversiBoard extends Board{
	
	private List<Integer> direction;
	Boolean lock;

	protected ReversiBoard(int rows, int cols, int maxPlayer, Interaction interaction, Piece currentPiece) {
		super(rows, cols, maxPlayer, interaction, currentPiece);
		
	}

	@Override
	public void startGame() {
		super.updateGrid(3, 3, new ReversiPiece("",2));
		super.updateGrid(3, 4, new ReversiPiece("",1));
		super.updateGrid(4, 3, new ReversiPiece("",1));
		super.updateGrid(4, 4, new ReversiPiece("",2));
		super.currentPlayer = 1;
		lock = false;
		direction = new ArrayList<Integer>();
		return;
		
	}

	@Override
	public boolean validMove(int row, int col) {
		if(getGridPieces()[row][col] != null)
			return false;
		
		if(leftDirection(row,col)==3){
			updateGrid(row, col, new ReversiPiece("",currentPlayer));
			direction.add(3);
		}
		resetLock();
		if(rightDirection(row,col) == 4){
			updateGrid(row, col, new ReversiPiece("",currentPlayer));
			direction.add(4);
		}
		resetLock();
		if(upDirection(row,col) == 5){
			updateGrid(row, col, new ReversiPiece("",currentPlayer));
			direction.add(5);
		}
		resetLock();
		if(downDirection(row,col) == 6){
			updateGrid(row, col, new ReversiPiece("",currentPlayer));
			direction.add(6);
		}
		resetLock();
		if(leftUpDirection(row,col)==7){
			updateGrid(row, col, new ReversiPiece("",currentPlayer));
			direction.add(7);
		}
		resetLock();
		if(rightUpDirection(row,col)==8){
			updateGrid(row, col, new ReversiPiece("",currentPlayer));
			direction.add(8);
		}
		resetLock();
		if(leftDownDirection(row,col) == 9){
			updateGrid(row, col, new ReversiPiece("",currentPlayer));
			direction.add(9);
		}
		resetLock();
		if(rightDownDirection(row,col) == 10){
			updateGrid(row, col, new ReversiPiece("",currentPlayer));
			direction.add(10);
		}
		resetLock();
		
		if(direction.size() >0){
			for(int i = 0; i<direction.size(); i++){
				switch(direction.get(i)){
				case 3: flipLeftDirection(row,col);
						break;
				case 4: flipRightDirection(row,col);
						break;
				case 5: flipUpDirection(row,col);
						break;
				case 6: flipDownDirection(row,col);
						break;
				case 7: flipLeftUpDirection(row,col);
						break;
				case 8: flipRightUpDirection(row,col);
						break;
				case 9: flipLeftDownDirection(row,col);
						break;
				case 10: flipRightDownDirection(row,col);
						break;
				}
			}
			direction.clear();
			return true;
		}
		return false;
	}

	@Override
	public HashMap<Integer, Integer> nextMove() {
		HashMap<Integer, Integer> allValidMoves = new HashMap<Integer, Integer>();
		for(int row = 0; row<8; row++){
			for(int col = 0; col<8; col++){
				if(super.getGridPieces()[row][col] == null){
					if(validMove(row, col)){
						allValidMoves.put(row, col);
					}
				}
			}
		}
		return allValidMoves;
	}

	@Override
	public boolean endGame() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	protected void calculateScore() {
		// TODO Auto-generated method stub
		
	}
	
	private int leftDirection(int r, int c){
		int i = c-1;
		if(i < 0)
			return 0;
		for(; i >= 0; i--){
			if(super.getGridPieces()[r][i] == null)
				return 0;
			if(!getLock()){
				if(getGridPieces()[r][i].playerNumber()== currentPlayer)
					return 0;
				else
					setLock(true);
			}
			else
				if(getGridPieces()[r][i].playerNumber() == currentPlayer)
					return 3;
		}
		return 0;
	}
	
	private int rightDirection(int r, int c){
		int i = c+1;
		if(i > 7)
			return 0;
		for(; i <= 7; i ++){
			if(getGridPieces()[r][i] == null)
				return 0;
			if(!getLock()){
				if(getGridPieces()[r][i].playerNumber() == currentPlayer)
					return 0;
				else
					setLock(true);
			}
			else
				if(getGridPieces()[r][i].playerNumber() == currentPlayer)
					return 4;
		}
		return 0;
	}
	
	private int upDirection(int r, int c){
		int i = r-1;
		if(i < 0)
			return 0;
		for(; i >= 0; i--){
			if(getGridPieces()[i][c] == null)
				return 0;
			if(!getLock()){
				if(getGridPieces()[i][c].playerNumber() == currentPlayer)
					return 0;
				else
					setLock(true);
			}
			else
				if(getGridPieces()[i][c].playerNumber() == currentPlayer)
					return 5;
		}
		return 0;
	}
	
	private int downDirection(int r, int c){
		int i = r+1;
		if(i > 7)
			return 0;
		for(; i <=7; i++){
			if(getGridPieces()[i][c] == null)
				return 0;
			if(!getLock()){
				if(getGridPieces()[i][c].playerNumber() == currentPlayer)
					return 0;
				else
					setLock(true);
			}
			else
				if(getGridPieces()[i][c].playerNumber() == currentPlayer)
					return 6;
		}
		return 0;
	}
	
	private int leftUpDirection(int r, int c){
		int i = r-1;
		int j = c-1;
		if(i < 0 )
			return 0;
		if(j < 0)
			return 0;
		while(i >= 0 && j>= 0){
			if(getGridPieces()[i][j] == null)
				return 0;
			if(!getLock()){
				if(getGridPieces()[i][j].playerNumber() == currentPlayer)
					return 0;
				else
					setLock(true);
			}
			else
				if(getGridPieces()[i][j].playerNumber() == currentPlayer)
					return 7;
			i--;
			j--;
		}
		return 0;
	}
	
	private int rightUpDirection(int r, int c){
		int i = r-1;
		int j = c+1;
		
		if(i < 0)
			return 0;
		if( j > 7)
			return 0;
		while(i >= 0 && j <= 7){
			if(getGridPieces()[i][j] == null)
				return 0;
			if(!getLock()){
				if(getGridPieces()[i][j].playerNumber() == currentPlayer)
					return 0;
				else
					setLock(true);
			}
			else
				if(getGridPieces()[i][j].playerNumber() == currentPlayer)
					return 8;
			i--;
			j++;
		}
		return 0;
	}
	
	private int leftDownDirection(int r, int c){
		int i = r+1;
		int j = c-1;
		
		if(i > 7)
			return 0;
		if(j < 0)
			return 0;
		while(i <= 7 && j >= 0){
			if(getGridPieces()[i][j] == null)
				return 0;
			if(!getLock()){
				if(getGridPieces()[i][j].playerNumber() == currentPlayer)
					return 0;
				else
					setLock(true);
			}
			else
				if(getGridPieces()[i][j].playerNumber() == currentPlayer)
					return 9;
			i++;
			j--;
		}
		return 0;
	}
	
	private int rightDownDirection(int r, int c){
		int i = r+1;
		int j = c+1;
		
		if(i > 7)
			return 0;
		if(j > 7)
			return 0;
		while(i <=7 && j <= 7){
			if(getGridPieces()[i][j] == null)
				return 0;
			if(!getLock()){
				if(getGridPieces()[i][j].playerNumber() == currentPlayer )
					return 0;
				else
					setLock(true);
			}
			else
				if(getGridPieces()[i][j].playerNumber() == currentPlayer)
					return 10;
			i++;
			j++;
		}
		return 0;
	}
	
	private void flipLeftDirection(int r, int c){
		int i = c-1;
		while(getGridPieces()[r][i].playerNumber() != currentPlayer){
			updateGrid(r, i, new ReversiPiece("",currentPlayer));
			i--;
		}
			
	}
	
	private void flipRightDirection(int r, int c){
		int i = c+1;
		while(getGridPieces()[r][i].playerNumber() != currentPlayer){
			updateGrid(r, i, new ReversiPiece("", currentPlayer));
			i++;
		}
	}
	
	private void flipUpDirection(int r, int c){
		int i = r-1;
		while(getGridPieces()[i][c].playerNumber() != currentPlayer){
			updateGrid(i, c, new ReversiPiece("",currentPlayer));
			i--;
		}
	}
	
	private void flipDownDirection(int r, int c){
		int i = r+1;
		while(getGridPieces()[i][c].playerNumber() != currentPlayer){
			updateGrid(i, c, new ReversiPiece("", currentPlayer));
			i++;
		}
	}
	
	private void flipLeftUpDirection(int r, int c){
		int i = r-1;
		int j = c-1;
		while(getGridPieces()[i][j].playerNumber() != currentPlayer){
			updateGrid(i,j, new ReversiPiece("", currentPlayer));
			i--;
			j--;
		}
	}
	
	private void flipRightUpDirection(int r, int c){
		int i = r-1;
		int j = c+1;
		while(getGridPieces()[i][j].playerNumber()!= currentPlayer){
			updateGrid(i,j, new ReversiPiece("", currentPlayer));
			i--;
			j++;
		}
	}
	
	private void flipLeftDownDirection(int r, int c){
		int i = r+1;
		int j = c-1;
		while(getGridPieces()[i][j].playerNumber() != currentPlayer){
			updateGrid(i,j, new ReversiPiece("", currentPlayer));
			i++;
			j--;
		}
	}
	
	private void flipRightDownDirection(int r, int c){
		int i = r+1;
		int j = c+1;
		while(getGridPieces()[i][j].playerNumber() != currentPlayer){
			updateGrid(i,j,new ReversiPiece("", currentPlayer));
			i++;
			j++;
		}
	}

	private void setLock(boolean status){
		lock = status;
	}
	
	private boolean getLock(){
		return lock;
	}
	
	private void resetLock(){
		lock = false;
	}
}
