package com.suitecompiletech.fcukcancer.simulation;

import java.util.ArrayList;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Mesh;
import com.badlogic.gdx.graphics.Pixmap.Format;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Texture.TextureFilter;
import com.badlogic.gdx.graphics.VertexAttribute;
import com.badlogic.gdx.graphics.VertexAttributes.Usage;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.g3d.Material;
import com.badlogic.gdx.graphics.g3d.Model;
import com.badlogic.gdx.graphics.g3d.attributes.BlendingAttribute;
import com.badlogic.gdx.graphics.g3d.attributes.ColorAttribute;
import com.badlogic.gdx.graphics.g3d.attributes.TextureAttribute;
import com.badlogic.gdx.graphics.g3d.loader.ObjLoader;
import com.badlogic.gdx.graphics.g3d.utils.ModelBuilder;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.Disposable;
import com.suitecompiletech.fcukcancer.screens.GameLoop;

public class Simulation implements Disposable {
	public final static float PLAYFIELD_MIN_X = -14;
	public final static float PLAYFIELD_MAX_X = 14;
	public final static float PLAYFIELD_MIN_Z = -15;
	public final static float PLAYFIELD_MAX_Z = 2;
	public static final float TIME_BETWEEN_MISSLE = 1.5f;

	//public ArrayList<Invader> invaders = new ArrayList<Invader>();
	//public ArrayList<Block> blocks = new ArrayList<Block>();
	//public ArrayList<Shot> shots = new ArrayList<Shot>();
	public ArrayList<CancerCell> cancerCells = new ArrayList<CancerCell>();
	public ArrayList<Missle> missles = new ArrayList<Missle>();
	public ArrayList<Explosion> explosions = new ArrayList<Explosion>();
	
	public Hero hero;
	
	public Ship ship;
	public Shot shipShot = null;
	public transient SimulationListener listener;
	public float multiplier = 1;
	public int score;
	public int wave = 1;

	public Model shipModel;
	public Model invaderModel;
	public Model blockModel;
	public Model shotModel;
	public Model explosionModel;

	public ArrayList<Missle> removedMissles = new ArrayList<Missle>();
	private ArrayList<Explosion> removedExplosions = new ArrayList<Explosion>();

	private final Vector3 tmpV1 = new Vector3();
	private final Vector3 tmpV2 = new Vector3();
	private GameLoop gameLoop;
	private Sprite left;
	private Sprite right;
	private Missle lastMissle;

	
	

	public ArrayList<Explosion> getExplosions() {
		return explosions;
	}

	public void setExplosions(ArrayList<Explosion> explosions) {
		this.explosions = explosions;
	}

	public Hero getHero() {
		return hero;
	}

	public void setHero(Hero hero) {
		this.hero = hero;
	}

	public Ship getShip() {
		return ship;
	}

	public void setShip(Ship ship) {
		this.ship = ship;
	}

	public Shot getShipShot() {
		return shipShot;
	}

