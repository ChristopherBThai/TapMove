package com.mygdx.utils.ui.menu.background;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.World;
import com.mygdx.entities.Player;
import com.mygdx.entities.enemies.Enemy;
import com.mygdx.entities.enemies.NormalEnemy;
import com.mygdx.game.MyGame;
import com.mygdx.particles.light.ConeLighting;
import com.mygdx.particles.light.Light;
import com.mygdx.particles.light.Lighting;
import com.mygdx.screen.GameScreen;
import com.mygdx.utils.create.BodyCreater;
import com.mygdx.utils.ui.game.Score;

import java.util.ArrayList;

/**
 * Created by Mono on 6/20/2016.
 */
public class Background {

    World world;
    Box2DDebugRenderer b2dr;

    //Player player;
    //Lighting lighting;
    Light light;

    ArrayList<Enemy> enemies,enemyPool;
    float respawn,current;

    public Background(){
        world = new World(new Vector2(0, 0),true);
        b2dr = new Box2DDebugRenderer();
        //player = new Player(MyGame.WIDTH*.1f, MyGame.HEIGHT*.95f, MyGame.WIDTH*.04f,world, true);
        //lighting = new Lighting(player,world);
        //lighting.setLightLength(60);
        light = new Light(world);
        ConeLighting cone = new ConeLighting(null,light);
        cone.setPos(MyGame.WIDTH*.5F,MyGame.HEIGHT*1.02f);
        enemies = new ArrayList<Enemy>();
        enemyPool = new ArrayList<Enemy>();
        respawn = 1f;
        current = 0f;
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
        if(enemyPool.size()>0)
            enemies.add(enemyPool.remove(0).randomize());
        else
           enemies.add((new NormalEnemy(0,0, world)).randomize());
    }

    public void dispose(){
        b2dr.dispose();
        world.dispose();
        light.dispose();
    }

}
