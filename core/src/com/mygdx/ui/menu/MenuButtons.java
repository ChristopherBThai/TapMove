package com.mygdx.ui.menu;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.mygdx.game.MyGame;
import com.mygdx.screen.MenuScreen;
import com.mygdx.utils.Save;
import com.mygdx.utils.actors.ActorAnimator;
import com.mygdx.utils.actors.BoxButton;
import com.mygdx.utils.actors.Text;
import com.mygdx.managers.ColorManager;
import com.mygdx.managers.ScreenManager;
import com.mygdx.managers.SpriteManager;

public class MenuButtons {
    MenuScreen screen;
    Stage stage;

    BoxButton play,store,options;
    float width, height;
    float playX,storeX,optionsX;
    float playY,storeY,optionsY;
    ActorAnimator playClicked,storeClicked,optionsClicked;

    Text tap,move;
    Text money;
    Image moneyImage;

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
        store = new BoxButton(1,1,1,1){
            @Override
            public void justTouched(){
                store.setAnimation(storeClicked);
            }
        };
        options = new BoxButton(1,1,1,1){
            @Override
            public void justTouched(){
                options.setAnimation(optionsClicked);
            }
        };

        tap = new Text(Gdx.app.getGraphics().getWidth()/15f, "TAP");
        move = new Text(Gdx.app.getGraphics().getWidth()/15f, "MOVE");

        money = new Text(Gdx.app.getGraphics().getWidth()/15, ""+Save.getMoney());
        moneyImage = new Image(SpriteManager.getCircle());
        moneyImage.setColor(ColorManager.PLAYER);
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
                tap.moveTo(tap.getX(),Gdx.graphics.getHeight()+tap.getHeight(),.1f);
                move.moveTo(move.getX(),Gdx.graphics.getHeight()+move.getHeight(),.1f);
            }
        });
        playClicked.animateTo(-play.getThickness()*1.2f,-play.getThickness()*1.2f,Gdx.graphics.getWidth()+play.getThickness()*1.2f,Gdx.graphics.getHeight()+play.getThickness()*1.2f,.1f);
        playClicked.addCommand(new ActorAnimator.ActionCommand(){
            @Override
            public void command(ActorAnimator animator){
                ScreenManager.setScreen(MyGame.gameScreen,false,false);
                stage.clear();
            }
        });

        storeClicked = new ActorAnimator();
        storeClicked.addCommand(new ActorAnimator.ActionCommand(){
            @Override
            public void command(ActorAnimator animator){
                store.removeTouch();
                store.lockText(true);
                play.lockText(false);
                options.lockText(false);
                store.setAnimateInsideOpacity(0f);
                tap.moveTo(tap.getX(),Gdx.graphics.getHeight()+tap.getHeight(),.1f);
                move.moveTo(move.getX(),Gdx.graphics.getHeight()+move.getHeight(),.1f);
                play.moveTo(play.getX(),Gdx.graphics.getHeight(),width,height,.1f);
                options.moveTo(options.getX(),-options.getHeight(),width,height,.1f);
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
                tap.moveTo(Gdx.app.getGraphics().getWidth()/2f-tap.getWidth()/2f,Gdx.app.getGraphics().getHeight()*.84f-tap.getHeight()/2f,.1f);
                move.moveTo(Gdx.app.getGraphics().getWidth()/2f-move.getWidth()/2f,Gdx.app.getGraphics().getHeight()*.84f-tap.getHeight()/2f-move.getHeight(),.1f);
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
                tap.moveTo(tap.getX(),Gdx.graphics.getHeight()+tap.getHeight(),.1f);
                move.moveTo(move.getX(),Gdx.graphics.getHeight()+move.getHeight(),.1f);
                play.moveTo(play.getX(),Gdx.graphics.getHeight(),width,height,.1f);
                store.moveTo(options.getX(),Gdx.graphics.getHeight(),width,height,.1f);
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
                tap.moveTo(Gdx.app.getGraphics().getWidth()/2f-tap.getWidth()/2f,Gdx.app.getGraphics().getHeight()*.84f-tap.getHeight()/2f,.1f);
                move.moveTo(Gdx.app.getGraphics().getWidth()/2f-move.getWidth()/2f,Gdx.app.getGraphics().getHeight()*.84f-tap.getHeight()/2f-move.getHeight(),.1f);
            }
        });
        optionsClicked.animateTo(optionsX,optionsY,width,height,.1f);
    }

    public void set(){
        stage.addActor(tap);
        stage.addActor(move);
        stage.addActor(play);
        stage.addActor(store);
        stage.addActor(options);
        stage.addActor(money);
        stage.addActor(moneyImage);
    }

    public void resetScreen(){
        play.setBounds(playX,playY,width,height);
        play.setText("Play");
        play.setThickness(.1f);
        play.setOpacity(1f);
        play.lockText(false);
        play.addTouch();


        store.setBounds(storeX,storeY,width,height);
        store.setText("Store");
        store.setThickness(.1f);
        store.setOpacity(1f);
        store.lockText(false);
        store.addTouch();


        options.setBounds(optionsX,optionsY,width,height);
        options.setText("Options");
        options.setThickness(.1f);
        options.setOpacity(1f);
        options.lockText(false);
        options.addTouch();

        tap.setFontSize(Gdx.app.getGraphics().getWidth()/15f);
        tap.setPosition(Gdx.app.getGraphics().getWidth()/2f-tap.getWidth()/2f,Gdx.app.getGraphics().getHeight()*.84f-tap.getHeight()/2f);
        move.setFontSize(Gdx.app.getGraphics().getWidth()/15f);
        move.setPosition(Gdx.app.getGraphics().getWidth()/2f-move.getWidth()/2f,tap.getY()-move.getHeight());

        money.setText(""+Save.getMoney());
        money.setFontSize(Gdx.app.getGraphics().getWidth()/24f);
        money.setPosition(Gdx.app.getGraphics().getWidth()*.1f,Gdx.app.getGraphics().getHeight()*.95f+money.getHeight()/2f);
        moneyImage.setSize(Gdx.app.getGraphics().getHeight()*.025f,Gdx.app.getGraphics().getHeight()*.025f);
        moneyImage.setPosition(Gdx.app.getGraphics().getWidth()*.02f,Gdx.app.getGraphics().getHeight()*.95f-moneyImage.getHeight()/2f);

        this.set();
    }

    public void dispose(){
        play.dispose();
        store.dispose();
        options.dispose();
        tap.dispose();
        move.dispose();
    }
}
