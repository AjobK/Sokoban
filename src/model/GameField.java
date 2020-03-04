
package model;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
//import java.util.HashMap;
import java.util.Scanner;
import javafx.scene.input.KeyCode;
import model.Player;
//import model.Box;

public class GameField {
	// Level data
	char level[][];
	int y, x, levelNumber;
	
	// Level object data
	ArrayList<Player> players = new ArrayList<Player>();
	
	// Rest
	int stepCounter = 0;
	private Scanner sc;
	
	public GameField() {
		this.setLevel(100);
	}
	
	public char[][] getLevel() {
		return level;
	}

	public char getCharAt(int x, int y) {
		return level[x][y];
	}

	public int getLevelNumber() {
		return this.levelNumber;
	}
	
	public ArrayList<Player> getPlayers() {
		return this.players;
	}
	
	public void setCharAt(int x, int y, char Character) {
		level[x][y] = Character;
	}
	
	public void clearPlayerList() {
		this.players.clear();
	}
	
	public void setLevel(int levelNumber) {
		System.out.println("Start of run");
		// Export level
		System.out.println("Did get into setlevel");
		this.players.clear();
		
		this.levelNumber = levelNumber;

		try {
			File f = new File("levels/level1.sok");
			sc = new Scanner(f);
		} catch (FileNotFoundException e) {
			System.out.println("File not found 1");
		}

		// x & y of array
		x = 0;
		y = 1;
		
		// Create level string & Array width and height
		String tempString;
		while (sc.hasNextLine()) {
			tempString = sc.nextLine();
			System.out.println(tempString);
			if (x < tempString.length())
				x = tempString.length();
			y++;
		}
		
		// Create empty level tilemap
		level = new char[x][y];
		System.out.println("+-MAP-+\nx: " + x + "\ny: " + y + "\n+-----+");
		
		// Reset Scanner
		try {
			sc = new Scanner(new File("levels/level1.sok"));
		} catch (FileNotFoundException e) {
			System.out.println("File not found 2");
		}
		
		// Fill array
		tempString = "";
		int boxCount = 0;
		for (int yC = 0; yC < y - 1; yC++) {
			tempString = sc.nextLine();
			for (int xC = 0; xC < tempString.length(); xC++) {
				level[xC][yC] = tempString.charAt(xC);
				if (level[xC][yC] == '@' || level[xC][yC] == '+')
					players.add(new Player(xC, yC));
			}
		}
		System.out.println("Flawless run");
	}
	
	public int getLevelX() {
		return x;
	}
	
	public int getLevelY() {
		return y;
	}
	
	public int[] getPlayerCoords(int playerCount) {
		int[] passageArray = {this.players.get(playerCount).getXPos(), this.players.get(playerCount).getYPos()};
		return passageArray;
	}
	
	public int getSteps() {
		return this.stepCounter;
	}
	
	public int getPlayerWalkCount(int playerCount) {
		return this.players.get(playerCount).getWalkCount();
	}
	
	public String getPlayerDirection(int playerCount) {
		return this.players.get(playerCount).getDirection();
	}

	// To test game
	public void printLevel() {
		String tempString;
		System.out.println("Steps: " + stepCounter);
		for (int yC = 0; yC < y ;yC++) {
			tempString = "";
			for (int xC = 0; xC < x ;xC++) {
				tempString += level[xC][yC];
			}
			System.out.println(tempString);
		}
	}
	
