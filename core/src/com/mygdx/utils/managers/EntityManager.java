package com.mygdx.utils.managers;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.ContactListener;
import com.badlogic.gdx.physics.box2d.Manifold;
import com.badlogic.gdx.physics.box2d.World;
import com.mygdx.entities.enemies.Enemy;
import com.mygdx.entities.Player;
import com.mygdx.game.MyGame;
import com.mygdx.particles.light.Light;
import com.mygdx.particles.light.Lighting;
import com.mygdx.screen.GameScreen;
import com.mygdx.utils.create.EnemyCreator;
import com.mygdx.utils.ui.game.Score;

import java.util.ArrayList;

public class EntityManager implements ContactListener{
	
	GameScreen game;

	public World world;
	public Box2DDebugRenderer b2dr;
	
	public Player player;
	public Light light;
	public Lighting lighting;

	public ArrayList<Enemy> enemies;
	public ArrayList<Enemy> enemyPool;
	
	boolean slowTime;
	float slowAmount;
	
	public EntityManager(GameScreen game){
		this.game = game;
		
		world = new World(new Vector2(0,0),true);
		world.setContactListener(this);
		b2dr = new Box2DDebugRenderer();
		
		player = new Player(MyGame.WIDTH/2f, MyGame.HEIGHT/2f, MyGame.WIDTH*.04f,world);
		enemies = new ArrayList<Enemy>();
		enemyPool = new ArrayList<Enemy>();

		light = new Light(world);
		lighting = new Lighting(player,light);
		
		slowAmount = 1;
	}

	public float update(float delta) {
		if(!GameScreen.pause){
			if(slowTime){
				if(slowAmount<2.4)
					slowAmount+=.1;
				delta/=slowAmount;
			}else
				slowAmount = 1;
			world.step(delta, 6, 2);


			if(game.running){
				EnemyCreator.createEnemy(delta);

				player.update(delta);

				boolean slowTemp = false;

				for(Enemy e:enemies){
					e.update(delta);
					if(player.check(e))
						slowTemp = true;
				}

				if(slowTemp!=slowTime){
					if(slowTemp)
						light.animateTo(light.getLightLevel()*.7f,.1f);
					else
						light.resetLights();
					slowTime = slowTemp;
				}

				if(player.isDead())
					game.end();
				checkDead();
			}
		}

		light.update(delta);

		return delta;
	}

	public void render(SpriteBatch sb) {
		for(Enemy e:enemies)
			e.render(sb);
		player.render(sb);
	}

	public void renderLights(){
		light.render();
	}
	
	public void renderDEBUG(){
		if(MyGame.DEBUG)
			b2dr.render(world, MyGame.camera.combined);
	}

	public boolean useAbility(){
		return player.useAbility();
	}

	private void checkDead() {
		for(int i=0;i<enemies.size();i++)
			if(enemies.get(i).dead()){
				Enemy enemy = enemies.remove(i);
				enemy.disable();
				enemyPool.add(enemy);
				Score.addMoney(1);
			}
		
	}

	public boolean abilityReady() {
		return player.abilityReady();
	}

	public void tap(float x, float y) {
		player.moveTo(x,y);
	}
	
	public void reset(){
		for(Enemy e:enemies) {
			e.disable();
		}
		player.setPos(MyGame.WIDTH/2f, MyGame.HEIGHT/2f);
		player.setVelocity(0,0);
		player.reset();
	}

	public void dispose() {
		world.dispose();
		b2dr.dispose();
		light.dispose();
	}

	public void resize(int width, int height) {}
	
	public void pause() {}

	public void resume() {}

	@Override
	public void beginContact(Contact contact) {
		Body a = contact.getFixtureA().getBody();
	    Body b = contact.getFixtureB().getBody();
	    
	    if(!player.invincible&&(a.getUserData() instanceof Player ||b.getUserData() instanceof Player)&&(b.getUserData() instanceof Enemy||a.getUserData() instanceof Enemy)){
	    	game.end();
	    }else{
			/*
			Vector2 avg = new Vector2(a.getPosition());
			avg.add(b.getPosition());
			avg.set(avg.x/2f,avg.y/2f);
			float dst = avg.dst(player.getPos());
			dst = 1-dst/MyGame.HEIGHT;
			*/
			/*
			Vector2 force = new Vector2(a.getLinearVelocity());
			force.add(b.getLinearVelocity());
			float volume = force.len2();
			volume/=300;
			SoundManager.playThunk(volume);
			*/
		}
	}

	@Override
	public void endContact(Contact contact) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void preSolve(Contact contact, Manifold oldManifold) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void postSolve(Contact contact, ContactImpulse impulse) {
		// TODO Auto-generated method stub
		
	}

}
