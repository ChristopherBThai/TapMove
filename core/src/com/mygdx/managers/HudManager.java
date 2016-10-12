package com.mygdx.managers;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.mygdx.game.MyGame;
import com.mygdx.screen.GameScreen;
import com.mygdx.ui.game.AbilityButton;
import com.mygdx.ui.game.GameoverButtons;
import com.mygdx.ui.game.Pause;
import com.mygdx.ui.game.Score;

public class HudManager {
	
	GameScreen game;
	Stage stage;

	//public AbilityButton ability;
	public Pause pause;
	public Score score;
	public GameoverButtons gameover;

	float opacity;

	public HudManager(GameScreen game){
		this.game = game;
		stage = new Stage(new ScreenViewport());

		//ability = new AbilityButton(stage);
		pause = new Pause(stage);
		score = new Score(stage);
		gameover = new GameoverButtons(stage,game);

		this.reset();
	}
	
	public void render(SpriteBatch sb){
		stage.draw();
	}
	
	public void render(ShapeRenderer sr){
		if(!game.running){
			opacity+=.01f;
			sr.setColor(.15f,.15f,.15f,opacity);
			sr.rect(0, 0, MyGame.WIDTH, MyGame.HEIGHT*1.3f);
		}
	}
	
	public void update(float delta){
		stage.act();
		pause.update(delta);
		score.update(delta);
		//ability.update(delta);
		gameover.update(delta);
	}
	
	public boolean tap(float x, float y){
		if(opacity>1){
			gameover.tap(x,y);
		}
		return pause.tap(x,y);
	}
	
	public void reset(){
		opacity = 0;
		stage.clear();
		score.reset();
		//ability.reset();
		pause.reset();
		gameover.reset();
	}
	
	public void dispose(){
		stage.dispose();
	}
}
