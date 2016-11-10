package com.mygdx.ui.menu.shop.cosmetics;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.mygdx.managers.ColorManager;
import com.mygdx.managers.SpriteManager;
import com.mygdx.screen.GameScreen;
import com.mygdx.ui.menu.shop.ShopItems.ItemListInterface;
import com.mygdx.ui.menu.shop.ShopItems.ShopItem;
import com.mygdx.ui.menu.shop.ShopItems.ShopList;

/**
 * Created by Christopher Thai on 11/10/2016.
 */

public enum DesignList implements ItemListInterface{
	NONE(0,"Blank",null,true),
	QUARTER_SLICE(1000,"Checkers", SpriteManager.getCheckers(),false);

	private final int cost;
	private final String name;
	private final Sprite sprite;
	private final static Sprite background = SpriteManager.getCircle();
	private final boolean isBought;

	DesignList(int cost, String name, Sprite sprite, boolean bought){
		this.cost = cost;
		this.name = name;
		this.sprite = sprite;
		this.isBought = bought;

	}

	@Override
	public void addToList(ShopList list){

		ShopItem temp = new ShopItem(cost,name, SpriteManager.getCircle(),isBought){
			@Override
			public void equipItem(){
				GameScreen.entMan.player.setDesign(sprite);
			}

			@Override
			public void render(Batch batch, float parentAlpha){
				batch.setColor(ColorManager.PLAYER.r,ColorManager.PLAYER.g,ColorManager.PLAYER.b,opacity);
				batch.draw(background,getX(),getY(),getWidth(),getHeight());
				if(sprite!=null){
					batch.setColor(ColorManager.PLAYER_DESIGN.r,ColorManager.PLAYER_DESIGN.g,ColorManager.PLAYER_DESIGN.b,opacity);
					batch.draw(sprite,getX(),getY(),getWidth(),getHeight());
				}
			}
		};

		list.add(temp);
	}
}
