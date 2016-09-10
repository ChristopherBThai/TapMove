package com.mygdx.ui.menu.shop.cosmetics;

import com.badlogic.gdx.scenes.scene2d.Stage;
import com.mygdx.screen.MenuScreen;
import com.mygdx.ui.menu.BackButton;

/**
 * Created by Mono on 6/20/2016.
 */
public class CosmeticsMenu {
    MenuScreen screen;
    Stage stage;

    BackButton back;

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
    }

    public void setActions(){
    }

    public void resetScreen(){
        back.resetScreen();
    }

    public void dispose(){
        back.dispose();
    }
}