package net.clockworkgiant.gfx;

import net.clockworkgiant.entities.Entity;
import net.clockworkgiant.gamebase.Handler;
import net.clockworkgiant.tiles.Tile;

public class GameCamera {
	private Handler handler;
	private float xOffset, yOffset;
	public float xAmt = 10, yAmt = 10;
	private float xISO, yISO;
	private boolean lock = false;
	private int tileH, tileW;
	
	public GameCamera(Handler handler, float xOffset, float yOffset) {
		this.handler = handler;
		this.xOffset = xOffset;
		this.yOffset = yOffset;
		this.tileH = Tile.T_HEIGHT;
		this.tileW = Tile.T_WIDTH;
	}
	
	public void update() {
		if(!handler.getKeyManager().c_SNAP && !lock) {
			moveX();
			moveY();
		}
		
		//System.out.println("||||||||||||||||");
		//System.out.println("xOffset: " + xOffset + " | yOffset: " + yOffset);
		//System.out.println("Right: " + (handler.getKeyManager().c_RIGHT 
		//		&& ((xOffset) < (handler.getWorld().getISOWidth() - handler.getGame().getWidth() / 2))));
		//System.out.println("Left: " + (handler.getKeyManager().c_LEFT
		//		&& ((xOffset) > (-handler.getWorld().getISOWidth() - handler.getGame().getWidth() / 2))));
		//System.out.println("Up: " + (handler.getKeyManager().c_UP
		//		&& ((yOffset) > (- handler.getGame().getHeight() / 2))));
		//System.out.println("Down: " + (handler.getKeyManager().c_DOWN
		//		&& ((yOffset) < (handler.getWorld().getISOHeight() - handler.getGame().getHeight() / 2))));
	}
	
	public void moveX() {
		if(handler.getKeyManager().c_RIGHT 
				&& ((xOffset) < (handler.getWorld().getISOWidth() - handler.getGame().getWidth() / 2))) {
			xOffset += xAmt;
			System.out.println("xOffset: " + xOffset + " | yOffset: " + yOffset);
		}
		else if(handler.getKeyManager().c_LEFT
				&& ((xOffset) > (-handler.getWorld().getISOWidth() - handler.getGame().getWidth() / 2))) {
			xOffset -= xAmt;
			System.out.println("xOffset: " + xOffset + " | yOffset: " + yOffset);
		}
	}

	public void moveY() {
		if(handler.getKeyManager().c_UP
				&& ((yOffset) > (- handler.getGame().getHeight() / 2))) {
			yOffset -= yAmt;
			System.out.println("xOffset: " + xOffset + " | yOffset: " + yOffset);
		}
		else if(handler.getKeyManager().c_DOWN
				&& ((yOffset) < (handler.getWorld().getISOHeight() - handler.getGame().getHeight() / 2))) {
			yOffset += yAmt;
			System.out.println("xOffset: " + xOffset + " | yOffset: " + yOffset);
		}
	}
	
	public void centerOnEntity(Entity e) {
		Rescale();
		xOffset = e.getISOX() + e.getWidth() / 2 - handler.getWidth() / 2;
		yOffset = e.getISOY() + e.getHeight() / 2 - handler.getHeight() / 2;
	}

	public float getxOffset() {
		return xOffset;
	}

	public void setxOffset(float xOffset) {
		this.xOffset = xOffset;
	}

	public float getyOffset() {
		return yOffset;
	}

	public void setyOffset(float yOffset) {
		this.yOffset = yOffset;
	}

	public boolean isLock() {
		return lock;
	}

	public void setLock(boolean lock) {
		this.lock = lock;
	}
	
	public void Rescale() {
		int tileX = (int) ((xOffset + handler.getWidth() / 2.0) / tileW);
		int tileY = (int) ((yOffset + handler.getHeight() / 2.0) / tileH);
		tileW = Tile.T_WIDTH;
		tileH = Tile.T_HEIGHT;
		xOffset = tileX * tileW - handler.getWidth() / 2.0f;
		yOffset = tileY * tileH - handler.getHeight() / 2.0f;
	}

}
