package net.clockworkgiant.gamebase;

import java.awt.Graphics;
import java.awt.image.BufferStrategy;

import net.clockworkgiant.gfx.Assets;
import net.clockworkgiant.gfx.GameCamera;
import net.clockworkgiant.input.KeyManager;
import net.clockworkgiant.input.MouseManager;
import net.clockworkgiant.screen.Screen;
import net.clockworkgiant.states.GameStates;
import net.clockworkgiant.states.MenuStates;
import net.clockworkgiant.states.State;

public class Game implements Runnable {
	
	private Screen screen;
	private Thread thread;
	
	private BufferStrategy bs;
	private Graphics g;
	
	private boolean running = false;
	
	private int width, height;
	public String title;
	
	public State gameState;
	public State menuState;
	
	private KeyManager keyManager;
	private MouseManager mouseManager;
	private GameCamera gameCamera;
	private Handler handler;
	
	public Game(String title, int width, int height){
		this.width = width;
		this.height = height;
		this.title = title;
		keyManager = new KeyManager();
		mouseManager = new MouseManager();
		
	}
	
	private void init(){
		screen = new Screen(title, width, height);
		screen.getFrame().addKeyListener(keyManager);
		screen.getFrame().addMouseListener(mouseManager);
		screen.getFrame().addMouseMotionListener(mouseManager);
		screen.getFrame().addMouseWheelListener(mouseManager);
		screen.getCanvas().addMouseListener(mouseManager);
		screen.getCanvas().addMouseMotionListener(mouseManager);
		screen.getCanvas().addMouseWheelListener(mouseManager);
		Assets.init();
		handler = new Handler(this);
		mouseManager.setHandler(handler);
		
		gameCamera = new GameCamera(handler, 10, 10);
		gameState = new GameStates(handler);
		menuState = new MenuStates(handler);
		State.setState(menuState);
	}
	
	private void update(){
		keyManager.update();
		if(State.getState() != null)
			State.getState().update();
	}
	
	private void render(){
		bs = screen.getCanvas().getBufferStrategy();
		if(bs == null){
			screen.getCanvas().createBufferStrategy(3);
			return;
		}
		g = bs.getDrawGraphics();
		
		//Clear screan srea
		g.clearRect(0, 0, width, height);
		
		//Check state for render

		if(State.getState() != null)
			State.getState().render(g);
		
		//Can't remember. What was this?
		bs.show();
		g.dispose();
	}
	
	//Main loop
	public void run(){
		init();
		
		int fps = 60;
		double timePerTick = 1_000_000_000.0 / fps;
		double delta = 0;
		long nTime, oTime = System.nanoTime();
		long timer = 0;
		int ticks = 0, ups = 0;
		
		while(running){
			nTime = System.nanoTime();
			delta += (nTime - oTime) / timePerTick;
			timer += nTime - oTime;
			oTime = System.nanoTime();
			ups++;
			
			if(delta >= 1){
				update();
				render();
				ticks++;
				delta--;
			}
			
			if(timer >= 1_000_000_000){
				//System.out.println("FPS: " + ticks);
				screen.getFrame().setTitle(title + " | FPS: " + ticks + " | UPS: " + ups);
				ticks = 0;
				timer = 0;
				ups = 0;
			}

		}
		
		stop();
	}
	
	public KeyManager getKeyManager() {
		return keyManager;
	}
	
	public MouseManager getMouseManager() {
		return mouseManager;
	}

	public GameCamera getGameCamera() {
		return gameCamera;
	}

	public synchronized void start(){
		if(running) return;
		running = true;
		
		thread = new Thread(this);
		thread.start();
	}
	
	public synchronized void stop(){
		if(!running) return;
		running = false;
		
		try {
			thread.join();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public int getWidth() {
		return width;
	}

	/*public void setWidth(int width) {
		this.width = width;
	}*/

	public int getHeight() {
		return height;
	}

	/*public void setHeight(int height) {
		this.height = height;
	}*/

}
