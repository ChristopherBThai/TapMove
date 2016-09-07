package com.mygdx.particles.enemy;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.mygdx.utils.managers.SpriteManager;

/**
 * Created by Christopher Thai on 9/6/2016 at 5:16 PM.
 */
public class EnemySplit {


    class Particle{
        Sprite sprite;
        float x1,y1,x2,y2;
        float orginx,orginy;
        float angle1,angle2;
        Color color;
        float width,height;
        float currentTime,maxTime;
        float opacity;

        public Particle(float x, float y, Color color, float radius, float angle){
            if(sprite==null)
                sprite = SpriteManager.getHalfCircle();
            x1 = x;
            x2 = x;
            y1 = y;
            y2 = y;
            width = radius;
            height = radius*2;
            angle1 = angle;
            angle2 = angle+180;
            this.color = color;
            maxTime = 2.5f;
            currentTime = maxTime;
            opacity = 1f;
            orginx = width;
            orginy = height/2f;
        }

        public void update(float delta) {
            if(!isDead()) {
                currentTime -= delta;
                opacity = currentTime/maxTime;
            }
        }

        public void render(SpriteBatch sb){
            sb.setColor(color.r,color.g,color.b,opacity);
            sb.draw(sprite,x1,y1,orginx,orginx,width,height,1,1,angle1);
            sb.draw(sprite,x2,y2,orginx,orginx,width,height,1,1,angle2);
        }

        public boolean isDead(){
            return currentTime<=0;
        }

    }
}
