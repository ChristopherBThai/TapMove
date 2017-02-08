package com.mygdx.ui.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.mygdx.managers.SoundManager;
import com.mygdx.screen.GameScreen;
import com.mygdx.utils.actors.ActorAnimator;
import com.mygdx.utils.actors.BoxButton;
import com.mygdx.managers.SpriteManager;


public class Pause {

    private Stage stage;

    private BoxButton button,menu,mute,resume;
    private ActorAnimator pause,unpause;

    private boolean prevPause;

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
        button.setInside(SpriteManager.PAUSE);
        button.setInsideScale(.7f);
        stage.addActor(button);

        float buttonWidth = pwidth*.8f;
        float buttonHeight = pwidth*.2f;
        float buttonX =  Gdx.app.getGraphics().getWidth()*.5f-buttonWidth/2f;
        float buttonY = Gdx.app.getGraphics().getHeight()/2f-buttonHeight/2f;

        mute = new BoxButton(buttonX,buttonY,buttonWidth,buttonHeight);

        pause = new ActorAnimator();
        pause.addCommand(new ActorAnimator.ActionCommand(){
            @Override
            public void command(ActorAnimator animator){
                GameScreen.entMan.light.animateTo(0f,.1f);
                button.setLockInside(true);
                button.setAnimateInsideOpacity(0f);
            }
        });
        pause.animateTo(px,py,pwidth,pheight,.1f);
        pause.addCommand(new ActorAnimator.ActionCommand(){
            @Override
            public void command(ActorAnimator animator){
                mute.animateToVisible();
                mute.addTouch();
                updateMuteText();
            }
        });

        unpause = new ActorAnimator();
        unpause.addCommand(new ActorAnimator.ActionCommand(){
            @Override
            public void command(ActorAnimator animator){
                GameScreen.entMan.light.resetLights();
                mute.setAnimateOpacity(0f);
                mute.setAnimateInsideOpacity(0f);
                mute.removeTouch();
            }
        });
        unpause.animateTo(x,y,width,height,.1f);
        unpause.addCommand(new ActorAnimator.ActionCommand(){
            @Override
            public void command(ActorAnimator animator){
                button.setLockInside(false);
                button.setAnimateInsideOpacity(1f);
            }
        });
    }

    public void update(float delta){
    }

    public boolean tap(float x, float y){
       // if(!GameScreen.pause) {
            if(mute.tap(x,y)){
                SoundManager.toggleMute();
                updateMuteText();
            }else if(button.tap(x, y)){
                GameScreen.pause = !GameScreen.pause;
                if(GameScreen.pause){           //Just paused
                    button.setAnimation(pause);
                }else{                         //Just unpaused
                    button.setAnimation(unpause);

                }

                return true;
            }
            return false;
        //}
       // return false;
    }

    private void updateMuteText(){
        if(SoundManager.isMute())
            mute.setText("Sound: Off");
        else{
            mute.setText("Sound: On");
            SoundManager.BGM_GAME.play();
        }
    }

    public void reset(){
        stage.addActor(button);
        stage.addActor(mute);
        mute.setOpacity(0f);
        mute.removeTouch();
    }
}
