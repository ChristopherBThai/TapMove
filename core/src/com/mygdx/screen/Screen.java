package com.mygdx.screen;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

public abstract class Screen {
	
	public abstract void create();
	
	public abstract void update(float delta);
	
	public abstract void render(ShapeRenderer sr,SpriteBatch sb);
	
	public abstract void resize(int width, int height);
	
	public abstract void dispose();
	
	public abstract void pause();
	
	public abstract void resume();

	public abstract void resetScreen();
}
