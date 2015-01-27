package com.suitecompiletech.fcukcancer.simulation;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;

public class ControlsSprite extends Sprite {
	public ControlsSprite(Texture texture) {
		super(texture);
	}

	public Vector2 pos = new Vector2();
	public float width;
	public float height;
}
