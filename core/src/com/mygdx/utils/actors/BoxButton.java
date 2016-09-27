package com.mygdx.utils.actors;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.mygdx.game.MyGame;
import com.mygdx.managers.SpriteManager;

public class BoxButton extends AnimatableActor {

    private float down;
    private boolean touchable;
    private boolean visible;

    private Image inside;
    private float insideScale;

    private Text text;
    private float textScale;

    private boolean scaleText,scaleInside,adjustLines;
    private boolean lockText,lockInside;

    private Sprite line,corner;
    private float lineThickness;

    private float cornerSize;
    private float orginXCorner,orginYCorner;
    private float xCorner1,yCorner1;
    private float xCorner2,yCorner2;						//2	1
    private float xCorner3,yCorner3;						//3	4
    private float xCorner4,yCorner4;

    private float xLine1,yLine1,widthLine1,heightLine1;								//	1
    private float xLine2,yLine2,widthLine2,heightLine2;								//2	  4
    private float xLine3,yLine3,widthLine3,heightLine3;								//	3
    private float xLine4,yLine4,widthLine4,heightLine4;

    public BoxButton(float x, float y, float width, float height){
        super();
        super.setBounds(x, y, width, height);
        visible = true;
        addTouch();
        addListener(new ClickListener() {
            public void clicked (InputEvent event, float x, float y) {
                justTouched();
                down = .15f;
            }
        });
        line = SpriteManager.getLine();
        corner = SpriteManager.getCorner();
        lineThickness = .2f;
        insideScale = .8f;
        textScale = 1f;
    }

    public void draw(Batch batch, float parentAlpha){
        if(!lockInside)
            insideScale();
        adjustValues();
        if(!lockText)
            textScale();
        if(visible){
            drawButton(batch,parentAlpha);
        }
    }

    @Override
    public void act(float delta){
        super.act(delta);

        if(down>0)
            down-=delta;
        else if(down<0)
            down = 0;

        if(text!=null)
            text.act(delta);
        if(inside!=null)
            inside.act(delta);
    }

    public void drawButton(Batch batch, float parentAlpha){
        if(isPressed()) {
            batch.setColor(.7f, .7f, .7f, opacity);
            if(text!=null)
                text.setColor(.7f, .7f, .7f,opacity);
            if(inside!=null)
                inside.setColor(.7f,.7f,.7f,opacity);
        }else {
            batch.setColor(1f, 1f, 1f, opacity);
            if(text!=null)
                text.setColor(1f, 1f, 1f, opacity);
            if(inside!=null)
                inside.setColor(1f, 1f, 1f, opacity);
        }

        if(inside!=null)
            inside.draw(batch,parentAlpha);
        if(text!=null)
            text.draw(batch,parentAlpha);

        batch.draw(corner, bufferX+xCorner1, bufferY+yCorner1, orginXCorner, orginYCorner, cornerSize, cornerSize, 1f, 1f, 270);
        batch.draw(corner, bufferX+xCorner2, bufferY+yCorner2, cornerSize, cornerSize);
        batch.draw(corner, bufferX+xCorner3, bufferY+yCorner3, orginXCorner, orginYCorner, cornerSize, cornerSize, 1f, 1f, 90);
        batch.draw(corner, bufferX+xCorner4, bufferY+yCorner4, orginXCorner, orginYCorner, cornerSize, cornerSize, 1f, 1f, 180);

        batch.draw(line, bufferX+xLine1, bufferY+yLine1, widthLine1, heightLine1);
        batch.draw(line, bufferX+xLine2, bufferY+yLine2, 0f, 0f, widthLine2, heightLine2, 1f, 1f, 90);
        batch.draw(line, bufferX+xLine3, bufferY+yLine3, widthLine3, heightLine3);
        batch.draw(line, bufferX+xLine4, bufferY+yLine4, 0f, 0f, widthLine4, heightLine4, 1f, 1f, 90);
    }

    private void adjustValues(){
        if(!adjustLines)
            return;

        cornerSize = MyGame.camera.viewportWidth*lineThickness*.03f;
        float lineWidth = cornerSize*.97f;
        orginXCorner = cornerSize/2f;
        orginYCorner = cornerSize/2f;

        xCorner1 = this.getXWithoutBuffer()+this.getWidth()-cornerSize;
        yCorner1 = this.getYWithoutBuffer()+this.getHeight()-cornerSize;

        xCorner2 = this.getXWithoutBuffer();
        yCorner2 = this.getYWithoutBuffer()+this.getHeight()-cornerSize;

        xCorner3 = this.getXWithoutBuffer();
        yCorner3 = this.getYWithoutBuffer();

        xCorner4 = this.getXWithoutBuffer()+this.getWidth()-cornerSize;
        yCorner4 = this.getYWithoutBuffer();

        xLine1 = xCorner2+cornerSize;
        yLine1 = yCorner2+cornerSize-lineWidth;
        widthLine1 = xCorner1 - xLine1;
        heightLine1 = lineWidth;

        xLine2 = xCorner3+lineWidth;
        yLine2 = yCorner3+cornerSize;
        heightLine2 = lineWidth;
        widthLine2 = yCorner2 - yLine2;

        xLine3 = xCorner3+cornerSize;
        yLine3 = yCorner3;
        widthLine3 = xCorner4 - xLine3;
        heightLine3 = lineWidth;

        xLine4 = xCorner4+cornerSize;
        yLine4 = yCorner4+cornerSize;
        heightLine4 = lineWidth;
        widthLine4 = yCorner1 - yLine4;

        adjustLines = false;
    }

