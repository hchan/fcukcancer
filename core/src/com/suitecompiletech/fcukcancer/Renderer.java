package com.suitecompiletech.fcukcancer;

import java.util.ArrayList;

import com.badlogic.gdx.Application.ApplicationType;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.suitecompiletech.fcukcancer.simulation.CancerCell;
import com.suitecompiletech.fcukcancer.simulation.Hero;
import com.suitecompiletech.fcukcancer.simulation.Missle;
import com.suitecompiletech.fcukcancer.simulation.Simulation;

public class Renderer {
	private SpriteBatch spriteBatch;
	

	public Renderer () {
		try {
			
			spriteBatch = new SpriteBatch();
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	public SpriteBatch getSpriteBatch() {
		return spriteBatch;
	}


	public void render (Simulation simulation, float delta) {
		
		GL20 gl = Gdx.gl;
		gl.glClear(GL20.GL_COLOR_BUFFER_BIT | GL20.GL_DEPTH_BUFFER_BIT);
	
		spriteBatch.enableBlending(); // enables .png transparency
		spriteBatch.begin();
		
		for (Missle missle : simulation.missles) {
			spriteBatch.draw(missle.animation.getKeyFrame(missle.stateTime, true), missle.pos.x, missle.pos.y, missle.width, missle.height);
			
		}
		for (int row = 0; row < simulation.cancerCells.size(); row++) {
			ArrayList<CancerCell> cancerCellRow = simulation.cancerCells.get(row);
			for (int col = 0; col < cancerCellRow.size(); col++) {
				CancerCell cancerCell = cancerCellRow.get(col);
				float keyFrame = cancerCell.stateTime + cancerCell.randFloat;
				spriteBatch.draw(cancerCell.animation.getKeyFrame(keyFrame, true), cancerCell.pos.x, cancerCell.pos.y, cancerCell.width, cancerCell.height);
			}	
		}
		
		Hero hero = simulation.getHero();
		spriteBatch.draw(hero.getTexture(), hero.pos.x, hero.pos.y, hero.width, hero.height);
		
		if (Gdx.app.getType() == ApplicationType.Android || Gdx.app.getType() != ApplicationType.iOS) {
		
			spriteBatch.draw(simulation.left, simulation.left.pos.x, simulation.left.pos.y, simulation.left.width, simulation.left.height);
			spriteBatch.draw(simulation.right, simulation.right.pos.x, simulation.right.pos.y, simulation.right.width, simulation.right.height);
			//Gdx.app.log(this.getClass().getSimpleName(), "Rendercount:" + renderCount);
		}
		
		spriteBatch.end();
	}
	
	
	public void dispose () {
		spriteBatch.dispose();
	}
}
