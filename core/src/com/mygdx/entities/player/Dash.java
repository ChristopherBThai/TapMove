package com.mygdx.entities.player;

/**
 * Created by Christopher Thai on 8/30/2016
 */

public class Dash {
    private Player player;

    private float dashStrength;
    private float currentDashTime,maxDashTime;
    private float currentDashingTime,maxDashingTime;

    public Dash(Player player){
        this.player = player;
        dashStrength = player.getPushStrength()*50;
        maxDashTime = 1;
        currentDashTime = maxDashTime;
        maxDashingTime = .2f;
        currentDashingTime = -1;
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
            return true;
        }
        return false;
    }
}
