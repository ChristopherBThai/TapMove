package com.mygdx.ui.menu.shop.Scrollable;

import com.mygdx.utils.actors.ActorAnimator;
import com.mygdx.utils.actors.BoxButton;
import com.mygdx.managers.SpriteManager;

/**
 * Created by Christopher Thai on 7/26/2016 at 9:22 PM.
 */
public class ScrollButton {

    Scroll scroll;

    BoxButton button;

    float x,y,width,height; 
    int row,column;

    ActorAnimator select,deselect;
    boolean selected;

    public ScrollButton(Scroll scroll, float x,float y,float width,float height,int row, int column){
        this.scroll = scroll;
        button = new BoxButton(x,y,width,height){
            @Override
            public void act(float delta){
                super.act(delta);
                update(delta);
            }
            @Override
            public void justTouched(){
                selected();
            }
        };
        button.setThickness(.1f);
        button.setInside(SpriteManager.getExplosion());
        button.setInsideScale(.7f);

        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.row = row;
        this.column = column;

        select = new ActorAnimator();
        select.addCommand(new ActorAnimator.ActionCommand(){
            @Override
            public void command(ActorAnimator actorAnimator){
                button.setLockInside(true);
            }
        });
        select.animateTo(x,y,width*2.5f,height,.1f,.08f);
        select.animateTo(x,y-(height*.7f)/2f,width*2.5f,height*1.7f,.1f);
        deselect = new ActorAnimator();
        deselect.animateTo(x,y,width,height,.1f);
        deselect.addCommand(new ActorAnimator.ActionCommand(){
            @Override
            public void command(ActorAnimator actorAnimator){
                button.setLockInside(false);
            }
        });
        selected = false;
    }

    public void update(float delta){
    }

    public void selected(){
        if(selected){
            button.setAnimation(deselect);
        }else{
            button.setAnimation(select);
        }
        selected = !selected;
        scroll.clicked(this);
    }

    public void animateToReset(){
        button.moveTo(x,y,width,height,.1f);
    }

    public boolean isPos(int row,int column){
        return row==this.row&&column==this.column;
    }

    public boolean isRow(int row){
        return this.row == row;
    }

    public boolean isColumn(int column){
        return this.column == column;
    }

    public BoxButton getBoxButton(){
        return button;
    }

    public void reset(){
        button.setBuffers(0,0);
        button.setBounds(x,y,width,height);
    }

    public void dispose(){
        button.dispose();
    }

}
