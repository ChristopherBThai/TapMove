package com.mygdx.ui.menu.shop.other;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.mygdx.game.MyGame;
import com.mygdx.screen.MenuScreen;
import com.mygdx.ui.menu.ButtonLayout;
import com.mygdx.ui.menu.extra.actors.BackButton;
import com.mygdx.ui.menu.extra.actors.MoneyDisplay;
import com.mygdx.utils.Save;
import com.mygdx.utils.actors.ActorAnimator;
import com.mygdx.utils.actors.BoxButton;

public class OtherMenu extends ButtonLayout{

    public OtherMenu(){
        super("Other",true,2,false);
        buttons.get(0).setText("Remove Ads");
        buttons.get(1).setText("Buy 1000");
    }

    @Override
    public void resetScreen(){
        super.resetScreen();
        BoxButton ads = buttons.get(1);
        if(!Save.adsEnabled()){
            ads.removeTouch();
            ads.setOpacity(0f);
            ads.setAnimateOpacity(.3f);
            ads.setAnimateInsideOpacity(.3f);
        }
    }

    @Override
    protected void buttonTouched(int index){

    }

    @Override
    protected void buttonActivated(int index){
        switch(index){
            case 0:
                MyGame.buyAds();
                break;
            case 1:
                MyGame.buyMoney();
                break;
        }
    }

    @Override
    protected void backClicked(){
        MenuScreen.shop.set(false);
    }

}
