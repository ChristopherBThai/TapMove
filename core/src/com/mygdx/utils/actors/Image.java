package com.mygdx.utils.actors;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.mygdx.managers.SpriteManager;

/**
 * Created by Christopher Thai on 7/27/2016 at 4:45 PM.
 */
public class Image extends AnimatableActor{

    private SpriteManager sprite;

    public Image(SpriteManager sprite,float x, float y, float width, float height){
        super();
        setBounds(x,y,width,height);
        this.sprite = sprite;
    }

    public Image(SpriteManager sprite){
        super();
        setBounds(0,0,0,0);
        this.sprite = sprite;
    }

    @Override
    public void update(float delta){
    }

    @Override
    public void render(Batch batch, float parentAlpha){
        batch.setColor(getColor().r,getColor().g,getColor().b,opacity);
        batch.draw(sprite.getSprite(),getX(),getY(),getWidth(),getHeight());
    }

    public void setSprite(SpriteManager sprite){
        this.sprite = sprite;
    }

    public SpriteManager getSprite(){
        return sprite;
    }
}
