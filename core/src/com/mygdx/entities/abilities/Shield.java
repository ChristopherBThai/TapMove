package com.mygdx.entities.abilities;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.mygdx.entities.enemies.Enemy;
import com.mygdx.managers.ColorManager;
import com.mygdx.managers.SpriteManager;

/**
 * Created by Christopher Thai on 11/29/2016.
 */

public class Shield extends CooldownAbility{

	private float radius;

	public Shield(){
		super(SpriteManager.OUTER_CIRCLE,20);
		radius = player.getRadius()*1.3f;
		desc = "Create a shield every\ncouple of seconds";
	}

	@Override
	public void render(SpriteBatch sb){
		if(visual!=null){
			sb.setColor(ColorManager.NORMAL.r,ColorManager.NORMAL.g,ColorManager.NORMAL.b,opacity);
			sb.draw(visual.getSprite(),player.getPos().x-radius,player.getPos().y-radius,radius*2f,radius*2f);
		}
	}

	@Override
	public void justReady(){
		super.justReady();
		player.invincible = true;
		player.hostile = true;
	}

	@Override
	public boolean playerHit(Enemy e){
		if(super.isReady()&&e.isEnemy&&!player.isDashing()){
			player.invincible = false;
			player.hostile = false;
			super.activate();
			return true;
		}
		return false;
	}

	@Override
	public void reset(){
		super.reset();
		player.invincible = true;
		player.hostile = true;
	}
}
