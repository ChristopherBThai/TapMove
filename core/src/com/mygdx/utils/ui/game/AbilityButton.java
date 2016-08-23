package com.mygdx.utils.ui.game;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.mygdx.screen.GameScreen;
import com.mygdx.utils.actors.ActorAnimator;
import com.mygdx.utils.actors.BoxButton;
import com.mygdx.utils.managers.SpriteManager;

public class AbilityButton {

    Stage stage;

    BoxButton ability;
    boolean animate;
    ActorAnimator ready,hide,show;

    public AbilityButton(Stage stage){
        this.stage = stage;
        initSize();
    }

    public void initSize(){
        float width = Gdx.app.getGraphics().getWidth()*.3f;
        float height = Gdx.app.getGraphics().getHeight()*.09f;
        float x = Gdx.app.getGraphics().getWidth()*.5f-width*.5f;
        float y = height*.6f;
        float animateScale = .2f;

        ability = new BoxButton(x,y,width,height);
        ability.setThickness(.2f);
        ability.setInside(SpriteManager.getExplosion());
        stage.addActor(ability);

        ready = new ActorAnimator();
        ready.addCommand(new ActorAnimator.ActionCommand(){
            @Override
            public void command(ActorAnimator animator){ability.setOpacity(1f);}
        });
        ready.animateTo(x-((width*animateScale)/2f),y-((height*animateScale)/2f),width*(1+animateScale),height*(1+animateScale),.4f);
        ready.animateTo(x,y,width,height,.1f);
        hide = new ActorAnimator();
        hide.animateTo(x,-height*1.2f,width,height,.1f);
        show = new ActorAnimator();
        show.animateTo(x,y,width,height,.1f);
    }

    public boolean useAbility(float x, float y){
        if(ability.tap(x,y))
            return true;
        return false;
    }

    public void update(float delta){
        if(!GameScreen.entMan.player.getAbility().isReady()) {
            float percent = GameScreen.entMan.player.getAbility().getCooldownPercent();
            ability.setOpacity(percent / 2f);
            if(!animate)
                animate = true;
        }else if(animate){
            ability.setAnimation(ready);
            animate = false;
        }
    }

    public Actor getActor(){
        return ability;
    }

    public void reset(){
        stage.addActor(ability);
        animate = false;
        ability.setOpacity(1f);
    }

    public void hideButton(){
        ability.setAnimation(hide);
    }

    public void showButton(){
        ability.setAnimation(show);
    }

}
