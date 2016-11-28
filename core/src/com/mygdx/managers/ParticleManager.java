package com.mygdx.managers;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.mygdx.particles.ParticleTypes;
import com.mygdx.screen.GameScreen;

/**
 * Created by Christopher Thai on 8/25/2016 at 7:41 AM.
 */
public class ParticleManager {

    private GameScreen game;

    public ParticleManager(GameScreen game){
        this.game = game;
    }

    public void update(float delta){
        if(!GameScreen.pause){
            for(ParticleTypes particle:ParticleTypes.values()){
                particle.particle.update(delta);
            }
        }
        //Gdx.app.log("tap",""+ParticleTypes.ENEMY_ABSORB.particle);
    }

    public void render(SpriteBatch sb){
        for(ParticleTypes particle:ParticleTypes.values()){
            particle.particle.render(sb);
        }
    }

    public void reset(){
        for(ParticleTypes particle:ParticleTypes.values()){
            particle.particle.reset();
        }
    }

    public void dispose(){
        for(ParticleTypes particle:ParticleTypes.values()){
            particle.particle.dispose();
        }
    }
}
