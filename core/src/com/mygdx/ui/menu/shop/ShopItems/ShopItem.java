package com.mygdx.ui.menu.shop.ShopItems;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.mygdx.managers.SpriteManager;
import com.mygdx.utils.Save;

/**
 * Created by Mono on 9/27/2016.
 */

public class ShopItem{
	private int price;
	private boolean bought;
	private String name;
	private Sprite sprite;

	public ShopItem(){
		price = 0;
		bought = false;
		name = "null";
		sprite = SpriteManager.getCircle();
	}

	public ShopItem(int price, String name, Sprite sprite){
		bought = false;
		this.price = price;
		this.name = name;
		this.sprite = sprite;
	}

	public boolean buyItem(){
		return Save.take(price);
	}

	public void setPrice(int price){
		this.price = price;
	}

	public boolean isBought(){
		return bought;
	}

	public int getPrice(){
		return price;
	}

	public String getName(){
		return name;
	}

	public Sprite getSprite(){
		return sprite;
	}

}