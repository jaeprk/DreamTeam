package GameEnvironment;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Image;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import javax.accessibility.Accessible;
import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.CompoundBorder;

public final class GameGUI {
	//-------------------private global variables, used by GameGUI class-----------------------------------------------------------------------------
	final private String GAME_LAUNCHER = "Game Launcher";	//Name of Game Launcher GUI
	private final String QUIT = "QUIT GAME";  //Quit Button text
	private final String STATUS = "Status: ";  //Status text beginning 
	private String statusText;  //Text of current game status, used for statusLabel
	private String[] gameList;   //List of games name
	
	private JFrame frame;   //JFrame being used	
	private JPanel contentPanel, gamePanel;   //Primary content/background panel; panel to play game on
	private GridBagConstraints gbc;  //Constraints used for the GridBagLayout
	private JButton[] gameButton;   //List of game buttons	
	private JButton quitButton;   //Quit button for game
	private JTextField playerOne, playerTwo;  //Text field input for the two players
	private JLabel statusLabel;  //Status label for the game
	private JLabel[] player;   //Player label for the game
	private final Dimension gameDimension = new Dimension (600, 600);  //Dimension of the game panel
	
	private int[][] gameGrid;  //Grid of game, alternative to gameBoard.getGridPieces()	
	private final int MAX_X_COMPONENTS = 4;  //Maximum number of 
	private final int MAX_HIGHEST_PLAYER = 10;  //Number of players to display for the game stats
	private int gridy;  //Current y-axis of the grid, used for GridBagConstraints
	private int cellWidth, cellHeight, selectedRow, selectedCol; //Width of game cell, height of game cell, currently selected row and col
	
	private Board gameBoard;  //Current board game object	
	private boolean invalidInput;  //Is input valid
	
	
	//------------global color scheme-----------------------------------------------------------------------------------------------------------------
	Color dark_gray = Color.DARK_GRAY;
	Color light_gray = Color.LIGHT_GRAY;
	Color white = Color.WHITE;
	Color blue = Color.BLUE;
	Color black = Color.BLACK;
	Color red = Color.RED;
	
	//-----------default pieces icon-----------------------------------------------------------------------------------------------------------------
	private final String iconDirectory = Main.GAME_ENVIR_DIRECTORY + "PlayerIcon/";
	private final String[] defaultIcon = {iconDirectory + "player1.png", 
			                              iconDirectory + "player2.png", 
			                              iconDirectory + "player3.png", 
			                              iconDirectory + "player4.png"};
	private final String[] altIcon = {iconDirectory + "player1alt.png", 
            						  iconDirectory + "player2alt.png", 
            						  iconDirectory + "player3alt.png", 
            						  iconDirectory + "player4alt.png"};
	private final String[] altIcon2 = {iconDirectory + "player1alt2.png", 
            						   iconDirectory + "player2alt2.png", 
            						   iconDirectory + "player3alt2.png", 
            						   iconDirectory + "player4alt2.png"};
	
	//------------static global variable, used by package---------------------------------------------------------------------------------------------
	static JLabel[] highestScoreLabel;  //Label for highest scores per game
	static String gameSelected;  //Game selected
	
	/* @constructor to create the Game Launcher
	 * @param list of games found in the Game Folder
	 */
	GameGUI(String[] gameList){
		System.out.println("Loading Game Launcher...");
		this.gameList = gameList;
		
		//Create JFrame and add close operation procedures
		createFrame(this.GAME_LAUNCHER);
		closingOperation();   
		
		//Create content/background panel, JLabel from savedScores HashMap
		createContentPanel();
		createSavedGameLabel();
		resetSavedGameLabel();
		
		checkContentPanel(
				//TopPanel
				addComponents(1, new JLabel("Player One: ", SwingConstants.RIGHT), 
						      this.playerOne = new JTextField(), 
						      new JLabel("Player Two: ", SwingConstants.RIGHT), 
						      this.playerTwo = new JTextField()), 
				//MidPanel
				addComponents(1, createButton(this.gameList)),
				//BotPanel
				addComponents(1, GameGUI.highestScoreLabel));
		
		//Add content panel to JFrame, resize JFrame and make it visible 
		this.frame.add(this.contentPanel);
		this.frame.pack();
		this.frame.setVisible(true);
	}
	
