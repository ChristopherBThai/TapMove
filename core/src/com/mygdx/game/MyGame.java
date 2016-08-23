package com.mygdx.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.mygdx.screen.GameScreen;
import com.mygdx.screen.MenuScreen;
import com.mygdx.utils.ads.AdHandler;
import com.mygdx.utils.camera.OrthoCamera;
import com.mygdx.utils.managers.ColorManager;
import com.mygdx.utils.managers.EntityManager;
import com.mygdx.utils.managers.ScreenManager;
import com.mygdx.utils.managers.SoundManager;

public class MyGame extends ApplicationAdapter {
	public static boolean DEBUG = false;
	
	static AdHandler handler;
	boolean toggle;
	
	public static int WIDTH,HEIGHT;
	
	public static OrthoCamera camera;
	
	public static SpriteBatch sb;
	public static ShapeRenderer sr;

	public static MenuScreen menuScreen;
	public static GameScreen gameScreen;
	
	public MyGame(AdHandler handler){
		this.handler = handler;
	}
	
	public MyGame(){
		this.handler = null;
	}
	
	@Override
	public void create () {
		new ColorManager();
		float ratio = Gdx.app.getGraphics().getHeight()/((float)Gdx.app.getGraphics().getWidth());
		WIDTH = 50;
		HEIGHT = (int) (WIDTH*ratio);
		
		camera = new OrthoCamera(WIDTH,HEIGHT);
		camera.zoom = ((float)WIDTH) / Gdx.app.getGraphics().getWidth();
		
		sb = new SpriteBatch();
		sr = new ShapeRenderer();
		sr.setAutoShapeType(true);

		menuScreen = new MenuScreen();
		gameScreen = new GameScreen();
		gameScreen.create();
		menuScreen.create();
		ScreenManager.setScreen(menuScreen, false, false);

		this.showAd();
	}

	@Override
	public void render () {
		this.update();
		
		Gdx.gl.glClearColor(ColorManager.BACKGROUND.r, ColorManager.BACKGROUND.g, ColorManager.BACKGROUND.b, ColorManager.BACKGROUND.a);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		sb.setProjectionMatrix(camera.combined);
		sr.setProjectionMatrix(camera.combined);

		ScreenManager.render(sr, sb);
	}
	
	public void update(){
		float delta = Gdx.graphics.getDeltaTime();
		ScreenManager.update(delta);
		camera.update();
	}
	
	@Override
	public void resize(int width, int height){
		float ratio = Gdx.app.getGraphics().getHeight()/((float)Gdx.app.getGraphics().getWidth());
		WIDTH = 50;
		HEIGHT = (int) (WIDTH*ratio);
		camera.resize(width, height);
		ScreenManager.resize(width,height);
	}
	
	@Override
	public void pause(){
		ScreenManager.pause();
	}
	
	@Override
	public void resume(){
		ScreenManager.resume();
	}
	
	@Override
	public void dispose(){
		ScreenManager.dispose();
		sb.dispose();
		sr.dispose();
		SoundManager.dispose();
		menuScreen.dispose();
		gameScreen.dispose();
	}
	
	public static void showAd(){
		if(handler!=null)
			handler.showAds(true);
	}
	
	public static void hideAd(){
		if(handler!=null)
			handler.showAds(false);
	}
}
