package com.mygdx.ui.menu.extra.actors;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.mygdx.game.MyGame;
import com.mygdx.managers.SpriteManager;
import com.mygdx.utils.actors.BoxButton;
import com.mygdx.utils.actors.Image;

/**
 * Created by Christopher Thai on 1/20/2017.
 */

public class SignInButton{

	private BoxButton signInOut,achievement,leaderboard;
	private Image no;
	private float length,y;
	private float signX,achievementX,leaderboardX;
	public static boolean noResponse = true;
	public static boolean prevSignedInState,signedIn;

	public SignInButton(float x, float y, float width, float height,float gap){
		this.setBounds(x,y,width,height,gap);
		this.setActors();
		prevSignedInState = false;
	}

	public void moveToReset(){
		signInOut.moveTo(signX,y,length,length,.1f);
		achievement.moveTo(achievementX,y,length,length,.1f);
		leaderboard.moveTo(leaderboardX,y,length,length,.1f);
		if(no.getOpacity()>0){
			float size = length *.3f;
			no.moveTo(signX+length-size*1.3f,y+size*.3f,size,size,.1f);
		}
	}

	public void moveToHide(){
		signInOut.moveTo(signX,-length,length,length,.1f);
		achievement.moveTo(achievementX,-length,length,length,.1f);
		leaderboard.moveTo(leaderboardX,-length,length,length,.1f);
		if(no.getOpacity()>0){
			float size = length *.3f;
			no.moveTo(signX+length-size*1.3f,-length+size*.3f,size,size,.1f);
		}
	}

	public void addActor(Stage stage){
		stage.addActor(signInOut);
		stage.addActor(achievement);
		stage.addActor(leaderboard);
		stage.addActor(no);
	}

	public void setBounds(float x, float y, float width, float height,float gap){
		length = (width - (2*gap))/3;
		this.y = y;
		achievementX = x;
		signX = x+length+gap;
		leaderboardX = x+length*2+gap*2;
	}

	public void setActors(){
		signInOut = new BoxButton(){
			@Override
			public void justTouched(){
				if(signedIn)
					MyGame.signOut();
				else
					MyGame.signIn();
			}
			@Override
			public void act(float delta){
				super.act(delta);
				if(prevSignedInState!=signedIn){
					prevSignedInState = signedIn;
					if(signedIn){
						achievement.setAnimateOpacity(1f);
						achievement.setAnimateInsideOpacity(1f);
						leaderboard.setAnimateOpacity(1f);
						leaderboard.setAnimateInsideOpacity(1f);
						no.setAnimateOpacity(0f);
						achievement.addTouch();
						leaderboard.addTouch();
					}else{
						achievement.setAnimateOpacity(.3f);
						achievement.setAnimateInsideOpacity(.3f);
						leaderboard.setAnimateOpacity(.3f);
						leaderboard.setAnimateInsideOpacity(.3f);
						no.setAnimateOpacity(1f);
						achievement.removeTouch();
						leaderboard.removeTouch();
					}
				}
			}
		};
		signInOut.setThickness(.1f);
		signInOut.setInside(SpriteManager.PLAY_BUTTON);
		signInOut.setInsideScale(.7f);

		achievement = new BoxButton(){
			@Override
			public void justTouched(){
				MyGame.getGooglePlayHandler().displayAchievements();
			}
			@Override
			public void act(float delta){
				super.act(delta);
			}
		};
		achievement.setThickness(.1f);
		achievement.setInside(SpriteManager.ACHIEVEMENT);
		achievement.setInsideScale(.7f);

		leaderboard = new BoxButton(){
			@Override
			public void justTouched(){
				MyGame.getGooglePlayHandler().displayLeaderboards();
			}
			@Override
			public void act(float delta){
				super.act(delta);
			}
		};
		leaderboard.setThickness(.1f);
		leaderboard.setInside(SpriteManager.LEADERBOARD);
		leaderboard.setInsideScale(.7f);

		no = new Image(SpriteManager.NO_SYMBOL);
	}

	public void resetScreen(){
		signInOut.setBounds(signX,y,length,length);
		achievement.setBounds(achievementX,y,length,length);
		leaderboard.setBounds(leaderboardX,y,length,length);
		float size = length *.3f;
		no.setBounds(signX+length-size*1.3f,y+size*.3f,size,size);
		if(signedIn){
			achievement.setOpacity(1f);
			leaderboard.setOpacity(1f);
			no.setOpacity(0f);
			achievement.addTouch();
			leaderboard.addTouch();
		}else{
			achievement.setOpacity(.3f);
			leaderboard.setOpacity(.3f);
			no.setOpacity(1f);
			achievement.removeTouch();
			leaderboard.removeTouch();
		}
	}

	public void dispose(){
		signInOut.dispose();
		achievement.dispose();
		leaderboard.dispose();
	}

	public static void signedIn(){
		noResponse = false;
		signedIn = true;
	}

	public static void signedOut(){
		noResponse = false;
		signedIn = false;
	}

}
