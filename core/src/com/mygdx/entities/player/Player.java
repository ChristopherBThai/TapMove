package com.mygdx.entities.player;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.ParticleEffectPool;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.physics.box2d.World;
import com.mygdx.entities.Entity;
import com.mygdx.entities.abilities.Ability;
import com.mygdx.entities.abilities.ActiveAbility;
import com.mygdx.entities.enemies.LightBall;
import com.mygdx.game.MyGame;
import com.mygdx.particles.ParticleTypes;
import com.mygdx.utils.create.BodyCreater;
import com.mygdx.managers.ColorManager;
import com.mygdx.managers.SpriteManager;

public class Player extends Entity {

	private SpriteManager design;
	private ParticleEffectPool.PooledEffect trail;
	private ParticleTypes particleName;
	private float designAngle, designTargetAngle;
	
	private float radius;
	
	private float pushStrength;

	private Dash dash;

	public boolean invincible;
	private float life,currentLife;

	private Ability ability;
	
	public Player(float x, float y, float radius, World world){
		super(BodyCreater.createCircle(x, y, radius, false, true, world));
		init(radius);
	}

	public Player(float x, float y, float radius, World world, boolean isStatic){
		super(BodyCreater.createCircle(x, y, radius, isStatic, true, world));
		init(radius);
	}

	private void init(float radius){
		this.body.setLinearDamping(.3f);
		this.body.setAngularDamping(10f);
		this.radius = radius;
		pushStrength = 400;
		life = 20;
		currentLife = life;
		dash = new Dash(this);
		particleName = ParticleTypes.PLAYER_TRAIL;
	}
	
	@Override
	public void render(SpriteBatch sb){
		sb.setColor(ColorManager.PLAYER);
		sb.draw(SpriteManager.CIRCLE.getSprite(), body.getPosition().x-radius, body.getPosition().y-radius, radius*2, radius*2);
		if(design!=null){
			sb.setColor(ColorManager.PLAYER_DESIGN);
			sb.draw(design.getSprite(),body.getPosition().x-radius,body.getPosition().y-radius,radius,radius,radius*2,radius*2,1f,1f, designAngle);
		}

		if(ability!=null)
			ability.render(sb);
	}
	
	@Override
	public void update(float delta){
		if(trail!=null)
			trail.setPosition(this.getPos().x, this.getPos().y);
		if(dash!=null)
			dash.update(delta);
		currentLife -= delta;

		designAngle = designAngle + (designTargetAngle - designAngle) * .1f;

		if(ability !=null)
			ability.update(delta);
	}

	public void moveTo(float x, float y) {
		lookAtPoint(x,y);
		push(pushStrength);
	}

	public void dashTo(float x, float y){
		if(dash.fling(x,y))
			currentLife -= life*dash.getCostPercent();
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

		designTargetAngle = MathUtils.radiansToDegrees*body.getAngle();
		float direction = (designTargetAngle + 270) - (designAngle + 270);
		if(direction > 180)
			designAngle += 360;
		else if(direction < -180)
			designAngle -= 360;

	}

	public boolean check(com.mygdx.entities.enemies.Enemy e){
		boolean result1 =  isClose(e);
		boolean result2 = false;
		if(ability != null)
			result2 = ability.check(e);
		return result1||result2;
	}

	public boolean isClose(com.mygdx.entities.enemies.Enemy e) {
		//if(!(e instanceof LightBall)&&this.getPos().dst(e.getPos())<this.radius*2+e.getRadius())
		//	return true;
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

	public void setDesign(SpriteManager sprite){
		this.design = sprite;
	}

	public void setAbility(Ability ability){
		this.ability = ability;
	}

	public void abilityActivate(float x, float y){
		if(ability instanceof ActiveAbility)
			((ActiveAbility)ability).activate(x,y);
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

	public SpriteManager getDesign(){
		return design;
	}

	public Dash getDash(){
		return dash;
	}

	public void giveLife(float lifeAmount){
		currentLife += lifeAmount;
		if(currentLife>life)
			currentLife = life;
	}

	public float getRadius(){
		return radius;
	}

	public float getPushStrength(){
		return pushStrength;
	}

	public void reset() {
		this.setPos(MyGame.WIDTH/2f, MyGame.HEIGHT/2f);
		this.setVelocity(0,0);
		this.getBody().setLinearVelocity(0f,0f);
		trail = particleName.particle.getEffect();
		ParticleTypes.PLAYER_TRAIL.particle.setColor(trail,ColorManager.PLAYER);
		currentLife = life;
		dash.reset();
		if(ability!=null)
			ability.reset();
	}
}
