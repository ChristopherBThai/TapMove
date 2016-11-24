package com.mygdx.entities.abilities;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.mygdx.entities.enemies.Enemy;
import com.mygdx.entities.player.Player;
import com.mygdx.managers.ColorManager;
import com.mygdx.managers.SpriteManager;

public class Explosion extends Ability{

    float radius;
    float growthRate;

    float power;

    public Explosion(Player player){
        super(player,SpriteManager.OUTER_CIRCLE.getSprite());
        radius = 0;
        growthRate = 8f;
        power = 1500f;
        setDurationTime(.3f);
        setCooldownTime(30f);
    }

    @Override
    protected void justActivated(){
        radius = 0;
    }

    @Override
    protected void justEnded() {

    }

    @Override
    public void render(SpriteBatch sb) {
        if(isActive()) {
            sb.setColor(ColorManager.PLAYER);
            sb.draw(sprite,player.getPos().x-radius,player.getPos().y-radius,radius*2f,radius*2f);
        }
    }

    @Override
    public void update(float delta) {
        super.update(delta);
        if(isActive())
            radius += delta*growthRate*player.getRadius()*(radius+1);
    }

    @Override
    public void reset() {
        super.reset();
        radius = 0;
    }

    @Override
    public boolean check(Enemy e) {
        if(!isActive())
            return false;
        float dx = player.getPos().x - e.getPos().x;
        float dy = player.getPos().y - e.getPos().y;
        double dst = Math.sqrt(dx*dx + dy*dy);
        if(Math.abs(dst-this.radius)<=player.getRadius()*5){
            e.getBody().setLinearVelocity(0f,0f);
            e.setVelocity(-(dx/dst)*power, -(dy/dst)*power);
        }
        return false;
    }
}
