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
import com.badlogic.gdx.physics.box2d.WorldManifold;
import com.mygdx.entities.enemies.Enemy;
import com.mygdx.entities.player.Player;
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

	public ArrayList<Enemy> enemies, enemyPool, enemiesKilled;
	
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
		enemiesKilled = new ArrayList<Enemy>();

		light = new Light(world);
		lighting = new Lighting(player,light);
		
		slowAmount = 1;
	}

	public float update(float delta) {
		if(!GameScreen.pause){
			if(!player.isDashing()&&slowTime){
				if(slowAmount<2.4)
					slowAmount+=.1;
				delta/=slowAmount;
			}else
				slowAmount = 1;
			world.step(delta, 6, 2);


			if(game.running){
				//System.out.println(enemies.size()+"|"+enemiesKilled.size()+"|"+enemyPool.size());
				checkKilledByDash();
				if(EnemyCreator.createEnemy(delta))
					checkDead();

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
		//System.out.println(enemies.size()+"|"+enemyPool.size());
		for(int i=0;i<enemies.size();i++)
			if(enemies.get(i).dead()){
				if(killEnemy(enemies.get(i)))
					i--;
			}
	}

	private void checkKilledByDash(){
		for(int i=0;i<enemiesKilled.size();i++){
			killEnemyByDash(enemiesKilled.get(i));
			i--;
		}
	}

	public boolean killEnemy(Enemy enemy){
		//System.out.println("KilledByOutOfBOunds");
		enemy.disable();
		enemyPool.add(enemy);
		Score.addMoney(1);
		return enemies.remove(enemy);
	}

	public boolean killEnemyByDash(Enemy enemy){
		//System.out.println("KilledByDash");
		enemy.disable();
		enemyPool.add(enemy);
		Score.addMoney(2);
		return enemiesKilled.remove(enemy);
	}

	public void addToEnemiesKilledByDash(Enemy enemy){
		enemies.remove(enemy);
		enemiesKilled.add(enemy);
	}

	public boolean abilityReady() {
		return player.abilityReady();
	}

	public void tap(float x, float y) {
		player.moveTo(x,y);
	}

	public void fling(float velx, float vely){
		player.dashTo(velx,vely);
	}
	
	public void reset(){
		for(int i=0;i<enemies.size();i++) {
			if(killEnemy(enemies.get(i)))
				i--;
		}

		for(int i=0;i<enemiesKilled.size();i++){
			killEnemyByDash(enemiesKilled.remove(i));
			i--;
		}
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
		//Gdx.app.log("tap","beginContact");
		Body a = contact.getFixtureA().getBody();
	    Body b = contact.getFixtureB().getBody();
	    
	    if(!player.invincible){
			Player p = null;
			if(a.getUserData() instanceof  Player)
				p = ((Player)a.getUserData());
			else if(b.getUserData() instanceof Player)
				p = ((Player)b.getUserData());
			Enemy e = null;
			if(a.getUserData() instanceof  Enemy)
				e = ((Enemy)a.getUserData());
			else if(b.getUserData() instanceof  Enemy)
				e = ((Enemy)b.getUserData());

			if(p!=null&&e!=null){
				if(p.isDashing()) {
					//contact.setEnabled(false);
					//System.out.println(enemies.indexOf(e));
					//e.getBody().setActive(false);
					//e.setPos(0,0);
					//this.killEnemy(e);
				} else
					game.end();
			}
	    }
	}

	@Override
	public void endContact(Contact contact) {
		//Gdx.app.log("tap","endcontact");
		
	}

	@Override
	public void preSolve(Contact contact, Manifold oldManifold) {
		//Gdx.app.log("tap","presolve");
		WorldManifold manifold = contact.getWorldManifold();
		for(int j = 0; j < manifold.getNumberOfContactPoints(); j++) {
			//Gdx.app.log("tap",""+j);
			if (!player.invincible) {

				Body a = contact.getFixtureA().getBody();
				Body b = contact.getFixtureB().getBody();

				Player p = null;
				if (a.getUserData() instanceof Player)
					p = ((Player) a.getUserData());
				else if (b.getUserData() instanceof Player)
					p = ((Player) b.getUserData());
				Enemy e = null;
				if (a.getUserData() instanceof Enemy)
					e = ((Enemy) a.getUserData());
				else if (b.getUserData() instanceof Enemy)
					e = ((Enemy) b.getUserData());

				if (p != null && e != null) {
					if (p.isDashing()) {
						contact.setEnabled(false);
						addToEnemiesKilledByDash(e);
						//Gdx.app.log("tap","contact disabled");
					}
				}
			}
		}
	}

	@Override
	public void postSolve(Contact contact, ContactImpulse impulse) {
		// TODO Auto-generated method stub
		
	}

}
