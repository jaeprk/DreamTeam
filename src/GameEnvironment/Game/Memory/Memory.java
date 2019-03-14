package GameEnvironment.Game.Memory;

import GameEnvironment.Board;
import GameEnvironment.GameFactory;
import GameEnvironment.GameGUI;
import GameEnvironment.Interaction;
import GameEnvironment.Piece;

public class Memory implements GameFactory {

	public Memory () {
		buildGame(this.getClass().getSimpleName());	
	}
	@Override
	public void buildGame(String gameName) {
		new GameGUI(buildBoard(), gameName);
	}

	@Override
	public Board buildBoard() {
		return new MemoryBoard(8, 8, 2, Interaction.BOARD, buildPieces());
	}

	@Override
	public Piece buildPieces() {
		// TODO Auto-generated method stub
		return null;
	}

}
