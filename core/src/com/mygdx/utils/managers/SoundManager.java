package com.mygdx.utils.managers;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.mygdx.utils.audio.Bgm;
import com.mygdx.utils.audio.SoundEffect;

import java.util.ArrayList;


public class SoundManager{


    static SoundEffect thunk;

    static Bgm menuBgm;

    public static void playThunk(float volume){
        if(thunk==null)
            thunk = new SoundEffect("audios/thunk.ogg");
        else if(volume>=0f)
            thunk.setVolume(volume);
        thunk.play();
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
        if (thunk != null) {
            thunk.dispose();
            thunk = null;
        }

        if (menuBgm != null){
            menuBgm.dispose();
            menuBgm = null;
        }
    }
}
