package com.mygdx.managers;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;

public class SpriteManager {
	
	private static Sprite buttonBorder;
	private static Sprite play;
	private static Sprite circle,halfCircle;
	private static Sprite explosion;
	private static Sprite corner,line,pause;
	private static Sprite back;
	private static Sprite checkers,swirl;
	
	public static Sprite getButtonBorder(){
		if(buttonBorder==null)
			buttonBorder = new Sprite(new Texture(Gdx.files.internal("sprites/border.png")));
		return buttonBorder;
	}
	
	public static Sprite getPlay(){
		if(play==null)
			play = new Sprite(new Texture(Gdx.files.internal("sprites/play.png")));
		return play;
	}

	public static Sprite getCircle() {
		if(circle==null)
			circle = new Sprite(new Texture(Gdx.files.internal("sprites/circle.png")));
		return circle;
	}

	public static Sprite getExplosion() {
		if(explosion==null)
			explosion = new Sprite(new Texture(Gdx.files.internal("sprites/explosion.png")));
		return explosion;
	}

	public static Sprite getPause() {
		if(pause==null)
			pause = new Sprite(new Texture(Gdx.files.internal("sprites/pause.png")));
		return pause;
	}

	public static Sprite getLine() {
		if(line==null)
			line = new Sprite(new Texture(Gdx.files.internal("sprites/line.png")));
		return line;
	}

	public static Sprite getCorner() {
		if(corner==null)
			corner = new Sprite(new Texture(Gdx.files.internal("sprites/corner.png")));
		return corner;
	}

	public static Sprite getHalfCircle() {
		if(halfCircle==null)
			halfCircle = new Sprite(new Texture(Gdx.files.internal("sprites/half_circle.png")));
		return halfCircle;
	}

	public static Sprite getBack() {
		if(back==null)
			back = new Sprite(new Texture(Gdx.files.internal("sprites/back.png")));
		return back;
	}

	public static Sprite getCheckers() {
		if(checkers==null)
			checkers = new Sprite(new Texture(Gdx.files.internal("sprites/pattern1.png")));
		return checkers;
	}

	public static Sprite getSwirl() {
		if(swirl==null)
			swirl = new Sprite(new Texture(Gdx.files.internal("sprites/pattern2.png")));
		return swirl;
	}
}
