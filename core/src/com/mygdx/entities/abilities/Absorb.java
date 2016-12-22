package com.mygdx.entities.abilities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.ParticleEffectPool;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Pool;
import com.mygdx.entities.enemies.Enemy;
import com.mygdx.entities.enemies.LightBall;
import com.mygdx.managers.ColorManager;
import com.mygdx.particles.ParticleTypes;

import java.util.ArrayList;

/**
 * Created by Christopher Thai on 12/21/2016.
 */

public class Absorb extends Ability{

	private float gain;
	private float numOfParticles;
	private float particleGain;
	private Pool<OrbParticle> poolParticle;
	private ArrayList<OrbParticle> particles;

	public Absorb(){
		super();
		gain = 1.25f;
		numOfParticles = 5;
		particleGain = gain/numOfParticles;
		poolParticle = new Pool<OrbParticle>(){
			@Override
			protected OrbParticle newObject(){
				return new OrbParticle();
			}
		};

		particles = new ArrayList<OrbParticle>();
	}

	@Override
	public void update(float delta){
		super.update(delta);
		for(int i = 0; i < particles.size(); i++){
			if(particles.get(i).update(delta))
				i--;
		}
	}

	@Override
	public boolean playerHit(Enemy e){
		if(!(e instanceof LightBall))
			for(int i=0;i<numOfParticles;i++)
				poolParticle.obtain().reset(e.getPos());

		return false;
	}

	@Override
	public void reset(){
		super.reset();
	}

	private class OrbParticle{
		private ParticleEffectPool.PooledEffect particle;

		private float currentTime,maxTime;
		private float healthValue;

		private Vector2 currentPos,startPos,dst;

		public OrbParticle(){
			particle = null;
			maxTime = 1.5f;
			currentTime = maxTime;
			healthValue = particleGain;
			currentPos = new Vector2();
			startPos = new Vector2();
			dst = new Vector2();
		}

		public boolean update(float delta){
			if(!done()){
				currentTime -= delta;
				dst.set(player.getPos()).sub(startPos);
				calculatePos(maxTime-currentTime,maxTime,startPos,dst,currentPos);
				particle.setPosition(currentPos.x,currentPos.y);
				return false;
			}
			free();
			return true;
		}

		private void calculatePos(float currentTime, float maxTime, Vector2 startPos, Vector2 dst, Vector2 currentPos){
			float temp = (float)Math.pow(2, 10 * ((currentTime/maxTime) - 1));
			currentPos.x = dst.x * temp + startPos.x;
			currentPos.y = dst.y * temp + startPos.y;
		}

		public boolean done(){
			return currentTime <= 0;
		}

		public void free(){
			particles.remove(this);
			poolParticle.free(this);
			player.giveLife(healthValue);
		}

		public void reset(Vector2 pos){
			particles.add(this);
			currentTime = maxTime;
			particle = ParticleTypes.ABSORB.particle.getEffect();
			ParticleTypes.ABSORB.particle.setColor(particle, ColorManager.PLAYER);
			currentPos.set(pos);
			startPos.set(pos);
			particle.setPosition(pos.x,pos.y);
		}

	}

}
