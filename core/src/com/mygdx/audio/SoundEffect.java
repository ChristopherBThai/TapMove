package com.mygdx.audio;


import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Music;

public class SoundEffect {
    Music sound;
    AssetManager asset;
    String loc;
    float volume;

    public SoundEffect(String loc){
        this.loc = loc;
        asset = new AssetManager();
        asset.load(loc,Music.class);
        volume = .7f;
    }

    public void play(){
        asset.update();
        if(sound==null&&asset.isLoaded(loc)){
            sound = asset.get(loc,Music.class);
            sound.setVolume(volume);
            sound.play();
        }else if(sound!=null){
            sound.play();
        }
    }

    public void setVolume(float volume){
        this.volume = volume;
        if(sound!=null){
            sound.setVolume(this.volume);
        }
    }

    public void stop(){
        sound.stop();
    }

    public void pause(){
        sound.pause();
    }

    public void dispose(){
        sound.dispose();
        asset.dispose();
    }
}
