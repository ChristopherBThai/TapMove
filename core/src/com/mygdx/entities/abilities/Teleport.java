package com.mygdx.entities.abilities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.ParticleEffectPool;
import com.badlogic.gdx.math.Vector2;
import com.mygdx.managers.ColorManager;
import com.mygdx.managers.SpriteManager;
import com.mygdx.particles.ParticleTypes;

/**
 * Created by Christopher Thai on 12/12/2016.
 */

public class Teleport extends ActiveAbility{

	private ParticleEffectPool.PooledEffect particle;
	private Vector2 endPos,startPos,distance,currentPos;

	public Teleport(){
		super(SpriteManager.TELEPORT, 30, 1.5f);
		endPos = new Vector2();
		currentPos = new Vector2();
		startPos = new Vector2();
		distance = new Vector2();
	}

	@Override
	public void update(float delta){
		super.update(delta);
		if(isActive()&&particle != null){
			calculatePos(duration - currentDuration,duration,startPos,distance,currentPos);
			particle.setPosition(currentPos.x,currentPos.y);
		}
	}

	@Override
	public void justEnded(){
		super.justEnded();
		particle = null;
		player.stasis = false;
		player.setPos(endPos);
	}

	@Override
	public void activate(float x, float y){
		if(isReady()){
			endPos.set(x,y);
			currentPos.set(player.getPos());
			startPos.set(player.getPos());
			distance.set(endPos);
			distance.sub(startPos);
			particle = ParticleTypes.TELEPORT.particle.getEffect();
			ParticleTypes.TELEPORT.particle.setColor(particle, ColorManager.PLAYER);
			particle.setPosition(currentPos.x,currentPos.y);
			player.setPos(-1000,-1000);
			player.stasis = true;
			super.activate(x,y);
		}
	}

	private void calculatePos(float currentTime, float maxTime, Vector2 startPos, Vector2 dst, Vector2 currentPos){
		float temp = (float)Math.pow(2, 10 * ((currentTime/maxTime) - 1));
		currentPos.x = dst.x * temp + startPos.x;
		currentPos.y = dst.y * temp + startPos.y;
	}
}
