package com.mygdx.entities.player;

import com.mygdx.entities.abilities.BetterDash;

/**
 * Created by Christopher Thai on 8/30/2016
 */

public class Dash {
    private Player player;

    private float costPercent,newCostPercent;

    private float dashStrength;
    private float currentDashTime,maxDashTime,bufferDashTime;
    private float currentDashingTime,maxDashingTime;

    boolean betterDash;

    public Dash(Player player){
        this.player = player;
        dashStrength = player.getPushStrength()*50;
        maxDashTime = .1f;
        currentDashTime = maxDashTime;
        maxDashingTime = .2f;
        currentDashingTime = -1;
        costPercent = .1f;
    }

    public void update(float delta){
        if(!isReady())
            currentDashTime -= delta;
        if(isDashing()){
            currentDashingTime -= delta;
            if(currentDashingTime<=0)
                player.getBody().setLinearVelocity(player.getBody().getLinearVelocity().x*.1f,player.getBody().getLinearVelocity().y*.1f);
        }
    }

    public boolean isReady(){
        return currentDashTime <= 0;
    }

    public boolean isDashing(){
        return currentDashingTime>0;
    }

    public boolean fling(float x, float y){
        if(isReady()){
            x += player.getPos().x;
            y = player.getPos().y - y;
            player.lookAtPoint(x,y);
            player.getBody().setLinearVelocity(0,0);
            player.push(dashStrength);
            currentDashingTime = maxDashingTime;
            currentDashTime = maxDashTime;
            if(betterDash)
                currentDashTime += bufferDashTime;
            return true;
        }
        return false;
    }

    public float getCostPercent(){
        if(betterDash)
            return newCostPercent;
        return costPercent;
    }

    public void setBetterDash(boolean isBetterDash,float newCost,float cooldownBuffer){
        betterDash = isBetterDash;
        this.newCostPercent = newCost;
        this.bufferDashTime = cooldownBuffer;
    }

    public void reset(){
        betterDash = false;
    }
}
