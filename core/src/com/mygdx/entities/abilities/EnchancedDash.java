package com.mygdx.entities.abilities;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.mygdx.entities.enemies.Enemy;

/**
 * Created by Christopher Thai on 11/27/2016.
 */

public class EnchancedDash extends Ability{

	public EnchancedDash(){
		super();
	}


	@Override
	public void update(float delta){

	}

	@Override
	public void render(SpriteBatch sb){

	}

	@Override
	public boolean check(Enemy e){
		return false;
	}

	@Override
	public void reset(){

	}
}
