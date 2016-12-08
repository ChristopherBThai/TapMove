package com.mygdx.particles.light;


import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.physics.box2d.Body;
import com.mygdx.entities.player.Player;
import com.mygdx.managers.ColorManager;

import box2dLight.PointLight;

public class FollowLight{
    private PointLight pointLight;

    public FollowLight(Player player,WorldLighting light){
        pointLight = new PointLight(light.rayHandler, 200, ColorManager.PLAYER_LIGHT.getColor(), 40, 0, 0);
        pointLight.setSoftnessLength(5f);
        if(player!=null)
            pointLight.attachToBody(player.getBody());
    }

    public FollowLight(Body body, WorldLighting light, boolean xray){
        pointLight = new PointLight(light.rayHandler, 50, ColorManager.NORMAL.getColor(), 5, 0, 0);
        pointLight.setSoftnessLength(5f);
        pointLight.setXray(xray);
        if(body!=null)
            pointLight.attachToBody(body);
    }

    public void setLightLength(float lightLength){
        pointLight.setDistance(lightLength);
    }

    public void setColor(Color color){
        pointLight.setColor(color);
    }
}
