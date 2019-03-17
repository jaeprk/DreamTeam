package GameEnvironment.Game.Memory;

import java.awt.Point;
import java.util.ArrayList;
import java.util.List;

import GameEnvironment.Piece;

class MemoryPiece extends Piece 
{
	private boolean flipped = false;
	private int belongsTo = 0;
	private static final String gameFolder = System.getProperty("user.dir") + "/src/" + new Object(){}.getClass().getPackage().getName().replace(".", "/") + "/";
	// Line below for Windows
	//private static final String gameFolder = System.getProperty("user.dir") + "/" + new Object(){}.getClass().getPackage().getName().replace(".", "/") + "/";
	private final String[] pieceIcon = {gameFolder + "1.png", gameFolder + "2.png", gameFolder + "3.png", gameFolder + "4.png",
										gameFolder + "5.png", gameFolder + "6.png", gameFolder + "A.png", gameFolder + "B.png",
										gameFolder + "C.png", gameFolder + "D.png", gameFolder + "E.png", gameFolder + "F.png",
										gameFolder + "G.png", gameFolder + "H.png", gameFolder + "I.png", gameFolder + "J.png",
										gameFolder + "K.png", gameFolder + "L.png", gameFolder + "M.png", gameFolder + "N.png",
										gameFolder + "O.png", gameFolder + "P.png", gameFolder + "Q.png", gameFolder + "R.png",
										gameFolder + "S.png", gameFolder + "T.png", gameFolder + "U.png", gameFolder + "V.png",
										gameFolder + "W.png", gameFolder + "X.png", gameFolder + "Y.png", gameFolder + "Z.png"};
	
	protected MemoryPiece(String pieceName, int player) 
	{
		super(pieceName, player);
		this.player = player;
	}

	@Override
	public List<Point> getMoves() {
		return new ArrayList<Point>();
	}

	@Override
	public String getIcons(int player) 
	{
		return pieceIcon[player - 1];
	}
	
	public int getPiece() {
		return this.player;
	}
	
	public boolean isFlipped() {
		return this.flipped;
	}
	
	public void setFlipped(boolean flipped) {
		this.flipped = flipped;
	}
	
	public int getBelongs() {
		return this.belongsTo;
	}
	
	public void setPlayer(int belongsTo) { 
		this.belongsTo = belongsTo;
	}

}
