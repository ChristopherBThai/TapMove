package com.mygdx.ui.menu;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.Array;
import com.mygdx.screen.MenuScreen;
import com.mygdx.ui.menu.extra.actors.BackButton;
import com.mygdx.ui.menu.extra.actors.MoneyDisplay;
import com.mygdx.utils.actors.ActorAnimator;
import com.mygdx.utils.actors.BoxButton;
import com.mygdx.utils.actors.Text;

/**
 * Created by Christopher Thai on 2/7/2017.
 */

public abstract class ButtonLayout{

	private static MenuScreen screen;
	protected static Stage stage;

	private static final float HEIGHT = Gdx.app.getGraphics().getHeight();
	private static final float WIDTH = Gdx.app.getGraphics().getWidth();

	private BackButton back;
	private MoneyDisplay money;
	private boolean showMoney;

	protected static float titleLoc = .79f;
	protected static float titleTextSize = .06f*HEIGHT;
	protected static float buttonLoc = .4f,buttonGapSize = .02f;
	protected static float buttonWidth=.47f,buttonHeight=.08f;
	protected static float buttonTextSize = .9f;

	protected Text title;
	protected float titleX,titleY;
	protected String titleName;
	protected Array<BoxButton> buttons;
	protected int numOfButtons;
	protected Array<Vector2> buttonPos;
	protected Vector2 buttonSize;
	protected float buttonGap;
	protected ActorAnimator animateClicked;
	protected boolean animate;

	public ButtonLayout(String title,boolean showMoney,int numOfButtons,boolean animate){
		this.numOfButtons = numOfButtons;
		this.titleName = title;
		this.showMoney = showMoney;
		this.animate = animate;
		setBounds();
		setActors();
		setActions();
	}

	/**
	 * Sets all the bounds
	 */
	protected void setBounds(){
		if(title!=null){
			titleY = titleLoc*HEIGHT+title.getHeight()/2f;
			titleX = WIDTH*.5f-title.getWidth()/2f;
		}

		if(buttonSize==null)
			buttonSize = new Vector2();
		buttonSize.set(buttonWidth*WIDTH,buttonHeight*HEIGHT);
		buttonGap = HEIGHT*buttonGapSize;
		if(buttonPos==null)
			buttonPos = new Array<Vector2>();

		float x = (float)(WIDTH*.5-buttonSize.x/2f);
		float y = (float)(HEIGHT*buttonLoc+(buttonSize.y*numOfButtons+buttonGap*(numOfButtons-1))/2);
		for(int i=0;i<numOfButtons;i++){
			buttonPos.add(new Vector2(x,y));
			y-=buttonSize.y+buttonGap;
		}
	}

	/**
	 * Sets the actors
	 */
	protected void setActors(){

		if(showMoney)
			money = new MoneyDisplay();

		if(titleName!=null&&!titleName.equals("")){
			title = new Text(titleTextSize,titleName);
			titleY = titleLoc*HEIGHT+title.getHeight()/2f;
			titleX = WIDTH*.5f-title.getWidth()/2f;
			title.setPosition(titleX,titleY);
		}

		buttons = new Array<BoxButton>();
		for(int i=0;i<numOfButtons;i++){
			buttons.add(new BoxButton(buttonPos.get(i).x,buttonPos.get(i).y,buttonSize.x,buttonSize.y){
				@Override
				public void justTouched(){
					int index = buttons.indexOf(this,true);
					buttonTouched(index);
					if(animate)
						this.setAnimation(animateClicked);
					else{
						buttonTouched(index);
						buttonActivated(index);
					}
				}
			});
			buttons.get(i).setTextScale(buttonTextSize);
		}

		back = new BackButton(){
			@Override
			public void back(){
				backClicked();
			}
		};
	}

	/**
	 * Sets all the Actor Animators
	 */
	protected void setActions(){
		if(animate){
			animateClicked  = new ActorAnimator();
			animateClicked.addCommand(new ActorAnimator.ActionCommand(){
				@Override
				public void command(ActorAnimator animator){
					int index = buttons.indexOf((BoxButton)animator.actor,true);

					buttons.get(index).removeTouch();
					buttons.get(index).lockText(true);
					buttons.get(index).setAnimateInsideOpacity(0f);

					if(title!=null)
						title.moveTo(title.getX(),Gdx.graphics.getHeight()+title.getHeight(),.1f);

					if(showMoney)
						money.moveToHide();

					back.moveToHide();

					for(int i=0;i<index;i++)
						moveUp(i);
					for(int i=index+1;i<numOfButtons;i++)
						moveDown(i);


				}
			});
			animateClicked.animateTo(-.15f,-.15f,WIDTH+.15f,HEIGHT+.15f,.1f);
			animateClicked.addCommand(new ActorAnimator.ActionCommand(){
				@Override
				public void command(ActorAnimator animator){
					int index = buttons.indexOf((BoxButton)animator.actor,true);
					buttons.get(index).addTouch();
					buttonActivated(index);

					buttons.get(index).setAnimateInsideOpacity(1f);

					if(title!=null)
					title.moveTo(titleX,titleY,.1f);

					if(showMoney)
						money.moveToReset();

					back.moveToReset();

					for(int i=0;i<numOfButtons;i++)
						moveBack(i);
				}
			});
		}
	}

	/**
	 * Adds all the actors to the stage
	 */
	public void set(boolean withReset){
		stage.clear();
		if(title!=null)
			stage.addActor(title);
		if(back!=null)
			stage.addActor(back.getActor());
		if(showMoney)
			money.addToStage(stage);
		for(BoxButton button:buttons)
			stage.addActor(button);
		if(withReset)
			resetScreen();
	}

	/**
	 * Resets the screen to its origional state
	 */
	public void resetScreen(){
		if(title!=null){
			title.setPosition(titleX,titleY);
			title.animateToVisible();
		}
		if(back!=null){
			back.resetScreen();
			back.doAnimation();
		}
		if(showMoney){
			money.resetScreen();
			money.doAnimation();
		}
		for(int i=0;i<numOfButtons;i++){
			buttons.get(i).setBounds(buttonPos.get(i).x,buttonPos.get(i).y,buttonSize.x,buttonSize.y);
			buttons.get(i).lockText(false);
			buttons.get(i).addTouch();
			buttons.get(i).animateToVisible();
		}

	}

	protected abstract void buttonTouched(int index);
	protected abstract void buttonActivated(int index);
	protected abstract void backClicked();

	private void moveUp(int index){
		BoxButton temp = buttons.get(index);
		temp.lockText(false);
		temp.moveTo(temp.getX(),Gdx.graphics.getHeight(),.1f);
	}

	private void moveDown(int index){
		BoxButton temp = buttons.get(index);
		temp.lockText(false);
		temp.moveTo(temp.getX(),-temp.getY(),.1f);
	}

	private void moveBack(int index){
		BoxButton temp = buttons.get(index);
		Vector2 tempLoc = buttonPos.get(index);
		temp.moveTo(tempLoc.x,tempLoc.y,buttonSize.x,buttonSize.y,.1f);
	}

	/**
	 * Dispose
	 */
	public void dispose(){
		if(title!=null)
			title.dispose();
		if(back!=null)
			back.dispose();
		money.dispose();
		for(BoxButton button:buttons)
			button.dispose();
	}

	public static void bind(MenuScreen screen){
		ButtonLayout.screen = screen;
		ButtonLayout.stage = MenuScreen.stage;
	}

}
