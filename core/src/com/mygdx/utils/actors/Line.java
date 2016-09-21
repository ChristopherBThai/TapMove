package com.mygdx.utils.actors;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;
import com.mygdx.game.MyGame;
import com.mygdx.managers.SpriteManager;
import com.mygdx.utils.MathUtility;

/**
 * Created by Mono on 9/20/2016.
 */

public class Line extends AnimatableActor{

	private Sprite line;
	private float angle,length,thickness,rawThickness;
	private boolean adjustValues, adjustThickness;

	public Line(float x, float y, float xDiff, float yDiff){
		super();
		super.setBounds(x,y,xDiff,yDiff);
		super.setColor(Color.WHITE);
		line = SpriteManager.getLine();
		adjustValues = true;
		setThickness(.1f);
	}

	@Override
	public void draw(Batch batch, float parentAlpha){
		adjustSize();
		adjustThickness();
		batch.setColor(this.getColor().r,this.getColor().g,this.getColor().b,opacity);
		batch.draw(line,x,y-thickness/2f,0,thickness/2f,length,thickness,1,1,angle);
		//Gdx.app.log("Tap","angle:"+angle+" | length:"+length+" | thickness:"+thickness+" | x:"+x+" | y:"+y);
		//Gdx.app.log("Tap","w:"+getWidth()+" | h:"+getHeight());
	}

	private void adjustSize(){
		if(!adjustValues)
			return;

		angle = MathUtility.vectorToAngle(this.getWidth(),this.getHeight());
		length = Vector2.dst(0,0,getWidth(),getHeight());

		adjustValues = false;
	}

	private void adjustThickness(){
		if(!adjustThickness)
			return;
		thickness = MyGame.camera.viewportWidth*rawThickness*.03f*.97f;
		adjustThickness = false;
	}

	public void setThickness(float lineThickness){
		rawThickness = lineThickness;
		adjustThickness = true;
	}

	@Override
	protected void sizeChanged(){
		adjustValues = true;
	}

}
