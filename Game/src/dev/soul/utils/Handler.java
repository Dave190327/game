package dev.soul.utils;

import dev.soul.game.Game;

public class Handler{
	private Game game;
	
	public int getWidth(){return game.getWidth();}
	public int getHeight(){return game.getHeight();}
	public Game getGame(){return game;}
	
}