package com.mygdx.ui.menu.shop.cosmetics;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.mygdx.managers.ColorManager;
import com.mygdx.managers.SpriteManager;
import com.mygdx.screen.MenuScreen;
import com.mygdx.ui.menu.BackButton;
import com.mygdx.ui.menu.shop.ShopItems.ShopItem;
import com.mygdx.ui.menu.shop.ShopItems.ShopList;

/**
 * Created by Mono on 6/20/2016.
 */
public class CosmeticsMenu {
    MenuScreen screen;
    Stage stage;

    BackButton back;
    YesNoButton playerColor;

    public CosmeticsMenu(MenuScreen screen){
        this.screen = screen;
        this.stage = screen.stage;
        setBounds();
        setActors();
        setActions();
    }

    public void set(boolean withReset){
        resetScreen();
        back.doAnimation();
        playerColor.doAnimation();
        stage.addActor(back.getActor());
        playerColor.addActor(stage);
    }

    public void setBounds(){

    }

    public void setActors(){
        back = new BackButton(){
            @Override
            public void back(){
                stage.clear();
                MenuScreen.shop.set(false);
            }
        };
        ShopList list = new ShopList("Color");
        ShopItem temp = new ShopItem(100,"Blue", SpriteManager.getCircle());
        temp.setColor(ColorManager.BLUE);
        list.add(temp);
        temp = new ShopItem(100,"RED", SpriteManager.getCircle());
        temp.setColor(ColorManager.RED);
        list.add(temp);
        temp = new ShopItem(100,"GREEN", SpriteManager.getCircle());
        temp.setColor(ColorManager.GREEN);
        list.add(temp);
        playerColor = new YesNoButton(list,Gdx.graphics.getWidth()*.5f,Gdx.graphics.getHeight()*.5f,stage);
    }

    public void setActions(){
    }

    public void resetScreen(){
        back.resetScreen();
        playerColor.resetScreen();
    }

    public void dispose(){
        back.dispose();
        playerColor.dispose();
    }
}
