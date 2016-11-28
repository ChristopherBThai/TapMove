package com.mygdx.entities.abilities;

import com.mygdx.managers.SpriteManager;

/**
 * Created by Christopher Thai on 11/26/2016.
 */

public abstract class ActiveAbility extends CooldownAbility{
	protected float duration,currentDuration;

	public ActiveAbility(SpriteManager visual, float cooldown, float duration){
		super(visual, cooldown);
		this.duration = duration;
		this.currentDuration = 0;
	}

	@Override
	public void update(float delta){
		super.update(delta);
		if(currentDuration>0){
			currentDuration -= delta;
			if(!isActive())
				justEnded();
		}
	}

	public boolean isActive(){
		return currentDuration > 0;
	}

	@Override
	public void reset(){
		super.reset();
		currentDuration = 0;
	}

	public void activate(float x, float y){
		if(isReady()){
			currentDuration = duration;
			super.activate();
			justActivated();
		}
	}

	protected void justActivated(){}

	protected void justEnded(){}
}
