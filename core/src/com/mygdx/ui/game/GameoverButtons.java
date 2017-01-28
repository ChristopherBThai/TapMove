package com.mygdx.ui.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.mygdx.game.MyGame;
import com.mygdx.managers.SoundManager;
import com.mygdx.screen.GameScreen;
import com.mygdx.utils.actors.BoxButton;
import com.mygdx.managers.ScreenManager;


public class GameoverButtons {

    private GameScreen game;
    private Stage stage;

    private BoxButton menu,restart;

    public GameoverButtons(Stage stage,GameScreen game){
        this.stage = stage;
        this.game = game;

        float width = Gdx.app.getGraphics().getWidth()*.5f;
        float height = width*.35f;
        float x = Gdx.app.getGraphics().getWidth()/2f-width/2f;
        float y = Gdx.app.getGraphics().getHeight()*.5f-height/2f;

        restart = new BoxButton(x,y,width,height);
        restart.setText("PlayAgain");
        restart.setTextScale(.6f);
        restart.setThickness(.12f);
        menu = new BoxButton(x,restart.getY()-height*1.2f,width,height);
        menu.setText("Main Menu");
        menu.setTextScale(restart.getTextScale());
        menu.setThickness(restart.getThickness());
    }

    public void update(float delta){
        if(!GameScreen.running){
            stage.addActor(menu);
            stage.addActor(restart);
        }
    }

    public boolean tap(float x, float y){
        boolean result = false;
        if(restart.tap(x,y)) {
            result = true;
            game.restart();
        }
        if(menu.tap(x,y)) {
            SoundManager.BGM_GAME.stop();
            SoundManager.BGM_MENU.play();
            result = true;
            ScreenManager.setScreen(MyGame.menuScreen,false, false);
        }
        return result;
    }

    public void reset(){

    }

}
