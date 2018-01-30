
package model;

public class Box {
    int xPos;
    int yPos;
    boolean onGoal;
    
    public Box(int xPos, int yPos) {
        this.xPos = xPos;
        this.yPos = yPos;
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
}
