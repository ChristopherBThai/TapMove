package com.mygdx.managers;

import com.badlogic.gdx.graphics.Color;
import com.mygdx.screen.GameScreen;

public enum ColorManager {
	BACKGROUND(70, 70, 70, 255),

	NORMAL_ENEMY(30,30,30,255),
	BIG_ENEMY(20,20,20,255),
	FAST_ENEMY(40,40,40,255),

	PLAYER(178,219,191,255),
	PLAYER_LIGHT(178,219,191,204),
	PLAYER_DESIGN(178,219,191,255),

	NORMAL(178,219,191,255);

	private boolean isGradient;
	private long prevTime; //in ms
	private Color color;
	public float r,g,b,a;

	ColorManager(int r, int g, int b, int a){
		color = toColor(r,g,b,a);
		isGradient = false;
		prevTime = 0;
		syncColor();
	}

	public Color getColor(){
		return color;
	}

	public void setColor(Color color){
		this.color = color;
		syncColor();
	}

	public void setColor(int r, int g, int b, int a){
		this.color.set(r/255f,g/255f,b/255f,a/255f);
		syncColor();
	}

	public void setColor(float r, float g, float b, float a){
		this.color.set(r,g,b,a);
		syncColor();
	}

	public static void setPlayer(Color color){
		ColorManager.PLAYER.setColor(color);
		ColorManager.PLAYER_LIGHT.setColor(PLAYER.color.r,PLAYER.color.g,PLAYER.color.b,.85f);
		ColorManager.PLAYER_DESIGN.setColor(PLAYER.color.r+.5f,PLAYER.color.g+.5f,PLAYER.color.b+.5f,PLAYER.color.a);
		GameScreen.entMan.pLighting.setColor(PLAYER_LIGHT.color);
	}
	
	public static Color toColor(float r, float g, float b, float a){
		Color result = new Color(r/255f,g/255f,b/255f,a/255f);
		return result;
	}

	private void syncColor(){
		this.r = color.r;
		this.g = color.g;
		this.b = color.b;
		this.a = color.a;
	}
}
