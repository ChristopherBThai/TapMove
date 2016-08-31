package com.mygdx.entities.player;

/**
 * Created by Christopher Thai on 8/30/2016 at 9:00 PM.
 */
public class Dash {
    float dashStrength;
    float currentDashTime,maxDashTime;
    float lastTap,doubleTapLength;

    public Dash(float strength){
        dashStrength = strength;
        maxDashTime = 1;
        currentDashTime = maxDashTime;
        doubleTapLength = .3f;
    }

    public void update(float delta){
        if(!isReady())
            currentDashTime -= delta;
        lastTap += delta;
    }

    public boolean isReady(){
        return currentDashTime <= 0;
    }

    public float tap(float x, float y){
        if(lastTap<doubleTapLength&&isReady()){
            lastTap = 0;
            currentDashTime = maxDashTime;
            return dashStrength;
        }else{
            lastTap = 0;
            return -1;
        }
    }
}
