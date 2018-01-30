
package controller;

import javafx.scene.input.KeyCode;
import javafx.stage.Stage;
import model.GameField;
import view.GameView;
import view.StartView;

public class Controller {
    
    GameField gameField = new GameField();
	GameView gameView;
	StartView startView;
    
    public Controller(Stage primaryStage) {
		this.startView = new StartView(this, primaryStage);
		this.gameView = new GameView(this, primaryStage);
		
		this.startGame();
	}
	
	public void reactToMovement(KeyCode key, int playerCount) {
		this.gameField.movePlayer(key, playerCount);	
		this.gameView.showGameMap(gameField.getLevelX(), gameField.getLevelY(), gameField.getLevel(), gameField.getPlayers());
		this.gameField.printLevel();
	}
	
	public void startGame() {
		this.gameView.showGame(gameField.getLevelX(), gameField.getLevelY(), gameField.getLevel(), gameField.getPlayers());
	}
	
	public void restartGame() {
		this.gameField.setLevel(this.gameField.getLevelNumber());
		this.gameView.showGame(gameField.getLevelX(), gameField.getLevelY(), gameField.getLevel(), gameField.getPlayers());
	}
	
	public void startSpecificGame(int levelNumber) {
		this.gameField.setLevel(levelNumber);
		this.gameView.showGame(gameField.getLevelX(), gameField.getLevelY(), gameField.getLevel(), gameField.getPlayers());
	}
}