package com.mygdx.utils.ui.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.mygdx.game.MyGame;
import com.mygdx.screen.GameScreen;
import com.mygdx.screen.MenuScreen;
import com.mygdx.utils.actors.BoxButton;
import com.mygdx.utils.managers.ScreenManager;


public class GameoverButtons {

    GameScreen game;
    Stage stage;

    BoxButton menu,restart;

    public GameoverButtons(Stage stage,GameScreen game){
        this.stage = stage;
        this.game = game;

        float width = Gdx.app.getGraphics().getWidth()*.5f;
        float height = width*.35f;
        float x = Gdx.app.getGraphics().getWidth()/2f-width/2f;
        float y = Gdx.app.getGraphics().getHeight()*.5f-height/2f;

        menu = new BoxButton(x,y,width,height);
        menu.setText("Main Menu");
        menu.setTextScale(.6f);
        menu.setThickness(.12f);
        restart = new BoxButton(x,menu.getY()-height*1.2f,width,height);
        restart.setText("Play Again");
        restart.setTextScale(menu.getTextScale());
        restart.setThickness(menu.getThickness());
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
            result = true;
            ScreenManager.setScreen(MyGame.menuScreen,false, false);
        }
        return result;
    }

    public void reset(){

    }

}
