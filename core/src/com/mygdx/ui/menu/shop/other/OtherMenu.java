package com.mygdx.ui.menu.shop.other;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.mygdx.game.MyGame;
import com.mygdx.screen.MenuScreen;
import com.mygdx.ui.menu.BackButton;
import com.mygdx.ui.menu.MoneyDisplay;
import com.mygdx.utils.Save;
import com.mygdx.utils.actors.ActorAnimator;
import com.mygdx.utils.actors.BoxButton;

public class OtherMenu {
    private MenuScreen screen;
    private Stage stage;

    private float width, height;
    private float adX,adY,moneyX,moneyY;
    private BoxButton ads,money;
    private ActorAnimator adClicked,moneyClicked;

    private BackButton back;
    private MoneyDisplay moneyDisplay;

    public OtherMenu(MenuScreen screen){
        this.screen = screen;
        this.stage = screen.stage;
        setBounds();
        setActors();
        setActions();
    }

    public void set(boolean withReset){
        resetScreen();
        back.doAnimation();
        stage.addActor(back.getActor());
        moneyDisplay.addToStage(stage);
        moneyDisplay.doAnimation();
        stage.addActor(ads);
        if(Save.adsEnabled()){
            ads.animateToVisible();
            ads.addTouch();
        }else{
            ads.removeTouch();
            ads.setOpacity(0f);
            ads.setAnimateOpacity(.3f);
            ads.setAnimateInsideOpacity(.3f);
        }
        stage.addActor(money);
        money.animateToVisible();
    }

    public void setBounds(){
        width = Gdx.graphics.getWidth()*.47f;
        height = Gdx.graphics.getHeight()*.08f;

        adX = Gdx.graphics.getWidth()*.5f-width*.5f;
        adY = Gdx.graphics.getHeight()*.6f+height*.5f;

        moneyX = Gdx.graphics.getWidth()*.5f-width*.5f;
        moneyY = Gdx.graphics.getHeight()*.4f+height*.5f;
    }

    public void setActors(){
        back = new com.mygdx.ui.menu.BackButton(){
            @Override
            public void back(){
                stage.clear();
                MenuScreen.shop.set(false);
            }
        };

        moneyDisplay = new MoneyDisplay();

        ads = new BoxButton(adX,adY,width,height){
            @Override
            public void justTouched(){
                ads.setAnimation(adClicked);
            }
        };
        ads.setThickness(.1f);
        ads.setText("Remove Ads");

        money = new BoxButton(moneyX,moneyY,width,height){
            @Override
            public void justTouched(){
                money.setAnimation(moneyClicked);
            }
        };
        money.setThickness(.1f);
        money.setText("1000");
    }

    public void setActions(){
        adClicked = new ActorAnimator();
        adClicked.addCommand(new ActorAnimator.ActionCommand(){
            @Override
            public void command(ActorAnimator animator){
                MyGame.buyAds();
            }
        });
        moneyClicked = new ActorAnimator();
        moneyClicked.addCommand(new ActorAnimator.ActionCommand(){
            @Override
            public void command(ActorAnimator animator){
                MyGame.buyMoney();
            }
        });
    }

    public void resetScreen(){
        back.resetScreen();
        moneyDisplay.resetScreen();
    }

    public void dispose(){
        back.dispose();
        money.dispose();
        moneyDisplay.dispose();
        ads.dispose();
    }

}
