package com.mygdx.ui.game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.mygdx.managers.SpriteManager;
import com.mygdx.screen.GameScreen;
import com.mygdx.utils.actors.BoxButton;
import com.mygdx.utils.actors.Image;

/**
 * Created by Christopher Thai on 10/17/2016.
 */
public class LifeBar {

    private Stage stage;

    private BoxButton border;
    private Image lifebar;

    private float x,y,width,height;
    private float barX,barY,barWidth,barHeight;

    public LifeBar(Stage stage){
        this.stage = stage;
        init();
        stage.addActor(border);
        stage.addActor(lifebar);
    }

    private void init(){
        width = Gdx.app.getGraphics().getWidth()*.8f;
        height = Gdx.app.getGraphics().getHeight()*.06f;
        x = (Gdx.app.getGraphics().getWidth()-width)/2;
        y = Gdx.app.getGraphics().getHeight()*.07f;

        border = new BoxButton(x,y,width,height);
        //border.setThickness(.4f);
        border.removeTouch();
        border.setOpacity(.6f);

        float gap = height *.15f;
        barWidth = width-gap*2;
        barHeight = height-gap*2;
        barX = x+gap;
        barY = y+gap;

        lifebar = new Image(SpriteManager.BOX,barX,barY,barWidth,barHeight);
        lifebar.setOpacity(.6f);
    }

    public void update(float delta){
        lifebar.setWidth(barWidth*GameScreen.entMan.player.getLifePercent());
        if(lifebar.getWidth()<0)
            lifebar.setWidth(0);
    }

    public void reset(){
        lifebar.setBounds(barX,barY,barWidth,barHeight);
        stage.addActor(border);
        stage.addActor(lifebar);
    }

    public void dispose(){
        border.dispose();
    }

}
