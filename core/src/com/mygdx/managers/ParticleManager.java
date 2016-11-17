package com.mygdx.managers;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.mygdx.particles.ParticleList;
import com.mygdx.screen.GameScreen;

import java.util.ArrayList;

/**
 * Created by Christopher Thai on 8/25/2016 at 7:41 AM.
 */
public class ParticleManager {

    GameScreen game;

    public ParticleManager(GameScreen game){
        this.game = game;
    }

    public void update(float delta){
        if(!GameScreen.pause){
            for(ParticleList particle:ParticleList.values()){
                particle.particle.update(delta);
            }
        }
    }

    public void render(SpriteBatch sb){
        for(ParticleList particle:ParticleList.values()){
            particle.particle.render(sb);
        }
    }

    public void reset(){
        for(ParticleList particle:ParticleList.values()){
            particle.particle.reset();
        }
    }

    public void dispose(){
        for(ParticleList particle:ParticleList.values()){
            particle.particle.dispose();
        }
    }
}
