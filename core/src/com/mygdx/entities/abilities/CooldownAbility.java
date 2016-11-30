package com.mygdx.entities.abilities;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.mygdx.managers.ColorManager;
import com.mygdx.managers.SpriteManager;

/**
 * Created by Christopher Thai on 11/26/2016.
 */

public abstract class CooldownAbility extends Ability{

	protected float cooldown,currentCooldown;

	protected SpriteManager visual;
	protected float opacity;
	protected float visualBuffer,visualLength;

	public CooldownAbility(SpriteManager visual, float cooldown){
		this.cooldown = cooldown;
		this.currentCooldown = cooldown;
		this.visual = visual;
		visualBuffer = player.getRadius() * .7f;
		visualLength = player.getRadius() * .7f;
	}

	@Override
	public void render(SpriteBatch sb){
		if(visual!=null){
			sb.setColor(ColorManager.NORMAL.r,ColorManager.NORMAL.g,ColorManager.NORMAL.b,opacity);
			sb.draw(visual.getSprite(),player.getPos().x + visualBuffer,player.getPos().y + visualBuffer,visualLength,visualLength);
		}
	}

	@Override
	public void update(float delta){
		if(currentCooldown>0){
			currentCooldown -= delta;
			opacity = ((cooldown-currentCooldown)/cooldown) * .5f;
			if(isReady())
				justReady();
		}
	}

	public void justReady(){
		opacity = 1f;
	}

	public boolean isReady(){
		return currentCooldown <=0;
	}

	@Override
	public void reset(){
		currentCooldown = 0;
		opacity = 1;
	}

	protected void activate(){
		if(isReady()){
			currentCooldown = cooldown;
			opacity = 0f;
		}
	}

}
