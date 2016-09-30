package com.mygdx.ui.menu.shop.cosmetics;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.mygdx.managers.AnimationManager;
import com.mygdx.managers.ColorManager;
import com.mygdx.managers.SpriteManager;
import com.mygdx.ui.menu.shop.ShopItems.ShopList;
import com.mygdx.utils.actors.ActorAnimator;
import com.mygdx.utils.actors.BoxButton;
import com.mygdx.utils.actors.Button;
import com.mygdx.utils.actors.Line;
import com.mygdx.utils.actors.Text;

/**
 * Created by Mono on 9/20/2016.
 */

public class YesNoButton{
	private Stage stage;

	private Actor act;

	private ShopList shopList;

	private Text question,bought,name1,name2;
	private BoxButton button,yes,no;
	private Button next,prev;
	private Line split,yes1,yes2,no1,no2;

	private float boughtOpacityTimer;

	private float oX,oY,oLength;
	private boolean selected;
	private float symbolBorder = .2f;

	private ActorAnimator buttonExtend,buttonRetract,buttonA,yesExtend,yesRetract;

	public YesNoButton(ShopList shopList, float x, float y, Stage stage){
		this.shopList = shopList;
		setBounds(x,y);
		setActors();
		setActions();
	}

	public void update(float delta){
		if(boughtOpacityTimer>0){
			boughtOpacityTimer -= delta;
			if(boughtOpacityTimer<=0){
				bought.setAnimateOpacity(0f);
			}
		}
		shopList.getCurrent().act(delta);
	}

	public void render(Batch batch,float parentAlpha){
		shopList.getCurrent().draw(batch,parentAlpha);
	}

	public void doAnimation(){
		button.setOpacity(0f);
		button.setAnimateOpacity(1f);
		shopList.getCurrent().setOpacity(0f);
		shopList.getCurrent().setAnimateOpacity(1f);
		name1.setOpacity(0f);
		name1.setAnimateOpacity(1f);
		next.setOpacity(0f);
		next.setAnimateOpacity(1f);
		prev.setOpacity(0f);
		prev.setAnimateOpacity(1f);
	}

	public void setBounds(float x, float y){
		oLength = Gdx.graphics.getHeight()*.09f;
		oX = x;
		oY = y;
		shopList.setBounds(oX+oLength*symbolBorder,oY+oLength*symbolBorder,oLength*(1-symbolBorder*2),oLength*(1-symbolBorder*2));
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
				shopList.next();
				name1.setText(shopList.getCurrent().getName());
				name1.setPosition(oX+oLength/2-name1.getWidth()/2,oY+oLength*1.2f+name1.getHeight());
			}
		};
		next.setSprite(SpriteManager.getLine());

		prev = new Button(){
			@Override
			public void justTouched(){
				shopList.prev();
				name1.setText(shopList.getCurrent().getName());
				name1.setPosition(oX+oLength/2-name1.getWidth()/2,oY+oLength*1.2f+name1.getHeight());
			}
		};
		prev.setSprite(SpriteManager.getLine());

		yes = new BoxButton(oX+oLength/2,oY,oLength,oLength){
			@Override
			public void justTouched(){
				touched();
				bought();
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
		name1 = new Text(oLength, shopList.getCurrent().getName());
		name1.setFontSize(question.getFontSize());

		split = new Line();
		no1 = new Line();
		no2 = new Line();
		yes1 = new Line();
		yes2 = new Line();


		next.setBounds(oX+oLength+oLength*symbolBorder,oY+oLength*symbolBorder,oLength*.5f,oLength*(1-symbolBorder*2));
		prev.setBounds(oX-oLength*symbolBorder-oLength*.5f,oY+oLength*symbolBorder,oLength*.5f,oLength*(1-symbolBorder*2));
		prev.setRotation(180);
		question.setPosition(oX+oLength/2-question.getWidth()/2,oY+oLength*1.2f+question.getHeight());
		bought.setPosition(oX+oLength/2-bought.getWidth()/2,oY-oLength*.2f);
	}

	private void touched(){
		selected = !selected;

		if(selected){
			button.setAnimation(buttonExtend);
		}else
			button.setAnimation(buttonRetract);
	}

	private void bought(){
		bought.setAnimateOpacity(1f);
		boughtOpacityTimer = 2.3f;
		ColorManager.setPlayer(shopList.getCurrent().getColor());
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
				name1.setAnimateOpacity(0f);
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
				name1.setAnimateOpacity(1f);
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
		stage.addActor(name1);
		stage.addActor(next);
		stage.addActor(prev);
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
		name1.setText(shopList.getCurrent().getName());
		name1.setPosition(oX+oLength/2-name1.getWidth()/2,oY+oLength*1.2f+name1.getHeight());
		selected = false;
		yes.removeTouch();
		no.removeTouch();
	}

	public void dispose(){
		button.dispose();
		question.dispose();
		bought.dispose();
		yes.dispose();
		no.dispose();
		name1.dispose();
	}

}
