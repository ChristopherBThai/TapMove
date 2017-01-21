package com.mygdx.ui.menu.shop.abilities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.mygdx.screen.MenuScreen;
import com.mygdx.ui.menu.extra.actors.BackButton;
import com.mygdx.ui.menu.extra.actors.MoneyDisplay;
import com.mygdx.ui.menu.shop.ShopItems.YesNoButton;
import com.mygdx.utils.actors.Text;


public class AbilitiesMenu {
    private MenuScreen screen;
    private Stage stage;

    private YesNoButton abilities;
    private Text description;
    private Vector2 descLoc;

    private MoneyDisplay money;

    private BackButton back;

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
        stage.addActor(back.getActor());
        money.addToStage(stage);
        money.doAnimation();
        abilities.addActor(stage);
        abilities.doAnimation();
        stage.addActor(description);
        description.animateToVisible();
    }

    public void setBounds(){
        descLoc = new Vector2(Gdx.app.getGraphics().getWidth()/2,Gdx.app.getGraphics().getHeight()*.56f);
    }

    public void setActors(){
        back = new BackButton(){
            @Override
            public void back(){
                stage.clear();
                MenuScreen.shop.set(false);
            }
        };

        money = new MoneyDisplay();

        abilities = new YesNoButton(AbilityList.getShopList(),0,0,stage){
            @Override
            public void changedItem(){
                updateDescription();
            }
        };
        abilities.setPos(Gdx.graphics.getWidth()*.5f-abilities.getWidth()/2,Gdx.graphics.getHeight()*.7f-abilities.getHeight()/2);

        description = new Text(Gdx.graphics.getWidth()*.04f,"");
    }

    public void setActions(){
    }

    public void resetScreen(){
        back.resetScreen();
        money.resetScreen();
        abilities.resetScreen();
        updateDescription();
    }

    public void dispose(){
        back.dispose();
        money.dispose();
        abilities.dispose();
        description.dispose();
    }

    private void updateDescription(){
        description.setText(AbilityList.values()[abilities.getShopeList().getCurrentLoc()].getAbility().getDescription());
        description.setPosition(descLoc.x-description.getWidth()/2,descLoc.y);
    }
}
