package net.clockworkgiant.utils;

public class Vector2I {
	private int x, y;
	
	public Vector2I() {
		set(0, 0);
	}
	
	public Vector2I(int x, int y) {
		set(x, y);
	}
	
	public Vector2I(Vector2I vector) {
		set(vector.x, vector.y);
	}

	public void set(int x, int y) {
		this.x = x;
		this.y = y;
	}
	
	public void add() {
		
	}
	
	public int getX() {
		return this.x;
	}
	
	public int getY() {
		return this.y;
	}
	
	public Vector2I setX(int x) {
		this.x = x;
		return this;
	}
	
	public Vector2I setY(int y) {
		this.y = y;
		return this;
	}
	
	public Vector2I add(Vector2I vector) {
		this.x += vector.x;
		this.y += vector.y;
		return this;
	}
	
	public Vector2I subtract(Vector2I vector) {
		this.x -= vector.x;
		this.y -= vector.y;
		return this;
	}
	
	public boolean equal(Vector2I vector) {
		if(this.x == vector.x && this.y == vector.y)
			return true;
		else
			return false;
	}
}
