package net.clockworkgiant.items.equipment;

import java.util.ArrayList;

import net.clockworkgiant.entities.mob.Mob;
import net.clockworkgiant.entities.mob.specifics.Effect;
import net.clockworkgiant.entities.mob.specifics.SkillEffect;
import net.clockworkgiant.gamebase.Handler;

public class Weapon extends Equipment{

	private int dmgW0, StrMod, DexMod, range, charges, maxCharges;
	private ArrayList<Effect> weaponEffect;
	
	public Weapon(Handler handler, String name, String type, int range, int dmgW0, int maxStrDmg, int maxDexDmg, 
			int maxCharges, SkillEffect weaponEffect) {
		super(handler, name, "Weapon", type);
		
		this.dmgW0 = dmgW0;
		this.range = range;
		this.maxCharges = maxCharges;
		this.StrMod = maxStrDmg / 99;
		this.DexMod = maxDexDmg / 99;
		
		this.weaponEffect = weaponEffect.skillEfect();
	}
	
	public int previewDamage(Mob mob) {
		return dmgW0 + mob.getStrength() + StrMod * mob.getStrength() + DexMod * mob.getDexterity();
	}
	
	private int getDamage() {
		return dmgW0 + this.mob.getStrength() + StrMod * this.mob.getStrength() + DexMod * this.mob.getDexterity();
	}
	
	public int getValue(String value) {
		//physical attack
		if(value.equals("p_damage")) return getDamage();
		else if(value.equals("range")) return range;
		else if(value.equals("charges")) return maxCharges;
		
		else return -133;
	}
	
	@Override
	public String toString() {
		return ("Name: " + name + " | SubGroup: " + subGroup + " | Type " + type);
	}

	@Override
	public ArrayList<Effect> getEffects(){
		ArrayList<Effect> effects = new ArrayList<Effect>();
		
		if(mob != null) {
			effects.add(new Effect());
			effects.get(0).damage = getDamage();
			effects.get(0).effectName = "Damage";
			effects.get(0).element = "Blunt";
		}
		
		effects.addAll(weaponEffect);
		return effects;
	}
	
	public int getRange() {
		return range;
	}
}
