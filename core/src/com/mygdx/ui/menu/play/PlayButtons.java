package com.mygdx.ui.menu.play;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.mygdx.game.MyGame;
import com.mygdx.managers.ScreenManager;
import com.mygdx.managers.SoundManager;
import com.mygdx.screen.GameScreen;
import com.mygdx.screen.MenuScreen;
import com.mygdx.ui.menu.ButtonLayout;
import com.mygdx.ui.menu.extra.actors.BackButton;
import com.mygdx.utils.Save;
import com.mygdx.utils.actors.ActorAnimator;
import com.mygdx.utils.actors.BoxButton;

/**
 * Created by Christopher Thai on 10/22/2016.
 */

public class PlayButtons extends ButtonLayout{

	public PlayButtons(){
		super("Play",false,2,true);
		buttons.get(0).setText("Classic");
		buttons.get(1).setText("Dark");
	}

	@Override
	public void resetScreen(){
		super.resetScreen();
		BoxButton dark = buttons.get(1);
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

	@Override
	protected void buttonTouched(int index){

	}

	@Override
	protected void buttonActivated(int index){
		switch(index){
			case 0:
				MyGame.gameScreen.setMode(GameScreen.CLASSIC);
				ScreenManager.setScreen(MyGame.gameScreen,false,false);
				stage.clear();
				break;
			case 1:
				MyGame.gameScreen.setMode(GameScreen.DARK);
				ScreenManager.setScreen(MyGame.gameScreen,false,false);
				stage.clear();
				break;
		}
	}

	@Override
	protected void backClicked(){
		MenuScreen.menu.set(false);
	}
}
