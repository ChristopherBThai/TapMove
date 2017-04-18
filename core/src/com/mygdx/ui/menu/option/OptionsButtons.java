package com.mygdx.ui.menu.option;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.mygdx.managers.SoundManager;
import com.mygdx.screen.MenuScreen;
import com.mygdx.ui.menu.ButtonLayout;
import com.mygdx.ui.menu.extra.actors.BackButton;
import com.mygdx.utils.Save;
import com.mygdx.utils.actors.ActorAnimator;
import com.mygdx.utils.actors.BoxButton;

/**
 * Created by Christopher Thai on 7/19/2016.
 */
public class OptionsButtons extends ButtonLayout{

    BoxButton tutorial;

    public OptionsButtons(){
        super("Options",false,2,false);
        buttons.get(0).setText("Tutorial");
        buttons.get(1).setText("Sound: On");
        buttons.get(1).setTextScale(.85f);
        tutorial = buttons.get(0);
    }

    @Override
    public void set(boolean withReset){
        super.set(withReset);
        updateMuteText();
    }

    @Override
    public void resetScreen(){
        super.resetScreen();
        if(Save.isTutorial()){
            tutorial.removeTouch();
            tutorial.setOpacity(0f);
            tutorial.setAnimateOpacity(.2f);
            tutorial.setAnimateInsideOpacity(.2f);
        }else{
            tutorial.addTouch();
            tutorial.setOpacity(0f);
            tutorial.setAnimateOpacity(1f);
            tutorial.setAnimateInsideOpacity(1f);
        }
    }

    @Override
    protected void buttonTouched(int index){

    }

    @Override
    protected void buttonActivated(int index){
        switch(index){
            case 0:
                tutorial.removeTouch();
                tutorial.setAnimateOpacity(.2f);
                tutorial.setAnimateInsideOpacity(.2f);
                Save.setTutorial(true);
                break;
            case 1:
                SoundManager.toggleMute();
                SoundManager.BGM_MENU.play();
                updateMuteText();
                break;
        }
    }

    @Override
    protected void backClicked(){
        MenuScreen.menu.set(false);
    }

    private void updateMuteText(){
        if(SoundManager.isMute())
            buttons.get(1).setText("Sound: Off");
        else
            buttons.get(1).setText("Sound: On");
    }

}
