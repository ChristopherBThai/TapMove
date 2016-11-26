package com.mygdx.entities.enemies;

import com.badlogic.gdx.graphics.g2d.ParticleEffectPool;
import com.badlogic.gdx.physics.box2d.World;
import com.mygdx.managers.ColorManager;
import com.mygdx.particles.ParticleTypes;
import com.mygdx.screen.GameScreen;

/**
 * Created by ChristopherThai on 11/25/2016.
 */

public class MagneticEnemy extends Enemy{

	static float pullRadius = NORMAL_RADIUS * 4;
	static float pullStrength = 10;
	private ParticleEffectPool.PooledEffect effect;

	public MagneticEnemy(float x, float y, World world) {
		super(x, y, NORMAL_RADIUS, .4f, 0f, world);
		this.speed *= .6f;
		this.color = ColorManager.BIG_ENEMY;
	}

	@Override
	public void update(float delta){
		super.update(delta);
		if(effect!=null)
			effect.setPosition(this.getPos().x, this.getPos().y);
		pullPlayer();
	}

	private void pullPlayer(){
		float x =  this.getPos().x - GameScreen.entMan.player.getPos().x;
		float y = this.getPos().y - GameScreen.entMan.player.getPos().y;

		if( x > 0){
			if(x > pullRadius){
				x = 0;
				y = 0;
			}else
				x = pullRadius - x;
		}else{
			if(x < -pullRadius){
				x = 0;
				y = 0;
			}else
				x = -pullRadius - x;
		}

		if( y > 0){
			if(y > pullRadius){
				x = 0;
				y = 0;
			}else
				y = pullRadius - x;
		}else if(y < 0){
			if(y < -pullRadius){
				x = 0;
				y = 0;
			}else
				y = -pullRadius - x;
		}

		x = (pullStrength/pullRadius) * x;
		y = (pullStrength/pullRadius) * y;

		GameScreen.entMan.player.setVelocity(x,y);
	}

	@Override
	public boolean equals(int id){
		return Enemy.MAGNET_ENEMY == id;
	}

	@Override
	public void removeParticle(){
		super.removeParticle();
		ParticleTypes.ENEMY_ABSORB.particle.remove(effect);
		this.effect = null;
	}

	@Override
	public Enemy randomize() {
		this.effect = ParticleTypes.ENEMY_ABSORB.particle.getEffect();
		ParticleTypes.ENEMY_ABSORB.particle.setColor(effect,ColorManager.PLAYER_LIGHT);
		ParticleTypes.ENEMY_ABSORB.particle.setScale(effect,radius/NORMAL_RADIUS);
		return super.randomize();
	}

}
