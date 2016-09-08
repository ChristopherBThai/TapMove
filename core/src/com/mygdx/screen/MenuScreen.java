package com.mygdx.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.mygdx.managers.SoundManager;
import com.mygdx.ui.menu.MenuButtons;
import com.mygdx.ui.menu.background.Background;
import com.mygdx.ui.menu.option.OptionsButtons;
import com.mygdx.ui.menu.shop.abilities.AbilitiesMenu;
import com.mygdx.ui.menu.shop.cosmetics.CosmeticsMenu;
import com.mygdx.ui.menu.shop.other.OtherMenu;
import com.mygdx.ui.menu.shop.ShopButtons;

public class MenuScreen extends Screen {
	
	public Stage stage;

	public static MenuButtons menu;
	public static ShopButtons shop;
	public static OptionsButtons options;
	public static CosmeticsMenu cosmetics;
	public static AbilitiesMenu abilities;
	public static OtherMenu other;
	public static Background background;

	@Override
	public void create() {
		stage = new Stage(new ScreenViewport());
		Gdx.input.setInputProcessor(stage);
		SoundManager.loopMenuBgm(.7f,true);
		menu = new MenuButtons(this);
		shop = new ShopButtons(this);
		options = new OptionsButtons(this);
		cosmetics = new CosmeticsMenu(this);
		abilities = new AbilitiesMenu(this);
		other = new OtherMenu(this);
		background = new Background();
	}

	@Override
	public void update(float delta) {
		stage.act(delta);
		background.update(delta);
	}

	@Override
	public void render(ShapeRenderer sr, SpriteBatch sb) {
		sb.begin();
		background.render(sb);
		sb.end();
		background.renderLights();
		background.renderDEBUG();
		stage.draw();
	}

	@Override
	public void resize(int width, int height) {
		
		
	}

	@Override
	public void pause() {

	}

	@Override
	public void resume() {

	}

	@Override
	public void resetScreen(){
		Gdx.input.setInputProcessor(stage);
		stage.clear();
		menu.resetScreen();
		shop.resetScreen();
		options.resetScreen();
	}

	@Override
	public void dispose() {
		stage.dispose();
		menu.dispose();
		shop.dispose();
		options.dispose();
		background.dispose();
		cosmetics.dispose();
		abilities.dispose();
		other.dispose();
	}

}