	/* @constructor to create games
	 * @param game board
	 */
	public GameGUI(Board board){
		System.out.println("Launching game: " + GameGUI.gameSelected + "...");
		
		//Grab game board and start it
		this.gameBoard = board;
		this.gameBoard.startGame();
		
		//Set up the gameGrid, player labels, and current status
		gameGrid = new int[gameBoard.getRows()][gameBoard.getCols()];
		player = new JLabel[this.gameBoard.maxPlayer];
		statusText = STATUS + "Currently Player " + this.gameBoard.currentPlayer + " turn";
		
		//Create JFrame and add close operation procedures
		createFrame(GameGUI.gameSelected);
		
		//Create content/background panel, JLabel from savedScores HashMap
		createContentPanel();		
		
		checkContentPanel(
			//TopPanel
			addComponents(1, player[0] = new JLabel("Player One: " + Main.playerOne, SwingConstants.LEFT), 
					      player[1] = new JLabel("Player Two: " + Main.playerTwo, SwingConstants.LEFT)), 
			//MidPanel
			addComponents(2, createGamePanel()),
			//BotPanel
			addComponents(1, this.statusLabel = new JLabel(statusText, SwingConstants.LEFT), this.quitButton = new JButton(this.QUIT)));
		
		//Add content panel to JFrame, resize JFrame and make it visible 
		buttonListener();
		this.frame.add(this.contentPanel);
		this.frame.pack();
		this.frame.setVisible(true);		
	}
	
