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
	WHITE(0,"White",ColorManager.NORMAL),
	BLUE(0,"BLUE",ColorManager.BLUE),
	RED(0,"RED",ColorManager.RED),
	GREEN(0,"GREEN",ColorManager.GREEN);

	private final int cost;
	private final String name;
	private final Color color;

	ColorList(int cost,String name,Color color){
		this.cost = cost;
		this.name = name;
		this.color = color;
	}


	public void addToList(ShopList list){
		ShopItem temp = new ShopItem(cost,name, SpriteManager.getCircle());
		temp.setColor(color);
		list.add(temp);
	}
}
