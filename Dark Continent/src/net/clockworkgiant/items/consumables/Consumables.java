package net.clockworkgiant.items.consumables;

import java.util.ArrayList;

import net.clockworkgiant.entities.mob.specifics.Effect;
import net.clockworkgiant.entities.mob.specifics.SkillEffect;
import net.clockworkgiant.gamebase.Handler;
import net.clockworkgiant.items.Item;

public abstract class Consumables extends Item{
	
	private int uses;
	private ArrayList<Effect> effect;

	public Consumables(Handler handler, String name, int uses, SkillEffect effect) {
		super(handler, name, "Consumable");
		
		this.uses = uses;
		this.effect = effect.skillEfect();
	}
	
	public ArrayList<Effect> getEffects(){
		return this.effect;
	}

	public int getUses() {
		return uses;
	}

	public void setUses(int uses) {
		this.uses = uses;
	}
	
	public void addEffect(Effect effect) {
		this.effect.add(effect);
	}
	
	public void removeEffect(Effect effect) {
		this.effect.remove(effect);
	}

	public ArrayList<Effect> getEffect() {
		return effect;
	}

	public void setEffect(ArrayList<Effect> effect) {
		this.effect = effect;
	}

	
}
