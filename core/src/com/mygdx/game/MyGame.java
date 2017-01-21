package com.mygdx.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.mygdx.handler.BillingHandler;
import com.mygdx.handler.GooglePlayHandler;
import com.mygdx.handler.ReturnBillingHandler;
import com.mygdx.screen.GameScreen;
import com.mygdx.screen.MenuScreen;
import com.mygdx.handler.AdHandler;
import com.mygdx.ui.menu.extra.actors.MoneyDisplay;
import com.mygdx.utils.Save;
import com.mygdx.utils.camera.OrthoCamera;
import com.mygdx.managers.ColorManager;
import com.mygdx.managers.ScreenManager;

public class MyGame extends ApplicationAdapter implements ReturnBillingHandler{
	public static boolean DEBUG = false;
	
	private static AdHandler adHandler;
	private static BillingHandler billingHandler;
	private static GooglePlayHandler googlePlayHandler;
	
	public static int WIDTH,HEIGHT;
	
	public static OrthoCamera camera;
	
	public static SpriteBatch sb;
	public static ShapeRenderer sr;

	public static MenuScreen menuScreen;
	public static GameScreen gameScreen;
	
	public MyGame(AdHandler handler1,BillingHandler handler2,GooglePlayHandler handler3){
		this.adHandler = handler1;
		this.billingHandler = handler2;
		this.googlePlayHandler = handler3;
	}
	
	public MyGame(){
		this.adHandler = null;
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

		Gdx.input.setCatchBackKey(true);
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
		Gdx.input.setCatchBackKey(false);
	}
	
	@Override
	public void resume(){
		ScreenManager.resume();
		Save.load();
		Gdx.input.setCatchBackKey(true);
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
		if(adHandler!=null&&Save.adsEnabled())
			adHandler.showAds(true);
	}
	
	public static void hideAd(){
		if(adHandler!=null)
			adHandler.showAds(false);
	}

	public static void buyAds(){
		if(billingHandler!=null)
			billingHandler.buyAds();
	}



	public static void buyMoney(){
		if(billingHandler!=null)
			billingHandler.buyMoney();
	}

	@Override
	public void returnBuyAds(boolean bought){
		Save.setAds(!bought);
		if(bought&&!Save.adsEnabled())
			this.hideAd();
	}

	@Override
	public void returnBuyMoney(){
		Save.addMoney(1000);
		MoneyDisplay.resetMoneyDisplayAmount();
	}

	public static void signIn(){
		googlePlayHandler.signIn();
	}

	public static void signOut(){
		googlePlayHandler.signOut();
	}
}
