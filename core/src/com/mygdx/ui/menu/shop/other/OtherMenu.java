package com.mygdx.ui.menu.shop.other;

import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.mygdx.screen.MenuScreen;
import com.mygdx.ui.menu.BackButton;
import com.mygdx.ui.menu.MoneyDisplay;

public class OtherMenu {
    private MenuScreen screen;
    private Stage stage;

    private BackButton back;
    private MoneyDisplay money;

    public OtherMenu(MenuScreen screen){
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
    }

    public void setActions(){
    }

    public void resetScreen(){
        back.resetScreen();
        money.resetScreen();
    }

    public void dispose(){
        back.dispose();
        money.dispose();
    }

}
