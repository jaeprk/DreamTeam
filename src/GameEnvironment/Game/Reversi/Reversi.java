package GameEnvironment.Game.Reversi;

import GameEnvironment.Board;
import GameEnvironment.GameFactory;
import GameEnvironment.GameGUI;
import GameEnvironment.Interaction;
import GameEnvironment.Piece;

public class Reversi implements GameFactory{

	public Reversi() {
		buildGame(this.getClass().getSimpleName());	
	}

	@Override
	public void buildGame(String gameName) {
		new GameGUI(buildBoard(), gameName);		
	}

	@Override
	public Board buildBoard() {
		return new ReversiBoard(8, 8, 2, Interaction.BOARD, buildPieces());
	}

	@Override
	public Piece buildPieces() {
		return new ReversiPiece("", 1);
	}

}
