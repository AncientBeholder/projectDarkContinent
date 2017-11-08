package net.clockworkgiant.states;

import java.awt.Graphics;

import net.clockworkgiant.gamebase.Handler;
import net.clockworkgiant.gfx.GameCamera;
import net.clockworkgiant.worlds.World;

public class GameStates extends State{
	
	private World world;
	private GameCamera camera;

	public GameStates(Handler handler){
		super(handler);
		world = new World(handler, "res/Worlds/TestWorld.txt");
		handler.setWorld(world);
		camera = handler.getGameCamera();
		//handler.getGame().getGameCamera().move(10, 10);
	}
	
	@Override
	public void update() {
		world.update();
		camera.update();
		
	}

	@Override
	public void render(Graphics g) {
		world.render(g);
	}

}
