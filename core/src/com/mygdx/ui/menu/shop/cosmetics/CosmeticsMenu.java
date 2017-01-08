package com.mygdx.ui.menu.shop.cosmetics;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.mygdx.screen.MenuScreen;
import com.mygdx.ui.menu.BackButton;
import com.mygdx.ui.menu.MoneyDisplay;
import com.mygdx.ui.menu.shop.ShopItems.ShopList;
import com.mygdx.ui.menu.shop.ShopItems.YesNoButton;
import com.mygdx.utils.actors.Text;

/**
 * Created by Christopher Thai on 6/20/2016.
 */
public class CosmeticsMenu {
    private MenuScreen screen;
    private Stage stage;

    private BackButton back;
    private MoneyDisplay money;
    private YesNoButton playerColor,playerDesign,playerTrail;
    private Text colorText,designText,trailText;

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
        money.addToStage(stage);
        money.doAnimation();
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

        playerColor = new YesNoButton(ColorList.getShopList(),Gdx.graphics.getWidth()*.5f,Gdx.graphics.getHeight()*.5f,stage);

        playerDesign = new YesNoButton(DesignList.getShopList(),Gdx.graphics.getWidth()*.5f,Gdx.graphics.getHeight()*.8f,stage);

        playerTrail = new YesNoButton(ParticleList.getShopList(),Gdx.graphics.getWidth()*.5f,Gdx.graphics.getHeight()*.2f,stage);

        money = new MoneyDisplay();
    }

    public void setActions(){
    }

    public void resetScreen(){
        back.resetScreen();
        playerColor.resetScreen();
        playerDesign.resetScreen();
        playerTrail.resetScreen();
        money.resetScreen();
    }

    public void dispose(){
        back.dispose();
        playerColor.dispose();
        playerDesign.dispose();
        playerTrail.dispose();
        money.dispose();
    }
}
