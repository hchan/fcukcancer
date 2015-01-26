package com.suitecompiletech.fcukcancer.simulation;

import java.util.Random;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.suitecompiletech.fcukcancer.FcukCancer;

public class CancerCell {
	public static final float VELOCITY = 2;
	public static final int HEIGHT_WIDTH_DIVISOR_TO_SCREEN = 5;
	public Texture texture;
	public Simulation simulation;
	public Vector2 pos;
	public int width;
	public int height;
	public Animation animation;
	public float stateTime = 0;
	public float deltaTimeCreated;
	public float randFloat;
	public int directionX = 1; // 1 means right, -1 means left
	
	public CancerCell(Simulation simulation) {
		this.simulation = simulation;
		Random rand = new Random();
		randFloat = rand.nextFloat();
		pos = new Vector2();
		width = FcukCancer.maxOfHeightWidth/HEIGHT_WIDTH_DIVISOR_TO_SCREEN/3;
		height = FcukCancer.maxOfHeightWidth/HEIGHT_WIDTH_DIVISOR_TO_SCREEN/4;
		pos.y = 0;
		pos.x = 0;
		
		Texture texture = new Texture(Gdx.files.internal("cancerCell.png"));
		animation = new Animation(0.1f, TextureRegion.split(texture, 160, 120)[0]);
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

	public void update(float delta) {
		stateTime += delta;
	}
	

}
