package com.mygdx.ui.menu.shop.cosmetics;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.mygdx.screen.MenuScreen;
import com.mygdx.ui.menu.extra.actors.BackButton;
import com.mygdx.ui.menu.extra.actors.MoneyDisplay;
import com.mygdx.ui.menu.shop.ShopItems.YesNoButton;
import com.mygdx.utils.actors.Text;

/**
 * Created by Christopher Thai on 6/20/2016.
 */
public class CosmeticsMenu {
    private MenuScreen screen;
    private Stage stage;

    private BackButton back;
    private MoneyDisplay money;
    private YesNoButton playerColor,playerDesign,playerTrail;
    private Text colorText,designText,trailText;

    public CosmeticsMenu(MenuScreen screen){
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
        playerColor.doAnimation();
        playerColor.addActor(stage);
        playerDesign.doAnimation();
        playerDesign.addActor(stage);
        playerTrail.doAnimation();
        playerTrail.addActor(stage);
        money.addToStage(stage);
        money.doAnimation();
        stage.addActor(colorText);
        colorText.animateToVisible();
        stage.addActor(designText);
        designText.animateToVisible();
        stage.addActor(trailText);
        trailText.animateToVisible();
    }

    public void setBounds(){

    }

    public void setActors(){

        back = new BackButton(){
            @Override
            public void back(){
                stage.clear();
                MenuScreen.shop.set(false);
            }
        };

        float textGap = Gdx.graphics.getWidth()*.14f;

        playerColor = new YesNoButton(ColorList.getShopList(),0,0,stage);
        playerColor.setPos(Gdx.graphics.getWidth()*.7f-playerColor.getWidth()/2,Gdx.graphics.getHeight()*.78f-playerColor.getHeight()/2);
        colorText = new Text(Gdx.graphics.getWidth()*.06f,"Color");
        colorText.setPosition(playerColor.getX()-textGap-colorText.getWidth(),playerColor.getY()+playerColor.getHeight()/2+colorText.getHeight()/2);

        playerDesign = new YesNoButton(DesignList.getShopList(),0,0,stage);
        playerDesign.setPos(Gdx.graphics.getWidth()*.7f-playerDesign.getWidth()/2,Gdx.graphics.getHeight()*.55f-playerDesign.getHeight()/2);
        designText = new Text(Gdx.graphics.getWidth()*.06f,"Design");
        designText.setPosition(playerDesign.getX()-textGap-designText.getWidth(),playerDesign.getY()+playerDesign.getHeight()/2+designText.getHeight()/2);

        playerTrail = new YesNoButton(ParticleList.getShopList(),0,0,stage);
        playerTrail.setPos(Gdx.graphics.getWidth()*.7f-playerTrail.getWidth()/2,Gdx.graphics.getHeight()*.32f-playerTrail.getHeight()/2);
        trailText = new Text(Gdx.graphics.getWidth()*.06f,"Trail");
        trailText.setPosition(playerTrail.getX()-textGap-trailText.getWidth(),playerTrail.getY()+playerTrail.getHeight()/2+trailText.getHeight()/2);

        money = new MoneyDisplay();
    }

    public void setActions(){
    }

    public void resetScreen(){
        back.resetScreen();
        playerColor.resetScreen();
        playerDesign.resetScreen();
        playerTrail.resetScreen();
        money.resetScreen();
    }

    public void dispose(){
        back.dispose();
        playerColor.dispose();
        playerDesign.dispose();
        playerTrail.dispose();
        money.dispose();
    }
}
