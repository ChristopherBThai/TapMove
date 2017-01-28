package com.mygdx.managers;

import com.mygdx.audio.Sound;


public enum SoundManager{
    BGM_MENU("audios/bgm/title.wav",true),
    BGM_GAME("audios/bgm/game.wav",true);

    private Sound sound;
    private boolean loop;

    SoundManager(String loc, boolean loop){
        sound = new Sound(loc,loop);
        this.loop = loop;
    }

    public static void update(float delta){
        Sound.updateAssets(delta);
        for(SoundManager sound:SoundManager.values())
            sound.sound.updateLoad(delta);
    }

    public void load(){
        sound.load();
    }

    public void play(){
        sound.play();
    }

    public void stop(){
        sound.stop();
    }

    public void setVolume(float volume){
        sound.setVolume(volume);
    }

    public void dispose() {
        sound.dispose();
    }

    public static void disposeAll(){
        for(SoundManager sound:SoundManager.values())
            sound.dispose();
        Sound.disposeAssetManager();
    }
}
