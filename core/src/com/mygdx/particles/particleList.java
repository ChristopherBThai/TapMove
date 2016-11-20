package com.mygdx.particles;

/**
 * Created by Christopher Thai on 11/17/2016.
 */

public enum ParticleList{
	PLAYER_CLICK("particle/Click",true),
	PLAYER_TRAIL("particle/PlayerTrail",false),
	ENEMY_TRAIL("particle/EnemyTrail7",false),
	ENEMY_SMOKE("particle/enemyKilled3",true);

	public final Particle particle;

	ParticleList(String loc, boolean ends){
		particle = new Particle(loc,ends);
	}
}
