package com.mygdx.ui.menu.shop.cosmetics;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.mygdx.managers.ColorManager;
import com.mygdx.managers.SpriteManager;
import com.mygdx.ui.menu.shop.ShopItems.ItemListInterface;
import com.mygdx.ui.menu.shop.ShopItems.ShopItem;
import com.mygdx.ui.menu.shop.ShopItems.ShopList;

/**
 * Created by Christopher Thai on 10/27/2016.
 */

public enum ColorList implements ItemListInterface{
	WHITE(0,"White",ColorManager.NORMAL,true),
	RED(100,"Red",ColorManager.RED,false),
	ORANGE(100,"Orange",ColorManager.ORANGE,false),
	YELLOW(100,"Yellow",ColorManager.YELLOW,false),
	GREEN(100,"Green",ColorManager.GREEN,false),
	BLUE(100,"Blue",ColorManager.BLUE,false),
	PURPLE(100,"Purple",ColorManager.PURPLE,false),
	PINK(100,"Pink",ColorManager.PINK,false),
	RAINBOW(1500,"Rainbow",ColorManager.RAINBOW,false),
	DISCO(1500,"Disco",ColorManager.DISCO,false),
	CHRISTMAS(1500,"Christmas",ColorManager.CHRISTMAS,false);

	private final int cost;
	private final String name;
	private final ColorManager color;
	private boolean isBought;
	private final static SpriteManager sprite = SpriteManager.CIRCLE;

	private static ColorList current = WHITE;
	private static ShopList shopList = new ShopList("Colors",ColorList.values());

	ColorList(int cost,String name,ColorManager color, boolean bought){
		this.cost = cost;
		this.name = name;
		this.color = color;
		this.isBought = bought;

	}


	public void addToList(ShopList list){
		ShopItem temp = new ShopItem(this){
			@Override
			public void equipItem(){
				equip();
			}

			@Override
			public void render(Batch batch, float parentAlpha){
				batch.setColor(color.getColor().r,color.g,color.b,opacity);
				batch.draw(sprite.getSprite(),getX(),getY(),getWidth(),getHeight());
			}
		};

		list.add(temp);
	}

	public void equip(){
		ColorManager.setPlayer(color);
		current = this;
	}

	public String getCurrent(){
		return current.name;
	}

	public boolean isBought(){
		return isBought;
	}

	public void setEquipped(String name){
		for(ColorList item : ColorList.values())
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
