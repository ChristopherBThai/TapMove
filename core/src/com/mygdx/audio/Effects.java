package com.mygdx.audio;

import com.badlogic.gdx.audio.Sound;

/**
 * Created by Christopher Thai on 2/2/2017.
 */

public class Effects extends SoundLayout{
	private Sound sound;

	public Effects(String loc){
		super(loc,Sound.class);
	}

	public void play(){
		if(isLoaded){
			long id = sound.play(volume);
		}
	}

	@Override
	public void loaded(Object sound){
		this.sound = (Sound)sound;
	}

	@Override
	public void dispose(){
		super.dispose();
		sound.dispose();
	}
}
