package net.clockworkgiant.states;

import java.awt.Graphics;

import net.clockworkgiant.gamebase.Handler;

public abstract class State {
	
	private static State currentState = null;
	
	public static void setState(State state) {
		currentState = state;
	}
	
	public static State getState() {
		return currentState;
	}

	protected Handler handler;
	
	public State(Handler handler) {
		this.handler = handler;
	}
	
	public abstract void update();
	public abstract void render(Graphics g);
	
}
