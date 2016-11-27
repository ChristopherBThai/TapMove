package com.mygdx.entities.abilities;


import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.mygdx.entities.player.Player;
import com.mygdx.entities.enemies.Enemy;
import com.mygdx.screen.GameScreen;

public abstract class Ability {

    protected static Player player;

    public Ability(){
        if(player == null)
            player = GameScreen.entMan.player;
    }

    public abstract void update(float delta);
    public abstract void render(SpriteBatch sb);
    public abstract boolean check(Enemy e);
    public abstract void reset();
}
