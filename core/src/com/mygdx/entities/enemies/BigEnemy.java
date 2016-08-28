package com.mygdx.entities.enemies;

import com.badlogic.gdx.physics.box2d.World;
import com.mygdx.utils.managers.ColorManager;

public class BigEnemy extends Enemy{
    public BigEnemy(float x, float y, float radius, World world) {
        super(x, y, NORMAL_RADIUS*2, 5f, 0f, world);
        this.speed *=.6f;
        this.speedBuffer *=1f;
        this.color = ColorManager.BIG_ENEMY;
    }

    @Override
    public boolean equals(int id){
        return Enemy.BIG_ENEMY == id;
    }
}
