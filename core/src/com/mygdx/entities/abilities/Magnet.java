package com.mygdx.entities.abilities;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.mygdx.entities.enemies.Enemy;
import com.mygdx.entities.enemies.LightBall;

/**
 * Created by Christopher Thai on 11/27/2016.
 */

public class Magnet extends Ability{

	private float pullStrength,pullRadius,x,y;

	public Magnet(){
		super();
		pullStrength = 16f;
		pullRadius = player.getRadius() * 15;
		desc = "All white orbs are\nattracted to you";
	}

	@Override
	public boolean check(Enemy e){
		if(e instanceof LightBall){
			x = player.getPos().x - e.getPos().x;
			y = player.getPos().y - e.getPos().y;

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

			e.setVelocity(x,y);
		}
		return false;
	}

	@Override
	public void reset(){

	}
}
