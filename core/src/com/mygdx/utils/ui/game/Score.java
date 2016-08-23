package com.mygdx.utils.ui.game;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.mygdx.screen.GameScreen;
import com.mygdx.utils.Save;
import com.mygdx.utils.actors.Text;

public class Score {

    Stage stage;

    Text score;
    Text finalScore,highScore;
    boolean showScore;
    static float scoreCount;
    static int moneyCount;

    public Score(Stage stage){
        this.stage = stage;

        score = new Text(Gdx.app.getGraphics().getWidth()/25f,"Score: 0");
        score.setPosition(0f, score.getHeight());
        finalScore = new Text(score.getFontSize()*1.5f, "");
        highScore = new Text(finalScore.getFontSize()*.8f,"");

    }

    public void update(float delta){

        if(GameScreen.running){
            if(!GameScreen.pause){
                scoreCount+=delta*10;
                score.setText("Score: "+(int)scoreCount);
            }
        }else{
            if(!showScore){
                stage.clear();
                stage.addActor(finalScore);
                stage.addActor(highScore);
                Save.setHighScore(scoreCount);
                Save.addMoney(moneyCount);
                finalScore.setText("Score: "+(int)scoreCount);
                highScore.setText("Highscore: "+(int)Save.getHighScore());
                finalScore.setPosition(Gdx.graphics.getWidth()/2f-finalScore.getWidth()/2f, Gdx.graphics.getHeight()*.75f+finalScore.getHeight()/2f);
                highScore.setPosition(Gdx.graphics.getWidth()/2f-highScore.getWidth()/2f, finalScore.getY()-((highScore.getHeight())*1.1f)-finalScore.getHeight());
                showScore = true;
            }
        }
    }

    public void reset(){
        scoreCount = 0;
        moneyCount = 0;
        showScore = false;
        stage.addActor(score);
    }

    public static void addMoney(int amount){
        moneyCount += amount;
    }

    public static float getScore(){
        return scoreCount;
    }

    public static int getMoney(){ return moneyCount; }

}
