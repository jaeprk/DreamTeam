package GameEnvironment;

public interface GameFactory {

	public boolean buildGame();
	
	public Board buildBoard();
	
	public Piece[] buildPieces();
	
	public Player[] buildPlayers();
	
	public Score[] buildScore();
}
