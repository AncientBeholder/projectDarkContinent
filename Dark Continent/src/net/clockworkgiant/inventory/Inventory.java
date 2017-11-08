package net.clockworkgiant.inventory;

import java.util.ArrayList;

import net.clockworkgiant.gamebase.Handler;
import net.clockworkgiant.items.Item;

public abstract class Inventory {
	
	protected Handler handler;
	protected ArrayList<Item> list;
	protected int volume, weight;
	
	
	public Inventory(Handler handler) {
		this.handler = handler;
		this.list = new ArrayList<Item>();
	}
	
	public void addItem(Item item) {
		list.add(item);
	}
	
	public void addItems(ArrayList<Item> list) {
		this.list.addAll(list);
	}
	
	public void removeItem(Item item) {
		this.list.remove(item);
	}
	
	public void removeItems(ArrayList<Item> list) {
		this.list.removeAll(list);
	}
	
	public ArrayList<Item> getList() {
		return this.list;
	}

}
