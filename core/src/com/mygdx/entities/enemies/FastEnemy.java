package com.mygdx.entities.enemies;

import com.badlogic.gdx.physics.box2d.World;
import com.mygdx.managers.ColorManager;


public class FastEnemy extends Enemy{
    public FastEnemy(float x, float y, World world) {
        super(x, y, NORMAL_RADIUS*.5f, .15f, 1.1f, world);
        this.speed *=.7f;
        this.speedBuffer *=2.5f;
        this.color = ColorManager.FAST_ENEMY;
    }

    @Override
    public boolean equals(int id){
        return Enemy.FAST_ENEMY == id;
    }
}
