package com.mygdx.utils.actors;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;

/**
 * Created by Christopher Thai on 7/27/2016 at 4:45 PM.
 */
public class Image extends AnimatableActor{

    Sprite sprite;

    public Image(Sprite sprite,float x, float y, float width, float height){
        super();
        setBounds(x,y,width,height);
        this.sprite = sprite;
    }

    @Override
    public void act(float delta){
        super.act(delta);
    }

    @Override
    public void draw(Batch batch, float parentAlpha){
        batch.setColor(getColor().r,getColor().g,getColor().b,opacity);
        batch.draw(sprite,getX(),getY(),getWidth(),getHeight());
    }

    public void setSprite(Sprite sprite){
        this.sprite = sprite;
    }

    public Sprite getSprite(){
        return sprite;
    }
}
