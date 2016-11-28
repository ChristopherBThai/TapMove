package com.mygdx.entities.abilities;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.mygdx.entities.enemies.Enemy;
import com.mygdx.managers.ColorManager;
import com.mygdx.managers.SpriteManager;


public class Invincible extends ActiveAbility{

    private float radius;

    private static Sprite sprite = SpriteManager.OUTER_CIRCLE.getSprite();

    public Invincible(){
        super(SpriteManager.SHIELD,30,10);
        radius = player.getRadius()*1.3f;
    }

    @Override
    protected void justActivated() {
        super.justActivated();
        player.invincible = true;
    }

    @Override
    protected void justEnded() {
        super.justEnded();
        player.invincible = false;
    }

    @Override
    public void render(SpriteBatch sb) {
        super.render(sb);
        if(isActive()) {
            sb.setColor(ColorManager.NORMAL.r,ColorManager.NORMAL.g,ColorManager.NORMAL.b,currentDuration/duration);
            sb.draw(sprite,player.getPos().x-radius,player.getPos().y-radius,radius*2f,radius*2f);
        }
    }

    @Override
    public boolean check(Enemy e) {
        return false;
    }
}
