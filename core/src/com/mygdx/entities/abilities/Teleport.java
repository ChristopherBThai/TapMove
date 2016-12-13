package com.mygdx.entities.abilities;

import com.mygdx.managers.SpriteManager;

/**
 * Created by Christopher Thai on 12/12/2016.
 */

public class Teleport extends ActiveAbility{
	public Teleport(){
		super(SpriteManager.TELEPORT, 30, 0.3f);
	}
}
