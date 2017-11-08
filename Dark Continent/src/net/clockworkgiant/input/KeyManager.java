package net.clockworkgiant.input;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class KeyManager implements KeyListener {
	
	private boolean[] keys, pressed, locked;
	public boolean up, down, left, right, c_UP, c_DOWN, c_LEFT, c_RIGHT, c_SNAP, escape, 
	p_BUTTON, c_LOCK, k_BUTTON, l_BUTTON, j_BUTTON;
	
	public KeyManager() {
		keys = new boolean[256];
		pressed = new boolean[keys.length];
		locked = new boolean[keys.length];
	}
	
	public void update() {
		for(int i = 0; i < keys.length - 1; i++) {
			if(locked[i] && !keys[i]) {
				locked[i] = false;
			}else if(pressed[i]) {
				locked[i] = true;
				pressed[i] = false;
			}
			if(!locked[i] && keys[i]) pressed[i] = true;
		}
		
		up = keys[KeyEvent.VK_W];
		down = keys[KeyEvent.VK_S];
		left = keys[KeyEvent.VK_A];
		right = keys[KeyEvent.VK_D];
		c_UP = keys[KeyEvent.VK_UP];
		c_DOWN = keys[KeyEvent.VK_DOWN];
		c_LEFT = keys[KeyEvent.VK_LEFT];
		c_RIGHT = keys[KeyEvent.VK_RIGHT];
		c_SNAP = keys[KeyEvent.VK_SPACE];
		escape = keys[KeyEvent.VK_ESCAPE];
		p_BUTTON = keys[KeyEvent.VK_P];
		c_LOCK = keys[KeyEvent.VK_C];
		k_BUTTON = keys[KeyEvent.VK_K];
		l_BUTTON = keys[KeyEvent.VK_L];
		j_BUTTON = keys[KeyEvent.VK_J];
	}

	@Override
	public void keyPressed(KeyEvent e) {
		if(e.getKeyCode() < 0 || e.getKeyCode() >= keys.length) return;
		keys[e.getKeyCode()] = true;
	}

	@Override
	public void keyReleased(KeyEvent e) {
		if(e.getKeyCode() < 0 || e.getKeyCode() >= keys.length) return;
		keys[e.getKeyCode()] = false;		
	}

	@Override
	public void keyTyped(KeyEvent e) {
	}
	
	public boolean keyJustPressed(int keyCode){
		if(keyCode < 0 || keyCode >= keys.length) return false;
		return pressed[keyCode];
	}
	
}
