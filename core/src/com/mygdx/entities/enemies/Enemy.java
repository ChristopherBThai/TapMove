package com.mygdx.entities.enemies;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.ParticleEffectPool;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.physics.box2d.World;
import com.mygdx.entities.Entity;
import com.mygdx.game.MyGame;
import com.mygdx.screen.GameScreen;
import com.mygdx.utils.create.BodyCreater;
import com.mygdx.utils.managers.ColorManager;
import com.mygdx.utils.managers.SpriteManager;

public class Enemy extends Entity {

	public static final int NORMAL_ENEMY = 1;
	public static final int FAST_ENEMY = 2;
	public static final int BIG_ENEMY = 3;
	public static final int INVISIBLE_ENEMY = 4;

	Color color;
	float radius;

	float speed,speedBuffer;
	float strafe, strafeBuffer;

	private ParticleEffectPool.PooledEffect particle;
	
	public Enemy(float x, float y, float radius, float density, float restitution, World world){
		super(BodyCreater.createCircle(x, y, radius, density, restitution, false, true, world));
		this.body.setLinearDamping(0f);
		this.radius = radius;
		speed = 500;
		speedBuffer = 300;
		strafe = 200;
		strafeBuffer = 100;
		color = ColorManager.NORMAL_ENEMY;
	}
	
	@Override
	public void render(SpriteBatch sb){
		sb.setColor(color);
		sb.draw(SpriteManager.getCircle(), body.getPosition().x-radius, body.getPosition().y-radius, radius*2, radius*2);
	}
	
	@Override
	public void update(float delta){
		if(particle!=null) {
			particle.setPosition(this.getPos().x, this.getPos().y);
			//GameScreen.partMan.et.setAngle(particle,(float)(Math.atan(this.getBody().getLinearVelocity().y/getBody().getLinearVelocity().x)* MathUtils.radiansToDegrees));
		}
	}
	
	public boolean dead(){
		return this.body.getPosition().x+radius<-radius*3||
				this.body.getPosition().x-radius> MyGame.WIDTH+radius*3||
				this.body.getPosition().y+radius<-radius*3||
				this.body.getPosition().y-radius> MyGame.HEIGHT+radius*3;
	}

	public void disable(){
		this.getBody().setLinearVelocity(0, 0);
		this.getBody().setActive(false);
		this.setPos(-100, -100);
		this.removeParticle();
	}
	
	public void removeParticle(){
		GameScreen.partMan.et.remove(particle);
		this.particle = null;
	}

	public float getRadius(){
		return radius;
	}

	public Enemy randomize() {
		this.body.setActive(true);
		this.particle = GameScreen.partMan.et.getEffect();
		GameScreen.partMan.et.setColor(particle,this.color);
		int random = (int) (Math.random()*4);
			
		switch(random){
			case 0:
				this.setPos(-this.getRadius()*2, MyGame.HEIGHT*Math.random());
				this.setVelocity(Math.random()*speed+speedBuffer, Math.random()*strafe-strafeBuffer);
				break;
			case 1:
				this.setPos(MyGame.WIDTH*Math.random(), -this.getRadius()*2);
				this.setVelocity(Math.random()*strafe-strafeBuffer, Math.random()*speed+speedBuffer);
				break;
			case 2:
				this.setPos(MyGame.WIDTH+this.getRadius()*2, MyGame.HEIGHT*Math.random());
				this.setVelocity(-Math.random()*speed+speedBuffer, Math.random()*strafe-strafeBuffer);
				break;
			case 3:
				this.setPos(MyGame.WIDTH*Math.random(), MyGame.HEIGHT+this.getRadius()*2);
				this.setVelocity(Math.random()*strafe-strafeBuffer, -Math.random()*speed+speedBuffer);
				break;
		}

		return this;
	}

	public boolean equals(int id){
		return false;
	}
}
