package com.mygdx.entities.enemies;

import com.badlogic.gdx.physics.box2d.World;

public class NormalEnemy extends Enemy{

    public NormalEnemy(float x, float y, float radius, World world) {
        super(x, y, radius, 1f, .7f, world);
    }

    @Override
    public boolean equals(int id){
        return Enemy.NORMAL_ENEMY == id;
    }
}
