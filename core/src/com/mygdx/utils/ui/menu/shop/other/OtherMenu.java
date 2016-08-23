package com.mygdx.utils.ui.menu.shop.other;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.mygdx.screen.MenuScreen;
import com.mygdx.utils.actors.ActorAnimator;
import com.mygdx.utils.actors.BoxButton;
import com.mygdx.utils.managers.AnimationManager;
import com.mygdx.utils.ui.menu.BackButton;

public class OtherMenu {
    MenuScreen screen;
    Stage stage;

    Actor act;
    BackButton back;

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
        back = new BackButton(){
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
