package com.mygdx.particles.enemy;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.World;
import com.mygdx.utils.create.BodyCreater;
import com.mygdx.utils.managers.SpriteManager;

import java.util.ArrayList;

/**
 * Created by Christopher Thai on 8/25/2016 at 7:37 AM.
 */
public class EnemyTrail {
    Body body;

    private float length;
    private Color color;

    Sprite sprite;

    private float currentTime,maxTime;
    private float opacity;

    public EnemyTrail(float x, float y, float length, Color color, float xvel, float yvel, float rotation, World world){
        maxTime = 2.3f;
        currentTime = maxTime;
        this.length = length;
        this.color = color;
        this.body = BodyCreater.createBox(x,y,length,length,false,false,world,0,0);
        body.setLinearVelocity(xvel,yvel);
        body.setAngularVelocity(rotation);
        body.setLinearDamping(5f);
        sprite = SpriteManager.getLine();
    }

    public void update(float delta){
        currentTime -= delta;
        opacity = currentTime/maxTime;
    }

    public void render(SpriteBatch sb){
        sb.setColor(color.r,color.g,color.b,opacity);
        sb.draw(sprite,body.getPosition().x-length/2f,body.getPosition().y-length/2f,length/2f,length/2f,length,length,1f,1f,body.getAngle()*MathUtils.radiansToDegrees);
    }

    public void reuse(float x, float y, Color color, float xvel, float yvel, float rotation){
        body.setActive(true);
        body.setLinearVelocity(xvel,yvel);
        body.setAngularVelocity(rotation);
        body.setTransform(x,y,0);
        this.color = color;
        currentTime = maxTime;
    }

    public void setColor(Color color){
        this.color = color;
    }

    public boolean isDead(){
        return currentTime<=0;
    }

    public float getLength(){
        return length;
    }

    public Color getColor(){
        return color;
    }

    public Body getBody(){
        return body;
    }

    @Override
    public boolean equals(Object obj){
        if(obj instanceof EnemyTrail)
            if(this.length==((EnemyTrail)obj).length)
                return true;
        return false;
    }
}
