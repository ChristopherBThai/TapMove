package com.mygdx.ui.menu.background;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.World;
import com.mygdx.entities.enemies.Enemy;
import com.mygdx.entities.enemies.NormalEnemy;
import com.mygdx.entities.player.Player;
import com.mygdx.game.MyGame;
import com.mygdx.particles.light.ConeLighting;
import com.mygdx.particles.light.FollowLight;
import com.mygdx.particles.light.WorldLighting;

import java.util.ArrayList;

/**
 * Created by Mono on 6/20/2016.
 */
public class Background {

    World world;
    Box2DDebugRenderer b2dr;

    //Player player;
    //float movePlayer;
    //public Lighting lighting;
    WorldLighting light;

    ArrayList<Enemy> enemies,enemyPool;
    float respawn,current;

    public Background(){
        world = new World(new Vector2(0, 0),true);
        b2dr = new Box2DDebugRenderer();
        light = new WorldLighting(world);
        ConeLighting cone = new ConeLighting(null,light);
        cone.setPos(MyGame.WIDTH*.5F,MyGame.HEIGHT*1.02f);
        enemies = new ArrayList<Enemy>();
        enemyPool = new ArrayList<Enemy>();
        respawn = 1f;
        current = 0f;
        //movePlayer = 0;

        //player = new Player(MyGame.WIDTH/2f, MyGame.HEIGHT*.5f, MyGame.WIDTH*.04f,world);
        //Enemy enemy = (new NormalEnemy(MyGame.WIDTH/2f, MyGame.HEIGHT*.5f, world)).randomize();
        //enemy.setPos(MyGame.WIDTH/2f, MyGame.HEIGHT*.5f);
        //enemy.removeParticle();
        //enemies.add(enemy);
        //lighting = new Lighting(player,light);
    }

    public void update(float delta){
        world.step(delta, 6, 2);
        light.update(delta);
        //player.update(delta);
        for(Enemy e:enemies)
            e.update(delta);
        if(current>=respawn){
            current -= respawn;
            spawn();
        }else
            current += delta;
        checkDead();

        //movePlayer += delta;
        //if(movePlayer > 1.5f){
        //    movePlayer -= 1.5f;
        //    player.moveTo(MyGame.WIDTH/2f, MyGame.HEIGHT*.55f);
//
        //}
    }

    public void render(SpriteBatch sb) {
        //player.render(sb);
        for(Enemy e:enemies)
            e.render(sb);
    }

    public void renderLights(){
        light.render();
    }

    public void renderDEBUG(){
        if(MyGame.DEBUG)
            b2dr.render(world, MyGame.camera.combined);
    }

    private void checkDead() {
        for(int i=0;i<enemies.size();i++)
            if(enemies.get(i).dead()){
                Enemy enemy = enemies.remove(i);
                enemy.getBody().setLinearVelocity(0, 0);
                enemy.getBody().setActive(false);
                enemyPool.add(enemy);
            }
    }

    private void spawn(){
        Enemy enemy;
        if(enemyPool.size()>0)
            enemy = enemyPool.remove(0).randomize();
        else
           enemy = (new NormalEnemy(0,0, world)).randomize();
        enemy.removeParticle();
        enemies.add(enemy);
    }

    public void dispose(){
        b2dr.dispose();
        world.dispose();
        light.dispose();
    }

}
