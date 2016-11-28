package com.mygdx.entities.abilities;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.mygdx.entities.enemies.Enemy;
import com.mygdx.managers.ColorManager;
import com.mygdx.managers.SpriteManager;

public class Explosion extends ActiveAbility{

    private float radius;
    private float growthRate;
    private float power;

    private static Sprite sprite = SpriteManager.OUTER_CIRCLE.getSprite();

    public Explosion(){
        super(SpriteManager.BOMB,30,.3f);
        radius = 0;
        growthRate = 8f;
        power = 1500f;
    }

    @Override
    public void render(SpriteBatch sb) {
        super.render(sb);
        if(isActive()) {
            sb.setColor(ColorManager.NORMAL);
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

    @Override
    public void justEnded(){
        super.justEnded();
        radius = 0;
    }
}
