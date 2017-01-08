package com.mygdx.entities.abilities;

import com.mygdx.entities.Entity;
import com.mygdx.entities.enemies.Enemy;
import com.mygdx.entities.enemies.LightBall;

/**
 * Created by Christopher Thai on 12/21/2016.
 */

public class SlowTime extends Ability{

	private float radius;

	public SlowTime(){
		super();
		radius = player.getRadius() * 2;
		desc = "Time is slowed when\nnear an enemy";
	}

	@Override
	public boolean check(Enemy e){
		return !(e instanceof LightBall)&&player.getPos().dst(e.getPos())<radius+e.getRadius();
	}

}
