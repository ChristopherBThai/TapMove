package com.mygdx.particles;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.ParticleEffect;
import com.badlogic.gdx.graphics.g2d.ParticleEffectPool;
import com.badlogic.gdx.graphics.g2d.ParticleEmitter;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.Array;
import com.mygdx.game.MyGame;

/**
 * Created by Christopher Thai on 9/7/2016 at 9:50 PM.
 */
public class Particle {

    ParticleEffect pe;
    ParticleEffectPool pool;
    private Array<ParticleEffectPool.PooledEffect> effects;

    float angleHighMax,angleHighMin,angleLowMax,angleLowMin;
    float scaleHighMax,scaleHighMin,scaleLowMax,scaleLowMin;
    float velHighMax,velHighMin,velLowMax,velLowMin;
    float gravityHighMax,gravityHighMin,gravityLowMax,gravityLowMin;
    float spawnWidthHighMax,spawnWidthHighMin,spawnWidthLowMax,spawnWidthLowMin;
    float spawnHeightHighMax,spawnHeightHighMin,spawnHeightLowMax,spawnHeightLowMin;

    float origionalScale;

    boolean endWhenEnd;

    public Particle(String loc,boolean ends){
        endWhenEnd = ends;
        pe = new ParticleEffect();
        pe.load(Gdx.files.internal(loc),Gdx.files.internal("sprites"));
        pe.setPosition(0,0);
        origionalScale = ((float) MyGame.WIDTH)/1500;
        pool = new ParticleEffectPool(pe,0,200);
        effects = new Array<ParticleEffectPool.PooledEffect>();

        ParticleEffectPool.PooledEffect effect = pool.obtain();
        angleHighMax = effect.getEmitters().first().getAngle().getHighMax();
        angleHighMin = effect.getEmitters().first().getAngle().getHighMin();
        angleLowMax = effect.getEmitters().first().getAngle().getLowMax();
        angleLowMin = effect.getEmitters().first().getAngle().getLowMin();

        scaleHighMax = effect.getEmitters().first().getScale().getHighMax();
        scaleHighMin = effect.getEmitters().first().getScale().getHighMin();
        scaleLowMax = effect.getEmitters().first().getScale().getLowMax();
        scaleLowMin = effect.getEmitters().first().getScale().getLowMin();

        velHighMax = effect.getEmitters().first().getVelocity().getHighMax();
        velHighMin = effect.getEmitters().first().getVelocity().getHighMin();
        velLowMax = effect.getEmitters().first().getVelocity().getLowMax();
        velLowMin = effect.getEmitters().first().getVelocity().getLowMin();

        gravityHighMax = effect.getEmitters().first().getGravity().getHighMax();
        gravityHighMin = effect.getEmitters().first().getGravity().getHighMin();
        gravityLowMax = effect.getEmitters().first().getGravity().getLowMax();
        gravityLowMin = effect.getEmitters().first().getGravity().getLowMin();

        spawnWidthHighMax = effect.getEmitters().first().getSpawnWidth().getHighMax();
        spawnWidthHighMin = effect.getEmitters().first().getSpawnWidth().getHighMin();
        spawnWidthLowMax = effect.getEmitters().first().getSpawnWidth().getLowMax();
        spawnWidthLowMin = effect.getEmitters().first().getSpawnWidth().getLowMin();

        spawnHeightHighMax = effect.getEmitters().first().getSpawnHeight().getHighMax();
        spawnHeightHighMin = effect.getEmitters().first().getSpawnHeight().getHighMin();
        spawnHeightLowMax = effect.getEmitters().first().getSpawnHeight().getLowMax();
        spawnHeightLowMin = effect.getEmitters().first().getSpawnHeight().getLowMin();

        this.remove(effect);

        pe.scaleEffect(origionalScale);
    }

    public void update(float delta){
        for(int i=0;i<effects.size;i++){
            if(endWhenEnd&&effects.get(i).isComplete()){
                this.remove(effects.get(i));
                i--;
            }else
                effects.get(i).update(delta);
        }
        //Gdx.app.log("pool stats", "active: " + effects.size + " | free: " + pool.getFree() + "/" + pool.max + " | record: " + pool.peak);
    }

    public void render(SpriteBatch sb){
        for(ParticleEffectPool.PooledEffect effect: effects)
            effect.draw(sb);
    }

    public ParticleEffectPool.PooledEffect getEffect(){
        ParticleEffectPool.PooledEffect effect = pool.obtain();
        effects.add(effect);
        effect.start();
        return effect;
    }

    public void setAngle(ParticleEffectPool.PooledEffect effect, float degrees){
        degrees -= 90;
        ParticleEmitter.ScaledNumericValue angle = effect.getEmitters().first().getAngle();
        angle.setHigh(angleHighMin+degrees,angleHighMax+degrees);
        angle.setLow(angleLowMin+degrees,angleLowMax+degrees);
    }

    public void setColor(ParticleEffectPool.PooledEffect effect, Color color){
        float[] floatColor = {color.r,color.g,color.b};
        effect.getEmitters().first().getTint().setColors(floatColor);
    }

    public void setScale(ParticleEffectPool.PooledEffect effect, float scale){
        float scaleFactor = origionalScale*scale;
        ParticleEmitter particleEmitter = effect.getEmitters().first();

        //System.out.println(particleEmitter.getScale().getHighMax()+","+particleEmitter.getScale().getHighMin()+","+particleEmitter.getScale().getLowMax()+","+particleEmitter.getScale().getLowMin());

        particleEmitter.getScale().setHigh(scaleHighMin * scaleFactor, scaleHighMax * scaleFactor);
        particleEmitter.getScale().setLow(scaleLowMin * scaleFactor, scaleLowMax * scaleFactor);

        particleEmitter.getVelocity().setHigh(velHighMin * scaleFactor, velHighMax * scaleFactor);
        particleEmitter.getVelocity().setLow(velLowMin * scaleFactor, velLowMax * scaleFactor);

        particleEmitter.getGravity().setHigh(gravityHighMin * scaleFactor, gravityHighMax * scaleFactor);
        particleEmitter.getGravity().setLow(gravityLowMin * scaleFactor, gravityLowMax * scaleFactor);

        particleEmitter.getSpawnWidth().setHigh(spawnWidthHighMin * scaleFactor, spawnWidthHighMax * scaleFactor);
        particleEmitter.getSpawnWidth().setLow(spawnWidthLowMin * scaleFactor, spawnWidthLowMax * scaleFactor);

        particleEmitter.getSpawnHeight().setHigh(spawnHeightHighMin * scaleFactor, spawnHeightHighMax * scaleFactor);
        particleEmitter.getSpawnHeight().setLow(spawnHeightLowMin * scaleFactor, spawnHeightLowMax * scaleFactor);

        effect.reset();
        effect.start();
    }
    public void remove(ParticleEffectPool.PooledEffect effect){
        if(effect!=null){
            effects.removeValue(effect, true);
            effect.free();
        }
    }

    public void reset(){
        for(ParticleEffectPool.PooledEffect effect: effects)
            this.remove(effect);
    }

    public void dispose(){
        pe.dispose();
    }
}