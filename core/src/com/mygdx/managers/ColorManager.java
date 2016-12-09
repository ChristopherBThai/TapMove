package com.mygdx.managers;

import com.badlogic.gdx.Gdx;
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

	NORMAL(178,219,191,255),
	RED(255,102,102,255),
	ORANGE(255,153,51,255),
	YELLOW(255,255,102,255),
	GREEN(102,255,102,255),
	BLUE(102,178,255,255),
	PURPLE(178,102,255,255),
	PINK(255,102,255,255),

	GREEN_YELLOW(new Color[]{GREEN.color,YELLOW.color},5),
	RAINBOW(new Color[]{RED.color,ORANGE.color,YELLOW.color,GREEN.color,BLUE.color,PURPLE.color,PINK.color},3);

	private Color color;
	public float r,g,b,a;

	private boolean isGradient;

	private long timePerColor;
	private long currentTimePerColor;
	private long prevMs;

	private int currentColor;
	private float rDiff,gDiff,bDiff,aDiff;
	private Color[] colors;

	ColorManager(int r, int g, int b, int a){
		color = toColor(r,g,b,a);
		isGradient = false;
		syncColor();
		timePerColor = 1;
		prevMs = 0;
	}

	ColorManager(Color[] colors,float secondsPerColor){
		this.color = colors[0].cpy();
		this.colors = colors;
		isGradient = true;
		syncColor();
		timePerColor = (long)(secondsPerColor*1000);
		currentTimePerColor = 0;
		currentColor = 0;
		prevMs = 0;
		calculateRGBADiff();
	}

	public Color getColor(){
		if(isGradient){
			float delta = System.currentTimeMillis() - prevMs;

			if(delta > timePerColor){
				currentColor += delta % timePerColor;
				currentColor %= colors.length;
				calculateRGBADiff();
				color.set(colors[currentColor]);
				delta %= timePerColor;
			}else if(currentTimePerColor >= timePerColor){
				currentTimePerColor -= timePerColor;
				delta -= currentTimePerColor;
				currentColor = (currentColor+1)%colors.length;
				calculateRGBADiff();
				color.set(colors[currentColor]);
			}

			color.add(rDiff*delta,gDiff*delta,bDiff*delta,aDiff*delta);
			syncColor();

			currentTimePerColor += delta;
			prevMs = System.currentTimeMillis();
		}

		return color;
	}

	private void calculateRGBADiff(){
		int next = (currentColor + 1)%colors.length;
		rDiff = (colors[next].r - colors[currentColor].r)/timePerColor;
		gDiff = (colors[next].g - colors[currentColor].g)/timePerColor;
		bDiff = (colors[next].b - colors[currentColor].b)/timePerColor;
		aDiff = (colors[next].a - colors[currentColor].a)/timePerColor;
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

	public static void setPlayer(ColorManager color){
		if(!color.isGradient){
			ColorManager.PLAYER.setColor(color.color);
			ColorManager.PLAYER_LIGHT.setColor(PLAYER.color.r,PLAYER.color.g,PLAYER.color.b,.85f);
			ColorManager.PLAYER_DESIGN.setColor(PLAYER.color.r+.5f,PLAYER.color.g+.5f,PLAYER.color.b+.5f,PLAYER.color.a);
		}else{
			ColorManager.PLAYER.copyColor(color);
			ColorManager.PLAYER_LIGHT.copyColor(color);
			ColorManager.PLAYER_DESIGN.copyColor(color);
		}
	}

	public void copyColor(ColorManager color){
		if(color.isGradient){
			this.isGradient = true;
			this.timePerColor = color.timePerColor;
			this.currentTimePerColor = 0;
			this.prevMs = 0;
			this.currentColor = 0;
			this.colors = color.colors;
			this.color.set(color.colors[0]);
			this.syncColor();
		}else{
			this.setColor(color.color);
		}
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
