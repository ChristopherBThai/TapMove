package com.mygdx.ui.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.mygdx.game.MyGame;
import com.mygdx.managers.ScreenManager;
import com.mygdx.managers.SoundManager;
import com.mygdx.screen.GameScreen;
import com.mygdx.utils.actors.ActorAnimator;
import com.mygdx.utils.actors.BoxButton;
import com.mygdx.managers.SpriteManager;


public class Pause {

    private Stage stage;

    private BoxButton button,menu,mute,resume;
    private ActorAnimator pause,unpause;
    private float x,y,width,height;

    public Pause(Stage stage){
        this.stage = stage;
        this.initSizes();
    }

    public void initSizes(){
        width = Gdx.app.getGraphics().getWidth()*.1f;
        height = width;
        x = Gdx.app.getGraphics().getWidth()*.95f-width;
        y = Gdx.app.getGraphics().getHeight()*.975f-height;

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
        float gap = buttonHeight*1.3f;

        mute = new BoxButton(buttonX,buttonY+gap,buttonWidth,buttonHeight);
        menu = new BoxButton(buttonX,buttonY,buttonWidth,buttonHeight);
        menu.setText("Menu");
        resume = new BoxButton(buttonX,buttonY-gap,buttonWidth,buttonHeight);
        resume.setText("Resume");

        pause = new ActorAnimator();
        pause.addCommand(new ActorAnimator.ActionCommand(){
            @Override
            public void command(ActorAnimator animator){
                GameScreen.entMan.light.animateTo(0f,.1f);
                button.setLockInside(true);
                button.setAnimateInsideOpacity(0f);
                GameScreen.hudMan.lifeBar.hide();
            }
        });
        pause.animateTo(px,py,pwidth,pheight,.1f);
        pause.addCommand(new ActorAnimator.ActionCommand(){
            @Override
            public void command(ActorAnimator animator){
                mute.animateToVisible();
                mute.addTouch();
                menu.animateToVisible();
                menu.addTouch();
                resume.animateToVisible();
                resume.addTouch();
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
                menu.setAnimateOpacity(0f);
                menu.setAnimateInsideOpacity(0f);
                menu.removeTouch();
                resume.setAnimateOpacity(0f);
                resume.setAnimateInsideOpacity(0f);
                resume.removeTouch();
                GameScreen.hudMan.lifeBar.show();
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
        if(mute.tap(x,y)){
            SoundManager.toggleMute();
            updateMuteText();
        }else if(menu.tap(x,y)){
            SoundManager.BGM_GAME.stop();
            SoundManager.BGM_MENU.play();
            ScreenManager.setScreen(MyGame.menuScreen,false, false);
            GameScreen.pause = false;
        }else if(resume.tap(x,y)){
            button.setAnimation(unpause);
            GameScreen.pause = false;
        }else if(!GameScreen.pause&&button.tap(x, y)){
            GameScreen.pause = true;
            button.setAnimation(pause);
            return true;
        }
        return false;
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
        button.setBounds(x,y,width,height);
        button.setInsideOpacity(1f);
        button.setLockInside(false);
        button.setAnimation(null);
        GameScreen.entMan.light.resetLights();
        stage.addActor(mute);
        mute.setOpacity(0f);
        mute.removeTouch();
        mute.setAnimation(null);
        stage.addActor(menu);
        menu.setOpacity(0f);
        menu.removeTouch();
        menu.setAnimation(null);
        stage.addActor(resume);
        resume.setOpacity(0f);
        resume.removeTouch();
        resume.setAnimation(null);
    }
}
