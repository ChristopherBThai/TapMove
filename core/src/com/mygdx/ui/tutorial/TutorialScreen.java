package com.mygdx.ui.tutorial;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.mygdx.entities.enemies.Enemy;
import com.mygdx.entities.enemies.LightBall;
import com.mygdx.entities.enemies.NormalEnemy;
import com.mygdx.game.MyGame;
import com.mygdx.managers.ColorManager;
import com.mygdx.managers.SpriteManager;
import com.mygdx.screen.GameScreen;
import com.mygdx.utils.Save;
import com.mygdx.utils.actors.BoxButton;
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

	private BoxButton skip;

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
		if(skip == null){
			skip = new BoxButton(WIDTH * .025f, HEIGHT * .975f - (WIDTH * .1f), WIDTH * .15f, WIDTH * .1f){
				@Override
				public void justTouched(){
					scene = 7;
					step = 1;
					skip.removeTouch();
					skip.setAnimateOpacity(0f);
					skip.setAnimateInsideOpacity(0f);
				}
			};
			skip.setText("Skip");
			skip.setTextScale(.75f);
		}
		skip.addTouch();
		skip.animateToVisible();
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
		stage.addActor(skip);
		background.setOpacity(0f);
		background.setAnimateOpacity(1f);
	}

	public float update(float delta){

		if(game.pause)
			return delta;

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
					stage.addActor(skip);
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
					stage.addActor(skip);
					step = 1;
					stage.addActor(background);
					background.setAnimateOpacity(0f);
					scene = 4;
					game.partMan.reset();
					game.entMan.reset();
				}
				break;
			case 3:
				if(step == 1 && background.getOpacity()<=0f){
					stage.clear();
					stage.addActor(skip);
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
					stage.addActor(skip);
					step = 1;
					stage.addActor(background);
					background.setAnimateOpacity(0f);
					scene = 5;
					game.partMan.reset();
					game.entMan.reset();
				}
				break;
			case 4:
				if(step == 1 && background.getOpacity()<=0f){
					stage.clear();
					stage.addActor(skip);
					stage.addActor(texts[0]);
					texts[0].setText("You also have a health bar");
					texts[0].setOpacity(0f);
					texts[0].setAnimateOpacity(1f);
					texts[0].setPosition(WIDTH/2-texts[0].getWidth()/2,game.hudMan.lifeBar.getY()+game.hudMan.lifeBar.getHeight()+texts[0].getHeight()*1.2f);
					step++;
					timer = 2.7f;
				}else if(step == 2 && timer <= 0){
					pause = false;
					stage.addActor(texts[1]);
					texts[1].setText("As time passes, your");
					texts[1].setOpacity(0f);
					texts[1].setAnimateOpacity(1f);
					texts[1].setPosition(WIDTH/2-texts[1].getWidth()/2,HEIGHT*.85f+texts[0].getHeight()/2);
					stage.addActor(texts[2]);
					texts[2].setText("health will deplete");
					texts[2].setOpacity(0f);
					texts[2].setAnimateOpacity(1f);
					texts[2].setPosition(WIDTH/2-texts[2].getWidth()/2,HEIGHT*.79f+texts[2].getHeight()/2);
					Enemy enemy = new LightBall(0,0,GameScreen.entMan.world);
					enemy.randomize();
					enemy.setPos(-1000,-1000);
					enemy.getBody().setLinearVelocity(0,0);
					GameScreen.entMan.enemies.add(enemy);
					step++;
					timer = 3f;
					health = true;
				}else if(step == 3 && timer <= 0){
					GameScreen.entMan.enemies.get(0).setPos(GameScreen.entMan.player.getPos().x,MyGame.HEIGHT+GameScreen.entMan.enemies.get(0).getRadius());
					GameScreen.entMan.enemies.get(0).getBody().setLinearVelocity(0,-GameScreen.entMan.enemies.get(0).getRadius()*3);
					texts[0].setAnimateOpacity(0f);
					texts[1].setAnimateOpacity(0f);
					texts[2].setAnimateOpacity(0f);
					timer = 2.5f;
					step++;
				}else if(step == 4 && timer <= 0){
					stage.addActor(texts[3]);
					texts[3].setText("Collect white orbs");
					texts[3].setOpacity(0f);
					texts[3].setAnimateOpacity(1f);
					texts[3].setPosition(WIDTH/2-texts[3].getWidth()/2,HEIGHT*.8f+texts[3].getHeight()/2);
					stage.addActor(texts[4]);
					texts[4].setFontSize(fontSize);
					texts[4].setText("to replenish your health");
					texts[4].setOpacity(0f);
					texts[4].setAnimateOpacity(1f);
					texts[4].setPosition(WIDTH/2-texts[4].getWidth()/2,HEIGHT*.74f+texts[4].getHeight()/2);
					step++;
					timer = 10f;
				}else if(step == 5 && (timer <= 0 || game.entMan.enemies.size() <= 0)){
					stage.addActor(texts[5]);
					texts[5].setText("Nice!");
					texts[5].setOpacity(0f);
					texts[5].setAnimateOpacity(1f);
					texts[5].setPosition(WIDTH/2-texts[5].getWidth()/2,HEIGHT*.4f+texts[5].getHeight()/2);
					step++;
					timer = 3.5f;
				}else if(step == 6 && timer <= 0){
					stage.addActor(background);
					background.setOpacity(0f);
					background.setAnimateOpacity(1f);
					step = 7;
				}else if(step == 7 && background.getOpacity()>=1f){
					stage.clear();
					stage.addActor(skip);
					step = 1;
					stage.addActor(background);
					background.setAnimateOpacity(0f);
					pause = false;
					health = false;
					scene = 3;
					game.partMan.reset();
					game.entMan.reset();
				}
				break;
			case 5:
				if(step == 1 && background.getOpacity()<=0f){
					stage.clear();
					stage.addActor(skip);
					stage.addActor(texts[0]);
					texts[0].setText("There are several abilities");
					texts[0].setOpacity(0f);
					texts[0].setAnimateOpacity(1f);
					texts[0].setPosition(WIDTH/2-texts[0].getWidth()/2,HEIGHT*.8f+texts[0].getHeight()/2);
					stage.addActor(texts[1]);
					texts[1].setText("you can buy!");
					texts[1].setOpacity(0f);
					texts[1].setAnimateOpacity(1f);
					texts[1].setPosition(WIDTH/2-texts[1].getWidth()/2,HEIGHT*.74f+texts[1].getHeight()/2);
					step++;
					timer = 2.5f;
				}else if(step == 2 && timer <= 0){
					stage.addActor(texts[2]);
					texts[2].setText("You currently have");
					texts[2].setOpacity(0f);
					texts[2].setAnimateOpacity(1f);
					texts[2].setPosition(WIDTH/2-texts[2].getWidth()/2,HEIGHT*.43f+texts[2].getHeight()/2);
					stage.addActor(texts[3]);
					texts[3].setFontSize(fontSize);
					texts[3].setText("the bomb ability");
					texts[3].setOpacity(0f);
					texts[3].setAnimateOpacity(1f);
					texts[3].setPosition(WIDTH/2-texts[3].getWidth()/2,HEIGHT*.37f+texts[3].getHeight()/2);
					Enemy enemy = new NormalEnemy(0,0,GameScreen.entMan.world);
					enemy.randomize();
					enemy.setPos(-1000,-1000);
					enemy.getBody().setLinearVelocity(0,0);
					GameScreen.entMan.enemies.add(enemy);
					enemy = new NormalEnemy(0,0,GameScreen.entMan.world);
					enemy.randomize();
					enemy.setPos(-1000,-1000);
					enemy.getBody().setLinearVelocity(0,0);
					GameScreen.entMan.enemies.add(enemy);
					enemy = new NormalEnemy(0,0,GameScreen.entMan.world);
					enemy.randomize();
					enemy.setPos(-1000,-1000);
					enemy.getBody().setLinearVelocity(0,0);
					GameScreen.entMan.enemies.add(enemy);
					enemy = new NormalEnemy(0,0,GameScreen.entMan.world);
					enemy.randomize();
					enemy.setPos(-1000,-1000);
					enemy.getBody().setLinearVelocity(0,0);
					GameScreen.entMan.enemies.add(enemy);
					step++;
					timer = 3f;
				}else if(step == 3 && timer <= 0){
					texts[0].setAnimateOpacity(0f);
					texts[1].setAnimateOpacity(0f);
					texts[2].setAnimateOpacity(0f);
					texts[3].setAnimateOpacity(0f);
					Enemy enemy = GameScreen.entMan.enemies.get(0);
					enemy.setPos(GameScreen.entMan.player.getPos().x,MyGame.HEIGHT+enemy.getRadius());
					enemy.getBody().setLinearVelocity(0,-enemy.getRadius()*4);
					enemy = GameScreen.entMan.enemies.get(1);
					enemy.setPos(GameScreen.entMan.player.getPos().x,-enemy.getRadius());
					enemy.getBody().setLinearVelocity(0,enemy.getRadius()*4);
					enemy = GameScreen.entMan.enemies.get(2);
					enemy.setPos(MyGame.WIDTH+enemy.getRadius(),GameScreen.entMan.player.getPos().y);
					enemy.getBody().setLinearVelocity(-enemy.getRadius()*2.3f,0);
					enemy = GameScreen.entMan.enemies.get(3);
					enemy.setPos(-enemy.getRadius(),GameScreen.entMan.player.getPos().y);
					enemy.getBody().setLinearVelocity(enemy.getRadius()*2.3f,0);
					step++;
					timer = 2f;
				}else if(step == 4 && timer <= 0){
					pause = true;
					texts[0].setText("Oh No!");
					texts[0].setOpacity(0f);
					texts[0].setAnimateOpacity(1f);
					texts[0].setPosition(WIDTH/2-texts[0].getWidth()/2,HEIGHT*.8f+texts[0].getHeight()/2);
					texts[1].setText("Tap and hold the");
					texts[1].setOpacity(0f);
					texts[1].setAnimateOpacity(1f);
					texts[1].setPosition(WIDTH/2-texts[1].getWidth()/2,HEIGHT*.43f+texts[1].getHeight()/2);
					texts[2].setText("screen to use your ability!");
					texts[2].setOpacity(0f);
					texts[2].setAnimateOpacity(1f);
					texts[2].setPosition(WIDTH/2-texts[2].getWidth()/2,HEIGHT*.37f+texts[2].getHeight()/2);
					step++;
				}else if(step == 5 && !pause){
					texts[0].setAnimateOpacity(0f);
					texts[1].setAnimateOpacity(0f);
					texts[2].setAnimateOpacity(0f);
					texts[3].setText("Wow!");
					texts[3].setOpacity(0f);
					texts[3].setAnimateOpacity(1f);
					texts[3].setPosition(WIDTH/2-texts[3].getWidth()/2,HEIGHT*.75f+texts[3].getHeight()/2);
					step++;
					timer = 3.8f;
				}else if(step == 6 && timer <= 0){
					stage.addActor(background);
					background.setOpacity(0f);
					background.setAnimateOpacity(1f);
					step = 7;
				}else if(step == 7 && background.getOpacity()>=1f){
					stage.clear();
					stage.addActor(skip);
					step = 1;
					stage.addActor(background);
					pause = false;
					health = false;
					scene = 6;
					game.partMan.reset();
					game.entMan.reset();
				}
				break;
			case 6:
				if(step == 1){
					stage.addActor(texts[0]);
					texts[0].setFontSize(largeFontSize);
					texts[0].setText("Now lets play");
					texts[0].setOpacity(0f);
					texts[0].setAnimateOpacity(1f);
					texts[0].setPosition(WIDTH/2-texts[0].getWidth()/2,HEIGHT*.54f+texts[0].getHeight()/2);
					stage.addActor(texts[1]);
					texts[1].setFontSize(largeFontSize);
					texts[1].setText("a real game!");
					texts[1].setOpacity(0f);
					texts[1].setAnimateOpacity(1f);
					texts[1].setPosition(WIDTH/2-texts[1].getWidth()/2,HEIGHT*.46f+texts[1].getHeight()/2);
					step++;
					timer = 3.4f;
				}else if(step == 2 && timer <= 0){
					texts[0].setAnimateOpacity(0f);
					texts[1].setAnimateOpacity(0f);
					background.setAnimateOpacity(0f);
					step++;
				}else if(step == 3 && background.getOpacity()<=0){
					stage.clear();
					stage.addActor(skip);
					GameScreen.tutorial = false;
					Save.setTutorial(false);
					game.restart();
				}
				break;
			case 7:
				if(step ==1){
					health = false;
					tap = false;
					dash = false;
					ability = false;
					score = false;
					pause = false;
					background.setAnimateOpacity(1f);
					if(!stage.getActors().contains(background,true))
						stage.addActor(background);
					step++;
				}else if(step == 2&&background.getOpacity()>=1f){
					stage.clear();
					game.partMan.reset();
					game.entMan.reset();
					stage.addActor(background);
					background.setAnimateOpacity(0f);
					step ++;
				}else if(step == 3 && background.getOpacity()<=0){
					stage.clear();
					GameScreen.tutorial = false;
					Save.setTutorial(false);
					game.restart();
				}
				break;
			default:
				GameScreen.tutorial = false;
				break;
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
		skip.tap(x,y);
	}

	public void fling(float velx, float vely){
		if(scene == 3 && step == 4 && pause){
			game.entMan.player.dashTo(0,100);
			pause = false;
		}
	}

	public void longpress(float x, float y){
		if(scene == 5 && step == 5 && pause){
			game.entMan.longPress(x,y);
			pause = false;
		}
	}
}
