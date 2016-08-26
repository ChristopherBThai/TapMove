package com.mygdx.particles.enemy;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.ParticleEffect;
import com.badlogic.gdx.graphics.g2d.ParticleEffectPool;
import com.badlogic.gdx.graphics.g2d.ParticleEmitter;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Array;
import com.mygdx.game.MyGame;
import com.mygdx.utils.create.BodyCreater;
import com.mygdx.utils.managers.SpriteManager;

import java.util.ArrayList;

/**
 * Created by Christopher Thai on 8/25/2016 at 7:37 AM.
 */
public class EnemyTrail {

    ParticleEffect pe;
    ParticleEffectPool pool;
    private Array<ParticleEffectPool.PooledEffect> effects;

    float highMax,highMin,lowMax,lowMin;

    public EnemyTrail(){
        pe = new ParticleEffect();
        pe.load(Gdx.files.internal("particle/EnemyTrail5"),Gdx.files.internal("sprites"));
        pe.setPosition(0,0);
        pe.scaleEffect(((float)MyGame.WIDTH)/Gdx.graphics.getWidth());
        pool = new ParticleEffectPool(pe,0,200);
        effects = new Array<ParticleEffectPool.PooledEffect>();
        ParticleEffectPool.PooledEffect effect = pool.obtain();
        ParticleEmitter.ScaledNumericValue angle = effect.getEmitters().first().getAngle();
        highMax = angle.getHighMax();
        highMin = angle.getHighMin();
        lowMax = angle.getLowMax();
        lowMin = angle.getLowMin();
        effect.free();
    }

    public void update(float delta){
        for(ParticleEffectPool.PooledEffect effect: effects)
            effect.update(delta);
        //Gdx.app.log("pool stats", "active: " + effects.size + " | free: " + pool.getFree() + "/" + pool.max + " | record: " + pool.peak);
    }

    public void render(SpriteBatch sb){
        for(ParticleEffectPool.PooledEffect effect: effects)
            effect.draw(sb);
    }

    public ParticleEffectPool.PooledEffect getEffect(){
        ParticleEffectPool.PooledEffect effect = pool.obtain();
        effects.add(effect);
        return effect;
    }

    public void setAngle(ParticleEffectPool.PooledEffect effect, float degrees){
        degrees -= 90;
        ParticleEmitter.ScaledNumericValue angle = effect.getEmitters().first().getAngle();
        angle.setHigh(highMin+degrees,highMax+degrees);
        angle.setLow(lowMin+degrees,lowMax+degrees);
    }

    public void setColor(ParticleEffectPool.PooledEffect effect, Color color){
        float[] floatColor = {color.r,color.g,color.b};
        System.out.println(floatColor[0]+","+floatColor[1]+","+floatColor[2]);
        effect.getEmitters().first().getTint().setColors(floatColor);
    }

    public void remove(ParticleEffectPool.PooledEffect effect){
        if(effect!=null){
            effects.removeValue(effect, true);
            effect.free();
        }
    }

    public void reset(){
        for(ParticleEffectPool.PooledEffect effect: effects)
            effect.free();
    }

    public void dispose(){
        pe.dispose();
    }
}
