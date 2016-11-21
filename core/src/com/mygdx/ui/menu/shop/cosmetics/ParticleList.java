package com.mygdx.ui.menu.shop.cosmetics;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.mygdx.managers.ColorManager;
import com.mygdx.managers.SpriteManager;
import com.mygdx.particles.ParticleTypes;
import com.mygdx.screen.GameScreen;
import com.mygdx.ui.menu.shop.ShopItems.ItemListInterface;
import com.mygdx.ui.menu.shop.ShopItems.ShopItem;
import com.mygdx.ui.menu.shop.ShopItems.ShopList;

/**
 * Created by Christopher Thai on 11/19/2016.
 */

public enum ParticleList implements ItemListInterface{
	CIRCLE(0,"Circles",SpriteManager.getCircle(),true),
	HEART(1000,"Hearts",SpriteManager.getHeart(),false),
	SWIRL(1000,"Swirls",SpriteManager.getSwirl(),false);

	private final int cost;
	private final String name;
	private final boolean isBought;
	private final Sprite sprite;

	ParticleList(int cost,String name,Sprite sprite, boolean bought){
		this.cost = cost;
		this.name = name;
		this.sprite = sprite;
		this.isBought = bought;
	}


	public void addToList(ShopList list){
		ShopItem temp = new ShopItem(cost,name, sprite,isBought){
			@Override
			public void equipItem(){
				ParticleTypes.PLAYER_TRAIL.particle.setAllSprite(sprite);
			}

			@Override
			public void render(Batch batch, float parentAlpha){
				batch.setColor(ColorManager.PLAYER.r,ColorManager.PLAYER.g,ColorManager.PLAYER.b,opacity);
				batch.draw(sprite,getX()+getWidth()/2f-getWidth()*.15f,getY()+getHeight()/2f-getHeight()*.15f,getWidth()*.3f,getHeight()*.3f);
			}
		};

		list.add(temp);
	}
}
