
package model;

public class Settings {
	private int stageWidth = 1280;
	private int stageHeight = 720;
	private int masterVolume = 100;
	
	public void setStageWidth(int width) {
		this.stageWidth = width;
	}
	
	public void setStageHeight(int height) {
		this.stageHeight = height;
	}
	
	public void setMasterVolume(int volume) {
		this.masterVolume = volume;
	}
	
	public int getStageWidth() {
		return this.stageWidth;
	}
	
	public int getStageHeight() {
		return this.stageHeight;		
	}
	
	public int getMasterVolume() {
		return this.masterVolume;
	}
}