    public void insideScale() {
        if(!scaleInside||inside==null)
            return;

        float width = inside.getSprite().getWidth();
        float height = inside.getSprite().getHeight();

        float scaledWidth,scaledHeight;

        if(width>height){
            scaledWidth = this.getWidth()*insideScale;
            scaledHeight = (height*scaledWidth)/width;
        }else{
            scaledHeight = this.getHeight()*insideScale;
            scaledWidth = (width*scaledHeight)/height;
        }

        float scaledX = this.getXWithoutBuffer()+this.getWidth()/2f-scaledWidth/2f;
        float scaledY = this.getYWithoutBuffer()+this.getHeight()/2f-scaledHeight/2f;

        inside.setBounds(scaledX,scaledY,scaledWidth,scaledHeight);

        scaleInside = false;
    }

    public void textScale(){
        if(!scaleText||text==null)
            return;

        text.setFontSize(this.getHeight()*.45f*textScale);
        text.setPosition(this.getXWithoutBuffer()+this.getWidth()/2f-text.getWidth()/2f,this.getYWithoutBuffer()+this.getHeight()/2f+text.getHeight()/2f);

        scaleText = false;
    }

    public void addTouch(){
        touchable = true;
        setTouchable(Touchable.enabled);
    }

    public void removeTouch(){
        setTouchable(Touchable.disabled);
        touchable = false;
    }

    public void justTouched(){
    }

    public boolean tap(float x, float y){
        if(touchable&&
                x>=this.getX()&&
                x<=this.getX()+this.getWidth()&&
                y>=this.getY()&&
                y<=this.getY()+this.getHeight()) {
            justTouched();
            down = .15f;
            return true;
        }
        return false;
    }

    public boolean isPressed(){
        return down>0;
    }

    public void setInside(Sprite inner){
        if(inner!=null) {
            if(inside==null)
                inside = new Image(inner,1,1,1,1);
            else
                inside.setSprite(inner);
            scaleInside = true;
        }else
            inside = null;
    }

    public void setVisible(boolean visible){
        this.visible = visible;
    }

    public void setInsideScale(float scale){
        this.insideScale = scale;
        scaleInside = true;
    }

    public void setThickness(float thickness){
        this.lineThickness = thickness;
        adjustLines = true;
    }

    public void setText(String text){
        if(this.text==null)
            this.text = new Text(1,text);
        else
            this.text.setText(text);
        scaleText = true;
    }

    public void setTextScale(float scale){
        this.textScale = scale;
        scaleText = true;
    }

    @Override
    public void setOpacity(float opacity){
        super.setOpacity(opacity);
        if(text!=null)
            text.setOpacity(opacity);
        if(inside!=null)
            inside.setOpacity(opacity);
    }

    public void setAnimateInsideOpacity(float opacity){
        if(text!=null)
            text.setAnimateOpacity(opacity);
        if(text!=null)
            text.setAnimateOpacity(opacity);
    }

    @Override
    public void setBuffers(float x, float y){
        if(text!=null)
            text.setBuffers(x,y);
        if(inside!=null)
            inside.setBuffers(x,y);

        boolean tempScaleInside = scaleInside;
        boolean tempAdjustLines = adjustLines;
        boolean tempScaleText = scaleText;

        super.setBuffers(x,y);

        scaleInside = tempScaleInside;
        adjustLines = tempAdjustLines;
        scaleText = tempScaleText;
    }

    public void lockText(boolean lock){
        lockText = lock;
    }

    public void setLockInside(boolean lock){
        lockInside = lock;
    }

    public float getTextScale(){
        return this.textScale;
    }

    public float getThickness(){
        return lineThickness;
    }

    @Override
    protected void positionChanged(){
        scaleInside = true;
        adjustLines = true;
        scaleText = true;
    }

    @Override
    protected void sizeChanged () {
        scaleInside = true;
        adjustLines = true;
        scaleText = true;
    }

    public void dispose(){
        if(text!=null)
            text.dispose();
    }
}
