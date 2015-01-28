
package com.suitecompiletech.fcukcancer.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.suitecompiletech.fcukcancer.FcukCancer;

public class YouWin extends InvadersScreen {
	
	private final SpriteBatch spriteBatch;
	
	private final Texture logo;
	
	private final Music music;
	private float deltaSum = 0;
	public YouWin (FcukCancer invaders) {
		super(invaders);
	
		music = Gdx.audio.newMusic(Gdx.files.internal("youWin.mp3"));
		music.setVolume(FcukCancer.VOLUME);
		music.play();
		
		spriteBatch = new SpriteBatch();
		

		logo = new Texture(Gdx.files.internal("youWin.png"));
		
	}

	@Override
	public void dispose () {
		music.stop();
		music.dispose();
		spriteBatch.dispose();
		logo.dispose();
		//font.dispose();
	}

	

	@Override
	public void draw (float delta) {
		Gdx.gl.glClearColor(0f, 0f, 0f, 1);
	    Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		spriteBatch.begin();
		spriteBatch.disableBlending();
		spriteBatch.setColor(Color.WHITE);
		spriteBatch.enableBlending();
		float logoHeightWidthRatio = (float)logo.getHeight()/logo.getWidth();
		float logoWidth = Gdx.graphics.getWidth()/2;
		float logoHeight = logoHeightWidthRatio * logoWidth;
		spriteBatch.draw(logo, (Gdx.graphics.getWidth()-logoWidth) /2, 
				(Gdx.graphics.getHeight()-logoHeight) /2, logoWidth, logoHeight);
		
		spriteBatch.end();
	}

	@Override
	public void update (float delta) {
		deltaSum += delta;
		if (deltaSum >= 3 && (Gdx.input.justTouched() || Gdx.input.isKeyPressed(Keys.SPACE))) {
			done = true;
			this.dispose();
			FcukCancer.INSTANCE.setScreen(new GameLoop(FcukCancer.INSTANCE) );
		}
	}
}
