package com.mygdx.ui.menu.shop.cosmetics;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.mygdx.managers.ColorManager;
import com.mygdx.managers.SpriteManager;
import com.mygdx.screen.MenuScreen;
import com.mygdx.ui.menu.BackButton;
import com.mygdx.ui.menu.shop.ShopItems.ShopItem;
import com.mygdx.ui.menu.shop.ShopItems.ShopList;
import com.mygdx.ui.menu.shop.ShopItems.YesNoButton;

/**
 * Created by Mono on 6/20/2016.
 */
public class CosmeticsMenu {
    private MenuScreen screen;
    private Stage stage;

    private BackButton back;
    private YesNoButton playerColor,playerDesign,playerTrail;

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
        stage.addActor(back.getActor());
        playerColor.doAnimation();
        playerColor.addActor(stage);
        playerDesign.doAnimation();
        playerDesign.addActor(stage);
        playerTrail.doAnimation();
        playerTrail.addActor(stage);
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


        ShopList list1 = new ShopList("Color");
        list1.add(ColorList.values());
        playerColor = new YesNoButton(list1,Gdx.graphics.getWidth()*.5f,Gdx.graphics.getHeight()*.5f,stage);

        ShopList list2 = new ShopList("Design");
        list2.add(DesignList.values());
        playerDesign = new YesNoButton(list2,Gdx.graphics.getWidth()*.5f,Gdx.graphics.getHeight()*.8f,stage);

        ShopList list3 = new ShopList("Trails");
        list3.add(ParticleList.values());
        playerTrail = new YesNoButton(list3,Gdx.graphics.getWidth()*.5f,Gdx.graphics.getHeight()*.2f,stage);
    }

    public void setActions(){
    }

    public void resetScreen(){
        back.resetScreen();
        playerColor.resetScreen();
        playerDesign.resetScreen();
        playerTrail.resetScreen();
    }

    public void dispose(){
        back.dispose();
        playerColor.dispose();
        playerDesign.dispose();
        playerTrail.dispose();
    }
}
