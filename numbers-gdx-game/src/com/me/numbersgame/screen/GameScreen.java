package com.me.numbersgame.screen;

import java.util.ArrayList;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.me.numbersgame.NumberResources;
import com.me.numbersgame.NumbersGame;
import com.me.numbersgame.Number;

public class GameScreen implements Screen, InputProcessor {

	public static SpriteBatch spriteBatch;
	private Texture texture;
	private Sprite sprite[][], spriteSelected[][];
	private OrthographicCamera camera;
	NumbersGame game;
	private ArrayList<Number> selectedNumbers = new ArrayList<Number>();
	private Number numbers[][];
	
	// location of the numbers
	int locX = 65, locY = 50;
	
	public GameScreen(NumbersGame game) {
		this.game = game;
		
		
		camera = new OrthographicCamera(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		// the origin for drawing is at bottom left, this flips it to upper left to be used by spriteBatch
		camera.setToOrtho(true);  
		
		int spriteSize = 70;
		
		numbers = new Number[4][4];
		for (int i = 0; i < 4; i++) {
			for (int j = 0; j < 4; j++) {
				numbers[j][i] = NumberResources.randomNumber();
				numbers[j][i].setJ(j);
				numbers[j][i].setI(i);
				
			}
		}
		
		sprite = new Sprite[4][4];
		spriteSelected = new Sprite[4][4];
		for (int i = 0; i < 4; i++) {
			for(int j = 0; j < 4; j++) {
				texture = new Texture(numbers[j][i].getFilePath());
				numbers[j][i].setSpriteWidth(spriteSize);
				numbers[j][i].setSpriteHeight(spriteSize);
				numbers[j][i].setLocX(locX + j*spriteSize + j*25);
				numbers[j][i].setLocY(locY+ i*spriteSize + i*25);
				sprite[j][i] = new Sprite(texture);
				sprite[j][i].flip(false, true); // sprite is flipped along with the camera, so we flip the Y of the sprite
				sprite[j][i].setBounds(locX + j*spriteSize + j*25, locY+ i*spriteSize + i*25 , spriteSize, spriteSize);
				
				spriteSelected[j][i] = new Sprite(new Texture(numbers[j][i].getFilePath_selected()));
				spriteSelected[j][i].flip(false, true); // sprite is flipped along with the camera, so we flip the Y of the sprite
				spriteSelected[j][i].setBounds(locX + j*spriteSize + j*25, locY+ i*spriteSize + i*25 , spriteSize, spriteSize);
				
			}
		}
		
		spriteBatch = new SpriteBatch();
		// sprite batch will use the camera
		spriteBatch.setProjectionMatrix(camera.combined);
		
		
		//sprite.setOrigin(sprite.getWidth()/2, sprite.getHeight()/2);
	}
	
	@Override
	public void render(float delta) {
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		
		spriteBatch.begin();
		for(int i = 0; i < 4; i++)
			for(int j = 0; j < 4; j++) {
				if(numbers[j][i].isSelected())
					spriteSelected[j][i].draw(spriteBatch);
				else
					sprite[j][i].draw(spriteBatch);
			}
		
		spriteBatch.end();
	}

	@Override
	public void resize(int width, int height) {
		
	}

	@Override
	public void show() {
		Gdx.input.setInputProcessor(this);
	}

	@Override
	public void hide() {
		
	}

	@Override
	public void pause() {
		
	}

	@Override
	public void resume() {
		
	}

	@Override
	public void dispose() {
		spriteBatch.dispose();
		texture.dispose();
	}

	@Override
	public boolean keyDown(int keycode) {
		return false;
	}

	@Override
	public boolean keyUp(int keycode) {
		return false;
	}

	@Override
	public boolean keyTyped(char character) {
		return false;
	}

	@Override
	public boolean touchDown(int screenX, int screenY, int pointer, int button) {
		//Gdx.app.log("", "x: " + screenX + " ; y: " + screenY);
		
		for (int i = 0; i < numbers.length; i++) {
			for (int j = 0; j < numbers[i].length; j++) {
				if(screenX >= numbers[j][i].getLocX() && screenX <= numbers[j][i].getLocX() + numbers[j][i].getSpriteWidth()
						&& screenY >= numbers[j][i].getLocY() && screenY <= numbers[j][i].getLocY() + numbers[j][i].getSpriteHeight()) {
					selectedNumbers.add(numbers[j][i]);
					numbers[j][i].setSelected(true);
					
				}
			}
		}
		
		return false;
	}

	@Override
	public boolean touchUp(int screenX, int screenY, int pointer, int button) {
		for (int i = 0; i < numbers.length; i++) {
			for (int j = 0; j < numbers[i].length; j++) {
				//if(screenX >= numbers[j][i].getLocX() && screenX <= numbers[j][i].getLocX() + numbers[j][i].getSpriteWidth()
				//		&& screenY >= numbers[j][i].getLocY() && screenY <= numbers[j][i].getLocY() + numbers[j][i].getSpriteHeight())
					numbers[j][i].setSelected(false);
					selectedNumbers.clear();
			}
		}
		return false;
	}

	@Override
	public boolean touchDragged(int screenX, int screenY, int pointer) {
		
		for (int i = 0; i < numbers.length; i++) {
			for (int j = 0; j < numbers[i].length; j++) {
				
				if(screenX >= numbers[j][i].getLocX() && screenX <= numbers[j][i].getLocX() + numbers[j][i].getSpriteWidth()
						&& screenY >= numbers[j][i].getLocY() && screenY <= numbers[j][i].getLocY() + numbers[j][i].getSpriteHeight()) {
					
					if(!numbers[j][i].isSelected()){
						if(selectedNumbers.size() > 0) {
							Number num = selectedNumbers.get(selectedNumbers.size()-1);
							/*
							if(selectedNumbers.size() == 1 && 
									(num.getJ()-1 == numbers[j][i].getJ() && num.getI() == numbers[j][i].getI() ||
									num.getJ()+1 == numbers[j][i].getJ() && num.getI() == numbers[j][i].getI() ||
									num.getJ() == numbers[j][i].getJ() && num.getI()-1 == numbers[j][i].getI() ||
									num.getJ() == numbers[j][i].getJ() && num.getI()+1 == numbers[j][i].getI()) ) {
								selectedNumbers.add(numbers[j][i]);
								numbers[j][i].setSelected(true);
							}
							*/
					
						
							//Number num = selectedNumbers.get(selectedNumbers.size()-1);
							
							if(num.getJ()-1 == numbers[j][i].getJ() && num.getI() == numbers[j][i].getI() ||
									num.getJ()+1 == numbers[j][i].getJ() && num.getI() == numbers[j][i].getI() ||
									num.getJ() == numbers[j][i].getJ() && num.getI()-1 == numbers[j][i].getI() ||
									num.getJ() == numbers[j][i].getJ() && num.getI()+1 == numbers[j][i].getI()) {
								selectedNumbers.add(numbers[j][i]);
								numbers[j][i].setSelected(true);
							}

						}
						
					}
				}
			}
		}
		
		return false;
	}

	@Override
	public boolean mouseMoved(int screenX, int screenY) {
		return false;
	}

	@Override
	public boolean scrolled(int amount) {
		return false;
	}

}
