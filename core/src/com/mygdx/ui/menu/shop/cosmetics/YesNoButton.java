package com.mygdx.ui.menu.shop.cosmetics;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.mygdx.managers.AnimationManager;
import com.mygdx.utils.actors.ActorAnimator;
import com.mygdx.utils.actors.BoxButton;
import com.mygdx.utils.actors.Line;
import com.mygdx.utils.actors.Text;

/**
 * Created by Mono on 9/20/2016.
 */

public class YesNoButton{
	private Stage stage;

	private Actor act;

	private Text question;
	private BoxButton button;//,yes,no;
	private Line split,yes1,yes2,no1,no2;

	private float oX,oY,oLength;
	private boolean selected;
	private float symbolBorder = .2f;

	private ActorAnimator buttonExtend,buttonRetract,buttonA,yesExtend,yesRetract;

	public YesNoButton(float x, float y, Stage stage){
		setBounds(x,y);
		setActors();
		setActions();
	}

	public void update(float delta){
	}

	public void doAnimation(){
		button.setAnimation(buttonA);
	}

	public void setBounds(float x, float y){
		oLength = Gdx.graphics.getHeight()*.09f;
		oX = x;
		oY = y;
	}

	public void setActors(){
		act = new Actor(){
			@Override
			public void act(float delta){
				update(delta);
			}
		};

		button = new BoxButton(oX+oLength/2,oY+oLength/2,0,0){
			@Override
			public void justTouched(){
				touched();
			}
		};
		button.setThickness(.1f);

		question = new Text(oLength,"Are you sure?");
		question.setFontSize(oLength*.2f);

		split = new Line();
		no1 = new Line();
		no2 = new Line();
		yes1 = new Line();
		yes2 = new Line();
	}

	private void touched(){
		selected = !selected;

		if(selected){
			button.setAnimation(buttonExtend);
		}else{
			button.setAnimation(buttonRetract);
		}
	}

	public void setActions(){
		buttonA = AnimationManager.getPopUp(oX,oY,oLength,oLength,.24f);

		buttonExtend = new ActorAnimator();
		buttonExtend.addCommand(new ActorAnimator.ActionCommand(){
			@Override
			public void command(ActorAnimator animator){
				split.setOpacity(1f);
				no1.setOpacity(1f);
				no2.setOpacity(1f);
				yes1.setOpacity(1f);
				yes2.setOpacity(1f);
				question.setAnimateOpacity(1f);
				split.moveTo(oX+oLength/2,oY,0,-oLength,.1f);
				no1.moveTo(oX+oLength/2-(oLength*symbolBorder),oY+(oLength*symbolBorder),-(1-symbolBorder*2)*oLength,(1-symbolBorder*2)*oLength,.1f);
				no2.moveTo(oX+oLength/2-(oLength*symbolBorder),oY+oLength-(oLength*symbolBorder),-(1-symbolBorder*2)*oLength,-(1-symbolBorder*2)*oLength,.1f);
				yes1.setAnimation(yesExtend);
				yes2.setAnimation(null);
			}
		});
		buttonExtend.animateTo(oX-oLength/2,oY,oLength*2,oLength,.1f);

		buttonRetract = new ActorAnimator();
		buttonRetract.addCommand(new ActorAnimator.ActionCommand(){
			@Override
			public void command(ActorAnimator animator){
				question.setAnimateOpacity(0f);
				split.moveTo(oX+oLength/2,oY+oLength/2,0,0,.1f);
				no1.moveTo(oX+oLength/2-(oLength*symbolBorder),oY+(oLength*symbolBorder),0,0,.1f);
				no2.moveTo(oX+oLength/2-(oLength*symbolBorder),oY+oLength-(oLength*symbolBorder),0,0,.1f);
				yes2.setAnimation(yesRetract);
				yes1.setAnimation(null);
			}
		});
		buttonRetract.animateTo(oX,oY,oLength,oLength,.1f);

		yesExtend = new ActorAnimator();
		yesExtend.animateTo(oX+oLength/2+(oLength*symbolBorder),oY+oLength/2,(oLength*(1-symbolBorder))/3,-((1-symbolBorder*2)*oLength)/2,.33f,.05f);
		yesExtend.addCommand(new ActorAnimator.ActionCommand(){
			@Override
			public void command(ActorAnimator animator){
				yes2.moveTo(oX+oLength/2+(oLength*symbolBorder)+(oLength*(1-symbolBorder))/3,oY+(oLength*symbolBorder),(2*oLength*(1-symbolBorder))/3-oLength*symbolBorder,((1-symbolBorder*2)*oLength),.22f);
			}
		});

		yesRetract = new ActorAnimator();
		yesRetract.animateTo(oX+oLength/2+(oLength*symbolBorder)+(oLength*(1-symbolBorder))/3,oY+(oLength*symbolBorder),0,0,.2f,.05f);
		yesRetract.addCommand(new ActorAnimator.ActionCommand(){
			@Override
			public void command(ActorAnimator animator){
				yes1.moveTo(oX+oLength/2+(oLength*symbolBorder),oY+oLength/2,0,0,.19f);
				split.setAnimateOpacity(0f,.2f);
				yes1.setAnimateOpacity(0f,.2f);
				yes2.setAnimateOpacity(0f,.2f);
				no1.setAnimateOpacity(0f,.2f);
				no2.setAnimateOpacity(0f,.2f);
			}
		});
	}

	public void addActor(Stage stage){
		this.stage = stage;
		stage.addActor(act);
		stage.addActor(button);
		stage.addActor(split);
		stage.addActor(no1);
		stage.addActor(no2);
		stage.addActor(yes1);
		stage.addActor(yes2);
		stage.addActor(question);
	}

	public void resetScreen(){
		button.setBounds(oX+oLength/2,oY+oLength/2,0,0);
		button.setAnimation(null);
		split.setBounds(oX+oLength/2,oY+oLength/2,0,0);
		split.setAnimation(null);
		no1.setBounds(oX+oLength/2-(oLength*symbolBorder),oY+(oLength*symbolBorder),0,0);
		no1.setAnimation(null);
		no2.setBounds(oX+oLength/2-(oLength*symbolBorder),oY+oLength-(oLength*symbolBorder),0,0);
		no2.setAnimation(null);
		yes1.setBounds(oX+oLength/2+(oLength*symbolBorder),oY+oLength/2,0,0);
		yes1.setAnimation(null);
		yes2.setBounds(oX+oLength/2+(oLength*symbolBorder)+(oLength*(1-symbolBorder))/3,oY+(oLength*symbolBorder),0,0);
		yes2.setAnimation(null);
		question.setPosition(oX+oLength/2-question.getWidth()/2,oY+oLength*1.2f+question.getHeight());
		question.setOpacity(0f);
	}

	public void dispose(){
		button.dispose();
		question.dispose();
		//yes.dispose();
		//no.dispose();
	}

}
