package GameEnvironment;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

public class GUI extends JFrame{

	private static final long serialVersionUID = 1L;
	
	private String title;
	private int frameSize;
	private int rows;
	private int cols;
	private int tileSize;

	private JPanel gamePanel;
	private JLabel statusBar;
	private Container container;

	private Board board;
	private Piece[][] grid;
	private Player player;


	public void buildFrame(GameFactory game) {

		this.title = game.getTitle();
		this.frameSize = game.getFrameSize();


		// Build game pieces
		game.buildPieces();

		
		// Build game players
		game.buildPlayers();
		
		
		// Build game board
		this.board = game.buildBoard();	
		this.grid = board.getGrid();
		

		// Initialize frame variables
		this.rows = board.getRows();
		this.cols = board.getCols();
		this.tileSize = frameSize / rows;

		
		// Build rest of game 
		game.buildGame();

		
		// Initialize game board/panel with pieces icons
		this.gamePanel = new JPanel() {
			private static final long serialVersionUID = 1L;

			@Override
			public void paintComponent(Graphics g) {  
				super.paintComponent(g);    
				setBackground(Color.WHITE); 

				g.setColor(Color.LIGHT_GRAY);
				for (int row = 0; row < rows; ++row) {
					for (int col = 0; col < cols; ++col) {
						if ((row + col) % 2 == 0) {
							g.fillRect(row * tileSize, col * tileSize, tileSize, tileSize);
						}
						if(grid[row][col] != null) {
							// TODO fix issue with rendering images in grid cells
							g.drawImage(grid[row][col].getPieceIcon() , col * tileSize, row * tileSize, null);
						}

					}
				}
			}
		}; 

		gamePanel.setPreferredSize(new Dimension(frameSize, frameSize));
		statusBar = new JLabel(game.getGameStatus());

		
		// event listener for mouse click - this is where game is played and board is updated
		gamePanel.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {  // mouse-clicked handler
				int mouseX = e.getX();
				int mouseY = e.getY();

				int rowSelected = mouseY / tileSize;
				int colSelected = mouseX / tileSize;
				
				if(!board.endGame()) {
					game.playGame(rowSelected, colSelected);
				}
				else {
					game.buildGame();
				}				

				// update status as game goes
				statusBar.setText(game.getGameStatus());
				
				// refresh the drawing panel
				repaint();  // call-back paintComponent().
			}
		});

		statusBar.setBorder(BorderFactory.createEmptyBorder(2, 5, 4, 5));

		SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
				container = getContentPane();
				container.setLayout(new BorderLayout());
				container.add(gamePanel, BorderLayout.CENTER);
				container.add(statusBar, BorderLayout.PAGE_START);

				setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
				pack(); 
				setTitle(title);
				setLocationRelativeTo(null);
				setVisible(true); 
			}
		});

	} // end buildFrame
}
