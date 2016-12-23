package com.mygdx.entities.abilities;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.mygdx.managers.ColorManager;
import com.mygdx.managers.SpriteManager;

/**
 * Created by Christopher Thai on 12/22/2016.
 */

public class LastStance extends Ability{

	private float range;

	private float currentRadius,retractedRadius,extendedRadius,radiusVel;
	private float opacity,opacityVel;
	private static SpriteManager sprite = SpriteManager.SPIKEY_CIRCLE;

	public LastStance(){
		super();
		range = .25f;
		retractedRadius = player.getRadius()*1.5f;
		extendedRadius = player.getRadius()*1.9f;
		currentRadius = extendedRadius;
		radiusVel = extendedRadius - retractedRadius;
		opacity = 0f;
		opacityVel = 1;
	}

	@Override
	public void render(SpriteBatch sb){
		if(opacity>0){
			sb.setColor(ColorManager.PLAYER.r,ColorManager.PLAYER.g,ColorManager.PLAYER.b,opacity);
			sb.draw(sprite.getSprite(),player.getPos().x-currentRadius,player.getPos().y-currentRadius,currentRadius*2f,currentRadius*2f);
		}
	}

	@Override
	public void update(float delta){
		super.update(delta);
		if(player.getLifePercent()<=range){
			player.hostile = true;
			player.invincible = true;
			if(opacity<1)
				opacity += delta*opacityVel;
			if(currentRadius>retractedRadius)
				currentRadius -= radiusVel*delta;
		}else{
			player.hostile = false;
			player.invincible = false;
			if(opacity>0)
				opacity -= opacityVel*delta;
			if(currentRadius<extendedRadius)
				currentRadius += radiusVel*delta;
		}
	}

	@Override
	public void reset(){
		player.hostile = false;
		player.invincible = false;
		opacity = 0f;
		currentRadius = extendedRadius;
	}

}
