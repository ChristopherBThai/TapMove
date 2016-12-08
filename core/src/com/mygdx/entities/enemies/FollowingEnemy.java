package com.mygdx.entities.enemies;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.physics.box2d.World;
import com.mygdx.managers.ColorManager;
import com.mygdx.managers.SpriteManager;
import com.mygdx.screen.GameScreen;
import com.mygdx.utils.MathUtility;

/**
 * Created by Christopher Thai on 6/22/2016.
 */
public class FollowingEnemy extends Enemy{

	private float angle,visualAngle;
	private static Sprite line = SpriteManager.BOX.getSprite();
	private float lineThickness;
	private ColorManager lineColor = ColorManager.NORMAL;

	private float pushSpeed;
	private float currentPushTime,maxPushTime;

	public FollowingEnemy(float x, float y, World world) {
		super(x, y, NORMAL_RADIUS, .1f, .7f, world);
		angle = 0;
		visualAngle = angle;
		lineThickness = NORMAL_RADIUS * .1f;
		pushSpeed = speed*1.2f;
		maxPushTime = 2;
		currentPushTime = maxPushTime;
	}

	@Override
	public void render(SpriteBatch sb){
		super.render(sb);
		sb.setColor(lineColor.getColor());
		sb.draw(line,body.getPosition().x,body.getPosition().y-lineThickness/2,0,lineThickness/2,radius,lineThickness,1f,1f,visualAngle);
	}

	@Override
	public void update(float delta){
		super.update(delta);
		currentPushTime -= delta;
		if(currentPushTime<=0)
			pushToPlayer();
		visualAngle = visualAngle + (angle - visualAngle) * .1f;
	}

	private void pushToPlayer(){
		float radians = MathUtility.vectorToRadians(GameScreen.entMan.player.getPos().x - body.getPosition().x,GameScreen.entMan.player.getPos().y - body.getPosition().y);

		float xVel = (float) Math.cos(radians);
		float yVel = (float) Math.sin(radians);
		this.setVelocity(xVel*pushSpeed,yVel*pushSpeed);

		angle = MathUtils.radiansToDegrees * radians;

		float direction = (angle + 270) - (visualAngle + 270);
		if(direction > 180)
			visualAngle += 360;
		else if(direction < -180)
			visualAngle -= 360;

		currentPushTime = maxPushTime;
	}

	@Override
	public boolean equals(int id){
		return Enemy.FOLLOW_ENEMY == id;
	}
}
