package com.suitecompiletech.fcukcancer.simulation;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class Hero {
	public Texture texture;
	public Simulation simulation;
	public Hero(Simulation simulation) {
		this.simulation = simulation;
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
	

}
