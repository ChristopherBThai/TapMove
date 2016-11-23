package com.mygdx.utils;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.utils.Json;
import com.badlogic.gdx.utils.JsonValue;
import com.mygdx.game.MyGame;
import com.mygdx.particles.Particle;
import com.mygdx.screen.GameScreen;
import com.mygdx.ui.menu.MenuButtons;
import com.mygdx.ui.menu.shop.ShopItems.ItemListInterface;
import com.mygdx.ui.menu.shop.cosmetics.ColorList;
import com.mygdx.ui.menu.shop.cosmetics.DesignList;
import com.mygdx.ui.menu.shop.cosmetics.ParticleList;

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
            MenuButtons.setMoneyText(""+money);
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

    @SuppressWarnings({"unchecked"})
    public static void load(){
        if(!Gdx.files.local("save.json").exists()) {
            classicHighScore = 0;
            darkHighScore = 0;
            money = 0;
            ads = true;
        }else{
            Json save = new Json();
            ArrayList<Object> saveObjects = save.fromJson(ArrayList.class,Gdx.files.local("save.json"));

            if(saveObjects.size()>0&&saveObjects.get(0) instanceof SaveValues)
                ((SaveValues) saveObjects.get(0)).load();
            if(saveObjects.size()>1&&saveObjects.get(1) instanceof ShopValues)
                ((ShopValues) saveObjects.get(1)).load(ColorList.values());
            if(saveObjects.size()>2&&saveObjects.get(2) instanceof ShopValues)
                ((ShopValues) saveObjects.get(2)).load(DesignList.values());
            if(saveObjects.size()>3&&saveObjects.get(3) instanceof ShopValues)
                ((ShopValues) saveObjects.get(3)).load(ParticleList.values());

        }
    }

    public static void save(){
        Json save = new Json();

        ArrayList<Object> saveObjects = new ArrayList<Object>();
        saveObjects.add(new SaveValues(classicHighScore,darkHighScore,money,ads));
        saveObjects.add(new ShopValues(ColorList.values()));
        saveObjects.add(new ShopValues(DesignList.values()));
        saveObjects.add(new ShopValues(ParticleList.values()));

        String saveText = save.prettyPrint(save.toJson(saveObjects));
        Gdx.app.log("Tap",saveText);

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
        private float classic,dark;
        private int money;
        private boolean ads;

        public SaveValues(){
            classic = 0;
            dark = 0;
            money = 0;
            ads = true;
        }

        private SaveValues(float classic,float dark,int money, boolean ads){
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

        private void load(){
            Save.classicHighScore = classic;
            Save.darkHighScore = dark;
            Save.money = 9999999;//money;
            Save.ads = ads;
            MenuButtons.setMoneyText(""+money);
        }
    }

    static private class ShopValues implements Json.Serializable{
        private String equipped;
        private boolean bought[];

        public ShopValues(){
            equipped = "";
            bought = new boolean[1];
            bought[0] = true;
        }

        private ShopValues(ItemListInterface[] items){
            equipped = items[0].getCurrent();

            bought = new boolean[items.length];
            for(int i=0;i<items.length;i++){
                bought[i] = items[i].isBought();
            }
        }

        @Override
        public void write(Json json){
            json.writeValue("equipped",equipped);
            json.writeValue("bought",bought);
        }

        @Override
        public void read(Json json, JsonValue jsonData){
            if(jsonData.has("equipped"))
                equipped = json.readValue("equipped", String.class, jsonData);
            if(jsonData.has("bought"))
                bought = json.readValue("bought", boolean[].class, jsonData);
        }

        private void load(ItemListInterface[] items){
            items[0].setEquipped(equipped);
            for(int i=0;i<items.length;i++)
                if(i<bought.length)
                    items[i].setBought(bought[i]);
        }
    }
}
