package com.me.numbersgame;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.me.numbersgame.NumbersGame;

public class Main {
	public static void main(String[] args) {
		LwjglApplicationConfiguration cfg = new LwjglApplicationConfiguration();
		cfg.title = "Numbers";
		cfg.useGL20 = false;
		// 480, 580
		cfg.width = 480;
		cfg.height = 580;
		
		new LwjglApplication(new NumbersGame(), cfg);
	}
}
