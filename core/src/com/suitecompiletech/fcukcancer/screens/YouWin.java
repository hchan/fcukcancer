
package com.suitecompiletech.fcukcancer.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.controllers.Controller;
import com.badlogic.gdx.controllers.ControllerAdapter;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Texture.TextureFilter;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.BitmapFont.HAlignment;
import com.badlogic.gdx.graphics.g2d.BitmapFont.TextBounds;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Matrix4;
import com.suitecompiletech.fcukcancer.FcukCancer;

public class YouWin extends InvadersScreen {
	
	private final SpriteBatch spriteBatch;
	/** the background texture **/
	private final Texture background;
	/** the logo texture **/
	private final Texture logo;
	/** the font **/
	//private final BitmapFont font;
	/** is done flag **/
	private boolean isDone = false;
	/** view & transform matrix **/
	private final Matrix4 viewMatrix = new Matrix4();
	private final Matrix4 transformMatrix = new Matrix4();

	public YouWin (FcukCancer invaders) {
		super(invaders);
		FcukCancer.music.stop();
		FcukCancer.music = Gdx.audio.newMusic(Gdx.files.internal("youWin.mp3"));
		FcukCancer.music.setVolume(FcukCancer.VOLUME);
		FcukCancer.music.play();
		
		spriteBatch = new SpriteBatch();
		background = new Texture(Gdx.files.internal("data/planet.jpg"));
		background.setFilter(TextureFilter.Linear, TextureFilter.Linear);

		logo = new Texture(Gdx.files.internal("youWin.png"));
		//logo.setFilter(TextureFilter.Linear, TextureFilter.Linear);

		//font = new BitmapFont(Gdx.files.internal("data/font16.fnt"), Gdx.files.internal("data/font16.png"), false);

		if (invaders.getController() != null) {
			invaders.getController().addListener(new ControllerAdapter() {
				@Override
				public boolean buttonUp(Controller controller,
						int buttonIndex) {
					controller.removeListener(this);
					isDone = true;
					return false;
				}
			});
		}
	}

	@Override
	public void dispose () {
		spriteBatch.dispose();
		background.dispose();
		logo.dispose();
		//font.dispose();
	}

	@Override
	public boolean isDone () {
		return isDone;
	}

	@Override
	public void draw (float delta) {
		Gdx.gl.glClearColor(0f, 0f, 0f, 1);
	    Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

		viewMatrix.setToOrtho2D(0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		spriteBatch.setProjectionMatrix(viewMatrix);
		//spriteBatch.setTransformMatrix(transformMatrix);
		spriteBatch.begin();
		spriteBatch.disableBlending();
		spriteBatch.setColor(Color.WHITE);
		//spriteBatch.draw(background, 0, 0, 480, 320, 0, 0, 512, 512, false, false);
		spriteBatch.enableBlending();
		float logoHeightWidthRatio = (float)logo.getHeight()/logo.getWidth();
		//(float) Gdx.graphics.getWidth() /logo.getWidth()
		float logoWidth = Gdx.graphics.getWidth()/2;
		float logoHeight = logoHeightWidthRatio * logoWidth;
		spriteBatch.draw(logo, (Gdx.graphics.getWidth()-logoWidth) /2, 
				(Gdx.graphics.getHeight()-logoHeight) /2, logoWidth, logoHeight);
		//spriteBatch.draw(logo, 0, 320 - 128, 480, 128, 0, 256, 512, 256, false, false);
		//String text = "It is the end my friend.\nTouch to continue!";
		//TextBounds bounds = font.getMultiLineBounds(text);
		//spriteBatch.setBlendFunction(GL20.GL_ONE, GL20.GL_ONE_MINUS_SRC_ALPHA);
		//font.drawMultiLine(spriteBatch, text, 0, 160 + bounds.height / 2, 480, HAlignment.CENTER);
		spriteBatch.end();
	}

	@Override
	public void update (float delta) {
		if (Gdx.input.justTouched()) {
			isDone = true;
		}
	}
}
