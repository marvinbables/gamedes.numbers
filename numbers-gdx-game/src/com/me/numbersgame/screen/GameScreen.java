package com.me.numbersgame.screen;

import java.util.ArrayList;
import java.util.Random;

import aurelienribon.tweenengine.Tween;
import aurelienribon.tweenengine.TweenCallback;
import aurelienribon.tweenengine.TweenEquation;
import aurelienribon.tweenengine.TweenEquations;

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
import com.me.numbersgame.tween.SpriteAccessor;

public class GameScreen implements Screen, InputProcessor {
	
	public static enum Rule {
		ODD_SUM, ODD_PRODUCT, EVEN_SUM, EVEN_PRODUCT
	}
	
	public static float SCALE;
	public static final int VIRTUAL_WIDTH = 320;
    public static final int VIRTUAL_HEIGHT = 480;
	
	public static SpriteBatch spriteBatch;
	private Texture texture;
	private Sprite sprite[][], spriteSelected[][], spriteWrong[][];
	//private Sprite sprOddSum, sprOddProduct, sprEvenSum, sprEvenProduct;
	private OrthographicCamera camera;
	NumbersGame game;
	private ArrayList<Number> selectedNumbers = new ArrayList<Number>();
	private Number numbers[][];
	
	private BitmapFont lblRuleFont, lblScore, lblTime, lblCountdown, lblOperation, lblMultiplier, lblGameOver;
	private Rule rule;
	private Color paleBlue = new Color(.45f, .54f, .73f, 1); // 120, 138, 188, 1; 
	//private Color royalBlue1 = new Color(.28f, .46f, 1f, 1); // 72 118 255
	
	int time, readyCount, elapsedMs = 16, incorrectDelay = 1000, incorrectDelayTimer;
	int timeCombo, TIME_LIMIT = 60000, oneTime = 0;
	float scoreMultiplier = 1; // for combo 
	
	String drawTime, operation;
	
	boolean correct, startDelay;
	private int spriteSize;
	private int score = 0;
	
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
	    
	    lblMultiplier = generator.generateFont((int) (30 * SCALE));
	    lblMultiplier.setScale((float) (1.0 / SCALE), (float) (-1.0 / SCALE));
	    lblMultiplier.getRegion().getTexture().setFilter(TextureFilter.Linear, TextureFilter.Linear);
	    lblMultiplier.setColor(paleBlue);
	    
	    lblGameOver = generator.generateFont((int) (100 * SCALE));
	    lblGameOver.setScale((float) (1.0 / SCALE), (float) (-1.0 / SCALE));
	    lblGameOver.getRegion().getTexture().setFilter(TextureFilter.Linear, TextureFilter.Linear);
	    //lblCountdown.setColor(paleBlue);
	    
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
		spriteWrong = new Sprite[4][4];
		
		
		spriteBatch = new SpriteBatch();
		// sprite batch will use the camera
		spriteBatch.setProjectionMatrix(camera.combined);
		
