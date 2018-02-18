import java.awt.Canvas;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.image.BufferStrategy;
import java.util.ArrayList;
import java.util.Arrays;
import javax.swing.JFrame;

public class Game implements Runnable {

	int ticks = 0;
	int[] temp=new int[5];
	public int width, height,fontSize=30,mode=1;
	public String title;
	String[] info= {"","","",""};
	
	private boolean running = false;
	private Thread thread;
	
	private BufferStrategy bs;
	private Graphics g;
	
	private JFrame frame;
	private Canvas canvas;
	
	private KeyManager keyManager;
	private MouseManager mouseManager;
	
	public int[] hand=new int[5];
	private World2 world=new World2();
	

	int rox=(width/10)+(width/100),roy=(height/100);//The upper left hand corner of the "game screen";
	int rgssx=(width-(2*(width/10)+2*(width/100))),rgssy=height-(height/10);//Size of the game screen;
	
	public Game(String title, int width, int height){
		this.width = width;
		this.height = height;
		this.title = title;
		keyManager = new KeyManager();
		mouseManager = new MouseManager();
	}
	
	public Game(){}

	private void init(){
		frame = new JFrame(title);
		frame.setSize(width, height);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setResizable(true);
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);
		frame.addMouseListener(mouseManager);
		frame.addMouseMotionListener(mouseManager);
		canvas = new Canvas();
		canvas.setPreferredSize(new Dimension(width, height));
		canvas.setMaximumSize(new Dimension(width, height));
		canvas.setMinimumSize(new Dimension(width, height));
		canvas.setFocusable(false);
		canvas.addMouseListener(mouseManager);
		canvas.addMouseMotionListener(mouseManager);
		frame.add(canvas);
		frame.pack();
		frame.addKeyListener(keyManager);
		Assets.init();
		world.init();
	}
	
	private void tick(){
		info[1]="Player One "+world.playersInfo[1][0]+" Player Two "+world.playersInfo[1][1]+" It is player "+(world.playerTurn+1)+" turn";
		if(temp[0]!=0){temp[0]++;}if(temp[0]>10){temp[0]=0;}
		width=frame.getWidth();
		height=frame.getHeight();
		keyManager.tick();
		tickMouse();
		if(keyManager.space&temp[0]==0){
			System.out.println("Player "+(world.playerTurn+1)+" Turn Ended");
			world.nextTurn();
			temp[0]++;
		}
		if(temp[0]==0){
			for(int x=0;x<4;x++){
				if(keyManager.numbers[x+1]){
					if(world.playersInfo[1][world.playerTurn]>=world.cost[x]){
						if(world.playerTurn==0){world.deckP1.add(x+1);}else{world.deckP2.add(x+1);}
						world.playersInfo[1][world.playerTurn]-=world.cost[x];
						temp[0]++;
					}
				}
			}
		}
	}
	private void tickMouse(){
		boolean end=false;
		int mx=mouseManager.getMouseX();
		int my=mouseManager.getMouseY();
		if(mouseManager.isLeftPressed()){
			for(int x=0;x<5;x++){
				if(mx>0&mx<(width/10)&my>(height/5)*x&my<(height/5)*(x+1)){
					world.playersInfo[0][0]=x;
					end=true;}
				if(mx>width-(width/10)&mx<width&my>(height/5)*x&my<(height/5)*(x+1)){
					world.playersInfo[0][1]=x;
					end=true;}
			}
			if(!end){
				for(int x=0;x<world.lineCount&!end;x++){
					if(mx>rox+(x*(rgssx/world.lineCount))&mx<rox+((x+1)*(rgssx/world.lineCount))){
						for(int y=0;y<world.lineCount&!end;y++){
							if(my>roy+(y*(rgssy/world.lineCount))&my<roy+((y+1)*(rgssy/world.lineCount))){
								if(world.playersInfo[0][0]>-1 | world.playersInfo[0][1]>-1){
									world.setTile(x, y);
								}
								end=true;
							}
						}
					}
				}
			}
		}
	}
	private void render(){
		bs = canvas.getBufferStrategy();
		if(bs == null){
			canvas.createBufferStrategy(3);
			return;
		}
		g = bs.getDrawGraphics();
		//Clear Screen
		g.clearRect(0, 0, width, height);
		//Draw Here!
		rox=(width/10)+(width/100);roy=(height/100);//The upper left hand corner of the "game screen";
		rgssx=(width-(2*(width/10)+2*(width/100)));rgssy=height-(height/10);//Size of the game screen;
		
		g.drawImage(Assets.background,0,0,width,height,null);
		if(mode==1){
			//Send variables to the world class
			ArrayList <Integer> render;render=new ArrayList<Integer>();
			int[] temp={rox,roy,rgssx,rgssy};
			for(int x=0;x<temp.length;x++){render.add(temp[x]);}
			//Players hands
			for(int x=0;x<5;x++){g.drawRect(0,(height/world.hands[0].length)*x,width/10,height/5);}
			for(int x=0;x<5;x++){g.drawRect(width-(width/10),(height/world.hands[1].length)*x,width/10,height/5);}
			for(int x=0;x<world.hands[0].length;x++){g.drawImage(Assets.cards[world.hands[0][x]],0,(height/5)*x,width/10,height/5,null);}
			for(int x=0;x<world.hands[1].length;x++){g.drawImage(Assets.cards[world.hands[1][x]],width-(width/10),(height/5)*x,width/10,height/5,null);}
			g.setColor(Color.RED);
			if(world.playersInfo[0][0]>=0){g.drawRect(0,(height/5)*world.playersInfo[0][0],width/10,height/5);}
			if(world.playersInfo[0][1]>=0){g.drawRect(width-(width/10),(height/5)*world.playersInfo[0][1],width/10,height/5);}
			//Game Screen
			g.setColor(Color.WHITE);
			g.drawRect(rox,roy,rgssx,rgssy);
			for(int x=0;x<world.lineCount;x++){
				g.drawLine(rox+(x*(rgssx/world.lineCount)), roy,rox+(x*(rgssx/world.lineCount)),roy+rgssy);
				g.drawLine(rox,roy+(x*(rgssy/world.lineCount)),rox+rgssx,roy+(x*(rgssy/world.lineCount)));
			}
			//Tiles
			for(int x=0;x<world.lineCount;x++){
				for(int y=0;y<world.lineCount;y++){
					for(int z=0;z<2;z++){
						g.drawImage(Assets.tiles[world.grid[x][y][z]],rox+(x*(rgssx/world.lineCount)),roy+(y*(rgssy/world.lineCount)),(rox+(1*(rgssx/world.lineCount)))/2-(width/64),roy+(rgssy/world.lineCount)-(height/100),null);
					}
				}
			}
			//------
		}
		g.setColor(Color.black);
		g.setFont(new Font("TimesRoman",Font.PLAIN,fontSize));
		g.setColor(Color.GREEN);
		for(int x=0;x<info.length;x++){
			g.drawString(info[x],rox,fontSize*(x+1));
		}
		//End Drawing!
		bs.show();
		g.dispose();
	}
	
	public void run(){
		init();
		int fps = 60;
		double timePerTick = 1000000000 / fps;
		double delta = 0;
		long now;
		long lastTime = System.nanoTime();
		long timer = 0;
		
		while(running){
			now = System.nanoTime();
			delta += (now - lastTime) / timePerTick;
			timer += now - lastTime;
			lastTime = now;
			
			if(delta >= 1){
				tick();
				render();
				ticks++;
				delta--;
			}
			
			if(timer >= 1000000000){
				info[0]=""+ticks;
				//System.out.println("Ticks and Frames: " + ticks);
				ticks = 0;
				timer = 0;
			}
		}
		
		stop();
		
	}
	
	public KeyManager getKeyManager(){
		return keyManager;
	}
	public synchronized void start(){
		if(running)
			return;
		running = true;
		thread = new Thread(this);
		thread.start();
	}
	public synchronized void stop(){
		if(!running)
			return;
		running = false;
		try {
			thread.join();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}	
}