package com.mygdx.services;

import com.mygdx.game.MyGame;
import com.mygdx.handler.GooglePlayHandler;

/**
 * Created by Christopher Thai on 1/22/2017.
 */

public enum Leaderboards{
	CLASSIC("CgkIuOXQ5dkCEAIQCg"),
	DARK("CgkIuOXQ5dkCEAIQCw");

	String id;

	private static GooglePlayHandler handler = MyGame.getGooglePlayHandler();

	Leaderboards(String id){
		this.id = id;
	}

	public void submit(long score){
		handler.submitLeaderboard(id,score);
	}
}
