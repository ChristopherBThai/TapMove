package com.mygdx.managers;

import com.badlogic.gdx.scenes.scene2d.Actor;
import com.mygdx.utils.actors.ActorAnimator;


public class AnimationManager {

    public static ActorAnimator getPopUp(float x, float y, float width, float height, float popScale){
        ActorAnimator result = new ActorAnimator();
        popScale+=1;
        result.animateTo(x-((width*popScale-width)/2f),y-((height*popScale-height)/2f),width*popScale,height*popScale,.2f,.1f);
        result.animateTo(x,y,width,height,.25f);
        return result;
    }

    public static ActorAnimator getPopUp(float x, float y, float width, float height, float popScale, ActorAnimator.ActionCommand command){
        ActorAnimator result = new ActorAnimator();
        popScale+=1;
        result.animateTo(x-((width*popScale-width)/2f),y-((height*popScale-height)/2f),width*popScale,height*popScale,.2f,.1f);
        if(command!=null)
            result.addCommand(command);
        result.animateTo(x,y,width,height,.25f);
        return result;
    }
}
