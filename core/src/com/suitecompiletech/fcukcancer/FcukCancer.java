package com.suitecompiletech.fcukcancer;

import com.badlogic.gdx.Application.ApplicationType;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.controllers.Controller;
import com.badlogic.gdx.controllers.ControllerAdapter;
import com.badlogic.gdx.controllers.Controllers;
import com.badlogic.gdx.graphics.FPSLogger;
import com.badlogic.gdx.utils.Array;
import com.suitecompiletech.fcukcancer.screens.InvadersScreen;
import com.suitecompiletech.fcukcancer.screens.MainMenu;

public class FcukCancer extends Game {
	public static FcukCancer INSTANCE;

	public static final float VOLUME = 0.2f;
	/** Music needs to be a class property to prevent being disposed. */

	private FPSLogger fps = new FPSLogger();
	public static int maxOfHeightWidth;
	private Controller controller;
	private ControllerAdapter controllerListener = new ControllerAdapter(){
		@Override
		public void connected(Controller c) {
			if (controller == null) {
				controller = c;
			}
		}
		@Override
		public void disconnected(Controller c) {
			if (controller == c) {
				controller = null;
			}
		}
	};


	public Controller getController() {
		return controller;
	}

	@Override
	public void render () {
		InvadersScreen currentScreen = getScreen();

		// update the screen
		currentScreen.render(Gdx.graphics.getDeltaTime());

		// When the screen is done we change to the
		// next screen. Ideally the screen transitions are handled
		// in the screen itself or in a proper state machine.
//		if (currentScreen.isDone()) {
//			// dispose the resources of the current screen
//			//currentScreen.dispose();
//
//			// if the current screen is a main menu screen we switch to
//			// the game loop
//			if (currentScreen instanceof MainMenu) {
//				setScreen(new GameLoop(this));
//			} else {
//				// if the current screen is a game loop screen we switch to the
//				// game over screen
//				if (currentScreen instanceof GameLoop) {
//					setScreen(new YouWin(this));
//				} else if (currentScreen instanceof GameOver) {
//					// if the current screen is a game over screen we switch to the
//					// main menu screen
//					setScreen(new MainMenu(this));
//				}
//			}
//		}
//
//		fps.log();
	}

	@Override
	public void create () {
		INSTANCE = this;
		maxOfHeightWidth = Gdx.graphics.getHeight();
		if (Gdx.graphics.getWidth() > maxOfHeightWidth) {
			maxOfHeightWidth = Gdx.graphics.getWidth();
		}
		
		Array<Controller> controllers = Controllers.getControllers();
		if (controllers.size > 0) {
			controller = controllers.first();
		}
		Controllers.addListener(controllerListener);

		setScreen(new MainMenu(this));
		
		Gdx.input.setInputProcessor(new InputAdapter() {
			@Override
			public boolean keyUp (int keycode) {
				if (keycode == Keys.ENTER && Gdx.app.getType() == ApplicationType.WebGL) {
					if (!Gdx.graphics.isFullscreen()) Gdx.graphics.setDisplayMode(Gdx.graphics.getDisplayModes()[0]);
				}
				return true;
			}
		});

	}

	/** For this game each of our screens is an instance of InvadersScreen.
	 * @return the currently active {@link InvadersScreen}. */
	@Override
	public InvadersScreen getScreen () {
		return (InvadersScreen)super.getScreen();
	}
}
