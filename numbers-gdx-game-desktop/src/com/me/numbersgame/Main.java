package com.me.numbersgame;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;

public class Main {
	public static void main(String[] args) {
		LwjglApplicationConfiguration cfg = new LwjglApplicationConfiguration();
		cfg.title = "Numbers";
		cfg.useGL20 = false;
		cfg.width = 480;
		cfg.height = 560;
		
		new LwjglApplication(new NumbersGame(), cfg);
	}
}
