
package model;

public class Player {
    int xPos;
    int yPos;
	int walkCount = 0;
	String direction = "down";
    String name;
    boolean onGoal;
    
    public Player(int xPos, int yPos, String name) {
        this.xPos = xPos;
        this.yPos = yPos;
        this.name = name;
    }
	
	public Player(int xPos, int yPos) {
		this.xPos = xPos;
		this.yPos = yPos;
	}
    
    public void setName(String name) {
        this.name = name;
    }
    
    public boolean moveUp() {
        return false;
    }
    
    public boolean moveDown() {
        return false;
    }
    
    public boolean moveLeft() {
        return false;
    }
    
    public boolean moveRight() {
        return false;
    }
    
    public int getXPos() {
        return this.xPos;
    }
    
    public int getYPos() {
        return this.yPos;
    }
	
	public int getWalkCount() {
		return this.walkCount;
	}
	
	public void setWalkCount(int count) {
		this.walkCount = count;
	}
	
	public String getDirection() {
		return this.direction;
	}
	
	public void setDirection(String direction) {
		this.direction = direction;
	}
    
    public void setXPos(int aX) {
		this.xPos = aX;
    }
    
    public void setYPos(int aY) {
        this.yPos = aY;
    }
}
