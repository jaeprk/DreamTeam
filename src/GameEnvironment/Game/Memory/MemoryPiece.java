package GameEnvironment.Game.Memory;

import java.util.HashMap;

import GameEnvironment.Piece;

class MemoryPiece extends Piece {
	private static final String gameFolder = System.getProperty("user.dir") + "\\GameEnvironment\\Game\\TicTacToe\\";
	
	private final String[] pieceIcon = {gameFolder + "X.png", gameFolder + "O.png"};
	
	protected MemoryPiece(String pieceName, int player) {
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
		return null;
	}

}
