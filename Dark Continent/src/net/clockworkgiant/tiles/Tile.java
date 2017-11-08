package net.clockworkgiant.tiles;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

import net.clockworkgiant.gamebase.Handler;

public class Tile {
	
	protected BufferedImage texture;
	private final int id;
	
	public static Tile[] tiles = new Tile[256];
	public static Tile floorD1 = new FloorD1(0);
//	public static Tile floorD2 = new FloorD2(10);
//	public static Tile floorD3 = new FloorD3(11);
//	public static Tile floorD4 = new FloorD4(12);
//	public static Tile floorD5 = new FloorD5(13);
//	public static Tile floorD6 = new FloorD6(14);
//	public static Tile floorD7 = new FloorD7(15);
	public static Tile wallD1 = new WallD1(1);
	public static Tile doorD1 = new DoorD1(2);
	public static Tile doorD2 = new DoorD2(3);
	public static Tile lava = new Lava(4);
	public static Tile blank = new Blank(255);
	
	protected static final int TILE_WIDTH = 64, TILE_HEIGHT = 64;
	public static int T_WIDTH, T_HEIGHT;
	
	public Tile(BufferedImage texture, int id) {
		this.texture = texture;
		this.id = id;
		Rescale();
		
		tiles[id] = this;
	}
	
	public static void Rescale() {
		T_HEIGHT = (int) (Handler.getSCALE() * TILE_HEIGHT);
		T_WIDTH = (int) (Handler.getSCALE() * TILE_WIDTH);
	}
	
	public boolean isSolid() {
		return false;
	}
	
	public int getID() {
		return id;
	}
	
	public void update() {
		
	}
	
	public void render(Graphics g, int x, int y) {
		g.drawImage(texture, x, y, T_WIDTH, T_HEIGHT, null);
	}
	
	public static int getZeroWidth() {
		return TILE_WIDTH;
	}
	
	public static int getZeroHeight() {
		return TILE_HEIGHT;
	}
}
