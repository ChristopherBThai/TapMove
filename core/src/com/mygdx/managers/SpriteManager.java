package com.mygdx.managers;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;

public enum SpriteManager {
	TITLE("sprites/title.png"),
	CIRCLE("sprites/circle.png"),
	OUTER_CIRCLE("sprites/outer_circle.png"),
	CORNER("sprites/corner.png"),
	BOX("sprites/box.png"),
	PAUSE("sprites/pause.png"),
	BACK("sprites/back.png"),

	CHECKER("sprites/design/checker.png"),
	SWIRL("sprites/design/swirl.png"),
	ARROW("sprites/design/arrow.png"),
	BIOHAZARD("sprites/design/biohazard.png"),
	HAZARD("sprites/design/hazard.png"),
	HEART("sprites/design/heart.png"),
	NEUTRON("sprites/design/neutron.png"),
	STRIPES("sprites/design/stripes.png"),
	TARGET("sprites/design/target.png"),
	NO_SYMBOL("sprites/design/no_symbol.png"),
	PAW("sprites/design/paw.png"),
	LOTUS("sprites/design/lotus.png"),
	STAR("sprites/design/star.png"),

	BOMB("sprites/ability/bomb.png"),
	SHIELD("sprites/ability/shield.png"),
	BOOST("sprites/ability/boost.png"),
	MAGNET("sprites/ability/magnet.png");

	private final String loc;
	private Sprite sprite;

	SpriteManager(String loc){
		this.loc = loc;
		sprite = null;
	}

	public Sprite getSprite(){
		if(sprite == null)
			sprite = new Sprite(new Texture(Gdx.files.internal(loc)));
		return sprite;
	}


	public void dispose(){
		for(SpriteManager spriteManager : SpriteManager.values())
			spriteManager.sprite = null;
	}

	public static String getActiveCount(){
		int count = 0;
		for(SpriteManager spriteManager:SpriteManager.values())
			if(spriteManager.sprite!=null)
				count++;
		return "Active: " + count + "/" + SpriteManager.values().length;
	}
}
