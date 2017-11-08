package net.clockworkgiant.tiles;

import net.clockworkgiant.gfx.Assets;

public class Lava extends Tile {

	public Lava(int id) {
		super(Assets.lava, id);
	}
	
	@Override
	public boolean isSolid() {
		return true;
	}

}
