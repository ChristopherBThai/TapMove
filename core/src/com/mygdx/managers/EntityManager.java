package com.mygdx.managers;

import com.badlogic.gdx.graphics.g2d.ParticleEffectPool;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
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
import com.mygdx.particles.ParticleTypes;
import com.mygdx.particles.light.FollowLight;
import com.mygdx.particles.light.WorldLighting;
import com.mygdx.screen.GameScreen;
import com.mygdx.utils.MathUtility;
import com.mygdx.utils.create.EnemyCreator;
import com.mygdx.ui.game.Score;

import java.util.ArrayList;

public class EntityManager implements ContactListener{
	
	GameScreen game;

	public World world;
	public Box2DDebugRenderer b2dr;
	
	public Player player;
	public WorldLighting light;
	public FollowLight pLighting;
	private float defaultBrightness,defaultBrightnessMin, defaultPlayerLightLength;

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

		light = new WorldLighting(world);
		pLighting = new FollowLight(player,light);
		defaultBrightness = .5f;
		defaultPlayerLightLength = 40;
		
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
				//Gdx.app.log("Tap",enemies.size()+"|"+enemiesKilled.size()+"|"+enemyPool.size());
				checkKilledByDash();
				if(EnemyCreator.createEnemy(delta))
					checkDead();

				player.update(delta);
				pLighting.setLightLength(player.getLifePercent()*(defaultPlayerLightLength-10)+10);
				light.setLightLevel(defaultBrightness*player.getLifePercent()+defaultBrightnessMin);

				boolean slowTemp = false;

				for(Enemy e:enemies){
					e.update(delta);
					if(player.check(e))
						slowTemp = true;
				}

				if(slowTemp!=slowTime){
					if(slowTemp)
						light.animateTo(defaultBrightness*player.getLifePercent()*.7f,.1f);
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
		//System.out.println("KilledByOutOfBounds");
		enemy.disable();
		enemyPool.add(enemy);
		Score.addMoney(1);
		return enemies.remove(enemy);
	}

	public boolean killEnemyByDash(Enemy enemy){
		//System.out.println("KilledByDash");
		ParticleEffectPool.PooledEffect effect = ParticleTypes.ENEMY_SMOKE.particle.getEffect();
		effect.setPosition(enemy.getPos().x,enemy.getPos().y);
		ParticleTypes.ENEMY_SMOKE.particle.setAngle(effect, MathUtility.vectorToRadians(player.getBody().getLinearVelocity().x,player.getBody().getLinearVelocity().y)* MathUtils.radiansToDegrees);
		ParticleTypes.ENEMY_SMOKE.particle.setColor(effect, enemy.getColor());
		ParticleTypes.ENEMY_SMOKE.particle.setScale(effect, enemy.getRadius()/enemy.NORMAL_RADIUS);
		enemy.disable();
		enemyPool.add(enemy);
		Score.addMoney(2);
		return enemiesKilled.remove(enemy);
	}

	public void addToEnemiesKilledByDash(Enemy enemy){
		enemies.remove(enemy);
		enemiesKilled.add(enemy);
	}

	public void tap(float x, float y) {
		player.moveTo(x,y);
	}

	public void fling(float velx, float vely){
		player.dashTo(velx,vely);
	}

	public void longPress(float x, float y){
		player.abilityActivate(x,y);
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
		if(GameScreen.currentMode==GameScreen.DARK){
			defaultBrightness = .05f;
			defaultBrightnessMin = .1f;
		}else{
			defaultBrightness = .6f;
			defaultBrightnessMin = .1f;
		}
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
	    
	    if(game.running&&!player.invincible){
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
				if(p.isDashing()||!e.isEnemy) {
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
			if (game.running) {

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
					if (p.isDashing()||p.hostile||!e.isEnemy) {
						contact.setEnabled(false);
						addToEnemiesKilledByDash(e);
						if(!e.isEnemy){
							p.orbGained();
						}
						p.hit(e);
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
