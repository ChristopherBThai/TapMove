package com.mygdx.ui.menu.shop;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.mygdx.screen.MenuScreen;
import com.mygdx.ui.menu.ButtonLayout;
import com.mygdx.ui.menu.extra.actors.BackButton;
import com.mygdx.utils.actors.ActorAnimator;
import com.mygdx.utils.actors.BoxButton;


public class ShopButtons extends ButtonLayout{


    public ShopButtons(){
        super("Shop",true,3,true);
        buttons.get(0).setText("Cosmetics");
        buttons.get(1).setText("Abilities");
        buttons.get(2).setText("Other");
    }

    @Override
    protected void buttonTouched(int index){

    }

    @Override
    protected void buttonActivated(int index){
        switch(index){
            case 0:
                MenuScreen.cosmetics.set(true);
                break;
            case 1:
                MenuScreen.abilities.set(true);
                break;
            case 2:
                MenuScreen.other.set(true);
                break;
        }
    }

    @Override
    protected void backClicked(){
        MenuScreen.menu.set(false);
    }
}
