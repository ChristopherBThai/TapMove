package com.mygdx.handler;

/**
 * Created by Christopher Thai on 1/20/2017.
 */

public interface GooglePlayHandler{
	public void signIn();
	public void signOut();
	public void achieve(String id);
	public void incAchieve(String id, int amount);
	public void displayAchievements();
}
