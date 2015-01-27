package com.suitecompiletech.fcukcancer.android;

import android.os.Bundle;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.backends.android.AndroidApplication;
import com.badlogic.gdx.backends.android.AndroidApplicationConfiguration;
import com.suitecompiletech.fcukcancer.FcukCancer;

public class AndroidLauncher extends AndroidApplication {
	@Override
	protected void onCreate (Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		AndroidApplicationConfiguration config = new AndroidApplicationConfiguration();
		initialize(new FcukCancer(), config);
	    Thread.setDefaultUncaughtExceptionHandler (new Thread.UncaughtExceptionHandler()
	    {
	      @Override
	      public void uncaughtException (Thread thread, Throwable e)
	      {
	    Gdx.app.log(this.getClass().getSimpleName(), e.getMessage());
	      }
	    });
	
	}
}
