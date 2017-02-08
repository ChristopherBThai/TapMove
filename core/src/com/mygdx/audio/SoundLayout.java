package com.mygdx.audio;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.utils.Disposable;

import java.util.ArrayList;

/**
 * Created by Christopher Thai on 2/2/2017.
 */

public abstract class SoundLayout implements Disposable{

	private static final AssetManager assetManager = new AssetManager();
	private Class type;

	protected String loc;
	protected float volume;

	protected boolean loading,isLoaded;

	protected static boolean mute;

	public SoundLayout(String loc, Class type){
		volume = .7f;
		this.loc = loc;
		this.type = type;
		mute = false;
	}

	public void load(){
		if(!loading&&!isLoaded()){
			assetManager.load(loc, type);
			loading = true;
		}
	}

	public boolean isLoaded(){
		return isLoaded;
	}

	public abstract void play();

	public void setVolume(float volume){
		this.volume = volume;
	}

	public static void updateAssets(float delta){
		assetManager.update();
	}

	public void updateLoad(float delta){
		if(!isLoaded&&assetManager.isLoaded(loc)){
			loaded(assetManager.get(loc,type));
			isLoaded = true;
			loading = false;
		}
	}

	public abstract void loaded(Object sound);

	public void dispose(){
		assetManager.unload(loc);
	}

	public static void disposeAssetManager(){
		assetManager.dispose();
	}

	public static void mute(boolean mute){
		SoundLayout.mute = mute;
	}
}
