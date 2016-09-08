package com.mygdx.particles.light;

import com.mygdx.entities.player.Player;
import com.mygdx.managers.ColorManager;
import box2dLight.ConeLight;

/**
 * Created by Mono on 7/18/2016.
 */
public class ConeLighting{
    ConeLight coneLight;


    public ConeLighting(Player player, Light light){
        coneLight = new ConeLight(light.rayHandler,100, ColorManager.PLAYER_LIGHT,100,0,0,270,30);
        coneLight.setSoftnessLength(5f);
        if(player!=null)
            coneLight.attachToBody(player.getBody());
    }

    public void setLightLength(float lightLength){
        coneLight.setDistance(lightLength);
    }

    public void setPos(float x, float y){
        coneLight.setPosition(x,y);
    }
}
