package com.suitecompiletech.fcukcancer.simulation;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class ControlsHelper {
	public static int leftButtonWidth = 40;
	public static int leftButtonHeight = 40;
	public static int rightButtonWidth = 40;
	public static int rightButtonHeight = 40;
	
	public static Sprite getLeft() {
		Texture left = new Texture(Gdx.files.internal("left.png"));
		
		return new Sprite(left);
	}

	public static Sprite getRight() {
		Sprite right = getLeft();
		right.flip(true, false);
		return right;
	}

}
