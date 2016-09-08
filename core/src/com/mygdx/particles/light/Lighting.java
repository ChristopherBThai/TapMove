package com.mygdx.particles.light;


import com.mygdx.entities.player.Player;
import com.mygdx.managers.ColorManager;

import box2dLight.PointLight;

public class Lighting{
    PointLight pointLight;

    public Lighting(Player player,Light light){
        pointLight = new PointLight(light.rayHandler, 200, ColorManager.PLAYER_LIGHT, 40, 0, 0);
        pointLight.setSoftnessLength(5f);
        if(player!=null)
            pointLight.attachToBody(player.getBody());
    }

    public void setLightLength(float lightLength){
        pointLight.setDistance(lightLength);
    }
}
