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
	WHITE(0,"White",ColorManager.NORMAL.getColor(),true),
	RED(300,"Red",ColorManager.toColor(255,102,102,255),false),
	ORANGE(300,"Orange",ColorManager.toColor(255,153,51,255),false),
	YELLOW(300,"Yellow",ColorManager.toColor(255,255,102,255),false),
	GREEN(300,"Green",ColorManager.toColor(102,255,102,255),false),
	BLUE(300,"Blue",ColorManager.toColor(102,178,255,255),false),
	PURPLE(300,"Purple",ColorManager.toColor(178,102,255,255),false),
	PINK(300,"Pink",ColorManager.toColor(255,102,255,255),false);

	private final int cost;
	private final String name;
	private final Color color;
	private boolean isBought;
	private final static SpriteManager sprite = SpriteManager.CIRCLE;

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
