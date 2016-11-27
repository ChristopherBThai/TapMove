package com.mygdx.ui.menu;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.mygdx.screen.MenuScreen;
import com.mygdx.utils.MathUtility;
import com.mygdx.utils.Save;
import com.mygdx.utils.actors.ActorAnimator;
import com.mygdx.utils.actors.BoxButton;
import com.mygdx.utils.actors.Image;
import com.mygdx.utils.actors.Text;
import com.mygdx.managers.ColorManager;
import com.mygdx.managers.SpriteManager;

public class MenuButtons {
    private static MenuScreen screen;
    private static Stage stage;

    private static Image title;
    private static float titleWidth,titleHeight,titleX,titleY;

    private static BoxButton play,store,options;
    private static float width, height;
    private static float playX,storeX,optionsX;
    private static float playY,storeY,optionsY;
    private static ActorAnimator playClicked,storeClicked,optionsClicked;

    public static MoneyDisplay money;

    public MenuButtons(MenuScreen screen){
        this.screen = screen;
        this.stage = screen.stage;
        setBounds();
        setActors();
        setActions();
    }

    private void setActors() {
        play = new BoxButton(1,1,1,1){
            @Override
            public void justTouched(){
                play.setAnimation(playClicked);
            }
        };
        play.setText("Play");
        play.setThickness(.1f);


        store = new BoxButton(1,1,1,1){
            @Override
            public void justTouched(){
                store.setAnimation(storeClicked);
            }
        };
        store.setText("Store");
        store.setThickness(.1f);

        options = new BoxButton(1,1,1,1){
            @Override
            public void justTouched(){
                options.setAnimation(optionsClicked);
            }
        };
        options.setText("Options");
        options.setThickness(.1f);

        title = new Image(SpriteManager.TITLE.getSprite());
        title.setColor(ColorManager.NORMAL);
        title.setSize(titleWidth,titleHeight);
        title.setPosition(titleX,titleY);

        money = new MoneyDisplay();
    }

    private void setBounds(){
        width = Gdx.graphics.getWidth()*.47f;
        height = Gdx.graphics.getHeight()*.08f;
        playX = Gdx.graphics.getWidth()*.5f-width*.5f;
        playY = Gdx.graphics.getHeight()*.35f;
        storeX = playX;
        storeY = playY-height*1.3f;
        optionsX = storeX;
        optionsY = storeY-height*1.3f;
        titleWidth = MathUtility.getSpriteWidth(SpriteManager.TITLE.getSprite(),.18f);
        titleHeight = MathUtility.getSpriteHeight(.18f);
        titleX = Gdx.app.getGraphics().getWidth()/2f-titleWidth/2f;
        titleY = Gdx.app.getGraphics().getHeight()*.73f - titleHeight/2f;
    }

