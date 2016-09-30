package com.mygdx.utils.actors;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;

/**
 * Created by Mono on 9/29/2016.
 */

public class Button extends AnimatableActor{

	private float down;
	private boolean touchable;
	private boolean visible;

	Sprite sprite;

	public Button(float x, float y, float width, float height){
		super();
		super.setBounds(x,y,width,height);
		init();
	}

	public Button(){
		super();
		init();
	}

	private void init(){
		this.setOrigin(getWidth()/2,getHeight()/2);
		this.setRotation(0);
		addTouch();
		addListener(new ClickListener() {
			public void clicked (InputEvent event, float x, float y) {
				justTouched();
				down = .15f;
			}
		});
	}

	@Override
	public void act(float delta){
		super.act(delta);

		if(down > 0)
			down -= delta;
		else if(down < 0)
			down = 0;
	}

	@Override
	public void draw(Batch batch, float parentAlpha){
		if(sprite!=null){
			batch.setColor(getColor().r,getColor().g,getColor().b,opacity);
			if(getRotation()==0)
				batch.draw(sprite,getX(),getY(),getWidth(),getHeight());
			else
				batch.draw(sprite,getX(),getY(),getOriginX(),getOriginY(),getWidth(),getHeight(),1,1,getRotation());
		}
	}

	public void addTouch(){
		touchable = true;
		setTouchable(Touchable.enabled);
	}

	public void removeTouch(){
		setTouchable(Touchable.disabled);
		touchable = false;
	}

	public void justTouched(){}

	public boolean tap(float x, float y){
		if(touchable&&
				x>=this.getX()&&
				x<=this.getX()+this.getWidth()&&
				y>=this.getY()&&
				y<=this.getY()+this.getHeight()) {
			justTouched();
			down = .15f;
			return true;
		}
		return false;
	}

	public boolean isPressed(){
		return down>0;
	}

	public void setVisible(boolean visible){
		this.visible = visible;
	}

	public void setSprite(Sprite sprite){
		this.sprite = sprite;
	}

	@Override
	protected void sizeChanged () {
		setOrigin(getWidth()/2,getHeight()/2);
	}
}
