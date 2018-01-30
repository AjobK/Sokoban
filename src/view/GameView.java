
package view;

import controller.Controller;
import java.util.ArrayList;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.Pane;
import model.GameField;
import model.Player;

public class GameView {
	Controller c;
	Stage primaryStage;
	Scene scene;
	Pane root;
	
    public GameView(Controller c, Stage primaryStage) {
		this.c = c;
		this.primaryStage = primaryStage;
	}
	
	public void showGame(int width, int height, char[][] level, ArrayList<Player> players) {
		
		primaryStage.setHeight(height * 32 + 8);
		primaryStage.setWidth(width * 32 + 16);
		
		root = new Pane();
		scene = new Scene(root, width * 32, height * 32, Color.BEIGE);
		
		this.showGameMap(width, height, level, players);
		
		primaryStage.setScene(scene);
		primaryStage.setTitle("Sokoban");
		primaryStage.show();
		
		scene.setOnKeyPressed(new EventHandler<KeyEvent>() {
			@Override
			public void handle(KeyEvent event) {
				try {
					if (event.getCode().isArrowKey()) {
						c.reactToMovement(event.getCode(), 1);
					} else if (event.getCode() == KeyCode.W || event.getCode() == KeyCode.S || event.getCode() == KeyCode.A || event.getCode() == KeyCode.D) {
						c.reactToMovement(event.getCode(), 0);
					}else if (event.getCode() == KeyCode.R) {
						c.restartGame();
					} else if (event.getCode().isDigitKey()) {
						// Ugly, but only for testing purposes
						switch(event.getCode().toString()) {
							case "DIGIT1":
								c.startSpecificGame(1);
								break;
							case "DIGIT2":
								c.startSpecificGame(2);
								break;
							case "DIGIT3":
								c.startSpecificGame(3);
								break;
							case "DIGIT4":
								c.startSpecificGame(4);
								break;
							case "DIGIT5":
								c.startSpecificGame(5);
								break;
							case "DIGIT6":
								c.startSpecificGame(6);
								break;
							case "DIGIT7":
								c.startSpecificGame(7);
								break;
							case "DIGIT8":
								c.startSpecificGame(8);
								break;
							case "DIGIT9":
								c.startSpecificGame(9);
								break;
						}
					}
				} catch (IndexOutOfBoundsException e) {
					System.out.println("Key not working");
					System.out.println("Level/Player not found");
				}
			}
		});
		
	}
	
