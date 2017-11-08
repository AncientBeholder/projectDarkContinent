package net.clockworkgiant.items.itemLists;

import java.util.ArrayList;

import net.clockworkgiant.entities.mob.specifics.Effect;
import net.clockworkgiant.entities.mob.specifics.SkillEffect;
import net.clockworkgiant.gamebase.Handler;
import net.clockworkgiant.items.equipment.Weapon;

public class WeaponList {

	private Handler handler;
	
	public WeaponList() {
		
	}
	
	public WeaponList(Handler handler) {
		this.handler = handler;
	}
	
	public Weapon getWeapon(String name) {
		Weapon tmp_weapon;
		
		if(name.equals("dummy_stick")) {
			tmp_weapon = new Weapon(handler, "Dummy Stick", "One-Handed", 1, 5, 100, 100, 4,
					new SkillEffect() {
				@Override
				public ArrayList<Effect> skillEfect() {
					ArrayList<Effect> effects = new ArrayList<Effect>();
					
					effects.add(new Effect());
					effects.get(0).damage = 8;
					effects.get(0).effectName = "Damage";
					effects.get(0).element = "Fire";
					
					effects.add(new Effect());
					effects.get(1).effectName = "Burning";
					effects.get(1).element = "Fire";
					effects.get(1).duration = 2;
					effects.get(1).damage = 2;
					effects.get(1).ifAction = true;
					
					return effects;
				}
			});
		}
		
		else tmp_weapon = null;
		return tmp_weapon;
	}
}
