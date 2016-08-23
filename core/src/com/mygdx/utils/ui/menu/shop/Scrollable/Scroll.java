package com.mygdx.utils.ui.menu.shop.Scrollable;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.mygdx.utils.actors.ActorAnimator;
import com.mygdx.utils.actors.BoxButton;
import com.mygdx.utils.managers.AnimationManager;

import java.util.ArrayList;

/**
 * Created by Christopher Thai on 7/19/2016 at 5:15 PM.
 */
public class Scroll {


    Stage stage;

    Actor act;
    ArrayList<ScrollButton> buttons;

    float center;
    float buffer;

    int maxRows;
    float buttonWidth,buttonHeight,buttonGap;
    int currentRow,currentColumn;

    boolean prevTouch;
    int prevDeltaX;
    float vel,decel,direction;

    int animationOrder;
    boolean animating;
    float currentAnimationTime,animationTimeGap;

    ActorAnimator select,deselect;

    public Scroll(Stage stage){
        this.stage = stage;

        setBounds();
        setActors();
    }

    public void setBounds(){
        currentRow = 1;
        currentColumn = 1;
        maxRows = 5;
        buttonWidth = Gdx.graphics.getWidth()*.15f;
        buttonHeight = buttonWidth;
        buttonGap = buttonWidth * .3f;
        center = Gdx.graphics.getHeight()*.5f;

        decel = 1f;

        animationTimeGap = .05f;
    }

    public void setActors(){
        buttons = new ArrayList<ScrollButton>();

        act = new Actor(){
            @Override
            public void act(float delta){
                super.act(delta);
                update(delta);
            }
        };
    }

    public void update(float delta){
        if(animating){
            currentAnimationTime += delta;
            if(currentAnimationTime>=animationTimeGap) {
                currentAnimationTime -= animationTimeGap;
                if(animationOrder<maxRows){
                    for(int i=animationOrder;i<animationOrder+(maxRows-1)*animationOrder;i+=maxRows-1)
                        popupButton(i-1);
                    animationOrder++;
                }else if(animationOrder<=buttons.size()){
                    for(int i=animationOrder;i<animationOrder+(maxRows-1)*maxRows;i+=maxRows-1)
                        popupButton(i-1);
                    animationOrder+=maxRows;
                }
            }
        }
        if(Gdx.input.isTouched()){
            if(Gdx.input.getDeltaX()!=0) {
                vel = 0;
                prevDeltaX = Gdx.input.getDeltaX();
                buffer += prevDeltaX;
                for (ScrollButton button : buttons)
                    button.getBoxButton().setBuffers(buffer, 0);
            }
            prevTouch = true;
        }else{
            if(prevTouch){
                if(Math.abs(prevDeltaX)>Gdx.app.getGraphics().getWidth()*.02f){
                    vel = prevDeltaX/2f;
                    if(prevDeltaX>0)
                        direction = 1;
                    else
                        direction = -1;
                }
            }else if(vel!=0){
                buffer += vel;
                for (ScrollButton button : buttons)
                    button.getBoxButton().setBuffers(buffer, 0);
                vel -= decel * direction;
                if(Math.abs(vel)<=decel)
                    vel = 0;
            }
            prevTouch = false;
        }
    }

    public void addButton(Sprite sprite){
        if(currentRow>maxRows){
            currentRow = 1;
            currentColumn++;
        }

        float y = center+(maxRows*buttonHeight+(maxRows-1)*buttonGap)/2f-buttonHeight-((currentRow-1)*(buttonHeight+buttonGap));
        float x = buttonGap+((currentColumn-1)*(buttonWidth+buttonGap));

        buttons.add(new ScrollButton(this,x,y,buttonWidth,buttonHeight,currentRow,currentColumn));
        currentRow++;
    }

    public void set(){
        stage.addActor(act);
        resetScreen();
        animationOrder = 1;
        currentAnimationTime = 0;
        animating = true;
    }

    private boolean popupButton(int loc){
        if(loc<buttons.size()) {
            BoxButton button = buttons.get(loc).getBoxButton();
            float x = button.getXWithoutBuffer();
            float y = button.getYWithoutBuffer();
            float width = button.getWidth();
            float height = button.getHeight();
            button.setAnimation(AnimationManager.getPopUp(x,y,width,height,.3f));
            button.setBounds(x+width/2f,y+height/2f,0,0);
            if(animationOrder>buttons.size()-maxRows)
                button.getAnimate().addCommand(new ActorAnimator.ActionCommand(){
                    @Override
                    public void command(ActorAnimator actorAnimator){
                        animating = false;
                    }
                });
            stage.addActor(button);
            return true;
        }
        return false;
    }

    public void clicked(ScrollButton button){

    }

    public void resetScreen(){
        buffer = 0;
        vel = 0;
        for(ScrollButton button:buttons)
            button.reset();
    }

    public void dispose(){
        for(ScrollButton button:buttons)
            button.dispose();
    }
}
