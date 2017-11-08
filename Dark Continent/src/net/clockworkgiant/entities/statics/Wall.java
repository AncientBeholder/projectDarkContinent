package net.clockworkgiant.entities.statics;

import java.awt.Graphics;

import net.clockworkgiant.gamebase.Handler;
import net.clockworkgiant.gfx.Assets;
import net.clockworkgiant.tiles.Tile;

public class Wall extends StaticEntity{

	public Wall(Handler handler, int x, int y) {
		super(handler, x, y, Tile.getZeroWidth(), Tile.getZeroHeight());
	}

	@Override
	public void update() {
		
	}

	@Override
	public void render(Graphics g) {
		Rescale();
		int mTX = handler.getMouseTX();
		int mTY = handler.getMouseTY();
		double rad = Math.sqrt((mTX - tileX) * (mTX - tileX) + (mTY - tileY) * (mTY - tileY));
		
		if(rad >= 2.5) {
			g.drawImage(Assets.wall, (int) (a - handler.getGameCamera().getxOffset()), 
						(int) (b - handler.getGameCamera().getyOffset()), Tile.T_WIDTH, Tile.T_HEIGHT, null);
		}
	}

}
