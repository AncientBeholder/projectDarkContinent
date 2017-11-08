package net.clockworkgiant.gfx;

import java.awt.image.BufferedImage;

public class Animation {
	
	private int index, speed, zeroIndex;
	private BufferedImage[] frames;
	private long lastTime, timer;
	
	public Animation(int speed, BufferedImage[] frames, int zeroIndex) {
		this.speed = speed;
		this.frames = frames;
		index = zeroIndex;
		lastTime = System.currentTimeMillis();
		timer = 0;
		this.zeroIndex = zeroIndex;
	}
	
	public void update() {
		timer += System.currentTimeMillis() - lastTime;
		lastTime = System.currentTimeMillis();
		
		if(timer > speed) {
			index++;
			timer = 0;
			if(index >= (zeroIndex + 8)) {
				index = zeroIndex;
			}
		}
	}
	
	public BufferedImage getCFrame() {
		return frames[index];
	}
}
