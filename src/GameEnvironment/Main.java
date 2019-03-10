package GameEnvironment;

import TicTacToe.TicTacToeGame;

public class Main {

	public static void start() {
		
		/*
		 * here needs to go the initial environment frame where players sign in and choose a game
		 * it would probably go something like this:
		 * GameFactory[] games = { new TicTacToeGame(), new BattleShipGame(), new ReversiGame, new CheckersGame };
		 * 
		 * GUI gameFrame = new GUI();
		 * gameFrame.buildInitialFrame() --> this function needs to be defined and built
		 * 
		 * based on index chosen from menu of games on initial frame do:
		 * gameFrame.buildFrame(games[gameIndex]) --> this will start the chosen game by calling the game's constructor
		 * 
		 */
		
		TicTacToeGame tttGame = new TicTacToeGame();
		
		GUI gameFrame = new GUI();
		gameFrame.buildFrame(tttGame);
	}
	
	
	public static void main(String[] args) {
		start();
	}

}
