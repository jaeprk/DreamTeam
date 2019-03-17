package GameEnvironment.Game.Reversi;

import java.awt.Point;
import java.util.List;

import GameEnvironment.Piece;

public class ReversiPiece extends Piece{
	
	private static final String gameFolder = System.getProperty("user.dir") + "/src/" + new Object(){}.getClass().getPackage().getName().replace(".", "/") + "/";
	private final String[] pieceIcon = {gameFolder + "blackPiece.png", gameFolder +"whitePiece.png"};
	
	/* Constructor for Piece
	 * @param name of piece (used for debugging), player the piece belong to
	 */
	protected ReversiPiece(String pieceName, int player) {
		super(pieceName, player);
	}

	@Override
	public List<Point> getMoves() {
		return null;
	}
	/* Get the Piece Icon from the pieceIcon string
	 * @param the player that own the piece
	 * @return the path of the icon
	 */
	@Override
	public String getIcons(int player) {
		return pieceIcon[player-1];
	}

	

}
