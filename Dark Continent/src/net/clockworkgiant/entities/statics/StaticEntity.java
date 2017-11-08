package net.clockworkgiant.entities.statics;

import net.clockworkgiant.entities.Entity;
import net.clockworkgiant.gamebase.Handler;
import net.clockworkgiant.tiles.Tile;

public abstract class StaticEntity extends Entity {
	
	protected int tileX, tileY;
	
	public StaticEntity(Handler handler, int x, int y, int width, int height) {
		super(handler, x * Tile.T_WIDTH, y * Tile.T_HEIGHT, width, height);
		
		tileX = x;
		tileY = y;
		isoPosition(tileX * Tile.T_WIDTH, tileY * Tile.T_HEIGHT);
	}
	
	@Override
	public void Rescale() {
		isoPosition(tileX * Tile.T_WIDTH, tileY * Tile.T_HEIGHT);
	}

	@Override
	public float getISOX() {
		return (tileX * Tile.T_WIDTH - tileY * Tile.T_HEIGHT) / 2.0f;
	}

	@Override
	public float getISOY() {
		return (tileY * Tile.T_HEIGHT + tileX * Tile.T_WIDTH) / 4.0f;
	}
	
}
