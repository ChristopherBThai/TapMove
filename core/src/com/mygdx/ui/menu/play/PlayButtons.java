package com.mygdx.ui.menu.play;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.mygdx.game.MyGame;
import com.mygdx.managers.ScreenManager;
import com.mygdx.screen.GameScreen;
import com.mygdx.screen.MenuScreen;
import com.mygdx.ui.menu.BackButton;
import com.mygdx.utils.Save;
import com.mygdx.utils.actors.ActorAnimator;
import com.mygdx.utils.actors.BoxButton;

/**
 * Created by Christopher Thai on 10/22/2016.
 */

public class PlayButtons{
	private MenuScreen screen;
	private Stage stage;

	private BackButton back;

	private float width, height;
	private float classicX,classicY,darkX,darkY;
	private BoxButton classic,dark;
	private ActorAnimator classicClicked,darkClicked;

	public PlayButtons(MenuScreen screen){
		this.screen = screen;
		this.stage = screen.stage;
		this.setBounds();
		this.setActors();
		this.setActions();
	}

	public void set(boolean withReset){
		if(withReset) {
			resetScreen();
			back.doAnimation();
			stage.addActor(back.getActor());
			stage.addActor(classic);
			classic.setOpacity(0f);
			classic.setAnimateOpacity(1f);
			classic.setAnimateInsideOpacity(1f);
			stage.addActor(dark);
		}else{
			stage.addActor(back.getActor());
			stage.addActor(classic);
			stage.addActor(dark);
		}
	}

	public void setBounds(){
		width = Gdx.graphics.getWidth()*.47f;
		height = Gdx.graphics.getHeight()*.08f;

		classicX = Gdx.graphics.getWidth()*.5f-width*.5f;
		classicY = Gdx.graphics.getHeight()*.5f+height*.5f;
		darkX = classicX;
		darkY = Gdx.graphics.getHeight()*.5f-height*1.5f;
	}

	public void setActors(){
		back = new com.mygdx.ui.menu.BackButton(){
			@Override
			public void back(){
				stage.clear();
				MenuScreen.menu.set();
			}
		};

		classic = new BoxButton(classicX,classicY,width,height){
			@Override
			public void justTouched(){
				classic.setAnimation(classicClicked);
			}
		};
		classic.setThickness(.1f);
		classic.setText("Classic");

		dark = new BoxButton(darkX,darkY,width,height){
			@Override
			public void justTouched(){
				dark.setAnimation(darkClicked);
			}
		};
		dark.setThickness(.1f);
		dark.setText("Dark");

	}

	public void setActions(){

		classicClicked = new ActorAnimator();
		classicClicked.addCommand(new ActorAnimator.ActionCommand(){
			@Override
			public void command(ActorAnimator animator){
				classic.removeTouch();
				classic.lockText(true);
				dark.lockText(false);
				classic.setAnimateInsideOpacity(0f);
				dark.moveTo(dark.getX(),-dark.getHeight(),.1f);
				back.moveToHide();
			}
		});
		classicClicked.animateTo(-classic.getThickness()*1.2f,-classic.getThickness()*1.2f,Gdx.graphics.getWidth()+classic.getThickness()*1.2f,Gdx.graphics.getHeight()+classic.getThickness()*1.2f,.1f);
		classicClicked.addCommand(new ActorAnimator.ActionCommand(){
			@Override
			public void command(ActorAnimator animator){
				MyGame.gameScreen.setMode(GameScreen.CLASSIC);
				ScreenManager.setScreen(MyGame.gameScreen,false,false);
				stage.clear();
			}
		});

		darkClicked = new ActorAnimator();
		darkClicked.addCommand(new ActorAnimator.ActionCommand(){
			@Override
			public void command(ActorAnimator animator){
				dark.removeTouch();
				dark.lockText(true);
				classic.lockText(false);
				dark.setAnimateInsideOpacity(0f);
				classic.moveTo(classic.getX(),Gdx.graphics.getHeight()+classic.getHeight(),.1f);
				back.moveToHide();
			}
		});
		darkClicked.animateTo(-dark.getThickness()*1.2f,-dark.getThickness()*1.2f,Gdx.graphics.getWidth()+dark.getThickness()*1.2f,Gdx.graphics.getHeight()+dark.getThickness()*1.2f,.1f);
		darkClicked.addCommand(new ActorAnimator.ActionCommand(){
			@Override
			public void command(ActorAnimator animator){
				MyGame.gameScreen.setMode(GameScreen.DARK);
				ScreenManager.setScreen(MyGame.gameScreen,false,false);
				stage.clear();
			}
		});
	}

	public void resetScreen(){
		back.resetScreen();
		classic.setBounds(classicX,classicY,width,height);
		classic.lockText(false);
		classic.addTouch();
		dark.setBounds(darkX,darkY,width,height);
		dark.lockText(false);
		if(Save.isTutorial()){
			dark.removeTouch();
			dark.setOpacity(0f);
			dark.setAnimateOpacity(.3f);
			dark.setAnimateInsideOpacity(.3f);
		}else{
			dark.addTouch();
			dark.setOpacity(0f);
			dark.setAnimateOpacity(1f);
			dark.setAnimateInsideOpacity(1f);
		}
	}

	public void dispose(){
		back.dispose();
	}
}
