package net.clockworkgiant.entities.mob.specifics.race;

import net.clockworkgiant.entities.mob.Mob;

public class Race {
	private String race;
	private Mob mob;

	public Race () {
	}
	
	public void setRaceStats(Mob mob) {
		this.mob = mob;
		this.race = this.mob.getRace();
		
		if(race == "Dummy") {
			mob.setConstitution(5);
			mob.setStrength(5);
			mob.setDexterity(5);
			mob.setIntellect(5);
			mob.setWisdom(5);
			mob.setCharisma(5);
			mob.setLuck(5);
		}else if(race == "Human") {
			mob.setConstitution(4);
			mob.setStrength(4);
			mob.setDexterity(6);
			mob.setIntellect(5);
			mob.setWisdom(4);
			mob.setCharisma(5);
			mob.setLuck(7);
		}else if(race == "Warlord") {
			mob.setConstitution(7);
			mob.setStrength(6);
			mob.setDexterity(6);
			mob.setIntellect(4);
			mob.setWisdom(4);
			mob.setCharisma(5);
			mob.setLuck(4);
		}else if(race == "Senator") {
			mob.setConstitution(4);
			mob.setStrength(4);
			mob.setDexterity(5);
			mob.setIntellect(6);
			mob.setWisdom(7);
			mob.setCharisma(5);
			mob.setLuck(4);
		}else if(race == "Juggernaut") {
			mob.setConstitution(4);
			mob.setStrength(7);
			mob.setDexterity(6);
			mob.setIntellect(4);
			mob.setWisdom(5);
			mob.setCharisma(4);
			mob.setLuck(5);
		}else if(race == "Master") {
			mob.setConstitution(4);
			mob.setStrength(4);
			mob.setDexterity(5);
			mob.setIntellect(5);
			mob.setWisdom(6);
			mob.setCharisma(7);
			mob.setLuck(4);
		}else if(race == "Mystic") {
			mob.setConstitution(5);
			mob.setStrength(4);
			mob.setDexterity(4);
			mob.setIntellect(7);
			mob.setWisdom(6);
			mob.setCharisma(5);
			mob.setLuck(4);
		}
	}
}
