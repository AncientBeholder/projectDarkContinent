package net.clockworkgiant.entities;

import java.awt.Graphics;
import java.util.ArrayList;
import java.util.Comparator;

import net.clockworkgiant.entities.mob.Mob;
import net.clockworkgiant.entities.mob.Player;
import net.clockworkgiant.gamebase.Handler;
import net.clockworkgiant.tiles.Tile;

public class EntityManager {
	
	private Handler handler;
	private ArrayList<Mob> player;
	private ArrayList<Entity> entities;
	
	private Comparator<Entity> renderSorter = new Comparator<Entity>(){

		@Override
		public int compare(Entity a, Entity b) {
			if((a.getISOY() + a.getHeight() * 0.75f) < (b.getISOY() + b.getHeight() * 0.75f)) {
				return -1;
			}
			return 1;
		}
		
	};
	
	public EntityManager(Handler handler) {
		this.handler = handler;
		entities = new ArrayList<Entity>();
		player = new ArrayList<Mob>();
	}
	
	public void update() {
		for(int i = 0; i < entities.size(); i++) {
			entities.get(i).update();
		}
	}
	
	public void render(Graphics g) {
		for(Mob p: player) {
			p.renderGrid(g);
		}
		for(Entity e: entities) {
			if(((e.getISOX() - handler.getGameCamera().getxOffset() + Tile.T_WIDTH) > 0) 
					&& ((e.getISOX() - handler.getGameCamera().getxOffset()) < handler.getWidth()) 
					&& ((e.getISOY() - handler.getGameCamera().getyOffset() + Tile.T_HEIGHT) > 0) 
					&& ((e.getISOY() - handler.getGameCamera().getyOffset()) < handler.getHeight())) {
						
				e.render(g);
			}
		}
		entities.sort(renderSorter);
		for(Mob p: player) {
			p.battleUIrender(g);
		}
	}
	
	public void addEntity(Entity e) {
		entities.add(e);
	}

	public Handler getHandler() {
		return handler;
	}

	public void setHandler(Handler handler) {
		this.handler = handler;
	}

	public ArrayList<Mob> getPlayer() {
		return player;
	}

	public void setPlayer(Player player) {
		this.player.add(player);
		this.entities.add(player);
	}

	public ArrayList<Entity> getEntities() {
		return entities;
	}

	public void setEntities(ArrayList<Entity> entities) {
		this.entities = entities;
	}

}
