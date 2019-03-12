package GameEnvironment.Game.TicTacToe;

import java.util.HashMap;

import GameEnvironment.Piece;

public class TicTacToePiece extends Piece{
	private static final String gameFolder = System.getProperty("user.dir") + "/src/GameEnvironment/Game/TicTacToe/";
	private final String[] pieceIcon = {gameFolder + "X.png", gameFolder + "O.png"};
	
	TicTacToePiece(String pieceName, int player) {
		super(pieceName, player);
	}

	@Override
	public HashMap<Integer, Integer> move() {
		return null;
	}

	@Override
	public String getIcons(int player) {
		return pieceIcon[player - 1];
	}	
}

