package net.clockworkgiant.items;

import java.awt.image.BufferedImage;
import java.util.ArrayList;

import net.clockworkgiant.entities.mob.Mob;
import net.clockworkgiant.entities.mob.specifics.Effect;
import net.clockworkgiant.gamebase.Handler;

public abstract class Item {

	protected String name, description, group;
	protected int cost, volume, weight;
	protected Handler handler;
	protected BufferedImage inventoryIcon, onTileImage;
	
	public Item(Handler handler, String name, String group) {
		this.handler = handler;
		this.name = name;
		this.group = group;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getGroup() {
		return group;
	}

	public void setGroup(String group) {
		this.group = group;
	}
	
	public abstract String getSubGroup();
	public abstract String getType();
	public abstract ArrayList<Effect> getEffects();
	public abstract void equipTo(Mob mob);
	public abstract Mob equippedTo();
	public abstract int getValue(String value);
	public abstract void unequipFrom();

	public BufferedImage getInventoryIcon() {
		return inventoryIcon;
	}

	public void setInventoryIcon(BufferedImage inventoryIcon) {
		this.inventoryIcon = inventoryIcon;
	}

	public int getCost() {
		return cost;
	}

	public void setCost(int cost) {
		this.cost = cost;
	}

	public int getVolume() {
		return volume;
	}

	public void setVolume(int volume) {
		this.volume = volume;
	}

	public int getWeight() {
		return weight;
	}

	public void setWeight(int weight) {
		this.weight = weight;
	}

	public BufferedImage getOnTileImage() {
		return onTileImage;
	}

	public void setOnTileImage(BufferedImage onTileImage) {
		this.onTileImage = onTileImage;
	}
	
	
}
