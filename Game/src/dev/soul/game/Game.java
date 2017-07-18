package dev.soul.game;

import java.awt.Graphics;
import java.awt.image.BufferStrategy;
import dev.soul.display.Display;

public class Game implements Runnable{

	private boolean running;
	private Thread thread;
	private int width,height;
	private String title;
	private Display display;
	private BufferStrategy bs;
	private Graphics g;
	
	public Game(String title, int width, int height){
		this.width=width;
		this.height=height;
		this.title=title;
	}
	public void init(){
		running=true;
		display=new Display(title,width,height);
	}
	public void run(){}
	public void tick(){}
	private void render(){
		bs = display.getCanvas().getBufferStrategy();
		if(bs == null){
			display.getCanvas().createBufferStrategy(3);
			return;
		}
		g = bs.getDrawGraphics();
		g.clearRect(0, 0, width, height);
		//if(State.getState() != null){State.getState().render(g);}
		bs.show();
		g.dispose();
	}
	public synchronized void start(){
		if(running)
			return;
		running = true;
		thread = new Thread(this);
		thread.start();
		init();
	}
	
	public synchronized void stop(){
		if(!running)
			return;
		running = false;
		try{thread.join();
		}catch (InterruptedException e){
			e.printStackTrace();
		}
	}
	public int getWidth(){return width;}
	public int getHeight(){return height;}
	
}
