package com.mygdx.ui.menu.shop.other;

import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.mygdx.screen.MenuScreen;

public class OtherMenu {
    MenuScreen screen;
    Stage stage;

    Actor act;
    com.mygdx.ui.menu.BackButton back;

    public OtherMenu(MenuScreen screen){
        this.screen = screen;
        this.stage = screen.stage;
        setBounds();
        setActors();
        setActions();
    }

    public void update(float delta){

    }

    public void set(boolean withReset){
        resetScreen();
        back.doAnimation();
        stage.addActor(back.getActor());
        stage.addActor(act);
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
        act = new Actor(){
            @Override
            public void act(float delta){
                update(delta);
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
