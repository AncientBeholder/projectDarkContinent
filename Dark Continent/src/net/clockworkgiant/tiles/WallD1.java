package net.clockworkgiant.tiles;

import net.clockworkgiant.gfx.Assets;

public class WallD1 extends Tile {

	public WallD1(int id) {
		super(Assets.door2, id);
	}
	
	@Override
	public boolean isSolid() {
		return true;
	}

}
