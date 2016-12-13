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
	RED(300,"Red",ColorManager.RED,false),
	ORANGE(300,"Orange",ColorManager.ORANGE,false),
	YELLOW(300,"Yellow",ColorManager.YELLOW,false),
	GREEN(300,"Green",ColorManager.GREEN,false),
	BLUE(300,"Blue",ColorManager.BLUE,false),
	PURPLE(300,"Purple",ColorManager.PURPLE,false),
	PINK(300,"Pink",ColorManager.PINK,false),
	RAINBOW(2000,"Rainbow",ColorManager.RAINBOW,false),
	DISCO(2000,"Disco",ColorManager.DISCO,false),
	CHRISTMAS(2000,"Christmas",ColorManager.CHRISTMAS,false);

	private final int cost;
	private final String name;
	private final ColorManager color;
	private boolean isBought;
	private final static SpriteManager sprite = SpriteManager.CIRCLE;

	private static ColorList current = WHITE;

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
