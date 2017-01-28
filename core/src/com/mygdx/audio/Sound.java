package com.mygdx.audio;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.utils.Disposable;

/**
 * Created by Christopher Thai on 1/27/2017.
 */

public class Sound implements Disposable{

	private static final AssetManager assetManager = new AssetManager();

	private Music sound;
	private String loc;
	private float volume;
	private boolean loop,isPlaying;

	private boolean isLoaded;

	public Sound(String loc,boolean isLoop){
		this.loc = loc;
		loop = isLoop;
		volume = .7f;
		isPlaying = false;
	}

	public void load(){
		assetManager.load(loc,Music.class);
	}

	public static void updateAssets(float delta){
		assetManager.update();
	}

	public void updateLoad(float delta){
		if(!isLoaded&&assetManager.isLoaded(loc)){
			sound = assetManager.get(loc,Music.class);
			sound.setVolume(volume);
			sound.setLooping(loop);
			isLoaded = true;
			if(loop&&isPlaying)
				sound.play();
		}
	}
	public boolean isLoaded(){
		return isLoaded;
	}

	public void play(){
		if(isLoaded)
			sound.play();
		isPlaying = true;
	}

	public void stop(){
		if(isLoaded())
			sound.pause();
		isPlaying = false;
	}

	public void setVolume(float volume){
		this.volume = volume;
		if(isLoaded){
			sound.setVolume(this.volume);
		}
	}

	public void dispose(){
		assetManager.unload(loc);
		sound.dispose();
	}

	public static void disposeAssetManager(){
		assetManager.dispose();
	}
}
