package com.mygdx.particles;

/**
 * Created by Christopher Thai on 11/17/2016.
 */

public enum ParticleTypes{
	PLAYER_CLICK("particle/Click",true),
	PLAYER_TRAIL("particle/PlayerTrail",false),
	ENEMY_TRAIL("particle/EnemyTrail",false),
	ENEMY_SMOKE("particle/EnemySmoke",true),
	ENEMY_ABSORB("particle/EnemyAbsorb",false),
	TELEPORT("particle/Teleport",true),
	ABSORB("particle/Absorb",true);

	public final Particle particle;

	ParticleTypes(String loc, boolean ends){
		particle = new Particle(loc,ends);
	}
}
