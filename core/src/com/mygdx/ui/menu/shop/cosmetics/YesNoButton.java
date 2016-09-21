package com.mygdx.ui.menu.shop.cosmetics;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.mygdx.managers.AnimationManager;
import com.mygdx.utils.actors.ActorAnimator;
import com.mygdx.utils.actors.BoxButton;
import com.mygdx.utils.actors.Line;

/**
 * Created by Mono on 9/20/2016.
 */

public class YesNoButton{
	private Stage stage;

	private BoxButton button;//,yes,no;
	private Line test;

	private float oX,oY,oLength;
	private boolean selected;

	private ActorAnimator buttonExtend,buttonRetract,buttonA;

	public YesNoButton(float x, float y, Stage stage){
		setBounds(x,y);
		setActors();
		setActions();
	}

	public void doAnimation(){
		button.setAnimation(buttonA);
	}

	public void setBounds(float x, float y){
		oLength = Gdx.graphics.getHeight()*.09f;
		oX = x;
		oY = y;
	}

	public void setActors(){
		button = new BoxButton(oX+oLength/2,oY+oLength/2,0,0){
			@Override
			public void justTouched(){
				touched();
			}
		};
		button.setThickness(.1f);

		test = new Line(oX,oY,oLength,oLength);
	}

	private void touched(){
		selected = !selected;

		if(selected){
			button.setAnimation(buttonExtend);
		}else{
			button.setAnimation(buttonRetract);
		}
	}

	public void setActions(){
		buttonA = AnimationManager.getPopUp(oX,oY,oLength,oLength,.24f);

		buttonExtend = new ActorAnimator();
		buttonExtend.animateTo(oX-oLength,oY,oLength*2,oLength,.1f);

		buttonRetract = new ActorAnimator();
		buttonRetract.animateTo(oX,oY,oLength,oLength,.1f);
	}

	public void addActor(Stage stage){
		this.stage = stage;
		stage.addActor(button);
		stage.addActor(test);
	}

	public void resetScreen(){
		button.setBounds(oX+oLength/2,oY+oLength/2,0,0);
	}

	public void dispose(){
		button.dispose();
		//yes.dispose();
		//no.dispose();
	}

}
