package GameEnvironment;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FilenameFilter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.HashMap;
import java.util.Map;

/* Main.java class for: 
 *   - Read list of games from Game folder
 *   - Load list of games into static savedScores HashMap
 *   - Print out savedScores HashMap
 *   - Start Game Launcher
 *   - Save savedScores HashMap into SavedScores.txt file
 *   - Terminate program
 */
public final class Main {
	
	//-------------------private global variables, used by Main class---------------------------------------------------------------------------------------
	private final static String GAME_FOLDER_NAME = "Game"; //name of folder that contains all the game packages
	private final static String SAVE_FILE_NAME = "SavedScores.txt"; //text file for saved game scores
	private final static String GAME_REGEX = "##$$"; //identify game name in SavedScores.txt file
	private final static String PLAYER_SCORE_REGEX = "@@!!"; //identify player name and score in SavedScores.txt file
	
	
	//-------------------static variables, used by package---------------------------------------------------------------------------------------------------
	static Map<String, HashMap<String, Integer>> savedScores; 
	static String playerOne, playerTwo;
	final static String GAME_ENVIR_DIRECTORY = System.getProperty("user.dir") + "/src/GameEnvironment/"; //directory of Main package, GameEnvironment
	
	public static void main(String[] args) {
		System.out.println("Starting Game Environment...");
		start();		
	}
	
	//-------------------start game interface-----------------------------------------------------------------------------------------------------------------
	/* Read in list of game from Game folder
	 * Load scores into savedScores HashMap, print it out to console
	 * Run the Game Launcher
	 */
	private static void start() {
		String[] gameList = grabGameList();
		
		//If Game folder could not be found or is empty, print prompt and terminate program
		if (gameList == null) {
			System.out.println(Main.GAME_ENVIR_DIRECTORY + Main.GAME_FOLDER_NAME + "folder could not be found or folder is empty.");
			end(false);
		}
		
		//Store list of games into savedScores HashMap
		Main.savedScores = new HashMap<String, HashMap<String, Integer>>();
		
		for (String gameName: gameList)
			Main.savedScores.put(gameName, new HashMap<String, Integer>());
		
		//Load saved game scores into savedScores HashMap and print it to prompt
		loadGameScores();
		printSavedScores();	
		
		//Instantiate game launcher
		new GameGUI(gameList);
	}
	
	//-------------------start game helper classes-------------------------------------------------------------------------------------------------------------
	/* Read in the game lists/packages within the Game folder
	 * Used to determine what game is available
	 * @return a list of the games found in the folder, return null if folder does not exist or no game folder found
	 */
	private static String[] grabGameList() {
		System.out.println("Loading Game List...");
		File directory = new File(Main.GAME_ENVIR_DIRECTORY + Main.GAME_FOLDER_NAME);
		String[] subdirectory = directory.list(new FilenameFilter() {
		  @Override
		  public boolean accept(File current, String name) {
		    return new File(current, name).isDirectory();
		  }
		});
		return subdirectory;
	}
	
	/* Load game scores from SavedScores.txt file into savedScroes HashMap
	 * @return boolean of whether or not saved game scores were loaded successfully
	 */
	private static boolean loadGameScores() {
		System.out.println("Loading Saved Scores...");
		
		//Check savedScores have at least one game (i.e. there are packages within Game Folder)
        if (Main.savedScores.size() < 1)
            return false;
        
        BufferedReader reader = null;
        
        try {
        	//Attempt to read SavedScores.txt file
        	reader = new BufferedReader(new FileReader(Main.GAME_ENVIR_DIRECTORY + Main.SAVE_FILE_NAME));
        	
        	String line, game = null;
        	String[] parse;
        	
        	//While there is a next line, read it
    		while ((line = reader.readLine()) != null) 
    			
    			//If String contains ##$$, it is the game name, store it into game String
    			if (line.contains(GAME_REGEX))
    				game = line.replace(GAME_REGEX, "");
    		
    			//If String contains @@!!, it is the player name and score, store it into savedScores HashMap
    			else if (line.contains(PLAYER_SCORE_REGEX)) {
    				parse = line.split(PLAYER_SCORE_REGEX, -2);
    				Main.savedScores.get(game).put(parse[1], Integer.parseInt(parse[2]));
    			}        				
        		
        }
        
        //Failed to read from SavedScores.txt file
        catch (IOException | NullPointerException e) {
            System.out.println("Failed to read player scores from SavedScores.txt file");
            return false;
        } finally {
        	
        	//Attempt to close reader
           try {reader.close();} catch (Exception ex) {/*do nothing*/}
        }
        return true;
	}
	
