package net.clockworkgiant.input;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;
import java.util.ArrayList;

import net.clockworkgiant.gamebase.Handler;
import net.clockworkgiant.tiles.Tile;
import net.clockworkgiant.ui.UIManager;

public class MouseManager implements MouseListener, MouseMotionListener, MouseWheelListener{
	
	private boolean leftB, rightB;
	private int mouseX, mouseY;
	private UIManager uiManager, battleUI;
	//private ArrayList<UIManager> battleUI = new ArrayList<UIManager>();
	private boolean[] keys, pressed, locked;
	private Handler handler;
	
	public MouseManager() {
		keys = new boolean[5];
		pressed = new boolean[keys.length];
		locked = new boolean[keys.length];
	}
	
	public void setHandler(Handler handler) {
		this.handler = handler;
	}
	
	public void setUIManager(UIManager uiManager) {
		this.uiManager = uiManager;
	}
	
	public void setBUIManager(UIManager battleUI) {
		//this.battleUI.add(battleUI);
		this.battleUI = battleUI;
	}

	public boolean isLeftPressed() {
		return leftB;
	}

	public boolean isRightPressed() {
		return rightB;
	}
	
	public int getMouseX() {
		return mouseX;
	}

	public int getMouseY() {
		return mouseY;
	}

	@Override
	public void mouseMoved(MouseEvent e) {
		mouseX = e.getX();
		mouseY = e.getY();
		
		if(uiManager != null) {
			uiManager.onMouseMove(e);
		}
		//for(UIManager battleUI: battleUI) {
			if(battleUI != null) {
				battleUI.onMouseMove(e);
			}
		//}
	}

	@Override
	public void mousePressed(MouseEvent e) {
		if(e.getButton() == MouseEvent.BUTTON1) {
			leftB = true;
		}
		if(e.getButton() == MouseEvent.BUTTON3) {
			rightB = true;
		}

		if(e.getButton() < 0 || e.getButton() >= keys.length) return;
		keys[e.getButton()] = true;
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		if(e.getButton() == MouseEvent.BUTTON1) {
			leftB = false;
		}
		if(e.getButton() == MouseEvent.BUTTON3) {
			rightB = false;
		}

		if(uiManager != null) {
			uiManager.onMouseRelease(e);
		}
		//for(UIManager battleUI: battleUI) {
			if(battleUI != null) {
				battleUI.onMouseRelease(e);
			}
		//}

		if(e.getButton() < 0 || e.getButton() >= keys.length) return;
		keys[e.getButton()] = false;
	}

	@Override
	public void mouseWheelMoved(MouseWheelEvent e) {
		mouseX = e.getX();
		mouseY = e.getY();
		
		if(((Handler.getSCALE() >= 0.5 && e.getWheelRotation() > 0) 
				|| (Handler.getSCALE() <= 4 && e.getWheelRotation() < 0)) 
				&& battleUI != null) {
			Handler.setSCALE(Handler.getSCALE() - 0.1 * e.getWheelRotation());
			Tile.Rescale();
			if (handler != null)
				handler.getGameCamera().Rescale();
		}
		
		if(uiManager != null) {
			uiManager.onMouseMove(e);
		}
		//for(UIManager battleUI: battleUI) {
			if(battleUI != null) {
				battleUI.onMouseMove(e);
			}
		//}
		
	}

	@Override
	public void mouseDragged(MouseEvent e) {
		
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}
	
	public boolean keyJustReleased(int keyCode){
		if(keyCode < 0 || keyCode >= keys.length) return false;
		
		for(int i = 0; i < keys.length - 1; i++) {
			if(locked[i] && keys[i]) {
				locked[i] = false;
			}else if(pressed[i]) {
				locked[i] = true;
				pressed[i] = false;
			}
			if(!locked[i] && !keys[i]) pressed[i] = true;
		}
	
		return pressed[keyCode];
	}
	
	public void removeBattleUI() {
		this.battleUI = null;
	}
}
