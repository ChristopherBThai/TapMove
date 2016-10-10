package com.mygdx.managers;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.mygdx.entities.enemies.Enemy;
import com.mygdx.particles.Player.PlayerClick;
import com.mygdx.particles.enemy.EnemySmoke;
import com.mygdx.particles.enemy.EnemySplit;
import com.mygdx.particles.enemy.EnemyTrail;
import com.mygdx.screen.GameScreen;

import java.util.ArrayList;

/**
 * Created by Christopher Thai on 8/25/2016 at 7:41 AM.
 */
public class ParticleManager {

    GameScreen game;

    public EnemyTrail et;
    public EnemySmoke es;
    public PlayerClick pc;

    public ParticleManager(GameScreen game){
        this.game = game;
        et = new EnemyTrail();
        es = new EnemySmoke();
        pc = new PlayerClick();
        for(int i=0;i<10;i++)
            pc.getEffect();
        pc.reset();
    }

    public void update(float delta){
        if(!GameScreen.pause){
            et.update(delta);
            es.update(delta);
            pc.update(delta);
        }
    }

    public void render(SpriteBatch sb){
        es.render(sb);
        et.render(sb);
        pc.render(sb);
    }

    public void reset(){
        es.reset();
        et.reset();
        pc.reset();
    }

    public void dispose(){
        et.dispose();
        es.dispose();
        pc.dispose();
    }
}
