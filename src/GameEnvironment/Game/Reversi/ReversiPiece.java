package GameEnvironment.Game.Reversi;

import java.awt.Point;
import java.util.HashMap;
import java.util.List;

import GameEnvironment.Piece;

public class ReversiPiece extends Piece{
	
	private static final String gameFolder = System.getProperty("user.dir") + "/src/" + new Object(){}.getClass().getPackage().getName().replace(".", "/") + "/";
	private final String[] pieceIcon = {gameFolder + "blackPiece.png", gameFolder +"whitePiece.png"};
	protected ReversiPiece(String pieceName, int player) {
		super(pieceName, player);
	}

	@Override
	public List<Point> getMoves() {
		return null;
	}

	@Override
	public String getIcons(int player) {
		return pieceIcon[player-1];
	}

	

}
