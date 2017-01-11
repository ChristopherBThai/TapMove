package com.mygdx.ui.tutorial;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.mygdx.entities.enemies.Enemy;
import com.mygdx.entities.enemies.NormalEnemy;
import com.mygdx.game.MyGame;
import com.mygdx.managers.ColorManager;
import com.mygdx.managers.SpriteManager;
import com.mygdx.screen.GameScreen;
import com.mygdx.utils.actors.Image;
import com.mygdx.utils.actors.Text;

import java.util.ArrayList;

/**
 * Created by Christopher Thai on 1/10/2017.
 */

public class TutorialScreen{

	Stage stage;
	GameScreen game;
	private final float WIDTH = Gdx.graphics.getWidth(),HEIGHT = Gdx.graphics.getHeight();

	private int scene;
	private float timer;
	private boolean pause;

	private Image background;
	private int step;
	private Text[] texts;
	private float fontSize,largeFontSize;

	public static boolean health,tap,dash,ability,score;

	public TutorialScreen(GameScreen game){
		this.game = game;
		background = new Image(SpriteManager.BOX,0,0, WIDTH, HEIGHT);
		background.setColor(Color.BLACK);
		largeFontSize = Gdx.graphics.getWidth()*.066f;
		fontSize = Gdx.graphics.getWidth()*.04f;
	}

	public void reset(){
		if(stage == null)
			stage = new Stage(new ScreenViewport());
		if(texts == null){
			texts = new Text[6];
			for(int i = 0;i < texts.length; i++){
				texts[i] = new Text(fontSize,"");
				texts[i].setColor(ColorManager.NORMAL);
			}
		}
		stage.clear();
		health = false;
		tap = false;
		dash = false;
		ability = false;
		score = false;
		pause = false;
		step = 1;
		scene = 1;
		stage.addActor(background);
		background.setOpacity(0f);
		background.setAnimateOpacity(1f);
	}

