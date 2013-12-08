package com.me.numbersgame.screen;

import java.util.ArrayList;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Texture.TextureFilter;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.me.numbersgame.Number;
import com.me.numbersgame.NumberResources;
import com.me.numbersgame.NumbersGame;

public class GameScreen implements Screen, InputProcessor {
	
	public static enum Rule {
		ODD_SUM, ODD_PRODUCT, EVEN_SUM, EVEN_PRODUCT
	}
	
	public static float SCALE;
	public static final int VIRTUAL_WIDTH = 320;
    public static final int VIRTUAL_HEIGHT = 480;
	
	public static SpriteBatch spriteBatch;
	private Texture texture;
	private Sprite sprite[][], spriteSelected[][];
	//private Sprite sprOddSum, sprOddProduct, sprEvenSum, sprEvenProduct;
	private OrthographicCamera camera;
	NumbersGame game;
	private ArrayList<Number> selectedNumbers = new ArrayList<Number>();
	private Number numbers[][];
	
	private BitmapFont lblRuleFont, lblScore, lblTime, lblCountdown, lblOperation;
	private Rule rule;
	private Color paleBlue = new Color(.45f, .54f, .73f, 1); // 120, 138, 188, 1; 
	private Color royalBlue1 = new Color(.28f, .46f, 1f, 1); // 72 118 255
	
	int time, readyCount;
	String drawTime, operation;
	
	// location of the numbers
	int locX = 65, locY = 100;
	
	public void loadFont() {
		SCALE = 1.0f * Gdx.graphics.getWidth() / VIRTUAL_WIDTH ;

	    if (SCALE < 1)
	        SCALE = 1;

	    FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal("font/GOTHIC.ttf"));

	    //12 is the size i want to give for the font on all devices
	    lblRuleFont = generator.generateFont((int) (28 * SCALE));
	    //Aplly the inverse scale of what Libgdx will do
	    lblRuleFont.setScale((float) (1.0 / SCALE), (float) (-1.0 / SCALE));
	    lblRuleFont.setColor(paleBlue);
	    //Apply Linear filtering; best choice to keep everything looking sharp
	    lblRuleFont.getRegion().getTexture().setFilter(TextureFilter.Linear, TextureFilter.Linear);
	    
	    lblScore = generator.generateFont((int) (20 * SCALE));
	    lblScore.setScale((float) (1.0 / SCALE), (float) (-1.0 / SCALE));
	    lblScore.getRegion().getTexture().setFilter(TextureFilter.Linear, TextureFilter.Linear);
	    //lblScore.setColor(royalBlue1);
	    
	    lblTime = generator.generateFont((int) (30 * SCALE));
	    lblTime.setScale((float) (1.0 / SCALE), (float) (-1.0 / SCALE));
	    lblTime.getRegion().getTexture().setFilter(TextureFilter.Linear, TextureFilter.Linear);
	    lblTime.setColor(paleBlue);
	    
	    lblCountdown = generator.generateFont((int) (150 * SCALE));
	    lblCountdown.setScale((float) (1.0 / SCALE), (float) (-1.0 / SCALE));
	    lblCountdown.getRegion().getTexture().setFilter(TextureFilter.Linear, TextureFilter.Linear);
	    //lblCountdown.setColor(paleBlue);
	    
	    lblOperation = generator.generateFont((int) (28 * SCALE));
	    lblOperation.setScale((float) (1.0 / SCALE), (float) (-1.0 / SCALE));
	    lblOperation.getRegion().getTexture().setFilter(TextureFilter.Linear, TextureFilter.Linear);
	    lblOperation.setColor(paleBlue);
	    
