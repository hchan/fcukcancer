package com.suitecompiletech.fcukcancer.desktop;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Files.FileType;
import com.badlogic.gdx.Graphics.DisplayMode;
import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.suitecompiletech.fcukcancer.FcukCancer;

public class DesktopLauncher {
	public static void main (String[] arg) {
		try { 


			LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
			config.width = 1024;
			config.height = 768;
			config.fullscreen = false;
			config.title = "Fcuk Cancer";
			config.addIcon("icon.png", FileType.Internal);
			new LwjglApplication(new FcukCancer(), config);
		} catch (Throwable e) {
			e.printStackTrace();
		}
	}
}
