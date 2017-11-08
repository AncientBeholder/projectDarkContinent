package net.clockworkgiant.items.equipment;

import java.util.ArrayList;

import net.clockworkgiant.entities.mob.specifics.Effect;
import net.clockworkgiant.entities.mob.specifics.SkillEffect;
import net.clockworkgiant.gamebase.Handler;

public class Accessories extends Equipment{
	
	private ArrayList<Effect> accEffect;

	public Accessories(Handler handler, String name, String type, SkillEffect accEffect) {
		super(handler, name, "Accessories", type);

		this.accEffect = accEffect.skillEfect();
	}
	

	public int getValue(String value) {return -111;}

	@Override
	public ArrayList<Effect> getEffects() {
		return this.accEffect;
	}

}
