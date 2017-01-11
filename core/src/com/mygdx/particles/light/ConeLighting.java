package com.mygdx.particles.light;

import com.mygdx.entities.player.Player;
import com.mygdx.managers.ColorManager;
import box2dLight.ConeLight;

/**
 * Created by Christopher Thai on 7/18/2016.
 */
public class ConeLighting{
    private ConeLight coneLight;


    public ConeLighting(Player player, WorldLighting light){
        coneLight = new ConeLight(light.rayHandler,200, ColorManager.PLAYER_LIGHT.getColor(),100,0,0,270,30);
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
