package com.mygdx.audio;

import com.badlogic.gdx.audio.Music;

/**
 * Created by Christopher Thai on 1/27/2017.
 */

public class Bgm extends SoundLayout{
	private Music bgm;
	private boolean isPlaying;

	public Bgm(String loc){
		super(loc,Music.class);
		isPlaying = false;
	}

	public void play(){
		if(isLoaded&&!mute)
			bgm.play();
		if(!mute)
			isPlaying = true;
	}

	public void stopBgm(){
		if(isLoaded()){
			bgm.pause();
			isPlaying = false;
		}
	}

	@Override
	public void setVolume(float volume){
		super.setVolume(volume);
		if(isLoaded){
			bgm.setVolume(this.volume);
		}
	}

	@Override
	public void loaded(Object sound){
		bgm = (Music)sound;
		bgm.setVolume(volume);
		bgm.setLooping(true);
		if(isPlaying)
			bgm.play();
	}

	@Override
	public void dispose(){
		super.dispose();
		bgm.dispose();
	}
}
