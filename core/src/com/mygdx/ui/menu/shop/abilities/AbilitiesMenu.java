package com.mygdx.ui.menu.shop.abilities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.mygdx.screen.MenuScreen;
import com.mygdx.managers.SpriteManager;
import com.mygdx.ui.menu.MoneyDisplay;
import com.mygdx.utils.actors.Text;


public class AbilitiesMenu {
    MenuScreen screen;
    Stage stage;

    private Text text;
    private MoneyDisplay money;


    com.mygdx.ui.menu.BackButton back;

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
        text.animateToVisible();
        stage.addActor(back.getActor());
        stage.addActor(text);
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

        text = new Text(140,"Coming soon!");
        text.setPosition(Gdx.graphics.getWidth()/2-text.getWidth()/2,Gdx.graphics.getHeight()/2+text.getHeight()/2);

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
        text.dispose();
        money.dispose();
    }
}
