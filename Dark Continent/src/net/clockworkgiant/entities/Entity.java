package net.clockworkgiant.entities;

import java.awt.Graphics;
import java.awt.Rectangle;

import net.clockworkgiant.gamebase.Handler;

public abstract class Entity {
	
	protected int STARTING_WIDTH, STARTING_HEIGHT;
	
	protected Handler handler;
	protected float x, y;
	protected float a, b;
	protected int width, height;
	protected Rectangle bounds;
	
	public Entity(Handler handler, float x, float y, int width, int height) {
		this.handler = handler;
		this.x = x;
		this.y = y;
		this.STARTING_WIDTH = width;
		this.STARTING_HEIGHT = height;
		this.width = (int) (Handler.getSCALE() * width);
		this.height = (int) (Handler.getSCALE() * height);
		
		bounds = new Rectangle(0, 0, width, height);
	}
	
	public abstract void update();
	public abstract void render(Graphics g);
	
	public boolean checkEntityCollision(float xOffset, float yOffset) {
		return false;
	}
	
	public Rectangle getCollisionBounds(float xOffset, float yOffset) {
		return new Rectangle((int) (x + bounds.x + xOffset),(int) (y + bounds.y + yOffset), bounds.width, bounds.height);
	}

	public float getX() {
		return x;
	}

	public void setX(float x) {
		this.x = x;
	}

	public float getY() {
		return y;
	}

	public void setY(float y) {
		this.y = y;
	}
	
	public float getISOX() {
		return (x - y) / 2.0f;
	}
	
	public float getISOY() {
		return (y + x) / 4.0f;
	}

	public int getWidth() {
		return width;
	}

	public void setWidth(int width) {
		this.width = width;
	}

	public int getHeight() {
		return height;
	}

	public void setHeight(int height) {
		this.height = height;
	}

	public Handler getHandler() {
		return handler;
	}
	

	protected void isoPosition(float x, float y) {
		a = (x - y) / 2.0f;
		b = (y + x) / 4.0f;
	}
	
	public abstract void Rescale();

}
