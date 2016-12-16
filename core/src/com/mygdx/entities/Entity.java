package com.mygdx.entities;

import com.badlogic.gdx.graphics.g2d.ParticleEmitter;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;

public class Entity {
	
	protected Body body;
	
	float weight;
	
	public Entity(Body body){
		this.body = body;
		this.body.setUserData(this);
		weight = this.body.getMass();
	}
	
	public void update(float delta){
		
	}
	
	public void render(SpriteBatch sb){
		
	}
	
	public void setPos(double x, double y) {
		this.body.setTransform((float)x, (float)y, this.body.getAngle());
	}

	public void setPos(Vector2 pos) {
		setPos(pos.x,pos.y);
	}
	
	public void setVelocity(double x, double y){
		this.body.applyForceToCenter((float)(x*weight), (float)(y*weight), true);
	}
	
	public Vector2 getPos(){
		return this.body.getPosition();
	}
	
	public Body getBody(){
		return body;
	}
}