	public float update(float delta){

		stage.act(delta);
		if(timer > 0)
			timer -= delta;

		switch(scene){
			case 1:
				if(background.getOpacity()>=1f&&step==1){
					pause = true;
					stage.addActor(texts[0]);
					texts[0].setFontSize(largeFontSize);
					texts[0].setText("Welcome to");
					texts[0].setOpacity(0f);
					texts[0].setAnimateOpacity(1f);
					texts[0].setPosition(WIDTH/2-texts[0].getWidth()/2,HEIGHT*.75f+texts[0].getHeight()/2);
					stage.addActor(texts[1]);
					texts[1].setFontSize(largeFontSize);
					texts[1].setText("Tap Move!");
					texts[1].setOpacity(0f);
					texts[1].setAnimateOpacity(1f);
					texts[1].setPosition(WIDTH/2-texts[1].getWidth()/2,HEIGHT*.67f+texts[1].getHeight()/2);
					step = 2;
					timer = 3.2f;
				}else if(step == 2 && timer <= 0){
					stage.addActor(texts[2]);
					texts[2].setFontSize(largeFontSize);
					texts[2].setText("Let me show you");
					texts[2].setOpacity(0f);
					texts[2].setAnimateOpacity(1f);
					texts[2].setPosition(WIDTH/2-texts[2].getWidth()/2,HEIGHT*.45f+texts[2].getHeight()/2);
					stage.addActor(texts[3]);
					texts[3].setFontSize(largeFontSize*.94f);
					texts[3].setText("how to play!");
					texts[3].setOpacity(0f);
					texts[3].setAnimateOpacity(1f);
					texts[3].setPosition(WIDTH/2-texts[3].getWidth()/2,HEIGHT*.372f+texts[3].getHeight()/2);
					step = 3;
					timer = 3.2f;
				}else if(step == 3 && timer <= 0){
					texts[0].setAnimateOpacity(0f);
					texts[1].setAnimateOpacity(0f);
					texts[2].setAnimateOpacity(0f);
					texts[3].setAnimateOpacity(0f);
					background.setAnimateOpacity(0f);
					step = 4;
				}else if(step == 4 && background.getOpacity()<=0){
					stage.clear();
					step = 1;
					scene = 2;
				}
				break;
			case 2:
				if(step == 1){
					stage.addActor(texts[0]);
					texts[0].setFontSize(fontSize);
					texts[0].setText("This is you");
					texts[0].setOpacity(0f);
					texts[0].setAnimateOpacity(1f);
					texts[0].setPosition(WIDTH/2-texts[0].getWidth()/2,HEIGHT*.45f+texts[0].getHeight()/2);
					Enemy enemy = new NormalEnemy(0,0,GameScreen.entMan.world);
					enemy.randomize();
					enemy.setPos(GameScreen.entMan.player.getPos().x,MyGame.HEIGHT+enemy.getRadius());
					enemy.getBody().setLinearVelocity(0,0);
					GameScreen.entMan.enemies.add(enemy);
					step++;
					timer = 2.5f;
				}else if(step == 2 && timer <= 0){
					GameScreen.entMan.enemies.get(0).getBody().setLinearVelocity(0,-GameScreen.entMan.enemies.get(0).getRadius()*4);
					step++;
					timer = 1f;
					pause = false;
				}else if(step == 3 && timer <= 0){
					pause = true;
					stage.addActor(texts[1]);
					texts[1].setFontSize(fontSize);
					texts[1].setText("This is the enemy");
					texts[1].setOpacity(0f);
					texts[1].setAnimateOpacity(1f);
					texts[1].setPosition(WIDTH/2-texts[1].getWidth()/2,HEIGHT*.95f);
					stage.addActor(texts[5]);
					texts[5].setFontSize(fontSize*.8f);
					texts[5].setText("(Don't touch it!)");
					texts[5].setOpacity(0f);
					texts[5].setAnimateOpacity(1f);
					texts[5].setPosition(WIDTH/2-texts[5].getWidth()/2,HEIGHT*.89f);
					timer = 2.5f;
					step++;
				}else if(step == 4 && timer <= 0){
					stage.addActor(texts[2]);
					texts[2].setFontSize(fontSize*.8f);
					texts[2].setText("Tap here to");
					texts[2].setOpacity(0f);
					texts[2].setAnimateOpacity(1f);
					stage.addActor(texts[3]);
					texts[3].setFontSize(fontSize*.8f);
					texts[3].setText("dodge the enemy!");
					texts[3].setOpacity(0f);
					texts[3].setAnimateOpacity(1f);
					float xTemp = WIDTH-texts[3].getWidth();
					texts[3].setPosition(xTemp,HEIGHT*.63f+texts[3].getHeight()/2);
					texts[2].setPosition(xTemp + (WIDTH-xTemp)/2 - texts[2].getWidth()/2,HEIGHT*.68f+texts[2].getHeight()/2);
					step++;
				}else if(step == 5 && !pause){
					texts[0].setAnimateOpacity(0f);
					texts[1].setAnimateOpacity(0f);
					texts[2].setAnimateOpacity(0f);
					texts[3].setAnimateOpacity(0f);
					texts[5].setAnimateOpacity(0f);
					stage.addActor(texts[4]);
					texts[4].setFontSize(largeFontSize);
					texts[4].setText("Well done!");
					texts[4].setOpacity(0f);
					texts[4].setAnimateOpacity(1f);
					texts[4].setPosition(WIDTH/2-texts[4].getWidth()/2,HEIGHT*.6f+texts[4].getHeight()/2);
					step++;
					timer = 4f;
				}else if(step == 6 && timer <= 0){
					stage.addActor(background);
					background.setOpacity(0f);
					background.setAnimateOpacity(1f);
					step++;
				}else if(step == 7 && background.getOpacity()>=1f){
					stage.clear();
					step = 1;
					stage.addActor(background);
					background.setAnimateOpacity(0f);
					scene = 3;
					game.partMan.reset();
					game.entMan.reset();
				}
				break;
			case 3:
				if(step == 1 && background.getOpacity()<=0f){
					stage.clear();
					stage.addActor(texts[0]);
					texts[0].setText("You can also dash");
					texts[0].setOpacity(0f);
					texts[0].setAnimateOpacity(1f);
					texts[0].setPosition(WIDTH/2-texts[0].getWidth()/2,HEIGHT*.35f+texts[0].getHeight()/2);
					stage.addActor(texts[1]);
					texts[1].setText("through enemies");
					texts[1].setOpacity(0f);
					texts[1].setAnimateOpacity(1f);
					texts[1].setPosition(WIDTH/2-texts[1].getWidth()/2,HEIGHT*.29f+texts[0].getHeight()/2);
					Enemy enemy = new NormalEnemy(0,0,GameScreen.entMan.world);
					enemy.randomize();
					enemy.setPos(GameScreen.entMan.player.getPos().x,MyGame.HEIGHT+enemy.getRadius());
					enemy.getBody().setLinearVelocity(0,0);
					GameScreen.entMan.enemies.add(enemy);
					step = 2;
					timer = 3f;
					pause = true;
				}else if(step == 2 && timer <= 0){
					GameScreen.entMan.enemies.get(0).getBody().setLinearVelocity(0,-GameScreen.entMan.enemies.get(0).getRadius()*4);
					step = 3;
					timer = 2.3f;
					pause = false;
				}else if(step == 3 && timer <= 0){
					pause = true;
					stage.addActor(texts[2]);
					texts[2].setFontSize(fontSize);
					texts[2].setText("Try swiping up!");
					texts[2].setOpacity(0f);
					texts[2].setAnimateOpacity(1f);
					texts[2].setPosition(WIDTH/2-texts[2].getWidth()/2,HEIGHT*.85f+texts[2].getHeight()/2);
					step = 4;
				}else if(step == 4 && !pause){
					texts[0].setAnimateOpacity(0f);
					texts[1].setAnimateOpacity(0f);
					texts[2].setAnimateOpacity(0f);
					stage.addActor(texts[4]);
					texts[4].setFontSize(largeFontSize);
					texts[4].setText("Bravo!");
					texts[4].setOpacity(0f);
					texts[4].setAnimateOpacity(1f);
					texts[4].setPosition(WIDTH/2-texts[4].getWidth()/2,HEIGHT*.6f+texts[4].getHeight()/2);
					step = 5;
					timer = 3.5f;
				}else if(step == 5 && timer <= 0){
					stage.addActor(background);
					background.setOpacity(0f);
					background.setAnimateOpacity(1f);
					step = 6;
				}else if(step == 6 && background.getOpacity()>=1f){
					stage.clear();
					step = 1;
					stage.addActor(background);
					background.setAnimateOpacity(0f);
					scene = 4;
					game.partMan.reset();
					game.entMan.reset();
				}
				break;
			case 4:
				if(step == 1 && background.getOpacity()<=0f){
					stage.clear();

				}
				break;
			case 5:
				break;
			default:
				GameScreen.tutorial = false;
		}

		if(pause)
			delta = 0;
		return delta;
	}

	public void render(SpriteBatch sb){
		stage.draw();
	}

	public void tap(float x, float y){
		if(scene == 2 && step == 5 && pause){
			game.entMan.tap(game.entMan.player.getPos().x+100,game.entMan.player.getPos().y);
			pause = false;
		}
	}

	public void fling(float velx, float vely){
		if(scene == 3 && step == 4){
			game.entMan.player.dashTo(0,100);
			pause = false;
		}
	}
}
