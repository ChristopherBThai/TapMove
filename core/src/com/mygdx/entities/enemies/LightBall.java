package com.mygdx.entities.enemies;

import com.badlogic.gdx.physics.box2d.World;
import com.mygdx.managers.ColorManager;
import com.mygdx.particles.light.FollowLight;
import com.mygdx.screen.GameScreen;

/**
 * Created by Christopher Thai on 10/9/2016.
 */

public class LightBall extends Enemy{

	public LightBall(float x, float y, World world) {
		super(x, y, NORMAL_RADIUS/2f, 1f, .7f, world);
		FollowLight light = new FollowLight(this.getBody(), GameScreen.entMan.light, true);
		this.color = ColorManager.NORMAL;
		isEnemy = false;
	}

	@Override
	public boolean equals(int id){
		return Enemy.LIGHT_BALL == id;
	}

}
