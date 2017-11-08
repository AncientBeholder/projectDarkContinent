package net.clockworkgiant.entities.mob.movement;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import net.clockworkgiant.entities.mob.Mob;
import net.clockworkgiant.gamebase.Handler;
import net.clockworkgiant.tiles.Tile;
import net.clockworkgiant.utils.Vector2I;
import net.clockworkgiant.worlds.Node;


public class AStarMovement {

	private Handler handler;
	
	private Comparator<Node> nodeSorter = new Comparator<Node>() {

		@Override
		public int compare(Node n0, Node n1) {
			if(n0.getFCost() > n1.getFCost()) return 1;
			else if(n0.getFCost() < n1.getFCost()) return -1;
			return 0;
		}
		
	};
	
	public AStarMovement(Handler handler) {
		this.handler = handler;
	}
	
	public List<Node> findPath(Vector2I start, Vector2I end, Mob mob){
		List<Node> openList = new ArrayList<Node>();
		List<Node> closedList = new ArrayList<Node>();
		Node current = new Node(start, null, 0, getDistance(start, end));
		openList.add(current);
		while(openList.size() > 0) {
			Collections.sort(openList, nodeSorter);
			current = openList.get(0);
			if(current.getTile().equal(end)) {
				List<Node> path = new ArrayList<Node>();
				while(current.getParentNode() != null) {
					//System.out.println("End in-check: " + current.getTile().getX() + " | " + current.getTile().getY());
					path.add(current);
					current = current.getParentNode();
				}
				openList.clear();
				closedList.clear();
				return path;
			}
			openList.remove(current);
			closedList.add(current);
			
			for(int i = 0; i < 9; i++) {
				if(i == 4) continue;
				int x = current.getTile().getX();
				int y = current.getTile().getY();
				int xi = (i % 3) - 1;
				int yi = (i / 3) - 1;
				Tile active = handler.getWorld().getTile(x + xi, y + yi);
				if(active == null) continue;
				if(active.isSolid()) continue;
				if(!mob.checkGrid(x + xi, y + yi)) continue;

				if(i == 8 && handler.getWorld().getTile(x, y + 1).isSolid() && handler.getWorld().getTile(x + 1, y).isSolid()) continue;
				if(i == 6 && handler.getWorld().getTile(x, y + 1).isSolid() && handler.getWorld().getTile(x - 1, y).isSolid()) continue;
				if(i == 2 && handler.getWorld().getTile(x, y - 1).isSolid() && handler.getWorld().getTile(x + 1, y).isSolid()) continue;
				if(i == 0 && handler.getWorld().getTile(x, y - 1).isSolid() && handler.getWorld().getTile(x - 1, y).isSolid()) continue;
				
				Vector2I activeV = new Vector2I(x + xi, y + yi);
				double gCost = current.getGCost() + getDistance(current.getTile(), activeV);
				double hCost = getDistance(activeV, end);
				Node node = new Node(activeV, current, gCost, hCost);
				if(vecInList(closedList, activeV) && gCost >= node.getGCost()) continue;
				if(!vecInList(openList, activeV) || gCost < node.getGCost()) openList.add(node);
				
			}

		}
		closedList.clear();
		return null;
	}
	
	private boolean vecInList(List<Node> list, Vector2I vector) {
		for(Node n : list) {
			if(n.getTile().equal(vector)) return true;
		}
		return false;
	}
	
	private double getDistance(Vector2I start, Vector2I end) {
		double dx = start.getX() - end.getX();
		double dy = start.getY() - end.getY();
		return Math.sqrt(dx * dx + dy * dy);
	}
}
