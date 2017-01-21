package com.mygdx.ui.menu.extra.actors;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.mygdx.managers.SpriteManager;
import com.mygdx.utils.actors.BoxButton;

/**
 * Created by Christopher Thai on 7/19/2016 at 3:06 PM.
 */
public class BackButton {

    private BoxButton back;
    private float backLength,backX,backY;

    private static float backed;

    public BackButton(){
        this.setBounds();
        this.setActors();
    }

    public void doAnimation(){
        //back.setAnimation(backA);
        back.setOpacity(0f);
        back.setAnimateOpacity(1f);
        back.setAnimateInsideOpacity(1f);
    }

    public void checkBack(float delta){
        if (backed<=0&&Gdx.input.isKeyPressed(Input.Keys.BACK)){
            back();
            backed = .5f;
        }else if(backed>0)
            backed -= delta;
    }

    public void moveToReset(){
        back.moveTo(backX,backY,backLength,backLength,.1f);
    }

    public void moveToHide(){
        back.moveTo(-backLength,-backLength,backLength,backLength,.1f);
    }

    public BoxButton getActor(){
        return back;
    }

    public void setBounds(){
        backLength = Gdx.graphics.getHeight()*.09f;
        backX = Gdx.graphics.getWidth()*.01f;
        backY = Gdx.graphics.getHeight()*.1f;
    }

    public void setActors(){
        back = new BoxButton(){
            @Override
            public void justTouched(){
                back();
            }
            @Override
            public void act(float delta){
                super.act(delta);
                checkBack(delta);
            }
        };
        back.setThickness(.1f);
        back.setInside(SpriteManager.BACK);
        back.setLockInside(false);
        //back.setInsideScale(.7f);
    }

    public void resetScreen(){
        back.setBounds(backX,backY,backLength,backLength);
    }

    public void dispose(){
        back.dispose();
    }

    public void back(){}
}
