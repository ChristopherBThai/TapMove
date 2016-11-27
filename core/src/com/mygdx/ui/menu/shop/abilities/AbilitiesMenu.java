package com.mygdx.ui.menu.shop.abilities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.mygdx.screen.MenuScreen;
import com.mygdx.managers.SpriteManager;
import com.mygdx.ui.menu.BackButton;
import com.mygdx.ui.menu.MoneyDisplay;
import com.mygdx.ui.menu.shop.ShopItems.ShopList;
import com.mygdx.ui.menu.shop.ShopItems.YesNoButton;
import com.mygdx.ui.menu.shop.cosmetics.ColorList;
import com.mygdx.utils.actors.Text;


public class AbilitiesMenu {
    MenuScreen screen;
    Stage stage;


    private YesNoButton abilities;

    private MoneyDisplay money;

    BackButton back;

    public AbilitiesMenu(MenuScreen screen){
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
        money.addToStage(stage);
        money.doAnimation();
        abilities.addActor(stage);
        abilities.doAnimation();
    }

    public void setBounds(){

    }

    public void setActors(){
        back = new com.mygdx.ui.menu.BackButton(){
            @Override
            public void back(){
                stage.clear();
                MenuScreen.shop.set(false);
            }
        };

        money = new MoneyDisplay();

        ShopList list1 = new ShopList("Abilities");
        list1.add(AbilityList.values());
        abilities = new YesNoButton(list1,Gdx.graphics.getWidth()*.5f,Gdx.graphics.getHeight()*.5f,stage);
    }

    public void setActions(){
    }

    public void resetScreen(){
        back.resetScreen();
        money.resetScreen();
        abilities.resetScreen();
    }

    public void dispose(){
        back.dispose();
        money.dispose();
        abilities.dispose();
    }
}
