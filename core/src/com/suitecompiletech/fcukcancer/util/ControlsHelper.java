package com.suitecompiletech.fcukcancer.util;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.suitecompiletech.fcukcancer.simulation.ControlsSprite;

public class ControlsHelper {
	public static int leftButtonWidth = Gdx.graphics.getHeight()/8;
	public static int leftButtonHeight = Gdx.graphics.getHeight()/8;
	public static int rightButtonWidth = Gdx.graphics.getHeight()/8;
	public static int rightButtonHeight = Gdx.graphics.getHeight()/8;
	
	public static ControlsSprite getLeft() {
		ControlsSprite retval = null;
		Texture left = new Texture(Gdx.files.internal("left.png"));
		retval = new ControlsSprite(left);
		retval.width = leftButtonWidth;
		retval.height = leftButtonWidth;
		retval.pos.x = 0;
		retval.pos.y = 0;
		return retval;
	}

	public static ControlsSprite getRight() {
		ControlsSprite right = getLeft();
		right.flip(true, false);
		right.pos.x = Gdx.graphics.getWidth()-ControlsHelper.rightButtonWidth;
		return right;
	}

}
