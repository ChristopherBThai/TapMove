package com.mygdx.ui.menu.shop.cosmetics;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.mygdx.screen.MenuScreen;
import com.mygdx.ui.menu.ButtonLayout;
import com.mygdx.ui.menu.extra.actors.BackButton;
import com.mygdx.ui.menu.extra.actors.MoneyDisplay;
import com.mygdx.ui.menu.shop.ShopItems.YesNoButton;
import com.mygdx.utils.actors.Text;

/**
 * Created by Christopher Thai on 6/20/2016.
 */
public class CosmeticsMenu extends ButtonLayout{
    private YesNoButton playerColor,playerDesign,playerTrail;
    private Text colorText,designText,trailText;

    public CosmeticsMenu(){
        super("Cosmetics",true,0,false);
    }

    @Override
    public void set(boolean withReset){
        super.set(withReset);
        playerColor.addActor(stage);
        playerDesign.addActor(stage);
        playerTrail.addActor(stage);
        stage.addActor(colorText);
        stage.addActor(designText);
        stage.addActor(trailText);
    }

    @Override
    public void resetScreen(){
        super.resetScreen();
        playerColor.doAnimation();
        playerDesign.doAnimation();
        playerTrail.doAnimation();
        colorText.animateToVisible();
        designText.animateToVisible();
        trailText.animateToVisible();
        playerColor.resetScreen();
        playerDesign.resetScreen();
        playerTrail.resetScreen();
    }

    @Override
    public void setActors(){
        super.setActors();

        float textGap = Gdx.graphics.getWidth()*.14f;

        playerColor = new YesNoButton(ColorList.getShopList(),0,0,stage);
        playerColor.setPos(Gdx.graphics.getWidth()*.7f-playerColor.getWidth()/2,Gdx.graphics.getHeight()*.56f-playerColor.getHeight()/2);
        colorText = new Text(Gdx.graphics.getWidth()*.06f,"Color");
        colorText.setPosition(playerColor.getX()-textGap-colorText.getWidth(),playerColor.getY()+playerColor.getHeight()/2+colorText.getHeight()/2);

        playerDesign = new YesNoButton(DesignList.getShopList(),0,0,stage);
        playerDesign.setPos(Gdx.graphics.getWidth()*.3f-playerDesign.getWidth()/2,playerColor.getY()-playerDesign.getHeight()-textGap);
        designText = new Text(Gdx.graphics.getWidth()*.06f,"Design");
        designText.setPosition(playerDesign.getX()+playerDesign.getWidth()*2f+textGap,playerDesign.getY()+playerDesign.getHeight()/2+designText.getHeight()/2);

        playerTrail = new YesNoButton(ParticleList.getShopList(),0,0,stage);
        playerTrail.setPos(Gdx.graphics.getWidth()*.7f-playerTrail.getWidth()/2,playerDesign.getY()-playerTrail.getHeight()-textGap);
        trailText = new Text(Gdx.graphics.getWidth()*.06f,"Trail");
        trailText.setPosition(playerTrail.getX()-textGap-trailText.getWidth(),playerTrail.getY()+playerTrail.getHeight()/2+trailText.getHeight()/2);
    }

    @Override
    public void dispose(){
        super.dispose();
        playerColor.dispose();
        playerDesign.dispose();
        playerTrail.dispose();
    }

    @Override
    protected void buttonTouched(int index){

    }

    @Override
    protected void buttonActivated(int index){

    }

    @Override
    protected void backClicked(){
        MenuScreen.shop.set(false);
    }
}
