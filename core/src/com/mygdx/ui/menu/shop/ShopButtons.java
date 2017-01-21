package com.mygdx.ui.menu.shop;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.mygdx.screen.MenuScreen;
import com.mygdx.ui.menu.extra.actors.BackButton;
import com.mygdx.utils.actors.ActorAnimator;
import com.mygdx.utils.actors.BoxButton;


public class ShopButtons {
    private MenuScreen screen;
    private Stage stage;

    private BoxButton cosmetics,abilities,other;
    private BackButton back;
    private float width,height,x,y,gap;
    private ActorAnimator cosmeticsClicked,abilitiesClicked,otherClicked;

    public ShopButtons(MenuScreen screen){
        this.screen = screen;
        this.stage = screen.stage;
        this.setBounds();
        this.setActors();
        this.setActions();
    }

    public void set(boolean withReset){
        if(withReset) {
            resetScreen();
            cosmetics.lockText(false);
            cosmetics.setOpacity(0f);
            cosmetics.setAnimateOpacity(1f);
            cosmetics.setAnimateInsideOpacity(1f);
            stage.addActor(abilities);
            abilities.lockText(false);
            abilities.setOpacity(0f);
            abilities.setAnimateOpacity(1f);
            abilities.setAnimateInsideOpacity(1f);
            stage.addActor(cosmetics);
            other.lockText(false);
            other.setOpacity(0f);
            other.setAnimateOpacity(1f);
            other.setAnimateInsideOpacity(1f);
            stage.addActor(other);
            back.doAnimation();
            stage.addActor(back.getActor());
        }else{
            stage.addActor(cosmetics);
            stage.addActor(abilities);
            stage.addActor(other);
            stage.addActor(back.getActor());
        }
    }

    public void setBounds(){
        width = Gdx.graphics.getWidth()*.47f;
        height = Gdx.graphics.getHeight()*.08f;
        x = Gdx.graphics.getWidth()*.5f-width/2f;
        y = Gdx.graphics.getHeight()*.7f;
        gap = Gdx.graphics.getHeight()*.03f+height;
    }

    public void setActors(){
        cosmetics = new BoxButton(){
            @Override
            public void justTouched(){
                cosmetics.setAnimation(cosmeticsClicked);
            }
        };
        cosmetics.setThickness(.1f);
        cosmetics.setText("Cosmetics");
        cosmetics.setTextScale(.8f);

        abilities = new BoxButton(){
            @Override
            public void justTouched(){
                abilities.setAnimation(abilitiesClicked);
            }
        };
        abilities.setThickness(.1f);
        abilities.setText("Abilities");
        abilities.setTextScale(.8f);

        other = new BoxButton(){
            @Override
            public void justTouched(){
                other.setAnimation(otherClicked);
            }
        };
        other.setThickness(.1f);
        other.setText("Other");
        other.setTextScale(.8f);

        back = new BackButton(){
            @Override
            public void back(){
                stage.clear();
                MenuScreen.menu.set();
            }
        };
    }

