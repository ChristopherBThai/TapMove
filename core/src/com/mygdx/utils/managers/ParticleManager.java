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

    ArrayList<EnemyTrail> enemyTrails,enemyTrailPool;

    public ParticleManager(GameScreen game){
        this.game = game;
        enemyTrails = new ArrayList<EnemyTrail>();
        enemyTrailPool = new ArrayList<EnemyTrail>();
    }

    public void update(float delta){
        for(int i = 0; i<enemyTrails.size(); i++) {
            enemyTrails.get(i).update(delta);
            if(enemyTrails.get(i).isDead()){
                enemyTrails.get(i).getBody().setActive(false);
                enemyTrails.get(i).getBody().setTransform(-100, -100, 0f);
                enemyTrails.get(i).getBody().setLinearVelocity(0,0);
                enemyTrails.get(i).getBody().setAngularVelocity(0f);
                enemyTrailPool.add(enemyTrails.remove(i));
            }
        }
    }

    public void render(SpriteBatch sb){
        for(EnemyTrail eTrail: enemyTrails)
            eTrail.render(sb);
    }

    public void create(float length, float x, float y, float xvel, float yvel, float rotation, Color color){
        for(int i = 0; i<enemyTrailPool.size(); i++) {
            if(enemyTrailPool.get(i).getLength()==length){
                EnemyTrail eTrail= enemyTrailPool.remove(i);
                eTrail.reuse(x,y,color,xvel,yvel,rotation);
                enemyTrails.add(eTrail);
                return;
            }
        }
        EnemyTrail eTrail = new EnemyTrail(x, y, length, color, xvel, yvel, rotation, game.entMan.world);
        enemyTrails.add(eTrail);
    }

    public void reset(){
        for(int i=0; i<enemyTrails.size(); i++){
            enemyTrails.get(i).getBody().setActive(false);
            enemyTrails.get(i).getBody().setTransform(-100, -100, 0f);
            enemyTrails.get(i).getBody().setLinearVelocity(0,0);
            enemyTrails.get(i).getBody().setAngularVelocity(0f);
            enemyTrailPool.add(enemyTrails.remove(i));
        }
    }

    public void dispose(){

    }
}
