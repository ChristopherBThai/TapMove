package com.mygdx.managers;

import com.badlogic.gdx.graphics.Color;
import com.mygdx.screen.GameScreen;
import com.mygdx.screen.MenuScreen;

public class ColorManager {
	public static Color BACKGROUND;
	public static Color PLAYER;
	public static Color NORMAL_ENEMY;
	public static Color BIG_ENEMY;
	public static Color FAST_ENEMY;
	public static Color PLAYER_LIGHT;
	public static Color NORMAL,BLUE,RED,GREEN;
	
	public ColorManager(){
		BACKGROUND = toColor(70, 70, 70, 255);
		NORMAL_ENEMY = toColor(30,30,30,255);
		BIG_ENEMY = toColor(20,20,20,255);
		FAST_ENEMY = toColor(40,40,40,255);

		NORMAL = toColor(178,219,191,255);
		BLUE = toColor(120,120,255,255);
		RED = toColor(255,120,120,255);
		GREEN = toColor(120,255,120,255);

		PLAYER = NORMAL;
		PLAYER_LIGHT = new Color(PLAYER.r,PLAYER.g,PLAYER.b,.8f);
	}

	public static void setPlayer(Color color){
		PLAYER = color;
		PLAYER_LIGHT = new Color(PLAYER.r,PLAYER.g,PLAYER.b,.8f);
		GameScreen.entMan.pLighting.setColor(PLAYER_LIGHT);
		//MenuScreen.background.lighting.setColor(PLAYER_LIGHT);
	}
	
	static Color toColor(float r, float g, float b, float a){
		Color result = new Color(r/255f,g/255f,b/255f,a/255f);
		return result;
	}
}
