package net.clockworkgiant.worlds;

import net.clockworkgiant.utils.Vector2I;

public class Node {
	private Vector2I tile;
	private Node parentNode;
	private double fCost, gCost, hCost;
	
	public Node(Vector2I tile, Node parentNode, double gCost, double hCost) {
		this.tile = tile;
		this.parentNode = parentNode;
		this.gCost = gCost;
		this.hCost = hCost;
		this.fCost = this.gCost + this.hCost;
	}
	
	public Vector2I getTile() {
		return this.tile;
	}
	
	public double getFCost() {
		return this.fCost;
	}
	
	public double getGCost() {
		return this.gCost;
	}
	
	public double getHCost() {
		return this.hCost;
	}
	
	public Node getParentNode() {
		return this.parentNode;
	}

}
