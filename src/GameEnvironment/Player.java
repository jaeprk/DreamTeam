package GameEnvironment;

public abstract class Player {

	private String playerName;
	private String playerColor;
	private Score playerScore;
	
	
	public Player(String iName, String iColor) {
		this.playerName = iName;
		this.playerColor = iColor;
	}
	
	public String getPlayer() {
		return this.playerName;
	}
	
	public String getPlayerColor() {
		return this.playerColor;
	}
	
	public void setScore(Score score) {
		this.playerScore = score;
	}
	
	public Score getScore() {
		return this.playerScore;
	}
}
