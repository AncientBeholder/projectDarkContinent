package net.clockworkgiant.entities.mob.specifics;

import java.util.ArrayList;
import java.util.List;

import net.clockworkgiant.entities.mob.Mob;
import net.clockworkgiant.gamebase.Handler;
import net.clockworkgiant.gfx.Assets;

public class Classes {
	
	private List<BasicClass> classList;
	private String[] class_name = new String[3];
	private Handler handler;
	private Mob mob;
	
	public Classes(Handler handler, Mob mob, String class_name1) {
		this.handler = handler;
		this.mob = mob;
		this.classList = new ArrayList<BasicClass>();
		this.class_name[0] = class_name1;

		this.class_name[1] = "0";
		this.class_name[2] = "0";
		
		generateClassList();
	}
	
	public void generateClassList(){
		for(int i = 0; i < 3; i++) {
			if(!class_name[i].equals("0")) {
				//System.out.println("Entered creation of " + class_name[i]);
				if(class_name[i].equals("pyromancer")) {
					//System.out.println("Created " + class_name[i]);
					
					BasicClass pyromancer = new BasicClass("Pyromancer", "mage");
					
					Skill fireball = new Skill(handler, mob, "Fireball");
					fireball.setAoe(true);
					fireball.setRadiusAOE(2);
					fireball.setDamage(3);
					fireball.setRange(10);
					fireball.setButtonImage(Assets.attack);
					
					fireball = new Skill(fireball, new SkillEffect() {
						@Override
						public ArrayList<Effect> skillEfect() {
							ArrayList<Effect> effects = new ArrayList<Effect>();
							
							effects.add(new Effect());
							effects.get(0).damage = 8;
							effects.get(0).effectName = "Damage";
							effects.get(0).element = "Fire";
							effects.get(0).radius = 1;
							
							effects.add(new Effect());
							effects.get(1).effectName = "Burning";
							effects.get(1).element = "Fire";
							effects.get(1).duration = 2;
							effects.get(1).damage = 2;
							effects.get(1).radius = 2;
							effects.get(1).ifAction = true;
							
							return effects;
						}
					});
					
					pyromancer.addSkill(fireball);
					
					classList.add(pyromancer);
				}
			}
		}
	}

	public List<BasicClass> getClassList(){
		return classList;
	}

}
