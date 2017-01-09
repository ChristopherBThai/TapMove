package com.mygdx.managers;

import com.mygdx.audio.Bgm;
import com.mygdx.audio.SoundEffect;


public class SoundManager{


    static SoundEffect menuClick;

    static Bgm menuBgm;

    public static void orb(float volume){
        if(menuClick==null)
            menuClick = new SoundEffect("audios/effects/orb_collect.wav");
        else if(volume>=0f)
            menuClick.setVolume(volume);
        menuClick.play();
    }

    public static void loopMenuBgm(float volume, boolean play){
        if(menuBgm==null)
            menuBgm = new Bgm("audios/bgm/Easy Lemon 60 second.mp3");
        else if(volume>=0f)
            menuBgm.setVolume(volume);
        if(play)
            menuBgm.play();
        else
            menuBgm.stop();
    }

    public void update(float delta){

    }

    public static void dispose() {
        if (menuClick != null) {
            menuClick.dispose();
            menuClick = null;
        }

        if (menuBgm != null){
            menuBgm.dispose();
            menuBgm = null;
        }
    }
}
