package net.clockworkgiant.entities.statics;

import java.awt.Graphics;

import net.clockworkgiant.gamebase.Handler;
import net.clockworkgiant.gfx.Assets;
import net.clockworkgiant.tiles.Tile;

public class Statue extends StaticEntity{

	public Statue(Handler handler, int x, int y) {
		super(handler, x, y, Tile.getZeroWidth(), Tile.getZeroHeight());
	}

	@Override
	public void update() {
		
	}

	@Override
	public void render(Graphics g) {
		Rescale();
		g.drawImage(Assets.statue, (int) (a - handler.getGameCamera().getxOffset()), 
					(int) (b - handler.getGameCamera().getyOffset()), Tile.T_WIDTH, Tile.T_HEIGHT, null);
	}

}
