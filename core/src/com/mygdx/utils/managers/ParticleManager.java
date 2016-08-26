package com.mygdx.utils.managers;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.mygdx.entities.enemies.Enemy;
import com.mygdx.particles.enemy.EnemyTrail;
import com.mygdx.screen.GameScreen;

import java.util.ArrayList;

/**
 * Created by Christopher Thai on 8/25/2016 at 7:41 AM.
 */
public class ParticleManager {

    GameScreen game;

    public EnemyTrail et;

    public ParticleManager(GameScreen game){
        this.game = game;
        et = new EnemyTrail();
    }

    public void update(float delta){
        et.update(delta);
    }

    public void render(SpriteBatch sb){
        et.render(sb);
    }

    public void reset(){
        et.reset();
    }

    public void dispose(){
        et.dispose();
    }
}
