package com.suitecompiletech.fcukcancer.simulation;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.suitecompiletech.fcukcancer.FcukCancer;

public class Hero {
	public static final float VELOCITY = 1000;
	public static final int HEIGHT_WIDTH_DIVISOR_TO_SCREEN = 10;
	public Texture texture;
	public Simulation simulation;
	public Vector2 pos;
	public int width;
	public int height;
	
	public Hero(Simulation simulation) {
		this.simulation = simulation;
		pos = new Vector2();
		width = FcukCancer.maxOfHeightWidth/HEIGHT_WIDTH_DIVISOR_TO_SCREEN;
		height = FcukCancer.maxOfHeightWidth/HEIGHT_WIDTH_DIVISOR_TO_SCREEN;
		pos.y = 0;
		pos.x = (Gdx.graphics.getWidth() - width)/2;
		
		
		//Gdx.app.log(this.getClass().getSimpleName(), pos.x + "");
		
		texture = new Texture("cancerHero.png");
	}
	
	public Texture getTexture() {
		return texture;
	}
	public void setTexture(Texture texture) {
		this.texture = texture;
	}
	public Simulation getSimulation() {
		return simulation;
	}
	public void setSimulation(Simulation simulation) {
		this.simulation = simulation;
	}

	public Vector2 getPos() {
		return pos;
	}

	public void setPos(Vector2 pos) {
		this.pos = pos;
	}
	

}
