package com.mygdx.ui.menu.shop.cosmetics;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.utils.Json;
import com.badlogic.gdx.utils.JsonValue;
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
	BLUE(300,"Blue",ColorManager.BLUE,false),
	RED(300,"Red",ColorManager.RED,false),
	GREEN(300,"Green",ColorManager.GREEN,false);

	private final int cost;
	private final String name;
	private final Color color;
	private boolean isBought;
	private final static Sprite sprite = SpriteManager.CIRCLE.getSprite();

	private static ColorList current = WHITE;

	ColorList(int cost,String name,Color color, boolean bought){
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
				batch.setColor(color.r,color.g,color.b,opacity);
				batch.draw(sprite,getX(),getY(),getWidth(),getHeight());
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
