package com.mygdx.ui.menu.shop.ShopItems;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.mygdx.utils.Save;
import com.mygdx.utils.actors.AnimatableActor;

/**
 * Created by Christopher Thai on 9/27/2016.
 */

public class ShopItem extends AnimatableActor{

	private int price;
	private boolean bought;
	private String name;

	public ShopItem(){
		super();
		price = 0;
		bought = false;
		name = "null";
	}

	public ShopItem(int price, String name, Sprite sprite) {
		bought = false;
		this.price = price;
		this.name = name;
	}

	public ShopItem(int price, String name, Sprite sprite,boolean isBought) {
		bought = isBought;
		this.price = price;
		this.name = name;
	}

	@Override
	public void update(float delta){}

	@Override
	public void render(Batch batch, float parentAlpha){}

	public void equipItem(){}

	public boolean buyItem(){
		bought = hasEnough();
		return Save.take(price);
	}

	public boolean hasEnough(){
		return Save.getMoney() >= price;
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

}