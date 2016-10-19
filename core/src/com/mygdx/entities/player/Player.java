package com.mygdx.entities.player;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.physics.box2d.World;
import com.mygdx.entities.Entity;
import com.mygdx.entities.abilities.Ability;
import com.mygdx.entities.abilities.Explosion;
import com.mygdx.entities.enemies.LightBall;
import com.mygdx.game.MyGame;
import com.mygdx.utils.create.BodyCreater;
import com.mygdx.managers.ColorManager;
import com.mygdx.managers.SpriteManager;

public class Player extends Entity {
	
	private float radius;
	
	public float pushStrength;

	private Dash dash;
	private Ability ability;

	public boolean invincible;
	private float life,currentLife;
	
	public Player(float x, float y, float radius, World world){
		super(BodyCreater.createCircle(x, y, radius, false, true, world));
		this.body.setLinearDamping(.3f);
		this.body.setAngularDamping(10f);
		this.radius = radius;
		this.ability = new Explosion(this);
		pushStrength = 400;
		life = 20;
		currentLife = life;
		dash = new Dash(this);
	}

	public Player(float x, float y, float radius, World world, boolean isStatic){
		super(BodyCreater.createCircle(x, y, radius, isStatic, true, world));
		this.body.setLinearDamping(.3f);
		this.body.setAngularDamping(10f);
		this.radius = radius;
		this.ability = new Explosion(this);
		life = 30;
		currentLife = life;
		pushStrength = 400;
	}
	
	@Override
	public void render(SpriteBatch sb){
		if(ability!=null)
			ability.render(sb);
		sb.setColor(ColorManager.PLAYER);
		sb.draw(SpriteManager.getCircle(), body.getPosition().x-radius, body.getPosition().y-radius, radius*2, radius*2);
	}
	
	@Override
	public void update(float delta){
		if(ability!=null)
			ability.update(delta);
		if(dash!=null)
			dash.update(delta);
		currentLife -= delta;
	}

	public void moveTo(float x, float y) {
		lookAtPoint(x,y);
		push(pushStrength);
	}

	public void dashTo(float x, float y){
		if(dash.fling(x,y))
			currentLife -= life*.25f;
	}

	public void push(float speed) {
		float rad = this.body.getAngle();
		float xVel = (float) Math.cos(rad);
		float yVel = (float) Math.sin(rad);
		this.setVelocity(xVel*speed,yVel*speed);
	}

	public void lookAtPoint(float x, float y) {
		float yDiff = y - this.body.getPosition().y;
		float xDiff = x - this.body.getPosition().x;
		float radians;
		
		if(xDiff==0)
			if(yDiff<0)
				radians = (float) (Math.PI/2f);
			else
				radians = -(float) (Math.PI/2f);
		else
			radians = (float) (Math.atan(yDiff/xDiff));
		
		if(xDiff<0){
			radians -= (float) (Math.PI);
		}
		
		this.body.setTransform(this.body.getPosition().x, this.body.getPosition().y, radians);
	}

	public boolean useAbility(){
		if(this.ability!=null)
			return this.ability.activate();
		return false;
	}

	public boolean check(com.mygdx.entities.enemies.Enemy e){
		boolean result1 =  isClose(e);
		boolean result2 = ability.check(e);
		return result1||result2;
	}

	public boolean isClose(com.mygdx.entities.enemies.Enemy e) {
		if(!(e instanceof LightBall)&&this.getPos().dst(e.getPos())<this.radius*2+e.getRadius())
			return true;
		return false;
	}

	public boolean isDashing(){
		if(dash==null)
			return false;
		return dash.isDashing();
	}

	public boolean isDead(){
		if(isDashing())
			return false;
		if(currentLife<=0)
			return true;
		if(this.getPos().x<0-this.radius||
				this.getPos().x> MyGame.WIDTH+this.radius||
				this.getPos().y<0-this.radius||
				this.getPos().y> MyGame.HEIGHT+radius)
			return true;
		return false;
	}

	public void setAbility(Ability ability){
		this.ability = ability;
	}

	public Ability getAbility(){
		return this.ability;
	}

	public float getLifePercent(){
		return currentLife/life;
	}

	public float getMaxLife(){
		return life;
	}

	public float getCurrentLife(){
		return currentLife;
	}

	public void giveLife(float lifeAmount){
		currentLife += lifeAmount;
		if(currentLife>life)
			currentLife = life;
	}

	public float getRadius(){
		return radius;
	}

	public boolean abilityReady() {
		return ability.isReady();
	}

	public void reset() {
		this.setPos(MyGame.WIDTH/2f, MyGame.HEIGHT/2f);
		this.setVelocity(0,0);
		this.getBody().setLinearVelocity(0f,0f);
		ability.reset();
		currentLife = life;
	}
}
