package com.suitecompiletech.fcukcancer.simulation;

import java.util.ArrayList;
import java.util.Iterator;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.Disposable;
import com.suitecompiletech.fcukcancer.FcukCancer;
import com.suitecompiletech.fcukcancer.screens.GameLoop;
import com.suitecompiletech.fcukcancer.screens.YouWin;
import com.suitecompiletech.fcukcancer.util.ControlsHelper;

public class Simulation implements Disposable {
	public static final float TIME_BETWEEN_MISSLE = 0.75f;
	public static int MAX_ROW = 1;
	public static int MAX_COL = 1;

	public ArrayList<ArrayList<CancerCell>> cancerCells = new ArrayList<ArrayList<CancerCell>>();
	public ArrayList<Missle> missles = new ArrayList<Missle>();
	public Hero hero;
	
	public transient SimulationListener listener;
	public float multiplier = 1;



	private GameLoop gameLoop;
	public ControlsSprite left;
	public ControlsSprite right;
	private Missle lastMissle;

	
	

	public Hero getHero() {
		return hero;
	}

	public void setHero(Hero hero) {
		this.hero = hero;
	}


	public SimulationListener getListener() {
		return listener;
	}

	public void setListener(SimulationListener listener) {
		this.listener = listener;
	}

	public float getMultiplier() {
		return multiplier;
	}

	public void setMultiplier(float multiplier) {
		this.multiplier = multiplier;
	}

	

	public Simulation (GameLoop gameLoop) {
		this.gameLoop = gameLoop;
		populate();
	}

	public GameLoop getGameLoop() {
		return gameLoop;
	}

	public void setGameLoop(GameLoop gameLoop) {
		this.gameLoop = gameLoop;
	}

	private void populate () {
		
			
		left = ControlsHelper.getLeft();
		right = ControlsHelper.getRight();
		
		
		hero = new Hero(this);
		

		for (int row = 0; row < MAX_ROW; row++) {
			ArrayList<CancerCell> cancerCellRows = new ArrayList<CancerCell>();
			cancerCells.add(cancerCellRows);
			for (int col = 0; col < MAX_COL; col++) {
				CancerCell cancerCell = new CancerCell(this);
				cancerCell.pos.x = col * cancerCell.width * 2;
				cancerCell.pos.y = Gdx.graphics.getHeight() - cancerCell.height - (row * cancerCell.height * 2);
				cancerCellRows.add(cancerCell);
			}
		}
		
	}
	public float deltaSum = 0;
	public void update (float delta) {
		deltaSum += delta;
		updateMissles(delta);
		updateCancerCells(delta);
		checkMissleCollision();
		checkYouWin();
	}

    private void checkYouWin() {
		if (cancerCells.size() == 0) {
			gameLoop.done = true;
			this.dispose();
			FcukCancer.INSTANCE.setScreen(new YouWin(FcukCancer.INSTANCE));
		}
	}


	
	private void checkMissleCollision() {
        Iterator<Missle> misslesIterator = missles.iterator();
		while (misslesIterator.hasNext()) {
			boolean collision = false;
			Missle missle = misslesIterator.next();
			Rectangle rectMissle = new Rectangle(missle.pos.x, missle.pos.y, missle.width, missle.height);
			
			Iterator<ArrayList<CancerCell>> cancerCellsIterator = cancerCells.iterator();
			
			while (cancerCellsIterator.hasNext() && !collision) {
				ArrayList<CancerCell> cancerCellRow = cancerCellsIterator.next();
				
				Iterator<CancerCell> cancerCellRowIterator = cancerCellRow.iterator();
				while (cancerCellRowIterator.hasNext()) {
					CancerCell cancerCell = cancerCellRowIterator.next();
					Rectangle rectCancerCell = new Rectangle(cancerCell.pos.x, cancerCell.pos.y, cancerCell.width, cancerCell.height);
					if (rectMissle.overlaps(rectCancerCell)) {
						//Gdx.app.log(this.getClass().getSimpleName(), "missle hit!");
						gameLoop.explosion.play();
						collision = true;
						cancerCellRowIterator.remove();
						misslesIterator.remove();
						break;
					}
				}
				if (cancerCellRow.size() == 0) {
					cancerCellsIterator.remove();
				}
			}
		}
	}
	

	private void updateCancerCells(float delta) {
	
		// change Direction?
		for (int row = 0; row < cancerCells.size(); row++) {
			ArrayList<CancerCell> cancerCellRow = cancerCells.get(row);
			
			CancerCell cancerCellRight = cancerCellRow.get(cancerCellRow.size() - 1);
			if (cancerCellRight.directionX == 1) {
				float newX = cancerCellRight.pos.x + CancerCell.VELOCITY;

				if (newX + cancerCellRight.width > Gdx.graphics.getWidth()) {
					for (int col = 0; col < cancerCellRow.size(); col++) {
						CancerCell cancerCell = cancerCellRow.get(col);
						cancerCell.directionX = -1;
					}
				}
			}
			CancerCell cancerCellLeft = cancerCellRow.get(0);
			if (cancerCellLeft.directionX == -1) {
				float newX = cancerCellLeft.pos.x - CancerCell.VELOCITY;

				if (newX < 0) {
					for (int col = 0; col < cancerCellRow.size(); col++) {
						CancerCell cancerCell = cancerCellRow.get(col);
						cancerCell.directionX = 1;
					}
				}
			}
		}
		
		
		// move left or right
		for (int row = 0; row < cancerCells.size(); row++) {
			ArrayList<CancerCell> cancerCellRow = cancerCells.get(row);
			for (int col = 0; col < cancerCellRow.size(); col++) {
				CancerCell cancerCell = cancerCellRow.get(col);
				cancerCell.update(delta);
				cancerCell.pos.x = cancerCell.pos.x + (cancerCell.directionX * CancerCell.VELOCITY);
			}
		}
		
	}
	
	private void updateMissles (float delta) {
		for (Missle missle : missles) {
			missle.update(delta);
		}
	}


	public void moveShipLeft (float delta, float scale) {
		hero.pos.x -= delta * Hero.VELOCITY * scale;
		if (hero.pos.x < 0) {
			hero.pos.x = 0;
		}
	}
	public void moveShipRight (float delta, float scale) {
		hero.pos.x += delta * Hero.VELOCITY * scale;
		if (hero.pos.x > Gdx.graphics.getWidth() - hero.width) {
			hero.pos.x = Gdx.graphics.getWidth() - hero.width;
		}
	}
	public void shot () {
		lastMissle = null;
		try {
			lastMissle = missles.get(missles.size()-1);
		} catch (Exception e) {
		}
		if (lastMissle == null || (deltaSum - lastMissle.deltaTimeCreated) > TIME_BETWEEN_MISSLE) {
			Missle missle = new Missle(this);
			missles.add(missle);
		} 
	}


	@Override
	public void dispose () {
		gameLoop.dispose();
	}

	
}
