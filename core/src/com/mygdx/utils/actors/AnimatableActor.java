package com.mygdx.utils.actors;

import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Touchable;

/**
 * Created by Christopher Thai on 7/27/2016 at 4:06 PM.
 */
public class AnimatableActor extends Actor {
    float x,y;
    float bufferX,bufferY;

    protected float opacity,aOpacity,aOpacityspeed;
    int animateOpacityTime;
    ActorAnimator animate;

    public AnimatableActor(){
        aOpacity = 1f;
        opacity = 1f;
        aOpacityspeed = .1f;
        setTouchable(Touchable.disabled);
    }

    @Override
    public void act(float delta){
        super.act(delta);

        if(animate!=null) {
            animate.update(delta);
            if(!animate.isAnimating()){
                animate.setActor(null);
                animate = null;
            }
        }

        if(animateOpacityTime>0){
            animateOpacityTime--;
            opacity = opacity + (aOpacity - opacity)*aOpacityspeed;
        }
    }

    public void moveTo(float x, float y, float width, float height, float speed){
        ActorAnimator action = new ActorAnimator();
        action.animateTo(x,y,width,height,speed);
        this.setAnimation(action);
    }

    public void moveTo(float x, float y, float speed){
        ActorAnimator action = new ActorAnimator();
        action.animateTo(x,y,getWidth(),getHeight(),speed);
        this.setAnimation(action);
    }

    public boolean setAnimation(ActorAnimator actorAnimator){
        if(actorAnimator==null){
            if(!(animate==null)){
                animate.setActor(null);
                animate = null;
            }
            return false;
        }
        animate = actorAnimator.cpy();
        animate.setActor(this);
        return true;
    }

    public void setBuffers(float x, float y){
        bufferX = x;
        bufferY = y;

        super.setPosition(this.x+bufferX,this.y+bufferY);
    }

    public void setOpacity(float opacity){
        this.opacity = opacity;
    }


    public void setAnimateOpacity(float opacity){
        aOpacity = opacity;
        animateOpacityTime = (int)(Math.log(.005f)/Math.log(1-aOpacityspeed))+1;
    }

    public void setAnimateOpacity(float opacity,float speed){
        aOpacityspeed = speed;
        aOpacity = opacity;
        animateOpacityTime = (int)(Math.log(.005f)/Math.log(1-aOpacityspeed))+1;
    }

    @Override
    public void setPosition(float x, float y){
        this.x = x;
        this.y = y;
        super.setPosition(x+bufferX,y+bufferY);
    }

    @Override
    public void setBounds(float x, float y, float width, float height){
        this.x = x;
        this.y = y;
        super.setBounds(x+bufferX,y+bufferY,width,height);
    }

    public float getXWithoutBuffer(){
        return x;
    }

    public float getYWithoutBuffer(){
        return y;
    }

    public float getBufferX(){
        return bufferX;
    }

    public float getBufferY(){
        return bufferY;
    }

    public ActorAnimator getAnimate(){
        return animate;
    }
}
