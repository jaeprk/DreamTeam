package GameEnvironment.Game.Checkers;

import java.awt.Point;
import java.util.ArrayList;
import java.util.List;

import GameEnvironment.Piece;

public class CheckersPiece extends Piece{
	private static final String gameFolder = System.getProperty("user.dir") + "/src/GameEnvironment/Game/Checkers/";
	private final String[] pieceIcon = {gameFolder + "black.png", gameFolder + "blackKing.png", gameFolder + "red.png", gameFolder + "redKing.png"};
	
	private boolean king;
	private List<Point> moves;

	protected CheckersPiece(String pieceName, int player) {
		super(pieceName, player);
		moves =  new ArrayList<Point>();
		if(super.playerNumber() == 1) {
			this.moves.add(new Point(-1, -1));
			this.moves.add(new Point(1, -1));				
		}
		if(super.playerNumber() == 2) {
			this.moves.add(new Point(1, 1));
			this.moves.add(new Point(-1, 1));
		}
		this.king = false;
	}

	@Override
	public String getIcons(int player) {
		if(this.king) {
			return pieceIcon[(2 * (player-1)) + 1];
		} 
		return pieceIcon[2*(player-1)];
	}
	
	public void promote() {
		if(super.playerNumber() == 1) {
			this.moves.add(new Point(1, 1));
			this.moves.add(new Point(-1, 1));
		}
		if(super.playerNumber() == 2) {
			this.moves.add(new Point(-1, -1));
			this.moves.add(new Point(1, -1));
		}
		this.king = true;
		return;
	}
	
	public boolean isKing() {
		return this.king;
	}
	
	@Override
	public List<Point> getMoves() {
		return moves;
	}

}
