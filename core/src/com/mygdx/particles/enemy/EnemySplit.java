package com.mygdx.particles.enemy;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.mygdx.managers.SpriteManager;

import java.util.ArrayList;

/**
 * Created by Christopher Thai on 9/6/2016 at 5:16 PM.
 */
public class EnemySplit {

    ArrayList<Particle> particles,particlePool;

    public EnemySplit(){
        particles = new ArrayList<Particle>();
        particlePool = new ArrayList<Particle>();
    }

    public void update(float delta){
        for(int i=0;i<particles.size();i++){
            particles.get(i).update(delta);
            if(particles.get(i).isDead()) {
                this.remove(particles.get(i));
                i--;
            }
        }
    }

    public void render(SpriteBatch sb){
        for(int i=0;i<particles.size();i++){
            particles.get(i).render(sb);
        }
    }

    public void create(float x, float y, float dirX, float dirY, Color color, float radius){
        Particle particle;
        if(particlePool.size()>0){
            particle = particlePool.remove(0);
        }else{
            particle = new Particle();
        }
        float radians;

        if(dirX==0)
            if(dirY<0)
                radians = (float) (Math.PI/2f);
            else
                radians = -(float) (Math.PI/2f);
        else
            radians = (float) (Math.atan(dirY/dirX));

        if(dirX<0){
            radians -= (float) (Math.PI);
        }

        particle.reset(x,y,color,radius,radians*MathUtils.radiansToDegrees+90);
        particles.add(particle);
    }

    public void reset(){
        for(int i=0;i<particles.size();i++){
            this.remove(particles.get(i));
            i--;
        }
    }

    private void remove(Particle particle){
        particles.remove(particle);
        particlePool.add(particle);
    }


    class Particle{
        Sprite sprite;
        float x1,y1,x2,y2;
        float velX1,velY1,velX2,velY2;
        float orginx,orginy;
        float angle1,angle2;
        Color color;
        float width,height;
        float currentTime,maxTime;
        float opacity;

        public Particle(){
            if(sprite==null)
                sprite = SpriteManager.getHalfCircle();
        }

        public void reset(float x, float y, Color color, float radius, float angle){
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
            float velocity = radius*.005f;
            float deltaAngle = 70;


            velX1 = -MathUtils.cosDeg(angle+deltaAngle)*velocity;
            velY1 = -MathUtils.sinDeg(angle+deltaAngle)*velocity;
            velX2 = MathUtils.cosDeg(angle-deltaAngle)*velocity;
            velY2 = MathUtils.sinDeg(angle-deltaAngle)*velocity;
        }

        public void update(float delta) {
            if(!isDead()) {
                currentTime -= delta;
                opacity = currentTime/maxTime;
                x1+=velX1;
                y1+=velY1;
                x2+=velX2;
                y2+=velY2;
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