	public void showGameMap(int width, int height, char[][] level, ArrayList<Player> players) {
		root.getChildren().clear();
		Image image;
		ImageView imgBlock;
		
		for (int yC = 0; yC < height; yC++) {
			for (int xC = 0; xC < width; xC++) {
				switch(level[xC][yC]) {
					case '@':
						image = new Image("images/air.png");
						break;
					case '$':
						image = new Image("images/box.png");
						break;
					case '#':
						// Readying the integer for a set wall
						int theWall = 0;
						
						//								  TOP				  RIGHT				    BOTTOM				   LEFT
						//char[] surroundingWalls = {level[xC + 1][yC], level[xC + 2][yC + 1], level[xC + 1][yC + 2], level[xC][yC + 1]};
						
						
						char[] surroundingWalls = new char[4];
						
						// Top
						if (yC <= 0) {
							surroundingWalls[0] = ' ';
						} else {
							surroundingWalls[0] = level[xC][yC - 1];
						}
						
						// Right
						if (xC >= width - 1) {
							surroundingWalls[1] = ' ';
						} else {
							surroundingWalls[1] = level[xC + 1][yC];
						}
						
						// Bottom
						if (yC >= height - 1) {
							surroundingWalls[2] = ' ';
						} else {
							surroundingWalls[2] = level[xC][yC + 1];
						}
						
						// Left
						if (xC <= 0) {
							surroundingWalls[3] = ' ';
						} else {
							surroundingWalls[3] = level[xC - 1][yC];
						}
						
						// . stands for a surrounding wall
						// ! stands for no surrounding wall
						
						// Example wall String: ....
						// In this situation it would mean the box has 4 surrounding boxes
						// First character in string is above the wall
						// Second character in string is on the right side of the wall
						// Third character in string is under the wall
						// Fourth character in string is on the left side of the wall
						
						String wallString = "";
						
						for (char i : surroundingWalls) {
							if (i == '#')
								wallString += ".";
							else
								wallString += "!";
						}
						
						// Individual spots
						switch (wallString) {
							case "!..!":
								// Upperleft corner
								theWall = 0;
								break;
							case "!!..":
								// Upperright corner
								theWall = 1;
								break;
							case "..!!":
								// Lowerleft corner
								theWall = 2;
								break;
							case ".!!.":
								// Lowerright corner
								theWall = 3;
								break;
							case "!.!.":
								// Vertical open
								theWall = 4;
								break;
							case ".!.!":
								// Horizontal open
								theWall = 30; // Does not work when I enter 5 for some reason
								break;
							case "....":
								// Upperview only
								theWall = 12;
								break;
							case "!!!!":
								// Seperate block
								theWall = 7;
								break;
							case "!...":
								// Three cross covered top
								theWall = 8;
								break;
							case ".!..":
								// Three cross covered right
								theWall = 9;
								break;
							case "..!.":
								// Three cross covered bottom
								theWall = 10;
								break;
							case "...!":
								// Three cross covered left
								theWall = 11;
								break;
							case ".!!!":
								// One open top
								theWall = 15;
								break;
							case "!.!!":
								// One open right
								theWall = 13;
								break;
							case "!!.!":
								// One open bottom
								theWall = 16;
								break;
							case "!!!.":
								// One open left
								theWall = 14;
								break;
							default:
								// Default
								theWall = 7;
								break;
						}
						
						image = new Image("images/walls/" + theWall + ".png");
						break;
					case '+':
						image = new Image("images/goal.png");
						break;
					case '*':
						image = new Image("images/boxongoal.png");
						break;
					case '.':
						image = new Image("images/goal.png");
						break;
					case ' ':
						image = new Image("images/air.png");
						break;
					default:
						image = new Image("images/air.png");
						break;
				}
				
				imgBlock = new ImageView(image);
				imgBlock.setX(xC * 32);
				imgBlock.setY(yC * 32);
				
				root.getChildren().add(imgBlock);
			}
			
			// Draft
			if (players.size() > 1) {
				if (players.get(0).getYPos() < players.get(1).getYPos()) {
					// Player 1
					image = new Image("images/character" + 0 + "/" + players.get(0).getDirection() + "/" + players.get(0).getWalkCount() + ".png");

					imgBlock = new ImageView(image);
					imgBlock.setX(players.get(0).getXPos() * 32 - 2);
					imgBlock.setY(players.get(0).getYPos() * 32 - 16);

					root.getChildren().add(imgBlock);

					// Player 2
					image = new Image("images/character" + 1 + "/" + players.get(1).getDirection() + "/" + players.get(1).getWalkCount() + ".png");

					imgBlock = new ImageView(image);
					imgBlock.setX(players.get(1).getXPos() * 32 - 2);
					imgBlock.setY(players.get(1).getYPos() * 32 - 16);

					root.getChildren().add(imgBlock);
				} else if (players.get(0).getYPos() == players.get(1).getYPos()) {
					if (players.get(0).getXPos() < players.get(1).getXPos()) {
						// Player 1
						image = new Image("images/character" + 0 + "/" + players.get(0).getDirection() + "/" + players.get(0).getWalkCount() + ".png");

						imgBlock = new ImageView(image);
						imgBlock.setX(players.get(0).getXPos() * 32 - 2);
						imgBlock.setY(players.get(0).getYPos() * 32 - 16);

						root.getChildren().add(imgBlock);

						// Player 2
						image = new Image("images/character" + 1 + "/" + players.get(1).getDirection() + "/" + players.get(1).getWalkCount() + ".png");

						imgBlock = new ImageView(image);
						imgBlock.setX(players.get(1).getXPos() * 32 - 2);
						imgBlock.setY(players.get(1).getYPos() * 32 - 16);

						root.getChildren().add(imgBlock);
					} else {
						// Player 2
						image = new Image("images/character" + 1 + "/" + players.get(1).getDirection() + "/" + players.get(1).getWalkCount() + ".png");

						imgBlock = new ImageView(image);
						imgBlock.setX(players.get(1).getXPos() * 32 - 2);
						imgBlock.setY(players.get(1).getYPos() * 32 - 16);

						root.getChildren().add(imgBlock);

						// Player 1
						image = new Image("images/character" + 0 + "/" + players.get(0).getDirection() + "/" + players.get(0).getWalkCount() + ".png");

						imgBlock = new ImageView(image);
						imgBlock.setX(players.get(0).getXPos() * 32 - 2);
						imgBlock.setY(players.get(0).getYPos() * 32 - 16);

						root.getChildren().add(imgBlock);
					}
				} else {
					// Player 2
					image = new Image("images/character" + 1 + "/" + players.get(1).getDirection() + "/" + players.get(1).getWalkCount() + ".png");

					imgBlock = new ImageView(image);
					imgBlock.setX(players.get(1).getXPos() * 32 - 2);
					imgBlock.setY(players.get(1).getYPos() * 32 - 16);

					root.getChildren().add(imgBlock);

					// Player 1
					image = new Image("images/character" + 0 + "/" + players.get(0).getDirection() + "/" + players.get(0).getWalkCount() + ".png");

					imgBlock = new ImageView(image);
					imgBlock.setX(players.get(0).getXPos() * 32 - 2);
					imgBlock.setY(players.get(0).getYPos() * 32 - 16);

					root.getChildren().add(imgBlock);
				}
			} else {
				// Player 1
				image = new Image("images/character" + 0 + "/" + players.get(0).getDirection() + "/" + players.get(0).getWalkCount() + ".png");

				imgBlock = new ImageView(image);
				imgBlock.setX(players.get(0).getXPos() * 32 - 2);
				imgBlock.setY(players.get(0).getYPos() * 32 - 16);

				root.getChildren().add(imgBlock);
			}
		}
	}
	
}
