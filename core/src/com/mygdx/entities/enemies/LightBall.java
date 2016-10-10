package com.mygdx.entities.enemies;

import com.badlogic.gdx.physics.box2d.World;
import com.mygdx.managers.ColorManager;
import com.mygdx.particles.light.Lighting;
import com.mygdx.screen.GameScreen;

/**
 * Created by Mono on 10/9/2016.
 */

public class LightBall extends Enemy{

	public LightBall(float x, float y, World world) {
		super(x, y, NORMAL_RADIUS/2f, 1f, .7f, world);
		Lighting light = new Lighting(this.getBody(), GameScreen.entMan.light);
		this.color = ColorManager.NORMAL;
	}

	@Override
	public boolean equals(int id){
		return Enemy.LIGHT_BALL == id;
	}

}
