package com.mygdx.ui.menu.shop.abilities;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.mygdx.entities.abilities.Ability;
import com.mygdx.entities.abilities.BetterDash;
import com.mygdx.entities.abilities.Explosion;
import com.mygdx.entities.abilities.Invincible;
import com.mygdx.entities.abilities.Magnet;
import com.mygdx.entities.abilities.Shield;
import com.mygdx.entities.player.Player;
import com.mygdx.managers.ColorManager;
import com.mygdx.managers.SpriteManager;
import com.mygdx.screen.GameScreen;
import com.mygdx.ui.menu.shop.ShopItems.ItemListInterface;
import com.mygdx.ui.menu.shop.ShopItems.ShopItem;
import com.mygdx.ui.menu.shop.ShopItems.ShopList;

/**
 * Created by Christopher Thai on 11/26/2016.
 */

public enum AbilityList implements ItemListInterface{
	EXPLOSION(3000,"Explosion",new Explosion(),SpriteManager.BOMB,true),
	INVINCIBLE(3000,"Invincible",new Invincible(),SpriteManager.SPIKEY_CIRCLE,false),
	DASH_UPGRADE(3000,"Dash Upgrade",new BetterDash(),SpriteManager.BOOST,false),
	MAGNET(3000,"Magnet",new Magnet(),SpriteManager.MAGNET,false),
	SHIELD(3000,"Shield",new Shield(),SpriteManager.SHIELD,false);

	private final int cost;
	private final String name;
	private final Ability ability;
	private final SpriteManager sprite;
	private boolean bought;

	private static AbilityList current = EXPLOSION;
	private static Player player = GameScreen.entMan.player;

	AbilityList(int cost, String name, Ability ability, SpriteManager sprite, boolean bought){
		this.cost = cost;
		this.name = name;
		this.ability = ability;
		this.sprite = sprite;
		this.bought = bought;
	}

	@Override
	public void addToList(ShopList list){
		ShopItem temp = new ShopItem(this){
			@Override
			public void equipItem(){
				equip();
			}

			@Override
			public void render(Batch batch, float parentAlpha){
				if(sprite!=null){
					batch.setColor(ColorManager.NORMAL.r,ColorManager.NORMAL.g,ColorManager.NORMAL.b,opacity);
					batch.draw(sprite.getSprite(),getX(),getY(),getWidth(),getHeight());
				}
			}
		};

		list.add(temp);
	}

	@Override
	public void equip(){
		player.setAbility(ability);
		current = this;
	}

	@Override
	public String getCurrent(){
		return current.name;
	}

	@Override
	public boolean isBought(){
		return bought;
	}

	@Override
	public void setEquipped(String name){
		for(AbilityList item : AbilityList.values())
			if(item.name.equals(name))
				item.equip();
	}

	@Override
	public void setBought(boolean isBought){
		this.bought = isBought;
	}

	@Override
	public int getPrice(){
		return cost;
	}

	@Override
	public String getName(){
		return name;
	}
}
