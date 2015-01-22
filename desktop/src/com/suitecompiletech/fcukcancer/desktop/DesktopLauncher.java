package com.suitecompiletech.fcukcancer.desktop;

import com.badlogic.gdx.Files.FileType;
import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.suitecompiletech.fcukcancer.FcukCancer;

public class DesktopLauncher {
	public static void main (String[] arg) {
		try { 
			
			LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
			config.addIcon("icon.png", FileType.Internal);
			new LwjglApplication(new FcukCancer(), config);
		} catch (Throwable e) {
			e.printStackTrace();
		}
	}
}
