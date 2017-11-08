package net.clockworkgiant.items.equipment;

import net.clockworkgiant.entities.mob.Mob;
import net.clockworkgiant.gamebase.Handler;
import net.clockworkgiant.items.Item;

public abstract class Equipment extends Item {
	
	protected Mob mob;
	protected String subGroup, type;

	public Equipment(Handler handler, String name, String subGroup, String type) {
		super(handler, name, "Equipment");
		
		this.subGroup = subGroup;
		this.type = type;
	}
	
	@Override
	public void equipTo(Mob mob) {
		this.mob = mob;
	}
	
	@Override
	public Mob equippedTo() {
		return this.mob;
	}

	@Override
	public String getSubGroup() {
		return subGroup;
	}
	
	@Override
	public void unequipFrom() {
		this.mob = null;
	}

	public void setSubGroup(String subGroup) {
		this.subGroup = subGroup;
	}

	@Override
	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

}
