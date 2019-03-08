import javax.swing.JLabel;

public abstract class Piece {

	private String pieceName;
	private JLabel image;
	
	
	public abstract void initialPosition(int x, int y);
	
	public abstract boolean isValidMove();
}
