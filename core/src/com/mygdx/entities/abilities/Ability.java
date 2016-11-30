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

    public void update(float delta){}
    public void render(SpriteBatch sb){}
    public boolean check(Enemy e){return false;}
    public boolean playerHit(Enemy e){return false;}
    public void reset(){}
}