	//-------------------JFrame functions----------------------------------------------------------------------------------------------------------
	/* Create the JFrame
	 * @param name of JFrame title
	 */
	private void createFrame(String title) {
		System.out.println("Creating JFrame...");
		this.frame = new JFrame(title);
        this.frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);              
	}
	
	/* Closing operation for when GUI is closed
	 * Call Main.end and print out savedGame HashMap
	 * Used only for Game Launcher
	 */
	private void closingOperation() {
		frame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
            	terminateGUI();
            	Main.end(true);                
            }
        });
	}
	
	//-------------------ContentPanel functions----------------------------------------------------------------------------------------------------
	/* Create the basic content panel
	 * Content panel is split into three sections (top, middle, and bottom)
	 */
	private void createContentPanel() {
		System.out.println("Creating Content Panel...");
		this.contentPanel = new JPanel(new GridBagLayout());
		
		//Instantiate GridBagConstraints to resize with JFrame
		this.gbc = new GridBagConstraints();
		this.gbc.weightx = 1;
        this.gbc.weighty = 1;
        this.gbc.fill = GridBagConstraints.BOTH;
        this.gbc.gridy = (this.gridy = 0);
        this.gbc.insets = new Insets(0, 0, 2, 0);
        
        //Set border style: border line weight = 3; empty border padding = 10 all around
		this.contentPanel.setBorder(new CompoundBorder(BorderFactory.createLineBorder(this.dark_gray, 3), BorderFactory.createEmptyBorder(10,10,10,10))); 
		
		//Set background style: color light grey 
		this.contentPanel.setBackground(this.light_gray);
	}
	
	/* Check if Components is added into contentPanel
	 * ContentPanel is split into three parts (top, mid, and bottom)
	 * @param top, middle, and bottom panels is added, prompt if panel is not added
	 */
	private void checkContentPanel(boolean topPanel, boolean midPanel, boolean botPanel) {	
		//Check if top, middle, and bottom panel are added  		
		if (!topPanel)
			System.out.println("Top Panel is missing");
		
		if (!midPanel)
			System.out.println("Mid Panel is missing");
		
		if (!botPanel)
			System.out.println("Bot Panel is missing");		
	}
	
	/* Add Components to contentPanel
	 * @param size of the Components, a list of Components to be added
	 * @return if Components are added, a boolean
	 */
	private boolean addComponents(int gridWeight, Accessible...components) {
		int i = 0;
		gbc.gridwidth = gridWeight;
		//If components are empty return false
		if (components == null)
			return false;
		
		//Loop through all components and add it to the contentPanel
		for (Accessible comp: components) {			
			gbc.gridx = i++ % this.MAX_X_COMPONENTS;  //Current column in the contentPanel		
			gbc.gridy = this.gridy++ / this.MAX_X_COMPONENTS;  //Current row in the contentPanel
			this.contentPanel.add((Component) comp, this.gbc);
		}

		//Re-correct gridy so that it would be the next row
		this.gridy = ((--this.gridy / this.MAX_X_COMPONENTS) + 1) * this.MAX_X_COMPONENTS;
		return true;
	}
	
	//-------------------Components function------------------------------------------------------------------------------------------------------
	/* Create JButton Components
	 * @param list of names for the JButton
	 * @return an array of JButton
	 */
	private JButton[] createButton(String...buttonLabels) {
		//Return null if there isn't any buttonLabel
		if (buttonLabels == null)
			return null;
		
		Arrays.sort(buttonLabels);
		int sizeButton = buttonLabels.length;
		this.gameButton = new JButton[sizeButton];
		
		//Loop through buttonLabels, create and add JButtons to jButtons array
		for (int i = 0; i < sizeButton; ++i) { 
			this.gameButton[i] = new JButton(buttonLabels[i]);
			this.gameButton[i].setPreferredSize(new Dimension(100 , 30));		
		}
		
		buttonListener();	
		return this.gameButton;
	}
	
	/* Create JLabel from savedScores HashMap, from Main.java
	 * Set properties of JLabel 
	 */
	private void createSavedGameLabel() {
		//Check if savedScores HashMap is empty
		if (Main.savedScores.isEmpty())
			return;
		
		int sizeLabel = Main.savedScores.size();
		GameGUI.highestScoreLabel = new JLabel[sizeLabel];	
		
		for (int i = 0; i < sizeLabel; ++i) {
			GameGUI.highestScoreLabel[i] = new JLabel();
		   	GameGUI.highestScoreLabel[i].setOpaque(true);
	    	GameGUI.highestScoreLabel[i].setBackground(this.white);
	    	GameGUI.highestScoreLabel[i].setVerticalAlignment(JLabel.TOP);
		}
	}
	
	/* Load savedScores HashMap into JLabel
	 * Display the top 10 players of each game, used HTML format
	 */
	private void resetSavedGameLabel() {
		//Check if savedScores HashMap is empty
		System.out.println("Loading saved scores to JLabel...");
		if (Main.savedScores.isEmpty())
			return;
		
		int player, i = 0, sLen = 23;
		
		//Iterate through savedScores HashMap
	    for (Map.Entry<String, HashMap<String, Integer>> gameList: Main.savedScores.entrySet()) { 
	    	player = 0;
        	String temp = "<html>" + gameList.getKey() + "<br/>";
        	for (int j = 0; j < sLen; ++j)
        		temp += "-";
        	temp += "<br/>";
	        
        	//Iterate through player and score HashMap
        	for (Map.Entry<String, Integer> playerScores : gameList.getValue().entrySet()) {     
        		
        		//Print player name and score
        		temp += "&nbsp;&nbsp;" + (player + 1) + ": " + playerScores.getKey() + "- ";
        		temp += playerScores.getValue().toString() + "<br/>";
        		player++;
        	} 
        	
        	//Remaining players are blank
        	for (; player < this.MAX_HIGHEST_PLAYER; ++player)
        		temp += "&nbsp;&nbsp;" + (player + 1) + ": _______" + ": __<br/>" ;
        	temp += "</html>";        	
        	
        	//Set text of JLabel
        	GameGUI.highestScoreLabel[i++].setText(temp);        	
		}
	}
	
	/* Create the game panel
	 * @return a game panel
	 */	
	private JPanel createGamePanel() {
		//Determine width and height of game cells
		this.cellWidth = (int) gameDimension.getWidth() / gameBoard.getCols();
		this.cellHeight = (int) gameDimension.getHeight() / gameBoard.getRows();
		
		//Instantiate game panel
		this.gamePanel = new JPanel() {
			private static final long serialVersionUID = 1L;

			@Override
			public void paintComponent(Graphics g) {  
				//Determine color of player; current player is blue
				if (gameBoard.currentPlayer == 1) {
					player[0].setForeground(blue);
					player[1].setForeground(black);
				}
				else {
					player[0].setForeground(black);
					player[1].setForeground(blue);
				}
				
				//Set status label
				statusLabel.setText(statusText);
				super.paintComponent(g);    
				setBackground(white);
				g.setColor(dark_gray);	
				
				//Loop through the rows and cells
				for (int row = 0; row < gameBoard.getRows(); ++row) {
					for (int col = 0; col < gameBoard.getCols(); ++col) {
						
						//Alternate between cells and color them dark_gray
						if ((row + col) % 2 == 0) 
							g.fillRect(col * cellWidth, row * cellHeight, cellWidth, cellHeight);
						
						//If input is invalid, color that cell red 
						if (invalidInput && selectedRow == row && selectedCol == col) {
							g.setColor(red);
							g.fillRect(col * cellWidth, row * cellHeight, cellWidth, cellHeight);
							g.setColor(dark_gray);
						}
						
						//If there is a piece in the game board, add the piece icon
						if (gameBoard.getGridPieces()[row][col] != null) {
							BufferedImage image = null;
							
							try {
								//Pull icon path from Piece object
								image = ImageIO.read(new File(gameBoard.getGridPieces()[row][col].getIcons(gameGrid[row][col])));						
							} 
							catch (IOException e) {
								//Alternative image is used if not is found
								try {image = ImageIO.read(new File(defaultIcon[gameGrid[row][col]]));}
								catch (IOException ex) {/*do nothing*/}
							}
							finally {
								//Draw image
								g.drawImage(image, col * cellWidth, row * cellHeight, cellWidth, cellHeight, null);
							}
						}
					}				
				}				
			}
		}; 
		
		//Set dimension and add mouse listener
		this.gamePanel.setPreferredSize(gameDimension);
		mouseListener();
		return this.gamePanel;
	}
	//--------------Button/Action Listener-----------------------------------------------------------------------------------------------
	/* Action listener for when JButton is pressed
	 */
	private void buttonListener() {
		
		//Handle when Quit Game JButton is pressed, close JFrame and game
		if (this.quitButton != null) {	
			this.quitButton.addActionListener(new ActionListener() {
				
				@Override
				public void actionPerformed(ActionEvent source) {
					System.out.println("Quit Game...");	
					frame.dispose();
					terminateGUI();
				}
			});
		}
		
		//Handle when game is selected in Game Launcher
		else {			
			for (int i = 0; i < this.gameButton.length; ++i) {
				this.gameButton[i].addActionListener(new ActionListener()
				{
					
					@Override
					public void actionPerformed(ActionEvent source) {
						//Make sure there is something entered in the player 1 and 2 JTextField
						if (!playerOne.getText().isEmpty() && !playerTwo.getText().isEmpty()) {
							
							//Lock JTextField
							playerOne.setEditable(false);
							playerTwo.setEditable(false);
							
							//Set name of player 1 and 2
							Main.playerOne = playerOne.getText();
							Main.playerTwo = playerTwo.getText();
							
							//Get name of JButton
							GameGUI.gameSelected = ((JButton) source.getSource()).getActionCommand();
							try {
								
								//Attempt to instantiate game object
								//As long as the package and class (implemented GameFactory) is the same, a game object should be made
								Class gameClass = Class.forName("GameEnvironment.Game." + GameGUI.gameSelected + "." + GameGUI.gameSelected);
								Object game = gameClass.newInstance();					
							} 
							
							//Fail to create game object
							catch (InstantiationException | IllegalAccessException | ClassNotFoundException e) {
								System.out.println(GameGUI.gameSelected + ".java and package could not be found!");							
							}
						}
						
						else {
							System.out.println("Player One or Player Two text field is empty.");	
						}

		//				
		//				resetSavedGameLabel();
			//				contentPanel.revalidate();				
					}
				});
			}
		}
	}
	
	/* Add mouselistener to gamePanel
	 */
	private void mouseListener() {
		this.gamePanel.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) { 
				
				//Determine cell selected
				selectedCol = e.getX() / cellWidth;
				selectedRow = e.getY() / cellHeight;
				
				//If move is valid, add piece to game board, increment to next player and change status
				if (gameBoard.validMove(selectedRow, selectedCol)) {
					invalidInput = false;
					gameGrid[selectedRow][selectedCol] = gameBoard.currentPlayer - 1;
					gameBoard.nextPlayer();
					statusText = STATUS + "Currently Player " + gameBoard.currentPlayer + " turn";					
				}	
				
				//Else mark is invalid and print status 
				else {
					invalidInput = true;
					statusText = STATUS + "Invalid Input";
				}
				
				//Repaint game panel paintcomponents
				gamePanel.repaint();
			}
		});
	}
	
	//-----------------------Terminate GUI functions-----------------------------------------------------------------------------------------
	/* Free all instantiated objects in GameGUI.java
	 */
	private void terminateGUI() {
		System.out.println("Terminating GUI...");
		this.gameList = null;
		this.frame = null;	
		this.contentPanel = null;
		this.gbc = null;
		this.gameButton = null;	
		this.playerOne = null;
		this.playerTwo = null;
	}	
	
	/* Print out gridPieces from game board; used for debugging
	 */
	private void printBoard() {
		System.out.println(Arrays.deepToString(this.gameBoard.getGridPieces()));
	}
}