		//sprite.setOrigin(sprite.getWidth()/2, sprite.getHeight()/2);
	}
	
	private void initialize() {
		if(texture != null)
			texture.dispose();
		
		 spriteSize = 70;
		
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
				
				spriteWrong[j][i] = new Sprite(new Texture(numbers[j][i].getFilePath_wrong()));
				spriteWrong[j][i].flip(false, true); // sprite is flipped along with the camera, so we flip the Y of the sprite
				spriteWrong[j][i].setBounds(locX + j*spriteSize + j*25, locY+ i*spriteSize + i*25 , spriteSize, spriteSize);
			}
		}
		
		//time = 60000;
		time = TIME_LIMIT;
		drawTime = time/1000 + ":00";
		readyCount = 2000; // 4000
		timeCombo = 0;
		oneTime = 0;
		operation = "";
		correct = false;
		incorrectDelayTimer = 0;
		startDelay = false;
		rule = Rule.ODD_SUM;
		resetScore();
	}
	// comment comment comment // para ma-commit
	
	private void replace(Number num){
		int i,j;
		
		j = num.getJ();
		i = num.getI();
		
			numbers[j][i] = NumberResources.randomNumber();
			numbers[j][i].setJ(j);
			numbers[j][i].setI(i);
			
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
			
			spriteWrong[j][i] = new Sprite(new Texture(numbers[j][i].getFilePath_wrong()));
			spriteWrong[j][i].flip(false, true); // sprite is flipped along with the camera, so we flip the Y of the sprite
			spriteWrong[j][i].setBounds(locX + j*spriteSize + j*25, locY+ i*spriteSize + i*25 , spriteSize, spriteSize);
		
		
	}
	
	@Override
	public void render(float delta) {
		long time = System.currentTimeMillis();
		update(delta);
		draw(delta);
		
		time = (1000 / 60) - (System.currentTimeMillis() - time);
		
		if(time > 0) {
			try {
				Thread.sleep(time);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
	
	private void update(float delta) {
		readyCount -= elapsedMs;
		if(readyCount/1000 <= 0) {
			time -= elapsedMs;
			if(time < 0)
				time = 0;
			drawTime = (time/1000) + ":" + (time%1000)/10;
		}
		
		timeCombo -= elapsedMs;
		
		if(timeCombo <= 0)
			scoreMultiplier = 1;
		// delay if input was incorrect
		if(startDelay) {
			incorrectDelayTimer += elapsedMs;
			
			if(incorrectDelayTimer >= 80 && incorrectDelayTimer <= 150) 
				for (Number num : selectedNumbers)
					num.setSelected(false);
			
			if(incorrectDelayTimer >= 200 && incorrectDelayTimer <= 270)
				for (Number num : selectedNumbers)
					num.setSelected(true);
			
			if(incorrectDelayTimer >= 320 && incorrectDelayTimer <= 390)
				for (Number num : selectedNumbers)
					num.setSelected(false);
			
			if(incorrectDelayTimer >= 440 && incorrectDelayTimer <= 510)
				for (Number num : selectedNumbers)
					num.setSelected(true);
			
			if(incorrectDelayTimer >= incorrectDelay) {
				incorrectDelayTimer = 0;
				startDelay = false;
				for (Number num : selectedNumbers)
					num.setSelected(false);
				selectedNumbers.clear();
				operation = "";
			}
		}
		
	}

	private void draw(float delta) {
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
				
		spriteBatch.begin();
		for(int i = 0; i < 4; i++)
			for(int j = 0; j < 4; j++) {
				if(numbers[j][i].isSelected()) {
					if(startDelay)
						spriteWrong[j][i].draw(spriteBatch);
					else 
						spriteSelected[j][i].draw(spriteBatch);
				}
				else {
						sprite[j][i].draw(spriteBatch);
				}
			}
		//lblMultiplier.draw(spriteBatch, "x" + scoreMultiplier, locX, locY - 50);
		
		lblRuleFont.draw(spriteBatch, currentRule(rule), 
				Gdx.graphics.getWidth()/2 - lblRuleFont.getBounds(currentRule(rule)).width/2, locY + 380);
		
		lblScore.draw(spriteBatch, "Score: " + score, locX, locY - 25); // locY - 25
		lblTime.draw(spriteBatch, drawTime, Gdx.graphics.getWidth()/2 - lblTime.getBounds(drawTime).width/2, locY + 430);
		if(readyCount/1000 > 0)
			lblCountdown.draw(spriteBatch, (readyCount/1000)+"", 
					Gdx.graphics.getWidth()/2 - lblCountdown.getBounds((readyCount/1000)+"").width/2, locY + 120);
		
		lblOperation.draw(spriteBatch, operation, 
				Gdx.graphics.getWidth()/2 - lblOperation.getBounds(operation).width/2, locY - 70);
		
		if(time == 0) {
			lblGameOver.draw(spriteBatch, "Time's up", locX - 40 , locY + 120);
			oneTime++; // test
			if(oneTime == 1) {
				//Tween.set(sprite[0][0], SpriteAccessor.POS_XY).target(0).start(NumbersGame.tweenManager);
				Tween.to(sprite[0][0], SpriteAccessor.POS_XY, 2f)
				.target(150, 150)
				.ease(TweenEquations.easeInCubic)
				.start(NumbersGame.tweenManager);
				//System.out.println("Tweening!");
			}
		}
		
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
		//Tween.set(sprite[0][0], SpriteAccessor.ALPHA).target(0).start(NumbersGame.tweenManager);
		//Tween.to(sprite[0][0], SpriteAccessor.ALPHA, 2f).target(1).start(NumbersGame.tweenManager);
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
		if(time != 0){
			if(readyCount/1000 <= 0 && !startDelay) {
				for (int i = 0; i < numbers.length; i++) {
					for (int j = 0; j < numbers[i].length; j++) {
						if(screenX >= numbers[j][i].getLocX() && screenX <= numbers[j][i].getLocX() + numbers[j][i].getSpriteWidth()
								&& screenY >= numbers[j][i].getLocY() && screenY <= numbers[j][i].getLocY() + numbers[j][i].getSpriteHeight()) {
							selectedNumbers.add(numbers[j][i]);
							numbers[j][i].setSelected(true);
							
							operation = operationToString();
							
						}
					}
				}
				
			}
		}
		return false;
	}
	
	private void addScore() {
		int i = 0;
		
		i = selectedNumbers.size();
		score += i * i * 10 * scoreMultiplier;
	
		
		switch(i){
		
		case 3:
			scoreMultiplier += .25;
			break;
		case 4:
			scoreMultiplier += .5;
			break;
		case 5:
			scoreMultiplier += 1;
			break;
			
	}
		
		timeCombo = 3000;
			
	}
	
	private void resetScore(){
		score = 0;
	}
	
	private boolean checkIfCorrect() {
		int sum = 0, product = 1;
		for (Number num : selectedNumbers) {
			sum += num.getNumber();
			product *= num.getNumber();
		}
		switch(rule) {
			case ODD_SUM: 
				if(sum % 2 != 0) 
					return true;
				break;
			case ODD_PRODUCT:
				if(product % 2 != 0) 
					return true;
				break;
			case EVEN_SUM:
				if(sum % 2 == 0) 
					return true;
				break;
			case EVEN_PRODUCT:
				if(product % 2 == 0) 
					return true;
				break;
		}
		return false;
	}

	private void randomizeRule() {
		Random rand = new Random();
		switch(rand.nextInt(4)) {
			case 0: rule = Rule.ODD_SUM; break;
			case 1: rule = Rule.ODD_PRODUCT; break;
			case 2: rule = Rule.EVEN_SUM; break;
			case 3: rule = Rule.EVEN_PRODUCT; break;
		}
	}
	
	@Override
	public boolean touchUp(int screenX, int screenY, int pointer, int button) {
		if(time != 0){
			
			if(selectedNumbers.size() >= 3 && selectedNumbers.size() <= 5) {
				correct = checkIfCorrect(); 
				if(correct) {
					NumberResources.correct.play(1);
					addScore();
					randomizeRule();
					correct = false;
					for (Number num : selectedNumbers)
						num.setSelected(false);
					
					for(int k = 0; k < selectedNumbers.size(); k++){
						replace(selectedNumbers.get(k));
					}
					selectedNumbers.clear();
					operation = "";
					
					}
				else {
					scoreMultiplier = 1;
					startDelay = true;
					long id = NumberResources.wrong.play(1);
					//NumberResources.wrong.stop(id);
				}
			}
			else if(selectedNumbers.size() != 0){
				scoreMultiplier = 1;
				startDelay = true;
			}
			
			//for (Number num : selectedNumbers)
			//	num.setSelected(false);
			
		}	
		
		
		return false;
	}

	@Override
	public boolean touchDragged(int screenX, int screenY, int pointer) {
		if(time != 0){
			
			if(readyCount/1000 <= 0 && !startDelay)
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
											
											operation = operationToString();
										}
									}
								}
							}
						}
					}
		}	
			return false;
		
	}
	
	private String operationToString() {
		String operation = "";
		
		for (int i = 0; i < selectedNumbers.size(); i++) {
			Number num = selectedNumbers.get(i);
			if(rule == Rule.EVEN_SUM || rule == Rule.ODD_SUM)
				operation += ((i == 0)? "" : " + ") + num.getNumber();
			else
				operation += ((i == 0)? "" : " x ") + num.getNumber();
		}
		
		if(selectedNumbers.size() > 1)
			operation += " = " + getResult();
			
		return operation;
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
	
	public boolean checkProductOfEven(Number numbers[][]){
		
		for(int i = 0; i < numbers.length; i++){
			for(int j = 0; j < numbers[i].length; j++){
				if(numbers[i][j].getNumber() % 2 == 0)
					return true;		
			}
			
		}
		return false;	
	}
	
	public boolean checkProductOfOdd(Number numbers[][]){
		
		for(int i = 0; i < numbers.length; i++){
			for(int j = 0; j < numbers[i].length; j++){
				if(numbers[i][j].getNumber() % 2 == 1)
					return true;		
			}
			
		}
		return false;	
	}
	

}
