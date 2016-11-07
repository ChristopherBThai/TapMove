package com.mygdx.ui.menu.shop.cosmetics;

import com.badlogic.gdx.graphics.Color;
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
	BLUE(300,"BLUE",ColorManager.BLUE,false),
	RED(300,"RED",ColorManager.RED,false),
	GREEN(300,"GREEN",ColorManager.GREEN,false);

	private final int cost;
	private final String name;
	private final Color color;
	private final boolean isBought;

	ColorList(int cost,String name,Color color, boolean bought){
		this.cost = cost;
		this.name = name;
		this.color = color;
		this.isBought = bought;
	}


	public void addToList(ShopList list){
		ShopItem temp = new ShopItem(cost,name, SpriteManager.getCircle(),isBought);
		temp.setColor(color);

		list.add(temp);
	}
}
