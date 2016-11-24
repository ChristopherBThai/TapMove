package com.mygdx.ui.menu.shop.ShopItems;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.mygdx.managers.AnimationManager;
import com.mygdx.managers.ColorManager;
import com.mygdx.managers.SpriteManager;
import com.mygdx.ui.menu.shop.ShopItems.ShopList;
import com.mygdx.utils.actors.ActorAnimator;
import com.mygdx.utils.actors.AnimatableActor;
import com.mygdx.utils.actors.BoxButton;
import com.mygdx.utils.actors.Button;
import com.mygdx.utils.actors.Image;
import com.mygdx.utils.actors.Line;
import com.mygdx.utils.actors.Text;

import java.util.ArrayList;

/**
 * Created by Christopher Thai on 9/20/2016.
 */

public class YesNoButton{
	private Stage stage;

	private Actor act;

	private ShopList shopList;
	private ArrayList<AnimatableActor> removeList;
	float iX,iY,iLength;

	private Button next,prev;
	private Line next1,next2,next3,prev1,prev2,prev3;

	private Text question,bought,name;

	private BoxButton button,yes,no;
	private Line split,yes1,yes2,no1,no2;

	private Image moneySprite;
	private Text cost;
	private float gapBetweenSpriteAndCost;

	private float boughtOpacityTimer;

	private float oX,oY,oLength;
	private boolean selected;
	private float symbolBorder = .2f;

	private ActorAnimator buttonExtend,buttonRetract,buttonA,yesExtend,yesRetract;

	public YesNoButton(ShopList shopList, float x, float y, Stage stage){
		this.shopList = shopList;
		removeList = new ArrayList<AnimatableActor>();
		setBounds(x,y);
		setActors();
		setActions();
	}

	public void update(float delta){
		if(boughtOpacityTimer>0){
			boughtOpacityTimer -= delta;
			if(boughtOpacityTimer<=0){
				bought.setAnimateOpacity(0f);
				cost.setAnimateOpacity(1f);
				moneySprite.setAnimateOpacity(1f);
			}
		}

		if(next.getOpacity()>=0){
			next1.setOpacity(next.getOpacity());
			next2.setOpacity(next.getOpacity());
			next3.setOpacity(next.getOpacity());
			prev1.setOpacity(next.getOpacity());
			prev2.setOpacity(next.getOpacity());
			prev3.setOpacity(next.getOpacity());

		}

		for(int i=0;i<removeList.size();i++){
			removeList.get(i).act(delta);
			if(removeList.get(i).getAnimate()==null){
				removeList.remove(i);
				i--;
			}
		}

		if(button.isAnimating()){
			next1.setBuffers(oX-button.getX(),0);
			next2.setBuffers(oX-button.getX(),0);
			next3.setBuffers(oX-button.getX(),0);
			prev1.setBuffers(button.getX()-oX,0);
			prev2.setBuffers(button.getX()-oX,0);
			prev3.setBuffers(button.getX()-oX,0);
			Gdx.app.log("Tap:",""+next1.getBufferX());

		}

		shopList.getCurrent().act(delta);
	}

	public void render(Batch batch,float parentAlpha){
		shopList.getCurrent().draw(batch,parentAlpha);
		for(AnimatableActor actor:removeList){
			actor.draw(batch,parentAlpha);
		}
	}

	public void doAnimation(){
		button.animateToVisible();
		shopList.getCurrent().animateToVisible();
		name.animateToVisible();
		next.animateToVisible();
		prev.animateToVisible();
		moneySprite.animateToVisible();
		cost.animateToVisible();
	}

	public void setBounds(float x, float y){
		oLength = Gdx.graphics.getHeight()*.09f;
		oX = x;
		oY = y;
		iX = oX+oLength*symbolBorder;
		iY = oY+oLength*symbolBorder;
		iLength = oLength*(1-symbolBorder*2);
		shopList.setBounds(iX,iY,iLength,iLength);
		gapBetweenSpriteAndCost = oLength * .14f;
	}

