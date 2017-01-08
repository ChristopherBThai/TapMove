package com.mygdx.entities.abilities;

/**
 * Created by Christopher Thai on 11/30/2016.
 */

public class LongerLife extends Ability{

	private float orbAmount,life;

	public LongerLife(){
		super();
		life = player.getMaxLife() * 1.5f;
		orbAmount = player.getOrbAmount() * 1.2f;
		desc = "Your total health is\nincreased";
	}

	@Override
	public void reset(){
		super.reset();
		player.setMaxLife(life);
		player.giveLife(life);
		player.setOrbAmount(orbAmount);
	}

}
