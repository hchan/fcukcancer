

package com.suitecompiletech.fcukcancer.screens;

import com.badlogic.gdx.Files.FileType;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.controllers.Controller;
import com.badlogic.gdx.controllers.ControllerAdapter;
import com.badlogic.gdx.controllers.ControllerListener;
import com.badlogic.gdx.math.Rectangle;
import com.suitecompiletech.fcukcancer.FcukCancer;
import com.suitecompiletech.fcukcancer.Renderer;
import com.suitecompiletech.fcukcancer.simulation.Simulation;
import com.suitecompiletech.fcukcancer.simulation.SimulationListener;

public class GameLoop extends InvadersScreen implements SimulationListener {
	/** the simulation **/
	private final Simulation simulation;
	/** the renderer **/
	private final Renderer renderer;
	/** explosion sound **/
	public final Sound explosion;
	
	private final Music music;

	/** controller **/
	private int buttonsPressed = 0;
	private ControllerListener listener = new ControllerAdapter() {
		@Override
		public boolean buttonDown(Controller controller, int buttonIndex) {
			buttonsPressed++;
			return true;
		}

		@Override
		public boolean buttonUp(Controller controller, int buttonIndex) {
			buttonsPressed--;
			return true;
		}
	};

	public GameLoop (FcukCancer invaders) {
		super(invaders);
		
		music = Gdx.audio.newMusic(Gdx.files.getFileHandle("inspirational.mp3", FileType.Internal));
		music.setVolume(FcukCancer.VOLUME);
		music.setLooping(true);
		music.play();
	
		explosion = Gdx.audio.newSound(Gdx.files.internal("explosion.wav"));
		renderer = new Renderer();
		simulation = new Simulation(this);
		simulation.listener = this;
		if (invaders.getController() != null) {
			invaders.getController().addListener(listener);
		}
	}

	@Override
	public void dispose () {
		renderer.dispose();
		music.stop();
		music.dispose();
		if (invaders.getController() != null) {
			invaders.getController().removeListener(listener);
		}
	}


	@Override
	public void draw (float delta) {
		renderer.render(simulation, delta);
	}

	@Override
	public void update (final float delta) {
		simulation.update(delta);

		
		
		if (Gdx.input.isKeyPressed(Keys.DPAD_LEFT) || Gdx.input.isKeyPressed(Keys.A)) simulation.moveShipLeft(delta, 0.5f);
		if (Gdx.input.isKeyPressed(Keys.DPAD_RIGHT) || Gdx.input.isKeyPressed(Keys.D)) simulation.moveShipRight(delta, 0.5f);
		if (Gdx.input.isKeyPressed(Keys.SPACE)) {
			simulation.shot();			
		}
		
		if (Gdx.input.isTouched()) {
			float x = Gdx.input.getX();
			float y = Gdx.graphics.getHeight() - Gdx.input.getY(); // to
																	// accomodate
																	// for
																	// different
																	// co-ords
																	// system
			Rectangle rectLeft = new Rectangle(simulation.left.pos.x, simulation.left.pos.y,
					simulation.left.width, simulation.left.height);
			Rectangle rectRight = new Rectangle(simulation.right.pos.x, simulation.right.pos.y,
					simulation.right.width, simulation.right.height);
			
		//	Gdx.app.log(this.getClass().getSimpleName(), "rectRight: x"  + simulation.getRight().getX() + ",y:" + simulation.getRight().getY());
			if (rectLeft.contains(x, y)) {
				simulation.moveShipLeft(delta, 0.5f);
			} else if (rectRight
					.contains(x, y)) {
				simulation.moveShipRight(delta, 0.5f);
			} else {
				simulation.shot();
			}
		}

//		Gdx.input.setInputProcessor(new SimpleDirectionGestureDetector(new SimpleDirectionGestureDetector.DirectionListener() {
//			
//			@Override
//			public void onUp() {
//				simulation.moveShipLeft(delta, 0.5f);
//			}
//
//			@Override
//			public void onRight() {
//				simulation.moveShipRight(delta, 0.5f);
//
//			}
//
//			@Override
//			public void onLeft() {
//				simulation.moveShipLeft(delta, 0.5f);
//			}
//
//			@Override
//			public void onDown() {
//				simulation.moveShipRight(delta, 0.5f);
//			}
//		}));
	}

	@Override
	public void explosion () {
		explosion.play();
	}


	public int getButtonsPressed() {
		return buttonsPressed;
	}

	public void setButtonsPressed(int buttonsPressed) {
		this.buttonsPressed = buttonsPressed;
	}

	public ControllerListener getListener() {
		return listener;
	}

	public void setListener(ControllerListener listener) {
		this.listener = listener;
	}

	public Simulation getSimulation() {
		return simulation;
	}

	public Renderer getRenderer() {
		return renderer;
	}

	public Sound getExplosion() {
		return explosion;
	}

	
	
}
