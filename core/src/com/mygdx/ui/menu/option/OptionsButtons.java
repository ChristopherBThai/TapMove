package com.mygdx.ui.menu.option;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.mygdx.game.MyGame;
import com.mygdx.managers.ScreenManager;
import com.mygdx.screen.GameScreen;
import com.mygdx.screen.MenuScreen;
import com.mygdx.ui.menu.BackButton;
import com.mygdx.utils.Save;
import com.mygdx.utils.actors.ActorAnimator;
import com.mygdx.utils.actors.BoxButton;

/**
 * Created by Christopher Thai on 7/19/2016.
 */
public class OptionsButtons {
    private MenuScreen screen;
    private Stage stage;

    private float width, height;
    private float tutorialX,tutorialY;
    private BoxButton tutorial;
    private ActorAnimator tutorialClicked;

    private BackButton back;

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
            stage.addActor(tutorial);
        }else{
            stage.addActor(back.getActor());
            stage.addActor(tutorial);
        }
    }

    public void setBounds(){
        width = Gdx.graphics.getWidth()*.47f;
        height = Gdx.graphics.getHeight()*.08f;

        tutorialX = Gdx.graphics.getWidth()*.5f-width*.5f;
        tutorialY = Gdx.graphics.getHeight()*.5f+height*.5f;
    }

    public void setActors(){
        back = new com.mygdx.ui.menu.BackButton(){
            @Override
            public void back(){
                stage.clear();
                MenuScreen.menu.set();
            }
        };

        tutorial = new BoxButton(tutorialX,tutorialY,width,height){
            @Override
            public void justTouched(){
                tutorial.setAnimation(tutorialClicked);
            }
        };
        tutorial.setThickness(.1f);
        tutorial.setText("Enable Tutorial");
    }

    public void setActions(){



        tutorialClicked = new ActorAnimator();
        tutorialClicked.addCommand(new ActorAnimator.ActionCommand(){
            @Override
            public void command(ActorAnimator animator){
                tutorial.removeTouch();
                tutorial.setAnimateInsideOpacity(.3f);
                tutorial.setAnimateOpacity(.3f);
                Save.setTutorial(true);
            }
        });

    }

    public void resetScreen(){
        back.resetScreen();
        if(Save.isTutorial()){
            tutorial.removeTouch();
            tutorial.setOpacity(0f);
            tutorial.setAnimateOpacity(.3f);
            tutorial.setAnimateInsideOpacity(.3f);
        }else{
            tutorial.addTouch();
            tutorial.setOpacity(0f);
            tutorial.setAnimateOpacity(1f);
            tutorial.setAnimateInsideOpacity(1f);
        }
    }

    public void dispose(){
        back.dispose();
    }
}
