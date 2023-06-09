package com.mygdx.game.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.mygdx.game.Game;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
//		config.fullscreen = true;
//		config.width = 1920;
//		config.height = 1080;
		config.foregroundFPS = 60;
		config.vSyncEnabled = true;
		new LwjglApplication(new Game(), config);
	}
}