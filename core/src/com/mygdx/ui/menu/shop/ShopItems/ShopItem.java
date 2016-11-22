package com.mygdx.ui.menu.shop.ShopItems;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.mygdx.utils.Save;
import com.mygdx.utils.actors.AnimatableActor;

/**
 * Created by Christopher Thai on 9/27/2016.
 */

public class ShopItem extends AnimatableActor{

	private ItemListInterface item;

	public ShopItem(ItemListInterface item) {
		super();
		this.item = item;
	}

	@Override
	public void update(float delta){}

	@Override
	public void render(Batch batch, float parentAlpha){}

	public void equipItem(){}

	public boolean buyItem(){
		item.setBought(hasEnough());
		return Save.take(item.getPrice());
	}

	public boolean hasEnough(){
		return Save.getMoney() >= item.getPrice();
	}

	public boolean isBought(){
		return item.isBought();
	}

	public int getPrice(){
		return item.getPrice();
	}

	public String getName(){
		return item.getName();
	}

}