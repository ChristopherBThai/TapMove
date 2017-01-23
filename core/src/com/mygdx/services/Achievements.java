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
	HUNDRED_GAMES("CgkIuOXQ5dkCEAIQDA",true),
	FIRST_COSMETIC("CgkIuOXQ5dkCEAIQAw",false),
	THREE_COSMETIC("CgkIuOXQ5dkCEAIQBA",false),
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
		handler.achieve(id);

	}

	public void increment(int amount){
		handler.incAchieve(id,amount);

	}

	public static void displayAchievements(){
		handler.displayAchievements();
	}

	public static void newScore(float score){
		if(score>=1000)
			Achievements.ONE_K_SCORE.achieve();
		if(score>=5000)
			Achievements.FIVE_K_SCORE.achieve();
		if(score>=10000)
			Achievements.TEN_K_SCORE.achieve();
		FIRST_GAME.achieve();
		TEN_GAMES.increment(1);
		FIFTY_GAMES.increment(1);
		HUNDRED_GAMES.increment(1);
	}

	public static void boughtCosmetic(int catagory){
		FIRST_COSMETIC.achieve();
		THREE_COSMETIC.increment(1);

	}

	public static void boughtAbility(){
		FIRST_ABILITY.achieve();
	}
}