	public void movePlayer(KeyCode key, int playerCount) {
		
		int direction = 0;
		
		KeyCode cUp;
		KeyCode cDown;
		KeyCode cLeft;
		KeyCode cRight;
		
		if (playerCount == 0) {
			cUp = KeyCode.W;
			cDown = KeyCode.S;
			cLeft = KeyCode.A;
			cRight = KeyCode.D;
		} else {
			cUp = KeyCode.UP;
			cDown = KeyCode.DOWN;
			cLeft = KeyCode.LEFT;
			cRight = KeyCode.RIGHT;
		}
		
		this.players.get(playerCount).setWalkCount((this.players.get(playerCount).getWalkCount() + 1) % 4);
		
		if (key == cUp) this.players.get(playerCount).setDirection("up");
		if (key == cDown) this.players.get(playerCount).setDirection("down");
		if (key == cLeft) this.players.get(playerCount).setDirection("left");
		if (key == cRight) this.players.get(playerCount).setDirection("right");
		
		char currentObject, nextObject, previousObject;

		// Direction check
		if (key == cLeft || key == cUp) direction = -1;
		if (key == cRight || key == cDown) direction = 1;
		
		// Player location
		currentObject = level[this.players.get(playerCount).getXPos()][this.players.get(playerCount).getYPos()];
		boolean previousPosCondition = true;

		if (key == cLeft || key == cRight) {
			// Object in front of player
			nextObject = level[this.players.get(playerCount).getXPos() + direction][this.players.get(playerCount).getYPos()];
			
			// Next position
			switch (nextObject) {
				case ' ':
					stepCounter++;
					this.players.get(playerCount).setXPos(this.players.get(playerCount).getXPos() + direction);
					this.setCharAt(this.players.get(playerCount).getXPos(), this.players.get(playerCount).getYPos(), '@');
					break;
				case '#':
					// Nothing happens. The player can not move.
					previousPosCondition = false;
					break;
				case '@':
					// Nothing happens. The player can not move.
					previousPosCondition = false;
					break;
				case '+':
					// Nothing happens. The player can not move.
					previousPosCondition = false;
					break;
				case '$':
					if (level[this.players.get(playerCount).getXPos() + (direction * 2)][this.players.get(playerCount).getYPos()] == ' ') {	
						stepCounter++;
						this.players.get(playerCount).setXPos(this.players.get(playerCount).getXPos() + direction);
						this.setCharAt(this.players.get(playerCount).getXPos(), this.players.get(playerCount).getYPos(), '@');
						this.setCharAt(this.players.get(playerCount).getXPos() + direction, this.players.get(playerCount).getYPos(), '$');
					} else if (level[this.players.get(playerCount).getXPos() + (direction * 2)][this.players.get(playerCount).getYPos()] == '.') {
						stepCounter++;
						this.players.get(playerCount).setXPos(this.players.get(playerCount).getXPos() + direction);
						this.setCharAt(this.players.get(playerCount).getXPos(), this.players.get(playerCount).getYPos(), '@');
						this.setCharAt(this.players.get(playerCount).getXPos() + direction, this.players.get(playerCount).getYPos(), '*');
					}
					break;
				case '*':
					if (level[this.players.get(playerCount).getXPos() + (direction * 2)][this.players.get(playerCount).getYPos()] == ' ') {	
						stepCounter++;
						this.players.get(playerCount).setXPos(this.players.get(playerCount).getXPos() + direction);
						this.setCharAt(this.players.get(playerCount).getXPos(), this.players.get(playerCount).getYPos(), '+');
						this.setCharAt(this.players.get(playerCount).getXPos() + direction, this.players.get(playerCount).getYPos(), '$');
					} else if (level[this.players.get(playerCount).getXPos() + (direction * 2)][this.players.get(playerCount).getYPos()] == '.') {
						stepCounter++;
						this.players.get(playerCount).setXPos(this.players.get(playerCount).getXPos() + direction);
						this.setCharAt(this.players.get(playerCount).getXPos(), this.players.get(playerCount).getYPos(), '+');
						this.setCharAt(this.players.get(playerCount).getXPos() + direction, this.players.get(playerCount).getYPos(), '*');
					}
					break;
				case '.':
					stepCounter++;
					this.players.get(playerCount).setXPos(this.players.get(playerCount).getXPos() + direction);
					this.setCharAt(this.players.get(playerCount).getXPos(), this.players.get(playerCount).getYPos(), '+');
					break;
			}
			
			// Previous position
			if (previousPosCondition) {
				switch (level[this.players.get(playerCount).getXPos() - direction][this.players.get(playerCount).getYPos()]) {
					case '@':
						this.setCharAt(this.players.get(playerCount).getXPos() - direction, this.players.get(playerCount).getYPos(), ' ');
						break;
					case '+':
						this.setCharAt(this.players.get(playerCount).getXPos() - direction, this.players.get(playerCount).getYPos(), '.');
						break;
				}
			}

		} else if (key == cUp || key == cDown) {

			// Object in front of player
			nextObject = level[this.players.get(playerCount).getXPos()][this.players.get(playerCount).getYPos() + direction];

			switch (nextObject) {
				case ' ':
					stepCounter++;
					this.players.get(playerCount).setYPos(this.players.get(playerCount).getYPos() + direction);
					this.setCharAt(this.players.get(playerCount).getXPos(), this.players.get(playerCount).getYPos(), '@');
					break;
				case '#':
					// Nothing happens. The player can not move.
					previousPosCondition = false;
					break;
				case '@':
					// Nothing happens. The player can not move.
					previousPosCondition = false;
					break;
				case '+':
					// Nothing happens. The player can not move.
					previousPosCondition = false;
					break;
				case '$':
					if (level[this.players.get(playerCount).getXPos()][this.players.get(playerCount).getYPos() + (direction * 2)] == ' ') {
						stepCounter++;
						this.players.get(playerCount).setYPos(this.players.get(playerCount).getYPos() + direction);
						this.setCharAt(this.players.get(playerCount).getXPos(), this.players.get(playerCount).getYPos(), '@');
						this.setCharAt(this.players.get(playerCount).getXPos(), this.players.get(playerCount).getYPos() + direction, '$');
					} else if (level[this.players.get(playerCount).getXPos()][this.players.get(playerCount).getYPos() + (direction * 2)] == '.') {
						stepCounter++;
						this.players.get(playerCount).setYPos(this.players.get(playerCount).getYPos() + direction);
						this.setCharAt(this.players.get(playerCount).getXPos(), this.players.get(playerCount).getYPos(), '@');
						this.setCharAt(this.players.get(playerCount).getXPos(), this.players.get(playerCount).getYPos() + direction, '*');
					}
					break;
				case '*':
					if (level[this.players.get(playerCount).getXPos()][this.players.get(playerCount).getYPos() + (direction * 2)] == ' ') {
						stepCounter++;
						this.players.get(playerCount).setYPos(this.players.get(playerCount).getYPos() + direction);
						this.setCharAt(this.players.get(playerCount).getXPos(), this.players.get(playerCount).getYPos(), '+');
						this.setCharAt(this.players.get(playerCount).getXPos(), this.players.get(playerCount).getYPos() + direction, '$');
					} else if (level[this.players.get(playerCount).getXPos()][this.players.get(playerCount).getYPos() + (direction * 2)] == '.') {
						stepCounter++;
						this.players.get(playerCount).setYPos(this.players.get(playerCount).getYPos() + direction);
						this.setCharAt(this.players.get(playerCount).getXPos(), this.players.get(playerCount).getYPos(), '+');
						this.setCharAt(this.players.get(playerCount).getXPos(), this.players.get(playerCount).getYPos() + direction, '*');
					}
					break;
				case '.':
					stepCounter++;
					this.players.get(playerCount).setYPos(this.players.get(playerCount).getYPos() + direction);
					this.setCharAt(this.players.get(playerCount).getXPos(), this.players.get(playerCount).getYPos(), '+');
					break;
			}
			
			if (previousPosCondition) {
				// Previous position
				switch (level[this.players.get(playerCount).getXPos()][this.players.get(playerCount).getYPos() - direction]) {
					case '@':
						this.setCharAt(this.players.get(playerCount).getXPos(), this.players.get(playerCount).getYPos() - direction, ' ');
						break;
					case '+':
						this.setCharAt(this.players.get(playerCount).getXPos(), this.players.get(playerCount).getYPos() - direction, '.');
						break;
					default:

						break;
				}
			}
		}
	}
}
