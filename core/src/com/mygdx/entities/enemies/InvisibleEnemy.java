package com.mygdx.entities.enemies;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.physics.box2d.World;
import com.mygdx.managers.SpriteManager;

/**
 * Created by Christopher Thai on 6/22/2016.
 */
public class InvisibleEnemy extends Enemy{

    float opacity, opacityRate;
    float duration, durationTime;

    public InvisibleEnemy(float x, float y, World world) {
        super(x, y, NORMAL_RADIUS, 1f, .7f, world);
        opacity = 0f;
        opacityRate = 1/(4f*60);
        duration = 2f;
    }

    @Override
    public void render(SpriteBatch sb){
        sb.setColor(color.r,color.g,color.b,opacity);
        sb.draw(SpriteManager.CIRCLE.getSprite(), body.getPosition().x-radius, body.getPosition().y-radius, radius*2, radius*2);
    }

    @Override
    public void update(float delta){
        super.update(delta);

        if(durationTime>0)
            durationTime -= delta;
        else {
            opacity += opacityRate;
            if (opacity <= 0) {
                opacity = 0;
                durationTime = duration;
                opacityRate *= -1;
            } else if (opacity >= 1f) {
                opacity = 1f;
                opacityRate *= -1;
            }
        }
    }

    @Override
    public boolean equals(int id){
        return Enemy.INVISIBLE_ENEMY == id;
    }
}
