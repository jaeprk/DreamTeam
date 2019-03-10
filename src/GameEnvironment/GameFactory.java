package GameEnvironment;

public interface GameFactory {

	public void playGame(int x, int y);
	
	public boolean buildGame();
	
	public Board buildBoard();
	
	public Piece[] buildPieces();
	
	public Player[] buildPlayers();
	
	public Score[] buildScore();
	
	public String getTitle();
	
	public int getFrameSize();
	
	public String getGameStatus();
}
