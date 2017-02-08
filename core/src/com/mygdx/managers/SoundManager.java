package com.mygdx.managers;

import com.mygdx.audio.Bgm;
import com.mygdx.audio.Effects;
import com.mygdx.audio.SoundLayout;


public enum SoundManager{
    BGM_MENU("audios/bgm/title.wav",true),
    BGM_GAME("audios/bgm/game.wav",true),

    ABILITY("audios/effects/ability.wav",false),
    DASH("audios/effects/dash.wav",false),
    TAP("audios/effects/tap.wav",false),
    WHITEORB("audios/effects/whiteorb.wav",false),;

    private Bgm bgm;
    private Effects effect;
    private SoundLayout soundControl;

    private static boolean mute = false;

    SoundManager(String loc, boolean loop){
        if(loop){
            bgm = new Bgm(loc);
            soundControl = bgm;
        }else{
            effect = new Effects(loc);
            soundControl = effect;
        }

        this.load();
    }

    public void load(){
        soundControl.load();
    }

    public void play(){
        soundControl.play();
    }

    public void stop(){
        if(bgm!=null)
            bgm.stopBgm();
    }

    public void setVolume(float volume){
        soundControl.setVolume(volume);
    }

    public void dispose() {
        soundControl.dispose();
    }

    public static void disposeAll(){
        for(SoundManager sound:SoundManager.values())
            sound.dispose();
        SoundLayout.disposeAssetManager();
    }

    public static void mute(boolean mute){
        SoundLayout.mute(mute);
        BGM_GAME.stop();
        BGM_MENU.stop();
        SoundManager.mute = mute;
    }

    public static void toggleMute(){
        SoundManager.mute(!mute);
    }

    public static boolean isMute(){
        return mute;
    }

    public static void update(float delta){
        Bgm.updateAssets(delta);
        for(SoundManager sound:SoundManager.values()){
            sound.soundControl.updateLoad(delta);
        }
    }
}
