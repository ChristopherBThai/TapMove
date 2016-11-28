package com.mygdx.ui.menu.shop.ShopItems;

import java.util.ArrayList;

/**
 * Created by Christopher Thai on 9/27/2016.
 */

public class ShopList{
	private ArrayList<ShopItem> items;
	private int current;
	private String name;

	public ShopList(String name){
		this.name = name;
		current = 0;
		items = new ArrayList<ShopItem>();
	}

	public void add(ShopItem item){
		items.add(item);
	}

	public void add(ItemListInterface[] items){
		for(ItemListInterface item: items){
			item.addToList(this);
		}
	}

	public void setBounds(float x, float y, float width, float height){
		for(ShopItem item:items)
			item.setBounds(x,y,width,height);
	}

	public ShopItem getCurrent(){
		return items.get(current);
	}

	public void next(){
		current++;
		if(current>=items.size())
			current=0;
	}

	public void prev(){
		current--;
		if(current<0)
			current = items.size()-1;
	}
}
