package com.mygdx.ui.menu;

import com.badlogic.gdx.Gdx;
import com.mygdx.managers.SpriteManager;
import com.mygdx.utils.actors.ActorAnimator;
import com.mygdx.utils.actors.BoxButton;
import com.mygdx.managers.AnimationManager;

/**
 * Created by Christopher Thai on 7/19/2016 at 3:06 PM.
 */
public class BackButton {

    BoxButton back;
    float backWidth,backHeight,backX,backY;
    ActorAnimator backA;

    public BackButton(){
        this.setBounds();
        this.setActors();
        this.setActions();
    }

    public void doAnimation(){
        //back.setAnimation(backA);
        back.setOpacity(0f);
        back.setAnimateOpacity(1f);
        back.setAnimateInsideOpacity(1f);
    }

    public void moveToReset(){
        back.moveTo(backX,backY,backWidth,backHeight,.1f);
    }

    public void moveToHide(){
        back.moveTo(-backWidth,-backHeight,backWidth,backHeight,.1f);
    }

    public BoxButton getActor(){
        return back;
    }

    public void setBounds(){
        backHeight = Gdx.graphics.getHeight()*.09f;
        backWidth = backHeight;
        backX = Gdx.graphics.getWidth()*.01f;
        backY = Gdx.graphics.getHeight()*.1f;
    }

    public void setActors(){
        back = new BoxButton(){
            @Override
            public void justTouched(){
                back();
            }
        };
        back.setThickness(.1f);
        back.setInside(SpriteManager.BACK.getSprite());
        back.setLockInside(false);
        //back.setInsideScale(.7f);
    }

    public void setActions(){
        backA = AnimationManager.getPopUp(backX,backY,backWidth,backHeight,.24f);
    }

    public void resetScreen(){
        //back.setBounds(backX+backWidth/2f,backY+backHeight/2f,0,0);
        back.setBounds(backX,backY,backWidth,backHeight);
    }

    public void dispose(){
        back.dispose();
    }

    public void back(){}
}
