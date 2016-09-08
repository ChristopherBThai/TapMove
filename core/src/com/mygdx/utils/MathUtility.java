package com.mygdx.utils;

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
}
