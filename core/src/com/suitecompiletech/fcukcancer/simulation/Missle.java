package com.suitecompiletech.fcukcancer.simulation;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.suitecompiletech.fcukcancer.FcukCancer;

public class Missle {
	public static final float VELOCITY = 400;
	public static final int HEIGHT_WIDTH_DIVISOR_TO_SCREEN = 20;
	public Simulation simulation;
	public Vector2 pos;
	public int width;
	public int height;
	public Animation animation;
	public float stateTime = 0;
	public float deltaTimeCreated;
	
	public Missle(Simulation simulation) {
		this.simulation = simulation;
		deltaTimeCreated = simulation.deltaSum;
		
		pos = new Vector2();
		width = FcukCancer.maxOfHeightWidth/HEIGHT_WIDTH_DIVISOR_TO_SCREEN/2;
		height = FcukCancer.maxOfHeightWidth/HEIGHT_WIDTH_DIVISOR_TO_SCREEN;
		pos.y = simulation.getHero().height;
		pos.x = simulation.getHero().pos.x + (simulation.getHero().width-width)/2;
		
		
		//Gdx.app.log(this.getClass().getSimpleName(), pos.x + "");
		Texture misslesTexture = new Texture(Gdx.files.internal("missles.png"));
	
		animation = new Animation(0.1f, TextureRegion.split(misslesTexture, 80, 80)[0]);
	}
	
	public void update (float delta) {
		pos.y += delta * VELOCITY;
		stateTime += delta;
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
