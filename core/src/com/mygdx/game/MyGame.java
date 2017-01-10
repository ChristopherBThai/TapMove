package com.mygdx.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.mygdx.managers.SpriteManager;
import com.mygdx.screen.GameScreen;
import com.mygdx.screen.MenuScreen;
import com.mygdx.ads.AdHandler;
import com.mygdx.utils.Save;
import com.mygdx.utils.camera.OrthoCamera;
import com.mygdx.managers.ColorManager;
import com.mygdx.managers.ScreenManager;
import com.mygdx.managers.SoundManager;

public class MyGame extends ApplicationAdapter {
	public static boolean DEBUG = false;
	
	private static AdHandler handler;
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

		Save.load();

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
		//Gdx.app.log("tap","FPS: "+Gdx.graphics.getFramesPerSecond());
	}
	float time;
	public void update(){
		float delta = Gdx.graphics.getDeltaTime();
		ScreenManager.update(delta);
		camera.update();

		//Gdx.app.log("Tap", SpriteManager.getActiveCount());
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
		Save.save();
	}
	
	@Override
	public void resume(){
		ScreenManager.resume();
		Save.load();
	}
	
	@Override
	public void dispose(){
		Save.save();
		//ScreenManager.dispose();
		//sb.dispose();
		//sr.dispose();
		//SoundManager.dispose();
		//menuScreen.dispose();
		//gameScreen.dispose();
	}
	
	public static void showAd(){
		if(handler!=null&&Save.adsEnabled())
			handler.showAds(true);
	}
	
	public static void hideAd(){
		if(handler!=null)
			handler.showAds(false);
	}
}
