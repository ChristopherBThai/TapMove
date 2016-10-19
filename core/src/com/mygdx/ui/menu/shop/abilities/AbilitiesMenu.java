package com.mygdx.ui.menu.shop.abilities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.mygdx.screen.MenuScreen;
import com.mygdx.managers.SpriteManager;
import com.mygdx.utils.actors.Text;


public class AbilitiesMenu {
    MenuScreen screen;
    Stage stage;

    //com.mygdx.ui.menu.shop.Scrollable.Scroll scroll;
    Text text;


    com.mygdx.ui.menu.BackButton back;

    public AbilitiesMenu(MenuScreen screen){
        this.screen = screen;
        this.stage = screen.stage;
        setBounds();
        setActors();
        setActions();
    }

    public void set(boolean withReset){
        resetScreen();
        back.doAnimation();
        text.setOpacity(0f);
        text.setAnimateOpacity(1f);
        //scroll.set();
        stage.addActor(back.getActor());
        stage.addActor(text);
    }

    public void setBounds(){

    }

    public void setActors(){
        back = new com.mygdx.ui.menu.BackButton(){
            @Override
            public void back(){
                stage.clear();
                MenuScreen.shop.set(false);
            }
        };

        text = new Text(180,"Coming soon!");
        text.setPosition(Gdx.graphics.getWidth()/2-text.getWidth()/2,Gdx.graphics.getHeight()/2+text.getHeight()/2);

        //scroll = new com.mygdx.ui.menu.shop.Scrollable.Scroll(stage);
        //for(int i=0;i<80;i++){
        //    scroll.addButton(SpriteManager.getCircle());
        //}
    }

    public void setActions(){
    }

    public void resetScreen(){
        back.resetScreen();
    }

    public void dispose(){
        back.dispose();
        text.dispose();
    }
}