    public void setActions(){

        cosmeticsClicked = new ActorAnimator();
        cosmeticsClicked.addCommand(new ActorAnimator.ActionCommand(){
            @Override
            public void command(ActorAnimator animator){
                cosmetics.removeTouch();
                cosmetics.lockText(true);
                abilities.lockText(false);
                other.lockText(false);
                cosmetics.setAnimateInsideOpacity(0f);
                abilities.moveTo(abilities.getX(),-abilities.getHeight(),width,height,.1f);
                other.moveTo(other.getX(),-other.getHeight(),width,height,.1f);
                back.moveToHide();
            }
        });
        cosmeticsClicked.animateTo(-cosmetics.getThickness()*1.2f,-cosmetics.getThickness()*1.2f,Gdx.graphics.getWidth()+cosmetics.getThickness()*1.2f,Gdx.graphics.getHeight()+cosmetics.getThickness()*1.2f,.1f);
        cosmeticsClicked.addCommand(new ActorAnimator.ActionCommand(){
            @Override
            public void command(ActorAnimator animator){
                stage.clear();
                cosmetics.addTouch();
                screen.cosmetics.set(true);
                cosmetics.setAnimateInsideOpacity(1f);
                abilities.moveTo(x,y-gap,width,height,.1f);
                other.moveTo(x,y-gap*2,width,height,.1f);
                back.moveToReset();
            }
        });
        cosmeticsClicked.animateTo(x,y,width,height,.1f);
        cosmeticsClicked.addCommand(new ActorAnimator.ActionCommand(){
            @Override
            public void command(ActorAnimator animator){
                cosmetics.lockText(false);
            }
        });

        abilitiesClicked = new ActorAnimator();
        abilitiesClicked.addCommand(new ActorAnimator.ActionCommand(){
            @Override
            public void command(ActorAnimator animator){
                abilities.removeTouch();
                abilities.lockText(true);
                cosmetics.lockText(false);
                other.lockText(false);
                abilities.setAnimateInsideOpacity(0f);
                cosmetics.moveTo(x,Gdx.graphics.getHeight(),width,height,.1f);
                other.moveTo(x,-height,width,height,.1f);
                back.moveToHide();
            }
        });
        abilitiesClicked.animateTo(-abilities.getThickness()*1.2f,-abilities.getThickness()*1.2f,Gdx.graphics.getWidth()+abilities.getThickness()*1.2f,Gdx.graphics.getHeight()+abilities.getThickness()*1.2f,.1f);
        abilitiesClicked.addCommand(new ActorAnimator.ActionCommand(){
            @Override
            public void command(ActorAnimator animator){
                stage.clear();
                abilities.addTouch();
                screen.abilities.set(true);
                abilities.setAnimateInsideOpacity(1f);
                cosmetics.moveTo(x,y,width,height,.1f);
                other.moveTo(x,y-gap*2,width,height,.1f);
                back.moveToReset();
            }
        });
        abilitiesClicked.animateTo(x,y-gap,width,height,.1f);
        abilitiesClicked.addCommand(new ActorAnimator.ActionCommand(){
            @Override
            public void command(ActorAnimator animator){
                abilities.lockText(false);
            }
        });

        otherClicked = new ActorAnimator();
        otherClicked.addCommand(new ActorAnimator.ActionCommand(){
            @Override
            public void command(ActorAnimator animator){
                other.removeTouch();
                other.lockText(true);
                cosmetics.lockText(false);
                abilities.lockText(false);
                other.setAnimateInsideOpacity(0f);
                cosmetics.moveTo(x,Gdx.graphics.getHeight(),width,height,.1f);
                abilities.moveTo(x,Gdx.graphics.getHeight(),width,height,.1f);
                back.moveToHide();
            }
        });
        otherClicked.animateTo(-other.getThickness()*1.2f,-other.getThickness()*1.2f,Gdx.graphics.getWidth()+other.getThickness()*1.2f,Gdx.graphics.getHeight()+other.getThickness()*1.2f,.1f);
        otherClicked.addCommand(new ActorAnimator.ActionCommand(){
            @Override
            public void command(ActorAnimator animator){
                stage.clear();
                other.addTouch();
                screen.other.set(true);
                other.setAnimateInsideOpacity(1f);
                abilities.moveTo(x,y-gap,width,height,.1f);
                cosmetics.moveTo(x,y,width,height,.1f);
                back.moveToReset();
            }
        });
        otherClicked.animateTo(x,y-gap*2,width,height,.1f);
        otherClicked.addCommand(new ActorAnimator.ActionCommand(){
            @Override
            public void command(ActorAnimator animator){
                other.lockText(false);
            }
        });
    }

    public void resetScreen(){
        cosmetics.setBounds(x,y,width,height);
        cosmetics.addTouch();
        abilities.setBounds(x,y-gap,width,height);
        abilities.addTouch();
        other.setBounds(x,y-gap*2,width,height);
        other.addTouch();
        back.resetScreen();
    }

    public void dispose(){
        cosmetics.dispose();
        abilities.dispose();
        other.dispose();
        back.dispose();
    }
}
