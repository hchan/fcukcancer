
package com.suitecompiletech.fcukcancer.screens;

import com.badlogic.gdx.Application.ApplicationType;
import com.badlogic.gdx.Files.FileType;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.controllers.Controller;
import com.badlogic.gdx.controllers.ControllerAdapter;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Texture.TextureFilter;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Matrix4;
import com.suitecompiletech.fcukcancer.FcukCancer;

public class MainMenu extends InvadersScreen {
	private final SpriteBatch spriteBatch;

	public final Texture logo;
	public final Texture start;
	public MainMenu (FcukCancer invaders) {
		super(invaders);
	
		spriteBatch = new SpriteBatch();

		logo = new Texture(Gdx.files.internal("fcukCancer.png"));
		logo.setFilter(TextureFilter.Linear, TextureFilter.Linear);
		start = new Texture(Gdx.files.internal("start.png"));
		if (invaders.getController() != null) {
			invaders.getController().addListener(new ControllerAdapter() {
				@Override
				public boolean buttonUp(Controller controller, int buttonIndex) {
					controller.removeListener(this);
					return false;
				}
			});
		}
	}

	


	@Override
	public void update (float delta) {
		if (Gdx.input.justTouched() || Gdx.input.isKeyPressed(Keys.SPACE)) {
			done = true;
			this.dispose();
			FcukCancer.INSTANCE.setScreen(new GameLoop(FcukCancer.INSTANCE));
		}
	}

	@Override
	public void draw (float delta) {
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		Gdx.gl.glClearColor(0,0,0, 1);
		spriteBatch.enableBlending();
		spriteBatch.begin();
		spriteBatch.setColor(Color.WHITE);
		//spriteBatch.draw(background, 0, 0, 480, 320, 0, 0, 512, 512, false, false);
		float logoWidth = (float)Gdx.graphics.getWidth() * 2/3;
		float logoHeight = logo.getHeight() / logo.getWidth() * logoWidth;
		spriteBatch.draw(logo, (Gdx.graphics.getWidth() - logoWidth)/2, 
				(Gdx.graphics.getHeight() - logoHeight)/2, logoWidth, logoHeight);// 0, 320 - 128, 480, 128, 0, 0, 512, 256, false, false);
		float startWidth = (float)Gdx.graphics.getWidth() / 8;
		float startHeight = startWidth;
		spriteBatch.draw(start, (Gdx.graphics.getWidth() - startWidth)/2, 
				1, startWidth, startHeight);
		
		spriteBatch.end();
	}

	@Override
	public void dispose () {
		start.dispose();
		spriteBatch.dispose();
		logo.dispose();
	}
}
