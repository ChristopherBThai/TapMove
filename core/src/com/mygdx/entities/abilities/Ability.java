package com.mygdx.entities.abilities;


import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.mygdx.entities.player.Player;
import com.mygdx.entities.enemies.Enemy;

public abstract class Ability {

    Player player;
    Sprite sprite;
    float cooldownTime,cooldownCurrentTime;
    float durationTime,durationCurrentTime;
    boolean active;

    public Ability(Player player, Sprite sprite){
        this.sprite = sprite;
        this.player = player;
        cooldownTime = 30f;
        durationTime = 1f;
    }

    public boolean activate() {
        if(isReady()) {
            durationCurrentTime = durationTime;
            cooldownCurrentTime = cooldownTime;
            justActivated();
            return true;
        }else
            return false;
    }

    public void update(float delta){
        if(active&&active!=isActive()) {
            justEnded();
            active = false;
        }
        if(!isReady())
            cooldownCurrentTime -= delta;
        if(isActive()) {
            durationCurrentTime -= delta;
            active = true;
        }
    }

    public boolean isReady() {
        return cooldownCurrentTime<=0;
    }

    public boolean isActive(){
        return durationCurrentTime>0;
    }

    public void reset(){
        cooldownCurrentTime = 0;
        durationCurrentTime = 0;
    }

    public float getCooldownPercent() {
        return 1-(this.cooldownCurrentTime/this.cooldownTime);
    }

    public void setSprite(Sprite sprite){
        this.sprite = sprite;
    }

    public void setCooldownTime(float time){
        cooldownTime = time;
    }

    public void setDurationTime(float time){
        durationTime = time;
    }

    protected abstract void justActivated();

    protected abstract  void justEnded();

    public abstract void render(SpriteBatch sb);

    public abstract boolean check(Enemy e);
}
