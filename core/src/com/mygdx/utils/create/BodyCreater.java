package com.mygdx.utils.create;

import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;

public class BodyCreater {
	public static Body createBox(double x, double y, double width, double height, boolean isStatic,boolean isFixed, World world){
		Body pBody;
		
		BodyDef def = new BodyDef();
		if(isStatic) def.type = BodyDef.BodyType.StaticBody;
		else def.type = BodyDef.BodyType.DynamicBody;
		def.position.set((float)x, (float)y);
		def.fixedRotation = isFixed;
		pBody = world.createBody(def);
		
		PolygonShape shape = new PolygonShape();
		shape.setAsBox((float)(width / 2),(float) (height / 2));
		
		FixtureDef fixDef = new FixtureDef();
		fixDef.shape = shape;
		fixDef.density = 1.0f;
		fixDef.restitution = .7f;
		fixDef.friction = 0.4f;
		
		
		pBody.createFixture(fixDef);
		shape.dispose();
		return pBody;
	}


	public static Body createBox(double x, double y, double width, double height, boolean isStatic,boolean isFixed, World world, float density, float restitution){
		Body pBody;

		BodyDef def = new BodyDef();
		if(isStatic) def.type = BodyDef.BodyType.StaticBody;
		else def.type = BodyDef.BodyType.DynamicBody;
		def.position.set((float)x, (float)y);
		def.fixedRotation = isFixed;
		pBody = world.createBody(def);

		PolygonShape shape = new PolygonShape();
		shape.setAsBox((float)(width / 2),(float) (height / 2));

		FixtureDef fixDef = new FixtureDef();
		fixDef.shape = shape;
		fixDef.density = density;
		fixDef.restitution = restitution;
		fixDef.friction = 0.4f;


		pBody.createFixture(fixDef);
		shape.dispose();
		return pBody;
	}
	
	public static Body createCircle(float x, float y,float radius, boolean isStatic,boolean isFixed, World world){
		Body pBody;
		
		BodyDef def = new BodyDef();
		if(isStatic) def.type = BodyDef.BodyType.StaticBody;
		else def.type = BodyDef.BodyType.DynamicBody;
		def.position.set(x, y);
		def.fixedRotation = isFixed;
		pBody = world.createBody(def);
		
		CircleShape shape = new CircleShape();
		shape.setRadius(radius);
		
		FixtureDef fixDef = new FixtureDef();
		fixDef.shape = shape;
		fixDef.density = 1.0f;
		fixDef.restitution = .7f;
		fixDef.friction = 0.4f;
		
		
		pBody.createFixture(fixDef);
		shape.dispose();
		
		return pBody;
	}

	public static Body createCircle(float x, float y,float radius, float density, float restitution, boolean isStatic,boolean isFixed, World world){
		Body pBody;

		BodyDef def = new BodyDef();
		if(isStatic) def.type = BodyDef.BodyType.StaticBody;
		else def.type = BodyDef.BodyType.DynamicBody;
		def.position.set(x, y);
		def.fixedRotation = isFixed;
		pBody = world.createBody(def);

		CircleShape shape = new CircleShape();
		shape.setRadius(radius);

		FixtureDef fixDef = new FixtureDef();
		fixDef.shape = shape;
		fixDef.density = density;
		fixDef.restitution = restitution;
		fixDef.friction = 0.4f;


		pBody.createFixture(fixDef);
		shape.dispose();

		return pBody;
	}
	
	public static Body createCircle(double x, double y,double radius, double rotation, boolean isStatic,boolean isFixed, World world){
		Body pBody;
		
		BodyDef def = new BodyDef();
		if(isStatic) def.type = BodyDef.BodyType.StaticBody;
		else def.type = BodyDef.BodyType.DynamicBody;
		def.position.set((float)x, (float)y);
		def.fixedRotation = isFixed;
		pBody = world.createBody(def);
		
		CircleShape shape = new CircleShape();
		shape.setRadius((float)radius);
		
		FixtureDef fixDef = new FixtureDef();
		fixDef.shape = shape;
		fixDef.density = 1.0f;
		fixDef.restitution = .7f;
		fixDef.friction = 0.4f;
		
		
		pBody.createFixture(fixDef);
		shape.dispose();
		pBody.setTransform((float)x, (float)y, (float)rotation);
		return pBody;
	}
}
