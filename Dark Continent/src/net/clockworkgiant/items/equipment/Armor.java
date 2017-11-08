package net.clockworkgiant.items.equipment;

import java.util.ArrayList;

import net.clockworkgiant.entities.mob.Mob;
import net.clockworkgiant.entities.mob.specifics.Effect;
import net.clockworkgiant.entities.mob.specifics.SkillEffect;
import net.clockworkgiant.gamebase.Handler;

public class Armor extends Equipment{

	public int arm0, maxArmMod;
	private ArrayList<Effect> armorEffect;
	
	public Armor(Handler handler, String name, String type, int arm0, int maxArmMod, 
			SkillEffect armorEffect) {
		super(handler, name, "Armor", type);
		this.arm0 = arm0;
		this.maxArmMod = maxArmMod / 99;
		
		this.armorEffect = armorEffect.skillEfect();
	}
	
	public int previewDefense(Mob mob) {
		return this.arm0 + this.maxArmMod * mob.getConstitution();
	}
	
	public int getDefense() {
		return this.arm0 + this.maxArmMod * this.mob.getConstitution();
	}
	
	public int getValue(String value) {
		return getDefense();
	}
	
	@Override
	public ArrayList<Effect> getEffects(){
		return this.armorEffect;
	}
}