	    generator.dispose();
	}
	
	public GameScreen(NumbersGame game) {
		this.game = game;
		
		loadFont();
		
		rule = Rule.ODD_PRODUCT;
		
		camera = new OrthographicCamera(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		// the origin for drawing is at bottom left, this flips it to upper left to be used by spriteBatch
		camera.setToOrtho(true);  
		
		
		/*
		sprOddSum = new Sprite(new Texture(NumberResources.oddSum));
		sprEvenSum = new Sprite(new Texture(NumberResources.evenSum));
		sprOddProduct = new Sprite(new Texture(NumberResources.oddProduct));
		sprEvenProduct = new Sprite(new Texture(NumberResources.evenProduct));
		*/
		/*
		int productLocX = 100, LocY = 380 + locY;
		int sumLocX = 110;
		sprOddSum.setBounds(sumLocX, LocY, 280, 60);
		sprEvenSum.setBounds(sumLocX, LocY, 280, 60);
		sprOddProduct.setBounds(productLocX, LocY, 280, 60);
		sprEvenProduct.setBounds(productLocX, LocY, 280, 60);
		
		sprOddSum.flip(false, true);
		sprEvenSum.flip(false, true);
		sprOddProduct.flip(false, true);
		sprEvenProduct.flip(false, true);
		*/
		numbers = new Number[4][4];
		sprite = new Sprite[4][4];
		spriteSelected = new Sprite[4][4];
		
		
		spriteBatch = new SpriteBatch();
		// sprite batch will use the camera
		spriteBatch.setProjectionMatrix(camera.combined);
		
		//sprite.setOrigin(sprite.getWidth()/2, sprite.getHeight()/2);
	}
	
	private void initialize() {
		if(texture != null)
			texture.dispose();
		
		int spriteSize = 70;
		
		for (int i = 0; i < 4; i++) {
			for (int j = 0; j < 4; j++) {
				numbers[j][i] = NumberResources.randomNumber();
				numbers[j][i].setJ(j);
				numbers[j][i].setI(i);
			}
		}
		
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
		
		time = 60000;
		drawTime = "60:00";
		readyCount = 4000;
		operation = "";
	}
	
	
	@Override
	public void render(float delta) {
		update(delta);
		draw(delta);
	}
	
	private void update(float delta) {
		readyCount -= 15;
		if(readyCount/1000 <= 0) {
			time -= 15;
			if(time < 0)
				time = 0;
			drawTime = (time/1000) + ":" + (time%1000)/10;
		}
		
	}

	private void draw(float delta) {
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
		
		lblRuleFont.draw(spriteBatch, currentRule(rule), 
				Gdx.graphics.getWidth()/2 - lblRuleFont.getBounds(currentRule(rule)).width/2, locY + 380);
		
		lblScore.draw(spriteBatch, "Score: 2314", locX, locY - 25); // locY - 25
		lblTime.draw(spriteBatch, drawTime, Gdx.graphics.getWidth()/2 - lblTime.getBounds(drawTime).width/2, locY + 430);
		if(readyCount/1000 > 0)
			lblCountdown.draw(spriteBatch, (readyCount/1000)+"", 
					Gdx.graphics.getWidth()/2 - lblCountdown.getBounds((readyCount/1000)+"").width/2, locY + 120);
		
		lblOperation.draw(spriteBatch, operation, 
				Gdx.graphics.getWidth()/2 - lblOperation.getBounds(operation).width/2, locY - 70);
		
		spriteBatch.end();
	}
	
	public String currentRule(Rule rule) {
		switch(rule) {
			case ODD_SUM: return "Sum of Odd";
			case ODD_PRODUCT: return "Product of Odd";
			case EVEN_SUM: return "Sum of Even";
			case EVEN_PRODUCT: return "Product of Even";
		}
		return null;
	}

	@Override
	public void resize(int width, int height) {
		
	}

	@Override
	public void show() {
		Gdx.input.setInputProcessor(this);
		initialize();
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
		
		if(readyCount/1000 <= 0)
			for (int i = 0; i < numbers.length; i++) {
				for (int j = 0; j < numbers[i].length; j++) {
					if(screenX >= numbers[j][i].getLocX() && screenX <= numbers[j][i].getLocX() + numbers[j][i].getSpriteWidth()
							&& screenY >= numbers[j][i].getLocY() && screenY <= numbers[j][i].getLocY() + numbers[j][i].getSpriteHeight()) {
						selectedNumbers.add(numbers[j][i]);
						numbers[j][i].setSelected(true);
						
						operation = numbers[j][i].getNumber() + "";
						if(numbers[j][i].getJ() == 0 && numbers[j][i].getI() == 0)
							rule = Rule.ODD_SUM;
						else if(numbers[j][i].getJ() == 1 && numbers[j][i].getI() == 0)
							rule = Rule.ODD_PRODUCT;
						else if(numbers[j][i].getJ() == 2 && numbers[j][i].getI() == 0)
							rule = Rule.EVEN_SUM;
						else if(numbers[j][i].getJ() == 3 && numbers[j][i].getI() == 0)
							rule = Rule.EVEN_PRODUCT;
					}
				}
			}
		
		return false;
	}
	
	private void addPoints() {
		
	}
	
	private void compute() {
		int sum = 0, product = 1;
		for (Number num : selectedNumbers) {
			sum += num.getNumber();
			product *= num.getNumber();
		}
		switch(rule) {
			case ODD_SUM: 
				if(sum % 2 != 0) {
					addPoints();
				}
				break;
			case ODD_PRODUCT:
				if(product % 2 != 0) {
					addPoints();
				}
				break;
			case EVEN_SUM:
				if(sum % 2 == 0) {
					addPoints();
				}
				break;
			case EVEN_PRODUCT:
				if(product % 2 == 0) {
					addPoints();
				}
				break;
		}
	}

	@Override
	public boolean touchUp(int screenX, int screenY, int pointer, int button) {
		for (int i = 0; i < numbers.length; i++) 
			for (int j = 0; j < numbers[i].length; j++) 
					numbers[j][i].setSelected(false);
		
		
		if(selectedNumbers.size() >= 3 && selectedNumbers.size() <= 5) {
			compute();
		}
		
		selectedNumbers.clear();
		operation = "";
		
		return false;
	}

	@Override
	public boolean touchDragged(int screenX, int screenY, int pointer) {
		
		if(readyCount/1000 <= 0)
			if(selectedNumbers.size() < 5)
				for (int i = 0; i < numbers.length; i++) {
					for (int j = 0; j < numbers[i].length; j++) {
						
						if(screenX >= numbers[j][i].getLocX() && screenX <= numbers[j][i].getLocX() + numbers[j][i].getSpriteWidth()
								&& screenY >= numbers[j][i].getLocY() && screenY <= numbers[j][i].getLocY() + numbers[j][i].getSpriteHeight()) {
							
							if(!numbers[j][i].isSelected()){
								if(selectedNumbers.size() > 0) {
									Number num = selectedNumbers.get(selectedNumbers.size()-1);
									
									if(num.getJ()-1 == numbers[j][i].getJ() && num.getI() == numbers[j][i].getI() ||
											num.getJ()+1 == numbers[j][i].getJ() && num.getI() == numbers[j][i].getI() ||
											num.getJ() == numbers[j][i].getJ() && num.getI()-1 == numbers[j][i].getI() ||
											num.getJ() == numbers[j][i].getJ() && num.getI()+1 == numbers[j][i].getI()) {
										selectedNumbers.add(numbers[j][i]);
										numbers[j][i].setSelected(true);
										
										if(rule == Rule.EVEN_SUM || rule == Rule.ODD_SUM)
											operation += " + " + numbers[j][i].getNumber() ;
										else
											operation += " x " + numbers[j][i].getNumber();
									}
								}
							}
						}
					}
				}
		
		return false;
	}
	
	private int getResult() {
		int sum = 0, product = 1;
		for (Number num : selectedNumbers) {
			if(rule == Rule.ODD_SUM || rule == Rule.EVEN_SUM)
				sum += num.getNumber();
			else
				product *= num.getNumber();
		}
		
		if(rule == Rule.ODD_SUM || rule == Rule.EVEN_SUM)
			return sum;
		return product;
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
