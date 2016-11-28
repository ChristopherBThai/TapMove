package com.mygdx.ui.menu.shop.cosmetics;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.mygdx.managers.ColorManager;
import com.mygdx.managers.SpriteManager;
import com.mygdx.screen.GameScreen;
import com.mygdx.ui.menu.shop.ShopItems.ItemListInterface;
import com.mygdx.ui.menu.shop.ShopItems.ShopItem;
import com.mygdx.ui.menu.shop.ShopItems.ShopList;

/**
 * Created by Christopher Thai on 11/10/2016.
 */

public enum DesignList implements ItemListInterface{
	NONE(0,"Blank",null,true),
	SWIRL(1000,"Swirl",SpriteManager.SWIRL,false),
	QUARTER_SLICE(1000,"Checkers", SpriteManager.CHECKER,false),
	ARROW(1000,"Arrow",SpriteManager.ARROW,false),
	BIOHAZARD(1000,"Biohazard",SpriteManager.BIOHAZARD,false),
	HAZARD(1000,"Hazard",SpriteManager.HAZARD,false),
	HEART(1000,"Heart",SpriteManager.HEART,false),
	NEUTRON(1000,"Neutron",SpriteManager.NEUTRON,false),
	STRIPES(1000,"Stripes",SpriteManager.STRIPES,false),
	TARGET(1000,"Target",SpriteManager.TARGET,false),
	NO_SYMBOL(1000,"No Symbol",SpriteManager.NO_SYMBOL,false),
	PAW(1000,"Paw",SpriteManager.PAW,false),
	LOTUS(1000,"Lotus",SpriteManager.LOTUS,false),
	STAR(1000,"Star",SpriteManager.STAR,false);

	private final int cost;
	private final String name;
	private final SpriteManager sprite;
	private final static SpriteManager background = SpriteManager.CIRCLE;
	private boolean isBought;

	private static DesignList current = NONE;

	DesignList(int cost, String name, SpriteManager sprite, boolean bought){
		this.cost = cost;
		this.name = name;
		this.sprite = sprite;
		this.isBought = bought;

	}

	@Override
	public void addToList(ShopList list){

		ShopItem temp = new ShopItem(this){
			@Override
			public void equipItem(){
				equip();
			}

			@Override
			public void render(Batch batch, float parentAlpha){
				batch.setColor(ColorManager.PLAYER.r,ColorManager.PLAYER.g,ColorManager.PLAYER.b,opacity);
				batch.draw(background.getSprite(),getX(),getY(),getWidth(),getHeight());
				if(sprite!=null){
					batch.setColor(ColorManager.PLAYER_DESIGN.r,ColorManager.PLAYER_DESIGN.g,ColorManager.PLAYER_DESIGN.b,opacity);
					batch.draw(sprite.getSprite(),getX(),getY(),getWidth(),getHeight());
				}
			}
		};

		list.add(temp);
	}

	@Override
	public void equip(){
		GameScreen.entMan.player.setDesign(sprite);
		current = this;
	}

	public String getCurrent(){
		return current.name;
	}

	public boolean isBought(){
		return isBought;
	}

	public void setEquipped(String name){
		for(DesignList item : DesignList.values())
			if(item.name.equals(name))
				item.equip();
	}

	public void setBought(boolean isBought){
		this.isBought = isBought;
	}

	public int getPrice(){
		return this.cost;
	}

	public String getName(){
		return name;
	}
}
