package com.mygdx.entities.enemies;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.ParticleEffectPool;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.physics.box2d.World;
import com.mygdx.entities.Entity;
import com.mygdx.game.MyGame;
import com.mygdx.particles.ParticleTypes;
import com.mygdx.screen.GameScreen;
import com.mygdx.utils.create.BodyCreater;
import com.mygdx.managers.ColorManager;
import com.mygdx.managers.SpriteManager;

public class Enemy extends Entity {

    public static final int LIGHT_BALL = 0;
    public static final int NORMAL_ENEMY = 1;
    public static final int FAST_ENEMY = 2;
    public static final int BIG_ENEMY = 3;
    public static final int INVISIBLE_ENEMY = 4;
    public static final int FOLLOW_ENEMY = 5;
    public static final int MAGNET_ENEMY = 6;

    public static float NORMAL_RADIUS;

    protected Color color;
    protected float radius;

    protected float speed,speedBuffer;
    protected float strafe;

    protected ParticleEffectPool.PooledEffect particle;

    public boolean isEnemy;

    public Enemy(float x, float y, float radius, float density, float restitution, World world){
        super(BodyCreater.createCircle(x, y, radius, density, restitution, false, true, world));
        this.body.setLinearDamping(0f);
        this.radius = radius;
        speed = 500;
        speedBuffer = 300;
        strafe = 200;
        color = ColorManager.NORMAL_ENEMY;
        isEnemy = true;
        if(NORMAL_RADIUS==0)
            NORMAL_RADIUS = MyGame.WIDTH *.07f;
    }

    @Override
    public void render(SpriteBatch sb){
        sb.setColor(color);
        sb.draw(SpriteManager.CIRCLE.getSprite(), body.getPosition().x-radius, body.getPosition().y-radius, radius*2, radius*2);
    }

    @Override
    public void update(float delta){
        if(particle!=null) {
            particle.setPosition(this.getPos().x, this.getPos().y);
        }
    }

    public boolean dead(){
        return this.body.getPosition().x+radius<-radius*3||
                this.body.getPosition().x-radius> MyGame.WIDTH+radius*3||
                this.body.getPosition().y+radius<-radius*3||
                this.body.getPosition().y-radius> MyGame.HEIGHT+radius*3;
    }

    public void disable(){
        this.getBody().setLinearVelocity(0, 0);
        this.getBody().setActive(false);
        this.setPos(-1000, -1000);
        this.removeParticle();
    }

    public void removeParticle(){
        ParticleTypes.ENEMY_TRAIL.particle.remove(particle);
        this.particle = null;
    }

    public float getRadius(){
        return radius;
    }

    public Color getColor(){
        return color;
    }

    public Enemy randomize() {
        this.body.setActive(true);
        this.particle = ParticleTypes.ENEMY_TRAIL.particle.getEffect();
        ParticleTypes.ENEMY_TRAIL.particle.setColor(particle,this.color);
        ParticleTypes.ENEMY_TRAIL.particle.setScale(particle,radius/NORMAL_RADIUS);
        int random = (int) (Math.random()*4);

        switch(random){
            case 0:
                this.setPos(-this.getRadius(), MyGame.HEIGHT*Math.random());
                this.setVelocity(Math.random()*speed+speedBuffer, Math.random()*strafe-strafe/2);
                break;
            case 1:
                this.setPos(MyGame.WIDTH*Math.random(), -this.getRadius());
                this.setVelocity(Math.random()*strafe-strafe/2, Math.random()*speed+speedBuffer);
                break;
            case 2:
                this.setPos(MyGame.WIDTH+this.getRadius(), MyGame.HEIGHT*Math.random());
                this.setVelocity(-Math.random()*speed-speedBuffer, Math.random()*strafe-strafe/2);
                break;
            case 3:
                this.setPos(MyGame.WIDTH*Math.random(), MyGame.HEIGHT+this.getRadius());
                this.setVelocity(Math.random()*strafe-strafe/2, -Math.random()*speed-speedBuffer);
                break;
        }

        return this;
    }

    public boolean equals(int id){
        return false;
    }
}