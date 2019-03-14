package GameEnvironment.Game.Memory;

import java.awt.Point;
import java.util.List;

import GameEnvironment.Piece;

class MemoryPiece extends Piece {
	private static final String gameFolder = System.getProperty("user.dir") + "\\GameEnvironment\\Game\\TicTacToe\\";
	private final String[] pieceIcon = {gameFolder + "X.png", gameFolder + "O.png"};
	
	protected MemoryPiece(String pieceName, int player) {
		super(pieceName, player);
	}

	@Override
	public List<Point> getMoves() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getIcons(int player) {
		return pieceIcon[player - 1];
	}

}
