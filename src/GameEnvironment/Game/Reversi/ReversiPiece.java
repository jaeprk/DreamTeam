package GameEnvironment.Game.Reversi;

import java.util.HashMap;

import GameEnvironment.Piece;

public class ReversiPiece extends Piece{
	
	private static final String gameFolder = System.getProperty("user.dir")+"/src/GameEnvironment/Game/Reversi/";
	private final String[] pieceIcon = {gameFolder + "blackPiece.png", gameFolder +"whitePiece.png"};
	protected ReversiPiece(String pieceName, int player) {
		super(pieceName, player);
	}

	@Override
	public HashMap<Integer, Integer> move() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getIcons(int player) {
		// TODO Auto-generated method stub
		return pieceIcon[player-1];
	}

}
