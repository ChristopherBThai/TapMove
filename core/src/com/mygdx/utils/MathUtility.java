package com.mygdx.utils;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.MathUtils;

/**
 * Created by Christopher Thai on 9/7/2016 at 10:05 PM.
 */
public class MathUtility {

    public static float vectorToRadians(float x, float y){
        float radians;
        if(x==0)
            if(y<0)
                radians = (float) (Math.PI/2f);
            else
                radians = -(float) (Math.PI/2f);
        else
            radians = (float) (Math.atan(y/x));

        if(x<0){
            radians -= (float) (Math.PI);
        }

        return radians;
    }

    public static float vectorToAngle(float x, float y){
        return vectorToRadians(x,y)* MathUtils.radiansToDegrees;
    }

    public static float getSpriteHeight(float percent){
        return Gdx.app.getGraphics().getHeight()*percent;
    }

    public static float getSpriteWidth(Sprite sprite, float percent){
        return sprite.getWidth() * (getSpriteHeight(percent) / sprite.getHeight());
    }
}
