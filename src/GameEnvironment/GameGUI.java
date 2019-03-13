package GameEnvironment;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Image;
import java.awt.Insets;
import java.awt.Stroke;
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
	String gameSelected;  //Game selected
	
	private JFrame frame;   //JFrame being used	
	private JPanel contentPanel, gamePanel;   //Primary content/background panel; panel to play game on
	private GridBagConstraints gbc;  //Constraints used for the GridBagLayout
	private JButton[] gameButton;   //List of game buttons	
	private JLabel statusLabel;  //Status label for the game
	private JLabel[] playerLabel;   //Player label for the game
	private final Dimension gameDimension = new Dimension (600, 600);  //Dimension of the game panel
	private final Dimension textDimension = new Dimension (125, 25);  //Default dimension of JTextField
	private final Dimension buttonDimension = new Dimension (125, 30);   //Default dimension of JButton
	
	private final int MAX_HIGHEST_PLAYER = 10;  //Number of players to display for the game stats
	private int maxComponents;  //Maximum number of components per line	
	private int gridy;  //Current y-axis of the grid, used for GridBagConstraints
	private int cellWidth, cellHeight, selectedRow, selectedCol; //Width of game cell, height of game cell, currently selected row and col
	
	private Board gameBoard;  //Current board game object	
	private boolean invalidInput;  //Is input valid	
	private boolean continueGame;  //Determine if GUI is continue prompt
	private boolean addPlayers;  //Determine if there are more than 2 players in the game
	
	//-----------default pieces icon-----------------------------------------------------------------------------------------------------------------
	private final String iconDirectory = Main.GAME_ENVIR_DIRECTORY + "PlayerIcon/";
	private final String[] defaultIcon = {iconDirectory + "player1.png", 
			                              iconDirectory + "player2.png", 
			                              iconDirectory + "player3.png", 
			                              iconDirectory + "player4.png",
										  iconDirectory + "player1alt.png", 
										  iconDirectory + "player2alt.png", 
										  iconDirectory + "player3alt.png", 
										  iconDirectory + "player4alt.png",
										  iconDirectory + "player1alt2.png", 
										  iconDirectory + "player2alt2.png", 
										  iconDirectory + "player3alt2.png", 
										  iconDirectory + "player4alt2.png"};
	
	//------------static global variable, used by package---------------------------------------------------------------------------------------------
	static JLabel[] highestScoreLabel;  //Label for highest scores per game
	static JTextField[] enterPlayer;  //Text field input for the players
	static String[] players; //List of current players
	
	/* @constructor to create the Game Launcher
	 * @param list of games found in the Game Folder
	 */
	GameGUI(String[] gameList){
		System.out.println("Loading Game Launcher...");
		this.gameList = gameList;
		this.maxComponents = 4;
		GameGUI.enterPlayer = new JTextField[2];
		
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
						      GameGUI.enterPlayer[0] = new JTextField(), 
						      new JLabel("Player Two: ", SwingConstants.RIGHT), 
						      GameGUI.enterPlayer[1] = new JTextField()), 
				//MidPanel
				addComponents(1, createButton(this.gameList)),
				//BotPanel
				addComponents(1, GameGUI.highestScoreLabel));
		
		GameGUI.enterPlayer[0].setPreferredSize(this.textDimension);
		GameGUI.enterPlayer[1].setPreferredSize(this.textDimension);
		
		//Add content panel to JFrame, resize JFrame and make it visible 
		this.frame.add(this.contentPanel);
		this.frame.pack();
		this.frame.setVisible(true);
	}
	
	/* @constructor to create games
	 * @param game board, name of game
	 */
	public GameGUI(Board board, String gameName){
		this.gameSelected = gameName;
		System.out.println("Launching game: " + this.gameSelected + "...");
		this.maxComponents = 2;
		
		//Grab game board and start it
		this.gameBoard = board;
		this.gameBoard.startGame();	
		
		//If there are more than 2 players, generate JTextField to enter them
		if (Board.maxPlayer > 2) {
			this.addPlayers = true; 
			new GameGUI();
		}
		
		//Create JFrame and add close operation procedures
		createFrame(this.gameSelected);
		
		//Create content/background panel, JLabel from savedScores HashMap
		createContentPanel();
		JButton temp = new JButton(this.QUIT);
		
		checkContentPanel(
			//TopPanel
			addComponents(1, createPlayerLabels()), 
			//MidPanel
			addComponents(2, createGamePanel()),
			//BotPanel
			addComponents(1,  this.statusLabel = new JLabel(statusText, SwingConstants.LEFT), temp));
		
		//Add content panel to JFrame, resize JFrame and make it visible 
		buttonListener(temp);
		this.frame.add(this.contentPanel);
		this.frame.pack();
		this.frame.setVisible(true);		
	}
	
	/* @constructor to generate JTextField for more than 2 players
	 */
	private GameGUI(){
		System.out.println("Adding additional players...");
		this.maxComponents = 2;
		this.continueGame = true;
		
		//Create JFrame and add close operation procedures
		createFrame("Add Additional Players");
		
		//Create content/background panel, JLabel from savedScores HashMap
		createContentPanel();		
		
		checkContentPanel(
			//TopPanel
			addComponents(1, createPlayerInput()), 
			//MidPanel
			false,
			//BotPanel
			false);
		
		//Add content panel to JFrame, resize JFrame and make it visible 
		this.frame.add(this.contentPanel);
		this.frame.pack();
		this.frame.setVisible(true);		
	}
	
	/* @constructor to continue game
	 * @param name of winner and game
	 */
	private GameGUI(String winner, String game){
		System.out.println("Launching continue...");
		this.maxComponents = 2;
		this.continueGame = true;
		
		//Create JFrame and add close operation procedures
		createFrame("Restart " + game + "?");
		
		//Create content/background panel, JLabel from savedScores HashMap
		createContentPanel();		
		
		checkContentPanel(
			//TopPanel
			addComponents(2, new JLabel("<html>Congratulation to winner: " + winner + "<br/>Continue playing " + game + "?</html>", SwingConstants.CENTER)), 
			//MidPanel
			addComponents(1, createButton(this.QUIT, game)),
			//BotPanel
			false);
		
		//Add content panel to JFrame, resize JFrame and make it visible 
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
		this.contentPanel.setBorder(new CompoundBorder(BorderFactory.createLineBorder(Color.DARK_GRAY, 3), BorderFactory.createEmptyBorder(10,10,10,10))); 
		
		//Set background style: color light grey 
		this.contentPanel.setBackground(Color.LIGHT_GRAY);
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
	private boolean addComponents(int gridwidth, Component...components) {
		int i = 0;
		
		//If components are empty return false
		if (components == null)
			return false;
		
		//Loop through all components and add it to the contentPanel
		for (Component comp: components) {			
			gbc.gridwidth = gridwidth;
			gbc.gridx = (i++ % this.maxComponents);  //Current column in the contentPanel		
			gbc.gridy = this.gridy++ / this.maxComponents;  //Current row in the contentPanel
			this.contentPanel.add((Component) comp, this.gbc);
		}

		//Re-correct gridy so that it would be the next row
		this.gridy = ((--this.gridy / this.maxComponents) + 1) * this.maxComponents;
		return true;
	}
	
	//-------------------Components function------------------------------------------------------------------------------------------------------
	/* Create player labels and store current players
	 * @return list of player labels
	 */
	private JLabel[] createPlayerLabels() {
		//Set player labels, list of current players, and current status
		this.playerLabel = new JLabel[Board.maxPlayer];
		GameGUI.players = new String[Board.maxPlayer];
		this.statusText = STATUS + "Currently Player " + this.gameBoard.currentPlayer + " turn...";
		
		//Determine player names
		for (int i = 0; i < this.playerLabel.length; ++i) {			
			GameGUI.players[i] = GameGUI.enterPlayer[i].getText();			
			this.playerLabel[i] = new JLabel("Player " + (i + 1) + ": " + GameGUI.players[i], SwingConstants.CENTER);			
		}
		return this.playerLabel;
	}
	
	/* Create player input JTextField
	 * @return labels and JTextField for additional players
	 */
	private Component[] createPlayerInput() {
		Component[] temp = new Component[(Board.maxPlayer - 2) * 2];
		int j = 0;
		
		//Resize enterPlayer JTextField
		JTextField [] tempTextField = GameGUI.enterPlayer;
		GameGUI.enterPlayer = new JTextField[Board.maxPlayer];
		GameGUI.enterPlayer[j] = tempTextField[j++];
		GameGUI.enterPlayer[j] = tempTextField[j++];
		
		
		for (int i = 0; i < (Board.maxPlayer - 2) * 2; ++i) {			
			temp[i++] = new JLabel("Enter Player " + (j + 1) + ": ");			
			temp[i] = (GameGUI.enterPlayer[j] = new JTextField());
			GameGUI.enterPlayer[j++].setPreferredSize(this.textDimension);
		}		
		return temp;
	}
	
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
			this.gameButton[i].setPreferredSize(this.buttonDimension);		
			buttonListener(this.gameButton[i]);
		}
					
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
	    	GameGUI.highestScoreLabel[i].setBackground(Color.WHITE);
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
        		if (player++ < this.MAX_HIGHEST_PLAYER) {
	        		temp += "&nbsp;&nbsp;" + (player) + ": " + playerScores.getKey() + " - ";
	        		temp += playerScores.getValue().toString() + "<br/>";
        		}
        	} 
        	
        	//Remaining players are blank
        	for (; player < this.MAX_HIGHEST_PLAYER; ++player)
        		temp += "&nbsp;&nbsp;" + (player + 1) + ": _______" + " - __<br/>" ;
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
				for (int i = 0; i < playerLabel.length; ++i)
					if (i == (gameBoard.currentPlayer - 1))
						playerLabel[i].setForeground(Color.BLUE);
					else
						playerLabel[i].setForeground(Color.BLACK);
				
				//Set status label
				statusLabel.setText(statusText);
				super.paintComponent(g);    
				setBackground(gameBoard.getColor());
				g.setColor(Color.DARK_GRAY);	
				
				//Loop through the rows and cells
				for (int row = 0; row < gameBoard.getRows(); ++row) {
					for (int col = 0; col < gameBoard.getCols(); ++col) {
						
						//Alternate between cells and color them dark_gray
						if (gameBoard.getPattern() == Pattern.CHECKERED) {
							if ((row + col) % 2 == 0) 
								g.fillRect(col * cellWidth, row * cellHeight, cellWidth, cellHeight);							
						}
						else if (gameBoard.getPattern() == Pattern.BLANK)
							g.drawRect(col * cellWidth, row * cellHeight, cellWidth, cellHeight);
						
						//If input is invalid, color that cell red 
						if (invalidInput && selectedRow == row && selectedCol == col) {
							g.setColor(Color.RED);
							g.fillRect(col * cellWidth, row * cellHeight, cellWidth, cellHeight);
							g.setColor(Color.DARK_GRAY);
						}
						
						//If there is a piece in the game board, add the piece icon
						if (gameBoard.getGridPieces()[row][col] != null) {
							BufferedImage image = null;
							
							try {
								//Pull icon path from Piece object
								image = ImageIO.read(new File(gameBoard.getGridPieces()[row][col].getIcons(gameBoard.getGridPieces()[row][col].player)));						
							} 
							catch (IOException | ArrayIndexOutOfBoundsException e) {
								//Alternative image is used if not is found
								try {image = ImageIO.read(new File(defaultIcon[(gameBoard.getGridPieces()[row][col].player - 1) % defaultIcon.length]));}
								catch (IOException ex) {/*do nothing*/}
							}
							finally {
								//Draw image
								g.drawImage(image, col * cellWidth, row * cellHeight, cellWidth, cellHeight, null);
							}
						}
					}
//					g.setColor(red);
//					Graphics2D g2 = (Graphics2D) g;
//					double thickness = 10;
//					Stroke oldStroke = g2.getStroke();
//					g2.setStroke(new BasicStroke((float) thickness));
//					g2.drawRect(1 * cellWidth, 1 * cellHeight, cellWidth, cellHeight);
//					g2.setStroke(oldStroke);
//					g.setColor(dark_gray);
//					
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
	 * @param button to be added with listener
	 */
	private void buttonListener(JButton jButton) {
		
		jButton.addActionListener(new ActionListener() {				
			@Override
			public void actionPerformed(ActionEvent source) {
				//Handle when Quit Game JButton is pressed, close JFrame and game
				if (((JButton) source.getSource()).getActionCommand().equals(QUIT)) {
					System.out.println("Quit Game...");	
					frame.dispose();
					terminateGUI();
				}
				
				//Handle when game button is selected
				else {
					if (!GameGUI.enterPlayer[0].getText().isEmpty() && !GameGUI.enterPlayer[1].getText().isEmpty()) {
						
						//Lock JTextField
						enterPlayer[0].setEditable(false);
						enterPlayer[1].setEditable(false);
						
						//Get name of JButton
						gameSelected =((JButton) source.getSource()).getActionCommand();
						
						try {
							
							//Attempt to instantiate game object
							//As long as the package and class (implemented GameFactory) is the same, a game object should be made
							Class<?> gameClass = Class.forName("GameEnvironment.Game." + gameSelected.toString() + "." + gameSelected.toString());
							gameClass.newInstance();	
							
							if (continueGame)
								frame.dispose();
						} 
						
						//Fail to create game object
						catch (InstantiationException | IllegalAccessException | ClassNotFoundException e) {
							System.out.println(gameSelected + ".java and package could not be found!");							
						}
					}
					
					else {
						System.out.println("Player One or Player Two text field is empty.");	
					}
				}
			}
		});
	}
	
	/* Add mouseListener to gamePanel
	 */
	private void mouseListener() {
		this.gamePanel.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) { 
				
				//Determine cell selected
				selectedCol = e.getX() / cellWidth;
				selectedRow = e.getY() / cellHeight;
				
				//If move is valid, add piece to game board, increment to next player and change status
				if (gameBoard.isMoveValid(selectedRow, selectedCol)) {
					invalidInput = false;
					
					//If game reach end state
					if (gameBoard.endGame()) {
						
						//Call to update game scores after game ends. Winning player is last, current player
						//Sort savedScores HashMap and reprint highestScoreLabel
						//Close game JFrame and open continue prompt
						Main.addScore(gameSelected, GameGUI.players[gameBoard.currentPlayer - 1], gameBoard.calculateScore());
						Main.sortSavedScores(gameSelected);
						resetSavedGameLabel();
						frame.dispose();
						new GameGUI(GameGUI.players[gameBoard.currentPlayer - 1], gameSelected);
					}
					
					//If game ended in a draw, close game JFrame and open continue prompt
					else if (gameBoard.isGameTied()) {
						frame.dispose();
						new GameGUI("TIED GAME", gameSelected);
					}
					
					//Else, game continues with next player
					else {						
						gameBoard.nextPlayer();
						statusText = STATUS + "Currently Player " + gameBoard.currentPlayer + " turn...";		
					}
				}	
				
				//Else mark is invalid and print status 
				else {
					invalidInput = true;
					statusText = STATUS + "Invalid Input";
				}
				
				//Repaint game panel paintComponents
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
		GameGUI.players = null;
	}	
	
	/* Print out gridPieces from game board; used for debugging
	 */
	private void printBoard() {
		System.out.println(Arrays.deepToString(this.gameBoard.getGridPieces()));
	}
}
