package com.mygdx.utils;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.utils.Json;
import com.badlogic.gdx.utils.JsonValue;

import java.util.ArrayList;

/**
 * Created by Mono on 7/18/2016.
 */
public class Save {
    private static float highScore;
    private static int money;
    private static boolean ads = true;

    public static boolean setHighScore(float score){
        if(score>highScore){
            highScore = score;
            return true;
        }
        return false;
    }

    public static int addMoney(int amount){
        money += amount;
        return money;
    }

    public static boolean take(int amount){
        if(money>=amount){
            money -= amount;
            return true;
        }
        return false;
    }

    public static int getMoney(){
        return money;
    }

    public static float getHighScore(){
        return highScore;
    }

    public static boolean adsEnabled(){
        return ads;
    }

    public static void load(){
        if(!Gdx.files.local("save.json").exists()) {
            highScore = 0;
            money = 0;
            ads = true;
        }else{
            Json save = new Json();
            SaveValues saveValues = save.fromJson(SaveValues.class,Gdx.files.local("save.json"));
            highScore = saveValues.highScore;
            money = saveValues.money;
            ads = saveValues.ads;
        }
    }

    public static void save(){
        Json save = new Json();

        String saveText = save.prettyPrint(new SaveValues(highScore,money,ads));
        FileHandle file = null;
        try{
            file = Gdx.files.local("save.json");
        }catch (Exception e){
            Gdx.app.error("TapMove",e.getMessage());
        }finally {
            if(!Gdx.files.local("save.json").exists())
                file.writeString("",false);
        }
        file.writeString(saveText, false);
    }

    static private class SaveValues implements Json.Serializable{
        public float highScore;
        public int money;
        public boolean ads;

        public SaveValues(){
            highScore = 0;
            money = 0;
            ads = true;
        }

        public SaveValues(float highScore,int money, boolean ads){
            this.highScore = highScore;
            this.money = money;
            this.ads = ads;
        }

        @Override
        public void write(Json json){
            json.writeValue("highScore",highScore);
            json.writeValue("money",money);
            json.writeValue("ads",ads);
        }

        @Override
        public void read(Json json, JsonValue jsonData){
            if(jsonData.has("highScore"))
                highScore = json.readValue("highScore", float.class, jsonData);
            if(jsonData.has("money"))
                money = json.readValue("money", int.class, jsonData);
            if(jsonData.has("ads"))
                ads = json.readValue("ads", boolean.class, jsonData);
        }
    }
}