	public void setActors(){
		act = new Actor(){
			@Override
			public void act(float delta){
				update(delta);
			}
			@Override
			public void draw(Batch batch, float parentAlpha){
				render(batch,parentAlpha);
			}
		};

		button = new BoxButton(oX+oLength/2,oY+oLength/2,0,0){
			@Override
			public void justTouched(){
				touched();
			}
		};
		button.setThickness(.1f);

		next = new Button(){
			@Override
			public void justTouched(){
				shopList.getCurrent().moveTo(iX-oLength,iY,.1f);
				shopList.getCurrent().setAnimateOpacity(0f);
				removeList.add(shopList.getCurrent());
				shopList.next();
				shopList.getCurrent().setPosition(iX+oLength,iY);
				shopList.getCurrent().setOpacity(0f);
				shopList.getCurrent().moveTo(iX,iY,.1f);
				shopList.getCurrent().setAnimateOpacity(1f);
				name.setText(shopList.getCurrent().getName());
				name.setPosition(oX+oLength/2-name.getWidth()/2,oY+oLength*1.2f+name.getHeight());
				setCostText();
			}
		};

		prev = new Button(){
			@Override
			public void justTouched(){
				shopList.getCurrent().moveTo(iX+oLength,iY,.1f);
				shopList.getCurrent().setAnimateOpacity(0f);
				removeList.add(shopList.getCurrent());
				shopList.prev();
				shopList.getCurrent().setPosition(iX-oLength,iY);
				shopList.getCurrent().setOpacity(0f);
				shopList.getCurrent().moveTo(iX,iY,.1f);
				shopList.getCurrent().setAnimateOpacity(1f);
				name.setText(shopList.getCurrent().getName());
				name.setPosition(oX+oLength/2-name.getWidth()/2,oY+oLength*1.2f+name.getHeight());
				setCostText();
			}
		};

		yes = new BoxButton(oX+oLength/2,oY,oLength,oLength){
			@Override
			public void justTouched(){
				touched();
				buyItem();
			}
		};
		yes.setVisible(false);
		yes.removeTouch();

		no = new BoxButton(oX-oLength/2,oY,oLength,oLength){
			@Override
			public void justTouched(){
				touched();
			}
		};
		no.setVisible(false);
		no.removeTouch();

		question = new Text(oLength,"Are you sure?");
		question.setFontSize(oLength*.2f);
		bought = new Text(oLength, "Purchased!");
		bought.setFontSize(question.getFontSize());
		name = new Text(oLength, shopList.getCurrent().getName());
		name.setFontSize(question.getFontSize());

		split = new Line();
		no1 = new Line();
		no2 = new Line();
		yes1 = new Line();
		yes2 = new Line();

		next.setBounds(oX+oLength+oLength*symbolBorder,oY+oLength*symbolBorder,oLength*.5f,oLength*(1-symbolBorder*2));
		prev.setBounds(oX-oLength*symbolBorder-oLength*.5f,oY+oLength*symbolBorder,oLength*.5f,oLength*(1-symbolBorder*2));

		next1 = new Line();
		next1.setBounds(next.getX(),next.getY(),0,-next.getHeight());
		next2 = new Line();
		next2.setBounds(next.getX(),next.getY(),next.getWidth(),next.getHeight()/2);
		next3 = new Line();
		next3.setBounds(next.getX(),next.getY()+next.getHeight(),next.getWidth(),-next.getHeight()/2);
		prev1 = new Line();
		prev1.setBounds(prev.getX()+prev.getWidth(),prev.getY(),0,-prev.getHeight());
		prev2 = new Line();
		prev2.setBounds(prev.getX()+prev.getWidth(),prev.getY(),-prev.getWidth(),prev.getHeight()/2);
		prev3 = new Line();
		prev3.setBounds(prev.getX()+prev.getWidth(),prev.getY()+prev.getHeight(),-prev.getWidth(),-prev.getHeight()/2);

		prev.setRotation(180);
		question.setPosition(oX+oLength/2-question.getWidth()/2,oY+oLength*1.2f+question.getHeight());
		bought.setPosition(oX+oLength/2-bought.getWidth()/2,oY-oLength*.2f);

		cost = new Text(name.getFontSize(),"0");
		moneySprite = new Image(SpriteManager.CIRCLE.getSprite(),0,0,cost.getHeight(),cost.getHeight());
	}

