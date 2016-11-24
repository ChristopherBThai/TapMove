package com.mygdx.entities.abilities;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.mygdx.entities.player.Player;
import com.mygdx.entities.enemies.Enemy;
import com.mygdx.managers.ColorManager;
import com.mygdx.managers.SpriteManager;


public class Invincible extends Ability{

    float radius;

    public Invincible(Player player){
        super(player, SpriteManager.OUTER_CIRCLE.getSprite());
        super.setCooldownTime(30f);
        super.setDurationTime(5f);
        radius = player.getRadius()*1.3f;
    }

    @Override
    protected void justActivated() {
        player.invincible = true;
    }

    @Override
    protected void justEnded() {
        player.invincible = false;
    }

    @Override
    public void render(SpriteBatch sb) {
        if(isActive()) {
            sb.setColor(ColorManager.PLAYER);
            sb.draw(sprite,player.getPos().x-radius,player.getPos().y-radius,radius*2f,radius*2f);
        }
    }

    @Override
    public void update(float delta){
        super.update(delta);
    }

    @Override
    public boolean check(Enemy e) {
        return false;
    }
}
