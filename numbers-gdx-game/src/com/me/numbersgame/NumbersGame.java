package com.me.numbersgame;

import aurelienribon.tweenengine.TweenManager;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.Texture;
import com.me.numbersgame.screen.GameScreen;
import com.me.numbersgame.screen.MainMenu;
import com.me.numbersgame.screen.Splash;

public class NumbersGame extends Game {
	
	public static final String TITLE = "Numbers";
	public static TweenManager tweenManager;
	
	MainMenu mainMenuScreen;
	GameScreen gameScreen;
	Splash splashScreen;
	
	
	@Override
	public void create() {	
		Texture.setEnforcePotImages(false);
		tweenManager = new TweenManager();
		
		mainMenuScreen = new MainMenu(this);
		mainMenuScreen.initialize();
		
		gameScreen = new GameScreen(this);
		splashScreen = new Splash(this);
		
		setScreen(splashScreen);
		
	}

	@Override
	public void dispose() {
		super.dispose();
	}

	@Override
	public void render() {		
		super.render();
	}

	@Override
	public void resize(int width, int height) {
		super.resize(width, height);
	}

	@Override
	public void pause() {
		super.pause();
	}

	@Override
	public void resume() {
		super.resume();
	}

	public MainMenu getMainMenuScreen() {
		return mainMenuScreen;
	}

	public GameScreen getGameScreen() {
		return gameScreen;
	}

	public Splash getSplashScreen() {
		return splashScreen;
	}

}
