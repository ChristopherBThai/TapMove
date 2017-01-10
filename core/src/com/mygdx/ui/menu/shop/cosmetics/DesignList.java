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
	SWIRL(200,"Swirl",SpriteManager.SWIRL,false),
	QUARTER_SLICE(200,"Checkers", SpriteManager.CHECKER,false),
	ARROW(200,"Arrow",SpriteManager.ARROW,false),
	BIOHAZARD(200,"Biohazard",SpriteManager.BIOHAZARD,false),
	HAZARD(200,"Hazard",SpriteManager.HAZARD,false),
	HEART(200,"Heart",SpriteManager.HEART,false),
	NEUTRON(200,"Neutron",SpriteManager.NEUTRON,false),
	STRIPES(200,"Stripes",SpriteManager.STRIPES,false),
	TARGET(200,"Target",SpriteManager.TARGET,false),
	NO_SYMBOL(200,"No Symbol",SpriteManager.NO_SYMBOL,false),
	PAW(200,"Paw",SpriteManager.PAW,false),
	LOTUS(200,"Lotus",SpriteManager.LOTUS,false),
	STAR(200,"Star",SpriteManager.STAR,false);

	private final int cost;
	private final String name;
	private final SpriteManager sprite;
	private final static SpriteManager background = SpriteManager.CIRCLE;
	private boolean isBought;

	private static DesignList current = NONE;
	private static ShopList shopList = new ShopList("Designs",DesignList.values());

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
				batch.setColor(ColorManager.PLAYER.getColor().r,ColorManager.PLAYER.g,ColorManager.PLAYER.b,opacity);
				batch.draw(background.getSprite(),getX(),getY(),getWidth(),getHeight());
				if(sprite!=null){
					batch.setColor(ColorManager.PLAYER_DESIGN.getColor().r,ColorManager.PLAYER_DESIGN.g,ColorManager.PLAYER_DESIGN.b,opacity);
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
		shopList.setCurrent(name);
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

	public static ShopList getShopList(){return shopList;}
}
