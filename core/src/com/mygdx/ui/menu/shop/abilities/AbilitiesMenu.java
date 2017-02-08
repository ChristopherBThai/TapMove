package com.mygdx.ui.menu.shop.abilities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.mygdx.screen.MenuScreen;
import com.mygdx.ui.menu.ButtonLayout;
import com.mygdx.ui.menu.extra.actors.BackButton;
import com.mygdx.ui.menu.extra.actors.MoneyDisplay;
import com.mygdx.ui.menu.shop.ShopItems.YesNoButton;
import com.mygdx.utils.actors.Text;


public class AbilitiesMenu extends ButtonLayout{

    private YesNoButton abilities;
    private Text description;
    private Vector2 descLoc;

    public AbilitiesMenu(){
        super("Abilities",true,0,false);
    }

    @Override
    public void set(boolean withReset){
        super.set(withReset);
        abilities.addActor(stage);
        stage.addActor(description);
        updateDescription();
    }

    @Override
    public void resetScreen(){
        super.resetScreen();
        description.animateToVisible();
        abilities.doAnimation();
        abilities.resetScreen();
    }

    @Override
    public void setBounds(){
        super.setBounds();
        descLoc = new Vector2(Gdx.app.getGraphics().getWidth()/2,Gdx.app.getGraphics().getHeight()*.45f);
    }

    @Override
    public void setActors(){
        super.setActors();
        abilities = new YesNoButton(AbilityList.getShopList(),0,0,stage){
            @Override
            public void changedItem(){
                updateDescription();
            }
        };
        abilities.setPos(Gdx.graphics.getWidth()*.5f-abilities.getWidth()/2,Gdx.graphics.getHeight()*.6f-abilities.getHeight()/2);

        description = new Text(Gdx.graphics.getWidth()*.04f,"");
    }

    @Override
    public void dispose(){
        super.dispose();
        abilities.dispose();
        description.dispose();
    }

    private void updateDescription(){
        description.setText(AbilityList.values()[abilities.getShopeList().getCurrentLoc()].getAbility().getDescription());
        description.setPosition(descLoc.x-description.getWidth()/2,descLoc.y);
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