	public void setShipShot(Shot shipShot) {
		this.shipShot = shipShot;
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

	public int getScore() {
		return score;
	}

	public void setScore(int score) {
		this.score = score;
	}

	public int getWave() {
		return wave;
	}

	public void setWave(int wave) {
		this.wave = wave;
	}

	public Model getShipModel() {
		return shipModel;
	}

	public void setShipModel(Model shipModel) {
		this.shipModel = shipModel;
	}

	public Model getInvaderModel() {
		return invaderModel;
	}

	public void setInvaderModel(Model invaderModel) {
		this.invaderModel = invaderModel;
	}

	public Model getBlockModel() {
		return blockModel;
	}

	public void setBlockModel(Model blockModel) {
		this.blockModel = blockModel;
	}

	public Model getShotModel() {
		return shotModel;
	}

	public void setShotModel(Model shotModel) {
		this.shotModel = shotModel;
	}

	public Model getExplosionModel() {
		return explosionModel;
	}

	public void setExplosionModel(Model explosionModel) {
		this.explosionModel = explosionModel;
	}

	public ArrayList<Explosion> getRemovedExplosions() {
		return removedExplosions;
	}

	public void setRemovedExplosions(ArrayList<Explosion> removedExplosions) {
		this.removedExplosions = removedExplosions;
	}

	public static float getPlayfieldMinX() {
		return PLAYFIELD_MIN_X;
	}

	public static float getPlayfieldMaxX() {
		return PLAYFIELD_MAX_X;
	}

	public static float getPlayfieldMinZ() {
		return PLAYFIELD_MIN_Z;
	}

	public static float getPlayfieldMaxZ() {
		return PLAYFIELD_MAX_Z;
	}

	public Vector3 getTmpV1() {
		return tmpV1;
	}

	public Vector3 getTmpV2() {
		return tmpV2;
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
		ObjLoader objLoader = new ObjLoader();
		shipModel = objLoader.loadModel(Gdx.files.internal("data/ship.obj"));
		invaderModel = objLoader.loadModel(Gdx.files.internal("data/invader.obj"));
		blockModel = objLoader.loadModel(Gdx.files.internal("data/block.obj"));
		shotModel = objLoader.loadModel(Gdx.files.internal("data/shot.obj"));

		final Texture shipTexture = new Texture(Gdx.files.internal("data/ship.png"), Format.RGB565, true);
		shipTexture.setFilter(TextureFilter.MipMap, TextureFilter.Linear);
		final Texture invaderTexture = new Texture(Gdx.files.internal("data/invader.png"), Format.RGB565, true);
		invaderTexture.setFilter(TextureFilter.MipMap, TextureFilter.Linear);
		shipModel.materials.get(0).set(TextureAttribute.createDiffuse(shipTexture));
		invaderModel.materials.get(0).set(TextureAttribute.createDiffuse(invaderTexture));

		((ColorAttribute)blockModel.materials.get(0).get(ColorAttribute.Diffuse)).color.set(0, 0, 1, 0.5f);
		blockModel.materials.get(0).set(new BlendingAttribute(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA));

		shotModel.materials.get(0).set(ColorAttribute.createDiffuse(1, 1, 0, 1f));

		final Texture explosionTexture = new Texture(Gdx.files.internal("data/explode.png"), Format.RGBA4444, true);
		explosionTexture.setFilter(TextureFilter.MipMap, TextureFilter.Linear);

		final Mesh explosionMesh = new Mesh(true, 4 * 16, 6 * 16, new VertexAttribute(Usage.Position, 3, "a_position"),
			new VertexAttribute(Usage.TextureCoordinates, 2, "a_texCoord0"));

		float[] vertices = new float[4 * 16 * (3 + 2)];
		short[] indices = new short[6 * 16];
		int idx = 0;
		int index = 0;
		for (int row = 0; row < 4; row++) {
			for (int column = 0; column < 4; column++) {
				vertices[idx++] = 1;
				vertices[idx++] = 1;
				vertices[idx++] = 0;
				vertices[idx++] = 0.25f + column * 0.25f;
				vertices[idx++] = 0 + row * 0.25f;

				vertices[idx++] = -1;
				vertices[idx++] = 1;
				vertices[idx++] = 0;
				vertices[idx++] = 0 + column * 0.25f;
				vertices[idx++] = 0 + row * 0.25f;

				vertices[idx++] = -1;
				vertices[idx++] = -1;
				vertices[idx++] = 0;
				vertices[idx++] = 0f + column * 0.25f;
				vertices[idx++] = 0.25f + row * 0.25f;

				vertices[idx++] = 1;
				vertices[idx++] = -1;
				vertices[idx++] = 0;
				vertices[idx++] = 0.25f + column * 0.25f;
				vertices[idx++] = 0.25f + row * 0.25f;

				final int t = (4 * row + column) * 4;
				indices[index++] = (short)(t);
				indices[index++] = (short)(t + 1);
				indices[index++] = (short)(t + 2);
				indices[index++] = (short)(t);
				indices[index++] = (short)(t + 2);
				indices[index++] = (short)(t + 3);
			}
		}

		explosionMesh.setVertices(vertices);
		explosionMesh.setIndices(indices);

		explosionModel = ModelBuilder.createFromMesh(explosionMesh, GL20.GL_TRIANGLES, new Material(new BlendingAttribute(
			GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA), TextureAttribute.createDiffuse(explosionTexture)));

		ship = new Ship(shipModel);
		//ship.transform.rotate(0, 1, 0, 180);

//		for (int row = 0; row < 4; row++) {
//			for (int column = 0; column < 8; column++) {
//				Invader invader = new Invader(invaderModel, -PLAYFIELD_MAX_X / 2 + column * 2f, 0, PLAYFIELD_MIN_Z + row * 2f);
//				invaders.add(invader);
//			}
//		}

		for (int row = 0; row < 3; row++) {
			for (int col = 0; col < 3; col++) {
				CancerCell cancerCell = new CancerCell(this);
				cancerCell.pos.x = col * cancerCell.width * 2;
				cancerCell.pos.y = row * cancerCell.height * 2;
				cancerCells.add(cancerCell);
			}
		}
		
//		for (int shield = 0; shield < 3; shield++) {
//			blocks.add(new Block(blockModel, -10 + shield * 10 - 1, 0, -2));
//			blocks.add(new Block(blockModel, -10 + shield * 10 - 1, 0, -3));
//			blocks.add(new Block(blockModel, -10 + shield * 10 + 0, 0, -3));
//			blocks.add(new Block(blockModel, -10 + shield * 10 + 1, 0, -3));
//			blocks.add(new Block(blockModel, -10 + shield * 10 + 1, 0, -2));
//		}
	}
	public float deltaSum = 0;
	public void update (float delta) {
		deltaSum += delta;
		//ship.update(delta);
		//updateInvaders(delta);
		updateMissles(delta);
		updateCancerCells(delta);
		updateExplosions(delta);
		//checkShipCollision();
		checkInvaderCollision();
		//checkBlockCollision();
		//checkNextLevel();
	}

//	private void updateInvaders (float delta) {
//		for (int i = 0; i < invaders.size(); i++) {
//			Invader invader = invaders.get(i);
//			invader.update(delta, multiplier);
//		}
//	}

	
	private void updateCancerCells(float delta) {
		for (CancerCell cancerCell : cancerCells) {
			cancerCell.update(delta);
		}
	}
	
	private void updateMissles (float delta) {
		for (Missle missle : missles) {
			missle.update(delta);
		}
//		removedMissles.clear();
//		for (int i = 0; i < missles.size(); i++) {
//			Missle missle = missles.get(i);
//			//missle.update(delta);
//			//if (missle.hasLeftField) removedMissles.add(missle);
//		}
//
//		for (int i = 0; i < removedMissles.size(); i++)
//			shots.remove(removedMissles.get(i));
//
//		if (shipShot != null && shipShot.hasLeftField) shipShot = null;
//
//		if (Math.random() < 0.01 * multiplier && invaders.size() > 0) {
//			int index = (int)(Math.random() * (invaders.size() - 1));
//			invaders.get(index).transform.getTranslation(tmpV1);
//			Shot shot = new Shot(shotModel, tmpV1, true);
//			shots.add(shot);
//			if (listener != null) listener.shot();
//		}
	}

	public void updateExplosions (float delta) {
		removedExplosions.clear();
		for (int i = 0; i < explosions.size(); i++) {
			Explosion explosion = explosions.get(i);
			explosion.update(delta);
			if (explosion.aliveTime > Explosion.EXPLOSION_LIVE_TIME) removedExplosions.add(explosion);
		}

		for (int i = 0; i < removedExplosions.size(); i++)
			explosions.remove(removedExplosions.get(i));
	}

	private void checkInvaderCollision () {
//		if (shipShot == null) return;
//
//		for (int j = 0; j < invaders.size(); j++) {
//			Invader invader = invaders.get(j);
//			invader.transform.getTranslation(tmpV1);
//			shipShot.transform.getTranslation(tmpV2);
//			if (tmpV1.dst(tmpV2) < Invader.INVADER_RADIUS) {
//				shots.remove(shipShot);
//				shipShot = null;
//				invaders.remove(invader);
//				explosions.add(new Explosion(explosionModel, tmpV1));
//				if (listener != null) listener.explosion();
//				score += Invader.INVADER_POINTS;
//				break;
//			}
//		}
	}

	private void checkShipCollision () {
//		removedMissles.clear();
//
//		if (!ship.isExploding) {
//			ship.transform.getTranslation(tmpV1);
//			for (int i = 0; i < shots.size(); i++) {
//				Shot shot = shots.get(i);
//				if (!shot.isInvaderShot) continue;
//				shot.transform.getTranslation(tmpV2);
//				if (tmpV1.dst(tmpV2) < Ship.SHIP_RADIUS) {
//					removedMissles.add(shot);
//					shot.hasLeftField = true;
//					ship.lives--;
//					ship.isExploding = true;
//					explosions.add(new Explosion(explosionModel, tmpV1));
//					if (listener != null) listener.explosion();
//					break;
//				}
//			}
//
//			for (int i = 0; i < removedMissles.size(); i++)
//				shots.remove(removedMissles.get(i));
//		}
//
//		ship.transform.getTranslation(tmpV2);
//		for (int i = 0; i < invaders.size(); i++) {
//			Invader invader = invaders.get(i);
//			invader.transform.getTranslation(tmpV1);
//			if (tmpV1.dst(tmpV2) < Ship.SHIP_RADIUS) {
//				ship.lives--;
//				invaders.remove(invader);
//				ship.isExploding = true;
//				explosions.add(new Explosion(explosionModel, tmpV1));
//				explosions.add(new Explosion(explosionModel, tmpV2));
//				if (listener != null) listener.explosion();
//				break;
//			}
//		}
	}

//	private void checkBlockCollision () {
//		removedShots.clear();
//
//		for (int i = 0; i < shots.size(); i++) {
//			Shot shot = shots.get(i);
//			shot.transform.getTranslation(tmpV2);
//
//			for (int j = 0; j < blocks.size(); j++) {
//				Block block = blocks.get(j);
//				block.transform.getTranslation(tmpV1);
//				if (tmpV1.dst(tmpV2) < Block.BLOCK_RADIUS) {
//					removedShots.add(shot);
//					shot.hasLeftField = true;
//					blocks.remove(block);
//					break;
//				}
//			}
//		}
//
//		for (int i = 0; i < removedShots.size(); i++)
//			shots.remove(removedShots.get(i));
//	}

//	private void checkNextLevel () {
//		if (invaders.size() == 0 && ship.lives > 0) {
//			blocks.clear();
//			shots.clear();
//			shipShot = null;
//			ship.transform.getTranslation(tmpV1);
//			int lives = ship.lives;
//			populate();
//			ship.transform.setTranslation(tmpV1);
//			ship.lives = lives;
//			multiplier += 0.2f;
//			wave++;
//		}
//	}

	public void moveShipLeft (float delta, float scale) {
		hero.pos.x -= delta * Hero.VELOCITY * scale;
		if (hero.pos.x < 0) {
			hero.pos.x = 0;
		}
	}
	
	public void moveShipLeftOld (float delta, float scale) {
		if (ship.isExploding) return;

		ship.transform.trn(-delta * Ship.SHIP_VELOCITY * scale, 0, 0);
		ship.transform.getTranslation(tmpV1);
		if (tmpV1.x < PLAYFIELD_MIN_X) ship.transform.trn(PLAYFIELD_MIN_X - tmpV1.x, 0, 0);
	}
	public void moveShipRight (float delta, float scale) {
		hero.pos.x += delta * Hero.VELOCITY * scale;
		if (hero.pos.x >= Gdx.graphics.getWidth() - hero.width) {
			hero.pos.x = Gdx.graphics.getWidth() - hero.width;
		}
	}

	public void moveShipRightOld (float delta, float scale) {
		if (ship.isExploding) return;

		ship.transform.trn(+delta * Ship.SHIP_VELOCITY * scale, 0, 0);
		ship.transform.getTranslation(tmpV1);
		if (tmpV1.x > PLAYFIELD_MAX_X) ship.transform.trn(PLAYFIELD_MAX_X - tmpV1.x, 0, 0);
	}

	public void shot () {
//		if (shipShot == null && !ship.isExploding) {
//			ship.transform.getTranslation(tmpV1);
//			shipShot = new Shot(shotModel, tmpV1, false);
//			shots.add(shipShot);
//			if (listener != null) listener.shot();
//		}
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
		shipModel.dispose();
		invaderModel.dispose();
		blockModel.dispose();
		shotModel.dispose();
		explosionModel.dispose();
	}

	


	public Sprite getLeft() {
		return left;
	}

	public void setLeft(Sprite left) {
		this.left = left;
	}

	public Sprite getRight() {
		return right;
	}

	public void setRight(Sprite right) {
		this.right = right;
	}


}
