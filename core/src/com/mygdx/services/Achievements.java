package com.mygdx.services;

import com.mygdx.game.MyGame;
import com.mygdx.handler.GooglePlayHandler;


/**
 * Created by Christopher Thai on 1/21/2017.
 */

public enum Achievements{
	FIRST_GAME("CgkIuOXQ5dkCEAIQAQ",false),
	TEN_GAMES("CgkIuOXQ5dkCEAIQAg",true),
	FIFTY_GAMES("CgkIuOXQ5dkCEAIQCQ",true),
	FIRST_COSMETIC("CgkIuOXQ5dkCEAIQAw",false),
	ONE_OF_EACH_COSMETIC("CgkIuOXQ5dkCEAIQBA",false),
	FIRST_ABILITY("CgkIuOXQ5dkCEAIQBQ",false),
	ONE_K_SCORE("CgkIuOXQ5dkCEAIQBg",false),
	FIVE_K_SCORE("CgkIuOXQ5dkCEAIQBw",false),
	TEN_K_SCORE("CgkIuOXQ5dkCEAIQCA",false);

	private String id;
	private boolean inc;

	private static GooglePlayHandler handler = MyGame.getGooglePlayHandler();

	Achievements(String id,boolean inc){
		this.id = id;
		this.inc = inc;
	}

	public void achieve(){
		if(!inc){
			handler.achieve(id);
		}
	}

	public void increment(int amount){
		if(inc){
			handler.incAchieve(id,amount);
		}
	}

	public static void displayAchievements(){
		handler.displayAchievements();
	}
}
