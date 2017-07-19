package dev.soul.entities.creatures;

import java.awt.Graphics;

import dev.soul.Handler;


public class Zombie extends Creature{

	public Zombie(Handler handler, float x, float y) {
		super(handler, x, y, 64, 64);
	}
	
	public void AI(){
		
	}
	
	@Override
	public void tick(){
	}

	@Override
	public void render(Graphics g){
	}

	@Override
	public void die(){
	}

}
