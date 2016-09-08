package com.mygdx.ui.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.mygdx.screen.GameScreen;
import com.mygdx.utils.actors.ActorAnimator;
import com.mygdx.utils.actors.BoxButton;
import com.mygdx.managers.SpriteManager;


public class Pause {

    Stage stage;

    BoxButton button,menu,option,resume;
    ActorAnimator pause,unpause;

    boolean prevPause;

    public Pause(Stage stage){
        this.stage = stage;
        this.initSizes();
    }

    public void initSizes(){
        float width = Gdx.app.getGraphics().getWidth()*.1f;
        float height = width;
        float x = Gdx.app.getGraphics().getWidth()*.95f-width;
        float y = Gdx.app.getGraphics().getHeight()*.975f-height;

        float pwidth = Gdx.app.getGraphics().getWidth()*.85f;
        float pheight = Gdx.app.getGraphics().getHeight()*.85f;
        float px = (Gdx.app.getGraphics().getWidth() - pwidth)/2f;
        float py = (Gdx.app.getGraphics().getHeight() - pheight)/2f;


        button = new BoxButton(x,y,width,height);
        button.setInside(SpriteManager.getPause());
        button.setInsideScale(.7f);
        stage.addActor(button);

        pause = new ActorAnimator();
        pause.addCommand(new ActorAnimator.ActionCommand(){
            @Override
            public void command(ActorAnimator animator){
                GameScreen.entMan.light.animateTo(0f,.1f);
                GameScreen.hudMan.ability.hideButton();
                button.setInside(null);
            }
        });
        pause.animateTo(px,py,pwidth,pheight,.1f);

        unpause = new ActorAnimator();
        unpause.addCommand(new ActorAnimator.ActionCommand(){
            @Override
            public void command(ActorAnimator animator){
                GameScreen.entMan.light.resetLights();
                GameScreen.hudMan.ability.showButton();
            }
        });
        unpause.animateTo(x,y,width,height,.1f);
        unpause.addCommand(new ActorAnimator.ActionCommand(){
            @Override
            public void command(ActorAnimator animator){
                button.setInside(SpriteManager.getPause());
            }
        });
    }

    public void update(float delta){
    }

    public boolean tap(float x, float y){
       // if(!GameScreen.pause) {
            if(button.tap(x, y)){
                GameScreen.pause = !GameScreen.pause;
                if(GameScreen.pause)           //Just paused
                    button.setAnimation(pause);
                else                          //Just unpaused
                    button.setAnimation(unpause);

                return true;
            }
            return false;
        //}
       // return false;
    }

    public void reset(){
        stage.addActor(button);
    }
}
