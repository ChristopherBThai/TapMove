package com.mygdx.ui.menu.option;

import com.badlogic.gdx.scenes.scene2d.Stage;
import com.mygdx.screen.MenuScreen;
import com.mygdx.ui.menu.BackButton;

/**
 * Created by Christopher Thai on 7/19/2016.
 */
public class OptionsButtons {
    MenuScreen screen;
    Stage stage;

    BackButton back;

    public OptionsButtons(MenuScreen screen){
        this.screen = screen;
        this.stage = screen.stage;
        this.setBounds();
        this.setActors();
        this.setActions();
    }

    public void set(boolean withReset){
        if(withReset) {
            resetScreen();
            back.doAnimation();
            stage.addActor(back.getActor());
        }else{
            stage.addActor(back.getActor());
        }
    }

    public void setBounds(){

    }

    public void setActors(){
        back = new com.mygdx.ui.menu.BackButton(){
            @Override
            public void back(){
                stage.clear();
                MenuScreen.menu.set();
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
