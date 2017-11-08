package net.clockworkgiant.ui;

import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

import net.clockworkgiant.gamebase.Handler;

public class UIManager {
	
	private Handler handler;
	private ArrayList<UIObject> objects;
	private boolean active = true;
	
	public UIManager(Handler handler) {
		this.handler = handler;
		objects = new ArrayList<UIObject>();
	}

	public void update() {
		for(UIObject o: objects)
			o.update();
	}
	
	public void render(Graphics g) {
		for(UIObject o: objects)
			o.render(g);
	}
	
	public void onMouseMove(MouseEvent e) {
		for(UIObject o: objects)
			o.onMouseMove(e);
	}
	
	public void onMouseRelease(MouseEvent e) {
		for(UIObject o: objects)
			o.onMouseRelease(e);
	}
	
	public void addObject(UIObject o) {
		objects.add(o);
	}
	
	public Handler getHandler() {
		return handler;
	}

	public void setHandler(Handler handler) {
		this.handler = handler;
	}

	public ArrayList<UIObject> getObjects() {
		return objects;
	}

	public void setObjects(ArrayList<UIObject> objects) {
		this.objects = objects;
	}

	public void removeObject(UIObject o) {
		objects.remove(o);
	}
	
	public void setObjectsActive(boolean active) {
		for(UIObject o: objects) {
			o.setActive(active);
		}
		this.active = active;
	}
	
	public void unHowerObjects() {
		for(UIObject o: objects) {
			o.setHovering(false);
		}
	}
	
	public boolean getActive() {
		return this.active;
	}
}
