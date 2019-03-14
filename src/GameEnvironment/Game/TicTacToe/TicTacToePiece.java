package GameEnvironment.Game.TicTacToe;

import java.awt.Point;

import GameEnvironment.Piece;

/* Piece object for TicTacToe
 * Build the TicTacToe piece logic
 */
public class TicTacToePiece extends Piece{
	private static final String gameFolder = System.getProperty("user.dir") + "/src/" + new Object(){}.getClass().getPackage().getName().replace(".", "/") + "/";  //Folder of TicTacToe assets
	private final String[] pieceIcon = {gameFolder + "X.png", gameFolder + "O.png"};  //Assets image for each pieces
	
	/* Constructor for Piece
	 * @param name of piece (used for debugging), player the piece belong to
	 */
	TicTacToePiece(String pieceName, int player) {
		super(pieceName, player);
	}
	
	/* A HashMap(row, col) of all available moves that the Piece can make; used for nextMove() in Board 
	 * @return available moves; if Piece have no predetermined moves, return null
	 */
	@Override
	public Point[] getMoves() {
		return null;
	}
	
	/* Get the Piece Icon from the pieceIcon string
	 * @param the player that own the piece
	 * @return the path of the icon
	 */
	@Override
	public String getIcons(int player) {
		return pieceIcon[player - 1];
	}	
}

