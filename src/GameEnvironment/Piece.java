package GameEnvironment;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

public abstract class Piece {

	private String pieceName;
	private BufferedImage pieceIcon;
	
	
	public Piece(String iPieceName, String iPieceIconLocation) {
		this.pieceName = iPieceName;
		try {
			this.pieceIcon = ImageIO.read(new File(iPieceIconLocation));
		} catch (IOException e) {
			e.printStackTrace();
		}
	};
	
	public abstract void initialPosition(int x, int y);
	
	public abstract boolean isValidMove();
	
	public BufferedImage getPieceIcon( ) {
		return this.pieceIcon;
	}
	
	public String getPieceType() {
		return this.pieceName;
	}
}
