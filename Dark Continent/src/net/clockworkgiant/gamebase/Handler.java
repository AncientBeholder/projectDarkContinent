package net.clockworkgiant.gamebase;

import net.clockworkgiant.gfx.GameCamera;
import net.clockworkgiant.input.KeyManager;
import net.clockworkgiant.input.MouseManager;
import net.clockworkgiant.tiles.Tile;
import net.clockworkgiant.worlds.World;

public class Handler {
	
	private static double SCALE = 1.0; 
	
	private Game game;
	private World world;
	
	public Handler(Game game) {
		this.game = game;
		
	}
	
	public KeyManager getKeyManager() {
		return game.getKeyManager();
	}
	
	public MouseManager getMouseManager() {
		return game.getMouseManager();
	}
	
	public GameCamera getGameCamera() {
		return game.getGameCamera();
	}
	
	public int getWidth() {
		return game.getWidth();
	}
	
	public int getHeight() {
		return game.getHeight();
	}

	public Game getGame() {
		return game;
	}

	public void setGame(Game game) {
		this.game = game;
	}

	public World getWorld() {
		return world;
	}

	public void setWorld(World world) {
		this.world = world;
	}
	
	public int getMouseTX() {
	//	System.out.println("Mouse TX: " + (int) (1.0f * (game.getMouseManager().getMouseX() + game.getGameCamera().getxOffset() - Tile.T_WIDTH / 2.0f) / Tile.T_WIDTH 
	//			+ 2.0f * (game.getMouseManager().getMouseY() + game.getGameCamera().getyOffset() - Tile.T_HEIGHT / 2.0f) / Tile.T_HEIGHT));
		return (int) (1.0f * (game.getMouseManager().getMouseX() + game.getGameCamera().getxOffset() - Tile.T_WIDTH / 2.0f) / Tile.T_WIDTH 
				+ 2.0f * (game.getMouseManager().getMouseY() + game.getGameCamera().getyOffset() - Tile.T_HEIGHT / 2.0f) / Tile.T_HEIGHT);
	}
	
	public int getMouseTY() {
	//	System.out.println("Mouse TY: " + (int) (- 1.0f * (game.getMouseManager().getMouseX() + game.getGameCamera().getxOffset() - Tile.T_WIDTH / 2.0f) / Tile.T_WIDTH 
	//			+ 2.0f * (game.getMouseManager().getMouseY() + game.getGameCamera().getyOffset() - Tile.T_HEIGHT / 2.0f) / Tile.T_HEIGHT));
		return (int) (- 1.0f * (game.getMouseManager().getMouseX() + game.getGameCamera().getxOffset() - Tile.T_WIDTH / 2.0f) / Tile.T_WIDTH 
				+ 2.0f * (game.getMouseManager().getMouseY() + game.getGameCamera().getyOffset() - Tile.T_HEIGHT / 2.0f) / Tile.T_HEIGHT);
	}

	public static double getSCALE() {
		return SCALE;
	}

	public static void setSCALE(double sCALE) {
		SCALE = sCALE;
	}

}
