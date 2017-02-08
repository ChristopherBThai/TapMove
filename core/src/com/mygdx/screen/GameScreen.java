package com.mygdx.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.ParticleEffectPool;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.input.GestureDetector;
import com.badlogic.gdx.input.GestureDetector.GestureListener;
import com.badlogic.gdx.math.Vector2;
import com.mygdx.game.MyGame;
import com.mygdx.managers.EntityManager;
import com.mygdx.managers.HudManager;
import com.mygdx.managers.ParticleManager;
import com.mygdx.managers.SoundManager;
import com.mygdx.particles.ParticleTypes;
import com.mygdx.ui.menu.shop.abilities.AbilityList;
import com.mygdx.ui.tutorial.TutorialScreen;
import com.mygdx.utils.Save;

public class GameScreen extends Screen implements GestureListener{

	public static final int CLASSIC = 1, DARK = 2;
	public static int currentMode;

	public static EntityManager entMan;
	public static HudManager hudMan;
	public static ParticleManager partMan;
	
	public static boolean running;
	public static boolean pause;

	public static boolean tutorial;
	public static TutorialScreen tutorialScreen;

	private GestureDetector gesture;
	@Override
	public void create() {
		partMan = new ParticleManager(this);
		entMan = new EntityManager(this);
		hudMan = new HudManager(this);
		tutorialScreen = new TutorialScreen(this);

		gesture = new GestureDetector(this);
		Gdx.input.setInputProcessor(gesture);
		
		running = true;
		pause = false;
		MyGame.hideAd();
	}

	public void setMode(int mode){
		currentMode = mode;
	}

	@Override
	public void update(float delta) {
		float changedDelta = delta;
		if(tutorial)
			changedDelta = tutorialScreen.update(delta);
		changedDelta = entMan.update(changedDelta);
		hudMan.update(delta);
		partMan.update(changedDelta);
	}

	@Override
	public void render(ShapeRenderer sr, SpriteBatch sb) {
		sb.begin();
		partMan.render(sb);
		entMan.render(sb);
		sb.end();
		entMan.renderLights();
		
		Gdx.gl.glEnable(GL20.GL_BLEND);
		Gdx.gl.glBlendFunc(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);
		sr.begin(ShapeType.Filled);
		hudMan.render(sr);
		sr.end();
		Gdx.gl.glDisable(GL20.GL_BLEND);
		
		hudMan.render(sb);
		if(tutorial)
			tutorialScreen.render(sb);
		
		entMan.renderDEBUG();
	}
	
	public void restart(){
		SoundManager.BGM_GAME.play();
		SoundManager.BGM_MENU.stop();
		MyGame.hideAd();
		partMan.reset();
		entMan.reset();
		hudMan.reset();
		running = true;
		pause = false;
		tutorial = Save.isTutorial();
		if(tutorial){
			AbilityList.EXPLOSION.setEquipped(AbilityList.EXPLOSION.getName());
			tutorialScreen.reset();
		}
	}
	
	@Override
	public void resize(int width, int height) {
		entMan.resize(width, height);
	}

	@Override
	public void dispose() {
		entMan.dispose();
		hudMan.dispose();
		partMan.dispose();
	}

	@Override
	public void pause() {
		entMan.pause();
	}

	@Override
	public void resume() {
		entMan.resume();
	}

	@Override
	public boolean touchDown(float x, float y, int pointer, int button) {
		return false;
	}

	@Override
	public boolean tap(float x, float y, int count, int button) {
		/*if(hudMan.ability.useAbility(x,Gdx.app.getGraphics().getHeight()-y)){
			entMan.useAbility();
		}else*/ if(hudMan.tap(x,Gdx.app.getGraphics().getHeight()-y)){

		}else{
			Vector2 pos = MyGame.camera.unprojectCoordinates(x, y);
			if(running){
				if(!tutorial||(tutorial&&TutorialScreen.tap)){
					entMan.tap(pos.x, pos.y);
					ParticleEffectPool.PooledEffect effect = ParticleTypes.PLAYER_CLICK.particle.getEffect();
					effect.setPosition(pos.x,pos.y);
				}
				if(tutorial)
					tutorialScreen.tap(x,y);
			}
		}

		return true;
	}

	@Override
	public boolean longPress(float x, float y) {
		if(running&&!pause){
			if(!tutorial||(tutorial&&TutorialScreen.ability)){
				Vector2 pos = MyGame.camera.unprojectCoordinates(x, y);
				entMan.longPress(pos.x, pos.y);
			}
			if(tutorial)
				tutorialScreen.longpress(x,y);
		}
		return true;
	}

	@Override
	public boolean fling(float velocityX, float velocityY, int button) {
		if(!pause&&velocityX*velocityX+velocityY*velocityY>490000)
			if(!tutorial||(tutorial&&TutorialScreen.dash))
				entMan.fling(velocityX, velocityY);
			if(tutorial)
				tutorialScreen.fling(velocityX,velocityY);

		return true;
	}

	@Override
	public boolean pan(float x, float y, float deltaX, float deltaY) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean panStop(float x, float y, int pointer, int button) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean zoom(float initialDistance, float distance) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean pinch(Vector2 initialPointer1, Vector2 initialPointer2,
			Vector2 pointer1, Vector2 pointer2) {
		// TODO Auto-generated method stub
		return false;
	}

	public void end() {
		this.running = false;
		MyGame.showAd();
	}

	@Override
	public void resetScreen(){
		Gdx.input.setInputProcessor(gesture);
		this.restart();
	}

}
