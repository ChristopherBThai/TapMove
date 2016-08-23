package com.mygdx.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.input.GestureDetector;
import com.badlogic.gdx.input.GestureDetector.GestureListener;
import com.badlogic.gdx.math.Vector2;
import com.mygdx.game.MyGame;
import com.mygdx.utils.managers.EntityManager;
import com.mygdx.utils.managers.HudManager;
import com.mygdx.utils.managers.ScreenManager;

public class GameScreen extends Screen implements GestureListener{

	public static EntityManager entMan;
	public static HudManager hudMan;
	
	public static boolean running;
	public static boolean pause;

	private GestureDetector gesture;
	@Override
	public void create() {
		entMan = new EntityManager(this);
		hudMan = new HudManager(this);

		gesture = new GestureDetector(this);
		Gdx.input.setInputProcessor(gesture);
		
		running = true;
		pause = false;
		MyGame.hideAd();
	}

	@Override
	public void update(float delta) {
		entMan.update(delta);
		hudMan.update(delta);
	}

	@Override
	public void render(ShapeRenderer sr, SpriteBatch sb) {
		sb.begin();
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
		
		entMan.renderDEBUG();
	}
	
	public void restart(){
		MyGame.hideAd();
		entMan.reset();
		hudMan.reset();
		running = true;
		pause = false;
	}
	
	@Override
	public void resize(int width, int height) {
		entMan.resize(width, height);
	}

	@Override
	public void dispose() {
		entMan.dispose();
		hudMan.dispose();
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
		if(hudMan.ability.useAbility(x,Gdx.app.getGraphics().getHeight()-y)){
			entMan.useAbility();
		}else if(hudMan.tap(x,Gdx.app.getGraphics().getHeight()-y)){

		}else{
			Vector2 pos = MyGame.camera.unprojectCoordinates(x, y);
			if(running)
				entMan.tap(pos.x,pos.y);
		}

		return true;
	}

	@Override
	public boolean tap(float x, float y, int count, int button) {
		return false;
	}

	@Override
	public boolean longPress(float x, float y) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean fling(float velocityX, float velocityY, int button) {
		// TODO Auto-generated method stub
		return false;
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
