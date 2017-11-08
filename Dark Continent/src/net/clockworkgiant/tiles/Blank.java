package net.clockworkgiant.tiles;

import net.clockworkgiant.gfx.Assets;

public class Blank extends Tile{

	public Blank(int id) {
		super(Assets.blank, id);
	}
	
	@Override
	public boolean isSolid() {
		return true;
	}

}
