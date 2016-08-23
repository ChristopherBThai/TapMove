package com.mygdx.particles.light;


import com.badlogic.gdx.physics.box2d.World;
import com.mygdx.entities.Player;
import com.mygdx.game.MyGame;
import com.mygdx.utils.managers.ColorManager;

import box2dLight.PointLight;
import box2dLight.RayHandler;

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
