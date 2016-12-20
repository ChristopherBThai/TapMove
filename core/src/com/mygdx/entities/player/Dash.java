package com.mygdx.entities.player;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.mygdx.entities.abilities.BetterDash;
import com.mygdx.managers.ColorManager;
import com.mygdx.managers.SpriteManager;

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

    private SpriteManager trail = SpriteManager.BOX;
    private float angle,radius;
    private Vector2 startPos;
    private float finalDst;
    private float opacity,opacityDecay;


    public Dash(Player player){
        this.player = player;
        dashStrength = player.getPushStrength()*50;
        maxDashTime = .1f;
        currentDashTime = maxDashTime;
        maxDashingTime = .2f;
        currentDashingTime = -1;
        costPercent = .1f;
        startPos = new Vector2();
        radius = player.getRadius();
        opacity = .8f;
        opacityDecay = 1.1f;
    }

    public void render(SpriteBatch sb){
        if(opacity > 0){
            sb.setColor(ColorManager.PLAYER.r,ColorManager.PLAYER_LIGHT.g,ColorManager.PLAYER.b,opacity);
            if(isDashing())
                sb.draw(trail.getSprite(), startPos.x, startPos.y-radius, 0, radius, startPos.dst(player.getPos()),radius * 2, 1, 1, angle);
            else
                sb.draw(trail.getSprite(), startPos.x, startPos.y-radius, 0, radius, finalDst,radius * 2, 1, 1, angle);
        }
    }

    public void update(float delta){
        if(!isReady())
            currentDashTime -= delta;
        if(isDashing()){
            currentDashingTime -= delta;
            if(currentDashingTime<=0)
                player.getBody().setLinearVelocity(player.getBody().getLinearVelocity().x*.1f,player.getBody().getLinearVelocity().y*.1f);
            if(!isDashing())
                finalDst = startPos.dst(player.getPos());
        }else if(opacity > 0 ){
            opacity -= delta * opacityDecay;
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
            angle = player.getDesignTargetAngle();
            startPos.set(player.getPos());
            opacity = .8f;
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
