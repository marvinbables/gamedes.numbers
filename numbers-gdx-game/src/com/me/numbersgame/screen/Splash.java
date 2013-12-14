package com.me.numbersgame.screen;

import aurelienribon.tweenengine.BaseTween;
import aurelienribon.tweenengine.Tween;
import aurelienribon.tweenengine.TweenCallback;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.me.numbersgame.NumbersGame;
import com.me.numbersgame.tween.SpriteAccessor;

public class Splash implements Screen {
	
	private SpriteBatch batch;
	private Sprite spriteSplash;
	NumbersGame game;
	
	public Splash(NumbersGame game) {
		this.game = game;
	}
	
	@Override
	public void render(float delta) {
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		
		NumbersGame.tweenManager.update(delta);
		
		GameScreen.spriteBatch.begin();
		spriteSplash.draw(GameScreen.spriteBatch);
		GameScreen.spriteBatch.end();
		
	}

	@Override
	public void resize(int width, int height) {
		
	}

	@Override
	public void show() {
		Tween.registerAccessor(Sprite.class, new SpriteAccessor());
		
		Texture splashTexture = new Texture("img/tsaa_splash.png");
		spriteSplash = new Sprite(splashTexture);
		spriteSplash.setSize(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		// sprite is flipped along with the camera, so we flip the Y of the sprite
		spriteSplash.flip(false, true);
		
		Tween.set(spriteSplash, 0).target(0).start(NumbersGame.tweenManager);
		// previous duration 2
		//Tween.to(spriteSplash, 0, 2f).target(1).ease(TweenEquations.easeOutBounce);
		
		Tween.to(spriteSplash, 0, 0.1f).target(1).repeatYoyo(1, 0.1f).setCallback(new TweenCallback() {
			
			@Override
			public void onEvent(int type, BaseTween<?> source) {
				game.setScreen(game.getMainMenuScreen());
				//NumbersGame.tweenManager.killTarget(spriteSplash);
				NumbersGame.tweenManager.killAll();
			}
		}).start(NumbersGame.tweenManager);
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
		batch.dispose();
		spriteSplash.getTexture().dispose();
	}

}
