package com.mygdx.ui.menu.extra.actors;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.mygdx.managers.ColorManager;
import com.mygdx.managers.SpriteManager;
import com.mygdx.utils.Save;
import com.mygdx.utils.actors.Image;
import com.mygdx.utils.actors.Text;

/**
 * Created by Christohper Thai on 11/24/2016.
 */

public class MoneyDisplay{

	private Text money;
	private Image moneyImage;

	private static String moneyText;

	private float moneyX,moneyY,moneyGap,moneyLength;

	public MoneyDisplay(){
		this.setActors();
		this.setBounds();
		resetScreen();
	}

	public void setBounds(){
		moneyX = Gdx.app.getGraphics().getWidth() * .014f;
		moneyY = Gdx.app.getGraphics().getHeight() * .938f;
		moneyGap = Gdx.app.getGraphics().getWidth() * .03f;
		setMoneyText("99999");
		moneyLength = money.getHeight();
	}

	public void setActors(){
		money = new Text(Gdx.app.getGraphics().getWidth()/15, ""+ Save.getMoney()){
			@Override
			public void update(float delta){
				if(!moneyText.equals(money.getText())){
					money.setText(moneyText);
				}
			}
		};
		money.setFontSize(Gdx.app.getGraphics().getWidth()/24f);
		money.setColor(ColorManager.NORMAL);
		moneyImage = new Image(SpriteManager.CIRCLE);
		moneyImage.setColor(ColorManager.NORMAL);
	}

	public void addToStage(Stage stage){
		stage.addActor(money);
		stage.addActor(moneyImage);
	}

	public void doAnimation(){
		money.animateToVisible();
		moneyImage.animateToVisible();
	}

	public void moveToReset(){
		money.moveTo(moneyX + moneyLength + moneyGap,moneyY+moneyLength,.1f);
		moneyImage.moveTo(moneyX,moneyY,moneyLength,moneyLength,.1f);
	}

	public void moveToHide(){
		money.moveTo(-money.getWidth(),moneyY+moneyLength,.1f);
		moneyImage.moveTo(-(moneyLength+moneyGap+money.getWidth()),moneyY,.1f);
	}

	public void resetScreen(){
		resetMoneyDisplayAmount();
		money.setPosition(moneyX + moneyLength + moneyGap,moneyY+moneyLength);
		moneyImage.setBounds(moneyX,moneyY,moneyLength,moneyLength);
	}

	public void dispose(){
		money.dispose();
	}

	public static void setMoneyText(String text){
		moneyText = text;
	}

	public static void resetMoneyDisplayAmount(){
		moneyText = ""+Save.getMoney();
	}
}
