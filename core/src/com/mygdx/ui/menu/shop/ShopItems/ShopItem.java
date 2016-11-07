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
	private Sprite sprite;

	public ShopItem(){
		super();
		price = 0;
		bought = false;
		name = "null";
		sprite = null;
	}

	public ShopItem(int price, String name, Sprite sprite) {
		bought = false;
		this.price = price;
		this.name = name;
		this.sprite = sprite;
	}

	public ShopItem(int price, String name, Sprite sprite,boolean isBought) {
		bought = isBought;
		this.price = price;
		this.name = name;
		this.sprite = sprite;
	}

	@Override
	public void update(float delta){

	}

	@Override
	public void render(Batch batch, float parentAlpha){
		if(sprite != null){
			batch.setColor(getColor().r,getColor().g,getColor().b,opacity);
			batch.draw(sprite,getX(),getY(),getWidth(),getHeight());
		}
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

}