    private void setActions(){
        playClicked = new ActorAnimator();
        playClicked.addCommand(new ActorAnimator.ActionCommand(){
            @Override
            public void command(ActorAnimator animator){
                play.removeTouch();
                play.lockText(true);
                store.lockText(false);
                options.lockText(false);
                play.setAnimateInsideOpacity(0f);
                store.moveTo(store.getX(),-store.getHeight(),width,height,.1f);
                options.moveTo(options.getX(),-store.getHeight(),width,height,.1f);
                title.moveTo(title.getX(),Gdx.graphics.getHeight()+title.getHeight(),.1f);
                money.moveToHide();

            }
        });
        playClicked.animateTo(-play.getThickness()*1.2f,-play.getThickness()*1.2f,Gdx.graphics.getWidth()+play.getThickness()*1.2f,Gdx.graphics.getHeight()+play.getThickness()*1.2f,.1f);
        playClicked.addCommand(new ActorAnimator.ActionCommand(){
            @Override
            public void command(ActorAnimator animator){
                play.addTouch();
                stage.clear();
                screen.play.set(true);
                play.setAnimateInsideOpacity(1f);
                options.moveTo(optionsX,optionsY,width,height,.1f);
                store.moveTo(storeX,storeY,width,height,.1f);
                title.moveTo(titleX,titleY,.1f);
                money.moveToReset();
            }
        });
        playClicked.animateTo(playX,playY,width,height,.1f);

        storeClicked = new ActorAnimator();
        storeClicked.addCommand(new ActorAnimator.ActionCommand(){
            @Override
            public void command(ActorAnimator animator){
                store.removeTouch();
                store.lockText(true);
                play.lockText(false);
                options.lockText(false);
                store.setAnimateInsideOpacity(0f);
                title.moveTo(title.getX(),Gdx.graphics.getHeight()+title.getHeight(),.1f);
                play.moveTo(play.getX(),Gdx.graphics.getHeight(),width,height,.1f);
                options.moveTo(options.getX(),-options.getHeight(),width,height,.1f);
                money.moveToHide();
            }
        });
        storeClicked.animateTo(-store.getThickness()*1.2f,-store.getThickness()*1.2f,Gdx.graphics.getWidth()+store.getThickness()*1.2f,Gdx.graphics.getHeight()+store.getThickness()*1.2f,.1f);
        storeClicked.addCommand(new ActorAnimator.ActionCommand(){
            @Override
            public void command(ActorAnimator animator){
                store.addTouch();
                stage.clear();
                screen.shop.set(true);
                store.setAnimateInsideOpacity(1f);
                play.moveTo(playX,playY,width,height,.1f);
                options.moveTo(optionsX,optionsY,width,height,.1f);
                title.moveTo(titleX,titleY,.1f);
                money.moveToReset();
            }
        });
        storeClicked.animateTo(storeX,storeY,width,height,.1f);

        optionsClicked = new ActorAnimator();
        optionsClicked.addCommand(new ActorAnimator.ActionCommand(){
            @Override
            public void command(ActorAnimator animator){
                options.removeTouch();
                options.lockText(true);
                play.lockText(false);
                store.lockText(false);
                options.setAnimateInsideOpacity(0f);
                title.moveTo(title.getX(),Gdx.graphics.getHeight()+title.getHeight(),.1f);
                play.moveTo(play.getX(),Gdx.graphics.getHeight(),width,height,.1f);
                store.moveTo(options.getX(),Gdx.graphics.getHeight(),width,height,.1f);
                money.moveToHide();
            }
        });
        optionsClicked.animateTo(-store.getThickness()*1.2f,-store.getThickness()*1.2f,Gdx.graphics.getWidth()+store.getThickness()*1.2f,Gdx.graphics.getHeight()+store.getThickness()*1.2f,.1f);
        optionsClicked.addCommand(new ActorAnimator.ActionCommand(){
            @Override
            public void command(ActorAnimator animator){
                options.addTouch();
                stage.clear();
                screen.options.set(true);
                options.setAnimateInsideOpacity(1f);
                play.moveTo(playX,playY,width,height,.1f);
                store.moveTo(storeX,storeY,width,height,.1f);
                title.moveTo(titleX,titleY,.1f);
                money.moveToReset();
            }
        });
        optionsClicked.animateTo(optionsX,optionsY,width,height,.1f);
    }

    public void set(){
        stage.addActor(title);
        stage.addActor(play);
        stage.addActor(store);
        stage.addActor(options);
        money.addToStage(stage);
    }

    public void resetScreen(){
        play.setBounds(playX,playY,width,height);
        play.setOpacity(1f);
        play.lockText(false);
        play.addTouch();


        store.setBounds(storeX,storeY,width,height);
        store.setOpacity(1f);
        store.lockText(false);
        store.addTouch();


        options.setBounds(optionsX,optionsY,width,height);
        options.setOpacity(1f);
        options.lockText(false);
        options.addTouch();

        money.resetScreen();

        this.set();
    }

    public void dispose(){
        play.dispose();
        store.dispose();
        options.dispose();
        money.dispose();
    }
}