	private void touched(){
		if(shopList.getCurrent().isBought()){
			bought.setText("Equipped!");
			bought.setPosition(oX + oLength / 2 - bought.getWidth() / 2, oY - oLength * .2f);
			bought.setAnimateOpacity(1f);
			cost.setAnimateOpacity(0f);
			moneySprite.setAnimateOpacity(0f);
			boughtOpacityTimer = 2.3f;
			shopList.getCurrent().equipItem();
		}else if(!shopList.getCurrent().hasEnough()){
			bought.setText("Not Enough!!");
			bought.setPosition(oX + oLength / 2 - bought.getWidth() / 2, oY - oLength * .2f);
			bought.setAnimateOpacity(1f);
			cost.setAnimateOpacity(0f);
			moneySprite.setAnimateOpacity(0f);
			boughtOpacityTimer = 2.3f;
		}else{
			selected = !selected;
			if(selected){
				button.setAnimation(buttonExtend);
			}else
				button.setAnimation(buttonRetract);
		}
	}

	private void buyItem(){
		if(shopList.getCurrent().buyItem()){
			bought.setAnimateOpacity(1f);
			bought.setText("Purchased!");
			bought.setPosition(oX+oLength/2-bought.getWidth()/2,oY-oLength*.2f);
			cost.setAnimateOpacity(0f);
			moneySprite.setAnimateOpacity(0f);
			boughtOpacityTimer = 2.3f;
			shopList.getCurrent().equipItem();
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
				button.removeTouch();
				next.removeTouch();
				next.setAnimateOpacity(0f);
				prev.setAnimateOpacity(0f);
				prev.removeTouch();
				shopList.getCurrent().setAnimateOpacity(0f);
				name.setAnimateOpacity(0f);
			}
		});
		buttonExtend.animateTo(oX-oLength/2,oY,oLength*2,oLength,.1f);
		buttonExtend.addCommand(new ActorAnimator.ActionCommand(){
			@Override
			public void command(ActorAnimator animator){
				yes.addTouch();
				no.addTouch();
			}
		});

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
				yes.removeTouch();
				no.removeTouch();
				button.addTouch();
				next.addTouch();
				next.setOpacity(1f);
				prev.addTouch();
				prev.setOpacity(1f);
				shopList.getCurrent().setAnimateOpacity(1f);
				name.setAnimateOpacity(1f);
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
		stage.addActor(yes);
		stage.addActor(no);
		stage.addActor(bought);
		stage.addActor(name);
		stage.addActor(next);
		stage.addActor(prev);
		stage.addActor(next1);
		stage.addActor(next2);
		stage.addActor(next3);
		stage.addActor(prev1);
		stage.addActor(prev2);
		stage.addActor(prev3);
		stage.addActor(moneySprite);
		stage.addActor(cost);
	}

	public void resetScreen(){
		button.setBounds(oX,oY,oLength,oLength);
		button.setAnimation(null);
		button.addTouch();
		next.addTouch();
		prev.addTouch();
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
		question.setOpacity(0f);
		bought.setOpacity(0f);
		name.setText(shopList.getCurrent().getName());
		name.setPosition(oX+oLength/2-name.getWidth()/2,oY+oLength*1.2f+name.getHeight());
		setCostText();
		selected = false;
		yes.removeTouch();
		no.removeTouch();
		next1.setOpacity(0f);
		next2.setOpacity(0f);
		next3.setOpacity(0f);
		prev1.setOpacity(0f);
		prev2.setOpacity(0f);
		prev3.setOpacity(0f);
		removeList.clear();
	}

	public float getWidth(){
		return oLength*2;
	}

	public float getHeight(){
		return oLength;
	}

	public float getX(){
		return oX-oLength/2;
	}

	public float getY(){
		return oY;
	}

	public void dispose(){
		button.dispose();
		question.dispose();
		bought.dispose();
		yes.dispose();
		no.dispose();
		name.dispose();
		cost.dispose();
	}

	private void setCostText(){
		if(!shopList.getCurrent().isBought()){
			cost.setText(""+shopList.getCurrent().getPrice());
			float totalWidth = gapBetweenSpriteAndCost + cost.getWidth() + moneySprite.getWidth();
			moneySprite.setPosition(oX+oLength/2f-totalWidth/2f,oY-cost.getHeight()-oLength*.2f);
			cost.setPosition(moneySprite.getX()+moneySprite.getWidth()+gapBetweenSpriteAndCost,moneySprite.getY()+cost.getHeight());
			moneySprite.show();
		}else{
			cost.setText("owned");
			cost.setPosition(oX+oLength/2f-cost.getWidth()/2f,oY-cost.getHeight()-oLength*.2f+cost.getHeight());
			moneySprite.hide();
		}
	}

}
