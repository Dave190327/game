

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class KeyManager implements KeyListener {
	
	private boolean[] keys;
	public boolean[] numbers=new boolean[10];
	public boolean up,space;
	
	public KeyManager(){
		keys = new boolean[256];
	}
	
	public void tick(){
		numbers[0]=keys[KeyEvent.VK_0];
		numbers[1]=keys[KeyEvent.VK_1];
		numbers[2]=keys[KeyEvent.VK_2];
		numbers[3]=keys[KeyEvent.VK_3];
		space=keys[KeyEvent.VK_SPACE];
	}

	@Override
	public void keyPressed(KeyEvent e) {
		keys[e.getKeyCode()] = true;
	}

	@Override
	public void keyReleased(KeyEvent e) {
		keys[e.getKeyCode()] = false;
	}

	@Override
	public void keyTyped(KeyEvent e) {
		
	}

}