	//-----------------------end game, clean up any messes------------------------------------------------------------------------------------------------
	
	/* Terminate program
	 * Free all instantiated objects in Main.java and highestScoreLabel, from GameGui.java
	 * @param boolean to whether or not the program should save savedScores HashMap to SavedScores.txt file
	 */
	public static void end(boolean saveGame) {
		System.out.println("Terminating Game Environment...");
		if (saveGame)
			saveGameScores();
		Main.savedScores = null;
		GameGUI.highestScoreLabel = null;
        System.exit(0);
	}
	
	//-----------------------end game helper functions----------------------------------------------------------------------------------------------------
	/* Save current game scores into SavedScores.txt file from savedScores HashMap
	 */
	private static boolean saveGameScores() { 
		System.out.println("Saving Game Scores...");
		
        //Check savedScores have at least one game (i.e. there are packages within Game Folder)
        if (Main.savedScores.size() < 1)
            return false;
        
        Writer writer = null;

        try {        	
        	//Attempt to create SavedScores.txt file
            writer = new BufferedWriter(new OutputStreamWriter(
                  new FileOutputStream(Main.GAME_ENVIR_DIRECTORY + Main.SAVE_FILE_NAME), "utf-8"));
            
            //Iterate through savedScores HashMap
            for (Map.Entry<String, HashMap<String, Integer>> gameList: Main.savedScores.entrySet()) {
            	
            	//Write game name and game regex into SavedScores.txt file
            	writer.write(Main.GAME_REGEX + gameList.getKey() + "\n");
            	
            	//Iterate through player and score HashMap
            	for (Map.Entry<String, Integer> playerScores : gameList.getValue().entrySet()) {     
            		
            		//Write player name, score, and name/score regex into SavedScores.txt file
                    writer.write(Main.PLAYER_SCORE_REGEX + playerScores.getKey());
                    writer.write(Main.PLAYER_SCORE_REGEX + playerScores.getValue().toString());
                    writer.write("\n");
            	}            	
            }
        } 
        
        //Failed to write to SavedScores.txt file
        catch (IOException ex) {
            System.out.println("Failed to write player scores out into SavedScores.txt file");
            return false;
        } finally {
        	
        	//Attempt to close writer
           try {writer.close();} catch (Exception ex) {/*do nothing*/}
        }
        return true;
	}
	
	//-----------------------other helper function-------------------------------------------------------------------------------------------	
	/* Helper function to print out savedScores HashMap; used for debugging purposes
	 */
	private static void printSavedScores() {
		//Iterate through savedScores HashMap
        for (Map.Entry<String, HashMap<String, Integer>> gameList: Main.savedScores.entrySet()) {
        	
        	//Print game name
        	System.out.print(gameList.getKey() + ": {");
        	
        	//Iterate through player and score HashMap
        	for (Map.Entry<String, Integer> playerScores : gameList.getValue().entrySet()) {     
        		
        		//Print player name and score
        		System.out.print("(" + playerScores.getKey() + " , ");
        		System.out.print(playerScores.getValue().toString() + ")");
        	} 
        	System.out.print("}\n");
        }
	}
//	public static void start() {
//		
//		/*
//		 * here needs to go the initial environment frame where players sign in and choose a game
//		 * it would probably go something like this:
//		 * GameFactory[] games = { new TicTacToeGame(), new BattleShipGame(), new ReversiGame, new CheckersGame };
//		 * 
//		 * GUI gameFrame = new GUI();
//		 * gameFrame.buildInitialFrame() --> this function needs to be defined and built
//		 * 
//		 * based on index chosen from menu of games on initial frame do:
//		 * gameFrame.buildFrame(games[gameIndex]) --> this will start the chosen game by calling the game's constructor
//		 * 
//		 */
//		
//		TicTacToeGame tttGame = new TicTacToeGame();
//		
//		GUI gameFrame = new GUI();
//		gameFrame.buildFrame(tttGame);
//	}
	
	
	

}
