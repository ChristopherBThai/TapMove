package com.mygdx.ui.menu.shop.ShopItems;

/**
 * Created by Christopher Thai on 10/27/2016.
 */

public interface ItemListInterface{
	void addToList(ShopList list);
	void equip();
	String getCurrent();
	boolean isBought();
	void setEquipped(String name);
	void setBought(boolean isBought);
	int getPrice();
	String getName();
}
