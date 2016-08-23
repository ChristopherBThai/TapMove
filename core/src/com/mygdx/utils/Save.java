package com.mygdx.utils;

/**
 * Created by Mono on 7/18/2016.
 */
public class Save {
    private static float highScore;
    private static int money;

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
}
