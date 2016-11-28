package com.mygdx.particles.light;

import com.badlogic.gdx.physics.box2d.World;
import com.mygdx.game.MyGame;
import box2dLight.RayHandler;

/**
 * Created by Christopher Thai on 7/18/2016.
 */
public class WorldLighting {
    public RayHandler rayHandler;
    private float aLightLevel,aspeed;
    private int animationTime;
    private float cLightLevel;

    private float defaultLight;

    public WorldLighting(World world){
        rayHandler = new RayHandler(world);
        defaultLight = .6f;
        setLightLevel(defaultLight);
    }

    public void setLightLevel(float lightLevel){
        rayHandler.setAmbientLight(lightLevel);
        cLightLevel = lightLevel;
    }

    public float getLightLevel(){
        return cLightLevel;
    }

    public void update(float delta){
        rayHandler.setCombinedMatrix(MyGame.camera.combined);
        rayHandler.update();

        if(animationTime>0){
            animationTime--;
            cLightLevel = cLightLevel + (aLightLevel - cLightLevel)*aspeed;
            rayHandler.setAmbientLight(cLightLevel);
        }
    }

    public void render(){
        rayHandler.render();
    }

    public void dispose(){
        rayHandler.dispose();
    }

    public void animateTo(float lightLevel,float speed){
        aLightLevel = lightLevel;
        animationTime = (int)(Math.log(.000001f)/Math.log(1-speed))+1;
        aspeed = speed;
    }

    public void resetLights(){
        this.animateTo(defaultLight,.1f);
    }
}
