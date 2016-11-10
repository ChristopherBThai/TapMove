package com.mygdx.utils;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.utils.Json;
import com.badlogic.gdx.utils.JsonValue;
import com.mygdx.screen.GameScreen;

import java.util.ArrayList;

/**
 * Created by Christopher Thai on 7/18/2016.
 */
public class Save {
    private static float classicHighScore,darkHighScore;
    private static int money;
    private static boolean ads = true;

    public static boolean setHighScore(float score){
        if(GameScreen.currentMode==GameScreen.DARK){

            if(score>darkHighScore){
                darkHighScore = score;
                return true;
            }
            return false;
        }else{

            if(score>classicHighScore){
                classicHighScore = score;
                return true;
            }
            return false;
        }
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
        if(GameScreen.currentMode==GameScreen.DARK)
            return darkHighScore;
        return classicHighScore;
    }

    public static boolean adsEnabled(){
        return ads;
    }

    public static void load(){
        if(!Gdx.files.local("save.json").exists()) {
            classicHighScore = 0;
            darkHighScore = 0;
            money = 0;
            ads = true;
        }else{
            Json save = new Json();
            SaveValues saveValues = save.fromJson(SaveValues.class,Gdx.files.local("save.json"));
            classicHighScore = saveValues.classic;
            darkHighScore = saveValues.dark;
            money = 9999999;//saveValues.money;
            ads = saveValues.ads;
        }
    }

    public static void save(){
        Json save = new Json();

        String saveText = save.prettyPrint(new SaveValues(classicHighScore,darkHighScore,money,ads));
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
        public float classic,dark;
        public int money;
        public boolean ads;

        public SaveValues(){
            classic = 0;
            dark = 0;
            money = 0;
            ads = true;
        }

        public SaveValues(float classic,float dark,int money, boolean ads){
            this.classic = classic;
            this.dark = dark;
            this.money = money;
            this.ads = ads;
        }

        @Override
        public void write(Json json){
            json.writeValue("classic",classic);
            json.writeValue("dark",dark);
            json.writeValue("money",money);
            json.writeValue("ads",ads);
        }

        @Override
        public void read(Json json, JsonValue jsonData){
            if(jsonData.has("classic"))
                classic = json.readValue("classic", float.class, jsonData);
            if(jsonData.has("dark"))
                dark = json.readValue("dark", float.class, jsonData);
            if(jsonData.has("money"))
                money = json.readValue("money", int.class, jsonData);
            if(jsonData.has("ads"))
                ads = json.readValue("ads", boolean.class, jsonData);
        }
    }
}
