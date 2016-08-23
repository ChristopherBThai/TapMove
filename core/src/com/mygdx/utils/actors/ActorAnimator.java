package com.mygdx.utils.actors;

import com.badlogic.gdx.scenes.scene2d.Actor;

import java.util.ArrayList;

/**
 * Created by Mono on 6/17/2016.
 */
public class ActorAnimator {

    public AnimatableActor actor;
    public ArrayList<Action> actions;

    public ActorAnimator(){
        actions = new ArrayList<Action>();
    }

    public void update(float delta){
        if(actor!=null&&isAnimating()){
            if(actions.get(0).isAnimating())
                actions.get(0).update(delta);
            else
                actions.remove(0);
        }
    }

    public void animateTo(float x, float y, float width, float height, float speed){
        Action action = new Action();
        action.set(x,y,width,height,speed);
        this.addAction(action);
    }

    public void animateTo(float x, float y, float width, float height, float speed, float epsilon){
        Action action = new Action();
        action.set(x,y,width,height,speed, epsilon);
        this.addAction(action);
    }

    public void addCommand(ActionCommand command){
        Action action = new Action();
        action.setCommand(command);
        this.addAction(action);
    }

    public void addAction(Action action){
        this.actions.add(action);
    }

    public boolean isAnimating(){
        return actions.size()>0;
    }

    public void setActor(AnimatableActor actor){
        this.actor = actor;
    }

    public Action getAction(){
        Action action = new Action();
        return action;
    }

    public ActionCommand getActionCommand(){
        ActionCommand command = new ActionCommand();
        return command;
    }


    public class Action{

        ActionCommand command;

        float ax,ay,awidth,aheight;
        public float animationTime,aspeed,epsilon;

        public Action(){
            animationTime = 1;
            aspeed = -1;
            epsilon = .0005f;
        }

        public Action reset(){
            animationTime = 1;
            if(aspeed>0&&aspeed<1)
                animationTime = (int)(Math.log(epsilon)/Math.log(1-aspeed))+1;
            return this;
        }

        public void update(float delta){
            if(aspeed>0&&isAnimating()) {
                float x = actor.getXWithoutBuffer() + (ax - actor.getXWithoutBuffer()) * aspeed;
                float y = actor.getYWithoutBuffer() + (ay - actor.getYWithoutBuffer()) * aspeed;
                float width = actor.getWidth() + (awidth - actor.getWidth()) * aspeed;
                float height = actor.getHeight() + (aheight - actor.getHeight()) * aspeed;
                actor.setBounds(x, y, width, height);
            }else if(command!=null)
                command.command(ActorAnimator.this);
            animationTime--;
        }

        public void set(float x, float y, float width, float height, float speed){
            if(speed<=0)
                return;
            ax = x;
            ay = y;
            awidth = width;
            aheight = height;
            aspeed = speed;
            reset();
        }

        public void set(float x, float y, float width, float height, float speed, float epsilon){
            if(speed<=0)
                return;
            ax = x;
            ay = y;
            awidth = width;
            aheight = height;
            aspeed = speed;
            this.epsilon = epsilon;
            reset();
        }

        public void setCommand(ActionCommand command){
            this.command = command;
        }

        public boolean isAnimating(){
            return animationTime>0;
        }

        public Action cpy(Action action){
            action.command = this.command;
            if(aspeed>0)
                action.set(ax,ay,awidth,aheight,aspeed,epsilon);

            return action;
        }
    }

    public static class ActionCommand{
        public void command(ActorAnimator animator){}
    }

    public ActorAnimator cpy(){
        ActorAnimator animator = new ActorAnimator();
        for(Action action:actions) {
            animator.actions.add(action.cpy(animator.getAction()));
        }
        return animator;
    }
}
