package com.me.numbersgame;

public class Number {
	String filePath;
	private String filePath_selected;
	int number;
	private int j; //index
	private int i; // index
	int locX, locY;
	int spriteWidth, spriteHeight;
	private boolean selected;
	
	public Number(int number, String filePath, String filePath_selected) {
		this.number = number;
		this.filePath = filePath;
		this.filePath_selected = filePath_selected;
		locX = locY = spriteWidth = spriteHeight = 0;
		setSelected(false);
	}
	
	
	public String getFilePath() {
		return filePath;
	}

	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}

	public int getNumber() {
		return number;
	}

	public void setNumber(int number) {
		this.number = number;
	}

	public int getLocX() {
		return locX;
	}

	public void setLocX(int locX) {
		this.locX = locX;
	}

	public int getLocY() {
		return locY;
	}

	public void setLocY(int locY) {
		this.locY = locY;
	}

	public int getSpriteWidth() {
		return spriteWidth;
	}

	public void setSpriteWidth(int spriteWidth) {
		this.spriteWidth = spriteWidth;
	}

	public int getSpriteHeight() {
		return spriteHeight;
	}

	public void setSpriteHeight(int spriteHeight) {
		this.spriteHeight = spriteHeight;
	}

	public String getFilePath_selected() {
		return filePath_selected;
	}

	public void setFilePath_selected(String filePath_selected) {
		this.filePath_selected = filePath_selected;
	}

	public boolean isSelected() {
		return selected;
	}

	public void setSelected(boolean selected) {
		this.selected = selected;
	}


	public int getI() {
		return i;
	}


	public void setI(int i) {
		this.i = i;
	}


	public int getJ() {
		return j;
	}


	public void setJ(int j) {
		this.j = j;
	}
}
